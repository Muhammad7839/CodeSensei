package com.example.codesensei

// One analysis result item: a short title, an explanation, and a suggested fix.
data class AnalysisItem(
    val title: String,
    val explanation: String,
    val fix: String
)