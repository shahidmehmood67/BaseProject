package com.sm.android.baseproject.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun Context.getResString(resource: Int) = resources.getString(resource)

fun Context.getDrawable(resource: Int) = getDrawable(resource)

fun Context.getDrawableCompat(resource: Int) = ContextCompat.getDrawable(this, resource)

fun Context.getDimen(resource: Int) = resources.getDimension(resource)

fun Context.getColor(resource: Int) = ResourcesCompat.getColor(resources, resource, null)

