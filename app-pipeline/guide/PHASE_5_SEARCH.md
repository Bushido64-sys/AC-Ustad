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
| Free text in a model | that series' descriptions | `code_fts`, then that series' own text (trap 25) | other series, other brands |

**There is no global code search, and you must not add one.** `E1` is on 20 brands, `E3` on
21, `E6` on 16, `F4` on 15. A global search would answer "E6" with 16 different meanings and
force the technician to work out which brand they are standing in front of. Inside a series,
`E6` has exactly one meaning — and it is the right one.

A code search only ever runs with a `series_id` bound. Enforce it in the DAO signature, not in
the call site: `searchCodes(scope: ScopedSeries, query: String)` — and `ScopedSeries` carries
`brandId` too, because `series_id` alone repeats across nine brands (§8).

## 2. The two-job rule

| Query looks like | Route | SQL |
|---|---|---|
| `E1`, `Error 200`, `Er02`, `BLINK-RUNNING`, `P003` | **aliases** | see below |
| `compressor`, `over temperature`, `high pressure`, `phase` | **code_fts** | `MATCH '…'` |

**Rule:** if the normalised query contains no space and is short (≤ 24 chars) and matches
something in `aliases`, that is the answer. Only fall through to FTS when aliases return
nothing. Never send a code-looking string to FTS.

**Free text itself has two steps, and it is the second one that keeps §1's promise.**
`code_fts` holds `code_norm`, `aliases` and **titles only** — never the meanings, causes or
fix steps (see §3) — so `compressor` is answered and `air leakage` returns **0** rows
app-wide while the phrase sits in 12 fix steps. Step two is `SearchDao.codesDescription`: the
same words, AND-joined as `LIKE`, over that one model's titles, meanings, notes, causes and
fix steps, with `series_id` **and** `brand_id` bound (RULE 3). It runs only when the index
came back empty **and the query is not a code anywhere in the knowledge base** — a digit test
would get `BLINK-RUNNING` wrong — so `E6` in a model that lacks it still ends on "no code
matches" instead of a list of codes that merely mention it. Measured 1.5 ms on a four-code
model, 7.8 ms on the largest (106 codes), against §6's 50 ms budget.
`check_app_sql.py` reads the statement out of `SearchDao.kt` and pins it: 29 codes in 25 model
lines for `air leakage`, two inside each of two named models, and zero for a brand that does
not publish the series.

**A third step, and only when both of those fail: the same index with the words OR-joined.**
`SearchInput.ftsQueryAny` answers the words *apart* when they answer nothing *together* —
measured on the shipped database **no title holds both `air` and `leakage`** (0 of 4,418), so
the AND above returns zero in all 320 model lines while *Refrigerant leakage detection* and
*Anti-Cold Air Feature On* each hold one word of it. The precise steps always win: this runs
last, it never runs for a single word (nothing to loosen), and it never runs for a code that
exists somewhere in the knowledge base. In Dawlance's Splits the whole chain now reads
`air leakage` → 2 rows (`CF`, `E4`); in Panasonic Modern H/F it still reads F91 and F97.

**No step may depend on FTS5 being compiled into the device.** It is a compile-time option of
SQLite and Android's system build does not promise it, so `code_fts MATCH` can throw on a
phone. `SearchDao.codesText` catches that `SQLException`, logs it and returns an empty list,
which lets `searchCodes` continue to `codesDescription` — plain `LIKE`, no index required. The
index is a speed-up over that step, never a prerequisite: through the fallback alone,
`indoor` in Hitachi's SET FREE air365 returns **31 rows including `01`**, and that is what
`check_app_sql.py` pins (a JVM test cannot ask a phone which modules its SQLite was built
with).

**And the known-code guard must ask the model, not just the fact.** `isKnownCode` answers
"this string *is* a code somewhere", and on that reading alone the search used to end with
"no code matches" — wrong for the **673** codes whose canonical form contains a space
(`ERROR 200`, `Run flash 5Hz + Timer off`, `★-★-●`), because the routing rule above never
sends a space-containing query to the alias steps while `code_norm` still registers those
very codes. **55 of the 673 were unreachable by typing their own string**, in the model that
publishes them. The guard now runs the same scoped exact/prefix lookup
(`SearchDao.codesInModel`) before it answers; a code-shaped query already asked in step 1 and
does not pay for it twice. `check_app_sql.py` pins the data side of that call: all 673
resolve through their own model's alias table (RULE 3).

### Exact / prefix code lookup — the safe path

```sql
SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.is_fault, c.display
FROM aliases a JOIN codes c ON c.id = a.code_id
WHERE a.alias_norm = :canon        AND c.series_id = :seriesId
                                  AND c.brand_id  = :brandId     -- exact
UNION ALL
SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.is_fault, c.display
       -- c.display IS the display-style hint; there is no display_style column
FROM aliases a JOIN codes c ON c.id = a.code_id
WHERE a.alias_norm LIKE :canon || '%'
                                  AND c.series_id = :seriesId
                                  AND c.brand_id  = :brandId     -- prefix
GROUP BY c.id;
```

