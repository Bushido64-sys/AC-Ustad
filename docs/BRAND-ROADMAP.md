# Brand Roadmap

Ordered queue. **One brand at a time** — finish a brand completely (all series, all codes,
sources logged, validated) before starting the next. Status legend:

- ☐ planned
- 🔬 researching / in progress
- ☑ done (validated, registered in `data/index.json`, logged in `PROGRESS.md`)
- ⏸ paused/blocked (reason noted in PROGRESS.md)

Owner priority: **Pakistani brands first (AC + inverters)**, then international brands
commonly sold in Pakistan. Order is adjustable — edit this file if the owner re-prioritizes.

---

## Phase 1 — Local AC brands (Pakistan)

| # | Brand | id | Status | Codes | Date done | Notes |
|---|-------|----|--------|-------|-----------|-------|
| 1 | Dawlance | `dawlance` | ☑ | 42 | 2026-09-23 | Done. Official manuals OCR'd visually (E1/E2/E6/E4 + display states); 41 split codes + 1 FS-specific; conflicts logged |
| 2 | Orient | `orient` | ☑ | 43 | 2026-09-23 | Done. Official blog table recovered via Wayback (16 codes, high); extended E6/E7/E9/EA/F5/F7/FA/FC/Fd/P0–P9/L0–L3/EC/LE01 from PK sources; AYS F-series conflicts logged |
| 3 | PEL | `pel` | ☑ | 27 | 2026-09-23 | Done. No official table found — Electra Fix + Holife agree on 25-code core (E0–E5/E8, F0–F9, FA, P2–P8) high; P1 + FF low (video/title, meaning unpublished); EC omitted per staff reply |
| 4 | Kenwood | `kenwood` | ☑ | 25 | 2026-09-23 | Done. 3 agreeing tables (Electra Fix/Japan Electronics/AYS) = 21 high; numeric 6/19/36 medium (EF only); Error 20 low (video title). E4/E5 conflicts logged. No official table |
| 5 | Homage (AC) | `homage` | ☑ | 1 | 2026-09-23 | **Documented gap.** No official table (AYS 404); 30+ searches → 1 low code (F1, contested comment). R&I = Kenwood's co — platform not imported. UPS codes excluded |
| 6 | Enviro | `enviro` | ☑ | 1 | 2026-09-23 | **Documented gap.** Official E9 leak only (product pages + 8 retailers, high). No fault table (manuals page empty); 6 successive no-new-code searches. OEM E/F/P not imported |
| 7 | EcoStar | `ecostar` | ☑ | 26 | 2026-09-23 | Bijli Bazar sole full table (18 medium) + official EL non-fault (high) + 7 tag-only low. E5/F1/H3 conflicts resolved; Gree mapping NOT imported (E1/E2/E4 diverge) |
| 8 | Waves | `waves` | ☑ | 0 | 2026-09-23 | **Zero-code documented gap.** ~25+ searches (official/Wayback/CDX/acerrorcode/YouTube) → no published code; codes[] empty by design. Fixya E3 = false lead. OEM unnamed |
| 9 | Singer PK | `singer` | ☑ | 19 | 2026-09-23 | 8 high (Forever Tech + Fully5world PK: E0/E3/E7/E8/P9/F6/F7/F8) + 11 low BD/title claims. **Green Inverter = BD not PK** (roadmap corrected). Parent = Waves Corp. No official PK manual |
| 10 | Rays | `rays` | ☑ | 0 | 2026-09-23 | **Zero-code documented gap.** rays.com.pk no manuals (Wayback=SEA marketing only); EAS-1820 retail no codes; EuroAire OEM hint = dead domains. Service 0340 11119 25 |
| 11 | Elios | `elios` | ☑ | 19 | 2026-09-23 | Official user-manual E/F/H table only (15 high + 4 medium: E2 dual, E4 ADS?, F4 See-List gap, H4 IB?). Kascon Technologies / Sabro; Canadian Elios P-series excluded |
| 12 | Super Asia | `super-asia` | ☑ | 0 | 2026-09-23 | **No AC line confirmed + zero codes.** Official catalogs = coolers/washers/fans only; roadmap "smaller AC line" unverified. enic.pk AC tag disputed. Super General ≠ Super Asia |
| 13 | Nobel | `nobel` | ☑ | 0 | 2026-09-23 | **Zero-code gap.** Self-Diagnose advertised (18T3/NSAC/NCAC/NFA) but no table published; Gree compressor ≠ Gree platform (traps logged). 0800-NOBEL |
| 14 | Inverex (AC) | `inverex` | ☑ | 0 | 2026-09-23 | **Phase 1 final.** Solar full-DC AC line confirmed (1/1.5/2T, IVX-05, Panasonic compressor) but zero AC codes online (~55 queries). Solar F-codes → Phase 2. Mirage IVXH trap |

