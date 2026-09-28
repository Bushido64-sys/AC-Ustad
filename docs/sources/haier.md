# Haier (AC) — sources

Brand id: `haier` · Category: `ac` · Researched: 2026-09-24
Official PK: https://www.haier.com/pk · Haier Electric Pakistan
Support: via haier.com/pk self-service portal (engineer-only error policy)

**Status: 54 codes across 7 series files.**

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `haier-pk-split-inverter.json` | PK split inverter (T3 Plus HSU-*, official master table) | 31 |
| `haier-ays-extras.json` | PK retailer extras not on official table (E8/F0/F5/F10) | 4 |
| `haier-hdu-42ca03-duct.json` | HDU-42CA03 ducted (model-scoped) | 6 |
| `haier-hdu-24ca03-duct.json` | HDU-24CA03 ducted (model-scoped) | 4 |
| `haier-floor-standing-cabinet.json` | Floor standing / tower / cabinet / cassette | 6 |
| `haier-video-inverter-extras.json` | Video-only extras (E18, 88) | 2 |
| `haier-us-haierappliances-e0.json` | E0 single aggregator page (not on PK official) | 1 |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Haier PK master error table ("display shows E7 or F8") | official_support | https://www.haier.com/pk/service-support/self-service/20160120_103780.shtml | **31 official codes**: E1/E2/E4/E5/E7/E9/E14, F1–F4, F6–F9, F11–F14, F19–F25, F27/F28, F35, F43, FE — source of record | 2026-09-24 |
| 2 | Haier PK per-code FAQs (F1/F4/F6/F7/F8/F9/F19/E7/generic) | official_support | https://www.haier.com/pk/service-support/self-service/ (see research log for exact FAQ ids) | Official meanings + engineer-only policy for those codes | 2026-09-24 |
| 3 | AYS Online Haier error codes + troubleshooting | retailer_page | https://www.aysonline.pk/docs/haier-ac-error-codes/ | Per-code solutions; **extras E8/F0/F5/F10**; E9 drainage meaning (conflict) | 2026-09-24 |
| 4 | BijliBazar Haier AC Error Code List 2026 | retailer_page | https://www.bijlibazar.com/haier-ac-error-code-list-2026/ | Second PK copy corroborating AYS extras + E9 drainage | 2026-09-24 |
| 5 | Haier US Forward Series Service Manual §7.3 | service_manual | https://www.haierappliances.com/content/downloads/ductless/Forward-Series/Haier-Forward-Series-Service-Manual.pdf | Outdoor LED1 blink counts + detection logic (model family differs; semantics match PK) | 2026-09-24 |
| 6 | Arlington AC & Heating Haier decoder (HDU tables) | technician_note | https://www.arlingtonairconditioningheating.com/haier-air-conditioner-error-codes/ | HDU-42CA03 (6) + HDU-24CA03 (4) model tables, F66/E12 aliases, extra blinks | 2026-09-24 |
| 7 | Japan Electronics PK Haier list | retailer_page | https://japanelectronics.com.pk/blogs/all/haier-ac-error-code-list-pdf | Corroboration of standard table only | 2026-09-24 |
| 8 | YouTube PK/Urdu videos (floor-standing FA/E3/E5/E6/E9/F1, cassette, E18, 88) | video | youtube.com (see research log) | Occurrence on large form factors + 2 video-only codes | 2026-09-24 |
| 9 | Appliance Code Hub Haier E0 | other | https://www.appliancecodehub.com/haier-air-conditioner-error-e0.html | E0 page only (other per-code pages soft-404) | 2026-09-24 |
| 10 | errors.codes Haier E6 | other | https://errors.codes/en/haier/air-conditioning/error-e6 | E6 meaning (not on official split table) | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | E9: AYS+Bijli = drainage/water-flow vs official = indoor overload in heating | **Official wins**; drainage logged in per-code notes, not adopted |
| 2 | F1: Japan Electronics = communication vs official = IPM/power module | Official F1 FAQ wins; JE F1 article quarantined |
| 3 | E7: JE article = outdoor fan vs official = indoor/outdoor communication | Official wins; JE E7 article quarantined |
| 4 | E8/F0/F5/F10 on AYS+Bijli but absent from official table | Separate `haier-ays-extras` series (provenance preserved) |
| 5 | HDU-42 vs HDU-24 vs split: same digits, different meanings (E0/E1/E4/E7) | Separate model-scoped series files — never merged |
| 6 | hvacinexpert / Subhan / LahoreHomeServices / MyKarigar tables | Quarantined — conflict with official + Gree-family H6/U4 + mixed Gree rows |
| 7 | F42 videos | Wrong product (washing machine) — excluded |
| 8 | Floor-standing E3/E5/E6/E9/F1 meanings | Video-title occurrence only → low; meanings borrowed from split where noted |

## Exhaustiveness (Phase D)

- Official PK master table (31) + FAQs + service-manual blinks + 2 PK retailer tables + ducted technician tables + video sweep.
- Stop rule met: last two query batches (floor-standing extras, cassette, official mirrors) returned only already-known pages.
- Highest-value offline follow-up: PK T3 Plus / floor-standing **service manual PDF** (not published online); official PK HTML-only fault sheet (no PDF).

## Offline follow-up paths

- haier.com/pk self-service portal + hotline — request PK service manual / fault appendix PDF.
- In-box paper manuals from T3 Plus / floor-standing units.
- YouTube video descriptions for cassette code sets (text extraction when available).
