package com.sm.android.baseproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.android.baseproject.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    val savedData: MutableStateFlow<String?> = MutableStateFlow(null)

    fun saveData(key: String, value: String) = viewModelScope.launch {
        repository.saveData(key, value)
    }

    fun loadData(key: String) {
        viewModelScope.launch {
            repository.getData(key).collect { savedData.value = it }
        }
    }

}