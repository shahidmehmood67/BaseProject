package com.sm.android.baseproject.utils.extentions

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.onBackPressedDispatcher(callback: () -> Unit) {
    (activity as? AppCompatActivity)?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            callback.invoke()
        }
    })
}
