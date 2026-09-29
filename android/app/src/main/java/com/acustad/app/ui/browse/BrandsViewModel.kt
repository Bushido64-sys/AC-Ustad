package com.acustad.app.ui.browse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.acustad.app.model.Brand
import com.acustad.app.model.CategoryId
import com.acustad.app.model.ContentLanguage
import com.acustad.app.repo.KbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The brands in one category.
 *
 * All 33 (or 31) rows are held in memory and filtered in Kotlin: the whole table is 62 rows,
 * which is smaller than the machinery paging would need. `KbRepository.brands()` returns them
 * already sorted by code count descending, which is the highest-value ordering on this screen
 * — the model lines a technician actually meets have the most codes.
 */
class BrandsViewModel(
    app: Application,
    savedState: SavedStateHandle,
) : AndroidViewModel(app) {

    private val repo = KbRepository.get(app)

    val category: CategoryId =
        if (savedState.get<String>(ARG_CATEGORY) == "ac") CategoryId.AC else CategoryId.INVERTER

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _showAll = MutableStateFlow(false)

    private val _all = MutableStateFlow<List<Brand>>(emptyList())

    val state: StateFlow<BrandsState> =
        combine(_all, _query, _showAll, repo.contentLanguage) { brands, q, all, language ->
            val filtered = if (q.isBlank()) brands else brands.filter {
                it.name.contains(q.trim(), ignoreCase = true)
            }
            BrandsState(
                brands = if (all) filtered else filtered.take(FIRST_PAGE),
                total = filtered.size,
                canShowAll = !all && filtered.size > FIRST_PAGE,
                query = q,
                language = language,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), BrandsState())

    init {
        viewModelScope.launch {
            _all.value = runCatching { repo.brands(category) }.getOrDefault(emptyList())
        }
    }

    fun onQueryChange(value: String) {
        _query.value = value
        // A new query starts from the first page again, otherwise a short query can look
        // empty simply because the list is still collapsed.
        _showAll.value = false
    }

    fun onClearQuery() = onQueryChange("")

    fun showAll() {
        _showAll.value = true
    }

    companion object {
        const val ARG_CATEGORY = "category"

        /** First slice before "Show all"; see DESIGN.md §4.2. */
        const val FIRST_PAGE = 16
    }
}

/**
 * Brands are already English in the database, so `query` is matched against the name in any
 * content language — the EN/UR switch changes code content, never brand names (RULE 13).
 */
data class BrandsState(
    val brands: List<Brand> = emptyList(),
    val total: Int = 0,
    val canShowAll: Boolean = false,
    val query: String = "",
    val language: ContentLanguage = ContentLanguage.EN,
)
