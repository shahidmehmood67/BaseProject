package com.sm.android.baseproject.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sm.android.baseproject.R
import com.sm.android.baseproject.databinding.ActivityMainBinding
import com.sm.android.baseproject.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val key = "username"
        val value = "JohnDoe"

        viewModel.saveData(key, value)
        viewModel.loadData(key)

        lifecycleScope.launchWhenStarted {
            viewModel.savedData.collect { data ->
                Toast.makeText(this@MainActivity, "Saved: $data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}