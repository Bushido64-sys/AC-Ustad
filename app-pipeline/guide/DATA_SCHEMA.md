# DATA_SCHEMA.md — the 8 tables + FTS, exactly as they are

The database is finished and shipped. **You never write to it except `favourites`.**

> **This file was rewritten from `PRAGMA table_info` on the shipped database.** An earlier
> version of this guide documented columns that do not exist (`brands.uid`,
> `brands.unit_type`, `codes.display_style`, `aliases.kind`, the `favourites` columns) and
> told you to lower-case a search query — which would have returned **zero results for every
> code**. The app's `SchemaContractTest.kt` now pins the schema and is the authority if the
> two ever disagree.

**Totals: 62 brands · 320 series · 4,418 codes · 10,450 causes · 11,776 solutions ·
7,707 aliases (4,015 distinct keys) · 3,849 fault rows (87%) · 569 indicator rows (13%)**

---

## 1. Why you cannot key on the code string

| Code string | brands | reality |
|---|---|---|
| `E1` | 20 | 20 different meanings |
| `E3` | 21 | 21 different meanings |
| `E6` | 16 | 16 different meanings |
| `F4` | 15 | 15 different meanings |

**1,715 of 2,139 distinct code strings (80%) exist on exactly one brand.** That is why a
narrow, certain path beats a search engine: usually the code *is* unambiguous, you just have
to be in the right place.

## 2. The tables

### `brands` — 62 rows
| column | type | note |
|---|---|---|
| `id` | TEXT **PK** | the slug itself, e.g. `growatt`. **There is no `uid` column and `id` is not an integer** |
| `name` | TEXT | display name — **always English, never translated** |
| `categories` | TEXT | JSON array string: `'["ac"]'` 29, `'["inverter"]'` 31, `'["ac","inverter"]'` 2 |
| `country`, `website` | TEXT | provenance |
| `notes` | TEXT | editorial note (median 485, max 1,099 chars) — shown collapsed on the brand screen |
| `series_count` | INTEGER | denormalised |
| `code_count` | INTEGER | denormalised. **`SUM(code_count) = 4,418`** — trust it, never `COUNT(*)` |
| `source_notes` | TEXT | how this brand was researched |

There is **no `brands.unit_type`** — `unit_type` is on `series`, and a brand can hold
several. Because 2 brands sit in both categories, per-category brand counts sum to **64**,
not 62. That is the truth about the data, not a bug.

### `series` — 320 rows
| column | type | note |
|---|---|---|
| `uid` | TEXT **PK** | `growatt/growatt-mod-tl3x` |
| `id` | TEXT | bare slug `growatt-mod-tl3x` — **NOT unique on its own** (see the trap below) |
| `brand_id` | TEXT | → `brands.id` |
| `name` | TEXT | model line, **always English** |
| `category` | TEXT | single clean value: `'ac'` 99, `'inverter'` 221 |
| `unit_type` | TEXT | `hybrid_inverter` 109, `split` 62, `on_grid_inverter` 47, `generic` 36, `ups` 35, `off_grid_inverter` 14, `ducted` 7, `floor_standing` 5, `cassette` 3, `window` 2 |
| `model_patterns` | TEXT | JSON array of globs, e.g. `'["*HSU*","*SPLIT*"]'` |
| `notes` | TEXT | **long**: median 269, p90 702, max 1,450 chars. Always collapsed |
| `code_count` | INTEGER | denormalised; 65 series are 0 |
| `source_file` | TEXT | provenance |

**⚠ THE TRAP:** `series.id` is unique only *within* a brand — 6 values repeat, one of them
**14 times** (`inverter-split`). Therefore:

```sql
-- WRONG: returns 7,036 rows, not 4,418
SELECT COUNT(*) FROM codes co JOIN series s ON s.id = co.series_id;
-- RIGHT
SELECT COUNT(*) FROM codes co JOIN series s ON s.id = co.series_id AND s.brand_id = co.brand_id;
-- BEST: no join at all
SELECT SUM(code_count) FROM series;   -- 4418
```

Codes per series: median 9, p90 36, max 106. Sort brand screens by `code_count` **desc**.

