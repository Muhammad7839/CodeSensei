package com.example.codesensei

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.codesensei.data.local.AnalysisSessionEntity
import com.example.codesensei.data.local.CodeSenseiDatabaseProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A composable screen that displays a history of past code analysis sessions.
 * The sessions are retrieved from a local Room database and displayed in a list.
 *
 * @param onBack A lambda function to be invoked when the user wants to navigate back.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val dao = CodeSenseiDatabaseProvider.getInstance(context).analysisSessionDao()
    val sessions: List<AnalysisSessionEntity> by dao.getAllSessions().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { paddingValues ->

        // Display a message if the history is empty
        if (sessions.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text("No past analyses yet.")
            }
            // Early return to prevent rendering the LazyColumn
            return@Scaffold
        }

        // Display the list of past analysis sessions
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sessions) { session ->
                // Card for each session entry
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            text = session.mainSummary, // The main summary of the analysis
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(4.dp))

                        Text("Code length: ${session.codeLength}")
                        Text("Issues found: ${session.errorCount}")

                        Spacer(Modifier.height(4.dp))

                        // Format the timestamp for display
                        val formattedTime = SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss",
                            Locale.getDefault()
                        ).format(Date(session.timestampMillis))

                        Text(
                            // Display the formatted time
                            text = "Time: $formattedTime",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}