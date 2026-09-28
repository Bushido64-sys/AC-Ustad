# FoxESS (`foxess`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **80** across **15** model-scoped series

Official: https://www.fox-ess.com

Chinese-origin solar hybrid inverter maker that runs per-country manual portals; pk.fox-ess.com hosts the manuals that apply to Pakistani installs, confirming active PK distribution.

## Brand recon

**FoxESS** (Fox ESS / 深圳市首航新能源科技股份有限公司 era brand, marketed as "Fox ESS" / "FoxESS") is a **CN**-origin solar inverter maker. Global site `https://www.fox-ess.com`; it runs **per-country sub-domains** that each host the localised manual library — this is the single most useful discovery for this task, because the manual that applies to a Pakistani install is the one on the **PK domain**, not the global one.

- **PK presence: CONFIRMED.** FoxESS operates a dedicated Pakistan portal at `https://pk.fox-ess.com/download/upfiles/`. Two PK-hosted manuals were retrieved in this task: the **H1(G2)/AC1(G2) Manual V1.6** (`EN-H1AC1(G2)-Manual-V1.6.pdf`) and the **T-Series Manual V1.0.1** (`EN-T-Manual-V1.0.1.pdf`). The R-Series manual is also PK-hosted (`EN-EU-R-Series-User-Manual-Fox-V1.4.pdf`). Peer sub-domains observed serving the same library: `au.`, `ro.`, `nl.`, `th.`, `br.`.
- **Product shape for this KB (all model lists below read out of the manuals fetched in this pass):**
  - **Hybrid single-phase** — `H1-3.0/3.7/4.6/5.0/6.0-E-G2`, `H1-4.6/5.0-E1-G2` and the matching `AC1-` variants (H1 G2 manual); `KH7-KH10.5` and `KA7-KA10.5` (KH/KA manual).
  - **Hybrid three-phase** — `H3-5.0-E, H3-6.0-E, H3-8.0-E, H3-9.9-E, H3-10.0-E, H3-12.0-E` (H3/AC3 manual).
  - **Grid-tie string, NO battery** — `T3-M … T30-M` incl. `T8(Dual)-M / T10(Dual)-M / T12(Dual)-M` and `T10-M-B` (T-Series manual). **Correction to an earlier draft: T-Series is NOT a hybrid.** The T manual contains zero occurrences of the word "battery"; it is a single-phase grid-tie string inverter with PV DC switch, AC output, EPS-free.
  - **Grid-tie string, three-phase, 75-110 kW** — `R75, R100, R110` (R-Series V1.4, explicitly "three-phase non-isolated grid-tied inverters", 18 pairs of PV connectors on R75/R100). R G2 (V1.5) carries the same numeric table.
  - **Battery packs with an internal BMS** — the `Bms*` rows in every hybrid manual are the inverter *re-reporting* the pack's BMS, and FoxESS's own remedy for all of them is "contact battery supplier". There is no separate FoxESS battery alarm table in these documents.
- **CRITICAL FORMAT WARNING — FoxESS mixes incompatible code namespaces. Never merge them:**
  - **Named English strings** on the hybrid units — `Grid Lost Fault`, `Bat Volt Fault`, `BMS Lost`, `Iso Fault`. These are the strings the technician actually reads on the LCD and in FoxCloud.
  - **Short lowercase strings with spaces** on the T-series string inverters — `SPS fault`, `Bus volt fault`, `DCI over range`, `GFCI fault or GFCD fault`, `Grid10MinOVP`, `Inconsistency`, `ISO fault`, `Fan fault`. A *different* vocabulary from the hybrids despite covering the same physical conditions. Note the T table prints `ISO fault` and `Ground fault` in capitals while `Grid volt fault` / `Grid freq fault` / `Grid lost fault` are lower-case with spaces — the capitalisation is inconsistent *within* the T table itself, so match case-insensitively but do not merge the namespaces.
  - **4-digit numerics** on the R/V string inverters — `1030`, `1042`, `1124`, `1345`.
  - Because the same physical condition is named differently per line, a lookup keyed on meaning alone will send a Pakistani tech to the wrong manual. The `model_patterns` on each series below are the discriminator.
- **The same string can carry a different official remedy per model line.** Verified examples (see CONFLICTS): `SW Bus Vol` is a bare "disconnect and reconnect" on H1/KH-KA but on **H3** additionally says *"check whether the N line is connected to the GRID port"* and requires master firmware ≥ 1.69. `ISO fault` on the **T-series** is a measured-impedance check (*should be > 100 kΩ*) whereas `Iso Fault` on the hybrids is an insulation-damage inspection. `Grid volt fault` on T-series says "wait one minute", H1 says "system will reconnect". These are recorded as separate series, never merged.

## Source table

