package com.acustad.app.repo

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import com.acustad.app.data.CatalogDao
import com.acustad.app.data.CodeDao
import com.acustad.app.data.FavouritesDao
import com.acustad.app.data.KbDatabase
import com.acustad.app.data.SearchDao
import com.acustad.app.model.Brand
import com.acustad.app.model.CategoryCounts
import com.acustad.app.model.CategoryId
import com.acustad.app.model.CodeDetail
import com.acustad.app.model.CodeSummary
import com.acustad.app.model.ContentLanguage
import com.acustad.app.model.FavouriteItem
import com.acustad.app.model.KbMeta
import com.acustad.app.model.ScopedSeries
import com.acustad.app.model.Series
import com.acustad.app.model.ThemeMode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * The app's only door to the database.
 *
 * The UI never sees a `Cursor` and never knows a table name. It asks this class a question and
 * gets a list of immutable models back.
 *
 * Two things it deliberately does **not** do:
 *
 *  - **No paging.** Brands and model lines are 382 rows in total and the largest single code
 *    list is 106. Holding them in memory is smaller and faster than paging would be. Revisit
 *    only if a measurement says so (Phase 9).
 *  - **No network.** There is no other repository in the app (RULES.md RULE 14).
 *
 * One connection is opened for the process lifetime and wrapped in a throwaway DAO per call —
 * the DAOs are stateless, so allocating one costs nothing and removes any chance of a DAO
 * capturing a stale handle.
 *
 * ### One instance per process, deliberately
 *
 * Every view model takes this class from [get], never from the constructor. Two things depend
 * on it:
 *
 *  - **The content language has to reach every screen at once.** Both languages come back from
 *    every query, so the choice is made at read time from one flow — and a flow held by a
 *    private instance reaches only the screen that made it. With a repository per view model
 *    the EN/UR toggle would flip the codes list and nothing else. (RULES.md RULE 13)
 *  - **One `SQLiteDatabase` handle per process** rather than one per view model, so six
 *    screens cannot each hold an open connection to the same file.
 */
