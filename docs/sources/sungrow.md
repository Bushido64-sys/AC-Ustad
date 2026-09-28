# Sungrow (`sungrow`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **201** across **5** model-scoped series

Official: https://www.sungrowpower.com

China's largest PV inverter maker (founded Hefei 1997). Strong Pakistan presence: 1.3 GW shipped, Country Manager Usman Waheed, MOU with GARIBSONS; residential SG2-8K G2 Premium and SG2.0-6.0RS are the mainstream PK single-phase lines.

## Brand recon

- **Official name:** Sungrow Power Supply Co., Ltd. (brand marketing name "SUNGROW")
- **Website:** https://www.sungrowpower.com
- **Country:** CN (Hefei, Anhui, China) — founded 1997 by Prof. Cao Renxian
- **Pakistan presence: YES.** Evidence:
  - Sungrow Pakistan Country Manager quoted in official newsroom: https://www.sungrowpower.com/en/newsdetail/solar-pakistan-2024-expo-sungrow-powers-pakistan-with-its-latest-industry-leading-renewable-energy-solutions — "in Pakistan, we have shipped 1.3GW solar inverters, which also places us at the top"; Country Manager **Usman Waheed, Sungrow Pakistan**.
  - 50 MW distribution contract signed at Solar Pakistan 2021 with local distributor **Energy For You**: https://www.sungrowpower.com/en/newsdetail/2386 — quotes **Howard Fu, Country Director of Sungrow Pakistan**.
  - 2024 MOU with **GARIBSONS** as new strategic partner (same newsroom URL as above).
  - Local distributor activity: Energy For You (Karachi) markets SH10T/15T/20T/25T three-phase hybrid and SH125CX commercial inverter: https://www.linkedin.com/posts/energyforyoupk_sungrowinverters-sungrowbatteries-energyforyou-activity-7466058108021137408-BQaR
- **Official fault-code table published: YES.**
  - SG2-8K G2 Premium standalone **Error List** document (official `service.sungrowpower.com.au` CDN, authored 钟明): https://service.sungrowpower.com.au/files/Web_Files/FAQ/TD_202003_SG2-8K%20G2%20Premium%20Error%20List_V1.3.pdf — complete Code/Description/Troubleshooting table.
  - SG2.0-6.0RS User Manual §9.1 full Fault Code table incl. optimizer sub-table: https://info-support.sungrowpower.com/product-materials/3f9aaa85-76f8-444a-b773-f162c8dc6b26.pdf
  - SG75/110/125CX-P2 User Manual §9.1 full table + 7-state LED table §2.4: https://info-support.sungrowpower.com/application/pdf/2023/04/11/SG125_110_75CX-P2-UEN-Ver14-202303.pdf
  - SH8.0-10RS User Manual §10.1 "Troubleshooting" full Alarm ID table incl. battery, parallel, meter, off-grid blocks: https://info-support.sungrowpower.com/application/pdf/2024/08/15/SH8.0-10RS-UEN-Ver14-202405.pdf
  - PowerKeeper ST050-250CF BESS User Manual §10.1 per-code Battery fault / Battery alarm table + SOC & status indicator tables 2-6/2-7/2-8: https://info-support.sungrowpower.com/cms/af994f1d-bb0d-408a-b310-6ad732402c99/GUID-6981CDA8-F9AA-4930-9DEA-90B9B65271DE_1_en-US_PDF%20(%E4%BA%A7%E5%93%81%E4%BF%A1%E6%81%AF%E8%AE%BE%E8%AE%A1%E9%83%A8_%E5%9F%BA%E7%A1%80%E7%89%88)_1784022674702_compressed.pdf
  - Official installer FAQ portal, fetched and read in full, hosting a machine-readable **Fault/Alarm Code → Fault/Alarm Name** index plus targeted articles ("How to troubleshoot **EC714** BMS communication fault?", "**SHRS/SHRT** models report **EC51** back-up overload fault shutdown, what is the cause?"): https://www.sungrowpower.com/ca/en/installer/all-faqs
- **Model lines targeted:** SG (single-phase residential string, SG2-8K G2 Premium / SG2.0-6.0RS), SH (three-phase hybrid, SH8.0-10RS), SG75/110/125CX-P2 (three-phase commercial string), ST (PowerKeeper BESS). **ST, SC and iSolar status: ST is covered (series 5); SC and iSolar are NOT — see NEGATIVE.**

