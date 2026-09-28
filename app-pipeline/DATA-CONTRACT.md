# DATA-CONTRACT.md — the exact contract for the AC Ustad knowledge base

Everything a building agent needs to read this data correctly. No opinions about screens or design;
only facts, shapes, allowed values, and behaviours the data requires.

- **Built:** 2026-09-26 · **KB version:** 2026-09-26 · **schema version:** 1
- **Ship file:** `app-pipeline/db/kb.sqlite` (8.8 MB) — copy into `assets/databases/`, open **read-only**
- **Flat alternative:** `app-pipeline/kb.json` (8.8 MB) — every code with brand + series already inlined

---

## 1. Size of the data

| Thing | Count |
|---|---|
| Brands (companies) | **62** — 31 AC, 33 inverter, **2 sell both** (`inverex`, `homage`) |
| Series (model lines) | **320** |
| Codes | **4418** — 1723 AC, 2695 inverter |
| Codes that are faults (`is_fault = 1`) | 3849 (87%) |
| Codes that are normal status rows (`is_fault = 0`) | 569 (13%) |
| Cause bullets | 10450 (avg **2.47** per fault code) |
| Fix-step bullets | 11776 (avg **2.76** per fault code) |
| Aliases (alternative spellings) | 7707 |
| Search keys (every code string **and** alias) | 4124 |
| Distinct code strings, exactly as written | **2139** |
| Distinct code strings after normalising (see §6a) | **2074** |
| **Code strings used by more than one brand** | **359** |
| Code strings used by more than one series | 578 |

---

## 2. Tables (SQLite, 9 tables)

### `meta` — one row per key
`key TEXT PRIMARY KEY`, `value TEXT`. Ships with: `kb_version`, `built_at`, `schema_version`,
`brands`, `series`, `codes`, `search_keys`, `data_license_note`. Show `kb_version` as "data date".

### `brands` — 62 rows, one per company
| Column | Type | Notes |
|---|---|---|
| `id` | TEXT PK | lowercase kebab (`growatt`, `dawlance`, `fronius-at`) |
| `name` | TEXT | display name (`Growatt`) |
| `categories` | TEXT | **JSON array**: `["ac"]` or `["ac","inverter"]` |
| `country`, `website`, `notes` | TEXT | may be empty |
| `series_count`, `code_count` | INTEGER | for list headers |
| `source_notes` | TEXT | e.g. `See docs/sources/growatt.md` |

A brand is **one row** even when it sells both categories — filter by category through
`series.category` or `codes.category`, not by splitting the brand row.

### `series` — 320 rows
| Column | Type | Notes |
|---|---|---|
| `uid` | TEXT PK | `growatt/growatt-mod-tl3x` — **use this** (series ids repeat across brands) |
| `id` | TEXT | series id, unique only inside its brand |
| `brand_id` | TEXT | → `brands.id` |
| `name` | TEXT | e.g. `MOD 3-15KTL3-X (three-phase string, 2 MPPT)` |
| `category` | TEXT | `ac` \| `inverter` |
| `unit_type` | TEXT | see §4 |
| `model_patterns` | TEXT | JSON array of nameplate hints, e.g. `["*MOD*TL3-X*","*3-15KTL3-X*"]` |
| `notes` | TEXT | model scoping / conflicts |
| `code_count` | INTEGER | |
| `source_file` | TEXT | originating KB file (traceability only) |

### `codes` — 4418 rows
| Column | Type | Notes |
|---|---|---|
| `id` | INTEGER PK | internal; used by `favourites` |
| `uid` | TEXT UNIQUE | `growatt/growatt-mod-tl3x/Error 200` — **the stable external id** |
| `brand_id` | TEXT | → `brands.id` |
| `series_id` | TEXT | → `series.id` (combine with brand_id to reach `series.uid`) |
| `category` | TEXT | `ac` \| `inverter` (copied from series, saves a join) |
| `unit_type` | TEXT | copied from series |
| `code` | TEXT | **exactly as the device shows it** — `"Error 200"`, `"F4"`, `"LED1 x3 blink"` |
| `code_norm` | TEXT | upper-cased, spaces collapsed — for exact/prefix matching |
| `title_en`, `title_ur` | TEXT | short label, both always present |
| `meaning_en`, `meaning_ur` | TEXT | 1–2 sentences, both always present |
| `severity` | TEXT | see §4 |
| `display` | TEXT | see §4 |
| `is_fault` | INTEGER | 0/1 |
| `confidence` | TEXT | `high` \| `medium` \| `low` |
| `source_type` | TEXT | see §4 |
| `source_title`, `source_url`, `source_retrieved` | TEXT | url is NULL for 26 codes |
| `blink_pattern` | TEXT | LED flash description — present on **894** codes, NULL otherwise |
| `related_codes` | TEXT | JSON array — present on **370** codes |
| `notes_en`, `notes_ur` | TEXT | conflicts / model-scope warnings — present on **1951** codes |

