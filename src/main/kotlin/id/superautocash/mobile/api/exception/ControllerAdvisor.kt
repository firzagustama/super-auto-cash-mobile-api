package id.superautocash.mobile.api.exception

import id.superautocash.mobile.api.controller.response.ApiResponse
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ControllerAdvisor: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ParamIllegalException::class])
    fun handleException(e: ParamIllegalException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse(
            success = false,
            errorCode = e.exception.errorCode,
            errorMessage = "${e.paramName} ${e.paramStatus}",
            data = null
        )
        return ResponseEntity(response, HttpStatus.OK)
    }

    @ExceptionHandler(value = [ApiException::class])
    fun handleException(e: ApiException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse(
            success = false,
            errorCode = e.exception.errorCode,
            errorMessage = e.exception.errorMessage,
            data = null
        )
        return ResponseEntity(response, HttpStatus.OK)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: RuntimeException): ResponseEntity<ApiResponse<Nothing>> {
        e.printStackTrace()
        return handleException(ApiException(GeneralExceptionEnum.SERVER_ERROR))
    }

    @ExceptionHandler(value = [AuthenticationException::class])
    fun handleException(e: AuthenticationException): ResponseEntity<ApiResponse<Nothing>> {
        return when (e::class) {
            InternalAuthenticationServiceException::class -> handleException(ApiException(GeneralExceptionEnum.USER_NOT_FOUND))
            BadCredentialsException::class -> handleException(ApiException(GeneralExceptionEnum.PASSWORD_INVALID))
            else -> handleException(e as RuntimeException)
        }
    }

}