class KbRepository private constructor(
    context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private val appContext = context.applicationContext
    private val kb = KbDatabase.get(appContext)

    /**
     * Two settings in a preferences file: the content language, and (Phase 8) the theme.
     * `SharedPreferences` rather than DataStore or a table, per DEPENDENCIES.md §2 — there is
     * nothing to migrate and nothing to query.
     *
     * Declared before `_contentLanguage` below, deliberately: Kotlin runs property initialisers
     * in declaration order, and the language is seeded from this file.
     *
     * **Known StrictMode cost, accepted on purpose.** The first `getSharedPreferences` in a
     * process reads the XML off disk synchronously, and this runs on the main thread when the
     * first view model is constructed. `TESTING.md` §6 asks for no disk on the main thread, so
     * this is a real exception to it: the file is tens of bytes, it is read exactly once per
     * process, and the alternative — seeding the flow asynchronously — makes every screen that
     * reads `contentLanguage` flash from English to the stored language on every cold start. A
     * technician who chose Urdu would see English first, every time. The flicker is worse than
     * the microsecond of IO, so the IO is kept and recorded here rather than hidden.
     */
    private val prefs: SharedPreferences =
        appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Volatile
    private var handle: SQLiteDatabase? = null

    /** Guards the one-time open. A coroutine Mutex, because the work inside it suspends. */
    private val openLock = Mutex()

    // ── language ──────────────────────────────────────────────────────────────
    // A single flow, so toggling EN/UR re-renders with NO new query: both languages are always
    // loaded together and the choice is made at read time. (RULES.md RULE 13)
    //
    // The choice is **persisted**, because a toggle that forgets on every app launch is not a
    // toggle. A technician sets it once, in Urdu, and it should still be Urdu tomorrow. Two keys
    // in a preferences file, not a database: DEPENDENCIES.md §2 rules out DataStore for
    // two settings, and there is no migration story to invent.
    private val _contentLanguage = MutableStateFlow(languageFrom(readStoredLanguage()))
    val contentLanguage: StateFlow<ContentLanguage> = _contentLanguage.asStateFlow()

    fun setContentLanguage(language: ContentLanguage) {
        if (_contentLanguage.value == language) return
        _contentLanguage.value = language
        // commit() rather than apply(): a technician switches language, pockets the phone and
        // comes back to it tomorrow, and losing that is exactly what this setting exists to
        // stop. The file is a few bytes and this is not on a hot path.
        prefs.edit().putString(KEY_CONTENT_LANGUAGE, language.name).commit()
    }

    private fun readStoredLanguage(): String? = prefs.getString(KEY_CONTENT_LANGUAGE, null)

    // ── theme ─────────────────────────────────────────────────────────────────
    // The same shape as the language, and for the same reasons: one flow on the one shared
    // instance, so a change is visible to every screen at once, and persisted, because a
    // setting that forgets is not a setting.
    //
    // It lives here rather than in a Compose `remember` because the activity reads it *above*
    // the whole navigation graph, and a value held inside a composable would restart the app's
    // theming from the default on every configuration change. (MainActivity)
    private val _theme = MutableStateFlow(themeFrom(readStoredTheme()))
    val theme: StateFlow<ThemeMode> = _theme.asStateFlow()

    fun setTheme(mode: ThemeMode) {
        if (_theme.value == mode) return
        _theme.value = mode
        // commit(), for the same reason as the language: a technician sets it once and comes
        // back tomorrow. Losing that is the failure this whole block exists to prevent.
        prefs.edit().putString(KEY_THEME, mode.name).commit()
    }

    private fun readStoredTheme(): String? = prefs.getString(KEY_THEME, null)

    /**
     * Opens the database once, on the IO dispatcher, and sweeps stale saved ids the first
     * time. The sweep matters because `favourites.code_id` is only stable within one data
     * release: after a re-copy, a saved id can point at a different code.
     *
     * The lock is a coroutine `Mutex`, **not** `synchronized`. Opening the file and running
     * the sweep are both suspending, and Kotlin rejects a suspension point inside a
     * `synchronized` block — that is an error, not a warning. A Mutex is the primitive meant
     * for a critical section that spans suspension.
     *
     * `handle` is `@Volatile` and read outside the lock on the fast path, so the common case
     * (the database is already open) costs one volatile read and no lock at all.
     */
    private suspend fun db(): SQLiteDatabase {
        handle?.let { return it }
        return withContext(dispatcher) {
            openLock.withLock {
                handle ?: kb.open().also { opened ->
                    handle = opened
                    FavouritesDao(opened).sweepStaleIds()
                }
            }
        }
    }

    // ── content ───────────────────────────────────────────────────────────────

    suspend fun categoryCounts(): CategoryCounts = CatalogDao(db()).categoryCounts()

    suspend fun brands(category: CategoryId): List<Brand> {
        val needle = when (category) {
            CategoryId.AC -> "\"ac\""
            CategoryId.INVERTER -> "\"inverter\""
        }
        return CatalogDao(db()).brandsIn(needle)
    }

    suspend fun brand(brandId: String): Brand? = CatalogDao(db()).brand(brandId)

    suspend fun seriesOf(brandId: String): List<Series> = CatalogDao(db()).seriesOf(brandId)

    suspend fun series(seriesId: String, brandId: String): Series? =
        CatalogDao(db()).series(seriesId, brandId)

    suspend fun codesOf(scope: ScopedSeries): List<CodeSummary> = CodeDao(db()).codesIn(scope)

    suspend fun searchCodes(scope: ScopedSeries, query: String): List<CodeSummary> =
        SearchDao(db()).searchCodes(scope, query)

    suspend fun meta(): KbMeta = CatalogDao(db()).meta()

    /**
     * One code, in one query, with its causes, fix steps and saved state.
     * Returns null when the uid is not in the database — a normal outcome, not an error.
     */
    /**
     * One code by its primary key. The key is used rather than the uid because
     * `codes.uid` is `brand/series/code` and a route segment cannot contain slashes.
     */
    suspend fun codeDetail(id: Long): CodeDetail? {
        val database = db()
        return CodeDao(database).detailById(id, FavouritesDao(database).isFavourite(id))
    }

    // ── favourites ────────────────────────────────────────────────────────────

    suspend fun favouriteItems(): List<FavouriteItem> = FavouritesDao(db()).items()

    suspend fun setFavourite(codeId: Long, favourite: Boolean) {
        FavouritesDao(db()).set(codeId, favourite)
    }

    /**
     * Undo for a removed row. The caller's `createdAt` is written back verbatim so the row
     * returns to its place in a newest-first list instead of jumping to the top.
     */
    suspend fun restoreFavourite(codeId: Long, createdAt: String) {
        FavouritesDao(db()).restore(codeId, createdAt)
    }

    suspend fun isFavourite(codeId: Long): Boolean = FavouritesDao(db()).isFavourite(codeId)

    companion object {
        private const val PREFS_NAME = "ac-ustad"
        private const val KEY_CONTENT_LANGUAGE = "content_language"
        private const val KEY_THEME = "theme_mode"

        @Volatile
        private var instance: KbRepository? = null

        /**
         * The one repository. Mirrors [KbDatabase.get] exactly, for the same reason: a stale
         * handle must never be reachable, and `applicationContext` keeps it from leaking an
         * Activity.
         *
         * Call this, never the constructor — the constructor is private precisely so a
         * view model cannot quietly take a second copy and strand the language flow.
         */
        fun get(context: Context): KbRepository =
            instance ?: synchronized(this) {
                instance ?: KbRepository(context).also { instance = it }
            }
    }
}

/**
 * Maps the stored preference value to a language.
 *
 * Anything that is not exactly `UR` is English. That covers a missing value, a blank one, and —
 * the case worth writing down — a value written by a **future** version under a different enum
 * constant name, which is what `enumValueOf` would throw on. An old preferences file must never
 * be able to stop the app opening.
 *
 * Public, and pure, so the round trip can be tested without a device. It is the whole contract
 * for what may sit in that preferences file.
 */
fun languageFrom(raw: String?): ContentLanguage =
    if (raw == ContentLanguage.UR.name) ContentLanguage.UR else ContentLanguage.EN

/**
 * Maps the stored preference value to a theme, with exactly the same contract as [languageFrom]:
 * anything unrecognised is the **default**, never an exception.
 *
 * The default is [ThemeMode.SYSTEM], not LIGHT. The two are the same value today and are not the
 * same decision — SYSTEM will follow the phone into dark mode, LIGHT will not — so a value this
 * build cannot read must resolve to the one that keeps deferring to the system. Defaulting to
 * LIGHT here would be a preferences file quietly overriding the phone's own setting.
 */
fun themeFrom(raw: String?): ThemeMode = ThemeMode.entries.firstOrNull { it.name == raw }
    ?: ThemeMode.SYSTEM
