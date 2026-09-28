# Gree (AC) — sources

Brand id: `gree` · Category: `ac` · Researched: 2026-09-24
Official: https://www.greecomfort.com (US licensee) · Gree Electric Zhuhai (China)
PK distributor found: DWP Home https://dwphome.pk/gree (no public error table)
Support: greecomfort.com JS lookup tool; no official static PK table

**Status: 70 codes across 8 series files (66 fault/status + 4 LED-blink semantics).**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `e.json` | E-family (E0–E9, EE, EU) | 12 |
| `f.json` | F-family sensors (F0–F6, F8/F9, FA/FC/FE/FH) | 13 |
| `h.json` | H-family (H0–H9, HC, HE) | 12 |
| `l.json` | L-family (L3, LC, LD, LE) | 4 |
| `p.json` | P-family protections (P0, P5–P8, PA/PC/PH/PL/PU/PP) | 11 |
| `u.json` | U-family (U1–U5, U7–U9) | 8 |
| `other.json` | C5, oE, b5, b7, L9, FF | 6 |
| `led-blink.json` | Lamp↔family semantics + blink reading method | 4 (info/behavior) |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | AC Guide PK Gree error list | technician_note | https://acguide.pk/gree-ac-error-codes-list/ | Detailed E/F/H/L/P/U/other + causes/fix steps; model prefix guide | 2026-09-24 |
| 2 | AYS Online PK Gree error codes | retailer_page | https://www.aysonline.pk/docs/how-to-fix-gree-ac-common-error-codes/ | Full family tables (~70) + causes/solutions + reset procedure | 2026-09-24 |
| 3 | Japan Electronics PK Gree 2024 list | retailer_page | https://japanelectronics.com.pk/blogs/all/error-code-list-of-gree-ac-in-2024 | Second PK agreeing source (confidence upgrades) | 2026-09-24 |
| 4 | Electra Fix PK Gree breakdown | technician_note | https://electrafix.pk/gree-ac-error-code-breakdown/ | PK technician breakdown; E4/E6/H6 focus | 2026-09-24 |
| 5 | Maqsood & Sons Islamabad Gree codes | technician_note | https://maqsoodandsons.com/gree-error-codes/ | E/F/H/P/U subsets + LED flash reading method | 2026-09-24 |
| 6 | GREE Comfort official mini-split error article | official_support | https://www.greecomfort.com/news-and-events/understanding-gree-mini-split-error-codes/ | Official meanings/fix for E1/E3/E5/E7, F1–F5, H1/H5, C5, U3, L9 (~13) | 2026-09-24 |
| 7 | GREE Comfort interactive lookup | official_support | https://www.greecomfort.com/troubleshoot-error-codes/ | Confirmed official full lookup exists (JS-only, not extractable) | 2026-09-24 |
| 8 | HeatPumpAnswers Gree lookup | other | https://heatpumpanswers.com/gree-error-codes/ | Compact E/F/H/C/EE lookup + E6 deep-dive | 2026-09-24 |
| 9 | AirConditionerManuals Gree page | other | https://www.airconditioningmanuals.com/gree/gree-air-conditioners-error-codes/ | LED lamp↔family semantics, 3 s blink period; narrative meanings **quarantined** | 2026-09-24 |
| 10 | R-Pro Gree catalog | other | https://www.r-pro.app/error-codes/gree/ | 39-code cross-check (arlington "official" claim unverified) | 2026-09-24 |
| 11 | DWP Home Gree (PK distributor) | distributor_page | https://dwphome.pk/gree | PK lineup confirmation (split/floor/duct); no table | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | **F-series index conflict**: official greecomfort F1=indoor ambient … F5=discharge vs PK tables F0=indoor ambient / F1=indoor evaporator … | Two valid Gree mappings by series/region — **kept with per-code notes; never merge indices blindly** |
| 2 | acguide H6 = outdoor condenser fan vs majority (AYS/JE/ElectraFix/official/heatpumpanswers) = indoor fan no-feedback | Majority wins; acguide H6 row quarantined as note |
| 3 | airconditioningmanuals RU-narrative meanings (E0 start voltage, E5 compressor overload, E6 no phase, E8 over-cool, F0 discharge, H8 drain, H9 heater) | Quarantined — conflicts with 3+ PK/official tables; model-variant notes only |
| 4 | almumtaz.com.pk P0/P1/P2 mapping | Conflicts with standard Gree P-series — not used |
| 5 | R-Pro cites arlington as "Official Gree source" | Unverified third-party — not treated as official |
| 6 | Haier/Dawlance/Orient/PEL tables on sibling retailer pages | Different brands — only Gree-tagged articles used |
| 7 | Gree China / GMV VRF commercial alphabets | Out of PK residential scope unless model confirmed |
| 8 | AYS / Japan Electronics / Electra Fix near-identical series lists | Agreement is not fully independent (likely shared lineage) — noted in confidence |

## Exhaustiveness (Phase D)

- Official article + JS tool confirmed; 5 PK sources cross-checked; LED semantics recovered; blink-count PDF scanned (no text); manuals.plus 403; Scribd challenge.
- Stop rule met: last 2 query batches added only re-agreement, no new letter families.
- Offline follow-up: service-manual-level tables per model (Bora/Lomo/U-Match) via airconditioningmanuals index (Google Drive PDFs); idealairconditioningservice scanned blink PDF → visual read (`pdftoppm`).

## Offline follow-up paths

- greecomfort.com/troubleshoot-error-codes (JS) — manual screenshot harvest.
- DWP Home — request PK fault-code sheet.
- airconditioningmanuals.com/gree index → service manual PDFs (visual read for blink maps).
