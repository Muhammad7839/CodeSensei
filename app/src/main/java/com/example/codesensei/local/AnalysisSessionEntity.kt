package com.example.codesensei.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a single analysis session.
 *
 * Each row in the `analysis_sessions` table stores a summary of
 * one run of Code Sensei, so we can show a history list later.
 *
 * @param id Auto-generated primary key.
 * @param timestampMillis Time of the analysis (epoch millis).
 * @param codeLength Number of characters in the analyzed code.
 * @param errorCount Number of issues found in that run.
 * @param mainSummary Short text summary shown in the history list.
 */
@Entity(tableName = "analysis_sessions")
data class AnalysisSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val timestampMillis: Long,
    val codeLength: Int,
    val errorCount: Int,
    val mainSummary: String
)