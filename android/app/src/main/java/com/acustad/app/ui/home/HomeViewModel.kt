package com.acustad.app.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.acustad.app.data.BrandRepository
import com.acustad.app.data.CategoryCounts
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

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = BrandRepository(app)

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            _state.value = runCatching {
                val counts = repo.categoryCounts()
                val version = repo.databaseVersion()
                HomeState.Ready(counts, version)
            }.getOrElse { e ->
                HomeState.Failed(e.message ?: "Could not open the knowledge base")
            }
        }
    }
}