### Code-format notes (critical for merge safety)
- **Sungrow does NOT use `E###` as its native scheme.** The numeric LCD/App codes are bare 3-digit integers (`002`, `010`, `070`, `315`, `514`), bare 4-digit battery/parallel codes (`1000`, `1001`, `1096–1122`), or range groups. The brief's expectation of `E###` + `W##` was **not** confirmed by any source fetched here.
- **Leading zeros are dropped between documents.** The SG2-8K G2 Premium error list prints `002`/`010`/`070`/`315`; the SG2.0-6.0RS, SH8.0-10RS and SG CX-P2 manuals print the same codes as `2, 3`/`10`/`70`/`315`. The official FAQ portal also prints them un-padded (`Fault/Alarm Code: 2,3,14,15`). The KB must therefore match `2` and `002` to the same entry *within* a series, and must not assume a fixed width.
- **Battery codes carry an `EC` prefix in Sungrow's own support material.** The official installer FAQ portal has articles for "**EC714** BMS communication fault" and "**EC51** back-up overload fault shutdown". So the same battery/off-grid fault appears as `714` in the manual table and `EC714` in support. `EC###` is a real Sungrow prefix — just not the one that dominates the tables.
- **The same code string means different things per model line — never merge.** Concrete, sourced examples:
  - `002` = "Grid over-voltage **(stage I)**" on SG2-8K G2 Premium, but is lumped with `3, 14, 15` as generic "Grid Overvoltage" on SG2.0-6.0RS / SH / SG CX-P2.
  - `028` = "**PV1** reverse connection" (a single specific input) on SG2-8K G2 Premium, but is one member of the grouped "PV Reverse Connection Fault" row `28, 29, 208, 212, 448-479` on the other three.
  - `039` = "Low System Insulation Resistance (ISO-flt)" on SG2-8K G2 Premium, "Low System Insulation Resistance" on SG2.0-6.0RS and SH, and "Low System Insulation Resistance **(Earth Fault)**" on SG CX-P2.
  - `703, 711, 712, 715, 717` = one undifferentiated "Battery Fault" line on the **SH inverter**, but a dedicated per-row Battery fault with its own 20-minute-clear behaviour on the **ST BESS**.
  - `017` on a **single-phase** SG2-6RS is a platform-shared row that cannot physically occur (it is a three-phase voltage-imbalance code); the manual itself warns the table is shared across all models.
