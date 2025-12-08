package com.example.codesensei

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/**
 * Home screen for Code Sensei.
 *
 * - Full-screen background image (light / dark).
 * - Glass title card with "Code Sensei".
 * - Frosted-glass panel for the code editor.
 * - Import-from-file button to load code.
 * - Dark glass strip at the bottom for actions so they are always readable.
 */
@OptIn(ExperimentalMaterial3Api::class)
/**
 * The main screen of the application where users can input code for analysis.
 * This composable function builds the UI for the home screen, including a text editor for code,
 * buttons for analyzing code, importing from a file, viewing history, and accessing settings.
 * It adapts its appearance based on the current theme (light/dark).
 *
 * @param code The current value of the code editor text field.
 * @param onCodeChange A callback that is triggered when the code in the text field changes.
 * @param onAnalyze A callback to be invoked when the 'Analyze Code' button is clicked.
 * @param onHistoryClick A callback to be invoked when the 'View History' button is clicked.
 * @param onSettingsClick A callback to be invoked when the 'Settings' button is clicked.
 */
@Composable
fun HomeScreen(
    code: TextFieldValue,
    onCodeChange: (TextFieldValue) -> Unit,
    onAnalyze: () -> Unit,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val text = try {
                context.contentResolver.openInputStream(uri)?.bufferedReader()
                    .use { it?.readText() }
            } catch (_: Exception) {
                null
            }

            if (!text.isNullOrBlank()) {
                onCodeChange(TextFieldValue(text))
            }
        }
    }

    // Detect current theme
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f

    // Background image for light / dark theme
    val bgPainter = painterResource(
        if (isDarkTheme) R.drawable.bg_dark else R.drawable.bg_light
    )
    val bgAlpha = if (isDarkTheme) 0.35f else 1f

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Wallpaper
            Image(
                painter = bgPainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = bgAlpha
            )

            // Foreground content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Glass title card with "Code Sensei"
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = if (isDarkTheme)
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.30f)
                    else
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.20f),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                    ),
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = "Code Sensei",
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = if (isDarkTheme) Color.White else Color.Black
                        )
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Paste your Kotlin code below, or import it from a file, then let Code Sensei analyze it.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(12.dp))

                // Frosted-glass panel behind the code editor
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = if (isDarkTheme)
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.35f)
                    else
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.65f),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    tonalElevation = 6.dp,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    OutlinedTextField(
                        value = code,
                        onValueChange = onCodeChange,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(8.dp),
                        placeholder = {
                            Text(
                                "fun main() {\n" +
                                        "    println(\"Hello\")\n" +
                                        "}"
                            )
                        },
                        minLines = 10
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Bottom dark glass strip for actions (keeps text 100% visible)
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = if (isDarkTheme) {
                        Color.Black.copy(alpha = 0.7f)
                    } else {
                        Color.Black.copy(alpha = 0.55f)
                    },
                    tonalElevation = 8.dp,
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Button(
                            onClick = onAnalyze,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Analyze Code")
                        }

                        Spacer(Modifier.height(8.dp))

                        TextButton(
                            onClick = { filePickerLauncher.launch("text/*") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Import code from file",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            )
                        }

                        Spacer(Modifier.height(4.dp))

                        TextButton(
                            onClick = onHistoryClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "View History",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }

                        TextButton(
                            onClick = onSettingsClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Settings",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}