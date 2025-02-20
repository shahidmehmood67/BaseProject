package com.sm.android.baseproject.ads

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.sm.android.baseproject.BuildConfig
import com.sm.android.baseproject.ads.RemoteConfigKeys.value_int
import com.sm.android.baseproject.ads.RemoteConfigKeys.value_boolean

object RemoteConfigHelper {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    var isRemoteConfigFetched = false

    private val defaults: Map<String, Any> = mapOf(
        value_int to "15",
        value_boolean to "true"
    )

    init {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(defaults)
    }

    fun fetchRemoteConfig(onComplete: (Boolean, String?) -> Unit) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isRemoteConfigFetched = true
                    onComplete(true, "Fetch and activate successful")
                } else {
                    onComplete(false, task.exception?.message)
                }

            }
            .addOnFailureListener {
                onComplete(false, "Remote Config Failure")
            }

        // Log all default values and remote values
        defaults.forEach { (key, defaultValue) ->
            val fetchedValue = when (defaultValue) {
                is Boolean -> remoteConfig.getBoolean(key)
                is Long -> remoteConfig.getLong(key)
                is Double -> remoteConfig.getDouble(key)
                is String -> remoteConfig.getString(key)
                else -> defaultValue
            }
            Log.d("FetchRemoteConfig", "Key: $key, Default: $defaultValue, Fetched: $fetchedValue")
        }
    }

    fun getBooleanRC(key: String): Boolean {
        return remoteConfig.getBoolean(key)
    }

    fun getBooleanRCDebug(key: String, defaultValue: Boolean = false): Boolean {
        return if (BuildConfig.DEBUG) defaultValue else remoteConfig.getBoolean(key)
    }


    fun getStringRC(key: String): String {
        return remoteConfig.getString(key)
    }

    fun getLongRC(key: String): Long {
        return remoteConfig.getLong(key)
    }

    fun getIntLongRC(key: String): Int {
        return remoteConfig.getLong(key).toInt()
    }
}