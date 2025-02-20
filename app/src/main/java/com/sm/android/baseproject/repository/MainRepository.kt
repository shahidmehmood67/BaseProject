package com.sm.android.baseproject.repository

import com.sm.android.baseproject.db.User
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun saveData(key: String, value: String)
    fun getData(key: String): Flow<String?>
    suspend fun insertUser(user: User)
    fun getUser(userId: Int): Flow<User?>
}