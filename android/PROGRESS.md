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

## Schema corrections (important)

The build guide's `DATA_SCHEMA.md` documented columns that **do not exist** in the shipped
database. The real schema, now pinned by tests:

| Guide said | Reality |
|---|---|
| `brands.id` integer, `brands.uid` | `id` is a TEXT slug; there is no `uid` on `brands` |
| `brands.unit_type` | no such column — `brands.categories` is JSON (`["ac"]`); `unit_type` is on `series` |
| `brands.notes_en` | `brands.notes` (plus `country`, `website`, `series_count`, `source_notes`) |
| `series.notes_en` | `series.notes` |
| `codes.display_style` | does not exist — `blink_pattern` and `related_codes` do |
| `codes.source_ref` | `codes.source_title` |
| `causes.cause_en/cause_ur/ord` | `causes.code_id, idx, en, ur` |
| `solutions.step_no/text_en/text_ur` | `solutions.code_id, idx, en, ur` |
| `aliases.kind` | no such column — `code_id, alias, alias_norm` |
| `favourites.code_uid/is_read/titles` | `favourites.code_id INTEGER PK, created_at TEXT` |

**Traps that will bite:**

1. `series.id` is unique only *within* a brand (6 values repeat, one 14 times). Joining
   `codes → series` on `series_id` alone returns **7,036** rows instead of 4,418. Join on
   `series_id AND brand_id`, or avoid the join and use the denormalised `code_count`
   (`SUM(series.code_count) = 4418`).
2. `brands.categories` is a JSON array string. A brand can be in both categories (2 of 62
   are), so brand counts sum to 64, not 62. Code counts come from `series.category`, which is
   a single clean value and sums to exactly 4,418.
3. Favourites are keyed on `code_id`, which is stable only within one data release. On
   upgrade, re-check every saved `code_id` still exists and drop the ones that do not.

**Still true in the guide, and re-verified against the database:** 3,849 fault rows and 569
indicator/parameter rows; every fault row has ≥2 causes and ≥2 solutions; 26 codes have no
`source_url`; 8 brands and **65** series have zero codes (the guide said 8 *series* — wrong);
`title_en` median 32 / max 60 and `meaning_en` median 82 / max 249 (the guide said 40/101 and
85/359); fault-only averages 2.47 causes and 2.76 solutions are correct. And the FTS figure is
exact — **454 of 2,139 code strings fail unquoted, all 2,139 succeed quoted.**

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
