package id.superautocash.mobile.api.controller.response

data class ApiResponse<T>(
    var success: Boolean,
    var errorCode: String? = null,
    var errorMessage: String? = null,
    var data: T?,
    var extParams: MutableMap<String, Any> = mutableMapOf()
) : BaseResponse()
