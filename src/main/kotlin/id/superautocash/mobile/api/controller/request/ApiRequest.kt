package id.superautocash.mobile.api.controller.request

data class ApiRequest<T>(
    val token: String? = null,
    val data: T? = null
): BaseRequest()
