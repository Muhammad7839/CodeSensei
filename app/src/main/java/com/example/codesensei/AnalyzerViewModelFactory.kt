package com.example.codesensei

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.codesensei.data.rewards.RewardDataStore

/**
 * Factory for creating instances of [AnalyzerViewModel].
 *
 * This factory allows for the creation of [AnalyzerViewModel] with a specific
 * [RewardDataStore] dependency, enabling dependency injection for the ViewModel.
 *
 * @property rewardStore The data store for managing user rewards.
 */
class AnalyzerViewModelFactory(
    private val rewardStore: RewardDataStore
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `ViewModel` class.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AnalyzerViewModel(rewardStore) as T
    }
}