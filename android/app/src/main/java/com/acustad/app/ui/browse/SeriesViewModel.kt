package com.acustad.app.ui.browse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.acustad.app.model.ContentLanguage
import com.acustad.app.model.Series
import com.acustad.app.repo.KbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The model lines of one brand.
 *
 * The brand's `notes` are long — median 269, p90 702, max 1,450 characters — so they are
 * collapsed behind a **Show details** control and never dumped over the list. (DESIGN.md §4.3)
 *
 * 65 of the 320 model lines in the app have no codes at all, so the empty state is a main path
 * rather than an edge case, and it is worded as information.
 */
class SeriesViewModel(
    app: Application,
    savedState: SavedStateHandle,
) : AndroidViewModel(app) {

    private val repo = KbRepository.get(app)

    val brandId: String = savedState.get<String>(ARG_BRAND_ID).orEmpty()

    /**
     * The brand name is read from the database, not passed through the route: a route segment
     * cannot safely contain a name with slashes or brackets, and a slug cannot be shown to a
     * technician. It is therefore a mutable field set once on load, with a safe blank default
     * so the app bar never crashes before the first query returns.
     */
    var brandName: String = ""
        private set

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _notesExpanded = MutableStateFlow(false)
    val notesExpanded: StateFlow<Boolean> = _notesExpanded.asStateFlow()

    private val _all = MutableStateFlow<List<Series>>(emptyList())
    private val _brandNotes = MutableStateFlow<String?>(null)

    val state: StateFlow<SeriesState> =
        combine(_all, _query, repo.contentLanguage) { all, q, language ->
            val filtered = if (q.isBlank()) all else all.filter {
                it.name.contains(q.trim(), ignoreCase = true)
            }
            SeriesState(
                series = filtered,
                notes = _brandNotes.value,
                query = q,
                language = language,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SeriesState())

    init {
        viewModelScope.launch {
            val all = runCatching { repo.seriesOf(brandId) }.getOrDefault(emptyList())
            _all.value = all
            // One lookup gives both the app-bar name and the collapsible editorial note.
            val brand = runCatching { repo.brand(brandId) }.getOrNull()
            brandName = brand?.name.orEmpty()
            _brandNotes.value = brand?.notes
        }
    }

    fun onQueryChange(value: String) {
        _query.value = value
    }

    fun onClearQuery() = onQueryChange("")

    fun toggleNotes() {
        _notesExpanded.value = !_notesExpanded.value
    }

    companion object {
        const val ARG_BRAND_ID = "brandId"
    }
}

data class SeriesState(
    val series: List<Series> = emptyList(),
    val notes: String? = null,
    val query: String = "",
    val language: ContentLanguage = ContentLanguage.EN,
)
