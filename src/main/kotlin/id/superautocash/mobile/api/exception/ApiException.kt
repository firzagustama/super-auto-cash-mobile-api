package id.superautocash.mobile.api.exception

import id.superautocash.mobile.api.enums.GeneralExceptionEnum

data class ApiException(val exception: GeneralExceptionEnum): RuntimeException()