`aliases` is 7,707 rows and is **indexed** (`idx_alias_norm`) — measured on the shipped
database: exact **0.15 ms**, prefix 3.0 ms. No FTS involvement, no escaping, no crash
surface, and all **2,139** distinct code strings resolve.

```kotlin
// canon() is in SearchInput.kt. Reproduces alias_norm for all 4,124 distinct alias pairs.
fun canon(s: String) = s.uppercase()
    .replace(Regex("[^A-Z0-9_./\\-]+"), " ")
    .replace(Regex("\\s+"), " ")
    .trim()
```
**UPPER, not lower.** `alias_norm` is stored upper-cased (5,562 of 7,707 rows contain
capitals), so a lower-casing rule returns **0 rows** for `e1`, `e6`, `f4` — most of what a
technician types. Measured: 0.15 ms indexed versus 3.6 ms for a `LOWER()` full scan.
Applied to the **query only** — the stored values are already canonical.

## 3. The FTS escaping rule — the crash

`code_fts` is an external-content FTS5 table over **`code_norm`, `aliases`, `titles`** — the
English and Roman Urdu *titles*, and nothing else — with `rowid = codes.id`. It does **not**
index the meaning, the notes, the causes or the fix steps (`DATA_SCHEMA.md` §2), which is why
§2 needs a second step and why PROGRESS trap 25 exists. Passing raw user input to `MATCH` makes
SQLite parse the words as **column names**.

```
"BLINK-RUNNING"    -> no such column: RUNNING
"LED1 x1 blink; ..." -> syntax error near ";"
"ID+F101"          -> syntax error near "+"
```

Measured on the shipped database: **454 of the 2139 code strings throw when passed raw; all
2139 return results when quoted.**

The query itself is `SearchDao.codesText` — the app has no `ftsSearch` and no `CodeHit`:

```kotlin
fun ftsQuery(raw: String) = "\"" + raw.replace("\"", "\"\"") + "\""

suspend fun codesText(scope: ScopedSeries, quotedQuery: String, limit: Int = 60) = io {
    db.rawQuery(
        """
        SELECT c.id, c.uid, c.code, c.title_en, c.title_ur, c.severity, c.is_fault, c.display
          FROM code_fts JOIN codes c ON c.id = code_fts.rowid
         WHERE code_fts MATCH ? AND c.series_id = ? AND c.brand_id = ?
         ORDER BY bm25(code_fts, 10.0, 1.0, 3.0)
         LIMIT ?
        """.trimIndent(),
        arrayOf(quotedQuery, scope.seriesId, scope.brandId, limit.toString()),
    ).mapRows { it.toSummary() }
}
```

Call it as `codesText(scope, ftsQuery(input))`. Note `c.id = code_fts.rowid` — that join
condition is correct as written and is the second most common way to write this wrong.

