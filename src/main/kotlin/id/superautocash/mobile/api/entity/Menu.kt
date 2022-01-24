package id.superautocash.mobile.api.entity

import id.superautocash.mobile.api.controller.model.Menu
import java.sql.Timestamp
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "menu")
data class Menu(
    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: Int? = null,

    @Column(name = "merchant_id")
    var merchantId: Int,

    @Column(name = "name")
    var name: String,

    @Column(name = "image_url")
    var imageUrl: String,

    @Column(name = "price")
    var price: Int,

    @Column(name = "description")
    var description: String? = "",

    @Column(name = "created_date")
    var createdDate: Timestamp = Timestamp(Date().time),

    @Column(name = "updated_date")
    var updatedDate: Timestamp = Timestamp(Date().time)

): BaseEntity() {
    fun toMenuModel(): Menu {
        return Menu(id, merchantId, name, imageUrl, price, description, createdDate, updatedDate)
    }
}
