package com.example.codesensei

import androidx.compose.foundation.layout.*
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

@OptIn(ExperimentalMaterial3Api::class) // allow TopAppBar without errors
@Composable
fun ResultsScreen(
    results: List<AnalysisItem>,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Results") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(16.dp)) {
            if (results.isEmpty()) {
                Text("No issues found 🎉")
            } else {
                Text("Found ${results.size} issue(s):")
                Spacer(Modifier.height(8.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
}