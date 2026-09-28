# Simtek (inverter) — sources

Brand id: `simtek` · Category: `inverter` · Researched: 2026-09-24
Official PK: https://simtek.com.pk · Simtek Power Services, DHA Karachi
Support: 021-35386004 · WhatsApp 0332-2572227

**Status: 85 codes across 9 series files (4 coded + 5 documented zero-code gaps).**

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `simrex.json` | SIMREX hybrid 6KW-48V / PV-7000 Twin | 43 |
| `simron.json` | SIMRON PV5000-VM-IV / 4KW-24V Twin | 27 |
| `flux.json` | Flux STK-PV9000 / STK-PV14000 | 9 |
| `mppt-plus-hybrid.json` | MPPT Plus Hybrid charge controller (text messages) | 6 |
| `ups-xl.json` | Pure sine UPS/Inverter XL | 0 (gap) |
| `ups-mpr.json` | MPR1000E-6000E line-interactive UPS | 0 (gap) |
| `ups-eco.json` | ECO DC King / Planet40 MPPT | 0 (gap) |
| `ups-deluxe.json` | Deluxe Series UPS | 0 (gap) |
| `ups-line-interactive.json` | Line-Interactive Pure Sinewave UPS 1–6 kVA | 0 (gap) |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | SIMREX official manual PDF | user_manual | simtek.com.pk (official PDF) | Warning + fault + parallel + BMS tables (codes 01–59, W-family, BMS 60/69/70/71) | 2026-09-24 |
| 2 | SIMRON official manual PDF | user_manual | simtek.com.pk (official PDF) | Fault + warning tables incl. fault 04/59 and warnings 15/16/EQ not on SIMREX | 2026-09-24 |
| 3 | Flux STK-PV9000/PV14000 manual | user_manual | simtek.com.pk (official PDF) | Codes 00–08 mixed warning/fault (separate numbering — do not merge with SIMREX/SIMRON) | 2026-09-24 |
| 4 | MPPT Plus Hybrid manuals | user_manual | simtek.com.pk (official PDFs) | Full-word LCD WARNING/ERROR strings (text messages, not 2-digit codes) | 2026-09-24 |
| 5 | XL catalog + User-Manual | official_support | simtek.com.pk | Symptom troubleshooting only — no numeric codes (negative) | 2026-09-24 |
| 6 | MPR brochure | official_support | simtek.com.pk | Protection list + LED bar graph claim, no code table (negative) | 2026-09-24 |
| 7 | ECO booklet | official_support | simtek.com.pk | LED meanings only, no fault codes (negative) | 2026-09-24 |
| 8 | Deluxe / Line-Interactive catalogs | official_support | simtek.com.pk | LCD/LED metering shown; no fault-code appendix located (negative) | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Numeric warning digits collide with same-number faults inside SIMREX/SIMRON files | Warnings stored **W-prefixed** (`W01`…`W32`) within each series file; raw digit kept as alias; notes explain |
| 2 | BMS 60/71 collide with parallel-group fault 60/71 in SIMREX | BMS rows stored **BMS-60 / BMS-71** on collision; parallel rows keep bare digits; notes explain dual-use |
| 3 | SIMREX vs SIMRON same digits (01–10, 51–58, W-family) with different meanings | Kept in **separate series files** — never merged (platform-scoped numbering) |
| 4 | Flux 00–08 reuses digits owned by SIMREX warnings | Separate `flux.json` file; series notes say "do not merge" |
| 5 | Voltronic-like bare numeric tables could be imported as Simtek OEM | **Not imported** — no Simtek badge statement on Voltronic OEM PDFs; hint logged only |
| 6 | Simrex Corporation (US) — radios, ham gear | Name collision — hard-excluded (PK Simtek "SIMREX" model line only) |

## Exhaustiveness (Phase D)

- Official PDFs for all coded product lines (SIMREX/SIMRON/Flux/MPPT) read; five UPS/MPPT catalog lines documented as zero-code negative evidence.
- Stop rule met: remaining searches return only Voltronic OEM PDFs (no Simtek badge) and US Simrex radio pages.
- Offline follow-up: request any in-box paper manuals for XL/MPR/ECO/Deluxe/Line-Interactive via WhatsApp 0332-2572227.

## Offline follow-up paths

- simtek.com.pk contact / 021-35386004 — request full code appendix for UPS lines.
- WhatsApp 0332-2572227 (DHA Karachi service centre).
- In-box paper manuals from field units.
