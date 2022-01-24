package id.superautocash.mobile.api.enums

enum class GeneralExceptionEnum(
    var errorCode: String,
    var errorMessage: String
) {
    /** SERVER EXCEPTION */
    SERVER_ERROR("1001", "Server error, please check API logs"),

    /** CLIENT EXCEPTION */
    PARAM_ILLEGAL("2001", "Param illegal"),
    UNAUTHORIZED("2002", "This user is not authorized"),
    FORBIDDEN("2003", "This user is forbidded to access this api"),

    /** USER EXCEPTION */
    USER_NOT_FOUND("3002", "User not found"),
    PASSWORD_INVALID("3003", "Password invalid"),
    USERNAME_ALREADY_EXISTS("3004", "Username already exists"),

    /** MENU EXCEPTION */
    MENU_NOT_FOUND("4002", "Menu not found")
}