package com.sm.android.baseproject.utils

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun AppCompatActivity.onBackPressedDispatcher(callback: () -> Unit) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            callback.invoke()
        }
    })
}

fun View.gone() = run { visibility = View.GONE }

fun View.visible() = run { visibility = View.VISIBLE }

fun View.invisible() = run { visibility = View.INVISIBLE }


fun View.animate1() {
    animate().apply {
        scaleX(1.1f)
        scaleY(1.1f)
        duration = 100
    }.withEndAction {
        animate().apply {
            scaleX(1.0f)
            scaleY(1.0f)
            duration = 100
        }
    }
}

fun View.animate2() {
    animate().apply {
        scaleX(1.1f)
        scaleY(1.1f)
        duration = 100
    }.withEndAction {
        animate().apply {
            scaleX(1.0f)
            scaleY(1.0f)
            duration = 100
        }
    }
}



infix fun View.visibleIf(condition: Boolean) =
    run { visibility = if (condition) View.VISIBLE else View.GONE }

infix fun View.goneIf(condition: Boolean) =
    run { visibility = if (condition) View.GONE else View.VISIBLE }

infix fun View.invisibleIf(condition: Boolean) =
    run { visibility = if (condition) View.INVISIBLE else View.VISIBLE }