### `causes` and `solutions` — child rows, order matters
Both: `code_id INTEGER → codes.id`, `idx INTEGER`, `en TEXT`, `ur TEXT`, PK `(code_id, idx)`.
Fetch with `ORDER BY idx`. **Solutions are ordered safe-first** (isolate power before probing).
Every fault code has ≥2 of each. One non-fault row has none (Panasonic `H00`) — that is correct.

### `aliases` — 7707 rows
`code_id`, `alias` (as written), `alias_norm` (upper-cased). The code itself is always included as
its first alias. Use for "user typed 200 → find `Error 200`".

### `code_fts` — FTS5 full-text index
`rowid = codes.id`, columns `code_norm`, `aliases`, `titles`. Query with `MATCH`:
`SELECT ... FROM code_fts WHERE code_fts MATCH 'E6'` then join `codes ON codes.id = code_fts.rowid`.
Joining on `rowid` is required — joining on `code_norm` produces duplicates.

### `favourites` — ships EMPTY
`code_id INTEGER PRIMARY KEY → codes.id`, `created_at TEXT`. Local, per-device. Never pre-filled.

---

## 3. Identity: the rule that prevents wrong answers

```
code alone is NOT an identity.
identity = brand_id + series_id + code        (equivalently the uid column)
```

Real data proving it:

| Code string | brands | series entries | example of differing meaning |
|---|---|---|---|
| `E1` | 20 | 30 | indoor temp sensor (some brands) vs communication error (others) |
| `E3` | 21 | 29 | fan motor fault vs EEPROM vs gas-flow protection |
| `E6` | 16 | 23 | room sensor vs compressor lock vs indoor fan motor |
| `F4` | 15 | 18 | discharge-temperature protection vs outdoor EEPROM error |
| `01` | 14 | 28 | fan failure vs overload — depends on brand *and* model line |

Even inside one brand: Carrier `E6` = "room/evaporator sensor" on Elite hi-wall, "indoor↔outdoor
communication" on Multi Split, "outdoor unit malfunction" on SHV fixed-speed. **Never collapse these.**

---

## 4. Allowed values (never invent new ones)

**`severity`** — drives the badge; 5 values:
| value | count | meaning |
|---|---|---|
| `info` | 357 | not a problem — normal status (defrost, filter reminder) |
| `self_clear` | 153 | often clears after a restart / transient event |
| `check_restart` | 1226 | check the basics, then restart |
| `stop_pro` | 2321 | needs diagnosis; running on may damage the unit |
| `danger` | 361 | electrical / gas / fire risk — stop, qualified tech only |

**`display`** — where the user sees it: `controller` 2510 · `indoor` 751 · `led_blink` 413 ·
`remote` 398 · `outdoor` 315 · `unknown` 31.
When `display = 'led_blink'`, `blink_pattern` usually explains the flashes.

**`confidence`** — how sure the research is: `high` 3565 · `medium` 718 · `low` 135.
Show it; a `low` code means "verify on your model".

**`unit_type`** — device family: `hybrid_inverter` 109 · `split` 62 · `on_grid_inverter` 47 ·
`generic` 36 · `ups` 35 · `off_grid_inverter` 14 · `ducted` 7 · `floor_standing` 5 ·
`cassette` 3 · `window` 2.

**`source_type`** — provenance: `service_manual` 2083 · `user_manual` 1292 · `official_support` 397 ·
`other` 249 · `technician_note` 155 · `video` 102 · `retailer_page` 61 · `distributor_page` 60 ·
`forum` 19.

---

## 5. Bilingual rule (this is the product)

- Every user-visible string exists in **both** `en` and `ur`. There are no exceptions in the data.
- `ur` is **Roman Urdu** — Latin script only, natural technician speech, with English technical
  words kept in English (capacitor, PCB, sensor, MPPT, relay, grid, battery).
