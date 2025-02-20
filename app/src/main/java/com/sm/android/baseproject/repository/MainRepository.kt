package com.sm.android.baseproject.repository

import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun saveData(key: String, value: String)
    fun getData(key: String): Flow<String?>
}