# GoodWe (`goodwe`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **79** across **11** model-scoped series

Official: https://www.goodwe.com

Chinese maker of on-grid, hybrid and storage inverters plus batteries and BMS. Pakistan is an official GoodWe market with a dedicated en.goodwe.com/pakistan portal, warehouses in Rawalpindi and Lahore and service hubs in Karachi and Lahore; the SDT G3 8-30kW three-phase hybrid is one of the lines actively marketed there.

## Brand recon

- **Brand**: GoodWe Technologies Co., Ltd. (brand code prefix `GW`), founded 2010, HQ Suzhou, China. Vertical (cell→module→inverter→EMS) plus ESS.
- **Website**: https://www.goodwe.com (regional portals: en.goodwe.com, us.goodwe.com, nl.goodwe.com, it.goodwe.com, goodwe.com.au, goodwe.com.pl).
- **Category**: inverter — on-grid, hybrid, energy storage (+ battery/BMS, AFCI, Smart Energy).
- **Pakistan presence**: CONFIRMED official — GoodWe runs a dedicated Pakistan portal at https://en.goodwe.com/pakistan (fetched 2026-09-26, HTTP 200). It states warehouse "Sec D Ext lane 1, Bahria Town Phase 8 Rawalpindi" + "Maraka, Lahore", "fully equipped service hubs in Karachi and Lahore", service UAN 03311110217, Urdu/English SEMAR app support, and markets ES Uniq, SDT G3, Lynx A G3, ET Hybrid (HV), BAT-S, GT, SMT, UT and HT lines in-country. So PK is a direct GoodWe market, not just a distributor-import channel.
- **Monitoring**: SolarGo app (older: PV Master / SEMS+), SEMS Portal (semsportal.com). Codes surface on LCD, in app, and by email.
- **Scheme note (critical, re-verified against the fetched PDFs)**: GoodWe runs at least **five** mutually incompatible schemes. (a) F01…F163 on SDT-G3; (b) F01…F165 on EH/EH Plus — the *same numbers do not always mean the same thing* (SDT F32 = Inverter Internal Failure vs EH F32 = Relay Check Abnormal). (c) bare integers 1–35 on ES-US/SBP-US. (d) bare integers 1–39 on UT (utility, 250–350 kW) — a *different* bare-integer table from (c). (e) 2-digit SolarGo/PV Master app codes (00…31 plus `xxETU` three-phase variants) on EMEA EM/ET/BT, with separate bare-integer 1–17 battery/BMS and 1–16 system-fault tables. (f) named-message strings ("Utility Loss", "VAC Failure", "Isolation Failure"…) on ES/SBP/ET/EHB legacy firmware. Never merge schemes; resolve the model number first.
- **Research date**: 2026-09-26.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | GoodWe SDT-G3 User Manual (en.goodwe.com FTP) | official manual (PDF) | https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_SDT-G3_User-Manual-EN.pdf | F01–F163 fault table for SDT-G3 | 2026-09-26 |
| 2 | GoodWe EH User Manual (nl.goodwe.com FTP) | official manual (PDF) | https://nl.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_EH_User%20Manual-EN.pdf | F01… fault table for EH/EH Plus | 2026-09-26 |
| 3 | GoodWe ES-US / SBP-US User Manual (us.goodwe.com FTP) | official manual (PDF) | https://us.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_ES-US,%20SBP-US_User%20Manual-EN.pdf | bare-integer code table for ES-US | 2026-09-26 |
| 4 | GoodWe ESS Troubleshooting Guide EMEA (goodwe.com.pl) | official troubleshooting guide (PDF) | https://www.goodwe.com.pl/goodwe_download/GW_ESS%20Troubleshooting%20Guide-EMEA_EN.pdf | 2-digit app codes for EM/ET + LED legend | 2026-09-26 |
| 5 | GoodWe SBP User Manual (en.goodwe.com FTP) | official manual (PDF) | https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_SBP_User%20Manual-EN.pdf | named-message error table for SBP | 2026-09-26 |
| 6 | GoodWe EHB User Manual (goodwe.com.au FTP) | official manual (PDF) | https://www.goodwe.com.au/Ftp/EN/Downloads/User%20Manual/GW_EHB_User%20Manual-EN.pdf | named-message error table for EHB | 2026-09-26 |
| 7 | GoodWe UT User Manual (it.goodwe.com FTP) | official manual (PDF) | https://it.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_UT_User%20Manual-EN.pdf | utility-scale F-codes + symptom handling for UT | 2026-09-26 |
| 8 | GoodWe ES Series Hybrid Inverter User Manual V1.2 (en.goodwe.com FTP) | official manual (PDF) | https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_ES_User%20Manual-EN.pdf | named-message error table + LED legend for GW3648D-ES / GW5048D-ES | 2026-09-26 |
| 9 | GoodWe ET Series / ET Plus Series Hybrid Inverter User Manual V1.1 (en.goodwe.com FTP) | official manual (PDF) | https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_ET_User%20Manual-EN.pdf | named-message error table for ET / ET Plus+ (incl. Utility Phase Failure, PV/BAT Overvoltage) | 2026-09-26 |
| 10 | GoodWe EM Series User Manual V1.3 (en.goodwe.com FTP) | official manual (PDF) | https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_EM_User%20Manual-EN.pdf | model scope reference for EMEA EM line | 2026-09-26 |
| 11 | GoodWe Pakistan market portal | official country page (HTML) | https://en.goodwe.com/pakistan | PK presence: service hubs, warehouse, product lines marketed in PK | 2026-09-26 |
| 12 | GoodWe global site country selector (en.goodwe.com home) | official site (HTML) | https://en.goodwe.com/ | confirmation that a dedicated "Pakistan (English)" market portal exists | 2026-09-26 |

