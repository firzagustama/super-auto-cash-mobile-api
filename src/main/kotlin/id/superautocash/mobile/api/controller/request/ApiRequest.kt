package id.superautocash.mobile.api.controller.request

data class ApiRequest<T>(
    val data: T? = null,
    val extParams: MutableMap<String, Any> = mutableMapOf()
): BaseRequest()
