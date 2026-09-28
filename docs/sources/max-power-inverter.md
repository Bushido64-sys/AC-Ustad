# Max Power (inverter) — sources

Brand id: `max-power` · Category: `inverter` · Researched: 2026-09-24
Official PK: https://maxpower.com.pk · Company: Maxell Power (Pvt) Ltd PK
Support: via maxpower.com.pk site contact (no published helpline found)

**Status: 0 codes — zero-code documented gap across 6 series files (Voltas HYD-H6/H4, on-grid, Suntronic, PV Tech, Sofar-distributed).**

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `voltas-hyd-h6.json` | Voltas HYD-H6 hybrid 6K–20K | 0 (gap) |
| `voltas-h4.json` | Voltas H4 hybrid 6K–14K | 0 (gap) |
| `voltas-ongrid.json` | Voltas on-grid KTLX/KTLM | 0 (gap) |
| `suntronic.json` | Suntronic PV5000–12000 | 0 (gap) |
| `pv-tech.json` | PV Tech 1200–15000 | 0 (gap) |
| `sofar-distributed.json` | Sofar on-grid (Max Power distributed) | 0 (gap) |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Max Power official site (products/blog/downloads) | official_support | https://maxpower.com.pk | Product lines confirmed (Voltas HYD-H6/H4, Suntronic, PV Tech, Sofar KTLX distribution); **blog says "check your manual" but no manual/fault table is downloadable** (negative) | 2026-09-24 |
| 2 | Official blog troubleshooting posts | technician_note | https://maxpower.com.pk/blog | Advice-only text ("check manual") — zero named codes | 2026-09-24 |
| 3 | Retailer listings (PriceOye, PakRef, local shops) | retailer_page | various PK shops | Model confirmation only — no fault tables | 2026-09-24 |
| 4 | ManualsLib / manuals.plus / Scribd sweeps | other | — | **Zero** Max Power / Voltas-inverter code pages | 2026-09-24 |
| 5 | SolarNevs Max Power search | distributor_page | https://solarnevs.com | **Explicit empty** — no Max Power entry | 2026-09-24 |
| 6 | Sofar HYD ID0xx tables (platform adjacency check) | service_manual | sofar-solar docs | NOT imported — HYD naming adjacency ≠ OEM proof for Voltas-badged units | 2026-09-24 |
| 7 | Deye / Voltronic F-code generic tables | service_manual | deyeinverters.net / voltronicpower.com | NOT imported — no Max Power badge or confirmed twin | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | "Voltas" name = Voltas India (Tata AC brand) codes surface in search | **Hard-excluded** — Voltas AC India E/F codes never imported into PK Max Power inverter brand |
| 2 | MAX Power marine thrusters / US MAX Power | Name collision — hard-excluded |
| 3 | Sofar distributed on-grid under Max Power; Sofar ID0xx fault tables exist | Sofar tables stay under future Sofar brand entry; `sofar-distributed.json` kept empty with pointer note |
| 4 | HYD-H6 name adjacency to Sofar HYD hybrid series | Hint only — twin unconfirmed without Max Power-badged manual or explicit OEM statement; not imported |
| 5 | Generic Deye/Voltronic F-lists in search results | Never imported (no badge, no twin confirmation) |

## Exhaustiveness (Phase D)

- Queries covering: official site pages/blog, "Max Power inverter error code", "Voltas HYD-H6 fault code", "Voltas H4 inverter code", Suntronic/PV Tech codes, ManualsLib, Scribd, SolarNevs, YouTube, PK retailer pages.
- Stop rule met: trailing batches returned only Sofar/Deye/Voltronic generic tables and Voltas-India AC codes — no Max Power-badged fault table exists online.
- Highest-value offline follow-up: request the in-box paper/manual PDF for HYD-H6 / H4 via maxpower.com.pk contact; dealer service sheets.

## Offline follow-up paths

- maxpower.com.pk contact form — request HYD-H6 / H4 fault-code manual PDF.
- In-box paper manuals from dealer units.
- Service-centre visit with unit model plate.
