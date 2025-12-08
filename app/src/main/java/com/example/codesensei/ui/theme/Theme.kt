package com.example.codesensei.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Dark color scheme for the Code Sensei app.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = DarkBackground,
    surface = DarkSurface
)

/**
 * Light color scheme for the Code Sensei app.
 */
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = LightBackground,
    surface = LightSurface
)

/**
 * Composable function that applies the Code Sensei app theme to its content.
 *
 * This theme wrapper sets up the [MaterialTheme] with the appropriate color scheme
 * (either light or dark) and typography.
 *
 * @param darkTheme Whether to force dark theme (overrides system by DataStore).
 * @param content Screen content.
 */
@Composable
fun CodeSenseiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}