package com.sm.android.baseproject.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sm.android.baseproject.R
import com.sm.android.baseproject.adapters.LanguagesAdapter
import com.sm.android.baseproject.ads.RemoteConfigHelper
import com.sm.android.baseproject.databinding.ActivityLanguageBinding
import com.sm.android.baseproject.listeners.LanguageItemCallback
import com.sm.android.baseproject.model.LanguageItem
import com.sm.android.baseproject.utils.ActivityUtils.startKtxActivity
import com.sm.android.baseproject.utils.Languages.languages
import com.sm.android.baseproject.utils.RemoteConfig
import com.sm.android.baseproject.utils.SharedPref.SELECTED_LANG_CODE
import com.sm.android.baseproject.utils.SharedPref.getBooleanPref
import com.sm.android.baseproject.utils.SharedPref.getStringPref
import com.sm.android.baseproject.utils.SharedPref.isLanguageFinished
import com.sm.android.baseproject.utils.SharedPref.putPref
import com.sm.android.baseproject.utils.animExpandCollapse
import com.sm.android.baseproject.utils.invisible
import com.sm.android.baseproject.utils.setLocale
import com.sm.android.baseproject.utils.setSafeOnClickListener
import com.sm.android.baseproject.utils.showToast
import com.sm.android.baseproject.utils.visible

class LanguageActivity : AppCompatActivity(), LanguageItemCallback {

    private val binding by lazy { ActivityLanguageBinding.inflate(layoutInflater) }

    private val languagesAdapter = LanguagesAdapter(this)
    private var selectedLang = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale(getStringPref(SELECTED_LANG_CODE, "en"))
        setContentView(binding.root)

        init()
    }

    private fun init(){
        val dataCollected = getBooleanPref(isLanguageFinished)
        binding.apply {

            if (dataCollected) ivbackBtn.visible() else ivbackBtn.invisible()

            ivbackBtn.setSafeOnClickListener {
                it.animExpandCollapse{ onBackPressed() }
            }

            recyclerView.adapter = languagesAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@LanguageActivity)
            val languageList = languages.map {
                if (it.languageCode ==  getStringPref(SELECTED_LANG_CODE, "en")) {
                    it.copy(isSelected = true)
                } else {
                    it.copy(isSelected = false)
                }
            }

            languagesAdapter.submitList(languages)
            if (dataCollected) {
                languagesAdapter.submitList(languageList)
            } else {
                languagesAdapter.submitList(languages)
            }


            ivTick.setSafeOnClickListener {
                it.animExpandCollapse{
                    if (selectedLang.isEmpty() && !getBooleanPref(isLanguageFinished)) {
                        showToast(getString(R.string.select_a_language))
                        return@animExpandCollapse
                    }

                    if (dataCollected) {
                        putPref(SELECTED_LANG_CODE, selectedLang)
                        setLocale(getStringPref(SELECTED_LANG_CODE, "en"))

                        startKtxActivity<MainActivity>(finish = true)
                    } else {
                        putPref(SELECTED_LANG_CODE, selectedLang)
                        putPref(isLanguageFinished, true)
                        setLocale(getStringPref(SELECTED_LANG_CODE, "en"))

                        startKtxActivity<MainActivity>(finish = true)
                    }
                }
            }
        }
    }

    override fun itemClicked(item: LanguageItem) {
        val list = languages.map {
            if (it.languageCode == item.languageCode) {
                it.copy(isSelected = true)
            } else {
                it.copy(isSelected = false)
            }
        }
        selectedLang = item.languageCode
        languagesAdapter.submitList(list)
    }
}