All 12 sources were re-fetched live on 2026-09-26 (PDF text extracted with `pdftotext -layout`; every excerpt below is a verbatim line from that extraction). No aggregator/mirror was used. `Ftp/EN/Downloads/User Manual/` directory listing is 403, so manual discovery is by exact filename — treat any filename not listed above as unverified.

## Negative results

Things that could NOT be verified from a URL fetched on 2026-09-26. **Not entered as codes.** Do not add these without a primary source.

| Item | Why it is not in a series | What was actually checked |
|------|---------------------------|--------------------------|
| GoodWe **G3 / ES Uniq / Lynx A / BAT-S / HT** fault codes (the current Pakistan product page lines) | No fault table for these models was reachable. The PK product page only has "Download" buttons to files behind the site's JS, and `Ftp/EN/Downloads/User Manual/` returns HTTP 403 for directory listing, so filenames could not be enumerated. | `https://en.goodwe.com/pakistan` (fetched, product list confirmed); HEAD probes of 10 guessed ES/ET/EM/SDT/ESH filenames on en.goodwe.com. |
| **ES01 / ES05 / ES06 / ES07** legacy F-code tables | Not confirmed to exist as separate documents. The only ES documents found are `GW_ES_User Manual-EN.pdf` (GW3648D-ES, GW5048D-ES, named messages) and `GW_ES-US,%20SBP-US_User Manual-EN.pdf` (bare integers 1-35). The "ES01/ES05/ES06/ES07" model names could not be found in any fetched GoodWe document. | `GW_ES_User Manual-EN.pdf` and `GW_ES-US,...pdf` both fetched and text-searched for "ES01", "ES05", "ES06", "ES07" — zero hits. |
| **SDT-G3 codes F165 and above** | The SDT-G3 manual's own table stops at F163; §7.4.5 then switches to a name-only table (Generator Failure, BMS Status Bit Error, Ambient Overtemperature, PV/BAT/AC Terminal Overtemperature Warning, Three-phase on-grid fault, External STS Failure, Parallel Comm Timeout Shutdown, Three-phase off-grid phase loss, EPO, gas alarm). Those named §7.4.5 rows were **not** given their own series in this pass — the numeric series are complete first. | `GW_SDT-G3_User-Manual-EN.pdf` §7.4.1–7.4.4 (F01–F163) and §7.4.5 read in full. |
| **EHB / EH / SBP named-message rows** not listed in SERIES 7–9 (`DC Injection High`, `EEPROM R/W Failure`, `SPI Failure`, `DC Bus High`, `Back-Up Over Load`) | These strings are verified verbatim in the ES, SBP and EHB PDFs, but the numeric + LED series were prioritised for this pass. They are safe to add as a `goodwe-*-named` extension; the wording is already extracted. | `GW_SBP_User Manual-EN.pdf` §4.1, `GW_EHB_User Manual-EN.pdf` §8.1, `GW_ES_User Manual-EN.pdf` §4.1 all fetched. |
| **EM series error codes** | `GW_EM_User Manual-EN.pdf` fetched successfully (18 pages) but it contains **no error/fault code table at all** — only installation, wiring and technical data. The EMEA ESS guide covers "ET, ET PLUS+ and BT" and "ES, SBP Series" explicitly; EM is not named as a code-table subject there either. | `GW_EM_User Manual-EN.pdf` text-searched for "Fault", "Error" → only `Max. Output Fault Current` and the IEC 62109-2 13.9 fault-indicator sentence. |
| **BT series** own fault table | BT is named in the EMEA ESS guide's LED table but has no dedicated fault table. Mapping SERIES 5/6/10 to BT is an inference, so BT is only listed in `model_patterns` with that caveat. | `GW_ESS Troubleshooting Guide-EMEA_EN.pdf` §03 and §4.3. |
| **Wi-Fi troubleshooting rows 1–9** (Cannot find Solar-WiFi, Cannot connect, Cannot Login 10.10.100.253, Cannot Find Router SSID, LED blinks twice, Cannot find signal, keeps going offline, blinks 4 times, offline on SEMS) | Verified in the EMEA guide §06, but these are connectivity symptoms, not inverter fault codes, and were out of scope for the fault-code series. | `GW_ESS Troubleshooting Guide-EMEA_EN.pdf` §06 read in full. |
| **ESS system-fault table rows 1–16** (Abnormal battery charging, Terminal Burnt, All LED off, All LED on, Backup output shut down, Meter test fail, No meter data, PV production much lower than expected, batteries do not communicate, inverter does not power on, Wi-Fi connection, Noise, Reconnecting, Waiting, ET inverter disconnects on BMS 4096) | Verified in the EMEA guide §05.1, but left out to keep the total inside the 40–80 target. Note the bare integers 1–16 here collide with SERIES 3/4/6 and must get their own series if added. | `GW_ESS Troubleshooting Guide-EMEA_EN.pdf` §05.1 read in full. |
| **SEC1000S troubleshooting** (13 steps) | Verified in EMEA guide §07; it is a comms-device procedure, not a code table. | Same PDF §07. |
| Any **aggregator / mirror** (solarnevs.com and similar) | Deliberately not used. Every code here comes from a `*.goodwe.com` / `goodwe.com.pl` document server or the official country site. | No third-party domain fetched. |
| **Pakistan-specific** fault-code list or SEMAR-app local code table | The PK portal mentions an Urdu/English **SEMAR** app, but no SEMAR code table document was reachable from the URLs fetched. | `https://en.goodwe.com/pakistan` fetched and text-extracted in full. |

