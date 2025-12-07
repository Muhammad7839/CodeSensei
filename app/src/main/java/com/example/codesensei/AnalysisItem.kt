package com.example.codesensei

/**
 * One item in the analysis result list.
 *
 * @param title Short label describing the issue or message.
 * @param explanation Human-readable explanation of what might be wrong
 *        or a summary message when there are no issues.
 * @param fix Optional suggestion on how to fix or improve the code.
 * @param isIssue True if this item represents an actual problem in the code.
 *        False for success / informational messages.
 */
data class AnalysisItem(
    val title: String,
    val explanation: String,
    val fix: String = "",
    val isIssue: Boolean = true
)