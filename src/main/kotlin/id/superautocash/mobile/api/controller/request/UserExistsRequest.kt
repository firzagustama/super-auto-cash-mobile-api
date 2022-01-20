package id.superautocash.mobile.api.controller.request

data class UserExistsRequest(
    var username: String? = null,
    var email: String? = null
): BaseRequest()