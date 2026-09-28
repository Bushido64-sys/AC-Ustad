# Huawei (`huawei`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **115** across **10** model-scoped series

Official: https://www.huawei.com

Scope limited to SUN2000 PV inverters and LUNA2000 battery/ESS products. Huawei maintains a dedicated Pakistan Smart PV site (solar.huawei.com/pk) listing LUNA2000-7/14/21-S1, SUN2000-12/15/17/20/25K-MB0, SUN2000-150K-MG0 and LUNA2000-241/4472/213KTL, plus local distributor and installer channels, so PK presence is confirmed.

## Brand recon

- **Official name:** Huawei Technologies Co., Ltd. (solar business = Huawei Digital Power / FusionSolar / Smart PV)
- **Official website (corporate):** https://www.huawei.com/en
- **Solar business site:** https://solar.huawei.com/en/ (FusionSolar SmartPVMS)
- **Pakistan presence: YES.**
  - Huawei runs a dedicated Pakistan Smart PV site: **https://solar.huawei.com/pk/** — title bar reads "HUAWEI Smart PV Pakistan", with country selector entry "Pakistan / English" linking to `/pk/`, and a `/pk/contact-us/` ("How to Buy") page. Fetched 2026-09-26.
  - PK product line-up listed on that site includes: `LUNA2000-7/14/21-S1` (residential Smart String ESS), `SUN2000-12/15/17/20/25K-MB0` (Smart Energy Controller), `SUN2000-450W-P2/600W-P`, `SmartGuard-63A-S0`, `SUN2000-150K-MG0` (C&I Smart PV Controller), `LUNA2000-241 Series` (C&I Hybrid Cooling GFM ESS), `MERC-1100/1300W-P`, `Smart Dongle - 4G`, `SUN2000-330KTL-H1` (utility Smart PV Controller), `LUNA2000-4472-2S`, `LUNA2000-213KTL-H0` (Smart PCS), and `SUN2000-5/6/8/10/12K-MAP0` (search recommendation on the PK page).
  - PK-specific support/distributor channels exist: https://solar.huawei.com/pk/find-distributor/ , https://solar.huawei.com/pk/find-installer/ , https://community.solar.huawei.com/pk/index.html , https://solar.huawei.com/pk/support/
  - Third-party PK channel evidence (not official, corroborating only): https://greentek.pk/huawei-inverter (Greentek Solutions Pvt Ltd, Gulberg-III, Lahore — lists SUN2000-10-20KTL-M0, SUN2000-60KTL-M0, SUN2000-100KTL-M1), https://buysolar.pk/products/huawei (Johar Town Lahore — sells SUN2000-12KTL-M5 / 20KTL-M5 / 25KTL-M5 / 30KTL-M3 / 50KTL-M3 / 115KTL / 10KTL-M0), https://cellsolgroup.com/huawei-solar ("CG Trade is an authorized distributor of Huawei Solar inverters in Pakistan").
- **Official fault-code table available: YES (multiple).** Huawei does NOT ship fault codes inside the User Manual's main body for the newest string inverters — the manuals say verbatim "For details about alarms, see Inverter Alarm Reference" (a separate document). Older manuals (M0, LUNA2000-S0) DO carry the full table in-manual, and the Huawei Enterprise support portal hosts those same tables as HTML.
  - Separate official doc referenced by SUN2000-(100/110/115KTL)-M2 and SUN2000-(50/75/80/150K)-MG manuals: **"Inverter Alarm Reference"** — exact PDF not resolved from a stable direct URL at time of research (see NEGATIVE).
  - HTML alarm tables confirmed live on support.huawei.com: SUN2000-(3.8KTL-11.4KTL)-USL0 `Table 8-2 Common alarms and troubleshooting measures` → https://support.huawei.com/enterprise/en/doc/EDOC1100031673/447aa2c4/inverter-troubleshooting
  - HTML alarm table for LUNA2000-S1 → https://support.huawei.com/enterprise/en/doc/EDOC1100339927/1bef0e95/troubleshooting
- **Model lines in scope (solar inverter + ESS only):**
  - `SUN2000` single-phase string: `-2/3/3.68/4/4.6/5/6KTL-L1`, `-3/4/5/6KTL-G1`, `-8/10K-LC0`
  - `SUN2000` three-phase string: `-3/4/5/6/8/10KTL-M1`, `-8/10/12/15/17/20KTL-M0`, `-12/15/17/20/25K-MB0`, `-5/6/8/10/12K-MAP0` (hybrid), `-20KTL-M2` / `-50/75/80/100/110/115KTL-M1/M2/M3`
  - `SUN2000` central / smart-string C&I: `-30/40K-MC0`, `-50/75/80/150K-MG0`, `-330KTL-H1`, `SD2000` (smart string controller)
  - `SUN2000` module controller: `-450W-P2/600W-P`, `MERC-1100/1300W-P`
  - `LUNA2000` battery / ESS: `-5/10/15-S0`, `(5-30)-S0`, `-5/7/10/12/14/15/17/19/21-S1`, `-7/14/21-S1`, `-215-2S10/215-2S12`, `-241 Series`, `-4472-2S`, `-213KTL-H0` (Smart PCS)
  - `EMMA` smart energy controller / `SmartGuard-63A-S0`
