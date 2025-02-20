package com.sm.android.baseproject.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sm.android.baseproject.R
import com.sm.android.baseproject.databinding.ActivityMainBinding
import com.sm.android.baseproject.db.User
import com.sm.android.baseproject.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val key = "username"
        val value = "JohnDoe"

        val user = User(1, "Jane Doe", "jane@example.com")

        viewModel.saveData(key, value)
        viewModel.loadData(key)

        viewModel.insertUser(user)
        viewModel.fetchUser(1)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.savedData.collect { data ->
                        Toast.makeText(this@MainActivity, "Saved: $data", Toast.LENGTH_SHORT).show()
                    }
                }

                launch {
                    viewModel.user.collect { user ->
                        user?.let {
                            Toast.makeText(this@MainActivity, "User: ${it.name}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}