## Quarantine (excluded)

No third-party brand codes were imported, so nothing had to be quarantined on brand grounds. Recording the near-misses that were deliberately kept out:

| Kept out | Reason |
|-----------|--------|
| Generic `CE1`–`CE9` / `PRO` / `OV` / `UND` style codes seen on European single-phase inverters | These are Huawei/SMA/Fronius/Growatt-style schemes. GoodWe does **not** document any `CE*` scheme in any of the 10 manuals fetched. Adding them would be a memory-based fabrication. |
| Huawei `NE 040`, `BMS Fault`, `Battery 1 connect Fault` etc. | Other brand. Not in scope. |
| SMA `2001`–`7999`, `AC side` category codes | Other brand. Not in scope. |
| Growatt / Solis / Deye / SunGrow numeric strings | Other brand. Not in scope. |
| GoodWe **"Grid voltage too low (CE1)"**-style entries that appear on solarnevs.com and similar mirrors | Not GoodWe's own scheme. GoodWe uses F-codes, bare integers, 2-digit app codes and named messages — never `CE*`. Not fetched, not used. |
| GoodWe **"F43 = Grid Waveform Abnormal"** style rows promoted to a generic "GoodWe F-series" table | Real GoodWe strings, but they are line-scoped: F43 is a documented EH row and is absent from SDT-G3. Kept scoped to their own series rather than flattened into one generic F-series. |

