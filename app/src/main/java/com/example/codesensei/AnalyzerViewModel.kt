package com.example.codesensei

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

// Holds the code input and analysis results for the whole app.
class AnalyzerViewModel : ViewModel() {

    // The user's code from the text box (keeps text + cursor info).
    var code by mutableStateOf(TextFieldValue(""))

    // The list of detected issues/explanations for the Results screen.
    var results by mutableStateOf(listOf<AnalysisItem>())

    // Simple rule-based checks for common beginner mistakes.
    fun analyze() {
        val text = code.text
        val issues = mutableListOf<AnalysisItem>()

        // Check 1: common typo "pritn" instead of "print".
        if ("pritn" in text) {
            issues += AnalysisItem(
                title = "Possible Typo: 'pritn'",
                explanation = "Looks like you meant 'print'. Typos cause 'unresolved reference' errors.",
                fix = "Replace 'pritn' with 'print'."
            )
        }

        // Check 2: warn if there's no main() function.
        if (!text.contains("fun main")) {
            issues += AnalysisItem(
                title = "Missing main() Function",
                explanation = "Kotlin programs usually start from a 'fun main()' function.",
                fix = "Add 'fun main() { ... }' and put your code inside."
            )
        }

        // Check 3: unmatched parentheses (basic count).
        if (text.count { it == '(' } != text.count { it == ')' }) {
            issues += AnalysisItem(
                title = "Unmatched Parentheses",
                explanation = "There are more '(' than ')' or vice versa.",
                fix = "Make sure each '(' has a matching ')'."
            )
        }

        // If no issues found, show a friendly success message.
        if (issues.isEmpty() && text.isNotBlank()) {
            issues += AnalysisItem(
                title = "No Issues Found",
                explanation = "No common beginner mistakes detected by these simple rules.",
                fix = "Nice work!"
            )
        }

        results = issues
    }
}