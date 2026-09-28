package com.acustad.app.repo

import android.content.Context
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
 */
class KbRepository(
    context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private val kb = KbDatabase.get(context)

    @Volatile
    private var handle: SQLiteDatabase? = null

    @Volatile
    private var swept = false

    // ── language ──────────────────────────────────────────────────────────────
    // A single flow, so toggling EN/UR re-renders with NO new query: both languages are always
    // loaded together and the choice is made at read time. (RULES.md RULE 13)
    private val _contentLanguage = MutableStateFlow(ContentLanguage.EN)
    val contentLanguage: StateFlow<ContentLanguage> = _contentLanguage.asStateFlow()

    fun setContentLanguage(language: ContentLanguage) {
        _contentLanguage.value = language
    }

    /**
     * Opens the database once, on the IO dispatcher, and sweeps stale saved ids the first
     * time. The sweep matters because `favourites.code_id` is only stable within one data
     * release: after a re-copy, a saved id can point at a different code.
     */
    private suspend fun db(): SQLiteDatabase = withContext(dispatcher) {
        handle?.let { return@withContext it }
        synchronized(this@KbRepository) {
            handle ?: kb.open().also { openedDb ->
                handle = openedDb
                if (!swept) {
                    swept = true
                    // The saved list is keyed on codes.id, which a data release can renumber.
                    // This is the one moment stale rows have to go. FavouritesDao.sweepStaleIds.
                    FavouritesDao(openedDb).sweepStaleIds()
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

    suspend fun seriesOf(brandId: String): List<Series> = CatalogDao(db()).seriesOf(brandId)

    suspend fun codesOf(scope: ScopedSeries): List<CodeSummary> = CodeDao(db()).codesIn(scope)

    suspend fun searchCodes(scope: ScopedSeries, query: String): List<CodeSummary> =
        SearchDao(db()).searchCodes(scope, query)

    suspend fun meta(): KbMeta = CatalogDao(db()).meta()

    /**
     * One code, in one query, with its causes, fix steps and saved state.
     * Returns null when the uid is not in the database — a normal outcome, not an error.
     */
    suspend fun codeDetail(uid: String): CodeDetail? {
        val database = db()
        val codeId = CodeDao(database).idFor(uid) ?: return null
        return CodeDao(database).detail(uid, FavouritesDao(database).isFavourite(codeId))
    }

    // ── favourites ────────────────────────────────────────────────────────────

    suspend fun favouriteItems(): List<FavouriteItem> = FavouritesDao(db()).items()

    suspend fun setFavourite(codeId: Long, favourite: Boolean) {
        FavouritesDao(db()).set(codeId, favourite)
    }

    suspend fun isFavourite(codeId: Long): Boolean = FavouritesDao(db()).isFavourite(codeId)
}