## Conflicts / caveats

Same code string, different meaning per product line. **This is the single biggest ingestion risk in the GoodWe KB.** All of these were confirmed by reading the cited tables on 2026-09-26.

### 1. Bare integers are reused across four unrelated tables
| Code | SERIES 3 (ES-US/SBP-US) | SERIES 4 (UT) | SERIES 5 (EMEA app) | SERIES 6 (EMEA battery) |
|------|--------------------------|----------------|----------------------|-------------------------|
| `1` | Utility Loss | **SPI Comm Fail** | (not used; app table starts 00) | **High battery temperature** |
| `2` | Grid Overvoltage | EEPROM Fail | (not used) | Low battery temperature |
| `3` | Grid Rapid Overvoltage | Grid frequency out of range (Fac Fail) | (not used) | Battery cell voltage differences |
| `4` | Grid Undervoltage | DC-SPD (lightning) | (not used) | Battery over total voltage |
| `5` | Grid 10min Overvoltage | Night DCSP Fail | (not used) | Battery discharge overcurrent |
| `7` | Grid Overfrequency | BUS-start Fail | (not used) | **Battery under SOC** |
| `9` | Anti-islanding | Night BUS Fault | **Utility Loss ac / AC / FAC Failure** | **Battery communication failure** |
| `10` | LVRT Undervoltage | CPLD Error | **Ground I failure** | Battery output shortage |
| `12` | **Abnormal GFCI 30mA** | **ISO Fail** | **Internal Fan Failure / back-up overload** | **BMS module fault** |
| `13` | Abnormal GFCI 60mA | Vac Failure | **Over Temperature** | BMS system fault |
| `15` | Abnormal GFCI (gradual) | **GFCI Chk Fail** | **PV overvoltage** | High battery charge temperature |
| `16` | Large DC of AC current L1 | **AFCI Fault** | (not used) | High battery discharge temperature |
| `18` | **Low Insulation Res.** | (not present) | (not used) | (not present) |
| `19` | Abnormal Ground | **Gnd I Fail** | (not used) | (not present) |
| `20` | Anti-reverse current (hardware) | Utility Loss | (not used) | (not present) |
| `24` | **Relay Check abnormal** | **SPD Fail** | (not used) | (not present) |
| `25` | **(no row 25 — table jumps 24→26)** | **DC Switch Fail** | **Relay check failure** | (not present) |
| `26` | Flash Fault | Ref-V Chk Fail | **Battery Licence Fault** | (not present) |
| `27` | DC Arc Fault | HCT Chk Fail | (not used) | Phase angle failure (27ETU) |
| `29` | (Cavity Overtemperature — dropped from this pass) | PV Over Curr | (part of the `09 17 29` row) | BMS internal fault |
| `30` | BUS Overvoltage | **Model Error** | **EEPROM R/W Failure** | (not present) |
| `31` | PV Input Overvoltage | PV Short Failure | Internal Communication Failure | (not present) |
| `33` | PV Continuous Software Overcurrent | **PV Over Voltage** | (not used) | (not present) |
| `35` | String1/String2 PV String Reversed | PV HCT Fail | (not used) | (not present) |

**Rule:** resolve line first (from the model number on the nameplate), then the code. Never key on the number alone.

