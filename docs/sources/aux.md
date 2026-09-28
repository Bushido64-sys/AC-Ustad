# AUX (AC) — sources

Brand id: `aux` · Category: `ac` · Researched: 2026-09-24
Official: https://www.auxair.com (global error-codes page; TH mirror identical) · **no auxair.com/pk**
PK presence: United Group of Industries (Lahore) consortium page · retail SKUs AXW-12/18PG/PW, Q-Series (PKR 116,999–184,499: almumtaz, mns, umar) — sales yes, no PK error-code content anywhere
Official published AC error table: **YES** — https://www.auxair.com/global/error-codes.html (25-code IDU list) + per-product-line tables with causes at us.auxair.com/error-codes-faq

**Status: 93 codes across 5 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `aux-official-global-idu.json` | Official AUX global IDU error-code list (25 codes, IDU + ODU split) | 25 |
| `aux-service-manual-2021-wall-floor.json` | 2021 AUX troubleshooting guide — wall/floor split E/F/P codes | 27 |
| `aux-service-manual-2021-odu-led3.json` | 2021 AUX outdoor 3-LED indicator patterns (symbol patterns) | 24 |
| `aux-service-manual-2021-l-subdivided.json` | 2021 AUX Category-L subdivided failures (L0-L9, LA, LC) | 12 |
| `aux-service-manual-2021-portable.json` | 2021 AUX portable A/C codes (E1/E3/E4/E8/P1) | 5 |

Product-line scoped — **E1/E3/E8/F6/H1/H4 meanings differ per line; digits never merged across files.**

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | AUX Global — Error Codes (IDU list) | official_support | https://www.auxair.com/global/error-codes.html | 25 official codes (10 IDU + 15 ODU) | 2026-09-24 |
| 2 | Troubleshooting Guide & Error Codes AUX Brand 2021 (Macroclima/AUX PDF) | service_manual | https://aux.com.ro/wp-content/uploads/2022/05/Macroclima-AUXTrouble_shooting1-2021.pdf | Wall-floor 27 + outdoor 3-LED 24 + Category-L 12 + portable 5 (68 total) | 2026-09-24 |
| 3 | AUX US — error-codes-faq (per-product-line tables) | official_support | https://us.auxair.com/error-codes-faq | Per-product-line causes (page 2 not fetched — gap) | 2026-09-24 |
| 4 | AUX TH mirror — error-codes | official_support | https://www.auxair.com/th/en/error-codes.html | Identical to global list (cross-check) | 2026-09-24 |
| 5 | United Group of Industries — AUX Group (PK) | distributor_page | unitedgroupofindustries.com | PK sales presence evidence (Lahore consortium) | 2026-09-24 |
| 6 | PK retailers (almumtaz, mns, umar) | retailer_page | various | PK SKUs AXW-12/18PG/PW, Q-Series pricing — no code tables | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Official global list vs 2021 wall/floor list — same digits, different product lines | **Separate series files — never merged** |
| 2 | E1/E3/E8/F6/H1/H4 differ per line (split vs portable vs US multi-zone) | Product-line scoped; portable = own file; US multi-zone/cassette/duct scoped out with reasons |
| 3 | AUX OEM claim (shared Chinese controllers) | **Hard rule: shared OEM ≠ brand-safe** — other brands' tables never imported as AUX |
| 4 | Commercial CAC/MULTI code families | Out of PK residential scope (documented) |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|--------------|
| Gree / Midea / Haier / Daikin tables in "AUX AC error codes" SERPs | Other brands — shared OEM does not permit import |
| PEL-branded codes (distributor ≠ manufacturer) | Different brand |
| Non-AC AUX products (electric appliances, etc.) | Out of scope |
| US multi-zone / cassette / duct code families | Different product line, scoped out with conflict log (E1/E3/E8/F6/H1/H4 differ) |
| Other-brand tables on multi-brand pages | Never imported |

## Exhaustiveness (Phase D)

- Official AUX publishes error tables: **YES (global + US per-product-line + TH mirror)** — no PK-localized table (no auxair.com/pk; no PK error content).
- PK sales presence: **YES** (United Group of Industries + retail SKUs) — but zero PK fault documentation.
- 93 total: official global 25 (high) + 2021 guide wall-floor 27 / LED 24 / L 12 / portable 5 (all high, service manual).
- Stop rule: successive batches yielded no new AUX-badged codes beyond the five series.
- Open gaps: us.auxair.com page 2, PK-specific fault sheet, ManualsLib E-Series (403), ac-maintenance-dubai (403).

## Offline follow-up paths

- United Group of Industries (Lahore) — request PK fault-code sheet / service manual.
- us.auxair.com/error-codes-faq page 2 — retry fetch.
- ManualsLib E-Series + ac-maintenance-dubai — retry via r.jina.ai.
- 2021 guide — check for newer revisions (2022+) on aux.com.ro / auxair.com.