## Phase 2 — Local inverter brands (solar/UPS, Pakistan)

| # | Brand | id | Status | Codes | Date done | Notes |
|---|-------|----|--------|-------|-----------|-------|
| 15 | Inverex | `inverex` | ☑ | 122 | 2026-09-23 | Done. 3 series: NitroX 3Ph string F01–F64+W (67) · NitroX hybrid confirmed F-set (30) · Voltronic numeric 01–59+W (25). Platform-split F13/F56/F58 logged. F45/F46 low (Scribd vs OEM). Yukon II/Veyron II printed tables = gap |
| 16 | Homage (inverter) | `homage` | ☑ | 29 | 2026-09-24 | Legacy UPS 25 (Fault/E/F + LED beeps) + Vertex 4 (00/01/02/08). Bolt/Apex-HAS/HQS = empty gaps. No official table; spam farm quarantined; Scribd manuals blocked |
| 17 | Knox | `knox` | ☑ | 89 | 2026-09-24 | Done. 3 coded series (Krypton VMIV 26 + FCS 36, Xenon 27 medium via OEM row) + 5 gaps (Zynex/Xerox/Solplanet-quarantine/ThinkPower/ongrid). Electro Pakistan badge; AISWEI not imported |
| 18 | Fronus (PK!) | `fronus` | ☑ | 145 | 2026-09-24 | Done. X1-Genki 106 high (official FRONUS manual) + X3 MIC 25 medium (cipher OCR) + PV-hybrid 14 medium. ≠ Austrian Fronius; Neo/SolaX quarantined; X3-Genki/Meta empty |
| 19 | Crown Micro | `crown-micro` | ☑ | 40 | 2026-09-24 | Done. Official Crown PDFs: Xavier 26 (incl. W16 low via SolarNevs) + Elego 14 parallel set. Nero II/UPS/other empty; Nexus quarantined (Crown Group automotive ≠ Crown Micro) |
| 20 | Ziewnic | `ziewnic` | ☑ | 22 | 2026-09-24 | Done. Diamond 5G only (Scribd 4.5kW manual: 01–09/51–53/55–59 + 5 W-codes). Lenox=Deye / Atom=Growatt / Axpert=Voltronic = hints only, not imported; 6 empty series |
| 21 | Max Power | `max-power` | ☑ | 0 | 2026-09-24 | **Zero-code documented gap.** Official blog "check manual" but no manual online; Sofar/India-Voltas/thruster tables quarantined. 6 empty series |
| 22 | SolarMax PK | `solarmax` | ☑ | 23 | 2026-09-24 | Done. ORION only (Scribd SM-ORION-DUAL-4KW + SolarNevs): F01–F10/F51–F53/F55/F57–F59 + 6 W. ≠ Swiss SolarMax; 6 empty series (UPS Scribd unread) |
| 23 | Tesla PK | `tesla-pk` | ☑ | 143 | 2026-09-24 | Done. HLE 43 high (Tesla Infinity Scribd + Tesla-PV) + Infini VII 38/VIII 37 + VM 25 medium. ≠ Tesla Inc; HSC/other Infinity empty |
| 24 | Simtek | `simtek` | ☑ | 85 | 2026-09-24 | Done. SIMREX 43 + SIMRON 27 + Flux 9 + MPPT text 6 (official PDFs, high); W-prefix + BMS-60/71 collision rules; XL/MPR/ECO/Deluxe/Line-Interactive = zero-code gaps |
| 25 | Stabimatic | `stabimatic` | ☑ | 18 | 2026-09-24 | Done. ZERO numeric codes — symbolic LED/buzzer/LCD IDs from official Gemini/ONL/GS/SP datasheets (medium); MSI PRO confirmed product, zero manuals; SP reclassified AVR |
| 26 | Trion | `trion` | ☑ | 0 | 2026-09-24 | **Zero-code documented gap.** Connect/Wise/Wise Plus/Hybrid/Flux/on-grid brochures only; OEM VM IV TWIN + Solplanet hints not imported; ManualsLib Trion Inc./EPEVER TRIRON/ Claas quarantined |
| 27 | Primax | `primax` | ☑ | 51 | 2026-09-24 | Done. Galaxy Dual only (official manual via Wayback): F01–F32 + W01–W19 (high); Nexa/Venus/Galaxy = gaps; ≠ primax.com.pk pest control; W18/W19 appendix conflict logged |
| 28 | Maestro | `maestro` | ☑ | 0 | 2026-09-24 | **Zero-code documented gap.** Abt/Lahore — full Wayback CDX negative; Voltronic/Growatt product names = OEM hints only; name collisions (Olimpia Splendid etc.) quarantined |
| 29 | Voltronic-platform (generic) | `voltronic-platform` | ☑ | 84 | 2026-09-24 | **Phase 2 final.** OEM reference: Table A 32 (VM III) + Table B 38 (Infini VII) + Table C variants 14 in separate files; EQ/BAT_NC/BMS_COMM symbolic ids; no fault 10 on VM III; rebadge pointer series empty by design |

