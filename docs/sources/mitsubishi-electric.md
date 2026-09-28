# Mitsubishi Electric (AC) — sources

Brand id: `mitsubishi-electric` · Category: `ac` · Researched: 2026-09-24
Official: https://www.mitsubishielectric.com (Cooling & Heating) · **no official PK site** (mitsubishielectric.com/pk → 404)
PK channel: PEL distributes Mitsubishi Electric AC in PK (roadmap note; recon) · retailers aysonline · japanelectronics · maqsoodandsons · gulfelectronics · bijlibazar · w11stop
Official published AC error table: **YES (not PK-localized)** — MY support page https://www.mitsubishielectricmalaysia.com/troubleshooting-ac/ · IE lookup tool (JS-only) · SG service PDFs (MrSlimErrorCode-V2, CityMultiErrorCode_V4) · ME UK/IE "Fault Code Check List" (orionair mirror)

**Status: 116 codes across 4 series files.**

## Hard scope rule (disambiguation)

**Mitsubishi Electric ≠ Mitsubishi Heavy Industries (MHI) ≠ Mitsubishi Motors.** MHI tables (e.g. E75 LED flash) are never imported as Mitsubishi Electric. Same for Toshiba RAS-/RAV- lookalike schemes.

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `mitsubishi-electric-mrslim-a-control.json` | Mr. Slim / split A-control check codes (P/E/U/F/A letter+digit + LED flash) | 51 |
| `mitsubishi-electric-mrslim-k-control.json` | K-control legacy codes (membrane remote + PUH-EK outdoor LEDs LD1-LD8) | 17 |
| `mitsubishi-electric-citymulti-pucy.json` | City Multi VRF outdoor PUCY-YKA/YKD/YKE 4-digit codes | 25 |
| `mitsubishi-electric-citymulti-pumy.json` | City Multi VRF outdoor PUMY-CP YKM/YBM 4-digit codes (+ 2-digit aliases) | 23 |

Control-type scoped: A-control vs K-control vs City Multi PUCY vs PUMY — **same digits across generations never merged**.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | ME Malaysia — troubleshooting-ac (official A-control check codes) | official_support | https://www.mitsubishielectricmalaysia.com/troubleshooting-ac/ | Primary A-control codes (P/E/U/F/A + 00) with LED flash + beeper routes | 2026-09-24 |
| 2 | MrSlimErrorCode-V2.pdf (ME SG service PDF) | service_manual | https://www.mitsubishielectric.com.sg/getmedia/2d5c4795-7b48-4a89-adff-ae5589e21020/MrSlimErrorCode-V2.pdf | A-control corroboration (10 codes) | 2026-09-24 |
| 3 | ME UK/IE Fault Code Check List (orionair.co.uk mirror) | service_manual | https://orionair.co.uk/PDF/Mitsubishi_elec_Fault_codes.pdf | 21-code checklist corroboration | 2026-09-24 |
| 4 | CityMultiErrorCode_V4.pdf (ME SG, official, rev 20250819r1) | service_manual | https://www.mitsubishielectric.com.sg/getmedia/f972a7c3-b07e-4c11-89a5-f8b3ad1c7659/CityMultiErrorCode_V4.pdf | PUCY 25 + PUMY 23 4-digit codes (primary) | 2026-09-24 |
| 5 | CityMultiErrorCode_V3.pdf (ME SG) | service_manual | ME SG getmedia (V3) | Corroboration for PUCY/PUMY rows | 2026-09-24 |
| 6 | ME HK user manual JG79Y754H02 | user_manual | https://www.mitsubishielectric.com.hk/uploads/download/496/JG79Y754H02.pdf | User-facing check-code corroboration | 2026-09-24 |
| 7 | arma.org.au Mitsubishi-Error-Codes.pdf | other | https://www.arma.org.au/wp-content/uploads/2017/02/Mitsubishi-Error-Codes.pdf | 9-code K-control/A-control corroboration | 2026-09-24 |
| 8 | K-control service manual PDF (npcdn mirror) | service_manual | https://cdn1.npcdn.net/userfiles/27434/download/.../Mitsubishi_Error_Codes... | K-control 17 codes (EO, P1-P8, LD1-LD8) primary | 2026-09-24 |
| 9 | ME IE support lookup tool | official_support | mitsubishielectric.ie (JS-only) | Official existence proof; content not extractable | 2026-09-24 |
| 10 | PK retailer pages | retailer_page | aysonline · maqsoodandsons · gulfelectronics · bijlibazar · w11stop | PK SKU confirmation; no code tables | 2026-09-24 |

manualslib.com → 403; ME Ireland lookup JS-only; no PK/Urdu-origin code source found (stop rule met).

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | A-control vs K-control same digits (P1/P2/...) different generations | **Separate series files — never merged** |
| 2 | City Multi PUCY vs PUMY 4-digit overlaps (0403, 1102, 1500...) | **Separate series files — model-scoped, never merged** |
| 3 | 2-digit aliases on PUMY (Ed, U2, UE, U7, P6) | Kept as aliases in PUMY file only; not duplicated across files |
| 4 | No PK-local official table | ME regional official tables (MY/SG/UK-IE) apply to PK-sold families; noted |
| 5 | MHI E75-style tables surfacing in search | Quarantined — different company (Mitsubishi Heavy Industries) |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|--------------|
| **Mitsubishi Heavy Industries (MHI)** tables (E75 etc.) | Different company — never imported |
| Mitsubishi Motors / car OBD codes | Wrong company |
| Hitachi/Toshiba/Daikin lookalike schemes (RAS- prefixes, etc.) | Other brands |
| Generic Chinese inverter E-code tables republished on content farms | Not ME scheme |
| Non-AC Mitsubishi products (elevator, semiconductor, etc.) | Out of scope |
| Other-brand tables on multi-brand pages | Never imported |

## Exhaustiveness (Phase D)

- Official ME publishes error tables: **YES but not PK-localized** (MY page, IE tool, SG PDFs, UK/IE checklist). No mitsubishielectric.com/pk.
- 4 series: A-control 51 + K-control 17 + City Multi PUCY 25 + PUMY 23 = **116 entries** (98 unique code strings with aliases).
- Display routes: remote_check 23 / outdoor_led 23 / central_controller 50 / indoor 1 (A/K/City Multi mapped accordingly).
- Stop rule: successive batches yielded no new ME-badged codes beyond the four series; no PK/Urdu-origin table exists.
- Open gaps: PK distributor fault sheet (PEL), IE lookup export, full City Multi indoor/alarm list beyond V4 PDF.

## Offline follow-up paths

- PEL (PK distributor) — request PK fault-code sheet / service manual.
- ME SG PDFs — re-fetch for V5+ revisions of CityMultiErrorCode / MrSlimErrorCode.
- ManualsLib 403 — retry via r.jina.ai for Mr. Slim service manuals.
- ME IE lookup — browser-automate JS tool to export full list.
