package com.sm.android.baseproject.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : MainRepository {
    override suspend fun saveData(key: String, value: String) {
        dataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    override fun getData(key: String): Flow<String?> = dataStore.data.map { it[stringPreferencesKey(key)] }
}