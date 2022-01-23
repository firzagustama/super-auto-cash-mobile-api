package id.superautocash.mobile.api.service.impl

import id.superautocash.mobile.api.controller.request.MerchantLoginRequest
import id.superautocash.mobile.api.controller.request.MerchantRegisterRequest
import id.superautocash.mobile.api.controller.response.MerchantLoginResponse
import id.superautocash.mobile.api.controller.response.MerchantRegisterResponse
import id.superautocash.mobile.api.entity.User
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import id.superautocash.mobile.api.enums.RoleEnum
import id.superautocash.mobile.api.repository.UserRepository
import id.superautocash.mobile.api.security.entity.UserDetailsSecurity
import id.superautocash.mobile.api.security.jwt.JwtUtils
import id.superautocash.mobile.api.service.MerchantService
import id.superautocash.mobile.api.utils.paramNotEitherBlank
import id.superautocash.mobile.api.utils.paramNotNull
import id.superautocash.mobile.api.utils.paramNotNullOrBlank
import id.superautocash.mobile.api.utils.throwException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class MerchantServiceImpl @Autowired constructor(
    val repository: UserRepository,
    val authenticationManager: AuthenticationManager,
    val jwtUtils: JwtUtils
): MerchantService {

    override fun login(request: MerchantLoginRequest?): MerchantLoginResponse {
        // parameter check
        paramNotNull(request, "request")
        paramNotEitherBlank(request!!.username, "username", request.email, "email")
        paramNotNullOrBlank(request.password, "password")
        // authenticate user
        val username = if (request.username.isNullOrBlank()) request.email else request.username
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, request.password))
        // role check
        val user = (auth.principal as UserDetailsSecurity).user
        if (user.roleId != RoleEnum.MERCHANT.id) throwException(GeneralExceptionEnum.USER_NOT_FOUND)
        // response
        return MerchantLoginResponse(
            token = jwtUtils.generateToken(auth)
        )
    }

    override fun register(request: MerchantRegisterRequest?): MerchantRegisterResponse {
        // parameter check
        with (request) {
            paramNotNull(this, "request")
            this!!
            paramNotNullOrBlank(username, "username")
            paramNotNullOrBlank(password, "password")
            paramNotNullOrBlank(email, "email")
            paramNotNullOrBlank(phoneNumber, "phoneNumber")
            paramNotNullOrBlank(fullName, "fullName")
        }
        request!!
        // username or email exists
        repository.findByUsernameOrEmail(request.username, request.email)?.let { throwException(GeneralExceptionEnum.USERNAME_ALREADY_EXISTS) }
        // register user
        val user = repository.save(User(
            password = request.password,
            roleId = RoleEnum.MERCHANT.id,
            username = request.username,
            phoneNumber = request.phoneNumber,
            fullName = request.fullName,
            email = request.email
        ))
        // authenticate user and return response
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))
        return MerchantRegisterResponse(
            token = jwtUtils.generateToken(auth)
        )
    }
}