### 2. F-prefixed codes: SDT-G3 and EH share the look but not the mapping
| Code | SERIES 1 (SDT-G3, `*SDT*`) | SERIES 2 (EH, `*EH*`) |
|------|-------------------------------|------------------------|
| `F04` | Grid Rapid Overvoltage Protection | Grid Rapid Overvoltage Protection (same) |
| `F16` | DCI **Primary** Protection | DCI **Level 1** Protection |
| `F17` | DCI **Secondary** Protection | DCI **Level 2** Protection |
| **`F32`** | **Inverter Internal Failure** | **Relay Check Abnormal** — opposite meaning |
| `F33` | Flash Read/Write Error | Flash Read/Write Error (same) |
| `F34` | AFCI Check Failure | AFCI Check Failure (same) |
| `F40` | String Reverse Connection, String 1-n, paired with F98 | String Reverse Connection, **String 1~16** (EH also lists 33~48 as a `-` row) |
| `F98` | String Reverse Connection (paired with F40) | String Reverse Connection, **String 17~32** |
| `F42` | DC Arcing Failure, String 1-n (general) | DC Arcing Failure, **String 1~16** (EH adds F164 = String 33~48, F165 = String Missing 33~48) |
| `F155` | Online Low Insulation Resistance | Online Low Insulation Resistance (same) |
| `F160`/`F161`/`F162`/`F163` | EMS Forced Off-grid / Passive Anti-islanding / Grid Type Fault / Grid Phase Instability | identical names (consistent) |

### 3. Same underlying condition, three different code shapes
| Condition | F-code | bare integer | named string |
|-----------|--------|--------------|--------------|
| Grid voltage outside range | SDT F02 / EH F02 | ES-US `2` or `4`, UT `13` | `VAC Failure` (ES/SBP/ET/EHB) |
| Grid frequency outside range | SDT F06 / EH F06 | ES-US `6`, UT `3` | `FAC Failure` (ES/SBP/ET/EHB) |
| No grid power | SDT F01 / EH F01 | ES-US `1`, UT `20` | `Utility Loss` (ES/SBP/ET/EHB) |
| Insulation / ISO too low | SDT F18/F155, EH F18/F155 | ES-US `18`, UT `12` | `Isolation Failure` (ES/ET/EHB) |
| Relay self-test failure | EH F32/F64, SDT F114 | ES-US `24`, UT `22` | `Relay Check Failure` (ES/ET/EHB) |
| DC arc / AFCI | SDT F42/F53, EH F42/F53/F164 | ES-US `27`, UT `16` | `AFCI Fault` (EHB) |
| Back-up overload | SDT F55, EH F55 | — | `Back-Up Over Load` (ES/SBP/EHB) |
| Phase sequence reversed | SDT F46, EH F46 | — | `Utility Phase Failure` (ET), app `14ETU`/`27ETU` |
| PV overvoltage | SDT F37, EH F37 | ES-US `31`, UT `33` | `PV Over Voltage` (EHB), `PV/BAT Overvoltage` (ET) |

**Rule:** a user typing "F18" may mean SDT insulation, EH insulation, or the ES-US `18` row. A user typing "18" with no F may mean ES-US Low Insulation Res., the EMEA battery `18` = Low battery charge temperature, or the ES/SBP LED table. The app must ask which model.

### 4. Internal conflicts inside one document
- **SDT-G3 F114 "Relay Failure 2"** ships with an untranslated Chinese fragment in the English PDF ("可能存在虚接或短路现象"), the PDF then paraphrases it in a note. Treated as "possible loose contact or short circuit"; the raw string should not be shown to a technician.
- **EMEA guide code `14`** has both "Auto test Failure" (reason/troubleshooting both `N/A`) and `14ETU` "Phase order Fault". One document, one number, two rows.
- **EMEA guide code `12`** explicitly changes meaning by model: "Internal Fan Failure (Back-Up Over Load for ES)". Not a conflict, but a model-dependent row that must not be flattened.
- **SDT-G3 F40 and F98 are the same fault** with a string-count-dependent code; likewise F96/F97, F99/F100, F146/F147/F148, F137/F138. Aliased in SERIES 1 where applicable.
- **SDT-G3 §7.4.5** re-lists some conditions by name only (no code) that also have a code in §7.4.1–7.4.4 (e.g. generator faults, terminal overtemperature). Prefer the numbered row.

