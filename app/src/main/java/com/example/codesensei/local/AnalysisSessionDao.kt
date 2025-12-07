package com.example.codesensei.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data access object (DAO) for reading and writing analysis session history.
 *
 * This interface defines how the app talks to the `analysis_sessions` table
 * in the Room database. Room generates the implementation at compile time.
 */
@Dao
interface AnalysisSessionDao {

    /**
     * Insert a new analysis session into the database.
     *
     * If a row with the same [AnalysisSessionEntity.id] already exists,
     * it will be replaced.
     *
     * @param session The session summary to persist.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: AnalysisSessionEntity)

    /**
     * Get all analysis sessions, newest first.
     *
     * @return A cold [Flow] that emits the full list whenever the table changes.
     *         The UI can collect this flow to show a live-updating history.
     */
    @Query("SELECT * FROM analysis_sessions ORDER BY timestampMillis DESC")
    fun getAllSessions(): Flow<List<AnalysisSessionEntity>>
}