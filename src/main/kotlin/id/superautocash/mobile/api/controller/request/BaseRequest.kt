package id.superautocash.mobile.api.controller.request

import com.google.gson.Gson
import id.superautocash.mobile.api.utils.ObjectUtils

open class BaseRequest {
    override fun toString(): String {
        return ObjectUtils.toString(this::class.java)
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}