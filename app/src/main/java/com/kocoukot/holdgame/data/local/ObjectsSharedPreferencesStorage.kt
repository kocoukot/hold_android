package com.kocoukot.holdgame.data.local

import com.google.gson.Gson
import kotlin.reflect.KClass

class ObjectsSharedPreferencesStorage(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferencesStorage,
) {

    operator fun set(key: String, value: Any?) {
        sharedPreferences[key] = gson.toJson(value)
    }

    operator fun <T : Any> get(key: String, type: KClass<T>): T? {
        val jsonObject: String? = sharedPreferences[key]
        return jsonObject?.let { gson.fromJson(it, type.java) }
    }
}
