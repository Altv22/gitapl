package com.example.gitapl.Theme

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("settings")

class SettingPreferences constructor(context: Context) {

    private val settingsDataStore = context.prefDataStore
    private val themeKEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> =
        settingsDataStore.data.map { preferences ->
            preferences[themeKEY] ?: false
        }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        Log.d("SettingPreferences", "saveThemeSetting: $isDarkModeActive")
        settingsDataStore.edit { preferences ->
            preferences[themeKEY] = isDarkModeActive
        }
    }
}