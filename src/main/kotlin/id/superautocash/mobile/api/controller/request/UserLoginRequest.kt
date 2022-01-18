package id.superautocash.mobile.api.controller.request

data class UserLoginRequest(
    var username: String,
    var password: String
) : BaseRequest()
