package id.superautocash.mobile.api.exception

import id.superautocash.mobile.api.enums.GeneralExceptionEnum

data class ParamIllegalException(
    val paramName: String,
    val paramStatus: String
): RuntimeException() {
    val exception: GeneralExceptionEnum = GeneralExceptionEnum.PARAM_ILLEGAL
}