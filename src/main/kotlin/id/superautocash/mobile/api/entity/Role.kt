package id.superautocash.mobile.api.entity

import javax.persistence.*

@Entity(name = "user_role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int,

    @Column(name = "role_name")
    var roleName: String
) : BaseEntity()