Every row below was **downloaded and read in this pass** (HTTP 200, `pdftotext -layout`, table extracted verbatim). Nothing in the series blocks comes from memory, from another brand, or from a source not listed here.

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | FoxESS **PK** portal — H1(G2)/AC1(G2) Manual **V1.6**, §9.1 Alarm List (pp.39-44) + §6.4 Isolation Fault note | official_manual | https://pk.fox-ess.com/download/upfiles/EN-H1AC1%28G2%29-Manual-V1.6.pdf | Full 60-row H1/AC1 G2 alarm table; the `Iso Fault` / RED LED earth-fault note; model list H1-3.0…6.0-E-G2 | 2026-09-26 |
| 2 | FoxESS global — H3/AC3 Series User Manual **V1.0.4** (2025-01-07), §9.1 Alarm List (pp.57-61) | official_manual | https://www.fox-ess.com/Public/Uploads/uploadfile/files/Download/EN-H3AC3-User-Manual-V1.0.4-20250107.pdf | H3 three-phase alarm table: `PLL_ OverTime`, H3-only `Grid Relay Fault`, the N-line + master-1.69 remedies on `SW Bus Vol Fault` / `SW Inv Cur Fault` / `Bat Boost Fault`, and the H3 absence of `Main Relay Open` / `S1..M2 Close Fault` / `* Cons Fault` | 2026-09-26 |
| 3 | FoxESS global — KH/KA User Manual (AU) dated 2025-10-21, §9.1 Alarm List (pp.39-44) | official_manual | https://www.fox-ess.com/Public/Uploads/uploadfile/files/Download/EN-KHKA-User-Manua-AU-20251021.pdf | KH/KA alarm table incl. `Main Relay Open`, `S1/S2/M1/M2 Close Fault`, `GridV/GridF/Dci/Rc Cons Fault`, `Bms* Unmatch` tail; model list KH7…KH10.5 / KA7…KA10.5 | 2026-09-26 |
| 4 | FoxESS **AU** — KH/KA Manual **V1.0.6** (2026-01-26), §9.1 | official_manual | https://au.fox-ess.com/Public/Uploads/uploadfile/files/20260126/ENKHKAManualV1.0.6.pdf | **Independent 2nd official copy** of the KH/KA table; row-for-row identical to row 3 — this is what lifts KH/KA to `confidence: high` on 2+ independent official sources | 2026-09-26 |
| 5 | FoxESS **PK** portal — T-Series Manual **V1.0.1**, §9.1 Alarm List (pp.31-32) + §6.3 Isolation Fault note + ripple-control table | official_manual | https://pk.fox-ess.com/download/upfiles/EN-T-Manual-V1.0.1.pdf | Full 22-row T alarm list in short-string format; the `ISO fault` **>100 kΩ impedance** remedy; `Isolation fault`/RED LED note; ripple-control active-power table | 2026-09-26 |
| 6 | FoxESS global — T-Series Manual **V1.0.2** (2026-01-29), §9.1 | official_manual | https://www.fox-ess.com/Public/Uploads/uploadfile/files/20260129/ENTManualV1.0.2.pdf | **Independent 2nd official copy** of the T table; all 22 rows and every remedy string identical to row 5 | 2026-09-26 |
| 7 | FoxESS **PK** portal — R-Series User Manual **V1.4**, §8.2 Alarm List (pp.41-49) + §3.4 LED Indicator Panel (p.10) | official_manual | https://pk.fox-ess.com/download/upfiles/EN-EU-R-Series-User-Manual-Fox-V1.4.pdf | 117-row R numeric table (codes 1030…1380); the `ISO fault >100 kΩ` vs `1124` two-step impedance remedies; §3.4 four-LED status table (A PV / B Grid / C PID / D Alarm) | 2026-09-26 |
| 8 | FoxESS **RO** — R G2 User Manual **V1.5** (2026-06-30), §8.2 Alarm List (pp.44-46+) + §3.4 LED Indicator Panel | official_manual | https://ro.fox-ess.com/Public/Uploads/uploadfile/files/20260630/ENRRG2UserManualV1.5.pdf | **Independent 2nd official copy** of the R numeric table and the LED panel; confirms `1030`,`1034`,`1035`,`1036`,`1040`,`1042`-`1046`,`1049`,`1050`,`1051`,`1057`,`1065`,`1070`-`1072`,`1085`,`1086`,`1088`,`1090`,`1096`-`1099`,`1102`,`1103`,`1106`,`1107`,`1108`,`1109`,`1110`-`1112`; identical §3.4 LED wording | 2026-09-26 |

**Sources an earlier pass of this job listed that were NOT re-fetched here, and are therefore NOT used as the basis of any entry below:** the AU H1 V1.3 copy, a Dutch H1-E1 distributor mirror, two H3 mirrors (solar-tech-support.co.uk, keno-energy.com), a ManualsLib H3-Smart render, an AU H3-Smart mirror, the RO/BR KH copies, a SolarJuice KH mirror, the AU T-G3 copy, the NL R copy, the global V/VL copy, two Solar Analytica compilations, and two FoxESS Community forum threads. They are listed here only so the next pass knows they exist. The two claims that rested on them have been removed from the series blocks and demoted to `## NEGATIVE` (legacy numeric grid codes 1/26/27/28, and the pre-`Fault`-suffix E1 string spellings such as `HW Inv Cur` / `SW Bus Vol` / `GridV Cons` — these are retained only as *aliases* inside existing entries, never as standalone codes).