### 5. Not resolvable from the documents
- Whether the ES Uniq / SDT G3 / Lynx A G3 / BAT-S units currently sold in Pakistan use the SDT F-scheme, the EMEA app scheme, or a third one. The G3 generation manuals were not reachable (see NEGATIVE). **Do not assume a G3 unit follows an SDT-G3 or EH table.**
- Whether SEMAR (the Pakistan app) displays the same code strings as SolarGo / PV Master. The PK page only names the app.
- ES-US table row 25: absent from the manual. If a US unit ever shows `25`, the manual gives no answer.

## Research report

**Total codes: 79** across **11 model-scoped series** (target 40–80 — inside range).

| # | Series | Codes | Unit type | Source document |
|---|--------|-------|-----------|-----------------|
| 1 | `goodwe-sdt-g3` — SDT-G3 three-phase hybrid, F01–F163 F-scheme | 18 | hybrid_inverter | GW SDT-G3 User Manual V2.4 §7.4 |
| 2 | `goodwe-eh` — EH / EH Plus, own F-scheme | 10 | hybrid_inverter | GW EH User Manual §9.4 |
| 3 | `goodwe-es-us` — ES-US / SBP-US, bare integers | 7 | hybrid_inverter | GW ES-US, SBP-US User Manual V1.3 §9.4 |
| 4 | `goodwe-ut` — UT utility string, bare integers | 6 | on_grid_inverter | GW UT User Manual 1.6 §9.4 |
| 5 | `goodwe-ess-emea-app` — EMEA 2-digit app codes (EM/ET/BT) | 9 | hybrid_inverter | GW ESS Troubleshooting Guide EMEA §4.3 |
| 6 | `goodwe-ess-battery` — EMEA battery/BMS alarms | 6 | generic | GW ESS Troubleshooting Guide EMEA §5.1 |
| 7 | `goodwe-es-named` — ES Series named messages | 6 | hybrid_inverter | GW ES Series User Manual V1.2 §4.1 |
| 8 | `goodwe-et-named` — ET / ET Plus+ named messages | 4 | hybrid_inverter | GW ET Series User Manual V1.1 §4.1 |
| 9 | `goodwe-ehb-named` — EHB named messages | 6 | hybrid_inverter | GW EHB User Manual §8.1 |
| 10 | `goodwe-led-emea` — ET/ET PLUS+/BT LED legend | 5 | hybrid_inverter | GW ESS Troubleshooting Guide EMEA §03 |
| 11 | `goodwe-led-es-sbp` — ES/SBP G2 LED legend | 2 | hybrid_inverter | GW ES Series User Manual V1.2 §01 |

**Top 5 URLs (all fetched live 2026-09-26, all official GoodWe servers):**
1. `https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_SDT-G3_User-Manual-EN.pdf` — 212 pages, §7.4.1–7.4.4 gave the largest table (F01–F163)
2. `https://nl.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_EH_User%20Manual-EN.pdf` — 144 pages, §9.4, second F-scheme
3. `https://www.goodwe.com.pl/goodwe_download/GW_ESS%20Troubleshooting%20Guide-EMEA_EN.pdf` — 37 pages, three tables used (app codes, battery alarms, LED legend)
4. `https://us.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_ES-US,%20SBP-US_User%20Manual-EN.pdf` — 81 pages, bare-integer 1–35 table
5. `https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_ES_User%20Manual-EN.pdf` — 42 pages, named-message table + ES/SBP LED legend

*(also used: `it.goodwe.com/.../GW_UT_User%20Manual-EN.pdf`, `en.goodwe.com/.../GW_ET_User%20Manual-EN.pdf`, `en.goodwe.com/.../GW_EM_User%20Manual-EN.pdf`, `goodwe.com.au/.../GW_EHB_User%20Manual-EN.pdf`, `en.goodwe.com/Ftp/EN/Downloads/User Manual/GW_SBP_User Manual-EN.pdf`, `en.goodwe.com/pakistan`, `en.goodwe.com/`)*

