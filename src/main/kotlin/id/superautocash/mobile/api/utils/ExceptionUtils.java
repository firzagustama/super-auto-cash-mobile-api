package id.superautocash.mobile.api.utils;

import id.superautocash.mobile.api.enums.GeneralExceptionEnum;
import id.superautocash.mobile.api.exception.ApiException;
import id.superautocash.mobile.api.exception.ParamIllegalException;

public class ExceptionUtils {
    public static void throwException(GeneralExceptionEnum e) {
        throw new ApiException(e);
    }

    public static void paramNotNull(Object value, String paramName) {
        if (value == null) {
            throw new ParamIllegalException(paramName, "can't be null");
        }
    }

    public static void paramNotNullOrBlank(String value, String paramName) {
        if (value == null || value.isEmpty()) {
            throw new ParamIllegalException(paramName, "can't be null or blank");
        }
    }
}
