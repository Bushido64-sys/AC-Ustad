# PHASE_5_SEARCH.md — scoped search, and the crash you must not ship

**Goal: on every screen, search finds the children of that screen and nothing else. No
crashes, ever, on any of the 2139 code strings.**

---

## 1. The scope rule (RULE 3)

| Screen | Searches | Column | Never searches |
|---|---|---|---|
| Brands | brand names | `brands.name` | codes, series |
| Model lines | model names | `series.name` | codes, brands |
| Codes in a model | that series' codes | `codes.code`, `aliases` | other series, other brands |
| Free text in a model | that series' descriptions | `code_fts` | other series, other brands |

**There is no global code search, and you must not add one.** `E1` is on 20 brands, `E3` on
21, `E6` on 16, `F4` on 15. A global search would answer "E6" with 16 different meanings and
force the technician to work out which brand they are standing in front of. Inside a series,
`E6` has exactly one meaning — and it is the right one.

A code search only ever runs with a `series_id` bound. Enforce it in the DAO signature, not in
the call site: `searchCodes(seriesId: Long, query: String)`.

## 2. The two-job rule

| Query looks like | Route | SQL |
|---|---|---|
| `E1`, `Error 200`, `Er02`, `BLINK-RUNNING`, `P003` | **aliases** | see below |
| `compressor`, `over temperature`, `high pressure`, `phase` | **code_fts** | `MATCH '…'` |

**Rule:** if the normalised query contains no space and is short (≤ 24 chars) and matches
something in `aliases`, that is the answer. Only fall through to FTS when aliases return
nothing. Never send a code-looking string to FTS.

### Exact / prefix code lookup — the safe path

```sql
SELECT c.id, c.uid, c.code, c.display, c.severity, c.is_fault, c.display_style,
       c.title_en, c.title_ur
FROM aliases a JOIN codes c ON c.id = a.code_id
WHERE a.alias_norm = :q            AND c.series_id = :seriesId   -- exact
UNION ALL
SELECT c.id, c.uid, c.code, c.display, c.severity, c.is_fault, c.display_style,
       c.title_en, c.title_ur
FROM aliases a JOIN codes c ON c.id = a.code_id
WHERE a.alias_norm LIKE :q || '%'  AND c.series_id = :seriesId   -- prefix
GROUP BY c.id;
```

`aliases` is 7707 rows and is **indexed** — measured: exact 0.2 ms, prefix 2 ms. No FTS
involvement, no parsing, no escaping, no crash surface.

```kotlin
fun normalise(s: String) = s.trim().lowercase()
    .replace(Regex("[^a-z0-9 +./-]"), "")
    .replace(Regex("\\s+"), " ")
```
Applied to the **query only** — the stored `alias_norm` is already normalised.

## 3. The FTS escaping rule — the crash

`code_fts` is an external-content FTS5 table over `title`, `meaning`, `solution_text`, with
`rowid = codes.id`. Passing raw user input to `MATCH` makes SQLite parse the words as **column
names**.

```
"BLINK-RUNNING"    -> no such column: RUNNING
"LED1 x1 blink; ..." -> syntax error near ";"
"ID+F101"          -> syntax error near "+"
```

Measured on the shipped database: **454 of the 2139 code strings throw when passed raw; all
2139 return results when quoted.**

```kotlin
fun ftsQuery(raw: String) = "\"" + raw.replace("\"", "\"\"") + "\""

@Query("SELECT c.id, c.uid, c.code, c.display, c.severity, c.is_fault, c.display_style, " +
       "       c.title_en, c.title_ur, " +
       "       snippet(code_fts, 2, '«', '»', '…', 12) AS hit " +
       "FROM code_fts JOIN codes c ON c.id = code_fts.rowid " +
       "WHERE code_fts MATCH :q AND c.series_id = :seriesId " +
       "ORDER BY bm25(code_fts, 10.0, 1.0, 3.0) LIMIT 60")
suspend fun ftsSearch(seriesId: Long, q: String): List<CodeHit>
```

Call it as `ftsSearch(seriesId, ftsQuery(input))`. Note `rowid = c.id` — that join condition
is correct as written and is the second most common way to write this wrong.

Weight `title` 10, `meaning` 1, `solution_text` 3: a code whose **name** matches should beat a
code that merely mentions the word in a fix step.

## 4. Search UI

- One field per list screen, **always visible at the top**, never hidden behind an icon and
  never a full-screen modal. 48dp tall, 2dp ink border, 4dp radius, canvas background, ink
  text, 16sp. Placeholder always names the scope:
  - Brands: `Search brands`
  - Model lines: `Search models`
  - Codes: `Search codes in this model`
- Debounce 180ms, cancel the previous job on every keystroke.
- IME: `capitalization = Words` for brands and models, `Characters` for codes.
- **Clear button** (one of the five permitted icons) appears only when the field is non-empty.
- As-you-type filtering on the in-memory list (382 brand+series rows, ≤106 codes) — no query
  per keystroke. Use the database for codes and aliases; the DAO is 0.2 ms anyway.
- Result cap 60 rows, then `Showing 60 of N` as a text row.
- Never lose the list on a failed query: keep the previous results and show a single
  `Search unavailable` line.

## 5. The empty state that teaches the rule

Typing a code on the **brands** screen finds nothing — and that is the moment to explain the
design instead of looking broken:

> **No brand matches "E6"**
> E6 means something different on 16 brands, so codes are searched inside a model.
> **Search models instead**

One line of explanation and a way forward. No image, no emoji, no "Try again".

## 6. Perf numbers to protect

| Operation | Budget | Measured |
|---|---|---|
| exact alias lookup | < 5 ms | 0.2 ms |
| prefix alias lookup (`E`) | < 20 ms | 2 ms, 1413 hits |
| FTS quoted, series-scoped | < 50 ms | safe |
| brand list filter | < 16 ms | in-memory, 62 rows |
| series list filter | < 16 ms | in-memory, 320 rows |

Debounce at 180ms so typing `Error` triggers ~1 query, not 5.

## 7. Tests (non-negotiable)

- [ ] **Every one of the 2139 code strings** searched through the code field, in its own
      series: no crash, all resolve
- [ ] Every code string with `;`, `+`, `-`, spaces and quotes: no crash
- [ ] `E6` on the brands screen → zero results + the explanatory empty state
- [ ] `E6` inside a series → exactly that series' `E6`
- [ ] A code from brand X is **impossible** to find from brand Y (assert 0 results)
- [ ] Every one of the 7707 alias values round-trips: `alias_norm` → its own code
- [ ] Debounce: 5 keystrokes → 1 query
- [ ] Backspace to empty → full list restored, no stale filter

## 8. Traps
- `LIKE '%x%'` against `code_fts` returns **nothing** — it is a contentless virtual table.
- `rowid` is `codes.id`, not `codes.uid` and not the row's own index.
- Joining `aliases` **without** the `series_id` filter silently reintroduces the global search
  this design exists to prevent.
- Putting a `~` or `*` in the query changes FTS semantics — normalise it away.
- `ORDER BY bm25(...)` ascending is **better matches first**; the sign is easy to get wrong.
