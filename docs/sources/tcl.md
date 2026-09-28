# TCL (AC) — sources

Brand id: `tcl` · Category: `ac` · Researched: 2026-09-24
Official: https://www.tcl.com/pk/en · PK e-shop https://www.tclpakistan.com/
PK support hub: https://www.tcl.com/pk/en/support-airconditioner (manuals/FAQ, **no error-code table**)
PK lines: MIRACLE (TAC-18T3S/B), ELITE (TAC-24HEA/18HEA–HEW/12HEB), T-PRO (TAC-12/18/24T3-Pro, TAC-24T3-FH), SMART, SAVEIN AI (TAC-12/18SVN-AI), VOXIN/Jetmax, T5/VOX, floor-standing TAC-24T5-CFRESH / TAC-24T3-FH
Official published AC error table: **No** (blog playbook 404; only US portable FT article)

**Status: 93 codes across 5 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `tac-inverter-split.json` | TAC inverter split (XA81I / FCI / ERA YA11I service manuals) | 53 |
| `tac-multi-split-fma.json` | FMA Free-Match multi / TPH11 multi-split (model-scoped) | 21 |
| `u-match-r32.json` | U-MATCH R32 ducted/cassette/floor multi (TCC-*) | 9 |
| `tac-fixed-speed-legacy.json` | TAC fixed-speed legacy (TAC-07…24 CS/CK/CHS) | 4 |
| `tcl-portable-ac.json` | Portable TCL AC (US/global — not PK retail line) | 6 |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | TCL TAC-09CSD/XA81I Service Manual (72 pp) | service_manual | https://www.manualslib.com/manual/3282287/Tcl-Tac-09csd-XA81i.html | §3.1 Failure code + §3.2 troubleshooting; indoor RUN/TIMER + outdoor wink-time blink maps | 2026-09-24 |
| 2 | TCL ERA YA11I Service Manual (52 pp) | service_manual | https://www.manualslib.com/manual/3215117/Tcl-Era-Ya11i.html | Error list p12, outdoor blink map p13, CL filter p41 | 2026-09-24 |
| 3 | TCL FMA-32I4HD/DVO Service Manual (41 pp) | service_manual | https://www.manualslib.com/manual/3454757/Tcl-Fma-32i4hd-Dvo.html | Free-Match multi error list p10 with cassette LED patterns | 2026-09-24 |
| 4 | TCL U-MATCH-R32 Series Service Manual (142 pp) | service_manual | https://www.manualslib.com/manual/3375542/Tcl-U-Match-R32-Series.html | Code tables p95–96, troubleshooting p97–112 (TCC duct/cassette/floor) | 2026-09-24 |
| 5 | TCL TAC-09CHSD/TPH11 DC Inverter Multi-Split (80 pp) | service_manual | https://www.manualslib.com/manual/3798986/Tcl-Tac-09chsd-Tph11.html | TROUBLE DISPLAY p72–74 multi-split codes | 2026-09-24 |
| 6 | TCL TAC-09CHSD/FCI Service Manual (80 pp) | service_manual | https://www.manualslib.com/manual/4036974/Tcl-Tac-09chsd-Fci.html | Same §3.1/§3.2 family as XA81I (cross-check) | 2026-09-24 |
| 7 | TCL TAC-07CS Service Manual (139 pp) | service_manual | https://www.manualslib.com/manual/837512/Tcl-Tac-07cs.html | Legacy fixed-speed Failure Display p22 | 2026-09-24 |
| 8 | manua.ls FAQ TCL TAC-12CSD | user_manual | https://manua.ls/tcl/tac-12csd/manual | E6, P1, E3, P4, P5 Q&A corroboration | 2026-09-24 |
| 9 | acerrorcode.com TCL AC error codes | technician_note | https://acerrorcode.com/articles/tcl-ac-error-codes.html | Portable codes + outdoor wink-time 1–17 + split table | 2026-09-24 |
| 10 | nejlepsinapat.cz TCL table | technician_note | https://nejlepsinapat.cz/tcl-klimatizace-chybove-kody | Mirror of older global list incl. EH + blink 1–17 | 2026-09-24 |
| 11 | citra-teknik.com TCL E/P table | technician_note | https://citra-teknik.com/tcl-air-conditioner-error-codes/ | E/P table + indoor RUN/TIMER blink map (E1 mapping conflict — see conflicts) | 2026-09-24 |
| 12 | mundochiller.com TCL AC codes | technician_note | https://mundochiller.com/tcl-air-conditioner-error-codes/ | Troubleshooting-oriented cross-check | 2026-09-24 |
| 13 | TCL US portable AC FT article | official_support | https://support.tcl.com/en_US/common-questions-PAC/full-tank-ft-error-code | FT full-tank portable code (official) | 2026-09-24 |
| 14 | TCL PK AC support hub | official_support | https://www.tcl.com/pk/en/support-airconditioner | PK manual/FAQ hub confirmation (no table) | 2026-09-24 |
| 15 | Japan Electronics PK TCL | retailer_page | https://japanelectronics.com.pk | PK series/model recon (TAC-18T3S etc.) | 2026-09-24 |

