package com.example.codesensei

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/**
 * HomeScreen
 * - Shows a large text box for the user to paste code
 * - Has an "Analyze" button that triggers the analysis
 */
@OptIn(ExperimentalMaterial3Api::class) // TopAppBar is marked experimental in some M3 versions
@Composable
fun HomeScreen(
    code: TextFieldValue,                        // current code from ViewModel
    onCodeChange: (TextFieldValue) -> Unit,      // updates code in ViewModel
    onAnalyze: () -> Unit                        // runs analysis (defined in ViewModel)
) {
    // Scroll state for the large code text field
    val scroll = rememberScrollState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Code Sensei") }) }
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)       // respect the scaffold's padding (status bar, etc.)
                .padding(16.dp)     // consistent screen padding
                .fillMaxSize()
        ) {
            // Simple instruction label
            Text("Paste your code below:")
            Spacer(Modifier.height(8.dp))

            // Big text area for code input
            OutlinedTextField(
                value = code,
                onValueChange = onCodeChange,     // update code as user types
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)                   // take remaining vertical space
                    .verticalScroll(scroll),      // allow scrolling within the field
                placeholder = { Text("fun main() { pritn(\"Hello\") }") },
                minLines = 10                     // make it tall enough for code
            )

            Spacer(Modifier.height(16.dp))

            // Analyze button triggers the ViewModel's analyze() through onAnalyze()
            Button(
                onClick = onAnalyze,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Analyze")
            }
        }
    }
}