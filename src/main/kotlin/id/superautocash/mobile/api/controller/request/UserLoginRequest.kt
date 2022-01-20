package id.superautocash.mobile.api.controller.request

data class UserLoginRequest(
    var username: String? = null,
    var email: String? = null,
    var password: String
) : BaseRequest()
