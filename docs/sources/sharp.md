# Sharp (AC) — sources

Brand id: `sharp` · Category: `ac` · Roadmap row 45 · Researched: 2026-09-25
Official: https://global.sharp (Sharp Air App support — error-code list) · no Sharp PK microsite with error content
PK presence: **YES** — Sharp ACs sold with "Official Warranty From Sharp Pakistan"; ledmart.pk states **"Import and warranty by ORIENT Electronics"** (AY-X12CTEP). Retailers: priceoye.pk (11 models), daraz.pk, ledmart.pk, hadielectronics.com.pk, laptab.com.pk, megashop.com.pk, yasirelectronics.com, trendstore.com.pk, amazon.pk
Official published AC error table: **YES** — https://global.sharp/smartapp/air/support/airconerror/ (83 hyphenated main-sub codes)

**Status: 145 codes across 3 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `sharp-air-app-hyphenated.json` | Sharp Air App hyphenated main-sub codes (PK-facing scheme: AH-X/AY-X/AE-X inverter splits; remote readout — hold THERMOSTAT/SELECT 5 s unit off, step main then sub) | 89 |
| `sharp-ay-x-psr-display.json` | SHARP(CHINA) AY-X9PSR/AE-X9PSR/AY-X12PSR service-manual indoor LED E/P codes | 25 |
| `sharp-ay-x-psr-outdoor-blink.json` | Same PSR manual — outdoor power-PCB LED1 blink counts | 31 |

**Three code schemes for one brand — digits never merged across files.** The hyphenated scheme
is the PK-facing one (official table + ZU1/ZHU1 service manual + AH-X18 user manual + JP TRS page).
The PSR series is model-scoped (`AY-X*PSR`/`AE-X*PSR`) and **not confirmed sold in Pakistan** —
kept for completeness with series notes (Phase A retail checks found no PSR-suffixed SKU).

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | SHARP AIR APP — Error Code Content of Diagnosis (official Sharp Corp) | official_support | https://global.sharp/smartapp/air/support/airconerror/ | 83 hyphenated main-sub codes (series 1 core) | 2026-09-25 |
| 2 | Sharp SM-ZU1 / ZHU1 Service Manual (AY-XP12ZU1) — check-points & actions | service_manual | https://sharphvac.ca/wp-content/uploads/2025/04/SM-ZU1-ZHU1.pdf | extras 1-5, 6-2, 6-3, 10-3 (medium) + 18-1 corroboration + fault check points | 2026-09-25 |
| 3 | TRS Engineering — Sharp room air-con error-code list (JP) | technician_note | https://trseng.jp/errorcode/sharp.html | extras 9-1, 17-1 (medium); cause/action paraphrased from JP | 2026-09-25 |
| 4 | Sharp AY-X9PSR Service Manual (SHARP CHINA) pp.37–46 via easymanua (ManualsLib mirror) | service_manual | https://www.easymanua.ls/sharp/ay-x9psr/manual?p=38 | series 2 (25 E/P), series 3 (31 LED1 blinks), troubleshooting actions pp.40–46 | 2026-09-25 |
| 5 | Sharp AH-X18 Series User Manual p.13 "Get the error code" | user_manual | https://www.easymanua.ls/sharp/ah-x18-series/manual?p=13 | confirms hyphenated readout procedure (example `23-4`, THERMOSTAT 5 s) | 2026-09-25 |
| 6 | Sharp AH-XP Series User Manual | user_manual | https://usersmanualguide.com/sharp/air-conditioner/ah-xp-series/user-manual/zd8n | readout/self-clean context (no code table) | 2026-09-25 |
| 7 | Snowflake Aircon — Sharp blinking-light guide | technician_note | https://snowflakeaircon.sg/guides/sharp-aircon-blinking-light | independent corroboration of 17 hyphenated codes + display/LED readout guidance | 2026-09-25 |
| 8 | Scribd — "5-2016 Error Codes SHARP" (AH-XP18SHV / AH-X18SEV) | service_manual | https://www.scribd.com/document/409931345/5-2016-Error-Codes-SHARP | fetch returned metadata only — **nothing imported** (see Quarantine) | 2026-09-25 |

