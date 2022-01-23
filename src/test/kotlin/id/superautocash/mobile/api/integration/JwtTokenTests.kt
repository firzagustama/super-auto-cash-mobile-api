package id.superautocash.mobile.api.integration

import id.superautocash.mobile.api.controller.request.ApiRequest
import id.superautocash.mobile.api.controller.response.BaseResponse
import id.superautocash.mobile.api.entity.User
import id.superautocash.mobile.api.repository.UserRepository
import id.superautocash.mobile.api.security.entity.UserDetailsSecurity
import id.superautocash.mobile.api.security.jwt.JwtUtils
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.*

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class JwtTokenTests: BaseApiTests() {

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository
    lateinit var testUser: User

    @Value("\${superautocash.auth.jwt.secret}")
    lateinit var secretKey: String

    @Value("\${superautocash.auth.jwt.expiration}")
    lateinit var expiration: String

    @BeforeAll
    fun createTestUser() {
        testUser = userRepository.save(User(
            password = "1234!@#$",
            username = "jwt_test_user",
            roleId = 1,
            email = "jwt.test.user@gmail.com",
            phoneNumber = "081938728478",
            fullName = "JWT Test User"
        ))
    }

    @AfterAll
    fun deleteTestUser() {
        userRepository.delete(testUser)
    }

    @Test
    fun token_valid() {
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(testUser.username, testUser.password))
        val token = "Bearer ${jwtUtils.generateToken(auth)}"

        clearHeader()
        addHeader("Authorization", token)
        val request = ApiRequest<Nothing>()
        val response = post("/auth/check", request, BaseResponse::class)

        assert(response.success)
    }

    @Test
    fun token_expired() {
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(testUser.username, testUser.password))
        val token = "Bearer ${generateExpiredToken(auth)}"

        clearHeader()
        addHeader("Authorization", token)
        val request = ApiRequest<Nothing>()
        val response = post("/auth/check", request, BaseResponse::class)

        assert(!response.success)
    }

    private fun generateExpiredToken(auth: Authentication): String {
        val user = (auth.principal as UserDetailsSecurity)
        return Jwts.builder()
            .setSubject(user.toJson())
            .setIssuedAt(Date())
            .setExpiration(Date(0))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
    }
}