**Official table: YES.** 12 of 12 sources are first-party GoodWe documents on `*.goodwe.com` / `goodwe.com.au` / `goodwe.com.pl` servers, plus the official country site. Zero aggregators, mirrors or third-party sites were used. Every `excerpt` line was machine-verified as a literal substring of the `pdftotext -layout` extraction of the fetched PDF. All 11 YAML blocks parse cleanly; all 79 entries pass field validation (valid `severity`/`display`/`confidence` enums, ≥2 causes and ≥2 solutions on fault rows, `en` titles ≤60 chars, `ur` ≠ `en` everywhere, no Arabic/Urdu script in any `ur` field).

**Pakistan presence: YES — confirmed official, not just a distributor channel.** `https://en.goodwe.com/pakistan` is a live dedicated GoodWe market portal (HTTP 200, 141 KB) linked from the global country selector on `en.goodwe.com`. It states: warehouse at "Sec D Ext lane 1, Bahria Town Phase 8 Rawalpindi" and "Maraka, Lahore"; "Operates fully equipped service hubs in Karachi and Lahore"; service UAN 03311110217; Urdu/English **SEMAR** app support; "the No. 1 Hybrid Inverter Brand in Pakistan". Lines marketed in-country: **ES Uniq** (8–12 kW single-phase, 2 MPPT), **SDT G3** (8–30 kW three-phase, 2 MPPT), **Lynx A G3** (5 kWh LV battery), **ET Hybrid (HV)** (40/50 kW three-phase), **BAT-S** (25.6–56.3 kWh HV battery), **GT** (100–125 kW), **SMT** (50–60 kW), **MV Station**, **UT** (320/350 kW), **HT** (225/250 kW), **GW225K-HT Ultra**. So SERIES 1 (SDT-G3), 4 (UT), 5/6/10 (ET, ET Plus+, BT) and 11 (ES/SBP) are directly PK-relevant; SERIES 2 (EH), 9 (EHB) and 3 (ES-US/SBP-US) are not listed for PK and should be surfaced only for the right model number. This corrects the earlier recon note, which had guessed a distributor-only channel and looked for a `pk.goodwe.com` host.

