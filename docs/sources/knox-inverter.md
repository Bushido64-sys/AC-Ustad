# Knox (inverter) — sources

Brand id: `knox` · Category: `inverter` · Researched: 2026-09-24
Official: https://knoxpv.com · Powered/warranted by **ELECTRO PAKISTAN** (Electro industries Pvt Ltd)
Support: UAN **0304-111(KNOX)5669** · support@knoxpv.com · www.knoxpv.com

**Status: 89 codes across 8 series files — Krypton VM IV 26 + Krypton FCS 36 + Xenon/WP 27; 5 empty/gap series (Zynex, Xerox G4, Solplanet quarantine, ThinkPower, on-grid 15/50KW).**

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `krypton-vmiv.json` | Krypton hybrid VM IV twin class (4/6KW twin) | 26 (01–10, 51–53, 55, 57–59 + W-prefixed warnings W01–W32) |
| `krypton-fcs.json` | Krypton hybrid InfiniSolar FCS 3.6/5.6KW | 36 (01/02/03/05–12, 51–53, 55–58 + W-prefixed warnings incl W-bP + parallel 60/71/72/80–86) |
| `xenon.json` | Xenon 9000/11000/14500T/16000T/22500T (WP twin) | 27 (01–12, 51–53, 55–58 + 8 W-prefixed warnings) |
| `zynex.json` | Zynex ZX-3M-0816 / ZX-3M-1020 IP66 hybrid | 0 (datasheet only — no fault table) |
| `xerox-g4.json` | Xerox G4 (roadmap series) | 0 (no Knox-badged manual found) |
| `solplanet.json` | Solplanet/AISWEI LTG2/G3/G4 | 0 (**QUARANTINE** — AISWEI manuals have no Knox badge; aswpdfknoxpv.pdf was 404 HTML) |
| `thinkpower.json` | ThinkPower on-grid (knoxpv.com) | 0 (TOC has fault/LED sections; body garbled in text layer) |
| `ongrid-3p.json` | Knox on-grid 15KW/50KW | 0 (Knox-badged datasheets only — specs, no codes) |

### Platform / badge evidence

- **Xenon OEM row** (knoxpv.com Xenon series datasheet): Xenon 9000 model platform = `INFINI V 4 WP 6KW`; 11000 = `AXPERT WP TWIN 8K`; Twin three-phase = `INFINISOLAR WP` — **brand-badged platform confirmation** (medium import under OEM-twin rule).
- **Krypton** line confirmed in knoxpv.com catalog (Krypton 5000/5600/12002/15002 datasheets exist under Knox/Electro badge); fault tables used are Voltronic OEM PDFs **without** Knox badge in the body — medium via platform-twin rule.
- **gdrive_krypton.pdf** = wrong file (IIT Bombay) — excluded.
- **usrfiles_hybrid.pdf** = Livoltek-branded — excluded.
- **aswpdfknoxpv.pdf** = 404 HTML page saved as PDF — excluded.
- **infini_viv.pdf** = Motoma-branded — excluded from Xenon import (used `infini_wp_manual.pdf` instead).

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | knoxpv.com product catalog / Xenon series datasheet | official_support | https://knoxpv.com | Brand badge, lineup (Krypton/Xenon/Zynex), OEM platform row, UAN 0304-111(KNOX)5669, Electro Pakistan warranty | 2026-09-24 |
| 2 | knoxpv.com Zynex / Xerox / ThinkPower / on-grid 15–50KW datasheets | official_support | https://knoxpv.com | Product existence only — zero fault tables (negative) | 2026-09-24 |
| 3 | Voltronic VM IV twin hybrid user manual (local extract `vmiv_twin.pdf`) | user_manual | file:/tmp/opencode/knox/vmiv_twin.pdf | Krypton VM IV fault table 01–10/51–53/55/57–59 + W-set (medium — no Knox badge) | 2026-09-24 |
| 4 | InfiniSolar FCS 3.6/5.6 hybrid manual (local `fcs_infini.pdf`) | user_manual | file:/tmp/opencode/knox/fcs_infini.pdf | Krypton FCS faults incl 11/12/56 + W bP + parallel 60/71/72/80–86 (medium) | 2026-09-24 |
| 5 | InfiniSolar WP 6KW manual (local `infini_wp_manual.pdf`) | user_manual | file:/tmp/opencode/knox/infini_wp_manual.pdf | Xenon 9000 table 01–12/51–53/55–58 + W (medium via Xenon OEM row) | 2026-09-24 |
| 6 | Solplanet / AISWEI official E-code manuals | service_manual | https://www.solplanet.net | NOT imported — no Knox badge; E01/E05/E33… list quarantined | 2026-09-24 |
| 7 | ThinkPower on-grid manual (knoxpv-branded) | user_manual | file:/tmp/opencode/knox/thinkpower_fx.pdf | NOT extracted — §5.2.5 fault body garbled in text layer | 2026-09-24 |
| 8 | Livoltek / Motoma / IIT Bombay PDFs found under Krypton search | other | — | EXCLUDED — wrong brand (badge mismatch) | 2026-09-24 |
| 9 | DDG/YouTube/Facebook Knox fault-code sweeps | video / forum | youtube.com / facebook.com | No complete Knox-badged table found; open for future | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Voltronic OEM PDFs have no "knox" string; Krypton is a Knox product | Import at **medium** under platform-twin rule (brand catalog confirms Krypton; table is OEM twin). Per-code notes record "no badge on PDF body". |
| 2 | Xenon OEM row lists INFINI V WP / AXPERT WP / INFINISOLAR WP platforms | **Brand-badged platform confirmation** on Knox datasheet — medium import of WP table for Xenon series with OEM-row note. |
| 3 | Solplanet E-codes look complete but manuals are AISWEI-only | Quarantine — `aswpdfknoxpv.pdf` was 404; zero Knox-badged Solplanet manual. Empty series with warning note. |
| 4 | gdrive_krypton / Livoltek / Motoma PDFs under Krypton search | Wrong-file trap — excluded; Krypton uses VM IV + FCS tables only. |
| 5 | ThinkPower PDF is Knoxpv-branded but fault section unreadable | Empty gap + offline visual-read follow-up (do not invent codes). |
| 6 | Krypton VM IV fault 59 wording vs FCS fault 11 (both PV voltage high) | Separate series files — do not merge; same physical class, different manuals. |
| 7 | FCS warning `bP` (battery not connected) vs fault 56 (battery connection open) | Both kept: `bP` is running warning (`isFault: false`); 56 is fault. |

## Exhaustiveness (Phase D)

- Queries: knoxpv.com manuals/catalog/datasheets (Krypton, Xenon, Zynex, Xerox G4, ThinkPower, on-grid 15/50KW), "Knox Krypton fault code", "Knox Xenon manual", Solplanet/AISWEI E-codes, Livoltek/Motoma/IIT Bombay wrong-file traps, DDG/YouTube/Facebook sweeps.
- Stop rule met: three OEM tables extracted (VM IV, FCS, WP); remaining series have zero published Knox-badged fault tables.
- Highest-value unread: ThinkPower §5.2.5 + LED blink table (visual PDF read); Krypton 12002/15002 service manual if released; full Solplanet link if Knox becomes AISWEI distributor with badge.

## Offline follow-up paths

- UAN **0304-111(KNOX)5669** / support@knoxpv.com — request Krypton 5600 official fault table, ThinkPower service manual, Zynex fault codes, Xerox G4 manual.
- knoxpv.com contact form / warranty claim desk (Electro Pakistan).
- In-box paper manuals from dealer units (Xenon 9000/11000/14500T).