Notes: ManualsLib direct returns 403 — content obtained through the `easymanua.ls` mirror (page text
extracts character-mirrored; reversed line-wise before use) and the `r.jina.ai` text proxy.

## Conflicts log

| ID | Conflict | Resolution |
|----|----------|------------|
| 1 | E9 troubleshooting says "firstly display P0 or P9, then change to E9" — but P0/P9 also have their own Ch.6 chart rows (module/drive protection) | **P0/P9 kept as separate entries** (distinct chart titles); E9 `aliases` NOT merged (would collide) — sequence logged in E9/P0/P9 `notes` + meanings |
| 2 | Same digits, three readout schemes (hyphenated remote codes vs indoor E/P LED vs outdoor LED1 blink count) | **Three series files, never merged** — different display channels and product scopes |
| 3 | PSR family (series 2/3) not confirmed in PK retail | `modelPatterns: AY-X*PSR/AE-X*PSR` scoped; series notes state Phase A negative retail check |
| 4 | 2-4 and 2-5 share the official title "IPM high temperature error" | Both are distinct official rows — kept as separate codes (official table verbatim structure) |
| 5 | TCL PS-Cloud manual has a near-twin E/P + blink scheme (fetched during research) | **Other brand — quarantined**, never merged |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|--------------|
| `pscloud` TCL PS-Cloud manual (pscloud.pdf) | **Other brand (TCL)** — near-twin E/P + blink scheme; tempting but never merged |
| Scribd "5-2016 Error Codes SHARP" tables (F1/F2/F0 indoor; E0…E9 + pump/liquid-level GX-X/GB-X floor-standing) | Fetch returned metadata only + attribution ambiguous — **not imported**; reopen if a readable copy appears |
| Sharp copier/MFP codes (e.g. `E7-20`), Sharp TV / mobile / fridge / solar-inverter codes | **Wrong product** — out of category `ac` |
| Other-brand tables in Sharp SERPs (Samsung, LG, Daikin, Gree, Midea, Haier, TCL) | **Other brand** — same digits across brands never merged |
| Multi-brand aggregators (appliancecodebase, hvacinexpert-style SERP junk) | Unverified aggregators, no primary citation |
| ManualsLib direct page, elektrotanya | 403/blocked — superseded by easymanua mirror + official Sharp URL |

## Exhaustiveness (Phase D)

- Official Sharp AC error table: **YES** — 83/83 hyphenated codes imported, none dropped (count verified against the cached official page).
- PK sales presence: **YES** (ORIENT Electronics warranty claim + 9 retailers) — but **zero PK-hosted error content** (no Sharp PK fault page; scheme 1 is the PK-facing readout).
- Total 145 = 89 hyphenated (83 official high + 18-1 corroborated high + 6 ZU1/TRS-only medium) + 25 PSR display (service-manual high, single source) + 31 PSR LED1 blink (1 high corroborated / 30 medium — blink-count rows from one manual page).
- Stop rule: last 2+ search rounds produced no new Sharp-badged residential codes.
- Open gaps: Scribd 2016 residential service doc unreadable (would hold the **LED-blink table for PK lines AH-XP18SHV/AH-X18SEV**); no second independent source for the PSR E/P scheme; PSR manual blink counts **22 and 29 absent from the source chart** (not fabricated); count 16's paired cell missing; **no cassette/ducted/window/floor-standing Sharp code tables anywhere** (floor-standing X24C3 sells in PK but no table published); no PK-localized fault sheet; no Roman-Urdu Sharp pages (all sources EN/JP/CN).

## Offline follow-up paths

- ORIENT Electronics (PK importer per ledmart.pk) — request a PK fault-code sheet / service manual for AH-X/AY-X lines.
- Scribd "5-2016 Error Codes SHARP" — retry with a logged-in session or ask a technician for the paper copy (would close the PK LED-blink gap).
- ManualsLib direct (403) — retry via `r.jina.ai` if easymanua ever drops the AY-X9PSR manual.
