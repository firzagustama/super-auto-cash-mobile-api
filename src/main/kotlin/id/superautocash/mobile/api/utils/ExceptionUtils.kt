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

fun paramIsTrue(value: Boolean, paramName: String, message: String) {
    if (!value) {
        throw ParamIllegalException(paramName, message)
    }
}

fun paramNotNullOrBlank(value: String?, paramName: String) {
    if (value == null || value.isBlank()) {
        throw ParamIllegalException(paramName, "can't be null or blank")
    }
}

fun paramNotEitherBlank(value1: String?, paramName1: String, value2: String?, paramName2: String) {
    if ((value1 == null || value1.isBlank()) && (value2 == null || value2.isBlank())) {
        throw ParamIllegalException("$paramName1 and $paramName2", "must either not null or blank")
    }
}