- Guaranteed by the build: **0** fields where `en == ur`, **0** non-Latin characters.
- A code detail always has: title, meaning, causes (≥2), solutions (≥2) — each bilingual.
- Example (real row): `en "AFCI fault (arc detected)"` / `ur "AFCI fault (arc detect hua)"`.

---

## 6. Behaviours the data requires

### 6a. Search — read this first, it will crash the app if you skip it

Two different jobs, two different tables. Do **not** send code strings into FTS raw.

**Job 1 — looking up a code (what a technician types: `E6`, `13-1`, `200`, `BLINK-RUNNING`).**
Use the indexed `aliases.alias_norm` column. The code is always stored as its own first alias,
so this covers codes *and* alternative spellings in one lookup, with no query-syntax risk.

Normalise the user's input the same way the build did:
1. trim, upper-case
2. every character outside `A-Z 0-9 _ . - /` becomes a space
3. collapse runs of spaces to one, trim again

```sql
-- exact code / alias  (the code is stored as its own alias)
SELECT c.code, c.title_en, c.title_ur, br.name, c.severity
  FROM aliases a
  JOIN codes  c  ON c.id  = a.code_id
  JOIN brands br ON br.id = c.brand_id
 WHERE a.alias_norm = 'E6';            -- user typed " e6 "

-- as the user types: prefix
WHERE a.alias_norm LIKE 'E6%'
```
Measured: exact 0.2 ms, prefix over all aliases 2 ms on the shipped DB.

**Job 2 — free text ("compressor", "over current", "drain").** Use `code_fts`, and **wrap the
input in double quotes**:

```kotlin
fun ftsSafe(input: String) = "\"" + input.replace("\"", "\"\"") + "\""
db.rawQuery("SELECT rowid FROM code_fts WHERE code_fts MATCH ?", arrayOf(ftsSafe(userInput)))
```

> **Why the quotes matter:** 454 of the 2139 code strings in this KB — 21% — make FTS5 throw if
> passed unquoted. Real examples: `BLINK-RUNNING` (parsed as `no such column: RUNNING`),
> `LED1 x1 blink; LED2 off; LED3 off` (`syntax error near ";"`),
> `Run flash + Timer flash (heat pump)` (`syntax error near "flash"`). With the quoting helper
> above, **all 2139 resolve, 0 errors** — and the build now tests this on every run
> (`data-manifest.json → qualityChecks.unsearchableCodes` must stay `0`).
> Prefix search: `ftsSafe("E").dropLast(1) + "*"` → `MATCH '"E"*'` (1413 hits, 0.8 ms).

### 6b. Everything else

1. **Drill-down:** category (AC / Inverter) → brand → series (model + variation) → codes in that
   series. Provide a search box at each level.
2. **Global search** must return matches **grouped by brand**, each showing brand, series, the
   code, and its meaning — never one merged answer. Use `code_fts` + join on `rowid`, or
   `aliases` for exact codes.
3. **Model matching:** `series.model_patterns` is a glob-ish list; use it to suggest the right
   series for a nameplate the user types, e.g. `"*MOD*TL3-X*"` matches `Growatt MOD 3-15KTL3-X`.
4. **Favourites:** toggle writes `favourites(code_id, created_at)`; list joins back to `codes`.
5. **Source + confidence on the detail page:** show confidence; make the source tappable when
   `source_url` is present (26 codes have none).