## Phase 3 — International AC brands sold in Pakistan

| # | Brand | id | Status | Codes | Date done | Notes |
|---|-------|----|--------|-------|-----------|-------|
| 30 | Haier | `haier` | ☑ | 54 | 2026-09-24 | Done. Official PK master table 31 high (haier.com/pk self-service) + AYS/Bijli extras 4 + HDU-42/24 ducted 10 medium (model-scoped) + floor-standing/video 8 low + E0 aggregator 1. Quarantined hvacinexpert/Subhan/MyKarigar; JE F1/E7 meanings; washer F42 |
| 31 | Gree | `gree` | ☑ | 70 | 2026-09-24 | Done. 66 fault/status codes across letter families (e12/f13/h12/l4/p11/u8/other6) + 4 LED-blink semantics. 2+ PK sources + greecomfort article = high; F-index conflict (official vs PK) logged; acguide H6 + RU-narratives + almumtaz P quarantined |
| 32 | Midea | `midea` | ☑ | 86 | 2026-09-24 | Done. Official Midea TSP global web app/API = primary (split inverter 21 + on/off 19 + LC IDU 14 + LC ODU 10, high) + service-manual tables (MDV LED 6 + MSG lamps 6 + R-series 10, medium). No static table on mideapakistan.com. US window/portable/EL codes, chiller MDV sections, onlytroubleshooting F0, Air Care P0 quarantined |
| 33 | TCL | `tcl` | ☑ | 93 | 2026-09-24 | Done. No official AC error table (blog 404). 7 ManualsLib service manuals: inverter split 53 + FMA/TPH11 multi 21 + U-MATCH R32 9 + fixed-speed 4 + portable 6 (not PK line). E6/EP/EF, Fy vs H6, P6/P7 thresholds model-scoped per series. citra E1 mapping + washer/TV quarantined |
| 34 | Hisense | `hisense` | ☑ | 98 | 2026-09-24 | Done. Tri-Angels (Karachi) assembled. No official full table (gcss how-to PDF only). T1 floor 77 high (service manual indoor+outdoor sheets) + E/F export 18 + portable 3. Indoor vs outdoor numeric meanings never merged. hvacinexpert H6/U4 + content farms + Changhong F1 quarantined |
| 35 | Changhong Ruba | `changhong-ruba` | ☑ | 37 | 2026-09-24 | Done. No official table (site 500). CSDC/CSDH 27 (PK tech videos w/ captions, LED blink counts) + generic DC 6 (F0 dual meaning) + RAC title-only 4. SDH-12QDN/18QEN/18QFN official lines. E0 conflict (Expert vs Fully4world) logged; China/CHiQ-global/fridge/TV/RO quarantined. F9/J5 + E3/P0/P1 = open caption gaps |
| 36 | Electrolux | `electrolux` | ☑ | 43 | 2026-09-24 | Done. Official Electrolux IE table 36 high + UK portable 4 (separate file, meanings differ) + PK video-title 3 low. Lux Air/Active Air PK + VEGA/KSV manuals = empty gap series. AUX OEM unverified; Frigidaire/THD/unbranded quarantined. electroluxpakistan.com 403, no PK table |
| 37 | Super General | `super-general` | ☑ | 33 | 2026-09-24 | Done. UAE brand (Dubai LLC); PK SKUs SGS*-PK. No official table (hotline only), no blink maps. AirChill 11 + Persian OEM table 16 + Accio 3 + Fixya 2 + P10 video 1. E1/E3/E4/E5/F9 conflicts logged per-code. ≠ Super Asia / O General / General Cool dealer / SGW washers |
| 38 | Samsung | `samsung` | ☑ | 91 | 2026-09-24 | Done. Official PK indoor display partial 10 high + service-manual E-codes 43 high + WindFree R-32 C-codes 33 medium (SplitAtlas) + cassette LED blink 5 medium. C121≠E121 unmerged. No full official PK master table; TV/mobile/fridge quarantined |
| 39 | LG | `lg` | ☑ | 79 | 2026-09-24 | Done. Official CH family (US help library, high, 13) + Universal Split sheet numeric 01-10/21-105 (Macedo/Orion, high, 43) + Multi F(DX) CH (high, 16) + single-zone DFS tech paper (high, 4) + Multi V partial Ample Air (medium, 3). Numeric vs CH never merged; no PK-localized table (lg.com/pk 403) |
| 40 | Daikin | `daikin` | ☑ | 83 | 2026-09-24 | Done. Global SM-TS3 official self-diag PDF 41 high + K-Series service manual (FTXS/RXS) 28 high + FTXC-C user manual + ARC remote 14 high. Letter+digit scheme; no PK table (daikin.com/pk 404); no LED blink map (all null) |
| 41 | Hitachi | `hitachi` | ☑ | 206 | 2026-09-24 | Done. 7 series — SET FREE air365 65 high + Utopia/IVX/Prime SMGB0136 61 high + Primairy GB 35 medium-high + ABNML commercial 15 + LD301 outdoor blink 14 + timer blink 7 + RAC mirror 9. Schemes never merged; no consolidated PK table (Incapsula/airCloud gated) |
| 42 | Mitsubishi Electric | `mitsubishi-electric` | ☑ | 116 | 2026-09-24 | Done. A-control 51 high (ME MY official + SG MrSlim PDF + UK/IE checklist) + K-control 17 + City Multi PUCY 25 + PUMY 23 (CityMultiErrorCode_V4). Control-type scoped never merged; MHI quarantined; no PK site (404) |
| 43 | Panasonic | `panasonic` | ☑ | 75 | 2026-09-24 | Done. Modern H/F self-diagnosis 55 (official page + CS-E7JKEW service manual) + legacy inverter S/E 20 (medium). Never merged across generations; wired-remote E-family quarantined; no PK table (403; PEL distributes) |
| 44 | AUX | `aux` | ☑ | 93 | 2026-09-24 | Done. Official global IDU 25 high + 2021 Macroclima/AUX guide (wall-floor 27, outdoor 3-LED 24, L-cat 12, portable 5) all high. Product-line scoped never merged; PK presence = United Group of Industries, no PK error content; OEM claim unverified |
| 45 | Sharp | `sharp` | ☑ | 145 | 2026-09-25 | Done. Official Sharp Air App table 83 high (hyphenated main-sub — PK-facing remote readout) + ZU1/TRS extras 6 medium + PSR service-manual E/P display 25 + outdoor LED1 blink 31 (model-scoped, not confirmed sold in PK). 3 schemes in separate files, never merged; E9/P0/P9 sequence logged (aliases not merged — key collision); TCL PS-Cloud + Sharp TV/fridge/copier quarantined |
| 46 | Carrier (residential if any) | `carrier` | ☑ | 118 | 2026-09-25 | **Phase 3 final.** Residential confirmed: Comfort 22 45MHHAQ 34 high (official OM + SplitAtlas) + multi-split 38GVM/40GVM 56 (R&R + HowTo HVAC: 54 high / 2 medium) + Elite E/P 11 high (official AU 246/247) + SHV inverter ducted 10 + SHV fixed ducted 7 high (official 249). 5 model-scoped series, 22 digit overlaps all meaning-divergent — never merged. PK = Smart Climate Solutions distributor + dhabione/OLX, no PK site/table; 38QHA/42QHA table = open gap. Commercial/VRF/Toshiba-Carrier/Trane/Enterprise-40MAQ/Alpha-lamp quarantined |

