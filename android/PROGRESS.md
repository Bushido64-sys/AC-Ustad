# PROGRESS — AC Ustad app

**Read this first when resuming.** Last updated: 2026-09-28

---

## Status

| Phase | State |
|---|---|
| 1 · Setup (skeleton, database, fonts, theme, home) | ✅ code written, **not yet compiled** |
| 2 · Data layer (DAOs, repository, favourites) | ⬜ not started |
| 3 · Browse (categories → brands → models) | ⬜ not started |
| 4 · Code detail | ⬜ not started |
| 5 · Search (scoped, 2 jobs) | ⬜ not started |
| 6 · Favourites | ⬜ not started |
| 7 · Offline & updates | ⬜ not started |
| 8 · Accessibility & Roman Urdu | ⬜ not started |
| 9 · Hardening & release | ⬜ not started |

**The whole app has never been compiled.** No Android SDK exists on this machine, so the
first real test of this code is the first CI run. Expect a short fix loop: build → read the
error → fix → push. That loop *is* the workflow here.

## What exists

- Gradle 8.10.2 wrapper, AGP 8.7.3, Kotlin 2.0.21, Compose BOM 2024.12.01, compileSdk 35
- `assets/db/kb.sqlite` — 62 brands, 320 series, 4,418 codes, hash-verified against the
  knowledge-base manifest
- 7 bundled IBM Plex weights + font-family XMLs
- Palette in `res/values/colors.xml` + `values-night/colors.xml` (single source of truth)
- Adaptive launcher icon whose "AU" is real IBM Plex Bold outline data
- `KbDatabase` (asset → cache, read-only, one repair retry)
- `BrandRepository` (verified count query: AC 31 brands/1,723 codes, inverter 33/2,695)
- `SearchInput` (normalise + FTS quoting) with unit tests
- `SchemaContractTest` pinning the real column names
- `HomeScreen` reading live counts
- CI: build + 4 gates + APK artifact

## Schema corrections — DONE, the guide now matches the database

The build guide's `DATA_SCHEMA.md` documented columns that **do not exist**. It has been
rewritten from `PRAGMA table_info` on the shipped database, and every one of the 33 counts it
quotes has been re-verified against the database (zero mismatches). The app's
`SchemaContractTest.kt` still pins the schema as a second line of defence.

| Guide said | Reality |
|---|---|
| `brands.id` integer, `brands.uid` | `id` is a TEXT slug; there is no `uid` on `brands` |
| `brands.unit_type` | no such column — `brands.categories` is a JSON array (`["ac"]`); `unit_type` is on `series` |
| `brands.notes_en` | `brands.notes` (plus `country`, `website`, `series_count`, `source_notes`) |
| `series.notes_en` | `series.notes` — and they are long: median 269, max 1,450 chars |
| `codes.display_style` | does not exist. **`codes.display` IS the display-style hint** (`controller` 2510, `indoor` 751, `led_blink` 413, `remote` 398, `outdoor` 315, `unknown` 31) — the guide had wrongly described it as a pre-joined `code · title` string |
| `codes.source_ref` | `codes.source_title` |
| `causes.cause_en/cause_ur/ord` | `causes.code_id, idx, en, ur` |
| `solutions.step_no/text_en/text_ur` | `solutions.code_id, idx, en, ur` |
| `aliases.kind` | no such column — `code_id, alias, alias_norm` |
| `favourites.code_uid/is_read/titles` | `favourites.code_id INTEGER PK, created_at TEXT` — two columns, nothing else |
| `meta.db_version` | `meta.kb_version` (e.g. `2026-09-26`) |
| lower-case the query before matching | `alias_norm` is stored **UPPER**-cased |

## The search bug this caught — the most serious finding

`alias_norm` is upper-cased: 5,562 of its 7,707 rows contain capitals. The old rule
(lower-case the query, then match) returns **zero rows** for `e1`, `e6`, `f4` — which is most
of what a technician actually types. Measured:

| query | old rule | corrected rule |
|---|---|---|
| `E1` | 31 rows | 31 rows |
| `e1` | **0 rows** | 31 rows |
| `e6` | **0 rows** | 26 rows |

`SearchInput.canon()` was derived by testing candidate rules against the database until one
reproduced `alias_norm` for **all 4,124 distinct alias pairs**, after which **all 2,139 code
strings resolve**:

```kotlin
canon(s) = s.uppercase()
    .replace(Regex("[^A-Z0-9_./\\-]+"), " ")  // keep _ . / - ; everything else -> one space
    .replace(Regex("\\s+"), " ").trim()
```

It is also **10× faster than the `LOWER()` alternative** (0.15 ms vs 3.6 ms) because the
canonical form is what `idx_alias_norm` holds, so it can use the index instead of full-scanning.

## Traps that will bite

1. **`series.id` is unique only within a brand** (6 values repeat, one 14 times). Joining
   `codes → series` on `series_id` alone returns **7,036** rows instead of 4,418. Bind
   `brand_id` too, or avoid the join and use `SUM(series.code_count) = 4418`.
2. `brands.categories` is a JSON array string, and 2 of 62 brands are in both categories, so
   per-category brand counts sum to **64**, not 62. Use `series.category` for code counts
   (`ac` 1,723 + `inverter` 2,695 = 4,418 exactly).
3. **65** of the 320 model lines have zero codes — more than one in ten, so the empty state
   is a main path, not an edge case. 8 brands also have zero.
4. Favourites are keyed on `code_id`, which a data release can renumber. **Sweep and drop
   stale ids on every database re-copy.** There is no `is_read`, so no unread dot.
5. `favourites.created_at` is TEXT. `causes`/`solutions` use `idx`, not `step_no`.

Verified and unchanged: 3,849 fault / 569 indicator rows; every fault row has ≥2 causes and
≥2 solutions (averages 2.47 and 2.76); 26 codes with no `source_url`; `en` and `ur` are never
identical anywhere (0 violations); and the FTS figure is exact — **454 of 2,139 code strings
fail unquoted, all 2,139 succeed quoted.**

## Next actions

1. Create the private GitHub repo `AC-Ustad-app` (empty, no README).
2. `git init -b main`, commit, add the SSH remote, push.
3. Watch the first CI run. Expect compile errors; fix them one at a time.
4. Then start Phase 2 (data layer: DAOs + favourites), and correct the guide's
   `DATA_SCHEMA.md` in the other repository **in the same change** as any data release.

## Testing (the six checks, every build)

1. Airplane mode on → app opens and works.
2. Home shows **31** AC brands / **1,723** codes and **33** inverter brands / **2,695** codes.
3. No white flash at launch; both light and dark themes render.
4. IBM Plex visible; mono on counts.
5. The APK is ~12 MB, not 20+.
6. `adb shell dumpsys package com.acustad.app` lists **no** permissions.
