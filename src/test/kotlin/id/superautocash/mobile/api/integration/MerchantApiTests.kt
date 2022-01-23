package id.superautocash.mobile.api.integration

import id.superautocash.mobile.api.controller.request.MerchantLoginRequest
import id.superautocash.mobile.api.controller.request.MerchantRegisterRequest
import id.superautocash.mobile.api.controller.response.MerchantLoginResponse
import id.superautocash.mobile.api.controller.response.MerchantRegisterResponse
import id.superautocash.mobile.api.entity.User
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import id.superautocash.mobile.api.enums.RoleEnum
import id.superautocash.mobile.api.repository.UserRepository
import id.superautocash.mobile.api.security.jwt.JwtUtils
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MerchantApiTests @Autowired constructor(
    val repository: UserRepository,
    val jwtUtils: JwtUtils
): BaseApiTests() {

    lateinit var testUser_user: User
    lateinit var testUser_merchant: User

    @BeforeAll
    fun createTestUser() {
        testUser_user = repository.save(User(
            password = "1234!@#$",
            roleId = RoleEnum.USER.id,
            username = "testUser_user",
            email = "test.user@gmail.com",
            phoneNumber = "081938748723",
            fullName = "testUser_user",
        ))

        testUser_merchant = repository.save(User(
            password = "1234!@#$",
            roleId = RoleEnum.MERCHANT.id,
            username = "testUser_merchant",
            email = "test.merchant@gmail.com",
            phoneNumber = "081938748723",
            fullName = "testUser_merchant",
        ))
    }

    @AfterAll
    fun deleteTestUser() {
        repository.delete(testUser_user)
        repository.delete(testUser_merchant)
    }

    @Test
    fun login_notExists() {
        val request = MerchantLoginRequest("testingtesting", null, "1234!@#$")
        val response = post("/merchant/login", request, MerchantLoginResponse::class)

        assert(!response.success)
        assert(response.errorCode == GeneralExceptionEnum.USER_NOT_FOUND.errorCode)
    }

    @Test
    fun login_roleNotMerchant() {
        val request = MerchantLoginRequest(testUser_user.username, null, testUser_user.password)
        val response = post("/merchant/login", request, MerchantLoginResponse::class)

        assert(!response.success)
        assert(response.errorCode == GeneralExceptionEnum.USER_NOT_FOUND.errorCode)
    }

    @Test
    fun login_success() {
        val request = MerchantLoginRequest(testUser_merchant.username, null, testUser_merchant.password)
        val response = post("/merchant/login", request, MerchantLoginResponse::class)

        assert(response.success)
        assert(jwtUtils.validateToken(response.data!!.token))
    }

    @Test
    fun login_invalidPassword() {
        val request = MerchantLoginRequest(null, testUser_merchant.email, "anything_wrong")
        val response = post("/merchant/login", request, MerchantLoginResponse::class)

        assert(!response.success)
        assert(response.errorCode == GeneralExceptionEnum.PASSWORD_INVALID.errorCode)
    }

    @Test
    fun register_userExists() {
        val request = MerchantRegisterRequest(
            password = "1234!@#$",
            username = testUser_merchant.username,
            email = testUser_merchant.email,
            phoneNumber = testUser_merchant.phoneNumber,
            fullName = testUser_merchant.fullName,
            address = testUser_merchant.address!!
        )
        val response = post("/merchant/register", request, MerchantRegisterResponse::class)

        assert(!response.success)
        assert(response.errorCode == GeneralExceptionEnum.USERNAME_ALREADY_EXISTS.errorCode)
    }

    @Test
    fun register_success() {
        val request = MerchantRegisterRequest(
            password = "1234!@#$",
            username = "inigabakalada",
            email = "unique.email@gmail.com",
            phoneNumber = testUser_merchant.phoneNumber,
            fullName = testUser_merchant.fullName,
            address = testUser_merchant.address!!
        )
        val response = post("/merchant/register", request, MerchantRegisterResponse::class)

        assert(response.success)
        assert(jwtUtils.validateToken(response.data!!.token))
    }
}