package com.example.codesensei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codesensei.data.settings.ThemePreferences
import com.example.codesensei.ui.navigation.AppNav
import com.example.codesensei.ui.theme.CodeSenseiTheme
import kotlinx.coroutines.launch

/**
 * Main entry point for Code Sensei.
 *
 * - Applies global theme (light/dark from DataStore).
 * - Provides the shared [AnalyzerViewModel].
 * - Hosts the navigation graph.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            // Observe dark-theme flag from DataStore.
            val isDarkTheme: Boolean by ThemePreferences
                .isDarkThemeEnabled(context)
                .collectAsState(initial = false)

            // Shared ViewModel.
            val vm: AnalyzerViewModel = viewModel()

            CodeSenseiTheme(darkTheme = isDarkTheme) {
                AppNav(
                    vm = vm,
                    isDarkTheme = isDarkTheme,
                    onToggleDarkTheme = { enabled ->
                        scope.launch {
                            ThemePreferences.setDarkThemeEnabled(context, enabled)
                        }
                    }
                )
            }
        }
    }
}