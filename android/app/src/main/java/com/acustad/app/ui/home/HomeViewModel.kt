package com.acustad.app.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.acustad.app.model.CategoryCounts
import com.acustad.app.repo.KbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** The three designed states every screen in this app has (RULES.md RULE 17). */
sealed interface HomeState {
    data object Loading : HomeState
    data class Ready(val counts: CategoryCounts) : HomeState
    data class Failed(val message: String) : HomeState
}

/**
 * Reads the two category counts from the database. Never hard-codes them, so a data release
 * updates the numbers with no app change.
 */
class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = KbRepository.get(app)

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            _state.value = runCatching {
                // One cheap query on the open connection, off the main thread. The data
                // version lives in Settings now, as the merged version line — Home no
                // longer reads meta, so a data release moves no number on this screen.
                HomeState.Ready(repo.categoryCounts())
            }.getOrElse { e ->
                HomeState.Failed(e.message ?: "Could not open the knowledge base")
            }
        }
    }
}
