package id.superautocash.mobile.api.entity

import com.google.gson.GsonBuilder
import id.superautocash.mobile.api.utils.ObjectUtils
import id.superautocash.mobile.api.utils.serialization.DefaultSerializationStrategy

open class BaseEntity {
    override fun toString(): String {
        return ObjectUtils.toString(this::class.java)
    }

    fun toJson(): String {
        return GsonBuilder()
            .setExclusionStrategies(DefaultSerializationStrategy())
            .create()
            .toJson(this)
    }
}