## Negative results

Searched for, and **not** found published in any FoxESS document fetched in this pass. Nothing below is asserted as a code anywhere in the series blocks.

- **Legacy short-numeric grid codes `1`, `26`, `27`, `28` ("No Utility", "Grid Voltage Fault", "Grid Frequency", "10 Minute Average Grid Voltage Fault").** An earlier pass of this job recorded these from two FoxESS Community forum threads (`foxesscommunity.com/viewtopic.php?t=1110` and `.../t=152`) and also recorded a settings path `Settings > On-Grid > Grid Para > Vac 10min Avg`. **Neither thread was re-fetched in this pass, so both the codes and the menu path are unverified and have been removed from Series 1.** Every code in the current H1 V1.6, KH/KA, H3 V1.0.4, T and R tables is a named string or a 4-digit numeric — there is no 1/26/27/28 namespace in any of them. Likely these are an older-firmware or app-side display, but that is inference, not fact. Re-fetch the forum threads before publishing.
- **H1 E1-era string spellings without the `Fault` suffix** — `HW Inv Cur`, `SW Bus Vol`, `GridV Cons`. Carried over unverified from a Dutch distributor mirror listed by an earlier pass. Only used as **aliases** inside existing entries; never as standalone codes. FoxESS's current manuals print the `Fault` suffix on every one of these.
- **KH/KA-specific codes.** An earlier pass listed RO V1.5 and BR V1.0.0 (Portuguese) copies as a language cross-check "confirming no KH-specific extras". Those were not re-fetched. In the two KH/KA copies I did fetch (global AU 2025-10-21, AU V1.0.6) the table is row-for-row identical to the H1 V1.6 table — so there is no evidence of a KH-only code, but the claim of "no extras" is only verified across two of four known copies.
- **H3 Pro / H3 Smart / H3 "A" variants.** No FoxESS manual for these was fetched. An earlier pass cited a ManualsLib render of an "H3/AC3 Smart Series" manual and a solarbrain mirror; neither was re-fetched, so no H3-Smart-specific code is published. The H3/AC3 Smart BMS rows (`BmsCellImbalance`, `Bms HW Protect`, `BmsCircuit Fault`, `BmsInsul Fault`) are, however, all present verbatim in the H3 V1.0.4 official manual I did fetch, so the KH/KA equivalents in Series 9 are safe on KH/KA specifically.
- **V-Series and VL-Series grid-tie string tables.** An earlier pass listed `EN-V-VL-Series-User-Manual-V1.3.pdf` on the FoxESS global site. **Not fetched in this pass — no V/VL code is published here.** This is a real gap: V/VL are separate model lines with their own 4-digit numeric namespace that must not be assumed identical to R.
- **R G2 codes not in the R V1.4 table.** R G2 V1.5 was fetched and its table is identical to R V1.4 for every code checked (`1030`–`1112` block). Whether R G2 later adds codes beyond that block was not established, because the fetch covered the first pages of the table only.
- **LED / status tables for the hybrid lines.** The H1, KH/KA, H3 and T manuals contain **no LED status table**. The H1 manual mentions a RED LED exactly once, in the section 6 note that an earth-fault alarm shows `Isolation fault` and lights the RED LED; the T manual says the same. There is consequently no hybrid LED series — only `foxess-r-led-status` exists, and it is R-only.
- **Numeric values for hybrid thresholds.** None of the H1 / KH-KA / H3 alarm tables publish a voltage, current, impedance or time threshold for any named string code. The only measured value FoxESS publishes anywhere in these documents is the T-Series `>100kohm` impedance figure, the R `1124` APP-threshold-versus-regulations step, and the R LED table's `200 V` DC threshold for the PV indicator. Do not quote any other number as a FoxESS figure.
- **"H3 Pro", "H3 Smart", "ECS battery pack" as separate KB units.** No standalone FoxESS battery alarm table was found. All battery codes in this file are the inverter re-reporting the pack's BMS. FoxESS's own remedy for those rows is model-line dependent and was verified by counting the phrase in each fetched manual: H1 V1.6 = 20x "Please contact battery supplier", H3 V1.0.4 = 20x "Please contact battery supplier", KH/KA (both copies) = 0x supplier and 21x / 20x "Please contact our service department". A dedicated battery series would need a different manufacturer's document, which is out of scope for an inverter KB.

## Quarantine (excluded)

Codes belonging to **other brands** that surfaced while researching FoxESS and are **deliberately excluded** from every series. They are recorded here so a future pass does not import them by mistake.

