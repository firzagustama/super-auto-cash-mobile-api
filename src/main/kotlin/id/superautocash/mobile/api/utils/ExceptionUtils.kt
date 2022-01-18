package id.superautocash.mobile.api.utils

import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import id.superautocash.mobile.api.exception.ApiException
import id.superautocash.mobile.api.exception.ParamIllegalException

fun throwException(e: GeneralExceptionEnum) {
    throw ApiException(e)
}

fun paramNotNull(value: Any?, paramName: String) {
    if (value == null) {
        throw ParamIllegalException(paramName, "can't be null")
    }
}

fun paramNotNullOrBlank(value: String?, paramName: String) {
    if (value == null || value.isBlank()) {
        throw ParamIllegalException(paramName, "can't be null or blank")
    }
}