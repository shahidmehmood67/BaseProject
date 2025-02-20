package com.sm.android.baseproject.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sm.android.baseproject.R
import com.sm.android.baseproject.databinding.ActivityLanguageBinding
import com.sm.android.baseproject.databinding.ActivityMainBinding
import com.sm.android.baseproject.utils.ActivityUtils.startKtxActivity
import com.sm.android.baseproject.utils.withDelay

class SplashActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        withDelay(1000) {
            startKtxActivity<LanguageActivity>(finish = true)
        }

    }
}