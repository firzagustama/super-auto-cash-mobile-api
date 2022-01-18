package id.superautocash.mobile.api.controller.request

data class UserRegisterRequest(
    val password: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val fullName: String
): BaseRequest()
