# PHASE_2_DATA_LAYER.md — reading the database

**Goal: every screen can read real data, in a coroutine, off the main thread, with no
flicker.**

---

## 1. No ORM — and that is a decision, not a shortcut

4418 rows, 9 tables, read-only. There are no writes to migrate, no relations to cache and no
schema to evolve on-device, so an ORM would add a dependency, a codegen step and a Room
migration story for zero benefit. Use `SupportSQLiteOpenHelper` / a small DAO over raw
`SQLiteDatabase`, with the SQL written out in `DATA_SCHEMA.md`.

Hard rule: **no `suspend fun` that touches the database may run on the main dispatcher.**

## 2. Layering

```
data/
  KbDatabase.kt        open asset → copy to cache → OPEN_READONLY
  DbCopy.kt            copy + version check + corruption fallback
  normalise.kt         query normalisation (DATA_SCHEMA.md §4)
  dao/BrandDao.kt      brands, series, meta
  dao/CodeDao.kt       code list, detail, causes, solutions
  dao/SearchDao.kt     aliases (exact) + code_fts (free text)  ← 2 jobs
  dao/FavouritesDao.kt the only writable table
model/                @Immutable data classes (DATA_SCHEMA.md §3)
repo/KbRepository.kt   dispatchers, caching, language resolution
ui/                   state + screens
```

Each DAO method is `suspend` and switches to `Dispatchers.IO` internally. `KbRepository`
exposes flows/state holders; Compose never sees a `Cursor`.

## 3. Opening the database safely

1. If `File(cacheDir, "kb.sqlite")` exists and its `meta.db_version` matches the asset's,
   open it. 2. Otherwise copy the asset over it. 3. Then
   `SQLiteDatabase.openDatabase(path, null, OPEN_READONLY)`.
4. If opening throws `SQLiteDatabaseCorruptException` (truncated copy, killed mid-write,
   full disk), delete the cached file, copy again, retry **once**, then surface a plain error
   state. Never loop.
5. Keep the helper open for the process lifetime; close it only in tests.

Never open the database on the main thread, even to read `meta`. Open it lazily inside a
`StateFlow` and expose "ready" so the first frame shows the real data, not a spinner.

## 4. The queries you need

```sql
-- categories, with live counts
SELECT b.uid, b.name, b.unit_type,
       COUNT(DISTINCT s.id) AS series_count,
       COUNT(c.id)         AS code_count
FROM brands b
LEFT JOIN series s ON s.brand_id = b.id
LEFT JOIN codes  c ON c.series_id = s.id
GROUP BY b.id ORDER BY b.code_count DESC, b.name COLLATE NOCASE;

-- brands in a category (categorise on unit_type in Kotlin; it is small and stable)
SELECT b.id, b.uid, b.name, b.code_count, b.notes_en
FROM brands b WHERE b.code_count > 0 OR :showEmpty = 1
ORDER BY b.code_count DESC, b.name COLLATE NOCASE;

-- series of a brand
SELECT s.id, s.uid, s.name, s.code_count, s.notes_en
FROM series s WHERE s.brand_id = ?
ORDER BY s.code_count DESC, s.name COLLATE NOCASE;

-- codes of a series
SELECT c.id, c.uid, c.code, c.display, c.severity, c.is_fault,
       c.display_style, c.title_en, c.title_ur
FROM codes c WHERE c.series_id = ?
ORDER BY c.code COLLATE NOCASE;

-- ONE query for detail, both languages, joined (never a second query on toggle)
SELECT c.id, c.uid, c.code, c.display, c.title_en, c.title_ur,
       c.meaning_en, c.meaning_ur, c.notes_en, c.notes_ur,
       c.severity, c.is_fault, c.confidence, c.source_type,
       c.source_ref, c.source_url, c.display_style
FROM codes c WHERE c.uid = ?;
```

`code_fts` is a contentless external FTS table — a `LIKE '%x%'` against it returns nothing, so
free-text search **must** go through `MATCH` (see `PHASE_5_SEARCH.md`).

## 5. Loading strategies

| Data | When | How |
|---|---|---|
| brand + series lists (382 rows total) | on first open, in the background | load once, hold in memory, filter in Kotlin. Smaller than the query overhead of paging. |
| code list of a series (≤106 rows) | when a series is opened | one query, in memory |
| code detail (≤30 rows across causes+solutions) | when a code is opened | one joined query, in memory |
| favourites | at launch + on change | one query, refreshed on write |

Paging is not needed. Add it only if a measurement says so, and add it in Phase 9.

## 6. Language resolution

Load `*_en` and `*_ur` in the same query; pick one in the ViewModel from a single
`contentLanguage` flow. Never re-query on toggle — toggling must be instant and offline
(RULE 13). The toggle affects title, meaning, notes, causes, solutions and the severity /
confidence words; brand names, series names and all UI labels stay English.

## 7. Favourites

`code_uid` is the primary key, not `code_id` — a new app version can renumber ids, and a
saved list must survive the database swap. One `upsert` per star tap, wrapped in a
transaction, plus a `Flow` refresh. `is_read` is set when a saved code is opened.

## 8. Failure behaviour

| Failure | Behaviour |
|---|---|
| cache file corrupt | delete, re-copy, retry once, then error state |
| asset missing (bad build) | crash in `BuildConfig` init, loudly, at launch — never a silent empty app |
| query returns 0 | normal empty state (`RULES.md` RULE 17) — 8 brands and 8 series really are empty |

## 9. Checks

- [ ] No database call on the main thread (assert with StrictMode in debug)
- [ ] Cold start → home shows real counts from the database
- [ ] Toggle EN/UR re-renders instantly with **no** new query
- [ ] Corrupt the cache file on purpose → app recovers or shows a clear error
- [ ] Star a code, force-stop, relaunch → still saved
- [ ] Force-stop, relaunch → still saved **after the database is re-copied**
- [ ] `./gradlew test` green
