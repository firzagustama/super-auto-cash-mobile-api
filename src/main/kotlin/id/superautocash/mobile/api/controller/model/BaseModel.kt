package id.superautocash.mobile.api.controller.model

import id.superautocash.mobile.api.entity.BaseEntity
import id.superautocash.mobile.api.utils.ObjectUtils

abstract class BaseModel {

    abstract fun toEntity(): BaseEntity?

    override fun toString(): String {
        return ObjectUtils.toString(this::class.java)
    }

    fun toJson(clazz: Any): String {
        return id.superautocash.mobile.api.utils.serialization.toJson(clazz)
    }
}