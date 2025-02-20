package com.sm.android.baseproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sm.android.baseproject.databinding.LanguageItemBinding
import com.sm.android.baseproject.listeners.LanguageItemCallback
import com.sm.android.baseproject.model.LanguageItem
import com.sm.android.baseproject.utils.diffutils.DiffUtilsLang

class LanguagesAdapter(private val itemCallback: LanguageItemCallback) :
    ListAdapter<LanguageItem, LanguageItemViewHolder>(DiffUtilsLang) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageItemViewHolder {
        val binding = LanguageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageItemViewHolder(binding, itemCallback)
    }

    override fun onBindViewHolder(holder: LanguageItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}