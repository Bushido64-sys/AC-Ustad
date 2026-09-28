# Ziewnic (inverter) — sources

Brand id: `ziewnic` · Category: `inverter` · Researched: 2026-09-24
Official: https://ziewnic.com · Company: Ziewnic (Pvt) Ltd PK
Support: site contact / service centers (ManualsLib has **no** Ziewnic brand page)

**Status: 22 codes across 7 series files — Diamond 5G only (17 faults 01–09/51–53/55–59 + 5 W-codes); 6 empty gap series.**

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `diamond-5g.json` | Diamond 5G hybrid (PV6500/8500/13000 class, 4.5kW manual scan) | 22 (01–09, 51–53, 55–59 + W01/W03/W04/W07/W10) |
| `lenox.json` | Lenox Series 3.0 / Lenox Hybrid | 0 (gap — Deye rebrand claim unconfirmed) |
| `roux.json` | Roux / Roux Lite | 0 (gap) |
| `atom.json` | Atom Series PV11000/14000 | 0 (gap — Growatt footer hint) |
| `lobo.json` | LoBo battery-less PV converter | 0 (gap) |
| `diamond-ii.json` | Diamond II 6G (PV3000/4500, SP-2200) | 0 (gap) |
| `legacy.json` | Mars / Marvel / Max / Z4–Z6 / Axpert / Sun / TriPower | 0 (gap — platform-name traps) |

Diamond 5G table = Voltronic-class bare two-digit codes (not F-prefixed). Program-25 on unit = fault-history recorder, **not** a fault code.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Ziewnic 4.5kW hybrid user manual (Scribd scan, Fault Reference §5.8) | user_manual | https://www.scribd.com/document/771703287 | Diamond 5G fault rows 01–09/51–53/55–59 + W-set (primary, high). OCR note: over-temp threshold 100 vs 120°C uncertain on scan | 2026-09-24 |
| 2 | Ziewnic official site — no fault-table download | official_support | https://ziewnic.com | Series lineup confirmed; **negative** for manuals/fault table | 2026-09-24 |
| 3 | A2Z Solar Ziewnic fault-code videos Pt.01–02 | video | https://www.youtube.com/watch?v=DtISMGthxhI | Corroborating existence of Diamond code series | 2026-09-24 |
| 4 | DIY Solar Forum — Lenox 8kW = Deye rebrand claim | forum | https://diysolarforum.com/ | Platform **hint only** — Deye F01–F64 NOT imported without Ziewnic-badged Lenox manual | 2026-09-24 |
| 5 | Retailer footer "POWERED BY GROWATT PK" (Atom/Mars) | retailer_page | various PK shops | Growatt tables NOT imported — footer ≠ fault-table evidence | 2026-09-24 |
| 6 | 'Axpert' name on legacy SKUs | other | retailer titles | Voltronic platform trap — no import without badge | 2026-09-24 |
| 7 | ManualsLib / manuals.plus sweeps | other | — | **Zero** Ziewnic manuals or code pages | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Lenox = Deye rebrand (forum claim) | Hint only — `lenox.json` empty; Deye F-codes not imported |
| 2 | Atom/Mars = Growatt (retailer footer) | Hint only — empty series; Growatt tables not imported |
| 3 | Legacy 'Axpert' = Voltronic name | Trap — not imported |
| 4 | OCR over-temp 100°C vs 120°C on Scribd scan | Both readings noted in meaning (`≈100–120°C OCR`); confidence stays high for the code itself |
| 5 | Fault 56 battery-detect row thin on Ziewnic OCR | Sibling Voltronic table fills meaning — flagged as sibling-table, medium content accuracy on that row's wording |
| 6 | Diamond II / SP-2200 share "Diamond" name ≠ Diamond 5G table | Separate empty series — do not copy 5G table over |

## Exhaustiveness (Phase D)

- Queries: ziewnic.com manuals, "Ziewnic fault code", Diamond 5G / Lenox / Roux / Atom / LoBo / Diamond II codes, A2Z Solar videos, Scribd scan, ManualsLib, SolarNevs, YouTube, Wayback.
- Stop rule met: only one Ziewnic-badged fault table online (4.5kW Scribd scan); all other lines are catalogs/specs.
- Highest-value unread: remainder of Scribd doc 771703287 (partial read); official in-box manuals for Lenox/Roux/Atom.

## Offline follow-up paths

- ziewnic.com contact — request Lenox/Roux/Atom/Diamond II fault-code manuals.
- A2Z Solar (PK) video descriptions may link PDFs.
- In-box paper manuals from dealer units.
