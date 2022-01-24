package id.superautocash.mobile.api.controller.request

import id.superautocash.mobile.api.entity.Menu

data class CreateMenuRequest(
    val name: String?,
    val imageUrl: String?,
    val price: Int?,
    val description: String? = null
): BaseRequest() {

    fun toEntity(merchantId: Int): Menu {
        return Menu(
            merchantId = merchantId,
            name = name!!,
            imageUrl = imageUrl!!,
            price = price!!,
            description = description
        )
    }

}