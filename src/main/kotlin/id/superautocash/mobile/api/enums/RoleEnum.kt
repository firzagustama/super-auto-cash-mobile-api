package id.superautocash.mobile.api.enums

enum class RoleEnum(
    val id: Int,
    val roleName: String
) {
    USER(1, "USER"),
    MERCHANT(2, "MERCHANT")
}