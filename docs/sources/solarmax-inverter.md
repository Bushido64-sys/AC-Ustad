# SolarMax PK (inverter) — sources

Brand id: `solarmax` · Category: `inverter` · Researched: 2026-09-24
Official PK: https://solarmax.pk · Brand owner: **Power Highway (Lahore, since 2007)**
Support: via solarmax.pk contact · Downloads page = datasheets only (manual via customer care)

**Status: 23 codes across 7 series files — ORION only (F01–F10, F51–F53, F55, F57–F59 + 6 W-codes); 6 empty gap series.**

**Brand trap:** this is PK SolarMax (Power Highway) — **NOT** Swiss SolarMax / SolarMax AG (`solarmax.com`). Swiss text-message/Modbus tables hard-excluded.

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `orion.json` | ORION hybrid PLUS/DUAL/PRO/ULTRA (SM-ORION-DUAL-4KW etc.) | 23 (F01–F10, F51–F53, F55, F57–F59 + W01/W03/W04/W07/W10/W15) |
| `orra.json` | ORRA off-grid 1.5–3 kW | 0 (gap) |
| `falcon.json` | FALCON hybrid | 0 (gap) |
| `solon.json` | SOLON hybrid IP65 | 0 (gap) |
| `onyx.json` | ONYX Lite/Dual/UL single-phase | 0 (gap) |
| `ongrid-5g-6g.json` | On-Grid 5G/6G 10–60 kW | 0 (gap) |
| `ups-legacy.json` | Legacy UPS 1–3 kVA | 0 (gap — Scribd manual not extracted) |

ORION table = Voltronic/Axpert-class numeric F-codes. **Deye F01 ≠ this F01** — never cross-apply.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | SolarMax SM-ORION-DUAL-4KW user manual (Scribd scan, PK-badged fault table pp.42–49) | user_manual | https://www.scribd.com/document/844875225 | Full ORION F01–F59 core + W-codes (primary, high) | 2026-09-24 |
| 2 | SolarNevs — PK SolarMax error codes guide (cites Orion manual) | distributor_page | https://solarnevs.com/errors/solarmax | Independent corroboration of ORION rows | 2026-09-24 |
| 3 | SolarMax PK official downloads | official_support | https://solarmax.pk/downloads | Datasheets only — **no fault-table PDF** (negative; manual via customer care) | 2026-09-24 |
| 4 | Voltronic Axpert VM/MKS OEM manual (platform wording match) | service_manual | https://voltronicpower.com/ | OEM cross-check for wording/platform — cited only as platform, not as SolarMax | 2026-09-24 |
| 5 | Swiss SolarMax (solarmax.com) code pages | other — **EXCLUDE** | https://www.solarmax.com | Different company; text-message tables **never imported** | 2026-09-24 |
| 6 | ORRA/FALCON/SOLON/ONYX/5G/6G product pages + Scribd datasheet | official_support / other | solarmax.pk | Specs/marketing only — zero fault rows | 2026-09-24 |
| 7 | Legacy UPS manual (Scribd doc 340353733) | user_manual | https://www.scribd.com/document/340353733 | Exists but fault table **not extracted** — open gap | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | "SolarMax" = Swiss AG vs Power Highway PK | PK brand scoped by solarmax.pk + SM-ORION-* model prefix; Swiss tables hard-excluded |
| 2 | ORION F-codes vs Deye F01–F64 same-looking strings | Different platforms (Voltronic vs Deye) — never cross-apply; series notes warn explicitly |
| 3 | ORRA/FALCON/ONYX likely Axpert-class (platform inference) | Inference ≠ evidence — empty series, not imported |
| 4 | SolarNevs vs Scribd ORION wording | Agreeing rows → high; Scribd is primary page-located source |
| 5 | On-Grid 5G/6G feature pages mention "protection" | No code numbers published — zero-code gap |

## Exhaustiveness (Phase D)

- Queries: solarmax.pk manuals/downloads, "SolarMax inverter error code", SM-ORION fault table, ORRA/FALCON/SOLON/ONYX codes, Scribd legacy UPS, SolarNevs, YouTube, Wayback of solarmax.pk.
- Stop rule met: only ORION carries a PK-badged table; all other lines return datasheets/marketing.
- Highest-value unread: Scribd legacy UPS doc 340353733 fault pages; ORRA/FALCON in-box manuals; customer-care PDF request.

## Offline follow-up paths

- solarmax.pk contact / customer care — request ORION full manual PDF + ORRA/FALCON/ONYX fault tables + legacy UPS manual.
- Power Highway dealer service sheets.
