package com.example.codesensei.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Main Room database for Code Sensei.
 *
 * Holds the `analysis_sessions` table and exposes [AnalysisSessionDao]
 * to read and write analysis history.
 */
@Database(
    entities = [AnalysisSessionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CodeSenseiDatabase : RoomDatabase() {

    /**
     * DAO for accessing analysis session history.
     */
    abstract fun analysisSessionDao(): AnalysisSessionDao
}

/**
 * Simple provider for a singleton instance of [CodeSenseiDatabase].
 *
 * Keeping the singleton logic outside the @Database class itself avoids
 * any confusion for Room's annotation processor.
 */
object CodeSenseiDatabaseProvider {

    @Volatile
    private var instance: CodeSenseiDatabase? = null

    /**
     * Get a singleton [CodeSenseiDatabase] for the given [context].
     *
     * @param context Any context; the application context is used internally.
     */
    fun getInstance(context: Context): CodeSenseiDatabase {
        return instance ?: synchronized(this) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                CodeSenseiDatabase::class.java,
                "code_sensei_db"
            ).build()
            instance = db
            db
        }
    }
}