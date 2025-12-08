package com.example.codesensei

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Detailed view for a single analysis item.
 *
 * Shows:
 * - Issue title
 * - Full explanation
 * - Suggested fix (if any)
 * - A clear, step-by-step checklist for the student to follow.
 *
 */
/**
 * Detailed view for a single analysis item.
 *
 * Shows:
 * - Issue title
 * - Full explanation
 * - Suggested fix (if any)
 * - A clear, step-by-step checklist for the student to follow.
 *
 * @param item The analysis item to show in detail.
 * @param onBack Called when the user taps "Back" in the top bar.
 */
@Composable
fun ErrorDetailScreen(item: AnalysisItem, onBack: () -> Unit) {
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDetailScreen(
    item: AnalysisItem,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Issue Details") },
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
                .fillMaxSize()
        ) {
            // Title
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(12.dp))

            // Explanation
            Text(
                text = "Explanation:",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = item.explanation,
                style = MaterialTheme.typography.bodyMedium
            )

            // Suggested fix if present
            if (item.fix.isNotBlank()) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Suggested fix:",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = item.fix,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Checklist
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Checklist to follow:",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))

            Text(
                text = "1. Locate the part of your code that matches this issue.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "2. Compare your code to the explanation and suggested fix above.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "3. Edit your code to remove the typo, missing symbol, or wrong structure.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "4. Re-run your program and check if the error message has changed or disappeared.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "5. If new errors appear, repeat this process with the next issue in the list.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
}