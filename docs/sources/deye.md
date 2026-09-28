# Deye (`deye`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **80** across **4** model-scoped series

Official: https://www.deyeess.com

Ningbo Deye Inverter Technology — hybrid/string/micro/ESS maker. Strongly present in the Pakistani market via local distributors (Lahore/Karachi); the -EU global manuals below are the same fault firmware sold in PK, Deye publishes no PK-specific fault table.

## Brand recon

- **Manufacturer:** Ningbo Deye Inverter Technology Co., Ltd. — Chinese (Ningbo, Zhejiang) maker of hybrid solar inverters, string inverters, microinverters and ESS. Brand umbrella worldwide is "Deye" (also sold as "Deye ESS", "Deye Micro").
- **Official global site:** https://www.deyeinverter.com (manual library at `/download/product-manual/`, currently 151 documents, newest 2026-09). Regional mirrors: `cn.`, `pt.`, `nl.`, `it.`, `de.`, `es.`, `vi.`, `pl.`, `au.`, `jp.deyeinverter.com`.
- **Pakistan presence:** Deye is actively sold in Pakistan through local distributors (Lahore/Karachi), mainly the low-voltage three-phase and single-phase hybrid range plus microinverters. The manuals we use are the "-EU" global variants, which are the same firmware/fault tables sold in PK. No PK-specific (-PK) fault table exists; Deye publishes one global fault table per model line.
- **Naming:** model = `SUN-<kW>K-<platform><phase>` e.g. `SG04LP3` (three-phase low voltage), `SG01HP3` (three-phase high voltage), `SG03LP1` (single-phase low voltage), `M(130-200)G4` (micro).
- **Fault-table conventions seen so far:** on Deye the home-screen centre icon shows `Comm./F01~F64`; the manual chapter is titled "Fault information and processing" and the table is labelled "Chart 7-1 Fault information" (or chapter 8 "Error code list of alarms and errors" on newer high-voltage firmware). Codes are `Fxx` for faults (unit cuts output, red LED solid) and `Wxx`/`*_warn` for warnings (unit keeps running, red LED flashes). Several Deye platforms also print the internal firmware string (e.g. `Tz_Dc_OverCurr_Fault`, `GFDI_Relay_Failure`) next to the number — those go in `aliases`.
- **OEM-twin note:** Deye hardware is also rebadged and sold by other brands. This KB already has an `inverex` brand holding Deye-OEM twin tables (SUN G03/G01P3-AM8 and the F36–F64 numeric set sourced from deyeinverters.net). We do **not** re-import that F-table here; for Deye proper we use Deye's own PDFs on deyeinverter.com.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Deye manual library index | index | https://www.deyeinverter.com/download/product-manual/ | Model list, official PDF URLs, chapter structure | 2026-09-26 |
| 2 | Manual_SUN-3.6-6K-SG03LP1-EU_240203_en.pdf | user manual | https://www.deyeinverter.com/deyeinverter/2024/02/03/instructions_sun-3.6-6k-sg03lp1-eu_240203_en.pdf | Chart 7-1 F-table (19 rows) | 2026-09-26 |
| 3 | Manual_SUN-5-12K-SG04LP3-EU_240203_en.pdf | user manual | https://www.deyeinverter.com/deyeinverter/2024/02/03/instructions_sun-5-12k-sg04lp3-eu_240203_en.pdf | Chart 7-1 F-table, 25 rows over 3 pages (re-checked: no W-row warning table on this line) | 2026-09-26 |
| 4 | Manual_SUN-5-25K-SG01HP3-EU-AM2_240203_en.pdf | user manual | https://www.deyeinverter.com/deyeinverter/2024/02/03/rand/1391/instructions_sun-5-25k-sg01hp3-eu-am2_240203_en.pdf | Chart 7-1 F-table, 28 rows over 3 pages (no W-row warning table on this line either) | 2026-09-26 |
| 5 | 【B】manual_sun-3.6-8k-sg05lp1-eu_20260319_en.pdf | user manual | https://hqcdn.hqsmartcloud.com/deyeinverter/2026/03/19/rand/6545/%E3%80%90b%E3%80%91manual_sun-3.6-8k-sg05lp1-eu_20260319_en.pdf | Chart 7-1 (19 rows, byte-identical list to #2) — used only as corroboration, not filed separately | 2026-09-26 |
| 6 | instructions_sun-m130-200g4-eu-q0eu_240622_en.pdf (Installation/User Manual V2.0.0) | user manual (micro) | https://hqcdn.hqsmartcloud.com/deyeinverter/2024/06/22/instructions_sun-m130-200g4-eu-q0eu_240622_en.pdf | "Status Indications and Error Reporting" LED blink table — no numeric F/W codes exist on micro | 2026-09-26 |
| 7 | 【B】Manual_SUN-60-80K-G03_20260309_en.pdf | string manual | https://hqcdn.hqsmartcloud.com/deyeinverter/2026/03/09/%E3%80%90B%E3%80%91Manual_SUN-60-80K-G03_20260309_en.pdf | Nothing importable — error descriptions readable but every F/W digit is a broken glyph | 2026-09-26 |
| 8 | instructions_sun-15k-g05p3_240806_en.pdf | string manual | https://hqcdn.hqsmartcloud.com/deyeinverter/2024/08/06/instructions_sun-15k-g05p3_240806_en.pdf | Same broken-digit layer as #7 — rejected | 2026-09-26 |
| 9 | BManualSUN-7-8K-G02P1-EU-CM220260622en.pdf | string manual | https://hqcdn.hqsmartcloud.com/deyeinverter/2026/06/25/BManualSUN-7-8K-G02P1-EU-CM220260622en.pdf | Same broken-digit layer as #7 — rejected | 2026-09-26 |
| 10 | BManualSUN-M130-200G4-EU-Q0EU20260327en.pdf (micro V2.2.0) | user manual (micro) | https://hqcdn.hqsmartcloud.com/deyeinverter/2026/03/27/BManualSUN-M130-200G4-EU-Q0EU20260327en.pdf | Nothing — PDF is Adobe Illustrator outlined text, pdftotext returns 0 characters | 2026-09-26 |