manualslib pages fetched via r.jina.ai reader proxy (Cloudflare-gated direct).

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | citra-teknik maps E1 → outdoor sensor vs service manuals + 3 sites = room/IRT sensor | Service-manual mapping wins; citra noted in E1 notes, confidence stays high on manual |
| 2 | Gas-leak code Fy (XA81I/FCI/U-MATCH table) vs H6 (U-MATCH §3.3.16 + TPH11) | U-MATCH self-conflict: table p96 Fy=fluorine deficiency, §3.3.16 H6=gas leak, TPH11 p73 H6=T3 over-heat — both recorded with explicit per-series notes, **never merge meanings** |
| 3 | E6/EP/EF differ multi TPH11 (vane motor / vane motor / pressure) vs split inverter (indoor fan / ODU DC fan / compressor top switch) | Separate series files; notes call out divergence |
| 4 | P6 threshold 62 °C (XA81I/FCI) vs 65 °C (U-MATCH); P7 62 vs 64; P8 OAT windows differ | Per-series values noted per entry |
| 5 | U-MATCH troubleshooting uses J8/J9 (voltage), J6 (module temp), HE (phase) where other series use P1/P7/F6 | Included as U-MATCH codes/aliases with notes |
| 6 | Portable P1 (water-full) vs split P1 (voltage) | Separate portable series, explicit notes |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|----------------|
| TCL washing-machine error playbook | Wrong appliance (official page exists for washer only) |
| TCL TV / mobile / refrigerator support errors | Out of scope |
| Carrier / Trane / Hitachi / Daikin / GE / York tables in results | Other brands — never imported |
| Czech retail/heureka, Dell, Yaskawa pages from Seznam | Junk |
| US portable FT only used in portable series | Portable not PK retail line (kept separate for completeness) |

## Exhaustiveness (Phase D)

- Official TCL AC error-code page does not exist (404s confirmed); PK support hub = manuals only.
- 7 service manuals decoded via r.jina.ai; technician sites cross-checked.
- Search infra: websearch MCP 429; Google/DDG/Bing captcha — worked via Seznam + r.jina.ai + direct curl.
- YouTube timedtext transcripts empty — titles/descriptions only.
- Stop rule met: last two query batches + 8 YouTube description fetches produced zero new TCL codes.
- Unreachable: aysonline.com.pk, bijlibazar.pk (curl exit 000); no DNS on elektrotanya / airconditionererrorcode.

## Offline follow-up paths

- ManualsLib full PDFs (7 manuals) — re-fetch via r.jina.ai or download for blink-map visual verify.
- PK support hub — request consolidated fault-code sheet from TCL PK.
- Japanese Electronics / Gulfelectronics / Maqsood — ask for in-box fault tables.
