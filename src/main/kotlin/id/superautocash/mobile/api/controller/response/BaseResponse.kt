package id.superautocash.mobile.api.controller.response

import id.superautocash.mobile.api.utils.ObjectUtils

open class BaseResponse {
    override fun toString(): String {
        return ObjectUtils.toString(this::class.java)
    }
}