package com.sm.android.baseproject.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.sm.android.baseproject.repository.MainRepository
import com.sm.android.baseproject.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) : DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("ds_base_settings") }

    @Provides
    @Singleton
    fun provideRepository(dataStore: DataStore<Preferences>) : MainRepository = MainRepositoryImpl(dataStore)

}