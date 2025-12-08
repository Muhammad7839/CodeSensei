package com.example.codesensei.data.rewards

import android.content.Context
import kotlinx.coroutines.flow.Flow
import com.example.codesensei.data.settings.dataStore

/**
 * Manages the storage and retrieval of user's reward points using Jetpack DataStore.
 *
 * @param context The application context, used to access the DataStore.
 */
class RewardDataStore(private val context: Context) {

    companion object {
        /**
         * A key for storing the total points as an integer in the DataStore.
         */
        private val POINTS_KEY = intPreferencesKey("total_points")
    }

    /**
     * A [Flow] that emits the current total points of the user.
     * It observes changes in the DataStore and emits the new value.
     * If no points are set, it defaults to 0.
     */
    val pointsFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[POINTS_KEY] ?: 0
    }

    /**
     * Suspended function to increment the user's total points by one.
     * This is an atomic operation on the DataStore.
     */
    suspend fun addPoint() {
        context.dataStore.edit { preferences ->
            val currentPoints = preferences[POINTS_KEY] ?: 0
            preferences[POINTS_KEY] = currentPoints + 1
        }
    }

    /**
     * Resets the user's total points to zero.
     * This is an atomic operation on the DataStore.
     */
    suspend fun resetPoints() {
        context.dataStore.edit { preferences ->
            preferences[POINTS_KEY] = 0
        }
    }
}
/**
 * Calculates the user's level based on their total points.
 *
 * @param points The total points accumulated by the user.
 * @return A string representing the user's calculated level (e.g., "Beginner", "Sensei").
 */
fun calculateLevel(points: Int): String {
    return when {
        points >= 30 -> "Sensei"
        points >= 15 -> "Debugger"
        points >= 5 -> "Learner"
        else -> "Beginner"
    }
}