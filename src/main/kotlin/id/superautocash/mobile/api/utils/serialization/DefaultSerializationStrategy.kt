package id.superautocash.mobile.api.utils.serialization

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import id.superautocash.mobile.api.utils.annotation.Exclude

class DefaultSerializationStrategy: ExclusionStrategy {
    override fun shouldSkipField(field: FieldAttributes?): Boolean {
        return field?.getAnnotation(Exclude::class.java) != null
    }

    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return false
    }
}