**Blockers / next steps:**
1. **G3-generation manuals unreachable.** `Ftp/EN/Downloads/User Manual/` returns HTTP 403 on directory listing, so the ES Uniq / SDT G3 / Lynx A G3 / BAT-S / ET Hybrid (HV) fault tables could not be enumerated. This is the biggest gap: the lines Pakistan actually buys may not use any scheme in this file. **Recommended: use the "Download" links on `en.goodwe.com/pakistan` in a browser session to capture the actual G3 PDF filenames, then re-fetch.**
2. **Battery/BMS codes are thin (6 rows).** GoodWe's battery alarms are numbered 1–17 in the EMEA guide only; there is no GoodWe-native battery code table with names like `BMS Fault 4096`. A dedicated `goodwe-battery-bms` series combining §5.1 with the §05.1 row 16 note about "BMS alarm 4096" would be worthwhile — but the 4096 bit value is only mentioned in passing and must not be expanded into invented bit meanings.
3. **Wi-Fi / SEMAR / Wi-Fi-dongle troubleshooting not modelled.** ~25 verified rows exist (EMEA guide §06, plus Solar-Wi-Fi and SEC1000S procedures) that would suit a separate connectivity series.
4. **SBP named-message table not given its own series.** It is near-identical to SERIES 7 and SERIES 9 and would add 10 more rows; deferred to keep the total under 80.
5. **SDT-G3 §7.4.5 name-only table** (Generator Failure, BMS Status Bit Error, EPO, gas alarm, three-phase on/off-grid faults) is verified but not yet a series.
6. **No aggregator cross-check was performed by design.** If a second source is later wanted, the SEMS Portal and SolarGo app manuals are the natural GoodWe-native cross-reference, not a mirror site.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `goodwe-sdt-g3.json` | SDT-G3 three-phase hybrid (F01-F163 F-code scheme) | 18 |
| `goodwe-eh.json` | EH / EH Plus single-phase hybrid (F01-F163 F-code scheme) | 10 |
| `goodwe-es-us.json` | ES-US / SBP-US North America (bare integer codes 1-35) | 7 |
| `goodwe-ut.json` | UT utility string inverter (bare integer codes 1-39) | 6 |
| `goodwe-ess-emea-app.json` | EMEA ESS app error codes (2-digit codes on SolarGo / PV Master, EM / ET / BT) | 9 |
| `goodwe-ess-battery.json` | EMEA ESS battery / BMS alarm table (bare integers 1-17) | 6 |
| `goodwe-es-named.json` | ES Series named messages (PV Master era, GW3648D-ES / GW5048D-ES) | 6 |
| `goodwe-et-named.json` | ET Series / ET Plus+ named messages (PV Master era) | 4 |
| `goodwe-ehb-named.json` | EHB named messages (battery hybrid GW5000/6500/8600/0010-EHB) | 6 |
| `goodwe-led-emea.json` | EMEA ESS LED indicator legend (ET / ET PLUS+ / BT) | 5 |
| `goodwe-led-es-sbp.json` | ES / SBP Series (3.0-6.0kW) G2 LED legend | 2 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `goodwe-sdt-g3.json` | service_manual | GW SDT-G3 User Manual V2.4 (2026-03-25), section 7.4 Troubleshooting | https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_SDT-G3_User-Manual-EN.pdf | 2026-09-26 |
| `goodwe-eh.json` | service_manual | GW EH User Manual, section 9.4 Inverter Fault | https://nl.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_EH_User%20Manual-EN.pdf | 2026-09-26 |
| `goodwe-es-us.json` | service_manual | GW ES-US, SBP-US User Manual V1.3-2026-04-09, section 9.4 Troubleshooting | https://us.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_ES-US,%20SBP-US_User%20Manual-EN.pdf | 2026-09-26 |
| `goodwe-ut.json` | service_manual | GW UT User Manual 1.6-2026-01-16, section 9.4 Troubleshooting | https://it.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_UT_User%20Manual-EN.pdf | 2026-09-26 |
| `goodwe-ess-emea-app.json` | service_manual | GW ESS Troubleshooting Guide EMEA EN, section 4.3 Troubleshootings for Each Error Message | https://www.goodwe.com.pl/goodwe_download/GW_ESS%20Troubleshooting%20Guide-EMEA_EN.pdf | 2026-09-26 |
| `goodwe-ess-battery.json` | service_manual | GW ESS Troubleshooting Guide EMEA EN, section 5.1 Battery Faults | https://www.goodwe.com.pl/goodwe_download/GW_ESS%20Troubleshooting%20Guide-EMEA_EN.pdf | 2026-09-26 |
| `goodwe-es-named.json` | service_manual | GW ES Series Hybrid Inverter User Manual V1.2, section 4.1 Error Messages | https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_ES_User%20Manual-EN.pdf | 2026-09-26 |
| `goodwe-et-named.json` | service_manual | GW ET Series | ET Plus Series Hybrid Inverter User Manual V1.1, section 4.1 Error Messages | https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_ET_User%20Manual-EN.pdf | 2026-09-26 |
| `goodwe-ehb-named.json` | service_manual | GW EHB User Manual, section 8.1 Error Message | https://www.goodwe.com.au/Ftp/EN/Downloads/User%20Manual/GW_EHB_User%20Manual-EN.pdf | 2026-09-26 |
| `goodwe-led-emea.json` | service_manual | GW ESS Troubleshooting Guide EMEA EN, section 03 Troubleshooting of the System (Via LED indicators on the Inverter) | https://www.goodwe.com.pl/goodwe_download/GW_ESS%20Troubleshooting%20Guide-EMEA_EN.pdf | 2026-09-26 |
| `goodwe-led-es-sbp.json` | service_manual | GW ES Series Hybrid Inverter User Manual V1.2, section 01 Introduction - Indicators | https://en.goodwe.com/Ftp/EN/Downloads/User%20Manual/GW_ES_User%20Manual-EN.pdf | 2026-09-26 |

