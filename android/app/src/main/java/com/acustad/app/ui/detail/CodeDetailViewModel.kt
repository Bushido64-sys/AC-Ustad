package com.acustad.app.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.acustad.app.model.CodeDetail
import com.acustad.app.model.ContentLanguage
import com.acustad.app.repo.KbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * One code, in full.
 *
 * The screen is read once and held: both languages come from a single query, so flipping EN/UR
 * re-renders with no new query at all. (RULES.md RULE 13)
 *
 * Nothing is ever invented. If the data has no causes, no solutions, no notes or no source
 * link, the block is hidden rather than filled with a placeholder — a half-truth about a
 * machine is worse than a gap. (RULES.md RULE 1)
 */
class CodeDetailViewModel(
    app: Application,
    savedState: SavedStateHandle,
) : AndroidViewModel(app) {

    private val repo = KbRepository(app)

    private val codeId: Long = savedState.get<Long>(ARG_CODE_ID) ?: -1L

    /** The machine this code belongs to, read from the row itself rather than passed in. */
    var seriesName: String = ""
        private set
    var brandName: String = ""
        private set

    private val _detail = MutableStateFlow<CodeDetail?>(null)
    private val _loading = MutableStateFlow(true)
    private val _notesExpanded = MutableStateFlow(false)

    val notesExpanded: StateFlow<Boolean> = _notesExpanded.asStateFlow()

    val state: StateFlow<DetailState> =
        combine(_detail, _loading, _notesExpanded, repo.contentLanguage) { detail, loading, notes, language ->
            DetailState(
                detail = detail,
                isLoading = loading,
                notesExpanded = notes,
                language = language,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DetailState())

    init {
        viewModelScope.launch {
            val detail = runCatching { repo.codeDetail(codeId) }.getOrNull()
            _detail.value = detail
            // Two small lookups to name the machine in the app bar, so a technician always knows
            // which manual they are reading. Cheaper than putting a name in a route, which is not
            // even possible: model names contain slashes and brackets.
            if (detail != null) {
                seriesName = runCatching { repo.series(detail.seriesId, detail.brandId)?.name }
                    .getOrNull().orEmpty()
                brandName = runCatching { repo.brand(detail.brandId)?.name }.getOrNull().orEmpty()
            }
            _loading.value = false
        }
    }

    /**
     * Optimistic: the star fills immediately, and the write is a single transaction on a
     * two-column table, so the failure case is a rollback and one line of feedback. There is no
     * `is_read` column in the shipped favourites table, so starring is also the only state a
     * saved row has.
     */
    fun toggleFavourite() {
        val current = _detail.value ?: return
        val next = !current.isFavourite
        _detail.value = current.copy(isFavourite = next)
        viewModelScope.launch {
            runCatching { repo.setFavourite(current.summary.id, next) }
                .onFailure {
                    // Put the star back rather than lying about what was saved.
                    _detail.value = _detail.value?.copy(isFavourite = !next)
                }
        }
    }

    fun toggleNotes() {
        _notesExpanded.value = !_notesExpanded.value
    }

    companion object {
        const val ARG_CODE_ID = "codeId"

        /** Notes past this length are collapsed. p90 is 202 chars, max 725. */
        const val NOTES_COLLAPSE_CHARS = 180
    }
}

data class DetailState(
    val detail: CodeDetail? = null,
    val isLoading: Boolean = true,
    val notesExpanded: Boolean = false,
    val language: ContentLanguage = ContentLanguage.EN,
)
