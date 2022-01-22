package id.superautocash.mobile.api.controller.response

data class UserLoginResponse(
    var id: Int?,
    var roleId: Int?,
    var username: String?,
    var email: String?,
    var emailVerified: Boolean,
    var phoneNumber: String?,
    var fullName: String?,
    var token: String? = null
) : BaseResponse()
