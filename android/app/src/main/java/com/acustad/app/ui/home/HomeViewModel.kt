package com.acustad.app.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.acustad.app.model.CategoryCounts
import com.acustad.app.model.ContentLanguage
import com.acustad.app.repo.KbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** The three designed states every screen in this app has (RULES.md RULE 17). */
sealed interface HomeState {
    data object Loading : HomeState
    data class Ready(val counts: CategoryCounts, val kbVersion: String) : HomeState
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

    /**
     * Exposed so the screen can show which language is active. The counts and the two category
     * names are English either way — a brand count is not content — so this is only here to
     * render the toggle's own selected state.
     */
    val language: StateFlow<ContentLanguage> = repo.contentLanguage

    init {
        load()
    }

    fun load() {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            _state.value = runCatching {
                // Two cheap queries on one connection; both off the main thread.
                HomeState.Ready(repo.categoryCounts(), repo.meta().kbVersion)
            }.getOrElse { e ->
                HomeState.Failed(e.message ?: "Could not open the knowledge base")
            }
        }
    }

    /**
     * Sets the content language. Costs **no query**: both languages were already returned by
     * every read, so every screen holding this repository's flow re-renders on the spot.
     * (RULES.md RULE 13)
     */
    fun setLanguage(language: ContentLanguage) = repo.setContentLanguage(language)
}
