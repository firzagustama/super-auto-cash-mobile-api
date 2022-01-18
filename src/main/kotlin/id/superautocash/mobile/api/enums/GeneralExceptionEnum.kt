package id.superautocash.mobile.api.enums

enum class GeneralExceptionEnum(
    var errorCode: String,
    var errorMessage: String
) {
    /** SERVER EXCEPTION */
    SERVER_ERROR("1001", "Server error, please check API logs"),

    /** CLIENT EXCEPTION */
    PARAM_ILLEGAL("2001", "Param illegal"),

    /** USER EXCEPTION */
    USER_NOT_FOUND("2002", "User not found"),
    PASSWORD_INVALID("2003", "Password invalid")
}