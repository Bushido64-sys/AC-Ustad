package com.acustad.app.ui.saved

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.acustad.app.model.ContentLanguage
import com.acustad.app.model.FavouriteItem
import com.acustad.app.repo.KbRepository
import com.acustad.app.repo.ToggleGuard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** The three designed states every screen in this app has. (RULES.md RULE 17) */
sealed interface SavedState {
    data object Loading : SavedState

    /**
     * The database could not be read. `message` is the underlying cause, kept for a log; it is
     * never rendered, because a raw SQLite string is not a designed state and a view model
     * cannot reach a string resource anyway. (RULES.md RULE 17)
     */
    data class Failed(val message: String?) : SavedState

    data class Ready(val items: List<FavouriteItem>, val language: ContentLanguage) : SavedState
}

/**
 * A problem worth one line of feedback, named rather than spelled. The screen turns it into a
 * string, because a view model cannot read a string resource and a literal here would be a
 * second copy of the copy. (RULES.md RULE 1)
 */
enum class SavedNotice { COULD_NOT_SAVE, COULD_NOT_RESTORE }

/**
 * The saved list.
 *
 * One instance is owned by the navigation host, not by the screen, and shared with the bottom
 * bar — so the tab's star is filled by the same state that renders the rows, and a code starred
 * from the detail screen is reflected when the user comes back rather than on a later reopen.
 *
 * `favourites` is a two-column table keyed on `codes.id`, so a saved row carries no brand, no
 * model and no title of its own: [KbRepository.favouriteItems] joins them back for display, and
 * the joined values are held here in memory rather than by `ALTER`ing a table the app does not
 * own. (PHASE_6_FAVOURITES.md §2)
 *
 * Every write is optimistic and reversible. A failed write puts the row back rather than
 * leaving the screen claiming something the database does not have.
 */
class SavedViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = KbRepository(app)
    private val guard = ToggleGuard()

    /** `null` until the first read lands, which is what distinguishes Loading from empty. */
    private val _items = MutableStateFlow<List<FavouriteItem>?>(null)
    private val _failure = MutableStateFlow<String?>(null)
    private val _notice = MutableStateFlow<SavedNotice?>(null)

    val notice: StateFlow<SavedNotice?> = _notice.asStateFlow()

    /**
     * The language is part of the state rather than a separate read, so flipping EN/UR
     * re-renders the rows with **no new query** — both languages come back from the one join.
     * (RULES.md RULE 13)
     */
    val state: StateFlow<SavedState> =
        combine(_items, _failure, repo.contentLanguage) { items, failure, language ->
            when {
                failure != null -> SavedState.Failed(failure)
                items == null -> SavedState.Loading
                else -> SavedState.Ready(items, language)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SavedState.Loading)

    init {
        viewModelScope.launch { load() }
    }

    /**
     * Re-reads the list. Called when the user returns to a top-level screen, because a code may
     * have been starred from the detail screen, which has its own view model and its own
     * repository instance.
     */
    fun reload() {
        viewModelScope.launch { load() }
    }

    /**
     * Removes a row and reports the failure rather than swallowing it.
     *
     * Returns false when the write was dropped as a double tap, so the caller does not raise an
     * Undo bar for a remove that never happened.
     */
    fun remove(item: FavouriteItem): Boolean {
        val before = _items.value ?: return false
        if (!guard.allow(item.codeId, monotonicMs())) return false

        _items.value = before.filterNot { it.codeId == item.codeId }
        viewModelScope.launch {
            runCatching { repo.setFavourite(item.codeId, false) }
                .onFailure {
                    // Put the row back rather than lie about what is saved.
                    _items.value = before
                    _notice.value = SavedNotice.COULD_NOT_SAVE
                }
        }
        return true
    }

    /**
     * Puts a removed row back where it was. The stored `created_at` is written verbatim, so the
     * row returns to its place in the newest-first order instead of appearing as the newest.
     */
    fun undo(item: FavouriteItem) {
        viewModelScope.launch {
            runCatching { repo.restoreFavourite(item.codeId, item.createdAt) }
                .onSuccess { load() }
                .onFailure { _notice.value = SavedNotice.COULD_NOT_RESTORE }
        }
    }

    fun dismissNotice() {
        _notice.value = null
    }

    private suspend fun load() {
        val result = runCatching { repo.favouriteItems() }
        val failure = result.exceptionOrNull()
        when {
            failure == null -> {
                _failure.value = null
                _items.value = result.getOrThrow()
            }
            // Nothing on screen yet, so there is nothing to preserve: say so plainly.
            _items.value == null -> _failure.value = failure.message
            // A list is already on screen. Blanking it because a background refresh failed
            // would destroy the user's view of their own saved codes.
            else -> _notice.value = SavedNotice.COULD_NOT_SAVE
        }
    }

    /** Monotonic, and cheap. `System.currentTimeMillis()` is not usable here. */
    private fun monotonicMs(): Long = System.nanoTime() / 1_000_000L
}
