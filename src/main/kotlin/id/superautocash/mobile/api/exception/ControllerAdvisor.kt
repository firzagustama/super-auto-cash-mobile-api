package id.superautocash.mobile.api.exception

import id.superautocash.mobile.api.controller.response.ApiResponse
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [ApiException::class])
    fun handleException(e: ApiException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse(
            success = false,
            errorCode = e.exception.errorCode,
            errorMessage = e.exception.errorMessage,
            data = null
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: RuntimeException) {
        e.printStackTrace()
        throw ApiException(GeneralExceptionEnum.SERVER_ERROR)
    }

}