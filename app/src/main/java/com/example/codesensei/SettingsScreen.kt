package com.example.codesensei

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A composable screen that displays various settings for the user.
 * It includes options to change the app's theme and view user progress like points and level.
 *
 * @param isDarkTheme A boolean indicating if the dark theme is currently enabled.
 * @param onThemeChange A callback function that is invoked when the user toggles the theme switch. It provides the new theme state as a boolean.
 * @param points The user's current reward points.
 * @param level The user's current level.
 * @param onBack A callback function to be invoked when the user presses the back button, used for navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    points: Int,
    level: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                "Appearance",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))

            Text("Use dark theme")
            Switch(
                checked = isDarkTheme,
                onCheckedChange = onThemeChange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Text(
                "Your Progress",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))

            Text("Level: $level", style = MaterialTheme.typography.bodyLarge)
            Text("Points: $points", style = MaterialTheme.typography.bodyLarge)
        }
    }
}