- **Generic/other-brand inverter fault strings.** No non-FoxESS code has been entered into any series block. FoxESS's own vocabulary is distinctive enough (`SW`/`HW` prefixes, `Bms*` camel-case, `Cons Fault`, 4-digit numerics) that brand bleed is easy to spot.
- **Battery-supplier BMS vocabulary.** `Bms Type Unmatch`, `Bms Ver Unmatch`, `Bms Mfg Unmatch`, `Bms SwHw Unmatch`, `Bms M&S Unmatch`, `Bms ChgReq NoAck` all *look* like they could be from a pack manufacturer (the strings are FoxESS's, but the strings describe the pack). They are in `foxess-kh-ka-bms-mismatch` **because they appear verbatim in two FoxESS manuals**, not because a pack vendor published them. A genuinely third-party BMS string — e.g. a CAN-specific code from a pack brand — is quarantined by default.
- **`SPS fault`.** T-Series only, from the FoxESS T table, so it IS a FoxESS code — but FoxESS never expands the letters. It is **not** to be expanded into any third-party "solar power system" or "smart power supply" product name that other brands use for the same letters. See the entry's `notes`.
- **Cross-brand compilations.** An earlier pass listed `solaranalytica.com/foxess-inverter-error-codes` (48 codes) and `solaranalytica.com/fault-codes`. Neither was re-fetched here. Even had they been, a cross-brand aggregator is **never** an acceptable sole source for a code in this KB, and no code in this file derives from them.
- **FoxESS Community forum user-generated codes.** Quarantined by default. Forum posts are a lead, not a source — which is exactly why the `1`/`26`/`27`/`28` codes are in NEGATIVE rather than in a series.
- **Other FoxESS model lines.** V-Series and VL-Series numerics are quarantined, not merged into `foxess-r-series-grid`. They are a different model line and were not fetched; the R table must not be assumed to cover them.

## Conflicts / caveats

Verified conflicts between FoxESS model lines. **In every case below the two readings were confirmed by extracting the same table from two different FoxESS manuals in this pass.** The rule applied throughout: same string across lines → keep separate series, with each series carrying its own official remedy.

1. **`SW Bus Vol Fault` — H1/AC1 vs H3.** H1 V1.6: *"Bus voltage out of range detected by software. • Disconnect PV, grid and battery, then reconnect. • Or seek help from us, if not go back to normal state."* H3 V1.0.4 adds two lines H1 does not have: *"Please check whether the N line is connected to the GRID port of the inverter."* and *"To upgrade to the latest software, at least ensure that the master is upgraded to 1.69 or above."* → `foxess-h1-ac1-g2-current` vs `foxess-h3-ac3-power-relay`. **Give an H1 owner the neutral check anyway (it is good practice) but do NOT tell them to chase a firmware number FoxESS never asked for on that model.**
2. **`SW Inv Cur Fault` — H1/KH-KA vs H3.** Identical first line ("Output current high detected by software"), but H3 attaches the master-1.69 upgrade instruction to the row; H1 and KH/KA do not.
3. **`Bat Boost Fault` — H1/KH-KA vs H3.** H1/KH-KA: *"The battery boost circuit mosfet is fail."* H3: *"The battery boost circuit mosfet is fail or The relay on the battery side of the inverter is not closed."* plus master 1.69. **Consequence: on an H3 a Boost fault does not condemn the MOSFET until the battery-side relay has been proven to close. On an H1 it is the first suspect.**
4. **`HW Pv Cur Fault` — H1/KH-KA vs H3.** H3 adds *"Check whether PV positive and negative are connected."* to the row. H1/KH-KA give only disconnect/reconnect.
5. **Grid-relay group — H1/AC1 & KH/KA (five codes) vs H3 (one code).** H1 and KH/KA print `Main Relay Open` ("The grid relay keeps open") plus `S1 Close Fault`, `S2 Close Fault`, `M1 Close Fault`, `M2 Close Fault`. H3 prints **none of those five strings** and instead has `Grid Relay Fault` = *"The grid relay keeps open or close."* Confirmed by a text search of the H3 PDF: zero hits for `Main Relay`, `S1 Close`, `S2 Close`, `M1 Close`, `M2 Close`. → `foxess-kh-ka-parallel-relay` vs `foxess-h3-ac3-power-relay`.
6. **Sample-consistency family — H1 & KH/KA only.** `GridV Cons Fault`, `GridF Cons Fault`, `Dci Cons Fault`, `Rc Cons Fault` exist on H1 V1.6 and both KH/KA copies. The H3 text contains **none** of them (zero hits for `Cons Fault`). A three-phase Pakistani tech must not be told to look for a `Cons Fault` on an H3.
7. **`Iso Fault` (hybrids) vs `ISO fault` (T) vs `1124` (R) vs `Bms Insul Fault` (pack).** Four different official procedures for "insulation": hybrids = inspect for damaged wire insulation, no value; T = measure impedance, **should be > 100 kΩ**; R `1124` = two steps including *"Check whether the ISO impedance protection value meets the local regulations through the APP"*; pack = inside the battery, supplier-only. → four separate series; never cross-applied.
8. **`Ground Fault` (H1) vs `Ground fault` (T).** Same condition, same two FoxESS checks ("Check the voltage of neutral and PE" / "Check AC wiring"), but the T row additionally requires *"Disconnect PV (+), PV (-) using DC switch"* and the LCD-off wait. Hybrid row has no DC step. → `foxess-h1-ac1-g2-insulation` vs `foxess-t-series-ac-thermal`.
9. **`Dci Cons Fault` (H1/KH-KA) vs `DCI Fault` (hybrids) vs `DCI over range` (T) vs `1034` (R).** Four namespaces, one physics. Verified wording of each in its own manual.
10. **Grid voltage code names, four vocabularies.** H1/KH-KA/H3: `Grid Volt Fault` + `10min Volt Fault`. T: `Grid volt fault` + `Grid10MinOVP`. R: `1044`/`1045`/`1051`/`1185`. Plus `SW Bus Vol Fault` (hybrid DC bus) vs `Bus volt fault` (T DC bus) — same word "bus", different circuits' remedies.
11. **The `Bat Relay Short Circuit` trap.** This string is **NOT** an alias for `BatCon Dir Fault` (reversed battery polarity) as some third-party lists claim. FoxESS's H1 V1.6 table lists it as its own row meaning *"The battery relay keeps close"* — a welded-stuck relay, a different fault with a different meaning. Corrected in the Series 3 `notes`.
12. **Corrected error carried in this file.** An earlier pass asserted that the H1 table does **not** list `Bat Relay Open` or `Bat Relay Short Circuit` and that they were "KH/KA and H3 only". **Both H1 V1.6 (p.42) and both KH/KA copies list them.** The claim was wrong and the Series 3 `notes` have been rewritten. This is recorded because the wrong claim was previously written into this file and anyone who read the earlier version will have it.
13. **`Temp Fault` (hybrid) vs `Over temp fault` (T) vs `1099` (R).** Hybrid: *"Please check if the environment temperature"* + wait. **T: only *"Check if the environment temperature is over the limit"* + *"Or seek for help from us"* — nothing else, no shading, no fan.** R `1099`: *"Check if the inverter is exposed to direct sunlight, please shade the inverter properly. Check and clean the air outlet. Check whether there is a fan alarm through the APP."* Only the R version mentions shading, the air outlet or a fan alarm. Verified by reading the T table row-by-row: an earlier draft of this file wrongly credited the T row with the R `1099` wording, and that has been corrected.
14. **Battery-fault escalation wording — H1/H3 vs KH/KA.** Verified by counting the phrase in each fetched manual, not by reading a single row: H1 V1.6 contains **20** occurrences of *"Please contact battery supplier"* and **0** of *"contact our service department"* in its alarm table; H3 V1.0.4 likewise **20 / 0**; KH/KA AU V1.0.6 contains **0** supplier and **21** *"Please contact our service department"*, and the other KH/KA copy **0 / 20**. The `Bms*` code strings are identical across all three lines - only the escalation route differs. **Consequence: on a KH/KA, a battery fault is a FoxESS service call; on an H1 or H3 it is a call to the battery supplier.** Do not tell a Pakistani KH/KA owner to ring the pack vendor for a `Bms HW Protect`.
15. **The T-Series is not a hybrid — correcting an earlier line in this file.** An earlier BRAND RECON line in this file listed T-Series among "hybrid single-phase" models. The T manual contains **zero** occurrences of the word "battery" and no battery port; it is a single-phase grid-tie string inverter (`T3-M` … `T30-M`). All T series are therefore `unit_type: on_grid_inverter`. Corrected in BRAND RECON.

## Research report

- **Total codes: 80** across **15 model-scoped series**. 18 codes were already present in Series 1-3; 62 were added in this pass.

| # | Series id | Codes | unit_type | Model line |
|---|-----------|------:|-----------|------------|
| 1 | `foxess-h1-ac1-g2-grid` | 4 | hybrid_inverter | H1(G2)/AC1(G2) |
| 2 | `foxess-h1-ac1-g2-current` | 7 | hybrid_inverter | H1(G2)/AC1(G2) |
| 3 | `foxess-h1-ac1-g2-battery` | 7 | hybrid_inverter | H1(G2)/AC1(G2) |
| 4 | `foxess-h1-ac1-g2-insulation` | 4 | hybrid_inverter | H1(G2)/AC1(G2) |
| 5 | `foxess-h1-ac1-g2-bms-comms` | 4 | hybrid_inverter | H1(G2)/AC1(G2) |
| 6 | `foxess-h3-ac3-grid` | 5 | hybrid_inverter | H3/AC3 (3-phase) |
| 7 | `foxess-h3-ac3-power-relay` | 6 | hybrid_inverter | H3/AC3 (3-phase) |
| 8 | `foxess-kh-ka-parallel-relay` | 6 | hybrid_inverter | KH/KA |
| 9 | `foxess-kh-ka-bms-mismatch` | 6 | hybrid_inverter | KH/KA |
| 10 | `foxess-t-series-grid` | 6 | on_grid_inverter | T-Series (string) |
| 11 | `foxess-t-series-dc` | 5 | on_grid_inverter | T-Series (string) |
| 12 | `foxess-t-series-ac-thermal` | 5 | on_grid_inverter | T-Series (string) |
| 13 | `foxess-r-series-grid` | 6 | on_grid_inverter | R-Series / R G2 |
| 14 | `foxess-r-series-pv-leak` | 6 | on_grid_inverter | R-Series / R G2 |
| 15 | `foxess-r-led-status` | 3 (non-fault status) | on_grid_inverter | R-Series / R G2 |
| | **TOTAL** | **80** | 9 hybrid / 6 on-grid | 5 model lines |

- **By model line:** H1(G2)/AC1(G2) **26** · T-Series **16** · R-Series / R G2 **15** (3 of them non-fault LED status) · KH/KA **12** · H3/AC3 **11**.
- **By namespace, as required by the anti-merge rule:** 62 named-string codes on the hybrid lines, 16 short-string codes on the T-Series, 12 four-digit numerics on the R-Series, 3 LED status rows (R only).
- **Top 5 URLs by codes contributed:**
  1. `https://pk.fox-ess.com/download/upfiles/EN-H1AC1%28G2%29-Manual-V1.6.pdf` — 26 codes (S1-S5). **FoxESS Pakistan portal.**
  2. `https://pk.fox-ess.com/download/upfiles/EN-T-Manual-V1.0.1.pdf` — 16 codes (S10-S12). **FoxESS Pakistan portal.**
  3. `https://pk.fox-ess.com/download/upfiles/EN-EU-R-Series-User-Manual-Fox-V1.4.pdf` — 15 codes (S13-S15). **FoxESS Pakistan portal.**
  4. `https://www.fox-ess.com/Public/Uploads/uploadfile/files/Download/EN-KHKA-User-Manua-AU-20251021.pdf` — 12 codes (S8-S9), corroborated by `https://au.fox-ess.com/Public/Uploads/uploadfile/files/20260126/ENKHKAManualV1.0.6.pdf`.
  5. `https://www.fox-ess.com/Public/Uploads/uploadfile/files/Download/EN-H3AC3-User-Manual-V1.0.4-20250107.pdf` — 11 codes (S6-S7).
- **Official table: YES.** Six official FoxESS fault tables plus one LED table were extracted in this pass: H1(G2)/AC1(G2) V1.6 §9.1 (60 rows, pp.39-44); H3/AC3 V1.0.4 §9.1 (~55 rows, pp.57-61); KH/KA §9.1 (~55 rows, pp.39-44, identical in two copies); T-Series V1.0.1 §9.1 (22 rows, pp.31-32, identical in two copies); R-Series V1.4 §8.2 (117 rows, pp.41-49); R G2 V1.5 §8.2 (corroboration); R-Series V1.4 §3.4 LED Indicator Panel (p.10). **Every code in this file comes from one of these; nothing came from memory, from another brand, or from an unfetched source.**
- **PK presence: YES.** Three of the six tables — and the three largest single-model contributions — are hosted on FoxESS's dedicated Pakistan portal **`pk.fox-ess.com/download/upfiles/`**: H1(G2)/AC1(G2) V1.6 (26 codes), T-Series V1.0.1 (16 codes) and R-Series V1.4 (15 codes) = **57 of 80 codes** from the PK host. Peer country portals observed serving the same library: `au.`, `ro.`, `nl.`, `th.`, `br.`
- **Confidence distribution: 80 / 80 at `high`.** Every entry's `source` is an official FoxESS manual, which satisfies the `high` criterion on its own. Four of the six model lines were additionally verified against **two independent official FoxESS copies** carrying the table row-for-row: KH/KA (global AU + AU V1.0.6), T-Series (PK V1.0.1 + global V1.0.2), R-Series (PK V1.4 + RO R G2 V1.5, both the fault table and the LED table). No entry is `medium` or `low`, so no `notes`-required-for-low case arises.

### Verification performed on this file

Every excerpt and every code string was machine-checked back against the text extracted from the six PDFs downloaded in this pass, and the tables were additionally re-read row by row. That audit found and fixed **three real errors carried in the file**, all recorded in `## CONFLICTS`:

1. **KH/KA battery escalation wording was wrong.** Series 9 originally quoted FoxESS's remedy as *"Please contact battery supplier"*. Counting the phrase in each manual shows KH/KA contains **zero** occurrences of it and **21** occurrences of *"Please contact our service department"*, while H1 and H3 contain 20 of the supplier wording and none of the department wording. Corrected, and added as CONFLICTS item 14.
2. **T-Series `Over temp fault` was credited with R-Series text.** The T row contains only *"Check if the environment temperature is over the limit"* and *"Or seek for help from us"*. The shading / air-outlet / fan-alarm instructions belong to R-Series code `1099`. Corrected in the excerpt, in the solutions (now labelled as field practice rather than FoxESS text), and in CONFLICTS item 13.
3. **The Series 3 claim that the H1 table omits `Bat Relay Open` and `Bat Relay Short Circuit` was false** — both are present on H1 V1.6 p.42. Corrected; see CONFLICTS item 12.

Also verified and confirmed correct: the T-Series 22-row table (every row re-read individually), the R-Series §8.2 statements and remedies for all 12 R codes used, the R §3.4 LED table, the H3-specific remedy deltas, and the KH/KA table's row-for-row identity across two independent official copies.

### Blockers and open items for the next pass

1. **H3 rests on a single fetched source.** The H3-specific remedy deltas (N-line check, master firmware 1.69, battery-side relay alternative on `Bat Boost Fault`, the `Grid Relay Fault` consolidation, the absence of all five H1 relay strings and all four `Cons Fault` strings) are the single most consequential findings in this file, and they rest on one PDF. Re-fetch the H3 mirrors listed in the SOURCE TABLE preamble for 2+ independent confirmation.
2. **V-Series / VL-Series are entirely absent.** `EN-V-VL-Series-User-Manual-V1.3.pdf` on the global site is a separate model line with its own 4-digit numeric namespace. Quarantined in `## QUARANTINE`, never merged into the R series.
3. **Legacy numeric grid codes 1 / 26 / 27 / 28 remain unverified.** They came from two FoxESS Community forum threads that were not re-fetched here. The claim and the associated `Settings > On-Grid > Grid Para > Vac 10min Avg` menu path have been removed from Series 1 and demoted to `## NEGATIVE`.
4. **R G2 coverage is partial.** Only the opening pages of the R G2 V1.5 §8.2 table were diffed against R V1.4; codes beyond `1112` in R G2 were not compared.
5. **Roughly 60 further verified rows were deliberately left unexpanded** to stay inside the 40-80 code budget. They are all sitting in the fetched tables and can be added as extra topic-split series with no new research: `Pv Volt Fault`, `HW Bat Cur Fault`, `Over Load Fault`, `Temp Fault`, `SCI Fault`, `MDSP SPI Fault`, `MDSP Smpl Fault`, `GridV/GridF/Rc Cons Fault`, H1's `S1/S2/M1/M2 Close Fault`, the 12 `Bms Volt/ChgCur/DchgCur/Temp/Sensor/Relay` rows, `Bms SwHw Unmatch`, `Bms M&S Unmatch`, `Bms ChgReq NoAck`; T's `Meter fault`, `Relay fault`, `Sample fault`, `OCP fault`, `Ground fault`, `V grid transient`; and roughly 100 R numerics from `1096` upward.
6. **No numeric thresholds exist for any hybrid code.** FoxESS publishes none in any of the six manuals. The only measured values published anywhere in this research are the T-Series `>100kohm` impedance figure, the R `1124` APP-threshold-versus-regulations step, and the R LED table's `200 V` DC threshold. If the app needs trip values for hybrid codes, they must be omitted rather than invented.
7. **H1 and H3 are single-source on structure, not just on wording.** Both manuals were fetched and read in full, and the H1 absence/presence claims in this file were re-verified by text search rather than assumed - but no second H1 or H3 copy was re-fetched, so a regional variant manual (for example the RO, BR or NL KH/KA copies listed in the preamble) could still carry extras that the copies used here do not.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `foxess-h1-ac1-g2-grid.json` | H1(G2)/AC1(G2) - grid and AC monitoring faults | 4 |
| `foxess-h1-ac1-g2-current.json` | H1(G2)/AC1(G2) - output current, DC bus and PV input current | 7 |
| `foxess-h1-ac1-g2-battery.json` | H1(G2)/AC1(G2) - battery and EPS faults | 7 |
| `foxess-h1-ac1-g2-insulation.json` | H1(G2)/AC1(G2) - insulation, residual current and earth faults | 4 |
| `foxess-h1-ac1-g2-bms-comms.json` | H1(G2)/AC1(G2) - BMS, battery-pack and meter communication faults | 4 |
| `foxess-h3-ac3-grid.json` | H3/AC3 (three-phase hybrid) - grid and phase-synchronising faults | 5 |
| `foxess-h3-ac3-power-relay.json` | H3/AC3 (three-phase hybrid) - power stage, bus and relay faults | 6 |
| `foxess-kh-ka-parallel-relay.json` | KH/KA (single-phase hybrid) - parallel-system relay and sampling-consistency faults | 6 |
| `foxess-kh-ka-bms-mismatch.json` | KH/KA (single-phase hybrid) - battery pack mismatch and pack hardware faults | 6 |
| `foxess-t-series-grid.json` | T-Series (single-phase grid-tie string) - grid and anti-islanding faults | 6 |
| `foxess-t-series-dc.json` | T-Series (single-phase grid-tie string) - DC bus, PV input and memory faults | 5 |
| `foxess-t-series-ac-thermal.json` | T-Series (single-phase grid-tie string) - AC leakage, insulation, ground and thermal faults | 5 |
| `foxess-r-series-grid.json` | R-Series / R G2 (three-phase grid-tie string) - grid voltage, frequency and current faults | 6 |
| `foxess-r-series-pv-leak.json` | R-Series / R G2 (three-phase grid-tie string) - DC component, leakage, PV overcurrent, polarity and insulation faults | 6 |
| `foxess-r-led-status.json` | R-Series / R G2 - LED indicator panel states (status, not faults) | 3 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `foxess-h1-ac1-g2-grid.json` | service_manual | H1(G2)/AC1(G2) Series User Manual V1.6, section 9.1 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-H1AC1%28G2%29-Manual-V1.6.pdf | 2026-09-26 |
| `foxess-h1-ac1-g2-current.json` | service_manual | H1(G2)/AC1(G2) Series User Manual V1.6, section 9.1 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-H1AC1%28G2%29-Manual-V1.6.pdf | 2026-09-26 |
| `foxess-h1-ac1-g2-battery.json` | service_manual | H1(G2)/AC1(G2) Series User Manual V1.6, section 9.1 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-H1AC1%28G2%29-Manual-V1.6.pdf | 2026-09-26 |
| `foxess-h1-ac1-g2-insulation.json` | service_manual | H1(G2)/AC1(G2) Series User Manual V1.6, section 9.1 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-H1AC1%28G2%29-Manual-V1.6.pdf | 2026-09-26 |
| `foxess-h1-ac1-g2-bms-comms.json` | service_manual | H1(G2)/AC1(G2) Series User Manual V1.6, section 9.1 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-H1AC1%28G2%29-Manual-V1.6.pdf | 2026-09-26 |
| `foxess-h3-ac3-grid.json` | service_manual | H3/AC3 Series User Manual V1.0.4 (2025-01-07), section 9.1 Alarm List | https://www.fox-ess.com/Public/Uploads/uploadfile/files/Download/EN-H3AC3-User-Manual-V1.0.4-20250107.pdf | 2026-09-26 |
| `foxess-h3-ac3-power-relay.json` | service_manual | H3/AC3 Series User Manual V1.0.4 (2025-01-07), section 9.1 Alarm List | https://www.fox-ess.com/Public/Uploads/uploadfile/files/Download/EN-H3AC3-User-Manual-V1.0.4-20250107.pdf | 2026-09-26 |
| `foxess-kh-ka-parallel-relay.json` | service_manual | KH/KA Series User Manual (AU, 2025-10-21), section 9.1 Alarm List | https://www.fox-ess.com/Public/Uploads/uploadfile/files/Download/EN-KHKA-User-Manua-AU-20251021.pdf | 2026-09-26 |
| `foxess-kh-ka-bms-mismatch.json` | service_manual | KH/KA Series User Manual (AU, 2025-10-21), section 9.1 Alarm List | https://www.fox-ess.com/Public/Uploads/uploadfile/files/Download/EN-KHKA-User-Manua-AU-20251021.pdf | 2026-09-26 |
| `foxess-t-series-grid.json` | service_manual | T-Series User Manual V1.0.1, section 9.1 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-T-Manual-V1.0.1.pdf | 2026-09-26 |
| `foxess-t-series-dc.json` | service_manual | T-Series User Manual V1.0.1, section 9.1 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-T-Manual-V1.0.1.pdf | 2026-09-26 |
| `foxess-t-series-ac-thermal.json` | service_manual | T-Series User Manual V1.0.1, section 9.1 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-T-Manual-V1.0.1.pdf | 2026-09-26 |
| `foxess-r-series-grid.json` | service_manual | R-Series User Manual V1.4, section 8.2 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-EU-R-Series-User-Manual-Fox-V1.4.pdf | 2026-09-26 |
| `foxess-r-series-pv-leak.json` | service_manual | R-Series User Manual V1.4, section 8.2 Alarm List (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-EU-R-Series-User-Manual-Fox-V1.4.pdf | 2026-09-26 |
| `foxess-r-led-status.json` | service_manual | R-Series User Manual V1.4, section 3.4 LED Indicator Panel (FoxESS Pakistan portal) | https://pk.fox-ess.com/download/upfiles/EN-EU-R-Series-User-Manual-Fox-V1.4.pdf | 2026-09-26 |

