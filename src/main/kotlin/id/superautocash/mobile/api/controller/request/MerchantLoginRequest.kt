package id.superautocash.mobile.api.controller.request

data class MerchantLoginRequest(
    val username: String?,
    val email: String?,
    val password: String
): BaseRequest()
