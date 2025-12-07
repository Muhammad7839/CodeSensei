package com.example.codesensei.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Backing property for preferences DataStore on Context.
private val Context.dataStore by preferencesDataStore(name = "code_sensei_settings")

/**
 * Handles reading and writing of simple settings such as dark theme.
 */
object ThemePreferences {

    private val KEY_DARK_THEME = booleanPreferencesKey("dark_theme_enabled")

    /**
     * Observe whether dark theme is enabled.
     */
    fun isDarkThemeEnabled(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[KEY_DARK_THEME] ?: false
        }
    }

    /**
     * Persist the dark theme flag.
     */
    suspend fun setDarkThemeEnabled(context: Context, enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_DARK_THEME] = enabled
        }
    }
}