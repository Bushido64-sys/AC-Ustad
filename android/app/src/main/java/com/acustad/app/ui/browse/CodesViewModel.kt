package com.acustad.app.ui.browse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.acustad.app.model.CodeSummary
import com.acustad.app.model.ContentLanguage
import com.acustad.app.model.ScopedSeries
import com.acustad.app.repo.KbRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The codes of one model line, and the search field above them.
 *
 * Search here is **scoped to this series**: `SearchDao.searchCodes` binds `series_id` AND
 * `brand_id`, so typing `E6` can only ever find this model's `E6`. There is no code search
 * anywhere in the app that can escape a model line. (RULES.md RULE 3)
 *
 * Keystrokes are debounced by 180ms so a 5-letter code issues one query, not five.
 */
class CodesViewModel(
    app: Application,
    savedState: SavedStateHandle,
) : AndroidViewModel(app) {

    private val repo = KbRepository(app)

    val seriesId: String = savedState.get<String>(ARG_SERIES_ID).orEmpty()
    val brandId: String = savedState.get<String>(ARG_BRAND_ID).orEmpty()

    /**
     * Display names are read from the database rather than carried in the route, because a route
     * segment cannot safely hold a name containing slashes or brackets, and the slug
     * `inverter-split` means nothing to a technician. Set once on load; blank until then, which
     * is why the app bar simply shows the code count rather than flashing a slug.
     */
    var seriesName: String = ""
        private set
    var brandName: String = ""
        private set

    private val scope = ScopedSeries(seriesId, brandId)

    private val _all = MutableStateFlow<List<CodeSummary>>(emptyList())
    private val _query = MutableStateFlow("")
    private val _results = MutableStateFlow<List<CodeSummary>?>(null)

    val query: StateFlow<String> = _query.asStateFlow()

    /** null means "no search running", so the plain list is shown. */
    private val _searching = MutableStateFlow(false)

    val state: StateFlow<CodesState> =
        combine(_all, _query, _results, _searching, repo.contentLanguage) { all, q, results, busy, language ->
            CodesState(
                codes = results ?: all,
                isSearching = busy,
                query = q,
                language = language,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), CodesState())

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            val series = runCatching { repo.series(seriesId, brandId) }.getOrNull()
            seriesName = series?.name.orEmpty()
            brandName = series?.let { repo.brand(it.brandId)?.name }.orEmpty()
            _all.value = runCatching { repo.codesOf(scope) }.getOrDefault(emptyList())
        }
    }

    fun onQueryChange(value: String) {
        _query.value = value
        searchJob?.cancel()
        if (value.isBlank()) {
            _results.value = null
            _searching.value = false
            return
        }
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_MS)
            _searching.value = true
            _results.value = runCatching { repo.searchCodes(scope, value) }.getOrDefault(emptyList())
            _searching.value = false
        }
    }

    fun onClearQuery() = onQueryChange("")

    companion object {
        const val ARG_SERIES_ID = "seriesId"
        const val ARG_BRAND_ID = "brandId"

        /** 180ms: long enough to avoid a query per keystroke, short enough to feel instant. */
        const val DEBOUNCE_MS = 180L
    }
}

data class CodesState(
    val codes: List<CodeSummary> = emptyList(),
    val isSearching: Boolean = false,
    val query: String = "",
    val language: ContentLanguage = ContentLanguage.EN,
)
