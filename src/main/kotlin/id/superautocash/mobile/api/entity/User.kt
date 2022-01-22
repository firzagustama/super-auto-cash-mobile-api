package id.superautocash.mobile.api.entity

import com.google.gson.annotations.Expose
import id.superautocash.mobile.api.utils.annotation.Exclude
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
    var fullName: String
) : BaseEntity()
