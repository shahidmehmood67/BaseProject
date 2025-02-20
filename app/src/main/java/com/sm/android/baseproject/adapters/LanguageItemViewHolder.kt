package com.sm.android.baseproject.adapters

import androidx.recyclerview.widget.RecyclerView
import com.sm.android.baseproject.R
import com.sm.android.baseproject.databinding.LanguageItemBinding
import com.sm.android.baseproject.listeners.LanguageItemCallback
import com.sm.android.baseproject.model.LanguageItem
import com.sm.android.baseproject.utils.setSafeOnClickListener

class LanguageItemViewHolder(
    private val binding: LanguageItemBinding,
    private val languageItemCallback: LanguageItemCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LanguageItem) {
        binding.apply {
            ivIcon.setImageResource(item.icon)
            tvTitle.text = item.title
            tvLanguage.text = item.language

            if (item.isSelected) {
                itemView.setBackgroundResource(R.drawable.white_cell_corner_active)
            } else {
                itemView.setBackgroundResource(R.drawable.white_cell_corner_eight)
            }
            itemView.setSafeOnClickListener {
                languageItemCallback.itemClicked(item)
            }
        }
    }
}