## Negative results

Codes/behaviour that could NOT be verified from a URL fetched on 2026-09-26 — deliberately not imported:

1. **All Deye three-phase/single-phase string-inverter fault tables (G02P1, G03, G05P3) — REJECTED, broken text layer.** The error *descriptions* extract cleanly but every code number comes out as a broken glyph (`F` + two replacement characters, e.g. `F�� DC input polarity reverse fault`). Checked: `BManualSUN-7-8K-G02P1-EU-CM220260622en.pdf` (source #9, 1 legible code in the whole file), `instructions_sun-15k-g05p3_240806_en.pdf` (source #8), `【B】Manual_SUN-60-80K-G03_20260309_en.pdf` (source #7, only `F09` legible). No import — I will not guess Deye's own numbers from a readable description.
2. **The string-inverter W-code warning block — unverifiable.** `【B】Manual_SUN-60-80K-G03_20260309_en.pdf` does contain four warning rows with readable descriptions — "Arc warning", "Upgrade error warning", "Fan warning", "Prefix error" — but each number is also a broken glyph (`W��`), so the codes themselves are unknown. Recorded here as text only, not as codes.
3. **The "W-code warning blocks" on Deye hybrid lines — NOT FOUND.** Full-text search of all three hybrid manuals (SG03LP1, SG04LP3, SG01HP3) plus SG05LP1 returns no `Wxx` fault/warning table; the only "Warning" strings are safety notices and the "Grid Warning" LCD screenshot. The recon note about `Wxx` on Deye could not be substantiated for Deye proper, so no warning series was filed.
4. **Latest microinverter manual unreadable.** `BManualSUN-M130-200G4-EU-Q0EU20260327en.pdf` (source #10, 40 pages) is an Adobe Illustrator 25.4 file with all text converted to outlines — `pdftotext` (layout and raw) returns **0 characters**. Series 4 therefore rests on the 2024 V2.0.0 manual (#6). If the 2026 revision changed the blink table we cannot see it.
5. **LED blink *rate* for hybrid fault codes — not published.** Chart 4-1 in all three hybrid manuals documents only four LEDs: DC / AC / Normal = "Green led solid light", Alarm = "Red led solid light — Malfunction or warning". No flash-per-second rate is given, so no `blinkPattern` was invented for the F-codes; they are `display: indoor` (LCD centre icon `Comm./F01~F64` + solid red Alarm LED).
6. **Numeric trip thresholds — not published in the fault chapters.** No grid-voltage window, insulation minimum, leakage-current limit, over/under-frequency value or over-temperature figure appears in the fetched fault tables (the datasheet chapters were not used as a source of numbers). Every entry therefore says "compare with the specification range in the manual" rather than quoting a value.
7. **No PK-specific Deye fault table and no Deye web fault-code lookup found.** The manual library index exposes PDFs only; no support/fault-lookup page was found on deyeinverter.com from that index. PK coverage is inferred from the -EU global manuals (see brand notes), not from a PK document.
8. **Firmware-string aliases taken only where the manual prints them.** E.g. `Tz_Dc_OverCurr_Fault`, `GFDI_Relay_Failure`, `BusUnbalance_Fault`, `Heatsink_HighTemp_Fault` are printed in the HP3 table. Where the LP manuals print only English prose (no firmware string), `aliases` holds the English variant only — no internal firmware string was invented.
9. **Settings values, per-unit ARC enable state, DRM behaviour on non-AU firmware, and battery-protocol compatibility lists — not verifiable** from these manuals, so solutions describe checks rather than asserting menu paths or protocol numbers.

## Quarantine (excluded)

- **Other brands' codes are NOT in this file.** This KB has sibling brand directories for Growatt, GoodWe, Solis, Fronius, SMA, Huawei, FoxESS, KSTAR, Victron, Must Power, Felicity Solar, APC, Eaton and Cyberpower. None of their fault/warning codes were fetched or imported into `deye`.
- **Deye hardware rebadged under other labels** (the `inverex` brand already in this KB) stays quarantined under that brand id — see CONFLICTS item 1.
- **Deye-bundled accessories inside the same Deye manual library are out of scope for the inverter fault table** and were not imported: Eastron SDM120-CT / SDM230 / SDM630 meter manuals, CHINT DDSU666 and DTSU666 meter manuals, LS4G-4-C stick logger, LSE-3 / LSG-3 / LS4G-3GPRS quick guides, DeyeDataLogger DL1000B (4G / ETH / WIFI) specification + quick guide, Deye Cloud user manual. Their event/error codes belong to the meter, logger and cloud platform, not to the inverter; filing them under `deye` would make an inverter lookup return meter codes.
- **Not attempted:** Deye All-in-One ESS, Modular C&I ESS, ACS (air conditioner), EV charger, Smart devices (PLUG01/TX01/SMART-CT01/SMART-SWITCH01), battery BMS manuals and the G05/G06 string families — no fault table was fetched for them, so nothing was created. They remain a gap, not a partial import.

## Conflicts / caveats

1. **Already-filed Deye-OEM twin table (`inverex`).** This KB files Deye-OEM twin tables under brand `inverex`: SUN G03 / G01P3-AM8 and the F36–F64 numeric set sourced from **deyeinverters.net**. Deye proper is sourced here **only** from Deye's own PDFs on `deyeinverter.com` / `hqcdn.hqsmartcloud.com`. The F36–F64 numeric set therefore overlaps this brand's F-number space and is intentionally **not** re-imported. Practical effect for the app: an F-numbers-only search can hit both brands, so the UI must keep the codes series-scoped (`deye-sg04lp3:F46` is not the same lookup as `inverex:F46`).
2. **Same F-number, different meaning on different Deye lines — never merge these series.** Verified divergences between the three hybrid tables filed here:

   | Code | SG03LP1 (S1) | SG04LP3 (S2) | SG01HP3 (S3) |
   |------|--------------|--------------|--------------|
   | F13 | Working mode change | working mode change | Working_Mode_change |
   | F15 | *(absent)* | AC over current fault of **software** | AC_OverCurr_SW_Failure |
   | F16 | *(absent)* | AC leakage current fault | GFCI_Failure |
   | F18 | AC over current fault of hardware | AC over current fault of hardware | Tz_Ac_OverCurr_Fault |
   | F20 | DC over current fault of the hardware | same | Tz_Dc_OverCurr_Fault |
   | F22 | Tz_EmergStop_Fault ("contact your installer") | Tz_EmergStop_Fault ("remotely shutdown") | Tz_EmergStop_Fault |
   | F23 | AC leakage current **is transient** over current | Leakage current fault | Tz_GFCI_OC_Fault |
   | F35 | **No AC grid** | *(absent)* | *(absent)* |
   | F42 | **AC line low voltage** | **AC line low voltage** | **Parallel_Version_Fault** |
   | F46 | *(absent)* | **backup battery fault** | *(absent)* |
   | F52 / F53 | *(absent)* | *(absent)* | DC_VoltHigh_Fault / DC_VoltLow_Fault |
   | F54 / F57 | *(absent)* | *(absent)* | BAT2_VoltHigh_Fault / BAT2_VoltLow_Fault |
   | F55 | *(absent)* | **DC busbar voltage is too high** | **BAT1_VoltHigh_Fault** |
   | F56 | **DC busbar voltage is too low** | **DC busbar voltage is too low** | **BAT1_VoltLow_Fault** |
   | F62 | *(absent)* | DRMs0_stop | DRMs0_stop |
   | F63 | ARC fault | ARC fault | ARC_Fault |

   The dangerous pairs for a Pakistani technician: **F42** (grid under-voltage vs parallel firmware mismatch) and **F55/F56** (48V busbar vs 384V battery-1 string) — reading the wrong table sends a grid problem to a battery replacement and vice-versa.
3. **F63 / ARC across platforms.** ARC is **F63 on all three hybrid lines** (self-clearing only after the cable fault is fixed, and Deye states the ARC function is US-market only), while on the string-inverter manuals the arc event is a **W-code warning**, meaning `F63` on a G03/G05P3 string unit is some *other*, unknown fault. Those numbers are unreadable (NEGATIVE 1), so no F63-for-string entry was created.
4. **SG05LP1 vs SG03LP1.** The 2026 `manual_sun-3.6-8k-sg05lp1-eu_20260319_en.pdf` (source #5) prints a row-for-row identical 19-code Chart 7-1 (F08, F13, F18, F20, F22, F23, F24, F26, F29, F34, F35, F41, F42, F47, F48, F56, F58, F63, F64 — same wording). Treated as the same table, noted in SERIES 1 rather than filed twice; if Deye later ships an SG05LP1-specific table it must become its own series.
5. **Documented translation error in the SG01HP3 manual, F13 step 5.** It reads "If it remains same, turn on DC and AC switch for one minute, then turn on the DC and AC switch" — the two LP manuals read "turn **off** the DC switch and AC switch and wait for one minute and then turn on". Flagged in the SERIES 3 F13 entry `notes` so nobody follows the HP3 wording literally.
6. **Same library on two hosts.** The 2024 manuals are served from `www.deyeinverter.com/deyeinverter/...` while the 2024-06 onwards manuals are served from `hqcdn.hqsmartcloud.com/deyeinverter/...` (same path shape). No content conflict found between the two hosts; both are Deye official.
7. **Naming vs filename mismatch (harmless, recorded).** `instructions_sun-3.6-6k-sg03lp1-eu_240203_en.pdf` has a cover page listing only `SUN-5K-SG03LP1-EU` and `SUN-6K-SG03LP1-EU`, and `instructions_sun-5-12k-sg04lp3-eu...` covers 5/6/8/10/12K — so the filename range, not the cover, was used for `model_patterns`, and wildcards were kept loose.

## Research report

- **Total codes filed: 80** across **4 model-scoped series** (all `Fxx`/LED identifiers, all from Deye's own manuals fetched today):
  - `deye-sg03lp1` — 19 codes (F08, F13, F18, F20, F22, F23, F24, F26, F29, F34, F35, F41, F42, F47, F48, F56, F58, F63, F64)
  - `deye-sg04lp3` — 25 codes (F01, F07, F13, F15, F16, F18, F20, F21, F22, F23, F24, F26, F29, F34, F41, F42, F46, F47, F48, F55, F56, F58, F62, F63, F64)
  - `deye-sg01hp3` — 28 codes (F01, F07, F13, F15, F16, F18, F20, F21, F22, F23, F24, F26, F29, F34, F41, F42, F47, F48, F52, F53, F54, F55, F56, F57, F58, F62, F63, F64)
  - `deye-m130-200g4` — 8 codes (LED-1, LED-2, LED-BLUE-SLOW, LED-BLUE-FAST, LED-RED, LED-2RED, LED-3RED, LED-4RED)
- **Top 5 URLs:**
  1. https://www.deyeinverter.com/deyeinverter/2024/02/03/instructions_sun-3.6-6k-sg03lp1-eu_240203_en.pdf (19 codes)
  2. https://www.deyeinverter.com/deyeinverter/2024/02/03/instructions_sun-5-12k-sg04lp3-eu_240203_en.pdf (25 codes)
  3. https://www.deyeinverter.com/deyeinverter/2024/02/03/rand/1391/instructions_sun-5-25k-sg01hp3-eu-am2_240203_en.pdf (28 codes)
  4. https://hqcdn.hqsmartcloud.com/deyeinverter/2024/06/22/instructions_sun-m130-200g4-eu-q0eu_240622_en.pdf (8 codes)
  5. https://www.deyeinverter.com/download/product-manual/ (index that surfaced every PDF above; 151 documents)
- **Blockers:**
  - **Broken text layer on Deye's string-inverter fault tables** (G02P1, G03, G05P3): descriptions extract, code digits do not. This kills the whole string-inverter F-table and the W-code warning block. Needs OCR, or a text-layer-native manual from Deye support.
  - **Broken text layer (worse) on the newest microinverter manual** `BManualSUN-M130-200G4-EU-Q0EU20260327en.pdf` — Adobe Illustrator, all text outlined, `pdftotext` returns 0 characters on 40 pages. Needs OCR to confirm the 2026 blink table matches the 2024 one.
  - No Deye web fault-code lookup or per-model fault page was found from the manual index — PDFs are the only official table source, so table changes are invisible until a new PDF ships.
  - Access note: the site is behind a CDN that occasionally needs a browser User-Agent; the 2026-03 PDFs live under a `rand/<id>/` path that only the index page reveals.
  - Unfetched families (no table yet, not a blocker but a gap): G05/G06 string, SG02LP1, SG02HP3, SG01HP2, SG05LP3, SG06LP1/LP3, G04/G05/G06 hybrid, ESS (All-in-One / Modular C&I), EV charger, ACS.
- **Official table: YES** — all 80 codes come from official Deye user manuals hosted on Deye's own domains (3 fault charts + 1 micro LED status table), no third-party aggregator used.
- **PK presence: YES** — Deye is actively sold in Pakistan via local distributors; SG04LP3 (three-phase 48V hybrid) is the volume platform here and SG03LP1/SG05LP1 cover single-phase. All manuals are the `-EU` global variants, which are the same firmware tables sold in PK; **no PK-specific fault table exists**, so every entry is `-EU` scoped and none of the AU/US-only features (DRM, ARC) should be promised to a PK customer without a firmware check.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `deye-sg03lp1.json` | SUN-3.6/5/6/7.6K-SG03LP1-EU (single-phase 48V low-voltage hybrid) | 19 |
| `deye-sg04lp3.json` | SUN-5/6/8/10/12K-SG04LP3-EU (three-phase 48V low-voltage hybrid) | 25 |
| `deye-sg01hp3.json` | SUN-5/6/8/10/12/15/20/25K-SG01HP3-EU-AM2 (three-phase 384V high-voltage hybrid, dual battery) | 28 |
| `deye-m130-200g4.json` | SUN-M130-200G4-EU-Q0 (grid-connected microinverter, LED blink reporting) | 8 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `deye-sg03lp1.json` | service_manual | Deye Hybrid Inverter SUN-5K/6K-SG03LP1-EU User Manual (instructions_sun-3.6-6k-sg03lp1-eu_240203_en) | https://www.deyeinverter.com/deyeinverter/2024/02/03/instructions_sun-3.6-6k-sg03lp1-eu_240203_en.pdf | 2026-09-26 |
| `deye-sg04lp3.json` | service_manual | Deye Hybrid Inverter SUN-5K-12K-SG04LP3-EU User Manual (instructions_sun-5-12k-sg04lp3-eu_240203_en) | https://www.deyeinverter.com/deyeinverter/2024/02/03/instructions_sun-5-12k-sg04lp3-eu_240203_en.pdf | 2026-09-26 |
| `deye-sg01hp3.json` | service_manual | Deye Hybrid Inverter SUN-5K-25K-SG01HP3-EU-AM2 User Manual (instructions_sun-5-25k-sg01hp3-eu-am2_240203_en) | https://www.deyeinverter.com/deyeinverter/2024/02/03/rand/1391/instructions_sun-5-25k-sg01hp3-eu-am2_240203_en.pdf | 2026-09-26 |
| `deye-m130-200g4.json` | service_manual | Deye Photovoltaic Grid-connected Microinverter (Built-in WIFI-G4) Installation / User Manual, SUN-M(130-200)G4-EU-Q0, Ver 2.0.0 | https://hqcdn.hqsmartcloud.com/deyeinverter/2024/06/22/instructions_sun-m130-200g4-eu-q0eu_240622_en.pdf | 2026-09-26 |

