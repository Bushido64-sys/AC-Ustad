# DATA_SCHEMA.md — the 9 tables, and how the app reads them

The database is finished and shipped. **You never write to it.** This file explains what
each column means so you display it correctly, and gives the exact SQL for every screen.

**Totals: 62 brands · 320 series · 4418 codes · 10450 causes · 11776 solutions · 7707
aliases · 4124 search keys · 3849 fault rows (87%) · 569 non-fault rows (13%)**

---

## 1. Why you cannot key on the code string

| Code string | brands | series entries | reality |
|---|---|---|---|
| `E1` | 20 | 30 | 20 different meanings |
| `E3` | 21 | 34 | 21 different meanings |
| `E6` | 16 | 21 | 16 different meanings |
| `F4` | 15 | 16 | 15 different meanings |

**1715 of 2139 distinct code strings exist on exactly one brand** (80%). That is what makes a
narrow, certain path better than a search engine: usually the code *is* unambiguous — you just
have to be in the right place. A global search would drown the 20% in 20 wrong answers.

## 2. The nine tables

### `brands` — 62 rows
| column | note |
|---|---|
| `id` | primary key |
| `uid` | stable slug, e.g. `growatt` |
| `name` | **display name — always English, never translated** |
| `unit_type` | what the brand makes: `split` 62, `hybrid_inverter` 109, `on_grid_inverter` 47, `generic` 36, `ups` 35, `off_grid_inverter` 14, `ducted` 7, `floor_standing` 5, `cassette` 3, `window` 2 (a brand can have several) |
| `code_count` | denormalised count — use it to grey out the 8 brands with 0 codes, do not `COUNT(*)` |
| `notes_en` | short editorial note, English, shown expanded on the brand screen |

### `series` — 320 rows
| column | note |
|---|---|
| `id`, `brand_id` | `brand_id` → `brands.id` |
| `uid` | e.g. `growatt-mod-tl3x` |
| `name` | **model line, always English** |
| `notes_en` | when/where the manual came from — the only place a series note appears |
| `code_count` | 8 series are intentionally 0 |

Codes per series: median **9**, p90 36, max 106. Sort by `code_count` **descending** on the
brand screen — the biggest model lines are the common ones, and it saves scrolling.

### `codes` — 4418 rows
| column | note |
|---|---|
| `id` | primary key; also the FTS rowid |
| `series_id` | → `series.id` |
| `uid` | **`brand/series/code`** — the only globally unique identity |
| `code` | the display string, exactly as the unit shows it |
| `display` | `code · mean title` pre-joined for list rows — use this, do not re-join by hand |
| `title_en`, `title_ur` | short headline, e.g. "Compressor Drive IPM Failure" / "Compressor IPM fault" |
| `meaning_en`, `meaning_ur` | 1–2 short sentences, median 85 / max 359 chars |
| `severity` | `stop_pro` 2321 · `check_restart` 1226 · `danger` 361 · `info` 357 · `self_clear` 153 |
| `is_fault` | 1 = fault, 0 = indicator/parameter/self-clear row |
| `confidence` | `high` 3565 · `medium` 718 · `low` 135 |
| `source_type` | `service_manual` 2083 · `user_manual` 1292 · `official_support` 397 · `other` 249 · `technician_note` 155 · `video` 102 · `retailer_page` 61 · `distributor_page` 60 · `forum` 19 |
| `source_ref`, `source_url` | **26 codes have no `source_url`** — render the source line without a link, never a dead button |
| `notes_en`, `notes_ur` | present on 1951 codes |

`display` for display styles: indoor 2510, controller 413, led_blink 398, remote 315, outdoor 315, unknown 31. Treat `unknown` as the default look — it is a styling hint, not a data problem.

### `causes` — 10450 rows (`code_id` → `codes.id`, `cause_en`, `cause_ur`, `ord`)
Fault rows average **2.47 causes** each. Render as a numbered list, or as "Possible causes".

### `solutions` — 11776 rows (`code_id`, `step_no`, `text_en`, `text_ur`)
Fault rows average **2.76 steps**. `step_no` starts at 1 and is contiguous. Render the
**numbers** — "step 2" is what a technician says on the phone. Median 88 chars, max 292.

### `aliases` — 7707 rows — **your search path**
| column | note |
|---|---|
| `code_id` | → `codes.id` |
| `alias_norm` | normalised, deduped search key |
| `kind` | `exact` 4713 · `alt_spelling` 1320 · `substring` 950 · `display` 723 |