## Phase 4 — International inverter brands sold in Pakistan

| # | Brand | id | Status | Codes | Date done | Notes |
|---|-------|----|--------|-------|-----------|-------|
| 47 | Growatt | `growatt` | ☑ | 125 | 2026-09-26 | Done. 125 codes / 6 series files. See docs/sources/growatt.md |
| 48 | Deye | `deye` | ☑ | 80 | 2026-09-26 | Done. 80 codes / 4 series files. See docs/sources/deye.md |
| 49 | GoodWe | `goodwe` | ☑ | 79 | 2026-09-26 | Done. 79 codes / 11 series files. See docs/sources/goodwe.md |
| 50 | Solis | `solis` | ☑ | 89 | 2026-09-26 | Done. 89 codes / 8 series files. See docs/sources/solis.md |
| 51 | FoxESS | `foxess` | ☑ | 80 | 2026-09-26 | Done. 80 codes / 15 series files. See docs/sources/foxess.md |
| 52 | Sungrow | `sungrow` | ☑ | 201 | 2026-09-26 | Done. 201 codes / 5 series files. See docs/sources/sungrow.md |
| 53 | Huawei | `huawei` | ☑ | 115 | 2026-09-26 | Done. 115 codes / 10 series files. See docs/sources/huawei.md |
| 54 | Sofar Solar | `sofar` | ☑ | 103 | 2026-09-26 | Done. 103 codes / 9 series files. See docs/sources/sofar.md |
| 55 | Victron Energy | `victron` | ☑ | 123 | 2026-09-26 | Done. 123 codes / 13 series files. See docs/sources/victron.md |
| 56 | Fronius (AT) | `fronius-at` | ☑ | 76 | 2026-09-26 | Done. 76 codes / 8 series files. See docs/sources/fronius-at.md |
| 57 | SMA | `sma` | ☑ | 82 | 2026-09-26 | Done. 82 codes / 5 series files. See docs/sources/sma.md |
| 58 | Felicity Solar | `felicity-solar` | ☑ | 159 | 2026-09-26 | Done. 159 codes / 7 series files. See docs/sources/felicity-solar.md |
| 59 | Must Power | `must-power` | ☑ | 121 | 2026-09-26 | Done. 121 codes / 5 series files. See docs/sources/must-power.md |
| 60 | APC / Schneider | `apc` | ☑ | 140 | 2026-09-26 | Done. 140 codes / 5 series files. See docs/sources/apc.md |
| 61 | Other UPS brands (CyberPower, Eaton, Kstar, Numeric…) | — | ☑ | 271 | 2026-09-26 | Bulk UPS row done. CyberPower 81 (8 series) + Eaton 94 (7) + Kstar 96 (3) + Numeric 0 (negative-evidence: brand identity unconfirmed — the only 'Numeric UPS' is Indian/Legrand; numericpower.com is a parked domain) = 271 codes. UPS rule kept: same numeric code on a different family is a separate series file, never merged.|

---

## Rules for maintaining this file

- Never delete rows — mark ⏸ with reason instead, so history survives.
- `Codes` = total code entries written for that brand (sum across its series files).
- When a brand gets split across categories (e.g. Inverex AC vs Inverex inverter),
  one row may serve both — track per-folder counts in PROGRESS.md.
- International list is non-exhaustive; append new brands at the end of their phase.
