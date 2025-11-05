package com.example.codesensei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codesensei.ui.theme.CodeSenseiTheme

// App entry point. Sets theme, creates shared ViewModel, shows navigation.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeSenseiTheme {
                val vm: AnalyzerViewModel = viewModel()
                AppNav(vm = vm)
            }
        }
    }
}