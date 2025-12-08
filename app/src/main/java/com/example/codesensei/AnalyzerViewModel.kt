package com.example.codesensei

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codesensei.data.rewards.RewardDataStore
import kotlinx.coroutines.launch

/**
 * ViewModel for the code analyzer screen.
 *
 * This ViewModel holds the state for the code input field, the analysis results,
 * and handles the logic for analyzing the code and updating rewards.
 *
 * @property rewardStore The data store for managing user rewards.
 */
class AnalyzerViewModel(
    private val rewardStore: RewardDataStore
) : ViewModel() {

    /**
     * The current code text entered by the user, represented as a [TextFieldValue]
     * to support selection and cursor position.
     */
    var code by mutableStateOf(TextFieldValue(""))

    /** The list of analysis items (issues or success messages) generated from the code. */
    var results by mutableStateOf(listOf<AnalysisItem>())

    /**
     * Analyzes the current [code] for common beginner mistakes.
     *
     * It performs a series of simple checks and updates the [results] list.
     * If no issues are found, a success message is shown.
     * A reward point is added each time analysis is run.
     */
    fun analyze() {
        val text = code.text
        val issues = mutableListOf<AnalysisItem>()

        // Simple checks
        if ("pritn" in text) {
            issues += AnalysisItem(
                title = "Possible Typo: 'pritn'",
                explanation = "Looks like you meant 'print'.",
                fix = "Replace 'pritn' with 'print'."
            )
        }

        if (!text.contains("fun main")) {
            issues += AnalysisItem(
                title = "Missing main() function",
                explanation = "Kotlin programs need a entry point.",
                fix = "Add: fun main() { ... }"
            )
        }

        if (text.count { it == '(' } != text.count { it == ')' }) {
            issues += AnalysisItem(
                title = "Unmatched parentheses",
                explanation = "You have a '(' without a matching ')'.",
                fix = "Add or remove parentheses to balance them."
            )
        }

        if (issues.isEmpty() && text.isNotBlank()) {
            issues += AnalysisItem(
                title = "No Issues Found",
                explanation = "No common beginner mistakes detected.",
                fix = "Nice job!",
                isIssue = false
            )
        }

        results = issues

        // Give +1 reward point
        viewModelScope.launch {
            rewardStore.addPoint()
        }
    }
}