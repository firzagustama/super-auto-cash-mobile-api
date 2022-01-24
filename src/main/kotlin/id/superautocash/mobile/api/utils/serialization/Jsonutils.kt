package id.superautocash.mobile.api.utils.serialization

import com.google.gson.GsonBuilder

fun toJson(clazz: Any): String {
    return GsonBuilder()
        .setExclusionStrategies(DefaultSerializationStrategy())
        .create()
        .toJson(clazz)
}