**Three weights, three columns.** `bm25(code_fts, 10.0, 1.0, 3.0)` is read left to right
against the columns in the order `fts5(...)` declares them: `code_norm` 10.0, `aliases` 1.0,
`titles` 3.0. The prose this section used to carry — *weight `title` 10, `meaning` 1,
`solution_text` 3* — described an index this one is not: there is no `meaning` and no
`solution_text` in `code_fts` to weigh, so "a code whose name matches should beat a code that
merely mentions the word in a fix step" is a claim about a fallback that does not exist in
this index (the fallback is §2's `codesDescription`, and it is unranked by design). Do not
change the numbers to make a missed result appear — see §8 and PROGRESS trap 25.

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
  per keystroke. Use the database for codes and aliases; the exact lookup is 0.15 ms anyway.
- Result cap 60 rows, then `Showing 60 of N` as a text row.
- Never lose the list on a failed query: keep the previous results and show a single
  `Search unavailable` line.

## 5. The empty state that teaches the rule

Typing a code on the **brands** screen finds nothing — and that is the moment to explain the
design instead of looking broken:

> **No brand matches "E6".**
> E6 means something different on 16 brands, so codes are searched inside a model.
> **Show all brands**

One line of explanation and a way forward. No image, no emoji, no "Try again".

A **description** of a fault typed in that same box gets its own line, for the same reason —
it is not a wrong answer, it is a question asked one level too high (added 2026-09-30,
PROGRESS trap 25):

> **No brand matches "air leakage".**
> "air leakage" describes a fault, not a brand. This box searches brand names only. Open a
> brand, pick a model, and search there.
> **Show all brands**

Same headline, same action, different detail. The one-word typo (`sharpe`) still gets silence:
two words with no digit between them are how no one spells a brand name here.

Inside a **model** those same words are the right question and usually a right answer (§2): the
precise step finds them in a fix step, and the loose step finds the titles that hold one of
them when the two together hold none. Only when the whole chain comes back empty does the
codes screen stop answering with a bare zero (added 2026-09-30, PROGRESS trap 25):

> **No code matches "water pump" in this model.**
> Only Splits — Inverter & Fixed-Speed (shared platform) is searched — including what its
> codes mean, what causes them and how to fix them. Words that appear only in another model
> are not shown here.

One line, no button, no image — the brands screen's `isDescription` carve-out applied to a
different box. `E6` missing from a model still gets the headline alone: a code needs no
explanation, it just is not in this model.

## 6. Perf numbers to protect

| Operation | Budget | Measured |
|---|---|---|
| exact canonical alias lookup | < 5 ms | **0.15 ms** |
| prefix alias lookup (`E%`) | < 20 ms | 3.0 ms |
| a `LOWER()` variant of the same lookup | — | 3.6 ms and it misses lowercase input |
| FTS quoted, series-scoped | < 50 ms | safe |
| description fallback (`LIKE`, series-scoped) | < 50 ms | **1.5 ms** on a 4-code model, **7.8 ms** on the 106-code worst case |
| brand list filter | < 16 ms | in-memory, 62 rows |
| series list filter | < 16 ms | in-memory, 320 rows |

Debounce at 180ms so typing `Error` triggers ~1 query, not 5.

## 7. Tests (non-negotiable)

- [ ] **Every one of the 2,139 code strings** searched through the code field, in its own
      series, **typed in lower case** (`e1`, not `E1`): no crash, all resolve
- [ ] Every code string with `;`, `+`, `-`, spaces and quotes: no crash
- [ ] `E6` on the brands screen → zero results + the explanatory empty state
- [ ] `E6` inside a series → exactly that series' `E6`
- [ ] A code from brand X is **impossible** to find from brand Y (assert 0 results)
- [ ] **A description is findable where the index cannot reach it:** `air leakage` inside a
      model returns that model's own codes (2 in Panasonic H/F, 2 in FoxESS H1(G2)); in a model
      where the words appear nowhere, the loose OR step still returns the titles that hold one
      of them (Dawlance's Splits: `CF`, `E4`); the same words on the brands screen return
      **no** codes and the teaching line instead; `E6` in a model that lacks `E6` returns
      **no** rows at all. Pinned by `check_app_sql.py` and `SearchDaoContractTest`
- [ ] `canon()` reproduces all 4,124 distinct `alias`/`alias_norm` pairs exactly
- [ ] Every one of the 7,707 alias values round-trips: `canon(alias)` → its own code
- [ ] **A code the routing gate skips is still found:** `Run flash 5Hz + Timer off` (Midea) and
      `★-★-●` (AUX) typed inside their own model return that row; typed anywhere else, "no code
      matches". Pinned by `check_app_sql.py` (673 of 673 space-canon codes resolve scoped) and
      `SearchDaoContractTest`
- [ ] Debounce: 5 keystrokes → 1 query
- [ ] Backspace to empty → full list restored, no stale filter

## 8. Traps
- `LIKE '%x%'` against `code_fts` returns **nothing** — it is a contentless virtual table.
- `rowid` is `codes.id`, not `codes.uid` and not the row's own index.
- Joining `aliases` **without** the `series_id` filter silently reintroduces the global search
  this design exists to prevent.
- Putting a `~` or `*` in the query changes FTS semantics — normalise it away.
- `ORDER BY bm25(...)` ascending is **better matches first**; the sign is easy to get wrong.
- **`series_id` is not unique on its own** — 6 values repeat, one 14 times. Every scoped code
  query must bind `brand_id` as well, or a model can return another brand's codes.
- Do not "fix" a missed result by adding `LOWER()`. Canonicalise to UPPER instead: it is the
  form the index holds, and lower-cased input is what the canonical form exists to catch.
- **`code_fts` holds titles only.** A word a technician *says* (`air leakage`, `not cooling`)
  and nobody *named* a code with returns 0 rows from the index even though it is written all
  over the fix steps. The fix is `SearchDao.codesDescription` (§2), **not** a reindex: adding
  columns to `code_fts` changes `kb.sqlite` and therefore the `data-manifest.json` sha256 the
  APK hash gate verifies, and it would leave `bm25`'s three weights applied to different
  columns. PROGRESS trap 25.
- **A routing gate is not a fact about the data.** `looksLikeCode` (no space, ≤ 24 chars)
  decides which *steps* run, never what is *true*: 55 of 4,418 codes canonicalise to something
  containing a space, and the known-code guard — which trusts `code_norm` — answered "no code
  matches" for all of them while the row sat in that very model. Ask the data the gate
  skipped. PROGRESS trap 27.
