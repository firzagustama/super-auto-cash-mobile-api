package id.superautocash.mobile.api.entity

import id.superautocash.mobile.api.utils.ObjectUtils

open class BaseEntity {
    override fun toString(): String {
        return ObjectUtils.toString(this::class.java)
    }
}