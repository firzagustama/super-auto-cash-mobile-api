package id.superautocash.mobile.api.entity

import id.superautocash.mobile.api.utils.annotation.Exclude
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Int? = null,

    @Exclude
    @Column(name = "password")
    var password: String,

    @Column(name = "roleId")
    var roleId: Int,

    @Column(name = "username")
    var username: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "email_verified")
    var emailVerified: Boolean? = false,

    @Column(name = "phone_number")
    var phoneNumber: String,

    @Column(name = "full_name")
    var fullName: String,

    @Column(name = "address")
    var address: String? = "",

    @Column(name = "registered_date")
    var registeredDate: Timestamp = Timestamp(Date().time),

    @Column(name = "subscription_id")
    var subscriptionId: Int? = null,

    @Column(name = "subscription_date")
    var subscriptionDate: Timestamp? = null,

    @Column(name = "subscription_expiry_date")
    var subscriptionExpiryDate: Timestamp? = null
) : BaseEntity()
