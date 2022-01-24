package id.superautocash.mobile.api.controller.model

import id.superautocash.mobile.api.entity.Menu
import java.sql.Timestamp

data class Menu(
    var id: Int?,
    var merchantId: Int,
    var name: String,
    var imageUrl: String,
    var price: Int,
    var description: String?,
    var createdDate: Timestamp,
    var updatedDate: Timestamp
): BaseModel() {

    override fun toEntity(): Menu {
        return Menu(id, merchantId, imageUrl, name, price, description, createdDate, updatedDate)
    }
}
