package com.sm.android.baseproject.model

import androidx.annotation.DrawableRes

data class LanguageItem(
    val title: String,
    val language : String,
    val languageCode : String,
    @DrawableRes val icon : Int,
    var isSelected: Boolean = false
)