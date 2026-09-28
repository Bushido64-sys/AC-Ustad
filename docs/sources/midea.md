# Midea (AC) — sources

Brand id: `midea` · Category: `ac` · Researched: 2026-09-24
Official: https://mideapakistan.com (Green Leaves Pvt Ltd) · https://mideapakistan.store
Distributor examples: 3tech.pk (Lahore), Zahid Brothers, Surmawala, PakRef (Karachi)
Support: UAN 051 111 000 008 · WhatsApp 0334 770 0008 · customercare.midea@glpl.com.pk
Official error table: **YES (global TSP web app/API)** https://tsp.midea.com/tsp/errorcode/index.html — no static table on mideapakistan.com

**Status: 86 codes across 7 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `midea-pk-split-inverter.json` | PK split high-wall inverter (official TSP Fault Code 2 + LED blink map) | 21 |
| `midea-pk-split-onoff.json` | PK split high-wall on/off (official TSP Fault Code 3, 4-bit — statuses + faults) | 19 |
| `midea-pk-cassette-ducted-idu.json` | Light-commercial inverter IDU after 2019 (cassette/ducted/floor) | 14 |
| `midea-pk-light-commercial-odu.json` | Light-commercial / multi-split ODU after 2019 | 10 |
| `midea-mdv-outdoor-led-blink.json` | MDV outdoor 3-LED flash map (service guide) | 6 |
| `midea-msg-lamp-troubleshooting.json` | MSG lamp troubleshooting (cooling/heat pump, 5 Hz flashes) | 6 |
| `midea-r-series-service-manual.json` | R-series service manual §10.1 indoor error display | 10 |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Midea TSP official error-code tool (JS app + JSON API) | official_support | https://tsp.midea.com/tsp/errorcode/index.html | Full residential/light-commercial code lists via `/tsp/ibos/web/tspApi/errorCodeCategory|errorCodeList|errorCode/detail` endpoints: split hi-wall inverter & on/off, LC IDU/ODU, floor standing, wired controller, lamp blink fields | 2026-09-24 |
| 2 | Midea Pakistan corporate site | official_support | https://mideapakistan.com/ | Brand/distributor recon, AC product categories, quote-form series names, model prefixes | 2026-09-24 |
| 3 | Midea Pakistan support page | official_support | https://mideapakistan.com/support | Green Leaves service contacts; confirmed no public error-code table | 2026-09-24 |
| 4 | Midea Pakistan official online store (AC collection) | distributor_page | https://mideapakistan.store/collections/air-conditioners | PK SKUs/series (Xtreme, Titan, Breezeless E, Eco, cassette MCA3, MCD ceiling exposed, floor standing) | 2026-09-24 |
| 5 | Midea Pakistan store support page | distributor_page | https://mideapakistan.store/pages/support | Installation/service by Green Leaves; no error table | 2026-09-24 |
| 6 | Error codes for Midea air conditioners (airconditioningmanuals) | other | https://www.airconditioningmanuals.com/midea/error-codes-for-midea-air-conditioners/ | LED lamp maps for MSE/MSG/MSX; E/P/R tables; MDV outdoor codes; MCC/MUC, MUA/MCA tables | 2026-09-24 |
| 7 | Midea Service Manuals index (51 PDFs) | service_manual | https://www.airconditioningmanuals.com/midea/ | Google Drive links for service manual PDFs used below | 2026-09-24 |
| 8 | Midea Air-Conditioner Service Manual PDF | service_manual | https://drive.google.com/open?id=1sDYqpNogbp1HgdXWLzMNrf8e3vEFXn22 | Section 10.1 indoor error display E0–E6, P0–P2, P4 + diagnosis flowcharts | 2026-09-24 |
| 9 | Midea MSG Service Manual PDF | service_manual | https://drive.google.com/open?id=1BXj1PXJjGiOD3gjqz876AhAb1EoudN_l | Section 12.2 cooling/heat-pump lamp troubleshooting table (5 Hz flashes) | 2026-09-24 |
| 10 | Midea MCD/MCA cassette & ceiling Service Manual PDF | service_manual | https://drive.google.com/open?id=13aQIvvvsTEXHZFOtLHWjAH5XYoUDOzoG | Indoor lamp+display tables (E0–EC, P0–P4, F5), outdoor E0–E8/P0–P7 table, solving flows | 2026-09-24 |
| 11 | Midea MSC-09HRFN1-QD2E Service Manual PDF | service_manual | https://drive.google.com/open?id=1DB4ioCiNlmg_WUiTPDZz4bmC3uRLoqYu | Inverter split error display list + protection behaviour (cross-check) | 2026-09-24 |
| 12 | Midea Vertu series Service Manual PDF | service_manual | https://drive.google.com/open?id=1YtSBumE2dXheXZ0mxea3_GPaA5Ww8n9S | Troubleshooting indoor error display (same E/P family) | 2026-09-24 |
| 13 | Midea Service Guide (MDV) PDF | service_manual | https://drive.google.com/open?id=1mSdsOaojEyjLC2Jq7dvDOGGHMP50Gvws | MDV indoor LED map + outdoor 3-LED flash definitions (phase, pressure, current, sensors) | 2026-09-24 |
| 14 | Arlington AC & Heating — Midea error codes | other | https://www.arlingtonairconditioningheating.com/midea-air-conditioner-error-codes/ | E0–E9, F0–F9, P0–P5 table with Timer lamp count and Running lamp flashes/sec (user-manual style) | 2026-09-24 |
| 15 | Be Cool Refrigeration UK — Midea error codes 2025 | other | https://becoolrefrigeration.co.uk/media-air-conditioning-error-codes-updated-2025/ | E/F/P series plain-English meanings, cassette LED matrix, non-error displays (AP, cF, CL, dF, FP, SC) | 2026-09-24 |
| 16 | KDM Gas — Midea air conditioner codes | other | https://www.kdmgas.com/services/air-conditioning-service/midea-air-conditioner-codes/ | R Series E0–E6, P0–P4, P6, P7, PF list | 2026-09-24 |
| 17 | Air Care Solutions NSW — Midea error codes | other | https://aircaresolutionsnsw.com.au/midea-error-codes/ | Common-code table with causes + series tagging (R/9V/9A); cross-check only | 2026-09-24 |
| 18 | R-Pro — Midea error codes | other | https://www.r-pro.app/error-codes/midea/ | 20-code compact list (E/EC/EE/F/P); cites arlington as source | 2026-09-24 |
| 19 | 3Tech.pk Midea AC distributor page | distributor_page | https://3tech.pk/midea-ac-distributor/ | PK authorized distributor confirmation (Lahore) | 2026-09-24 |
| 20 | Zahid Brothers Midea AC category | retailer_page | https://estore.zahidbrothers.com/product-category/air-conditioner/midea/ | PK authorized dealer listing (no error codes) | 2026-09-24 |
| 21 | YouTube — Midea F0 F4 F5 F6 FC FP error codes | video | https://www.youtube.com/watch?v=xf2xNYgff30 | Video-title evidence of F-series codes in field use (title-level only) | 2026-09-24 |
| 22 | YouTube — Fix Midea AC E4 E5 E6 F1 F2 … | video | https://www.youtube.com/watch?v=7j_C1C5jpTE | Video-title evidence for sensor F-codes (title-level only) | 2026-09-24 |
| 23 | YouTube — Midea Inverter AC all error codes | video | https://www.youtube.com/watch?v=SQ0juhmgKOM | Video-title evidence of inverter code sets (title-level only) | 2026-09-24 |
| 24 | YouTube playlist — Training on Midea AC faults | video | https://www.youtube.com/playlist?list=PLYK-K5dkM-4ombtTvJuFmplwoZedkSlzt | Training playlist reference (not decoded) | 2026-09-24 |
| 25 | manuals.plus Midea error-code guide pages | other | https://manuals.plus/m/a017a7f970b451b9ade875a1874e248c9006025fcc3968fd8b3dba11ee908eb6 | Attempted; HTTP 403 to this collector — not used for tables | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | onlytroubleshooting.com F0 = refrigerant leak vs official F0 = current overload | Official (TSP + service manual + Arlington) wins; not imported |
| 2 | Air Care P0 = compressor temperature too high vs official P0 = IPM/IGBT overcurrent | Official wins; not imported |
| 3 | Midea China vs Midea US vs Midea PK site copy differences | PK site has no codes; TSP is global (en) — scoped by PK product families; US window/portable codes quarantined |
| 4 | Arlington multi-brand footers (Condura, Kolin, Toshiba, Daikin …) | Other-brand tables on same domain — not merged as Midea |
| 5 | KD Gas / R-Pro aggregation provenance | Treated as secondary; codes only used when matching official TSP/service manual |
| 6 | R-series service-manual E0–E6/P0–P2/P4 vs TSP split-inverter table | Overlapping digits live in **separate series files** (model/source-scoped, never merged) |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|----------------|
| ErrBase `midea-portable-ac-p1-error` | Portable Midea AC water-tray float only; portable units not in PK AC line-up |
| YouTube `7yWEp9tIicw` (EL OC / EL 0C / EC) and Reddit U-shape EL OC | Midea US window / U-shape product codes; not sold via mideapakistan.store |
| YouTube `Hc6PFSRz_MA` (EL-01) | U-shape/window oriented title; EL01 taken from official TSP instead |
| airconditioningmanuals "EACM mobile air conditioner" P1/P2/E1–E3 | Portable/mobile AC family — excluded from PK split/cassette KB |
| Midea MAD020T1N1S1 technical manual E001–E030 | Component-level soft-starter/inverter module codes, not unit-level AC fault display |
| MDV Service Guide sections: water chiller / digital-scroll water units | Chiller & water-side products outside PK retail AC scope |
| Onlytroubleshooting F0 / Air Care P0 | Conflicts with official Midea meanings — not imported |
| Gree / Haier / Dawlance code tables | None imported from Midea-specific pages |
| Washing-machine / appliance codes on mideapakistan.com | Non-AC categories ignored |

## Exhaustiveness (Phase D)

- Official Midea TSP = machine-readable SPA with JSON API (global en); no PK-static table, no PK-Urdu table.
- 7 series scoped separately; classic E/F/P family appears in both official TSP and service-manual tables with distinct provenance.
- PK retailers (aysonline, japanelectronics, maqsoodandsons, gulfelectronics, bijlibazar, w11stop) have no Midea AC error-code URLs (negative).
- No window/portable Midea AC SKUs on mideapakistan.store — window/portable families deprioritized.
- Stop rule met: successive query batches yielded no new PK-scoped code families.

## Offline follow-up paths

- Midea TSP JSON API — cache full category dumps for future re-harvest.
- airconditioningmanuals.com/midea index → 51 service manual PDFs (Google Drive) for per-model deep tables.
- Green Leaves (customercare.midea@glpl.com.pk) — request PK fault-code sheet.
- YouTube PK technician videos — JS-rendered, title-level only; decode offline if needed.
