package id.superautocash.mobile.api.service.impl

import id.superautocash.mobile.api.controller.request.UserExistsRequest
import id.superautocash.mobile.api.controller.request.UserLoginRequest
import id.superautocash.mobile.api.controller.request.UserRegisterRequest
import id.superautocash.mobile.api.controller.response.UserExistsResponse
import id.superautocash.mobile.api.controller.response.UserLoginResponse
import id.superautocash.mobile.api.controller.response.UserRegisterResponse
import id.superautocash.mobile.api.entity.User
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import id.superautocash.mobile.api.enums.RoleEnum
import id.superautocash.mobile.api.exception.ApiException
import id.superautocash.mobile.api.repository.UserRepository
import id.superautocash.mobile.api.security.entity.UserDetailsSecurity
import id.superautocash.mobile.api.security.jwt.JwtUtils
import id.superautocash.mobile.api.service.UserService
import id.superautocash.mobile.api.utils.paramNotEitherBlank
import id.superautocash.mobile.api.utils.paramNotNull
import id.superautocash.mobile.api.utils.paramNotNullOrBlank
import id.superautocash.mobile.api.utils.throwException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
    val repository: UserRepository,
    val authenticationManager: AuthenticationManager,
    val jwtUtils: JwtUtils
): UserService {

    override fun login(request: UserLoginRequest?): UserLoginResponse? {
        paramNotNull(request, "request")
        paramNotEitherBlank(request?.username, "username", request?.email, "email")
        paramNotNullOrBlank(request?.password, "password")

        request!!
        val username = if (request.username.isNullOrBlank()) request.email else request.username

        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, request.password))
        val user = (auth.principal as UserDetailsSecurity).user
        if (user.roleId != RoleEnum.USER.id) throwException(GeneralExceptionEnum.USER_NOT_FOUND)
        return UserLoginResponse(
            id = user.id,
            roleId = user.roleId,
            username = user.username,
            email = user.email,
            emailVerified = user.emailVerified ?: false,
            phoneNumber = user.phoneNumber,
            fullName = user.fullName,
            token = jwtUtils.generateToken(auth)
        )
    }

    override fun register(request: UserRegisterRequest?): UserRegisterResponse? {
        // request parameter check
        paramNotNullOrBlank(request?.password, "password")
        paramNotNullOrBlank(request?.username, "username")
        paramNotNullOrBlank(request?.email, "email")
        paramNotNullOrBlank(request?.phoneNumber, "phoneNumber")
        paramNotNullOrBlank(request?.fullName, "fullName")

        request!!

        // check if exists, if not insert user
        repository.findByUsername(request.username)?.let { throw ApiException(GeneralExceptionEnum.USERNAME_ALREADY_EXISTS) }
        val insertedUser = repository.save(User(
            password = request.password,
            username = request.username,
            email = request.email,
            phoneNumber = request.phoneNumber,
            fullName = request.fullName,
            roleId = RoleEnum.USER.id
        ))

        // authenticate user
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.username, request.password))
        val token = jwtUtils.generateToken(auth)

        // generate response
        return UserRegisterResponse(
            id = insertedUser.id,
            roleId = insertedUser.roleId,
            username = insertedUser.username,
            email = insertedUser.email,
            emailVerified = insertedUser.emailVerified,
            phoneNumber = insertedUser.phoneNumber,
            fullName = insertedUser.fullName,
            token = token
        )
    }

    override fun userCheck(request: UserExistsRequest?): UserExistsResponse? {
        paramNotNull(request, "request")
        paramNotEitherBlank(request?.username, "username", request?.email, "email")

        request!!
        val user = repository.findByUsernameOrEmail(request.username, request.email)
        return UserExistsResponse(
            exists = user != null
        )
    }
}