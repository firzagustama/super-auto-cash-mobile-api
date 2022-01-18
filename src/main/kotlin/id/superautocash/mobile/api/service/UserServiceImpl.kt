package id.superautocash.mobile.api.service

import id.superautocash.mobile.api.controller.request.UserLoginRequest
import id.superautocash.mobile.api.controller.request.UserRegisterRequest
import id.superautocash.mobile.api.controller.response.UserLoginResponse
import id.superautocash.mobile.api.controller.response.UserRegisterResponse
import id.superautocash.mobile.api.entity.User
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import id.superautocash.mobile.api.enums.RoleEnum
import id.superautocash.mobile.api.repository.UserRepository
import id.superautocash.mobile.api.utils.ExceptionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    @Autowired val repository: UserRepository
): UserService {

    override fun login(request: UserLoginRequest?): UserLoginResponse? {
        ExceptionUtils.paramNotNull(request, "request")
        ExceptionUtils.paramNotNullOrBlank(request?.username, "username")
        ExceptionUtils.paramNotNullOrBlank(request?.password, "password")

        request!!
        val user = repository.findByUsername(request.username)
        when {
            user == null -> ExceptionUtils.throwException(GeneralExceptionEnum.USER_NOT_FOUND)
            user.password != request.password -> ExceptionUtils.throwException(GeneralExceptionEnum.PASSWORD_INVALID)
        }

        return UserLoginResponse(
            id = user?.id,
            roleId = user?.roleId,
            username = user?.username,
            email = user?.email,
            emailVerified = user?.emailVerified ?: false,
            phoneNumber = user?.phoneNumber,
            fullName = user?.fullName
        )
    }

    override fun register(request: UserRegisterRequest?): UserRegisterResponse? {
        // request parameter check
        ExceptionUtils.paramNotNullOrBlank(request?.password, "password")
        ExceptionUtils.paramNotNullOrBlank(request?.username, "username")
        ExceptionUtils.paramNotNullOrBlank(request?.email, "email")
        ExceptionUtils.paramNotNullOrBlank(request?.phoneNumber, "phoneNumber")
        ExceptionUtils.paramNotNullOrBlank(request?.fullName, "fullName")

        request!!
        val user = User(
            password = request.password,
            username = request.username,
            email = request.email,
            phoneNumber = request.phoneNumber,
            fullName = request.fullName,
            roleId = RoleEnum.USER.id
        )
        val insertedUser = repository.save(user)
        return UserRegisterResponse(
            id = insertedUser.id,
            roleId = insertedUser.roleId,
            username = insertedUser.username,
            email = insertedUser.email,
            emailVerified = insertedUser.emailVerified,
            phoneNumber = insertedUser.phoneNumber,
            fullName = insertedUser.fullName
        )
    }
}