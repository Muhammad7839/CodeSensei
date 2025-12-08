package com.example.codesensei

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ElevatedCard
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
 * Shows results from the last analysis.
 *
 * Each issue card includes:
 * - A short title.
 * - A simple explanation.
 * - An optional suggested fix.
 * - A mini checklist of steps to follow.
 *
 * Tapping a card opens a full Error Detail screen.
 *
 * @param results The list of [AnalysisItem]s to display.
 * @param onBack Callback invoked when the user wants to navigate back.
 * @param onIssueClick Callback invoked when a user clicks on an issue card, passing the item's index.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    results: List<AnalysisItem>,
    onBack: () -> Unit,
    onIssueClick: (Int) -> Unit
) {
    val issueCount = results.count { it.isIssue }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analysis Results") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (results.isEmpty()) {
                Text("No code to analyze yet.")
                return@Column
            }

            Text(
                text = if (issueCount == 0) "No issues found 🎉" else "Found $issueCount issue(s):",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(results) { index, item ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onIssueClick(index) }
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            // Title of the issue
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(6.dp))

                            // Explanation in simple language
                            Text(
                                text = item.explanation,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            // Suggested fix text, if any
                            if (item.fix.isNotBlank()) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = "Suggested fix:",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = item.fix,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            // Simple checklist to guide the student step by step
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "Checklist:",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = "• Re-read the explanation above slowly.",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "• Find the matching line or section in your code.",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "• Apply the suggested fix (or adjust the code using the hint).",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "• Run your code again to see if this issue is gone.",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}