- **Out of scope / quarantined:** Huawei router (HG/HS/ANE), phone, telecom (iMaster), smart screen, HVAC, air purifier, and automotive (Smart Selection) error codes.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|---------------|-----------|
| 1 | SUN2000-(8KTL, 10KTL, 12KTL, 15KTL, 17KTL, 20KTL)-M0 User Manual, Issue 03 (2019-07-19), §7.2 Table 7-2 "Common alarms and troubleshooting measures" | official service manual (Huawei-hosted) | https://solar.huawei.com/-/media/Solar/attachment/pdf/au/service/commercial/SUN2000-8-20KTL-M0-User_Manual.pdf | 21 numeric alarm IDs 2001–2072 + 61440 with names, severity, causes, suggestions; plus Table 2-2 LED indicator description | 2026-09-26 |
| 2 | Same M0 manual, Table 2-2 "LED indicator description" | official service manual (same PDF as #1) | https://solar.huawei.com/-/media/Solar/attachment/pdf/au/service/commercial/SUN2000-8-20KTL-M0-User_Manual.pdf | LED1/LED2/LED3 state rows (steady/blinking/off → meaning) | 2026-09-26 |
| 3 | LUNA2000-(5-30)-S0 User Manual, Issue 14 (2023-05-31), §8.3 Table 8-2 "Common alarms and troubleshooting measures" | official service manual (Huawei-authored, distributor-hosted PDF) | https://7sun.eu/wp-content/uploads/2025/08/User-manual-LUNA2000-5-E0-EN.pdf | 20 numeric alarm IDs 3000–3061 with names, severity, causes, troubleshooting | 2026-09-26 |
| 4 | LUNA2000-S1 User Manual, Issue 06 (2025-09-30), §7.3 Table 7-2 "Common alarms and troubleshooting measures" | official service manual (Huawei-hosted) | https://solar.huawei.com/admin/asset/v1/pro/view/7fa828aede004fb1b1658929a49d6242.pdf | S1-era alarm IDs 3000+ with revised names, severity, causes, suggestions | 2026-09-26 |
| 5 | LUNA2000-S1 User Manual, §2.x LED indicator description | official service manual (same PDF as #4) | https://solar.huawei.com/admin/asset/v1/pro/view/7fa828aede004fb1b1658929a49d6242.pdf | S1 battery SOC/fault LED rows | 2026-09-26 |
| 6 | "Inverter Troubleshooting — SUN2000-(3.8KTL-11.4KTL)-USL0 User Manual", Table 8-2 "Common alarms and troubleshooting measures" (Huawei Enterprise support portal) | official support portal (HTML) | https://support.huawei.com/enterprise/en/doc/EDOC1100031673/447aa2c4/inverter-troubleshooting | Residential string-inverter alarm IDs 2002/2032/2064/… with Cause ID breakdown | 2026-09-26 |
| 7 | "Troubleshooting — LUNA2000-S1 User Manual", Table 7-2 (Huawei Enterprise support portal) | official support portal (HTML) | https://support.huawei.com/enterprise/en/doc/EDOC1100339927/1bef0e95/troubleshooting | Independent confirmation of LUNA2000-S1 alarm IDs 3000/3001/3002/3004/3005/3006 and names | 2026-09-26 |
| 8 | SUN2000-(100KTL, 110KTL, 115KTL)-M2 User Manual, Issue 13 (2024-01-12), §2.x LED indicator description | official service manual (distributor-hosted Huawei PDF) | https://ske-solar.com/productdata/SUN2000-M2/02_Manuals/User%20Manual_SUN2000-100KTL-115KTL-M2_V13_2024-01-12_EN.pdf | M2-series 4-LED indicator rows (PV / grid / comms / alarm-maintenance) | 2026-09-26 |
| 9 | HUAWEI Smart PV Pakistan — LUNA2000-7/14/21-S1 Specs | official PK product page | https://solar.huawei.com/pk/products/luna2000-7-14-21-s1/specs | PK market presence + PK-listed model line-up | 2026-09-26 |
| 10 | SUN2000-(3.8KTL, 5KTL, 7.6KTL, 9KTL, 10KTL, 11.4KTL)-USL0 User Manual, Issue 02 (2018-08-30), §8.2 Table 8-2 "Common alarms and troubleshooting measures" | official service manual (Huawei-authored, distributor-hosted PDF) | https://shop.frankensolar.ca/content/documentation/Huawei/Manual_Huawei_SUN2000-3.8KTL-11.4KTL-USL0-User-Manual_%28frankensolar%29.pdf | 21 residential alarm IDs 2002–2080 + 61440 with name, severity, action, cause, suggestion | 2026-09-26 |
| 11 | Same USL0 manual, Table 2-2 "LED description" | official service manual (same PDF as #10) | https://shop.frankensolar.ca/content/documentation/Huawei/Manual_Huawei_SUN2000-3.8KTL-11.4KTL-USL0-User-Manual_%28frankensolar%29.pdf | LED1/LED2/LED3 rows incl. orange backup-mode and red-slow optimizer-fault rows | 2026-09-26 |
| 12 | "Inverter Troubleshooting — SUN2000-(3.8KTL-11.4KTL)-USL0 User Manual", Table 8-2 (Huawei Enterprise support portal HTML) | official support portal (HTML, corroboration) | https://support.huawei.com/enterprise/en/doc/EDOC1100031673/447aa2c4/inverter-troubleshooting | Corroborates USL0 alarm IDs 2002/2032/2064 and severities; direct fetch returned HTTP 403 so used as secondary confirmation only | 2026-09-26 |
| 13 | LUNA2000-(107-215) Series Smart String ESS — **Alarm Reference**, Issue 03 (2025-07-22) | official standalone alarm-reference document | https://ske-solar.com/fileadmin/user_upload/AlarmReference_LUNA2000-215-2S10_V03_2025-07-22_EN.pdf | 202 numeric C&I ESS alarm IDs 3100–3913 with Alarm Severity, Alarm Type, Clearance Category (ADAC/ADMC) | 2026-09-26 |
| 14 | SUN2000-(100KTL, 110KTL, 115KTL)-M2 User Manual, Issue 13 (2024-01-12), §2.4.2 "Indicator Status" | official service manual (distributor-hosted Huawei PDF) | https://ske-solar.com/productdata/SUN2000-M2/02_Manuals/User%20Manual_SUN2000-100KTL-115KTL-M2_V13_2024-01-12_EN.pdf | 4-indicator status table (PV connection / grid connection / comms / alarm-maintenance) incl. steady-red, blink-fast, blink-slow rows | 2026-09-26 |
| 15 | LUNA2000-S1 User Manual, Issue 06 (2025-09-30), Table 6-4 "System running indication" | official service manual (Huawei-hosted) | https://solar.huawei.com/admin/asset/v1/pro/view/7fa828aede004fb1b1658929a49d6242.pdf | S1 running/fault LED rows (red short = env alarm, steady red = module fault) | 2026-09-26 |
| 16 | LUNA2000-(5-30)-S0 User Manual, Issue 14 (2023-05-31), Table 7-2 "LED indicators" | official service manual (Huawei-authored, distributor-hosted PDF) | https://7sun.eu/wp-content/uploads/2025/08/User-manual-LUNA2000-5-E0-EN.pdf | S0 running/battery-system LED rows | 2026-09-26 |
| 17 | SUN2000-(50K, 75K, 80K, 150K)-MG Series User Manual, Issue 12 (2025-06-10) | official service manual (distributor-hosted Huawei PDF) | https://ske-solar.com/fileadmin/user_upload/UserManual_SUN2000-150K-MG0_V12_2025-06-10_EN.pdf | Read to confirm MG has NO in-manual alarm table (§9 Alarm Reference → "see Inverter Alarm Reference"); Smart Dongle LED table quarantined | 2026-09-26 |
| 18 | SUN2000-(30K, 40K)-MC0 User Manual, Issue 01 (2025-08-30), §9 Alarm Reference | official service manual (Huawei-hosted) | https://solar.huawei.com/admin/asset/v1/pro/view/1baf3d199e094a528adc448d38c8cf68.pdf | Read to confirm MC0 has NO in-manual alarm table (§9 → "see Inverter Alarm Reference") | 2026-09-26 |

## Negative results

Things a Huawei solar technician would reasonably expect to find, which I could **NOT** verify from a URL fetched on 2026-09-26. Nothing below is guessed, extrapolated, or reconstructed from another brand. These are the gaps in this research pass.

1. **"Inverter Alarm Reference" (standalone, SUN2000 string inverters) — the single most important missing document.**
   The SUN2000-(100/110/115KTL)-M2, SUN2000-(50/75/80/150K)-MG, SUN2000-(30/40K)-MC0 and SUN2000-(250KTL/280KTL/300KTL/330KTL) manuals all contain the identical sentence, verbatim:
   - M2 Issue 13, §8.4: `For details about alarms, see the Inverter Alarm Reference.`
   - MG Issue 12, §9: `For details about alarms, see Inverter Alarm Reference.`
   - MC0 Issue 01, §9: `For details about alarms, see Inverter Alarm Reference.`
   This document holds the numeric alarm tables for all current C&I/utility SUN2000 platforms. I could not resolve a stable direct PDF URL for it; the Huawei Enterprise support portal has an "Alarm Reference" topic at `https://support.huawei.com/enterprise/en/doc/EDOC1100248746/f3cd806d/alarm-reference` (titled for SUN2000-(75KTL-M1, 100KTL-M2, 110KTL-M2, 115KTL-M2)) but a direct fetch of a support.huawei.com doc page returned **HTTP 403** from this environment, so I could not read its contents. **Do not synthesise 2001/2002/2032/... codes for MG/MC0/H1 from the M0 or USL0 tables** — that would be extrapolation, which is exactly what the brief forbids.

2. **New-format fault code strings — NOT FOUND for SUN2000.**
   The brief's example `"ACE.0-0x0123"` style string did not appear in any Huawei solar PDF I fetched. Huawei SUN2000/LUNA2000 documentation uses **plain decimal numeric alarm IDs** (2001-2072, 3000-3071, 3100-3913) plus named LED states. The only internal 5-digit ID I saw in a table body was `61440` (Monitoring Unit Faulty). If the AC Ustad KB expects `ACE.0-0x…` strings for Huawei, they come from a different Huawei product family (SmartLogger / EMMA / iMaster / FusionSolar app internals) that I did not document. **No such codes are recorded here.**

3. **No `## BRAND RECON`-level confirmation of a Huawei *Pakistan-specific* grid code / fault list.** solar.huawei.com/pk exists and is fully localised, but I did not locate a PK-only fault-code annexe. The M0/MG manuals carry an "A Grid Codes" appendix (grid-code parameters, not fault codes) which I did not extract. Whether a Pakistan grid code changes any alarm *threshold* is therefore **unverified**.

4. **LUNA2000-215-2S10 / 4472-2S grid-forming ESS (the model Huawei lists for PK utility scale) — numeric table not extracted.**
   `https://ske-solar.com/fileadmin/user_upload/LUNA2000-_2236__5015__User_Manual_20260415_V12_EN.pdf` (Issue 12, 2026-04-15) has an "A Alarm Reference" appendix whose body reads `For details about alarms, see LUNA2000-(2236, 5015) Series Smart String Grid Forming ESS Alarm Reference` — i.e. yet another separate document. I did not fetch it. The manual *is* the PK-relevant utility product, so this is a real gap.

5. **LUNA2000-213KTL-H0 Smart PCS alarm table — not documented.** Its manual has an `8.3 Alarm Reference` section (https://ske-solar.com/fileadmin/user_upload/LUNA2000-213KTL-H0_User_Manual_V04_20241230_EN.pdf) and I did read its LED table (Table 2-2) via search excerpts, but I did not download and extract its numeric alarm section, so no codes are claimed for it. This is a PK-listed product (solar.huawei.com/pk lists it under Utility Scale).

6. **LUNA2000-241 Series (PK-listed C&I hybrid cooling GFM ESS) — not documented at all.** Listed on solar.huawei.com/pk under Commercial & Industrial. No table fetched.

7. **EMMA / SmartGuard-63A-S0 (PK-listed) — not documented.** `EMMA` was named in scope. SmartGuard-63A-S0 appears on the PK site as a "Smart Guard" accessory, but I fetched **no** EMMA or SmartGuard fault table, so **zero EMMA codes are recorded here**. A Pakistani installer seeing an EMMA alarm gets nothing from this file.

8. **SUN2000-600W-PA0 / 450W-P2 / MERC-1100/1300W-P optimizer alarms — not documented.** The Huawei portal has a "SUN2000 Smart PV Optimizer User Manual" alarm list (`https://support.huawei.com/enterprise/en/doc/EDOC1100222020/4d371b3b/alarm-list`, snippet showed "Optimizer input overvoltage occurred", "Input overvoltage", "Internal…") but the page returned 403 on direct fetch and the excerpt is truncated mid-table. I only referenced optimizer faults indirectly inside SERIES 4 LED rows, quoted from the USL0 **LED** table, not from the optimizer alarm table. Optimizer-specific alarm IDs: **not recorded**.

9. **No alarm table for SUN2000-3/4/5/6/8/10KTL-M1** (a PK-relevant model per solar.huawei.com/pk's LUNA2000 compatibility list). Its manual (https://ske-solar.com/fileadmin/user_upload/SUN2000-_3KTL-10KTL_-M1_User_Manual_2025-07-25.pdf, Issue 26) contains only the pointer "For details about alarms, see the Inverter Alarm Reference" — same missing document as item 1. I read its LED table only via search excerpts, not extracted, so no M1 LED rows are recorded.

10. **SD2000 smart string controller — no fault table found.** Searched specifically ("Huawei SD2000 smart string controller fault alarm code list user manual"). What came back were SmartLogger2000 alarm lists (1103 MCB Disconnect, 1104 Abnormal Cubicle, 1105 Device Address Conflict, 1106 AC SPD fault), SmartPID2000 (1903 Module overtemp) and SmartACU2000B material. **SD2000 codes: not found, none recorded.** SmartLogger/SmartPID codes are a different product line and are QUARANTINED.

11. **LUNA2000-(215-2S10, 215-2S12) Smart String ESS User Manual LED / SOC ring detail** — the manual exists at https://solar.huawei.com/admin/asset/v1/pro/view/8459cfc0f20c4ecdbfcc5cbc93c0829d.pdf and its search excerpts show SOC-indicator and fire-alarm-indicator LED rows, but I did not download it. Not recorded.

12. **Severity cross-verification for SERIES 9 codes 3105, 3161, 3308, 3401, 3408, 3503, 3505, 3516, 3517, 3600, 3675, 3900 rests on a single fetched document** (the LUNA2000-(107-215) Alarm Reference PDF). These are `confidence: high` because the document is an official Huawei alarm reference, but note the *product class* (215 kWh containerised C&I ESS) is far from the Pakistani residential market — treat applicability carefully. Several of these (3401 DCDC Faulty, 3505 PCS Grid Undervoltage, 3517 PCS Temperature High) are *not* marked `low` because the source itself is authoritative, but a second independent source was not obtained.

13. **Ambiguous Cause-ID/Reason-ID semantics in SERIES 9.** The Alarm Reference has *Reason ID* and a nested *No.* per reason; my `causes:` lists paraphrase Reason ID 1 (and 1/2/5 where the manual distinguishes them, e.g. 3675). For 3600 and 3900 only Reason ID 1 was read from the extracted text. This is stated rather than hidden.

## Quarantine (excluded)

Out-of-scope Huawei code families found or encountered during research and **deliberately excluded** from the series above.

| Family | Example codes seen | Why quarantined | Source encountered |
|---|---|---|---|
| **HVAC / liquid-cooling sub-plant of the C&I ESS** | 3601 Power voltage abnormal, 3602 Power frequency abnormal, 3603 Outdoor temperature sensor fault, 3604 Outdoor low temperature alarm, 3605 LTMS Communication Abnormal, 3606 LTMS expiration alarm, 3608/3609 Certificate about to expire/expired, **3620-3628** (Compressor discharge/suction pressure & temperature sensor faults, Condenser outlet sensors, Dehumidifying temperature sensor), **3640-3646** (Compressor drive alarm/output/overcurrent/communication, High discharge temperature, discharge temperature sensor, Insufficient Refrigerant), 3650 Insufficient Cooling Capacity, 3651 LCC Output Overcurrent, 3652/3653 version mismatch, 3655 Auxiliary power abnormal, 3660 Outdoor cooling module blocked, 3661 Outdoor heat exchanger temperature sensor fault, 3665/3666 Fan fault, 3676 Electric heater power overvoltage, **3680-3690** (Power-side / Battery-side supply/return water temperature sensor faults, coolant expiration, shutdown due to coolant expiration, coolant replacement not completed), **3705-3707** (Water pump power supply/function/fault), **3715-3717** (Multi-way valve communication/power supply/faulty), 3725 Water tank low liquid level | Refrigeration/HVAC plant, not the PV inverter or the battery BMS. A Pakistani solar technician will never see these — they belong to a liquid-chilled 215 kWh container. | LUNA2000-(107-215) Alarm Reference, §2.7 Temperature Control System (https://ske-solar.com/fileadmin/user_upload/AlarmReference_LUNA2000-215-2S10_V03_2025-07-22_EN.pdf) |
| **Huawei routers / gateways / telecom (non-solar)** | Smart Dongle-4G and WLAN-module parameter states, "communication with router fails", "router access parameters not set" | The Huawei Smart Dongle is a comms accessory, and its LED table (MG Issue 12, Table 7-1/7-2) is about router/SIM dial-up parameters, not the inverter. Out of the `inverter` + `energy-storage` category. | SUN2000-(50K, 75K, 80K, 150K)-MG User Manual, Tables 7-1/7-2 (https://ske-solar.com/fileadmin/user_upload/UserManual_SUN2000-150K-MG0_V12_2025-06-10_EN.pdf) |
| **SmartLogger2000 (data collector / plant controller)** | 1103 MCB Disconnect, 1104 Abnormal Cubicle, 1105 Device Address Conflict, 1106 AC SPD fault | SmartLogger is a separate product line, not a SUN2000 or LUNA2000. Its alarm IDs share a numeric style with the inverter tables and will collide dangerously in a merged KB. | https://support.huawei.com/enterprise/en/doc/EDOC1100014379/2358e31b/alarm-list (via search excerpt; page 403s on direct fetch) |
| **SmartPID2000 / SmartACU2000B (PID module + smart array controller)** | 1903 Module overtemp | PID array controller, a different product line. | https://solar.huawei.com/~/media/Solar/attachment/pdf/eu/service/download/SmartPID2000%20User%20Manual.pdf (via search excerpt) |
| **Huawei phone / iMaster / smart-screen / air-purifier / automotive** | — | Explicitly out of scope per brief. Not researched. | — |
| **Third-party aggregator "Huawei fault code" pages** | pswenergy.com.au, solar-tech-support.co.uk, manualslib.com, green-technology/7sun/ske-solar **retailer blogs** | Not Huawei documentation. Where a distributor hosts an unmodified Huawei-authored PDF (7sun.eu, ske-solar.com, autosolar, frankensolar) I accepted it as the manual, since the PDF is verbatim Huawei content with Huawei issue numbers and copyright. Retailer *narrative* pages were not used for any code. | pswenergy.com.au/huawei-inverter-error-codes, solar-tech-support.co.uk/fault-codes/huawei, manualslib.com/manual/3965104/Huawei-Luna2000-Series.html |

## Conflicts / caveats

1. **Alarm `2063` Overtemperature — severity differs by manual.**
   - SUN2000-(8/10/12/15/17/20KTL)-**M0** manual, Table 7-2: `2063  Overtemperature  Minor`.
   - SUN2000-(3.8KTL-11.4KTL)-**USL0** manual, Table 8-2: `2063  Overtemperature  Major ... Shutdown, alarm reporting, and automatic recovery after the fault is rectified`.
   Same code, same apparent meaning, opposite severity class and opposite production impact. Not merged. Both preserved in SERIES 1 and SERIES 3.

2. **Alarm `2065` — severity differs by manual.**
   - M0: `2065  Upgrade Failed or Software Version Unmatch  Minor` (Cause ID = 1, 2 and 4).
   - USL0: `2065  Upgrade Failed  Major` (Cause ID = 1-4), action `Shutdown, alarm reporting (reset alarm)`.
   Not merged. Both preserved in SERIES 1 and SERIES 3.

3. **Alarm `2002` DC Arc Fault — Cause ID semantics differ.**
   - M0: `Cause ID 1 = PV1 and PV2`, `Cause ID 2 = PV3 and PV4` — i.e. Cause ID maps to **MPPT pairs**.
   - USL0: `Cause ID 1: string 1`, `Cause ID 2: string 2` — i.e. Cause ID maps to **individual strings**.
   A technician reading "Cause ID 1" would check a different circuit depending on which manual the unit shipped with. Flagged in both series' `notes`.

4. **Alarm `2002` / `2032` / `2064` / `2067` — same code, different manual wording and different suggested action text.** M0 and USL0 both carry these four IDs but with materially different `Possible Cause` and `Troubleshooting Suggestion` columns. They are recorded twice, once per series, rather than being reconciled.

5. **Alarm `2077` Output Overload has no M0 equivalent.** Present in USL0 (Cause ID 1-2, needs manual clearing). M0's table has `2039 Output Overcurrent` and `2040 Output DC Component Overhigh` but no `2077`. Not back-filled from USL0 into the M0 series.

6. **Alarm `2021` AFCI Self-Check Failure, `2068` Battery Abnormal and `2080` Abnormal PV Module Configuration exist only in USL0.** They are absent from the M0 table. Not assumed to apply to M0 hardware.

7. **LUNA2000 `3047` Battery Pack Undervoltage exists in S0 but is ABSENT from the S1 table.** Conversely `3071` Battery Expansion Module Locked is new in S1 and absent from S0. I did not carry `3047` into SERIES 7 or `3071` into SERIES 5.

8. **LUNA2000 `3007` / `3010` / `3011` — cause lists differ between S0 and S1.** S0's `3006 Abnormal battery expansion module` cause text is "A major fault has occurred on the internal circuit of the battery expansion module" plus a cable-check suggestion; S1's `3007`/`3010` add "The battery expansion module is abnormal" as an explicit second cause. Same numeric ID family, different generation semantics. Recorded separately.

9. **LUNA2000 `3049` / `3050` placeholder text differs.** S0 says `[Battery-CabinetNo]`; S1 says `[Battery-1/2/3/4]`. Cosmetic, but noted so the two series' paraphrases are not read as a single table.

10. **Alarm ID space COLLISION between the residential ESS tables and the C&I Alarm Reference — the most dangerous conflict in this file.**
   - `3000`-`3066` in SERIES 5 (LUNA2000-S0) and SERIES 7 (LUNA2000-S1) = **ESS module** alarms (low bus voltage, fuse blown, reverse connection, certificate, EN signal…).
   - `3100`-`3913` in SERIES 9 (LUNA2000-(107-215) Smart String ESS Alarm Reference) = **plant subsystem** alarms (battery pack, balancing module, BMU, RPCB, DCDC, PCS, LTMS, ESU). Note `3100` and `3101` here are *unrelated* to the residential `3000`/`3001`, and the C&I doc's own battery-pack lifecycle codes (`3101` Lifespan Reached) sit numerically adjacent to residential codes meaning something totally different.
   The C&I document explicitly states: *"Alarm ID — Indicates the ID of an alarm. **Unique identifier of an alarm in one product.**"* That qualifier is the whole point: these ID spaces are **per-product**, not global. A merged KB that flattens them will produce wrong answers. Do not merge.

11. **Severity vocabulary is inconsistent across Huawei's own solar documents** — at least three schemes in the documents fetched on this date:
   - M0 / LUNA2000-S0 / LUNA2000-S1 / USL0: `Major` / `Minor` / `Warning`, where *Major* sometimes means "output power decreases" and sometimes means "shuts down". M0's own definition: *"Major: The inverter is faulty. As a result, the output power decreases or the grid-tied power generation is stopped."*
   - USL0 additionally defines an `Alarm Action` column (`Shutdown, alarm reporting (reset alarm)` vs `automatic recovery after the fault is rectified`) which is the real discriminator and which M0 lacks entirely.
   - LUNA2000-(107-215) Alarm Reference adds `Alarm Type` (Equipment / Environmental / Communication alarm) and `Clearance Category` (`ADAC` = auto-clear, `ADMC` = manual clear).
   My `severity:` values are therefore an interpretation, not a quote. Every entry that relies on it says so in `notes`.

12. **`2067` Faulty Power Collector is described differently in the two manuals.** M0 says only "The power meter communication is interrupted"; USL0 says the interruption happens "In Grid Connection with Zero Power mode" or "In Power-limited Grid Connection Power mode". Different trigger conditions for the same ID. Recorded separately.

## Research report

**Total entries: 115. Fault codes: 100. Non-fault status rows: 15. Series: 10.**

| # | Series | Model / table | Entries | Faults |
|---|---|---|---|---|
| 1 | `huawei-sun2000-m0-3ph-string` | SUN2000-(8/10/12/15/17/20KTL)-M0 · Table 7-2 numbered alarms | 24 | 24 |
| 2 | `huawei-sun2000-m0-led` | SUN2000-M0 · Table 2-2 LED indicator | 6 | 3 |
| 3 | `huawei-sun2000-usl0-res` | SUN2000-(3.8KTL-11.4KTL)-USL0 · Table 8-2 numbered alarms (subset) | 10 | 10 |
| 4 | `huawei-sun2000-usl0-led` | SUN2000-USL0 · Table 2-2 LED description | 7 | 4 |
| 5 | `huawei-luna2000-s0-alarm` | LUNA2000-(5-30)-S0 · Table 8-2 numbered alarms | 14 | 14 |
| 6 | `huawei-luna2000-s0-led` | LUNA2000-(5-30)-S0 · Table 7-2 LED indicators | 8 | 5 |
| 7 | `huawei-luna2000-s1-alarm` | LUNA2000-S1 · Table 7-2 numbered alarms | 20 | 20 |
| 8 | `huawei-luna2000-s1-led` | LUNA2000-S1 · Tables 6-3/6-4 indicators | 8 | 4 |
| 9 | `huawei-luna2000-107-215-smartstring-alarmref` | LUNA2000-(107-215) Smart String ESS · standalone Alarm Reference (subset) | 9 | 9 |
| 10 | `huawei-sun2000-m2-mg-panel-led` | SUN2000-(100/110/115KTL)-M2 & MG · 4-LED panel indicator status | 9 | 7 |
| | | **TOTAL** | **115** | **100** |

**On the 40-80 target: overshot at 100 fault codes.** Reasons, stated rather than hidden:
- Huawei is the outlier case the brief anticipated. It is the only brand so far that ships **ten distinct documented tables**, and the brief's own rule ("one series per documented table/model line — never merge") forbids collapsing them. Four of the ten tables are LED/status tables, and the two LUNA2000 generations each have *two* tables of their own.
- `LUNA2000-S1` is the generation Huawei lists for Pakistan, so SERIES 7 + 8 (28 entries) are the highest-value rows in the file and were not trimmed.
- I preferred carrying a verified row over padding to a round number. Nothing was invented to reach a count, and nothing was merged across tables to save space.
- If a hard 80 is required, the cheapest cut is SERIES 9 (9 C&I container-ESS codes that will never be seen on a Pakistani rooftop) plus 8 of the SERIES 1 grid-frequency family (2036/2037/2038), which are near-identical in handling. That is a product decision, not a data-quality one, so it was left to you.

**Top 5 URLs (by entries contributed):**
1. `https://solar.huawei.com/-/media/Solar/attachment/pdf/au/service/commercial/SUN2000-8-20KTL-M0-User_Manual.pdf` — SERIES 1 + 2 = 30 entries (Huawei-hosted, AU region, Issue 03 2019-07-19)
2. `https://solar.huawei.com/admin/asset/v1/pro/view/7fa828aede004fb1b1658929a49d6242.pdf` — SERIES 7 + 8 = 28 entries (Huawei-hosted LUNA2000-S1, Issue 06 2025-09-30)
3. `https://shop.frankensolar.ca/content/documentation/Huawei/Manual_Huawei_SUN2000-3.8KTL-11.4KTL-USL0-User-Manual_%28frankensolar%29.pdf` — SERIES 3 + 4 = 17 entries
4. `https://7sun.eu/wp-content/uploads/2025/08/User-manual-LUNA2000-5-E0-EN.pdf` — SERIES 5 + 6 = 22 entries
5. `https://ske-solar.com/fileadmin/user_upload/AlarmReference_LUNA2000-215-2S10_V03_2025-07-22_EN.pdf` — SERIES 9 = 9 entries, and the **highest-value single document per byte**: a true standalone numbered Alarm Reference with 202 codes available, versus 24+20+14+10 = 68 codes across four entire user manuals.

Also used: `https://ske-solar.com/productdata/SUN2000-M2/02_Manuals/User%20Manual_SUN2000-100KTL-115KTL-M2_V13_2024-01-12_EN.pdf` (SERIES 10, 9 entries) and `https://solar.huawei.com/pk/products/luna2000-7-14-21-s1/specs` (PK presence).

**Official fault-code table available: YES — but fragmented across at least five separate documents.**
- Confirmed, fetched, official, numeric, in-manual tables: **SUN2000-M0 (Table 7-2)**, **SUN2000-USL0 (Table 8-2)**, **LUNA2000-S0 (Table 8-2)**, **LUNA2000-S1 (Table 7-2)**.
- Confirmed, fetched, official, numeric, **standalone** Alarm Reference: **LUNA2000-(107-215) Series Smart String ESS Alarm Reference** (202 codes, `ADAC`/`ADMC` clearance categories) — the only true "alarm reference" document found for any Huawei solar product.
- Not available: one consolidated SUN2000 alarm list. Every current C&I/utility SUN2000 manual (M2, MG, MC0, H1) contains the identical pointer *"For details about alarms, see Inverter Alarm Reference"* and carries **no** numeric table of its own. That referenced document's URL could not be resolved — see NEGATIVE 1.

**PK presence: YES.**
- Huawei runs a dedicated Pakistan Smart PV site, **https://solar.huawei.com/pk/** (fetched 2026-09-26). Page title "HUAWEI Smart PV Pakistan", country selector entry "Pakistan / English" -> `/pk/`, plus `/pk/contact-us/` ("How to Buy"), `/pk/find-distributor/`, `/pk/find-installer/`, `https://community.solar.huawei.com/pk/index.html`, `/pk/support/`.
- PK-listed products that map to this file: **`LUNA2000-7/14/21-S1`** (= SERIES 7 + 8, the PK-relevant battery table), `SUN2000-12/15/17/20/25K-MB0`, `SUN2000-450W-P2/600W-P`, `SmartGuard-63A-S0`, **`SUN2000-150K-MG0`** (= SERIES 10 panel LEDs), `LUNA2000-241 Series`, `MERC-1100/1300W-P`, `Smart Dongle-4G`, `SUN2000-330KTL-H1`, `LUNA2000-4472-2S`, `LUNA2000-213KTL-H0`; plus `SUN2000-5/6/8/10/12K-MAP0` in the PK page's search-recommendation list. The PK LUNA2000-7/14/21-S1 spec page also lists compatible inverters `SUN2000-2/3/3.68/4/4.6/5/6KTL-L1`, `SUN2000-8/10K-LC0`, `SUN2000-3/4/5/6/8/10KTL-M1`, `SUN2000-12/15/17/20/25K-MB0` — none of which have a verified table in this file except via SERIES 1/3 (M0/USL0) which are close relatives, NOT the listed models.
- Independent (non-official) channel corroboration: greentek.pk — Greentek Solutions Pvt Ltd, Gulberg-III Lahore (lists SUN2000-10-20KTL-M0, SUN2000-60KTL-M0, SUN2000-100KTL-M1); buysolar.pk — Johar Town Lahore (sells SUN2000-10KTL-M0, 12/20/25KTL-M5, 30/50KTL-M3, 115KTL, with PKR pricing); cellsolgroup.com ("CG Trade is an authorized distributor of Huawei Solar inverters in Pakistan").
- **Caveat:** the PK site is sales/localisation only. **No** Pakistan-specific *fault-code* annexe was found, and there is no confirmation that the Pakistan grid code changes any alarm threshold. Grid-code thresholds are in each manual's "A Grid Codes" appendix, which was not extracted.

**Blockers:**
1. **`support.huawei.com` returns HTTP 403 to direct fetch from this environment.** Every Enterprise-portal doc page (USL0 troubleshooting, LUNA2000-S1 troubleshooting, SmartLogger2000 alarm list, the M2 `Alarm Reference` page, the optimizer alarm list) was readable only through search-tool excerpts. Where a full table was needed I downloaded the underlying Huawei-authored PDF instead. This directly blocks NEGATIVE 1 and NEGATIVE 8.
2. **The SUN2000 "Inverter Alarm Reference" has no resolvable stable URL — highest-value missing artefact.** It is the numeric fault table for every current C&I/utility SUN2000 (M2, MG, MC0, H1) and for M1/M3. Without it, Pakistani C&I sites running SUN2000-150K-MG0 or SUN2000-330KTL-H1 get only LED indications (SERIES 10), never verified numeric codes. **Do not synthesise these from the M0/USL0 tables.**
3. **Zero coverage for two PK-listed products: `EMMA` (explicitly named in scope) and `LUNA2000-241 Series`.** No table was fetched for either. A Pakistani residential job on an EMMA-based system gets nothing from this file.
4. **`SD2000` smart string controller: no fault table found.** Searched specifically. Only SmartLogger2000 (1103 MCB Disconnect, 1104 Abnormal Cubicle, 1105 Device Address Conflict, 1106 AC SPD fault) and SmartPID2000 (1903 Module overtemp) surfaced — both QUARANTINED as different product lines. PK utility-scale sites running SD2000 are uncovered.
5. **PK-listed utility grid-forming ESS uncovered.** `LUNA2000-(2236, 5015)` / `4472-2S` — the models Huawei lists for PK utility scale — have an "A Alarm Reference" appendix that itself just points to yet another separate Alarm Reference document (Issue 12, 2026-04-15 manual was seen; its Alarm Reference was not fetched).
6. **`LUNA2000-213KTL-H0` Smart PCS alarm table not extracted.** PK-listed under Utility Scale; manual read only for its LED table via excerpts.
7. **PDF column extraction is lossy for the Alarm Reference format.** The LUNA2000-(107-215) Alarm Reference uses a 10-column Attribute table plus a nested Reason-ID/No. table. `pdftotext -layout` recovered Alarm ID, Name, Severity and Clearance Category reliably, but only partially recovered the nested Possible Cause text for a few codes. Where a cause line was ambiguous I kept only the Reason ID 1 text I could read cleanly and said so in NEGATIVE 13 — rather than reconstructing Reason IDs 2/3 from a pattern.

**Data-integrity statement:** every code in this file came from one of the 18 rows in SOURCE TABLE, fetched on 2026-09-26. No code was recalled from memory, extrapolated from another brand, or inferred from a similar-looking table. Code strings are reproduced exactly as the source prints them (e.g. `"61440"`, `"LED1 Steady red + LED2 Steady red"`, `"3100"`). Severities are a *documented interpretation* — Huawei uses at least three inconsistent severity vocabularies across these documents (see CONFLICTS 11) — and every series says so in its `notes`. All Roman Urdu is Latin-script technician speech with English technical nouns retained; no `ur` value duplicates its `en`.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `huawei-sun2000-m0-3ph-string.json` | SUN2000-(8KTL, 10KTL, 12KTL, 15KTL, 17KTL, 20KTL)-M0 (three-phase string inverter, numbered alarm table) | 24 |
| `huawei-sun2000-m0-led.json` | SUN2000-(8KTL...20KTL)-M0 LED1/LED2/LED3 indicator states (Table 2-2 LED indicator description) | 6 |
| `huawei-sun2000-usl0-res.json` | SUN2000-(3.8KTL, 5KTL, 7.6KTL, 9KTL, 10KTL, 11.4KTL)-USL0 (US residential string inverter, numbered alarm table) | 10 |
| `huawei-sun2000-usl0-led.json` | SUN2000-USL0 LED1/LED2/LED3 indicator states (Table 2-2 LED description) | 7 |
| `huawei-luna2000-s0-alarm.json` | LUNA2000-(5-30)-S0 battery / Smart String ESS (numbered alarm table, LUNA2000-5/10/15-S0 generation) | 15 |
| `huawei-luna2000-s0-led.json` | LUNA2000-(5-30)-S0 running + battery system LED indicators (Table 7-2 LED indicators) | 8 |
| `huawei-luna2000-s1-alarm.json` | LUNA2000-S1 ESS (LUNA2000-5/7/10/12/14/15/17/19/21-S1, LUNA2000-7/14/21-S1) numbered alarm table | 19 |
| `huawei-luna2000-s1-led.json` | LUNA2000-S1 system running indication and ESS indication (Tables 6-3 first power-on, 6-4 system running) | 8 |
| `huawei-luna2000-107-215-smartstring-alarmref.json` | LUNA2000-(107-215) Series Smart String ESS (LUNA2000-215-2S10 / 2S11 / 2S12, 161-2S11, 107-1S11) standalone Alarm Reference | 9 |
| `huawei-sun2000-m2-mg-panel-led.json` | SUN2000-(100KTL, 110KTL, 115KTL)-M2 / (50K, 75K, 80K, 150K)-MG panel indicator status (PV / grid / comms / alarm-maintenance) | 9 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `huawei-sun2000-m0-3ph-string.json` | service_manual | SUN2000-(8KTL, 10KTL, 12KTL, 15KTL, 17KTL, 20KTL)-M0 User Manual, Issue 03 (2019-07-19), Section 7.2 Table 7-2 | https://solar.huawei.com/-/media/Solar/attachment/pdf/au/service/commercial/SUN2000-8-20KTL-M0-User_Manual.pdf | 2026-09-26 |
| `huawei-sun2000-m0-led.json` | service_manual | SUN2000-(8KTL, 10KTL, 12KTL, 15KTL, 17KTL, 20KTL)-M0 User Manual, Issue 03 (2019-07-19), Table 2-2 LED indicator description | https://solar.huawei.com/-/media/Solar/attachment/pdf/au/service/commercial/SUN2000-8-20KTL-M0-User_Manual.pdf | 2026-09-26 |
| `huawei-sun2000-usl0-res.json` | service_manual | SUN2000-(3.8KTL, 5KTL, 7.6KTL, 9KTL, 10KTL, 11.4KTL)-USL0 User Manual, Issue 02 (2018-08-30), Section 8.2 Table 8-2 | https://shop.frankensolar.ca/content/documentation/Huawei/Manual_Huawei_SUN2000-3.8KTL-11.4KTL-USL0-User-Manual_%28frankensolar%29.pdf | 2026-09-26 |
| `huawei-sun2000-usl0-led.json` | service_manual | SUN2000-(3.8KTL, 5KTL, 7.6KTL, 9KTL, 10KTL, 11.4KTL)-USL0 User Manual, Issue 02 (2018-08-30), Table 2-2 LED description | https://shop.frankensolar.ca/content/documentation/Huawei/Manual_Huawei_SUN2000-3.8KTL-11.4KTL-USL0-User-Manual_%28frankensolar%29.pdf | 2026-09-26 |
| `huawei-luna2000-s0-alarm.json` | service_manual | LUNA2000-(5-30)-S0 User Manual, Issue 14 (2023-05-31), Section 8.3 Table 8-2 | https://7sun.eu/wp-content/uploads/2025/08/User-manual-LUNA2000-5-E0-EN.pdf | 2026-09-26 |
| `huawei-luna2000-s0-led.json` | service_manual | LUNA2000-(5-30)-S0 User Manual, Issue 14 (2023-05-31), Table 7-2 LED indicators | https://7sun.eu/wp-content/uploads/2025/08/User-manual-LUNA2000-5-E0-EN.pdf | 2026-09-26 |
| `huawei-luna2000-s1-alarm.json` | service_manual | LUNA2000-S1 User Manual, Issue 06 (2025-09-30), Section 7.3 Table 7-2 | https://solar.huawei.com/admin/asset/v1/pro/view/7fa828aede004fb1b1658929a49d6242.pdf | 2026-09-26 |
| `huawei-luna2000-s1-led.json` | service_manual | LUNA2000-S1 User Manual, Issue 06 (2025-09-30), Tables 6-3 and 6-4 indicator definitions | https://solar.huawei.com/admin/asset/v1/pro/view/7fa828aede004fb1b1658929a49d6242.pdf | 2026-09-26 |
| `huawei-luna2000-107-215-smartstring-alarmref.json` | official_support | LUNA2000-(107-215) Series Smart String ESS Alarm Reference, Issue 03 (2025-07-22) | https://ske-solar.com/fileadmin/user_upload/AlarmReference_LUNA2000-215-2S10_V03_2025-07-22_EN.pdf | 2026-09-26 |
| `huawei-sun2000-m2-mg-panel-led.json` | service_manual | SUN2000-(100KTL, 110KTL, 115KTL)-M2 User Manual, Issue 13 (2024-01-12), Section 2.4.2 Indicator Status | https://ske-solar.com/productdata/SUN2000-M2/02_Manuals/User%20Manual_SUN2000-100KTL-115KTL-M2_V13_2024-01-12_EN.pdf | 2026-09-26 |

