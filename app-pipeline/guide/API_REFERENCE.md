# API_REFERENCE.md — the app's entire "API"

**There is no network API. This file documents the in-process interface between the data layer
and the UI, so there is exactly one way to ask for each thing.**

If you are looking for an endpoint, a Retrofit interface, a base URL or a sync contract: there
are none, and adding any of them breaks RULE 14.

---

## 1. The repository surface

Everything the UI may do goes through `KbRepository`. Compose never sees a `Cursor`, never
touches SQLite, and never hard-codes a query.

```kotlin
interface KbRepository {

    // ---- content, read once and held in memory -------------------------
    suspend fun categories(): List<Category>              // 2 rows, with live counts
    suspend fun brands(category: CategoryId): List<Brand>
    suspend fun series(brandId: Long): List<Series>

    // ---- one series' codes --------------------------------------------
    suspend fun codes(seriesId: Long): List<CodeSummary>

    // ---- search, always scoped (RULE 3) -------------------------------
    suspend fun searchBrands(category: CategoryId, query: String): List<Brand>
    suspend fun searchSeries(brandId: Long, query: String): List<Series>
    suspend fun searchCodes(seriesId: Long, query: String): List<CodeSummary>
    suspend fun searchText(seriesId: Long, query: String): List<CodeHit>   // FTS, quoted

    // ---- one code, both languages, one query --------------------------
    suspend fun code(uid: String): CodeDetail?

    // ---- favourites: the only writes ----------------------------------
    // The shipped table is (code_id INTEGER PK, created_at TEXT). No uid, no is_read.
    fun observeFavourites(): Flow<List<FavouriteRow>>            // (codeId, createdAt)
    suspend fun setFavourite(codeId: Long, favourite: Boolean)
    suspend fun savedCodeIds(): List<Long>                        // for the staleness sweep

    // ---- meta ----------------------------------------------------------
    suspend fun meta(): KbMeta                                   // kbVersion, counts
}
```

The scoping is enforced **in the signatures**: `searchCodes` and `searchText` take a
`seriesId`, so it is not possible to write a global code search by accident. That is the
mechanism behind RULE 3, not a convention.

## 2. Value types

```kotlin
@Immutable data class Category(val id: CategoryId, val title: String,
                               val brandCount: Int, val codeCount: Int)
@Immutable data class CodeHit(val summary: CodeSummary, val snippet: String)
@Immutable data class FavouriteRow(val codeId: Long, val createdAt: String)
@Immutable data class KbMeta(val kbVersion: String, val builtAt: String,
                             val brandCount: Int, val seriesCount: Int, val codeCount: Int)
enum class CategoryId { AC, INVERTER }
```

`CodeSummary` and `CodeDetail` are defined in `DATA_SCHEMA.md` §3.

## 3. Behaviour contract

| Call | Guarantee |
|---|---|
| `categories()` | counts are **live from the database**, never hard-coded. Re-read after the database is (re)opened |
| `brands()` | includes brands with `code_count = 0` (8 of them). Sorted by `code_count` desc, then name. The caller decides whether to mute them |
| `series()` | sorted by `code_count` desc, then name. Includes the 8 empty series |
| `codes()` | one query, in memory, ≤106 rows. Use `display` as-is |
| `searchCodes()` | **aliases table only.** Exact then prefix. Never FTS, never another series. Empty list is a normal result, not an error |
| `searchText()` | FTS, wrapped in double quotes, series-scoped. Snippet marked with `«»`, 12-token radius |
| `code()` | `null` only if the uid is not in the database. All blocks may be empty; the UI hides them (`PHASE_4` §4) |
| `setFavourite()` | idempotent, keyed on `code_id`, optimistic, single transaction |
| `observeFavourites()` | emits on every write; newest first. Brand, series and title come from the join — the row has none of them |
| `savedCodeIds()` | run the staleness sweep on every database (re)copy; `codes.id` can shift between data releases |

## 4. Error handling

**No call throws for "not found".** Empty list or `null` is the answer, and every one of them
is a designed state: no brand matches, a brand with no codes, a code with no solutions.

Genuine failures — the asset missing, the cache file corrupt after one retry — are surfaced
once as a `KbState.Error` and rendered as a single line with a **Try again** action. Do not
wrap every call in try/catch; do not invent a loading state for a 0.15 ms lookup.

## 5. What does not exist

- No `suspend fun sync()`, no `refresh()`, no `updateData()` — there is nothing to sync
  (RULE 15).
- No `http`, no `baseUrl`, no `apiKey`, no `Result<T>` wrapper around network calls.
- No pagination API. 106 rows is the largest list in the app; it is loaded whole.
- No "recent searches" table. Search history is not needed for 4 taps to any code, and it
  would be another write.
- No `settings` API beyond the two values in `PHASE_7` (language, theme).

## 6. If you need a new question

Ask the database, not the network. If the answer cannot be produced by one of the eight
read-only tables (`DATA_SCHEMA.md` §2), either the question is not worth asking, or the
knowledge base is missing something that belongs in `data/`, not in the app.
