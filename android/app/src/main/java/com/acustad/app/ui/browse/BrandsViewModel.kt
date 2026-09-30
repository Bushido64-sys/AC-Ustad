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

    /**
     * The teaching hint for a dead-end query, or null while there is nothing to say.
     *
     * Null is a **third** state, distinct from "no hint" and from "hint failed". It means "not
     * applicable": the query was a brand name, or a phrase with nothing worth saying about it,
     * so there is no rule to teach. Keeping it as null rather than an `Ambiguous(0, …)` is what
     * stops a typo from producing a paragraph of explanation about a code that does not exist.
     *
     * A failed lookup also lands on null, and that is deliberate and worth being explicit
     * about: the teaching line is a courtesy, and a courtesy that could fail must never take the
     * plain "no brand matches" message down with it. (trap 5)
     */
    private val _teaching = MutableStateFlow<BrandSearchTeaching?>(null)

    val teaching: StateFlow<BrandSearchTeaching?> = _teaching.asStateFlow()

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
        refreshTeaching(value)
    }

    fun onClearQuery() = onQueryChange("")

    fun showAll() {
        _showAll.value = true
    }

    /**
     * Works out whether the current query deserves the teaching empty state.
     *
     * Two-stage on purpose, and the cheap stage first. [looksLikeACode] is a digit test over the
     * raw string — free, and it rejects the overwhelming majority of keystrokes. Only a query
     * that survives it reaches the database, so this costs at most one 0.15 ms query on a
     * debounced search field and usually costs nothing.
     *
     * The branch it returns into is the other half: a query that is *not* code-shaped is decided
     * from the text alone by [isDescription], because that question needs no data — no brand
     * name has a digit, and a two-word phrase with none is a fault described, not a name
     * misspelled. Running [com.acustad.app.repo.KbRepository.codePresence] for those would be a
     * query per keystroke to learn what the string already says.
     *
     * Every keystroke cancels the previous lookup's result. Without that, typing `E6` one
     * character at a time would leave whichever answer arrived last on screen, which is not
     * necessarily the one for the text currently in the field. A stale hint is worse than no
     * hint: it explains the wrong thing confidently.
     */
    private fun refreshTeaching(value: String) {
        _teaching.value = null
        if (!looksLikeACode(value)) {
            // brandCount 0 is what codePresence returns for anything that is not a code, so the
            // arguments describe the situation exactly: not a code, therefore possibly words.
            val hint = teachingFor(value, brandCount = 0)
            if (hint is BrandSearchTeaching.Description) _teaching.value = hint
            return
        }

        val asked = value
        viewModelScope.launch {
            val hint = runCatching {
                val presence = repo.codePresence(asked)
                teachingFor(asked, presence.brandCount, presence.brandName)
            }.getOrDefault(BrandSearchTeaching.None)

            // The query moved on while this was in flight. `String` equality, not identity, so
            // a re-typed identical query is still accepted.
            if (asked == _query.value) _teaching.value = hint
        }
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
