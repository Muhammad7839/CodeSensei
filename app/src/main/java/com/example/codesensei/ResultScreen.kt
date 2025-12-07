package com.example.codesensei

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Shows results from the last analysis.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    results: List<AnalysisItem>,
    onBack: () -> Unit
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

            if (issueCount == 0) {
                Text("No issues found 🎉")
            } else {
                Text("Found $issueCount issue(s):")
            }

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(results) { item ->
                    ElevatedCard {
                        Column(Modifier.padding(16.dp)) {
                            Text(item.title)
                            Spacer(Modifier.height(6.dp))
                            Text(item.explanation)

                            if (item.fix.isNotBlank()) {
                                Spacer(Modifier.height(8.dp))
                                Text("Fix: ${item.fix}")
                            }
                        }
                    }
                }
            }
        }
    }
}