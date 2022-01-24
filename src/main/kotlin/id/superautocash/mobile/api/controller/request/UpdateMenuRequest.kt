package id.superautocash.mobile.api.controller.request

data class UpdateMenuRequest(
    val id: Int?,
    val name: String?,
    val imageUrl: String?,
    val price: Int?,
    val description: String?,
): BaseRequest()