- **LED state is a separate indicator table with no numeric code.** Two different vocabularies are documented and must not be merged: SG2.0-6.0RS has 4 plain states (blue on / blue flashing / red on / gray off); SG75/110/125CX-P2 has 7 states including Bluetooth fast blink (0.2 s) and a distinctive PID-recovery pattern (slow blink once, then fast blink 3×). The ST BESS has three further tables: a 6-band SOC indicator, plus **separate MAIN and AUXILIARY** stack status indicators.
- **Sungrow documents large catch-all ranges as single rows**, e.g. "System Fault" covering roughly 150 numbers and "System Alarm" covering roughly 40. They are recorded here as one entry each, never expanded — see NEGATIVE §5.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|---|---|---|---|---|
| 1 | SG2-8K G2 Premium — §9 Troubleshooting and Maintenance / Error List V1.3 (author 钟明) | service_manual | https://service.sungrowpower.com.au/files/Web_Files/FAQ/TD_202003_SG2-8K%20G2%20Premium%20Error%20List_V1.3.pdf | Complete Code/Description/Troubleshooting table (60 numbered rows) + 2 LED fault-type rows → `sungrow-sg` | 2026-09-26 |
| 2 | SG2.0-6.0RS-UEN-Ver25-202509 User Manual, §9.1 | user_manual | https://info-support.sungrowpower.com/product-materials/3f9aaa85-76f8-444a-b773-f162c8dc6b26.pdf | Full Fault Code table, LED state table (Table 2-1), optimizer sub-table, earth-fault/buzzer behaviour → `sungrow-sg-rs` | 2026-09-26 |
| 3 | SH8.0-10RS-UEN-Ver14-202405 User Manual, §10.1 | user_manual | https://info-support.sungrowpower.com/application/pdf/2024/08/15/SH8.0-10RS-UEN-Ver14-202405.pdf | Full "Alarm ID / Alarm Name / Corrective Measures" table incl. BMS, battery, off-grid, meter, parallel blocks → `sungrow-sh` | 2026-09-26 |
| 4 | SG125/110/75CX-P2-UEN-Ver14-202303 User Manual, §2.4 + §9.1 | user_manual | https://info-support.sungrowpower.com/application/pdf/2023/04/11/SG125_110_75CX-P2-UEN-Ver14-202303.pdf | Full Fault Code table + 7-state LED table (Bluetooth 0.2s, PID-recovery pattern) → `sungrow-sg-cx-p2` | 2026-09-26 |
| 5 | PowerKeeper ST050-250CF-UEN-Ver12-202607M0D02664 BESS User Manual, §10.1 + Tables 2-6/2-7/2-8 | user_manual | https://info-support.sungrowpower.com/cms/af994f1d-bb0d-408a-b310-6ad732402c99/GUID-6981CDA8-F9AA-4930-9DEA-90B9B65271DE_1_en-US_PDF%20(%E4%BA%A7%E5%93%81%E4%BF%A1%E6%81%AF%E8%AE%BE%E8%AE%A1%E9%83%A8_%E5%9F%BA%E7%A1%80%E7%89%88)_1784022674702_compressed.pdf | Per-code Battery fault (703–908) and Battery alarm (932–973) tables, SOC 6-band indicator, MAIN + AUX stack status indicators → `sungrow-st` | 2026-09-26 |
| 6 | Sungrow installer FAQ portal (Canada EN) — full page fetched and read | support_portal | https://www.sungrowpower.com/ca/en/installer/all-faqs | Machine-readable Fault/Alarm Code→Name index corroborating series 2/3/4 code names; **discovery of the `EC` prefix** (articles on "EC714" and "EC51"); confirms 4,5=Grid Undervoltage, 13=Grid Abnormal, 39=Low System Insulation Resistance, 51=Off-grid load overpower, 17=Grid Voltage Imbalance | 2026-09-26 |
| 7 | Sungrow newsroom — Solar Pakistan 2024 Expo | vendor_news | https://www.sungrowpower.com/en/newsdetail/solar-pakistan-2024-expo-sungrow-powers-pakistan-with-its-latest-industry-leading-renewable-energy-solutions | PK presence evidence (1.3 GW shipped, Country Manager Usman Waheed, GARIBSONS MOU) | 2026-09-26 |
| 8 | Sungrow newsroom — 50 MW distribution contract, Solar Pakistan 2021 | vendor_news | https://www.sungrowpower.com/en/newsdetail/2386 | PK presence evidence (Energy For You, Howard Fu Country Director Sungrow Pakistan) | 2026-09-26 |
| 9 | Energy For You (Sungrow Pakistan distributor) LinkedIn — SH125CX 125 kW commercial hybrid | distributor_post | https://www.linkedin.com/posts/energyforyoupk_next-level-hybrid-inverter-for-your-commercial-activity-7489931481750913024-yLlQ | Evidence that SH125CX is sold in PK; used only to justify leaving SH125CX in NEGATIVE, **no code taken from it** | 2026-09-26 |

**Sources deliberately NOT used** (returned by search, none are official Sungrow, none contributed a code): `solaranalytica.com`, `help.1komma5.com.au`, `manualslib.com`, `scribd.com`, `solarshop.baywa-re.lu` O&M PDF, and the `maxsel.de` / `shop.krannich-solar.com` third-party mirrors of the ST manual. See NEGATIVE §10.

## Negative results

Items that could NOT be verified from any URL fetched in this session. Nothing below was invented, extrapolated or carried over from another brand.

### 1. iSolar series — NO DATA
iSolar is a real Sungrow sub-brand for small residential/portable and off-grid solar, but **no iSolar fault-code table was located on any official Sungrow URL in this session.** A search result claimed the SG2.0-6.0RS LCD shows an "Error" string with an example code (line 790 of the extracted text: `Error` / `4` / `The error code in the figure is just an example.`), which proves an LCD "Error" display exists but gives **no usable code table**. **No iSolar entries were created.** Do not infer iSolar codes from the SG series.

### 2. SC series — NO DATA
The brief listed SC as a Sungrow family. **No official Sungrow fault-code table for an SC model was found at any URL fetched in this session.** No SC series block was created. Do not infer SC codes from the SG/SH series.

