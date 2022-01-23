package id.superautocash.mobile.api.integration

import id.superautocash.mobile.api.controller.request.UserExistsRequest
import id.superautocash.mobile.api.controller.request.UserLoginRequest
import id.superautocash.mobile.api.controller.request.UserRegisterRequest
import id.superautocash.mobile.api.controller.response.UserExistsResponse
import id.superautocash.mobile.api.controller.response.UserLoginResponse
import id.superautocash.mobile.api.controller.response.UserRegisterResponse
import id.superautocash.mobile.api.entity.User
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import id.superautocash.mobile.api.repository.UserRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigInteger
import java.security.MessageDigest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserApiTests: BaseApiTests() {

    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeAll
    fun deleteAllUser() {
        userRepository.deleteAll()
    }

    @Test
    fun login_userNotFound() {
        val request = UserLoginRequest("username", password = "pwd")
        val response = post("/user/login", request, UserLoginResponse::class)
        assert(!response.success)
        assert(response.errorCode == GeneralExceptionEnum.USER_NOT_FOUND.errorCode)
    }

    @Test
    fun login_passwordInvalid() {
        val md5 = MessageDigest.getInstance("MD5")
        val user = userRepository.save(User(
            password = BigInteger(1, md5.digest("1234!@#$".toByteArray())).toString(16).padStart(32, '0'),
            roleId = 1,
            username = "firzagustama",
            email = "firza.gustama@gmail.com",
            phoneNumber = "081931066028",
            fullName = "Muhammad Firza Gustama"
        ))

        val request = UserLoginRequest(username = "firzagustama", password = "12345")
        val response = post("/user/login", request, UserLoginResponse::class)

        userRepository.delete(user)

        assert(!response.success)
        assert(response.errorCode == GeneralExceptionEnum.PASSWORD_INVALID.errorCode)
    }

    @Test
    fun register() {
        val md5 = MessageDigest.getInstance("MD5")
        val request = UserRegisterRequest(
            password = BigInteger(1, md5.digest("1234!@#$".toByteArray())).toString(16).padStart(32, '0'),
            username = "firzagustama",
            email = "firza.gustama@gmail.com",
            phoneNumber = "08192384719",
            fullName = "Muhammad Firza Gustama"
        )
        val response = post("/user/register", request, UserRegisterResponse::class)
        val data = response.data
        userRepository.deleteById(data?.id!!)

        assert(response.success)
        assert(data.id != null)
        assert(data.token.isNotBlank())
    }

    @Test
    fun registerUserExists() {
        val md5 = MessageDigest.getInstance("MD5")
        val request = UserRegisterRequest(
            password = BigInteger(1, md5.digest("1234!@#$".toByteArray())).toString(16).padStart(32, '0'),
            username = "firzagustama",
            email = "firza.gustama@gmail.com",
            phoneNumber = "08192384719",
            fullName = "Muhammad Firza Gustama"
        )
        val response = post("/user/register", request, UserRegisterResponse::class)
        val data = response.data

        val response2 = post("/user/register", request, UserRegisterResponse::class)
        userRepository.deleteById(data?.id!!)

        assert(!response2.success)
        assert(response2.errorCode == GeneralExceptionEnum.USERNAME_ALREADY_EXISTS.errorCode)
    }

    @Test
    fun registerAndLogin() {
        // register
        val md5 = MessageDigest.getInstance("MD5")
        val password = BigInteger(1, md5.digest("1234!@#$".toByteArray())).toString(16).padStart(32, '0')
        val registerRequest = UserRegisterRequest(
            password = password,
            username = "firzagustama",
            email = "firza.gustama@gmail.com",
            phoneNumber = "08192384719",
            fullName = "Muhammad Firza Gustama"
        )
        val registerResponse = post("/user/register", registerRequest, UserRegisterResponse::class)

        assert(registerResponse.success)
        assert(registerResponse.data?.id != null)
        assert(!registerResponse.data?.token.isNullOrBlank())

        // login with username
        val loginUsernameRequest = UserLoginRequest(
            username = registerResponse.data?.username ?: "",
            password = password
        )
        val loginUsernameResponse = post("/user/login", loginUsernameRequest, UserLoginResponse::class)

        assert(loginUsernameResponse.success)
        assert(loginUsernameResponse.data?.id != null)
        assert(!loginUsernameResponse.data?.token.isNullOrBlank())

        // login with email
        val loginEmailRequest = UserLoginRequest(
            email = registerResponse.data?.email ?: "",
            password = password
        )
        val loginEmailResponse = post("/user/login", loginEmailRequest, UserLoginResponse::class)

        assert(loginEmailResponse.success)
        assert(loginEmailResponse.data?.id != null)
        assert(!loginEmailResponse.data?.token.isNullOrBlank())

        // remove registered test user
        userRepository.deleteById(registerResponse.data?.id!!)
    }

    @Test
    fun userCheck_exists() {
        // register
        val registerRequest = UserRegisterRequest(
            password = "1234!@#$",
            username = "firzagustama",
            email = "firza.gustama@gmail.com",
            phoneNumber = "08192384719",
            fullName = "Muhammad Firza Gustama"
        )
        val registerResponse = post("/user/register", registerRequest, UserRegisterResponse::class)

        // check by username
        val requestUsername = UserExistsRequest(username = "firzagustama")
        val responseUsername = post("/user/check", requestUsername, UserExistsResponse::class)

        assert(responseUsername.success)
        assert(responseUsername.data?.exists == true)

        // check by email
        val requestEmail = UserExistsRequest(email = "firza.gustama@gmail.com")
        val responseEmail = post("/user/check", requestEmail, UserExistsResponse::class)

        assert(responseEmail.success)
        assert(responseEmail.data?.exists == true)

        // delete registered user
        userRepository.deleteById(registerResponse.data?.id!!)
    }

    @Test
    fun userCheck_notExists() {
        val request = UserExistsRequest("testingtesting")
        val response = post("/user/check", request, UserExistsResponse::class)

        assert(response.success)
        assert(response.data?.exists == false)
    }
}