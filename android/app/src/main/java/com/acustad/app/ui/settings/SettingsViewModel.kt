package com.acustad.app.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.acustad.app.model.ContentLanguage
import com.acustad.app.model.KbMeta
import com.acustad.app.model.ThemeMode
import com.acustad.app.repo.KbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The state of the **data block** only. The language and theme are not in it, and that is
 * deliberate: they are readable without touching the database, so a failed read must not take
 * them off the screen along with everything else. (RULES.md RULE 17)
 */
sealed interface SettingsState {
    data object Loading : SettingsState

    /**
     * The database could not be read, so the version and the coverage cannot be shown.
     * `message` is kept for a log and never rendered — a raw SQLite string is not a designed
     * state, and a view model cannot reach a string resource. (RULES.md RULE 17)
     */
    data class Failed(val message: String?) : SettingsState

    data class Ready(val meta: KbMeta, val dataBytes: Long?) : SettingsState
}

/**
 * The Settings screen's data.
 *
 * `meta` and the file size are the only two things here that need the database, and both are one
 * read of the already-open connection.
 *
 * The language and theme are exposed as [repo]'s own flows, exactly as the content language is
 * everywhere else in the app, so flipping either re-renders with **no query** and reaches every
 * screen at once. (RULES.md RULE 13, trap 12) They are separate from [state] on purpose — see
 * [SettingsState].
 */
class SettingsViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = KbRepository.get(app)

    private val _meta = MutableStateFlow<KbMeta?>(null)
    private val _dataBytes = MutableStateFlow<Long?>(null)
    private val _failure = MutableStateFlow<String?>(null)

    /** The stored content language, or the default before the first frame. Never a guess. */
    val language: StateFlow<ContentLanguage> = repo.contentLanguage

    /** The stored theme choice. [ThemeMode.SYSTEM] until the preferences file says otherwise. */
    val theme: StateFlow<ThemeMode> = repo.theme

    val state: StateFlow<SettingsState> =
        combine(_meta, _dataBytes, _failure) { meta, bytes, failure ->
            when {
                failure != null -> SettingsState.Failed(failure)
                meta == null -> SettingsState.Loading
                else -> SettingsState.Ready(meta, bytes)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SettingsState.Loading)

    init {
        load()
    }

    /**
     * Re-reads the data block. Called when the tab is opened, so a knowledge base picked up by
     * an app update shows its new version here without needing a restart.
     */
    fun load() {
        _meta.value = null
        _failure.value = null
        viewModelScope.launch {
            runCatching {
                // The size is read after `meta()` on purpose: opening the database is what
                // stages the copy out of the asset, so asking for a length first would measure
                // a file that may not exist yet.
                repo.meta() to repo.dataSizeBytes()
            }.onSuccess { (meta, bytes) ->
                _meta.value = meta
                _dataBytes.value = bytes
            }.onFailure { error ->
                // The controls stay on screen; only the data block reports this.
                _failure.value = error.message
            }
        }
    }

    /**
     * Both setters cost **no query**. They write to the shared repository, so the change is
     * persisted and reaches every screen already holding its flows.
     */
    fun setLanguage(language: ContentLanguage) = repo.setContentLanguage(language)

    fun setTheme(theme: ThemeMode) = repo.setTheme(theme)
}
