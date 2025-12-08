package com.example.codesensei.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.codesensei.AnalyzerViewModel
import com.example.codesensei.ErrorDetailScreen
import com.example.codesensei.HistoryScreen
import com.example.codesensei.HomeScreen
import com.example.codesensei.ResultsScreen
import com.example.codesensei.SettingsScreen
import com.example.codesensei.data.local.AnalysisSessionEntity
import com.example.codesensei.data.local.CodeSenseiDatabaseProvider
import com.example.codesensei.data.rewards.RewardDataStore
import com.example.codesensei.data.rewards.calculateLevel
import kotlinx.coroutines.launch

/**
 * Navigation route constants.
 */
object Routes {
    /** Route for the home screen where users input code. */
    const val HOME = "home"

    /** Route for the results screen displaying analysis feedback. */
    const val RESULTS = "results"

    /** Route for the history screen showing past analysis sessions. */
    const val HISTORY = "history"

    /** Route for the settings screen. */
    const val SETTINGS = "settings"

    /**
     * Route for the error detail screen. Requires an `index` argument to identify the specific error.
     * Example: `error_detail/0`
     */
    const val ERROR_DETAIL = "error_detail"
}

/**
 * Root navigation graph for Code Sensei.
 *
 * This composable sets up the [NavHost] and defines all the possible navigation destinations
 * within the application. It handles navigation logic between screens like [HomeScreen],
 * [ResultsScreen], [HistoryScreen], and [SettingsScreen].
 *
 * @param vm The shared [AnalyzerViewModel] that holds the state for the code analysis.
 * @param isDarkTheme A boolean flag indicating if the dark theme is currently active.
 * @param onToggleDarkTheme A callback function to toggle the dark theme on or off.
 * @param rewardStore The [RewardDataStore] instance for accessing user points and level data.
 */
@Composable
fun AppNav(
    vm: AnalyzerViewModel,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit,
    rewardStore: RewardDataStore
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val db = remember { CodeSenseiDatabaseProvider.getInstance(context) }
    val dao = remember { db.analysisSessionDao() }
    val scope = rememberCoroutineScope()

    // Observe points and compute level for Settings screen.
    val points = rewardStore.pointsFlow.collectAsState(initial = 0).value
    val level = calculateLevel(points)

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                code = vm.code,
                onCodeChange = { vm.code = it },
                onAnalyze = {
                    // Trigger analysis in the ViewModel.
                    vm.analyze()

                    scope.launch {
                        val text = vm.code.text
                        val results = vm.results

                        val session = AnalysisSessionEntity(
                            timestampMillis = System.currentTimeMillis(),
                            codeLength = text.length,
                            errorCount = results.count { it.isIssue },
                            mainSummary = results.firstOrNull()?.title ?: "No analysis"
                        )
                        // Save the analysis session to the local database.
                        dao.insertSession(session)
                    }

                    navController.navigate(Routes.RESULTS)
                },
                onHistoryClick = { navController.navigate(Routes.HISTORY) },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) }
            )
        }

        composable(Routes.RESULTS) {
            ResultsScreen(
                results = vm.results,
                onBack = { navController.popBackStack() },
                onIssueClick = { index ->
                    navController.navigate("${Routes.ERROR_DETAIL}/$index")
                }
            )
        }

        composable(
            // The route for the error detail screen includes a mandatory "index" argument.
            route = "${Routes.ERROR_DETAIL}/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // Retrieve the index from the navigation arguments.
            val index = backStackEntry.arguments?.getInt("index") ?: -1
            val item = vm.results.getOrNull(index)

            // If the item at the index is not found (e.g., invalid index), pop back.
            if (item == null) {
                navController.popBackStack()
            } else {
                ErrorDetailScreen(
                    item = item,
                    onBack = { navController.popBackStack() }
                )
            }
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
                points = points,
                level = level,
                onBack = { navController.popBackStack() }
            )
        }
    }
}