Median 1 alias per code, max 37. `exact` = the literal code string, normal. `alt_spelling` =
`Er 2`, `ER02`, `ER2`, `E2` — what a manual prints. `substring` = the long descriptor that
contains the code (`BLINK-RUNNING`, `LED1 x1 blink; LED2 off`) — **these are the strings that
crash FTS if used raw.** `display` = a spaced-out or prefixed form.

### `code_fts` — virtual table
External-content FTS5 over `title`, `meaning`, `solution_text`; `rowid = codes.id`.
**FTS is for full-text description search only — never for exact code lookup.**

### `favourites` — 0 rows shipped
| column | note |
|---|---|
| `code_uid` | TEXT PK, e.g. `growatt/growatt-mod-tl3x/Error 200` — **stores `uid`, not `code_id`** |
| `brand_name`, `series_name`, `code`, `title_en`, `title_ur` | copied so the list renders with zero queries |
| `is_read` | 0/1 — drives the unread dot in the Saved tab |
| `created_at` | unix seconds |

Design note: the favourite row is self-contained. Adding a new app version (new database)
cannot break the saved list. Writes only here.

### `meta`
`schema_version`, `generated_at`, `db_version`, `counts_json`. Show the version in Settings.

---

## 3. Kotlin model classes

```kotlin
@Immutable data class Brand(
    val id: Long, val uid: String, val name: String,
    val unitType: String?, val codeCount: Int, val notesEn: String?
)
@Immutable data class Series(
    val id: Long, val brandId: Long, val uid: String, val name: String,
    val codeCount: Int, val notesEn: String?
)
@Immutable data class CodeSummary(
    val id: Long, val uid: String, val code: String, val display: String,
    val titleEn: String?, val titleUr: String?,
    val severity: String, val isFault: Boolean, val displayStyle: String
)
@Immutable data class CodeDetail(
    val summary: CodeSummary, val meaningEn: String?, val meaningUr: String?,
    val notesEn: String?, val notesUr: String?,
    val confidence: String, val sourceType: String?,
    val sourceRef: String?, val sourceUrl: String?,
    val causes: List<Pair<String?, String?>>,   // (en, ur)
    val solutions: List<Pair<Int, String?>>,    // (step_no, text for current language)
    val isFavourite: Boolean, val isRead: Boolean
)
```

Load both languages in **one** query, resolve to one in the ViewModel. Never re-query on toggle.

## 4. The 2-job search rule

| User is looking for | Table | Column | Why |
|---|---|---|---|
| an **exact code** (`E1`, `Error 200`, `BLINK-RUNNING`) | `aliases` | `alias_norm` | 0.2 ms, always correct, never crashes |
| a **description** ("compressor", "over temperature", "high pressure") | `code_fts` | `MATCH` | free text only |

Full algorithm, escaping rules and the SQL for each are in `PHASE_5_SEARCH.md` §2.

`normalise()` — lower-case, collapse whitespace, keep `A-Z 0-9 + - . / space`, trim. Applied
to the query and to nothing else (the stored `alias_norm` is already normalised).

## 5. Read-only access

```kotlin
val db = context.assets.open("db/kb.sqlite").use { input ->
    val tmp = File(context.cacheDir, "kb.sqlite"); tmp.outputStream().use { input.copyTo(it) }
    tmp
}
SQLiteDatabase.openDatabase(db.path, null, SQLiteDatabase.OPEN_READONLY)
```

Read-only is deliberate (RULE 5). Do not add WAL, triggers or migrations to the shipped copy.

## 6. The six data cases the UI must handle

| Case | Where | Required behaviour |
|---|---|---|
| Brand with **0 codes** | 8 brands | Greyed row, tap shows an empty state, never a blank list or a crash |
| Code with **no solutions** | `panasonic` `H00` (the single non-fault row with no causes) | Show meaning; hide the solutions block; do not invent a step |
| Code with **no `source_url`** | 26 codes | Source line without a link, no disabled button |
| Very long note | max 725 chars, p90 202 | Collapsed by default, expand control, full text when open |
| Two-line code | `Error 200` + title | Row must be two lines, 56dp min, code in IBM Plex Mono |
| `severity = stop_pro` | 2321 rows (60% of faults) | Must be visually distinct from the other four without looking alarming |

## 7. The FTS escaping rule — read this twice

```kotlin
fun ftsQuery(raw: String): String = "\"" + raw.replace("\"", "\"\"") + "\""
```

Unquoted input makes SQLite read words as column names. Verified: raw input crashes **454**
of the 2139 code strings; quoted input returns all 2139. Examples that crash raw:
`BLINK-RUNNING` → `no such column: RUNNING`; anything containing `;` or `+` → syntax error.
See `RULES.md` RULE 4.
