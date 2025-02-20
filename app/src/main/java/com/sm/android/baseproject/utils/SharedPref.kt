package com.sm.android.baseproject.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    const val PREF_KEY = "PREF_KEY_APP_NAME"

    const val SELECTED_LANG_CODE = "SELECTED_LANG_CODE"
    const val isLanguageFinished = "isLanguageFinished"

    fun Context.putPref(name: String, data: Any) {
        val sharedPreference = getPref()
        val editor = sharedPreference.edit()

        when (data) {
            is String -> editor.putString(name, data)
            is Int -> editor.putInt(name, data)
            is Float -> editor.putFloat(name, data)
            is Boolean -> editor.putBoolean(name, data)
        }

        editor.apply()
    }

    fun Context.getPref(): SharedPreferences {
        return getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    }

    fun Context.getStringPref(name: String, defaultValue: String = ""): String =
        getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).getString(name, defaultValue)
            ?: ""

    fun Context.getIntPref(name: String, defaultValue: Int = 0): Int =
        getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).getInt(name, defaultValue)

    fun Context.getFloatPref(name: String, defaultValue: Float = 0f): Float =
        getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).getFloat(name, defaultValue)

    fun Context.getBooleanPref(name: String, defaultValue: Boolean = false): Boolean =
        getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
            .getBoolean(name, defaultValue)

    fun Context.removePrefs() {
        val sharedPreference = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
    }


}