### `codes` — 4,418 rows
| column | type | note |
|---|---|---|
| `id` | INTEGER **PK** | also the FTS rowid |
| `uid` | TEXT | **`brand/series/code`**, e.g. `growatt/growatt-mod-tl3x/E6`. The only globally unique identity |
| `brand_id` | TEXT | → `brands.id` (present directly, so a brand's code list needs no join) |
| `series_id` | TEXT | → `series.id` (bare slug) |
| `category`, `unit_type` | TEXT | denormalised from the series |
| `code` | TEXT | the display string, exactly as the unit shows it |
| `code_norm` | TEXT | canonical form, indexed (`idx_codes_norm`) |
| `title_en`, `title_ur` | TEXT | headline. median 32 / max 60 chars |
| `meaning_en`, `meaning_ur` | TEXT | 1–2 sentences. en median 82 / max 249 · ur median 85 / max 359 |
| `severity` | TEXT | `stop_pro` 2321 · `check_restart` 1226 · `danger` 361 · `info` 357 · `self_clear` 153 |
| **`display`** | TEXT | **a display-STYLE hint, NOT a pre-joined title**: `controller` 2510, `indoor` 751, `led_blink` 413, `remote` 398, `outdoor` 315, `unknown` 31. **There is no pre-joined `code · title` string — list rows must select `code`, `title_en` and `title_ur` separately** |
| `is_fault` | INTEGER | 1 = fault (3,849), 0 = indicator/parameter (569) |
| `confidence` | TEXT | `high` 3565 · `medium` 718 · `low` 135 |
| `source_type` | TEXT | `service_manual` 2083 · `user_manual` 1292 · `official_support` 397 · `other` 249 · `technician_note` 155 · `video` 102 · `retailer_page` 61 · `distributor_page` 60 · `forum` 19 |
| `source_title` | TEXT | the human title of the source (median 76 chars). **There is no `source_ref`** |
| `source_url` | TEXT | **26 codes have none** — render no link, never a dead button |
| `source_retrieved` | TEXT | date |
| `blink_pattern` | TEXT | on 894 codes |
| `related_codes` | TEXT | on 370 codes |
| `notes_en`, `notes_ur` | TEXT | on 1,951 codes. median 104 / p90 202 / max 725 |

No critical column is null or empty: `code`, `title_en`, `meaning_en`, `severity`,
`is_fault`, `display`, `uid` are all populated on all 4,418 rows.

### `causes` — 10,450 rows
| column | type |
|---|---|
| `code_id` | INTEGER (composite PK) → `codes.id` |
| `idx` | INTEGER (composite PK), 1-based |
| `en`, `ur` | TEXT |

**Not** `cause_en` / `cause_ur` / `ord`. Text: en median 51 / max 279.
Every **fault** row has ≥2 causes (2: 2,135 · 3: 1,611 · 4: 101 · 5: 2), average **2.47**.

### `solutions` — 11,776 rows
| column | type |
|---|---|
| `code_id` | INTEGER (composite PK) |
| `idx` | INTEGER (composite PK), 1-based and contiguous |
| `en`, `ur` | TEXT |

**Not** `step_no` / `text_en` / `text_ur`. Text: en median 83 / p90 123 / max 270.
Every **fault** row has ≥2 steps (2: 1,316 · 3: 2,199 · 4: 294 · 5: 38 · 6: 2), average **2.76**.

`en` and `ur` are **never equal** — 0 violations in causes, solutions, titles, meanings and
notes. That is enforced by the data validator and is what makes the Urdu real.

### `aliases` — 7,707 rows (4,015 distinct keys)
| column | type | note |
|---|---|---|
| `code_id` | INTEGER | → `codes.id` |
| `alias` | TEXT | as written, e.g. `High Temperature` |
| `alias_norm` | TEXT | **the canonical search key** — see §3 |

**There is no `kind` column.** Median 1 alias per code, max 37 (`SYSTEM_FAULT_RANGES` on
Sungrow). The same key appears many times because it is a multi-brand index.

### `favourites` — 0 rows shipped
```sql
CREATE TABLE favourites (
  code_id    INTEGER PRIMARY KEY REFERENCES codes(id) ON DELETE CASCADE,
  created_at TEXT NOT NULL
)
```

**Keyed on `code_id`, not on a uid.** There are no `is_read`, `brand_name`, `series_name`,
`title_en` or `title_ur` columns — do not write queries that assume them, and do not display
a saved row without joining back to `codes`.

Consequences, and they are real:

- Across a data release `codes.id` can shift, so a saved `code_id` can point at a different
  code. **On every database re-copy, re-check each saved `code_id` still exists and still has
  the expected `uid`, and drop the rows that do not.** That is the whole "migration".
- To render a saved row without a query per row, hold the joined values in memory in the
  ViewModel. If you later want them stored, add a **second, app-owned table in the cache
  copy** — never `ALTER` the shipped table.
- `created_at` is **TEXT**, not an integer.
- SQLite does not enforce foreign keys unless you switch them on. Leave them off, or a
  favourite for a code that no longer exists will throw instead of being cleaned up.

### `meta` — 8 rows
`key` TEXT PK, `value` TEXT. Actual contents: `kb_version` (`2026-09-26`), `built_at`,
`schema_version` (`1`), `brands` (62), `series` (320), `codes` (4,418), `search_keys`
(4,124 — note this is *not* the number of distinct `alias_norm` values, which is 4,015), `data_license_note`.

**The version key is `meta.kb_version`, not `meta.db_version`.** Show it in Settings so a
support report can be dated.

### `code_fts` — FTS5
```sql
CREATE VIRTUAL TABLE code_fts USING fts5(
  code_norm, aliases, titles, tokenize = 'unicode61')
```

Three columns — **`code_norm`, `aliases`, `titles`** — and `rowid = codes.id`. This is not the
`title / meaning / solution_text` triple an earlier version of this guide described. `titles` is
the English and Roman Urdu titles concatenated and upper-cased (`"INDOOR FAN MOTOR OR PCB ERROR
INDOOR FAN MOTOR YA PCB KHARABI"`), which is why a Roman Urdu word can be found. Its shadow
tables (`code_fts_config`, `_content`, `_data`, `_docsize`, `_idx`) are implementation detail —
never query them.

It is contentless, so `LIKE '%x%'` against it returns nothing; free-text search **must** use
`MATCH`. A `snippet()` of `titles` is a duplicated uppercase blob and reads as noise, so the app
selects no snippet — the code list already shows the real title on every row.

**Quote each term and AND them; do not wrap the whole query as one phrase.** A single quoted
string is an FTS5 *phrase*, so `"inverter fault"` matches only those words adjacent in that order.
Measured on the shipped database: phrase 10 hits, AND 89. See §8.

## 3. The canonical form — get this exactly right

`alias_norm` is **UPPER-CASED**. 5,562 of its 7,707 rows contain capitals. A rule that
lower-cases the query returns **0 rows** for `e1`, `e6`, `f4` — i.e. for most of what a
technician actually types.

The rule that reproduces the stored column for **all 4,124 distinct `alias`/`alias_norm` pairs**
(the 4,015 distinct `alias_norm` values), and
resolves **all 2,139 distinct code strings**:

```kotlin
fun canon(raw: String): String = raw
    .uppercase()                              // UPPER, not lower
    .replace(Regex("[^A-Z0-9_./\\-]+"), " ")  // keep _ . / - , everything else -> one space
    .replace(Regex("\\s+"), " ")              // collapse spaces
    .trim()
```

| typed | stored `alias_norm` | canon() |
|---|---|---|
| `e1` | `E1` | `E1` |
| `High Temperature` | `HIGH TEMP` | `HIGH TEMP` |
| `ISO_FAIL` | `ISO_FAIL` | `ISO_FAIL` |
| `BEEP-2.5SEC` | `BEEP-2.5SEC` | `BEEP-2.5SEC` |
| `LED1 x1 blink; LED2 off; LED3 off` | `LED1 X1 BLINK LED2 OFF LED3 OFF` | same |
| `GRID-INTF. (1030 DATA:0000)` | `GRID-INTF. 1030 DATA 0000` | same |

Measured: canonical exact lookup **0.15 ms** (uses `idx_alias_norm`) versus **3.6 ms** for
the `LOWER()` version, which also cannot use the index. Correct is *faster* here.

Applied to the **user's query only** — the stored values are already canonical.

## 4. The queries you need

```sql
-- home screen counts (verified: 31 / 33 brands, 1723 / 2695 codes = 4418)
SELECT
  (SELECT COUNT(*) FROM brands WHERE categories LIKE '%"ac"%'),
  (SELECT COUNT(*) FROM brands WHERE categories LIKE '%"inverter"%'),
  (SELECT IFNULL(SUM(code_count),0) FROM series WHERE category='ac'),
  (SELECT IFNULL(SUM(code_count),0) FROM series WHERE category='inverter');

-- model lines of a brand
SELECT id, uid, name, code_count, notes FROM series
 WHERE brand_id = ? ORDER BY code_count DESC, name COLLATE NOCASE;

-- codes of ONE model line  (scoped search - series_id is NOT unique on its own)
SELECT id, uid, code, code_norm, title_en, title_ur, severity, is_fault, display
  FROM codes WHERE series_id = ? AND brand_id = ?
 ORDER BY code COLLATE NOCASE;

-- exact code, scoped  (0.15 ms)
SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.is_fault, c.display
  FROM aliases a JOIN codes c ON c.id = a.code_id
 WHERE a.alias_norm = :canon AND c.series_id = :seriesId AND c.brand_id = :brandId;

-- one code, BOTH languages, ONE query
SELECT * FROM codes WHERE uid = ?;
```

Free text, quoted, series-scoped — note `bm25` ascending means **better first**:

```sql
SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.display,
       snippet(code_fts, 2, '«', '»', '…', 12) AS hit
  FROM code_fts JOIN codes c ON c.id = code_fts.rowid
 WHERE code_fts MATCH :quoted AND c.series_id = :seriesId AND c.brand_id = :brandId
 ORDER BY bm25(code_fts, 10.0, 1.0, 3.0) LIMIT 60;
```

## 5. Kotlin model classes

```kotlin
@Immutable data class Brand(
    val id: String, val name: String, val categories: List<String>,
    val country: String?, val website: String?, val notes: String?,
    val seriesCount: Int, val codeCount: Int
)
@Immutable data class Series(
    val uid: String, val id: String, val brandId: String, val name: String,
    val category: String, val unitType: String?, val notes: String?, val codeCount: Int
)
@Immutable data class CodeSummary(
    val id: Long, val uid: String, val code: String,
    val titleEn: String?, val titleUr: String?,
    val severity: String, val isFault: Boolean, val display: String
)
@Immutable data class CodeDetail(
    val summary: CodeSummary, val meaningEn: String?, val meaningUr: String?,
    val notesEn: String?, val notesUr: String?, val confidence: String,
    val sourceType: String?, val sourceTitle: String?, val sourceUrl: String?,
    val causes: List<Pair<String?, String?>>,      // (en, ur) in idx order
    val solutions: List<Pair<Int, String?>>,      // (idx, text for current language)
    val isFavourite: Boolean
)
@Immutable data class Favourite(val codeId: Long, val createdAt: String)
@Immutable data class KbMeta(val kbVersion: String, val builtAt: String)
```

Load both languages in **one** query and resolve in the ViewModel. Never re-query on toggle.

## 6. Read-only access

```kotlin
val asset = context.assets.open("db/kb.sqlite")
val file = File(context.cacheDir, "kb.sqlite")     // copy once, repair once
SQLiteDatabase.openDatabase(file.path, null, SQLiteDatabase.OPEN_READONLY)
```

## 7. The six data cases the UI must handle

| Case | Where | Required behaviour |
|---|---|---|
| Brand with **0 codes** | 8 brands | muted row, real empty state — never a blank list |
| Series with **0 codes** | **65** series | same. More than one in ten |
| Code with no solutions | `panasonic/panasonic-hf-self-diagnosis/H00` (the single code with no causes, and it is `is_fault = 0`) | hide the block; no gap, no placeholder |
| No `source_url` | 26 codes | source line without a link, no disabled button |
| Very long note | max 725 chars; series notes max 1,450 | collapsed by default |
| Two-line code | `Error 200` + title, or a 40-char blink descriptor | row must wrap, 56dp minimum |

## 8. The FTS escaping rule

Two separate requirements, and the second one is easy to miss.

**1. Quote every term.** Unquoted input makes SQLite read words as **column names**: measured on
the shipped database, raw input throws for **454 of 2,139** code strings, while quoted input
returns results for all 2,139. `BLINK-RUNNING` → `no such column: RUNNING`; `;` and `+` → syntax
error.

**2. AND the terms; do not emit one quoted phrase.** A single `"over current"` is a phrase
query — the words must be adjacent and in that order. A technician types words, not phrases, so
this silently hides answers. Measured:

| input | one quoted phrase | `"a" AND "b"` |
|---|---|---|
| `inverter fault` | 10 | **89** |
| `high temperature` | 32 | **67** |
| `over current` | 110 | **133** |

```kotlin
fun ftsQuery(raw: String) = raw
    .split(' ', '\t', '\n')
    .map { it.trim() }
    .filter { it.isNotEmpty() }
    .joinToString(" AND ") { "\"" + it.replace("\"", "\"\"") + "\"" }
```
