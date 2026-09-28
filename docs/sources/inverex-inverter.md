# Inverex (inverter) — sources

Brand id: `inverex` · Category: `inverter` · Researched: 2026-09-23
Official PK: https://aptinverex.com (Korangi Creek, Karachi)
Support PK: **+92-21-111-209-988** · info@aptinverex.com · HO 021-32711291 / 0300-0560830

**Status: 122 codes across 3 series files (string 67 · hybrid 30 · voltronic 25).**
AC-scope zero-gap note lives in `docs/sources/inverex.md` (solar F-codes were deferred here on purpose).

## Platform map (why three series files)

| Series file | Models | Platform | Codes |
|---|---|---|---|
| `nitrox-string-3ph.json` | NitroX 12/15/20/25KW-3Ph-5G | Deye G03/G01P3-AM8 OEM twin | F01–F64 + W03/W05/W13 (67) |
| `nitrox-hybrid.json` | NitroX Hybrid SP-5G 3.6–12KW | Deye SG04LP3/SG05LP3 OEM twin | confirmed F-subset (30) |
| `voltronic-numeric.json` | Veyron II/IV, Axpert King, AEROX, VM-II Premium | Voltronic Axpert-class | 01–11, 51–59 + W01–W07/W10/W15 (25) |

**Never mix across files** — same code string can mean different faults (see platform-split table).

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Inverex NITROX 12/15/20/25KW-3Ph-5G User Manual p.22 Table 10.1 (ManualsLib mirror) | service_manual | https://www.manualslib.com/manual/2505242/ | String platform F01–F35 core + W03/W05/W13 | 2026-09-23 |
| 2 | Deye SUN G03/G01P3-AM8 fault tables pp.60–63 (OEM twin) | service_manual | https://deyeinverters.net/en/error-codes/sun-g03-g01p3-am8-series | F36–F64 verbatim platform match; F61/F62 OEM-derived where Inverex snippet incomplete | 2026-09-23 |
| 3 | SolarNevs — Inverex Nitrox hybrid fault chart (PK-focused) | distributor_page | https://solarnevs.com/errors/inverex | Hybrid confirmed rows (F01/F08/F20/F24/F35/F42/F56/F58/F64) | 2026-09-23 |
| 4 | Deye SUN SG04LP3/SG05LP3 hybrid fault tables pp.45–48 (OEM twin) | service_manual | https://deyeinverters.net/en/error-codes/sun-sg04lp3-sg05lp3-hybrid | Hybrid F-set fill (F07/F15/F16/F18/F21/F22/F23/F26/F29/F34/F41/F47/F48/F55/F62/F63) | 2026-09-23 |
| 5 | Scribd — Inverex Inverter Fault Codes List (PK cheat sheet, partial snippets) | forum | https://www.scribd.com/document/718750700 | Hybrid F02/F03 + contested F45/F46 rows (low) | 2026-09-23 |
| 6 | Inverex AEROX 1.2/2.2/3.2KW manual (ManualsLib guide) | service_manual | https://www.manualslib.com/guide/3717935 | Voltronic numeric + W-codes (W01–W07/W10/W15) | 2026-09-23 |
| 7 | APT VEYRON IV 3.2KW manual p.43 fault table | service_manual | https://www.manualslib.com/manual/2182101 | Voltronic 10/11 (PV OC/OV) | 2026-09-23 |
| 8 | Inverex Axpert King 3.2/5.2KW manual (Voltronic OEM) | service_manual | https://solaro.sk/ | Voltronic 01/09 high-confidence cross-check | 2026-09-23 |
| 9 | Inverex Axpert VM-II Premium 1.5K–6.2K manual (Voltronic OEM) | service_manual | https://www.energypower.gr/ | Voltronic 51–59 + W-set source reassignment | 2026-09-23 |
| 10 | Voltronic Axpert MKS II / VM IV OEM manuals (platform cross-check) | service_manual | https://voltronicpower.com/ | Platform confirmation for numeric set (not cited as Inverex alone) | 2026-09-23 |
| 11 | Inverex official site — no fault-table download | official_support | https://aptinverex.com | Negative evidence (nav = inverters/panels/batteries; no docs section) | 2026-09-23 |

## Platform-split traps (same string, different meaning — do NOT merge)

| Code | NitroX 3Ph string | NitroX hybrid | Risk |
|---|---|---|---|
| F13 | Reserved (Deye notes: may fire on phase/relay/mode) — medium | Working-mode change (self-clears) — high | Medium: wrong action on string unit |
| F56 | DC bus low from PV/bus energy (string energy source = PV) — high | DC bus low → battery empty — high | High: tech charges battery on a string unit for no reason |
| F58 | Grid phase-U over-current — high | **BMS communication lost** — high | **HIGH RISK** — always confirm model plate first |

## Conflicts log (resolved in data notes)

| ID | Conflict | Resolution used |
|---|----------|------------------|
| 1 | Hybrid F45: PK Scribd = AC UV over-voltage vs Deye hybrid extract = no F45 row | Single partial source → confidence **low**; meaning flagged for verify badge |
| 2 | Hybrid F46: Deye OEM = backup battery bank fault vs PK sheet = AC UV under-voltage | Two credible sources disagree → confidence **low**; both meanings in `meaning`/`notes`; app must badge verify |
| 3 | F61/F62 numbering incomplete in Inverex p.22 snippet | OEM-derived from Deye G03 → confidence **medium** + notes |
| 4 | String vs hybrid F13/F56/F58 meanings diverge (platform-split) | Separate series files mandatory; cross-warnings in series `notes` + per-code `notes` |
| 5 | Yukon II / Veyron II Premium printed Fault Reference sections | Rows not extractable online (Scribd block / printed manual) → **open gap**, not imported by resemblance |
| 6 | Official aptinverex.com publishes no fault-table download | Negative evidence logged; ManualsLib mirrors of Inverex-branded manuals used instead |
| 7 | Growatt/Must generic F-code blogs surface for "Inverex" queries | **Never cited as Inverex** — only Inverex-branded manuals or confirmed Deye/Voltronic OEM twins |

## Exhaustiveness check (Protocol Phase D)

- Three OEM platforms covered with brand-badged or confirmed-twin evidence: Deye string, Deye hybrid, Voltronic numeric.
- Hybrid chart is a **confirmed subset** (SolarNevs + Deye + partial Scribd) — absence of a code in `nitrox-hybrid.json` does **not** prove the hybrid firmware lacks it; string F02–F06-style rows may exist on hybrid with unextracted charts (logged in series notes).
- Yukon II / Veyron II Premium fault tables = known remaining offline gap (in-box / printed manual / helpline).
- Official site negative: no PDF fault table at aptinverex.com.
- Trailing search stop: platform identity established (Deye + Voltronic twins); further generic "Inverex F code" SERPs recycle the same SolarNevs/Scribd/Deye pages without new Inverex-badged rows.

## Offline follow-up paths

- Helpline **+92-21-111-209-988** / info@aptinverex.com — request NitroX hybrid full fault table + Yukon II / Veyron II Premium service manual pages.
- HO 021-32711291 / 0300-0560830.
- In-box paper manual from a dealer unit (Veyron II p.31 Fault Reference).
