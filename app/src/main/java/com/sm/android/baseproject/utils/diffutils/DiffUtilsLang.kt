package com.sm.android.baseproject.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.sm.android.baseproject.model.LanguageItem

object DiffUtilsLang : DiffUtil.ItemCallback<LanguageItem>() {
    override fun areItemsTheSame(oldItem: LanguageItem, newItem: LanguageItem): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: LanguageItem, newItem: LanguageItem): Boolean =
        oldItem == newItem
}