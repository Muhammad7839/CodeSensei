package com.example.codesensei

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Simple route names
object Routes {
    const val HOME = "home"
    const val RESULTS = "results"
}

// Central navigation (Home -> Results). Everything in one package.
@Composable
fun AppNav(vm: AnalyzerViewModel) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                code = vm.code,
                onCodeChange = { vm.code = it },
                onAnalyze = {
                    vm.analyze()
                    nav.navigate(Routes.RESULTS)
                }
            )
        }
        composable(Routes.RESULTS) {
            ResultsScreen(
                results = vm.results,
                onBack = { nav.popBackStack() }
            )
        }
    }
}