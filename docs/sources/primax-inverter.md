# Primax (inverter) — sources

Brand id: `primax` · Category: `inverter` · Researched: 2026-09-24
Official PK: https://primaxsolarenergy.com · Primax Solar Energy (i.Solar app)
Support: UAN 042-111-120-152

**Status: 51 codes across 4 series files (1 coded + 3 documented zero-code gaps).**

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `galaxy-dual.json` | Galaxy Dual PV12000+ MAX Ultra / PV11000 / PV12000 Pro | 51 (F01–F32 + W01–W19) |
| `nexa.json` | Nexa series (NEXA-6KW) | 0 (gap — manual 403/not archived) |
| `venus.json` | Venus series | 0 (gap — truncated Wayback captures / image PDFs) |
| `galaxy.json` | Galaxy (non-Dual) PV2400 / 3/6/11KW | 0 (gap — datasheets only, no code table) |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | PRIMAX GALAXY DUAL PV12000+ MAX 11KW ULTRA User's Manual (Wayback range-stitched, 53p) | user_manual | https://web.archive.org/web/20241005190100id_/https://primaxsolarenergy.com/wp-content/uploads/2024/08/PRIMAX-GALAXY-DUAL-PV12000-MAX-11KW-ULTRA-Manual.pdf | **F01–F32 faults** (p. triage preamble: output off, continuous buzzer, RED solid) + **W01–W19 warnings** (p.18 triage table) | 2026-09-24 |
| 2 | primaxsolarenergy.com product pages / app | official_support | https://primaxsolarenergy.com | Model roster (Galaxy Dual, Nexa, Venus, Galaxy) confirmed | 2026-09-24 |
| 3 | NEXA-6KW manual fetch attempts | other | — | 403 / not archived (negative — documented gap) | 2026-09-24 |
| 4 | UserManuals Wayback Venus captures | other | web.archive.org | Truncated at 1 MiB or 404 (negative) | 2026-09-24 |
| 5 | Voltronic Axpert manuals hosted on primax site | user_manual | primaxsolarenergy.com | **OEM hint only** — not imported into Primax code space | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | primax.com.pk = pest control; Primax Electronics VR | **Hard-excluded** — only primaxsolarenergy.com is in scope |
| 2 | W18/W19 labels: Appendix II text vs triage table | Conflict flagged per-code; pending second source — confidence kept as researched |
| 3 | F20 appears twice in triage table | Specific row treated as authoritative; noted |
| 4 | F04/F21/F30/F31/F32 + W01/W02/W03/W12–W15 have no triage action row | Empty `solutions: []` legal; notes say "manual gap" |
| 5 | Voltronic Axpert PDFs on Primax site | OEM hints only — codes stay under `voltronic-platform` |
| 6 | Wayback PDF may be truncated (1 MiB limit) on other models | Documented risk; Galaxy Dual capture range-stitched successfully |

## Exhaustiveness (Phase D)

- Galaxy Dual official manual fully read (both fault and warning YAML blocks from research extraction).
- Nexa/Venus/Galaxy documented as gaps after archive/403/image-only sweeps.
- Stop rule met: no other Primax-badged code tables online.
- Offline follow-up: UAN 042-111-120-152 — request Nexa/Venus manuals.

## Offline follow-up paths

- UAN 042-111-120-152 — request Nexa-6KW + Venus manual PDFs.
- i.Solar app / dealer portal screenshots of fault appendix.
- In-box paper manuals.
