package com.example.codesensei

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

/**
 * Shared ViewModel for Code Sensei.
 *
 * Holds the current code input and the list of analysis results so that
 * multiple screens (Home, Results, History) can access the same state.
 */
class AnalyzerViewModel : ViewModel() {

    var code by mutableStateOf(TextFieldValue(""))
    var results by mutableStateOf(listOf<AnalysisItem>())

    /**
     * Run a rule-based analysis on the entered code.
     * These rules catch common beginner mistakes and give readable feedback.
     */
    fun analyze() {
        val text = code.text
        val issues = mutableListOf<AnalysisItem>()

        if (text.isBlank()) {
            results = emptyList()
            return
        }

        // -------------------------------------------------------
        // GLOBAL CHECKS
        // -------------------------------------------------------

        // Typo: "pritn"
        if ("pritn" in text) {
            issues += AnalysisItem(
                title = "Possible Typo: 'pritn'",
                explanation = "It looks like you meant 'print'. Typographical errors cause unresolved reference errors.",
                fix = "Replace 'pritn' with 'print'.",
                isIssue = true
            )
        }

        // Missing main()
        if (!text.contains("fun main")) {
            issues += AnalysisItem(
                title = "Missing main() Function",
                explanation = "A Kotlin console program usually starts with `fun main()`.",
                fix = "Add `fun main() { ... }` to make the program executable.",
                isIssue = true
            )
        }

        // Parentheses count mismatch
        val openParens = text.count { it == '(' }
        val closeParens = text.count { it == ')' }
        if (openParens != closeParens) {
            issues += AnalysisItem(
                title = "Unmatched Parentheses",
                explanation = "There are $openParens '(' but $closeParens ')'.",
                fix = "Make sure every '(' has a matching ')'.",
                isIssue = true
            )
        }

        // Braces count mismatch
        val openBraces = text.count { it == '{' }
        val closeBraces = text.count { it == '}' }
        if (openBraces != closeBraces) {
            issues += AnalysisItem(
                title = "Unmatched Braces",
                explanation = "There are $openBraces '{' but $closeBraces '}'.",
                fix = "Make sure every '{' has a matching '}'.",
                isIssue = true
            )
        }

        // print/println without parentheses
        if ("print " in text && !text.contains("print(")) {
            issues += AnalysisItem(
                title = "Suspicious print usage",
                explanation = "`print` was used without parentheses.",
                fix = "Use print(\"text\") or print(variable).",
                isIssue = true
            )
        }
        if ("println " in text && !text.contains("println(")) {
            issues += AnalysisItem(
                title = "Suspicious println usage",
                explanation = "`println` was used without parentheses.",
                fix = "Use println(\"text\") or println(variable).",
                isIssue = true
            )
        }

        // -------------------------------------------------------
        // LINE-BY-LINE CHECKS
        // -------------------------------------------------------

        val lines = text.lines()

        val bareIdentifierRegex = Regex("""^[A-Za-z_][A-Za-z0-9_]*$""")
        val doubleIdentifierRegex = Regex("""\b[A-Za-z_][A-Za-z0-9_]*\s+[A-Za-z_][A-Za-z0-9_]*\b""")
        val assignmentRegex = Regex("""^([A-Za-z_][A-Za-z0-9_]*)\s*=""")
        val tripleSlashRegex = Regex("""[A-Za-z_][A-Za-z0-9_]*///""")

        var materialThemeWarned = false

        for (raw in lines) {
            val line = raw.trim()
            if (line.isEmpty()) continue

            // Suspicious "///"
            if (tripleSlashRegex.containsMatchIn(line)) {
                issues += AnalysisItem(
                    title = "Suspicious '///' in code",
                    explanation = "Triple slashes likely mean you accidentally commented out part of this line.",
                    fix = "Use '//' for comments; avoid '///' inside code.",
                    isIssue = true
                )
            }

            // Bare identifier → unresolved variable / function
            if (bareIdentifierRegex.matches(line)) {
                issues += AnalysisItem(
                    title = "Unresolved identifier",
                    explanation = "`$line` looks like a variable or function but is not declared.",
                    fix = "Declare it with `val`, `var`, or call it like `$line()`. ",
                    isIssue = true
                )
                continue
            }

            // Two identifiers in a row
            if (doubleIdentifierRegex.containsMatchIn(line)) {
                issues += AnalysisItem(
                    title = "Possible missing '=' or comma",
                    explanation = "Two identifiers appear next to each other (e.g., `content  content`).",
                    fix = "Check that you wrote `name = value` and separated parameters with commas.",
                    isIssue = true
                )
            }

            // Assignment without val/var
            val match = assignmentRegex.find(line)
            if (match != null) {
                val name = match.groupValues[1]
                val declared =
                    text.contains("val $name ") || text.contains("var $name ")

                if (!declared) {
                    issues += AnalysisItem(
                        title = "Assignment without declaration",
                        explanation = "`$name` is assigned a value but never declared.",
                        fix = "Add `val $name = ...` or `var $name = ...` before using it.",
                        isIssue = true
                    )
                }
            }

            // Function declaration missing parentheses (SAFE, NO REGEX)
            // Detects: fun main { ... }
            if (
                line.startsWith("fun ") &&
                line.contains("{") &&
                !line.contains("(")
            ) {
                issues += AnalysisItem(
                    title = "Function missing parentheses",
                    explanation = "This function declaration is missing `()` after its name.",
                    fix = "Write: fun name() { ... }",
                    isIssue = true
                )
            }

            // Incomplete MaterialTheme call
            if (
                !materialThemeWarned &&
                line.contains("MaterialTheme(") &&
                !text.contains("colorScheme")
            ) {
                issues += AnalysisItem(
                    title = "Incomplete MaterialTheme call",
                    explanation = "MaterialTheme is missing `colorScheme = ...`.",
                    fix = "Add `colorScheme = lightColorScheme()` or your custom scheme.",
                    isIssue = true
                )
                materialThemeWarned = true
            }
        }

        // -------------------------------------------------------
        // SUCCESS MESSAGE
        // -------------------------------------------------------

        if (issues.isEmpty()) {
            issues += AnalysisItem(
                title = "No Issues Found",
                explanation = "No common beginner mistakes detected.",
                fix = "Nice work!",
                isIssue = false
            )
        }

        results = issues
    }
}