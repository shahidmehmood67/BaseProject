package com.sm.android.baseproject.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.io.Serializable

object ActivityUtils {

    /**
     * Returns Color from resource.
     * @param id Color Resource ID
     */
    fun Activity.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(applicationContext, id)

    inline fun <reified T : Activity> Activity.startKtxActivity(
        vararg values: Pair<String, Any>,
        flag: Int? = null,
        extra: Bundle? = null,
        finish: Boolean = false
    ) {
        startActivity(getIntent<T>(flag, extra, *values))
        if (finish) finish()
    }


    inline fun <reified T : Activity> Activity.startKtxActivity(
        vararg values: Pair<String, Any>,
        flag: Int? = null,
        extra: Bundle? = null
    ) = startActivity(getIntent<T>(flag, extra, *values))

    inline fun <reified T : Context> Context.getIntent(
        flag: Int? = null,
        extra: Bundle? = null,
        vararg pairs: Pair<String, Any>
    ): Intent =
        Intent(this, T::class.java).apply {
            flag?.let { setFlags(flags) }
            extra?.let { putExtras(extra) }
            pairs.forEach { pair ->
                val name = pair.first
                when (val value = pair.second) {
                    is Int -> putExtra(name, value)
                    is Byte -> putExtra(name, value)
                    is Char -> putExtra(name, value)
                    is Short -> putExtra(name, value)
                    is Boolean -> putExtra(name, value)
                    is Long -> putExtra(name, value)
                    is Float -> putExtra(name, value)
                    is Double -> putExtra(name, value)
                    is String -> putExtra(name, value)
                    is CharSequence -> putExtra(name, value)
                    is Parcelable -> putExtra(name, value)
                    is Array<*> -> putExtra(name, value)
                    is ArrayList<*> -> putExtra(name, value)
                    is Serializable -> putExtra(name, value)
                    is BooleanArray -> putExtra(name, value)
                    is ByteArray -> putExtra(name, value)
                    is ShortArray -> putExtra(name, value)
                    is CharArray -> putExtra(name, value)
                    is IntArray -> putExtra(name, value)
                    is LongArray -> putExtra(name, value)
                    is FloatArray -> putExtra(name, value)
                    is DoubleArray -> putExtra(name, value)
                    is Bundle -> putExtra(name, value)
                    is Intent -> putExtra(name, value)
                    else -> {
                    }
                }
            }
        }


    /**
     * Provides [ViewModel] of type [VM] from [factory].
     */
    inline fun <reified VM : ViewModel> AppCompatActivity.viewModelOf(
        factory: ViewModelProvider.Factory
    ) = ViewModelProvider(this, factory).get(VM::class.java)
}