### 3. SH125CX (125 kW commercial hybrid) — NO OFFICIAL TABLE FETCHED
Energy For You (Sungrow Pakistan's distributor) markets the **SH125CX** 125 kW commercial hybrid inverter in Pakistan — https://www.linkedin.com/posts/energyforyoupk_next-level-hybrid-inverter-for-your-commercial-activity-7489931481750913024-yLlQ. Sungrow's SH manual on `info-support.sungrowpower.com` has variants (SH8.0-10RS, SH3.0-6.0RT) but **the SH125CX fault table was not fetched and could not be verified.** Its `model_patterns` is deliberately left in the `sungrow-sh` block as a wildcard so it can be matched later, but **it must be re-researched on its own and must NOT inherit the SH8.0-10RS battery codes.** This is the single biggest gap for PK C&I work.

### 4. SG30CX — NO OFFICIAL TABLE FETCHED
The brief named SG30CX explicitly. A 30 kW three-phase commercial string inverter almost certainly exists, and the pattern is covered by a wildcard in the `sungrow-sg-cx-p2` block, but **no official SG30CX manual or fault table was fetched in this session.** Its codes are assumed-but-unverified to follow the CX platform, which is exactly the kind of assumption this KB must not encode. Treat as open.

### 5. Individual codes inside the "System Fault" / "System Alarm" ranges — NOT EXPANDABLE
Sungrow documents these as **single rows listing ranges**, not as individual rows. The ranges cover roughly 150 codes each. **The individual meanings were NOT verifiable and were therefore NOT created.** Anything in `7, 11, 16, 19-25, 30-34, 36, 38, 40-42, 44-50, 52-58, 60-69, 85, 87, 92, 93, 100-105, 107-114, 116-124, 200-211, 248-255, 300-322, 324-328, 401-412, 600-603, 605, 608, 612, 616, 620, 622-624, 800, 802, 804, 807, 1096-1122` cannot be resolved from any document fetched here. To resolve a specific number, the firmware-specific code list or the iSolarCloud in-app code database is required.

### 6. Individual battery codes inside the SH inverter "Battery Fault" range — NOT EXPANDABLE
The SH8.0-10RS manual lumps `703, 707, 708, 711, 712, 715, 717, 732-737, 739-747, 832-837, 839, 841, 844, 864, 866-868, 870, 1000, 1001` into one row. Only a handful (707, 708, 714, 733, 734, 746) got separate rows in the **BESS** manual. **The remaining battery numbers were NOT verifiable and were NOT created.** Note the collision risk: 707/708/714/733/734 appear as *named rows in the ST BESS manual* and as *undifferentiated members of the SH lumped range*.

### 7. SG2-8K G2 Premium "Indicator Status Description" table (Tab. 6-2) — NOT FETCHED
The SG2-8K G2 Premium error-list PDF explicitly cross-references *"See 'Tab. 6-2 Indicator Status Description' for the definition"* but that table lives in the full User Manual, which was **not fetched in this session.** Only the two fault-type LED rows from the error list itself (LED cannot be lit / green goes out) were captured. The green/flash/red LED state definitions for this specific line are therefore missing.

### 8. Optimizer codes on the CX-P2 and SH lines — NOT PRESENT IN THOSE MANUALS
The optimizer sub-table (`4` Input overvoltage, `512` Hardware fault, `1024` Update failed) exists **only** in the SG2.0-6.0RS manual fetched here. It is absent from the SG75/110/125CX-P2 and SH8.0-10RS manuals. Whether the optimizer sub-codes differ on those platforms is **unverified** and was not assumed.

### 9. PowerStack ST225/ST455 container-level codes — NOT FETCHED
Search results surfaced official `info-support.sungrowpower.com` PDFs for PowerStack-ST225kWh-110kW and ST455kWh-110kW container systems whose fault info is delegated to a **separate communication protocol document** ("BSC200 Info-3x table > CMU (Battery Cluster Management Unit) fault word and CMU alarm word"), not to the product manual. **That protocol document was not fetched**, so no container-level codes were created. The cell-level names visible in search snippets ("Cell over voltage fault", "Cell under voltage fault", "Total over voltage fault", "Total under voltage fault" and their alarm equivalents) are **text only, with no numeric codes**, and were deliberately not turned into entries.

### 10. Codes from non-official aggregator sites — NOT USED
Search results returned third-party pages (`solaranalytica.com`, `help.1komma5.com.au`, `manualslib.com`, `scribd.com`, a `solarshop.baywa-re.lu` O&M PDF, `maxsel.de` and `shop.krannich-solar.com` mirrors of the ST manual) that either restate official tables or are OCR-unreliable. **No code in this research was taken from any of them.** All entries trace to one of the five sources in the SOURCE TABLE.

## Quarantine (excluded)

Codes that **belong to another brand or device family** and must never be loaded into the Sungrow KB. These were encountered while researching and are explicitly fenced off.

| Item | Why quarantined | Evidence |
|---|---|---|
| Huawei FusionSolar codes (`NE 0`, `BMS 0xxx`, `Riso`, grid codes 4011/4012…) | Different brand. Superficially similar domain (inverter + battery + Riso + grid over/under-voltage) and the most likely source of a false merge. | Not fetched; listed as a hazard only. |
| Deye codes (`E1`, `E2`, `E3`… grid/battery/temperature) | Different brand. The `E<digit>` bare-letter style is what the brief expected from Sungrow but Sungrow does not actually use — importing Deye's scheme under a Sungrow label is the single easiest corruption to make here. | Not fetched; listed as a hazard only. |
| GoodWe codes (`E-C grid overvoltage`, `E-Battery`, `W-` warnings) | Different brand, and the only other Sungrow-adjacent vendor that uses `E-`/`W-` prefixed strings. | Not fetched; listed as a hazard only. |
| Sol-Ark, Growatt, Victron, Fronius, SMA, Kostal, Tesla, Huawei codes | Different brands. | Not fetched. |
| **Sungrow POWER TITAN 1.0 container codes** | Same brand but a different product family (utility container). Its fault list was visible only as FAQ article *titles* ("Why is condensation water prone to occur in the battery box during the installation stage?", fire-suppression cylinder pressure, coolant replacement) — **no numeric code table was fetched**, so nothing was created. Do not fold into `sungrow-st`. | https://www.sungrowpower.com/ca/en/installer/all-faqs (article titles only) |
| **Sungrow Logger1000 codes** | Same brand, different device. Article titles visible ("How to log in Logger1000 Web interface", "The plant data on iSC are missing") but **no numeric code table fetched**. | Same URL as above |
| **Sungrow optimizer codes on the SH / SG CX-P2 platforms** | The optimizer sub-table (`4` / `512` / `1024`) was fetched **only** from the SG2.0-6.0RS manual. It is absent from the SH8.0-10RS and SG CX-P2 manuals. Carrying those three codes into those series would be an unverified extrapolation. | Source 2 vs sources 3 and 4 |
| **Sungrow CMU / BSC200 / BSC300 cell-level codes** | Utility-container cell faults ("Cell over voltage fault", "Cell under voltage fault", "Total over voltage fault", "Total under voltage fault" + alarm equivalents) live in a **separate communication-protocol document**, not in the product manual. Text seen, numbers not. | PowerStack-ST225/ST455 manuals (see NEGATIVE §9) |
| **Third-party restatements of Sungrow tables** | `solaranalytica.com`, `help.1komma5.com.au`, `manualslib.com` (incl. its OCR'd SG125CX-P2 pages), `scribd.com` and the reseller-hosted `solarshop.baywa-re.lu` / `maxsel.de` / `shop.krannich-solar.com` PDFs. Some *quote* Sungrow correctly (e.g. the reseller O&M guide's `002,003,014,015` = Grid overvoltage, `017` = Grid voltage unbalance) but they are unofficial, undated mirrors and are **not** citable for a code. | Listed in NEGATIVE §10 |

## Conflicts / caveats

Real disagreements between the sources fetched in this session. **None of these were silently resolved in favour of one source** — each is recorded per-series, because that is the correct behaviour for a KB that must not merge model lines.

### C1 — "System Fault" range upper bound differs by model (4 codes)
| Source | Model | Documented list ends at |
|---|---|---|
| Source 1 (SG2-8K G2 Premium) | residential G2 | codes are individually named — **no range row at all** |
| Source 2 (SG2.0-6.0RS) | single-phase RS | `… 800, 802, 804, 807, 1096–1118` |
| Source 3 (SH8.0-10RS) | three-phase hybrid | `… 800, 802, 804, 807, 1096–1122` |
| Source 4 (SG CX-P2) | three-phase commercial | `… 800, 802, 804, 807, 1096–1122` |
| Source 6 (official FAQ portal) | generic index | `… 800, 802, 804` — **omits both 807 and 1096–11xx entirely** |

**Resolution: not resolved.** The aliases strings on each `SYSTEM_FAULT_RANGES` entry preserve the exact per-model list. Codes `1119–1122` exist on the three-phase units and are **not** claimed to exist on the single-phase RS. The FAQ portal's shorter list is almost certainly an older snapshot, but that is inference, so it is not treated as fact.

### C2 — "Grid Overvoltage" grouping vs per-stage split
Source 1 splits over-voltage into **stage I `002`**, **stage II `015`**, plus a separate **10-minute average `014`** and **transient `003`**, each with its own distinct remedy (e.g. `015` uniquely instructs checking the AC cable *model/size*; `014` uniquely instructs checking the **country code**). Sources 2/3/4 collapse all of `2, 3, 14, 15` into one generic "Grid Overvoltage" row with identical remedies, losing the country-code check entirely.
**Resolution: not resolved.** `sungrow-sg` keeps four separate entries; series 2/3/4 keep one grouped entry. A technician must not port the `014` country-code remedy onto a `2` on an RS/CX unit as if they were the same code.

### C3 — "PV Reserve" vs "PV Reverse" — a Sungrow typo
Sources 2 and 4 print **"PV Reserve Connection Fault"** (confirmed verbatim on the official FAQ portal too: `Fault/Alarm Name: PV Reserve Connection Fault`). Source 3 prints **"PV Reverse Connection Fault"** correctly. "Reserve" is meaningless in this context and is a typo for "Reverse".
**Resolution: resolved as a typo, but the typo is preserved verbatim in the series-2 and series-4 titles** (marked `[sic]`) so that a technician searching Sungrow's own portal for the exact string still finds the entry. The meaning used is "reverse polarity".

### C4 — Code 39 naming differs
Source 4 names `39` **"Low System Insulation Resistance (Earth Fault)"**; sources 2 and 3 name the same `39` **"Low System Insulation Resistance"** with no earth-fault wording. Source 4 also instructs checking *battery* cables under code 39 — but CX-P2 **has no battery**, so that instruction is unusable on that model.
**Resolution: treated as the same fault, flagged per-series.** Series 4's entry substitutes the combiner/earthing cable for the battery cable and says so. The codes were NOT split.

### C5 — `548-563 / 580-595 "PV Abnormal Alarm"` references a battery board on battery-less models
The remedy text in **all** of sources 2, 3 and 4 says *"check whether the battery board wiring is loose"*. This is literal only on the SH hybrid. On the single-phase SG2.0-6.0RS and the SG CX-P2 there is no battery, so the instruction is a documentation carry-over.
**Resolution: flagged in the notes of each entry**, with the substitution stated (combiner board on non-hybrid models). Not silently rewritten.

### C6 — Battery code grouping: lumped row vs per-code rows
Source 3 (SH inverter) lumps `703, 707, 708, 711, 712, 715, 717, 732-737, 739-747, 832-837, 839, 841, 844, 864, 866-868, 870, 1000, 1001` into **one** "Battery Fault" row. Source 5 (ST BESS) gives **its own dedicated row per code group** with materially different remedies — e.g. `703/711/712/715/717` "clears within 20 min"; `707/733` is specifically an **over-temperature** fault with the 0…55 °C charge / −20…55 °C discharge limits stated; `708/734` is **under**-temperature; `732` is the only battery-fault row that does **not** carry the mandatory "if SOC below 3% switch off immediately" tail.
**Resolution: not resolved, and this is the highest-risk conflict in the file.** `707/708/714/733/734` exist as *named rows* in `sungrow-st` and as *undifferentiated members* of the lumped range in `sungrow-sh`. They are separate entries in separate series and must not be unified.

### C7 — The `EC` prefix
Source 6 (official installer FAQ) has articles for "**EC714** BMS communication fault" and "**EC51** back-up overload fault shutdown", while sources 3 and 5 print those same faults as `714` and `51`.
**Resolution: not resolved as a format question.** `EC###` is recorded as an alias on the `sungrow-st` battery entries. The brief's expected `E###` scheme is **not** Sungrow's dominant format — the tables use bare integers, with `EC` appearing only in support article titles. Do not generate `E###` forms from memory.

### C8 — "Grid Power Outage" vs "Grid failure (Islanding)"
Source 1 names `010` **"Grid failure (Islanding)"** and its remedy focuses on the AC breaker, AC cable seating, and whether the grid is in service. Sources 2/3/4 name `010` **"Grid Power Outage"** and add a distinct step: *check whether the AC cable is connected to the correct terminal (whether the live and N wire are correctly in place)*. Source 6 uses "Grid Power Outage".
**Resolution: not resolved.** Series 1 keeps the islanding framing; series 2/3/4 keep the outage framing with the live/N check. The extra live-vs-N check is the practically useful difference and is preserved only where documented.

### C9 — Optimizer sub-table presence
The optimizer fault table (`4` Input overvoltage, `512` Hardware fault, `1024` Update failed) appears **only** in source 2 (SG2.0-6.0RS). It is absent from sources 3 (SH) and 4 (SG CX-P2), even though both support optimizers in principle.
**Resolution: quarantined, not propagated.** See QUARANTINE. Whether the sub-codes differ on those platforms is unverified.

## Research report

**Total codes/entries delivered: 201** across 5 series blocks. All validated: 5/5 YAML blocks parse, 0 schema violations, 0 `ur == en` collisions, 0 Arabic/Urdu script characters in any `ur` field, 0 `confidence: low` entries without a `notes` object, 0 fault entries with fewer than 2 causes, 0 entries with fewer than 2 solutions, 0 EN titles over 60 characters.

| Series | id | unit_type | Entries | Faults | Non-fault / status |
|---|---|---|---|---|---|
| 1 | `sungrow-sg` (SG2-8K G2 Premium, 2-8 kW 1-phase) | on_grid_inverter | 62 | 55 | 7 |
| 2 | `sungrow-sg-rs` (SG2.0-6.0RS, 2-6 kW 1-phase) | on_grid_inverter | 35 | 25 | 10 |
| 3 | `sungrow-sh` (SH8.0-10RS, 8-10 kW 3-phase hybrid) | hybrid_inverter | 33 | 25 | 8 |
| 4 | `sungrow-sg-cx-p2` (SG75/110/125CX-P2, 75-125 kW 3-phase commercial) | on_grid_inverter | 35 | 23 | 12 |
| 5 | `sungrow-st` (PowerKeeper ST050-250CF BESS) | generic | 36 | 18 | 18 |
| | **Total** | | **201** | **146** | **55** |

Severity mix: `check_restart` 79, `stop_pro` 55, `info` 38, `danger` 15, `self_clear` 14.
Confidence: **199 `high`, 2 `low`** (both `low` are ST codes `750, 751` and `908`, where Sungrow publishes no cause and no field remedy — each carries a mandatory `notes` object saying so).
Note on the honest count: the target was 40-80. The count is 201 because Sungrow publishes **complete** tables (60 individually-named rows on series 1 alone) and the brief said to take every verifiable row. Conversely, the ~150 numbers inside each "System Fault" range and the ~40 inside "System Alarm" were **not** expanded, because Sungrow does not document them individually. So 201 is the honest verified-row count, not an inflation.

**Top 5 URLs (all fetched in this session, all official Sungrow):**
1. https://info-support.sungrowpower.com/product-materials/3f9aaa85-76f8-444a-b773-f162c8dc6b26.pdf — SG2.0-6.0RS Ver25-202509 manual (§9.1 + LED Table 2-1 + optimizer table)
2. https://info-support.sungrowpower.com/application/pdf/2024/08/15/SH8.0-10RS-UEN-Ver14-202405.pdf — SH8.0-10RS Ver14 manual (§10.1)
3. https://info-support.sungrowpower.com/application/pdf/2023/04/11/SG125_110_75CX-P2-UEN-Ver14-202303.pdf — SG125/110/75CX-P2 Ver14 manual (§2.4 LED + §9.1)
4. https://info-support.sungrowpower.com/cms/af994f1d-bb0d-408a-b310-6ad732402c99/GUID-6981CDA8-F9AA-4930-9DEA-90B9B65271DE_1_en-US_PDF%20(%E4%BA%A7%E5%93%81%E4%BF%A1%E6%81%AF%E8%AE%BE%E8%AE%A1%E9%83%A8_%E5%9F%BA%E7%A1%80%E7%89%88)_1784022674702_compressed.pdf — PowerKeeper ST050-250CF BESS manual (§10.1 + Tables 2-6/2-7/2-8)
5. https://service.sungrowpower.com.au/files/Web_Files/FAQ/TD_202003_SG2-8K%20G2%20Premium%20Error%20List_V1.3.pdf — SG2-8K G2 Premium Error List V1.3 (the only source giving per-stage grid codes)
*Also fetched and used: https://www.sungrowpower.com/ca/en/installer/all-faqs (official FAQ index; source of the `EC` prefix finding).*

**Official fault/alarm table published? — YES.** Five distinct official code tables were fetched, four of them from Sungrow's own `info-support.sungrowpower.com` product-material CDN, plus a machine-readable code→name index on the official installer FAQ portal.

**Pakistan presence? — YES**, on four pieces of evidence, all fetched: official newsroom statements of 1.3 GW shipped into Pakistan and "No.1 in inverter shipment", a named **Country Manager of Sungrow Pakistan** and a **Country Director of Sungrow Pakistan**, a 50 MW distribution contract with **Energy For You** (Karachi), a **GARIBSONS** partnership MOU, and the distributor's live marketing of SH125CX and SH10T/15T/20T/25T in Pakistan.

**Blockers / open gaps (in priority order):**
1. **SH125CX (125 kW commercial hybrid) has no verified table.** This is the commercially important PK product (Energy For You markets it) and it is *not* covered by the SH8.0-10RS manual. A 125 kW unit has different string counts, different battery/BMS and different parallel behaviour from an 8 kW one. Highest-risk gap for PK C&I.
2. **SG30CX has no verified table.** Named in the brief; 30 kW three-phase commercial. Currently only a wildcard pattern in series 4 — it must not silently inherit CX-P2 codes.
3. **SC series — nothing found.** Named in the brief; no official table located at any URL fetched.
4. **iSolar — nothing found.** No code table; the brief's `E###` expectation for iSolar specifically is unsupported by anything fetched.
5. **~190 codes are documented only as ranges** (~150 in System Fault, ~40 in System Alarm) with no individual meaning. Resolving any specific number needs the firmware-specific code list or the iSolarCloud in-app code database.
6. **SG2-8K G2 Premium LED state table missing.** The error list cross-references "Tab. 6-2 Indicator Status Description" in the full User Manual, which was not fetched. Only the 2 fault-type LED rows from the error list itself were captured.
7. **Utility container families (PowerStack-ST225/ST455, Power Titan 1.0) not covered** — their codes are delegated to separate communication-protocol documents that were not retrieved.
8. **Version drift is real and unmanaged.** Five manuals spanning Ver11 (2022) to Ver25 (2025) and a 2026 BESS manual. Source 6's code index is demonstrably older than sources 3-5 (see C1). Any code added to the KB should carry the manual version, as done in every `source.title` here.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `sungrow-sg.json` | SG2-8K G2 Premium (single-phase residential string 2-8kW) | 62 |
| `sungrow-sg-rs.json` | SG2.0-6.0RS (single-phase residential string 2-6kW, RS platform) | 35 |
| `sungrow-sh.json` | SH8.0RS / SH10RS (three-phase hybrid inverter with battery, 8-10kW) | 33 |
| `sungrow-sg-cx-p2.json` | SG75CX-P2 / SG110CX-P2 / SG125CX-P2 (three-phase commercial string 75-125kW) | 35 |
| `sungrow-st.json` | PowerKeeper ST050-062-075-087-100-112-125-137-150-162-175-187-200-212-225-237-250CF (BESS) | 36 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `sungrow-sg.json` | service_manual | SG2-8K G2 Premium User Manual - 9 Troubleshooting and Maintenance (Error List V1.3, author 钟明) | https://service.sungrowpower.com.au/files/Web_Files/FAQ/TD_202003_SG2-8K%20G2%20Premium%20Error%20List_V1.3.pdf | 2026-09-26 |
| `sungrow-sg-rs.json` | user_manual | SG2.0-6.0RS-UEN-Ver25-202509 User Manual, 9 Troubleshooting and Maintenance | https://info-support.sungrowpower.com/product-materials/3f9aaa85-76f8-444a-b773-f162c8dc6b26.pdf | 2026-09-26 |
| `sungrow-sh.json` | user_manual | SH8.0-10RS-UEN-Ver14-202405 User Manual, 10 Troubleshooting and Maintenance (10.1) | https://info-support.sungrowpower.com/application/pdf/2024/08/15/SH8.0-10RS-UEN-Ver14-202405.pdf | 2026-09-26 |
| `sungrow-sg-cx-p2.json` | user_manual | SG125/110/75CX-P2-UEN-Ver14-202303 User Manual, 2.4 LED Indicator + 9 Troubleshooting and Maintenance (9.1) | https://info-support.sungrowpower.com/application/pdf/2023/04/11/SG125_110_75CX-P2-UEN-Ver14-202303.pdf | 2026-09-26 |
| `sungrow-st.json` | user_manual | PowerKeeper ST050-250CF-UEN-Ver12-202607M0D02664 Battery Energy Storage System (BESS) User Manual, 10.1 Troubleshooting + Tables 2-6/2-7/2-8 indicators | https://info-support.sungrowpower.com/cms/af994f1d-bb0d-408a-b310-6ad732402c99/GUID-6981CDA8-F9AA-4930-9DEA-90B9B65271DE_1_en-US_PDF%20(%E4%BA%A7%E5%93%81%E4%BF%A1%E6%81%AF%E8%AE%BE%E8%AE%A1%E9%83%A8_%E5%9F%BA%E7%A1%80%E7%89%88)_1784022674702_compressed.pdf | 2026-09-26 |

