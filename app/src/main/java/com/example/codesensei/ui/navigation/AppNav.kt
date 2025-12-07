package com.example.codesensei.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.codesensei.AnalyzerViewModel
import com.example.codesensei.HistoryScreen
import com.example.codesensei.HomeScreen
import com.example.codesensei.ResultsScreen
import com.example.codesensei.SettingsScreen
import com.example.codesensei.data.local.AnalysisSessionEntity
import com.example.codesensei.data.local.CodeSenseiDatabaseProvider
import kotlinx.coroutines.launch

/**
 * Navigation route constants.
 */
object Routes {
    const val HOME = "home"
    const val RESULTS = "results"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
}

/**
 * Root navigation graph for Code Sensei.
 *
 * @param vm Shared ViewModel for code and results.
 * @param isDarkTheme Current theme flag.
 * @param onToggleDarkTheme Callback to change theme preference.
 */
@Composable
fun AppNav(
    vm: AnalyzerViewModel,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    val context = LocalContext.current
    val db = remember { CodeSenseiDatabaseProvider.getInstance(context) }
    val dao = remember { db.analysisSessionDao() }
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                code = vm.code,
                onCodeChange = { vm.code = it },
                onAnalyze = {
                    vm.analyze()

                    scope.launch {
                        val text = vm.code.text
                        val results = vm.results

                        val session = AnalysisSessionEntity(
                            timestampMillis = System.currentTimeMillis(),
                            codeLength = text.length,
                            errorCount = results.count { it.isIssue },
                            mainSummary = when {
                                results.isEmpty() -> "No analysis"
                                else -> results.first().title
                            }
                        )
                        dao.insertSession(session)
                    }

                    navController.navigate(Routes.RESULTS)
                },
                onHistoryClick = {
                    navController.navigate(Routes.HISTORY)
                },
                onSettingsClick = {
                    navController.navigate(Routes.SETTINGS)
                }
            )
        }

        composable(Routes.RESULTS) {
            ResultsScreen(
                results = vm.results,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HISTORY) {
            HistoryScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onThemeChange = onToggleDarkTheme,
                onBack = { navController.popBackStack() }
            )
        }
    }
}