6. **Read-only data:** only `favourites` is ever written.
7. **Offline:** everything needed is inside the shipped DB — no network required to read content.
8. **Data freshness:** show `meta.built_at` / `meta.kb_version` somewhere (e.g. settings/about).
9. **Cascading deletes** (e.g. removing a favourite's code) only happen if the app opens the DB with
   `PRAGMA foreign_keys = ON`. Harmless either way, but enable it if you rely on the cascade.

### Ready-to-use queries
```sql
-- AC brands, biggest libraries first
SELECT name, series_count, code_count FROM brands
 WHERE categories LIKE '%ac%' ORDER BY code_count DESC;

-- series of one brand
SELECT id, name, unit_type, code_count FROM series
 WHERE brand_id='growatt' ORDER BY code_count DESC;

-- codes in one series
SELECT code, title_en, title_ur, severity, is_fault FROM codes
 WHERE brand_id='growatt' AND series_id='growatt-mod-tl3x' ORDER BY code_norm;

-- one code with everything
SELECT c.*, br.name AS brand, s.name AS series FROM codes c
  JOIN brands br ON br.id=c.brand_id
  JOIN series s ON s.uid=c.brand_id||'/'||c.series_id
 WHERE c.uid='growatt/growatt-mod-tl3x/Error 200';

-- global search, grouped by brand
SELECT br.name, s.name, c.code, c.title_en, c.severity
  FROM code_fts f
  JOIN codes   c ON c.id  = f.rowid
  JOIN brands  br ON br.id = c.brand_id
  JOIN series  s  ON s.uid = c.brand_id||'/'||c.series_id
 WHERE code_fts MATCH 'E6' ORDER BY br.name, s.name;

-- exact code, all brands (fast path, no FTS)
SELECT br.name, s.name, c.title_en FROM codes c
  JOIN brands br ON br.id=c.brand_id
  JOIN series s  ON s.uid=c.brand_id||'/'||c.series_id
 WHERE c.code_norm='F4' ORDER BY br.name;

-- alias lookup
SELECT c.code, c.title_en, br.name FROM aliases a
  JOIN codes c ON c.id=a.code_id JOIN brands br ON br.id=c.brand_id
 WHERE a.alias_norm='200';

-- favourites
SELECT c.code, c.title_en, c.title_ur, f.created_at
  FROM favourites f JOIN codes c ON c.id=f.code_id ORDER BY f.created_at DESC;
```

---

## 7. JSON shapes (if you skip SQLite)

`kb.json` = `{"meta": {...}, "codes": [ … ]}` where each code is:
```json
{
  "uid": "growatt/growatt-mod-tl3x/Error 200",
  "brandId": "growatt", "brandName": "Growatt", "category": "inverter",
  "seriesId": "growatt-mod-tl3x", "seriesName": "MOD 3-15KTL3-X (three-phase string, 2 MPPT)",
  "unitType": "on_grid_inverter", "modelPatterns": ["*MOD*TL3-X*", "*3-15KTL3-X*"],
  "code": "Error 200", "codeNorm": "ERROR 200",
  "aliases": ["Error 200", "200", "AFCI Fault"],
  "title":   { "en": "AFCI fault (arc detected)", "ur": "AFCI fault (arc detect hua)" },
  "meaning": { "en": "…", "ur": "…" },
  "causes":   [ { "en": "…", "ur": "…" }, { "en": "…", "ur": "…" } ],
  "solutions":[ { "en": "…", "ur": "…" }, { "en": "…", "ur": "…" }, { "en": "…", "ur": "…" } ],
  "severity": "danger", "display": "controller", "isFault": true, "confidence": "high",
  "blinkPattern": null, "relatedCodes": [], "notes": null,
  "source": { "type": "service_manual", "title": "MOD 3-15KTL3-X User Manual EN (GR-UM-212-A-02)",
              "url": "https://growatt.tech/…/MOD-3-15KTL3-X-User-Manual-EN-202201.pdf",
              "retrieved": "2026-09-26" }
}
```
`notes`, `blinkPattern`, `relatedCodes` may be `null`/`[]` — always handle absence.
`brands.json` = brand tree. `search-index.json` = `{codeOrAlias: [ {uid, brandId, brandName,
seriesId, seriesName, code, titleEn, titleUr, severity, isFault}, … ]}`.

---

## 8. Regenerating

```bash
python3 app-pipeline/build_kb.py     # re-reads data/, rewrites every file in app-pipeline/
```
It exits non-zero if a fault code would ship with <2 solutions or without causes, if a severity /
display / confidence / unitType value is invalid, or if two codes would collide on `uid`.
It also self-tests search on every run: all 2139 code strings must be searchable through `code_fts`
(quoted) **and** findable by exact `aliases.alias_norm` lookup, and `search-index.json` must contain
no duplicated code. Failures are listed in `data-manifest.json → qualityChecks`.

**Reproducibility:** the output is byte-for-byte deterministic **except** the `builtAt` / `built_at`
timestamp embedded in each file, so file hashes change on every build by design. The logical content
(brands, series, codes, causes, solutions, aliases, the search index) is identical run to run.
`data-manifest.json` carries the SHA-256 of the files as built, so an app update can prove which
build it shipped.
