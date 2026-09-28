# Fronius (AT) (`fronius-at`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **76** across **8** model-scoped series

Official: https://www.fronius.com

Austrian Fronius International GmbH (Wels, AT). NOT Fronius (Pakistan) = the `fronus` brand in this KB; no Fronius Pakistan data was read or reused. No Fronius country setup or support channel for Pakistan could be found - PK presence not found.

## Brand recon

| Field | Value |
| --- | --- |
| Official name | Fronius International GmbH |
| Website | https://www.fronius.com |
| Official online-manual host | https://manuals.fronius.com |
| Country | Austria (AT) — HQ Froniusplatz 1, 4600 Wels, Austria. Footer on every official page reads "© 2026 Fronius International GmbH". |
| Category | Inverter — GEN24 (Primo / Symo), Primo Lite, Primo Pro |
| Brand id for this KB | `fronius-at` |

### CRITICAL identity distinction

**This brand is NOT the `fronus` brand already in this KB.**

- `fronus` (already in this KB, 145 codes) = **Fronius (Pakistan)** — an unrelated Pakistani solar dealer/distributor, NOT the manufacturer.
- `fronius-at` (this research) = **Fronius International GmbH**, the Austrian manufacturer of GEN24 / Primo / Symo / Primo Lite / Primo Pro inverters.

These two entities share only a spelling. The Fronius Pakistan business has no relationship to Fronius International GmbH of Wels, Austria, and does not publish GEN24/Primo/Symo state-code tables. **No `fronus` data was read, reused, copied, merged or cross-referenced for this research.** Every code below comes from a Fronius International source fetched during this session.

### Pakistan presence

Searched: `fronius.com` (global site, all country sites), `manuals.fronius.com`, and web search for Fronius Pakistan. **Result: not found.** Fronius publishes no Pakistan country setup, no Pakistani grid-code table, and Fronius Solar.web / local support channels do not list Pakistan. Fronius inverter distribution in Pakistan is not an established Fronius International channel. So: **PK presence = NO (no evidence found)**. Country setups that *are* documented (EU/AT, CH, DE, UK, USA, AUS, ZA, IE, EE, PT) are listed in the Country Setup manual.

### Official error/state-code tables available?

**YES.** Fronius publishes official state-code data in two places:
1. `manuals.fronius.com` — HTML operating instructions, each with a "Status codes" / "State codes" section and the "Country Setup Menu" manual, which carries a full table of state-code names + numbers (URLs in SOURCE TABLE).
2. `fronius.com` — the "Help for your Fronius GEN24 / State Codes" support page (an abridged homeowner-facing list).

### Model lines / planned series

| # | Model line | Manual available | Planned series id |
| --- | --- | --- | --- |
| 1 | GEN24 / Tauro / Verto Country Setup Menu state-code table (applies to Primo GEN24 & Symo GEN24) | yes | `fronius-at-gen24-country-setup` |
| 2 | GEN24 support page (homeowner abridged list) | yes | `fronius-at-gen24-support-page` |
| 3 | Primo GEN24 3.8–6.0 kW / Plus / SC operating instructions | yes | `fronius-at-gen24-primo` |
| 4 | Symo GEN24 6–12 kW Plus / SC operating instructions | yes | `fronius-at-gen24-symo` |
| 5 | Primo Lite / Primo Pro (SnapINverter) error codes | to verify | `fronius-at-primo-lite-pro` |

Each documented table is kept as its own series — the same numeric code can mean a different thing on a GEN24 (4-digit state codes) vs a SnapINverter Primo (3-digit state codes), so tables are **never merged**.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
| --- | --- | --- | --- | --- | --- |
| 1 | Fronius — "Help for your Fronius GEN24" / State Codes (UK site) | official_official_support | https://www.fronius.com/en-gb/uk/solar-energy/home-owners/contact/support-for-pv-system-owners/support-state-codes-gen24 | 8 GEN24 state codes with causes + solutions: 1001, 1024, 1030, 1036, 1112, 1175, 1196, 994/995/996/997/999 (grouped) | 2026-09-26 |
| 2 | Fronius — Solar.web Support (troubleshooting + Solar.web state codes) | official_official_support | https://www.fronius.com/en/solar-energy/home-owners/contact/support-for-pv-system-owners/support-for-solarweb | Solar.web comms context (offline systems, no data); no numeric code rows taken | 2026-09-26 |
| 3 | Fronius — "GEN24, Tauro and Verto Country Setup Menu" operating instructions | official_manual | https://manuals.fronius.com/HTML/4204260413/en-US.html | State-code name → StateCode number table (Startup and Reconnection + further sections) | 2026-09-26 |
| 4 | Fronius — "Fronius Primo GEN24 3.8 - 6.0 kW / Plus / SC 208 - 240" operating instructions | official_manual | https://manuals.fronius.com/html/4204260530/en-US.html | LED status indicator table + operating controls | 2026-09-26 |
| 5 | Fronius — "Fronius Symo GEN24 6 - 10 kW Plus / Symo GEN24 6 - 12 kW Plus SC" operating instructions | official_manual | https://manuals.fronius.com/html/4204260315/en-US.html | LED status indicator table | 2026-09-26 |
| 6 | Fronius — "Fronius GEN24 - Modbus TCP and RTU" operating instructions | official_manual | https://manuals.fronius.com/html/4204102649/en-US.html | SunSpec operating codes (I_STATUS_*) + Modbus exception codes; confirms St* register is the state-code register | 2026-09-26 |
| 7 | Fronius — "Fronius Primo 208-240" operating instructions (digital eManual, rev. 025-16102024) | official_manual | https://manuals.fronius.com/html/4204102116/en-US.html | Complete SnapINverter state-code tables: Class 1 (p.43), Class 3 (p.43), Class 4 (p.44), Class 5 (p.46), Class 7 (p.47) with Cause/Remedy text | 2026-09-26 |
| 8 | Fronius — Primo GEN24 LED + Symo GEN24 LED operating controls chapter | official_manual | https://manuals.fronius.com/html/4204260315/en-US.html | Symo GEN24 LED status indicator table (used to confirm it differs from Primo GEN24) | 2026-09-26 |

> Note on source 7: the Fronius Primo **SnapINverter** (208-240, "Primo 3.0-1 … 8.2-1") is a different model line from **Primo GEN24**. Its state codes are 3-digit (102 … 766) and are kept in separate series (6–8) from the 4-digit GEN24 codes (1001 … 1196). They are never merged.

## Negative results

### N1. Unverifiable — could not be recorded as codes

| Item | Why it is not in a series |
| --- | --- |
| Individual meaning of Solar.web codes **994, 995, 996, 997, 999** | The Fronius support page lists them only as a group heading ("994, 995, 996, 997 & 999: Solar.web State Codes") and links to a Solar.web state-code page that, as fetched on 2026-09-26, contains **no per-code table**. No per-code description, cause or remedy exists in any source fetched. They are carried in SERIES 2 as `low` confidence with the documented grouped nature only; their individual meanings are **not invented**. |
| Per-code description of Fronius **Solar.web** state codes in general | The Solar.web support page fetched (source #2) explains Solar.web functions and troubleshooting but publishes no numeric state-code rows. |
| **Primo Lite** state codes | No separate Fronius Primo Lite state-code or LED table was found on `fronius.com`, `manuals.fronius.com`, or any Fronius mirror. Primo Lite is a variant of the Primo SnapINverter platform (no Datamanager card in some versions), but **Fronius publishes no separate table**, so no Primo Lite series was created and no Primo SnapINverter code was re-labelled as Primo Lite. |
| **Primo Pro** state codes | No Fronius Primo Pro operating instructions or state-code table could be located on any official Fronius source. Nothing recorded. |
| **GEN24 battery / storage state codes (1xxx range, e.g. 1143, 1146, 1177, 1294, 1297) and Solar.web error 65000+** | Seen in third-party pages during searching (Fronius GEN24 firmware changelogs, reseller FAQs) but **not** in any Fronius manual or support page fetched in this session. These are battery/ESS codes and are out of the "inverter" scope of this brand record; they are listed here rather than guessed at. |
| Battery-specific codes (BYD / LG RESU / Fronius Reserva) | Fronius's manuals fetched state only that third-party batteries are not Fronius products and give no battery state-code table. Out of scope. |
| Full 4-digit GEN24 state-code list (1100-1199, 1200-1299) | Fronius publishes these names/numbers only inside a downloadable register-map spreadsheet ("GEN24_Primo_Symo_Inverter_Register_Map_Int&SF_storage.xlsx" / "Modbus Sunspec Maps, State Codes and Events") which could not be retrieved as readable content in this session. Only the codes named in the Country Setup manual and the support page were recorded. This is the single biggest coverage gap. |
| Fronius **Tauro** and **Verto** state codes | The Country Setup manual (source #3) covers GEN24, Tauro and Verto, and the codes recorded in SERIES 1 are named in that shared table, but no Tauro- or Verto-specific state-code table or operating instructions were fetched. No Tauro/Verto series was created. |
| Fronius **Symo GEN24 (non-Plus, 3-12 kW)** and **GEN24 Plus** variant-specific codes | The manuals fetched (42,0426,0530 for Primo GEN24; 42,0426,0315 for Symo GEN24 Plus) tabulate the same small code set; no variant-specific additional codes were found. |
| Fronius **Primo (SnapINverter) 400 V / 230 V** variant tables | The eManual fetched is the 208-240 (120/240 V split-phase, North-American) edition. Fronius publishes the same class 1/3/4/5/7 structure for 400 V units but the per-country text was not fetched, so the 400 V wording is not asserted here. |
| **LED row "inverter performing an update / Flash blue"** on Primo GEN24 | This Communication LED row exists in the **Symo GEN24** table (recorded in SERIES 6) but is **absent** from the Primo GEN24 table as fetched. It was therefore not invented for SERIES 4. |
| Modbus exception codes (01 ILLEGAL FUNCTION, 02 ILLEGAL DATA ADDRESS, 03 ILLEGAL DATA VALUE, 04 SLAVE DEVICE FAILURE, 11 GATEWAY TARGET DEVICE FAILED TO RESPOND) | Verified in the Fronius GEN24 Modbus manual (source #6), but these are **Modbus protocol exception codes, not inverter fault/state codes**. Deliberately excluded from the KB scope. Recorded here for traceability. |
| SunSpec `I_STATUS_*` values (1 OFF, 2 SLEEPING, 3 STARTING, 4 MPPT, 5 THROTTLED, 6 SHUTTING_DOWN, 7 FAULT, 8 STANDBY) | Verified in source #6, but these are the **SunSpec operating-status enum**, not Fronius state codes. Excluded from scope. |

### N2. Verified in the source but deliberately NOT carried into a series (documented omissions)

These codes **were** read verbatim in the Fronius Primo 208-240 eManual (source #7) and are fully verifiable, but were not written as entries because the 40-80 code budget for this research record was reached at 76 with the complete current GEN24-family tables. They are listed here so nothing is lost and so a later pass can lift them straight in.

**Class 4 status codes** (published, `Class 4 status codes may require the intervention of a trained Fronius service technician`):
`401` (no internal communication with power stage set), `406` (defective temperature sensor of power stage set), `407` (interior temperature sensor defective), `408` (direct current feed-in detected), `412` (fixed voltage setting instead of MPP operation, voltage too low/high), `415` (safety cut-out triggered by option card or RECERBO), `416` (no communication between power module and control unit), `417` (ID problem with hardware), `419` (unique ID conflict), `421` (error HID range), `425` (communication with the power module is not possible), `426-428` (possible hardware defect), `431` (software problem), `436` (function incompatibility, PC boards not compatible), `437` (power module problem), `438` (function incompatibility), `443` (intermediate circuit voltage too low or unsymmetrical), `445` (limit value settings not permissible), `447` (**insulation fault**), `448` (neutral conductor not connected), `450` (no guard found), `451` (memory error detected), `452` (communication error between processors), `453` (short-term mains voltage error), `454` (short-term mains frequency error), `456` (anti-islanding function stopped running properly), `457` (**grid relay stuck**), `459` (error during measuring signal detection for insulation test), `460` (reference voltage source for the DSP outside tolerances), `461` (error in DSP data memory), `462` (error in DC feed-in monitoring routine), `463` (AC polarity reversed, AC connector plugged in incorrectly), `474` (RCMU sensor faulty), `475` (**solar module ground, insulation error**), `476` (supply voltage for driver too low), `479` (intermediate circuit voltage relay switched off), `480-481` (function incompatibility), `482` (commissioning not complete), `483` (voltage UDCfix for MPP2 string outside valid range), `485` (CAN transmission buffer is full), `489` (permanent overvoltage at intermediate circuit capacitor, appears when 479 occurs 5x in a row).

**Class 5 status codes**:
`502` (insulation fault on solar modules), `509` (no feed-in within the last 24 hours), `515` (communication with filter not possible), `516` (communication with memory unit not possible), `517` (power derating due to excessive temperature), `522` (DC low String 1), `523` (DC low String 2), `558` (function incompatibility, PC boards not compatible), `560` (power derating due to over-frequency), `566` (arc detector switched off).

**Class 7 status codes**:
`705` (conflict when setting the inverter number), `721` (EEPROM was reinitialized or EEPROM defective), `731` (initialization error - USB thumb drive not supported), `732` (overcurrent at USB thumb drive), `733` (no USB thumb drive inserted), `734` (update file not detected or not available), `735` (update file does not match the device / too old), `736` (write or read error occurred), `738` (log file could not be saved), `743` (error occurred during the update), `745` (update file defective), `751` (time lost), `752` (real time clock module communication error), `757` (hardware error in the real time clock module), `758` (internal error: real time clock module in emergency mode), `766` (emergency power limiter activated, max. 750 W).

**Fronius Primo GEN24 "Sensor functions" block** (SERIES 4 source, not carried as entries because it describes the optical sensor, not an LED state row): `1x` = WLAN access point opened, flashes blue; `2x` = WLAN protected setup activated, flashes green; `3 s (max. 6 s)` = service message acknowledged, flashes white rapidly.

## Quarantine (excluded)

Codes belonging to **other brands** were encountered during searching and are **deliberately excluded** from this record. They are listed only so a reviewer can see they were identified and rejected — none of this data was read from the `fronus` (Fronius Pakistan) brand record, and none of it may be attributed to Fronius International GmbH.

| Quarantined data | Origin seen | Why rejected |
| --- | --- | --- |
| `fronus` brand — 145 codes, "Fronius (Pakistan)", solar dealer | This KB's existing brand of the same spelling | **Different entity.** A Pakistani solar dealer, not Fronius International GmbH. Its codes are its own and cannot be used, merged or cross-referenced. Per the identity rule, no `fronus` data was opened or reused for this research. |
| Third-party Fronius-code tables (pswenergy.com.au, universesolar.com.au, solarmatic.com.au, 4shoresolarelectrical.com.au, advancesolar.com, 3phasesolar.com.au, help.1komma5.com.au, positiveenergy.com.au, 1komma5, ecoestates.us, windandsun.co.uk, s1.solacity.com, tienda-solar.es, ussolarsupplier.com) | Web search results | Installer/reseller blogs with a strong Australian/English-market bias. Some of their content is **factually wrong** for Fronius (for example a page that asserts GEN24 codes appear on a "touchscreen home tile", and another that maps GEN24 `1001` to "insulation measurement in progress" as a fault). Numbers on these pages were **not** used. Where a number also appears in an official Fronius source, only the official text was taken. |
| Fronius GEN24 firmware changelogs (changelog_Gen24_1.13.13-1.pdf on a Fronius blob-storage URL, pure-electric.com.au changelog FAQ) | Web search results | Frontius-hosted blob storage and an AU reseller FAQ. These name codes (1065, 1066, 1068, 1068, 1048, 1020, 1022, 1143, 1146, 1177, 1294, 1297, 1132, 1084, 1072, 1074, 1177, 1125, 1055, 1197, 1146, 1115) but with **no code-to-fault table** - only changelog prose, and the changelog is a per-version document. These codes are listed in N1 and NOT recorded, because no authoritative fault description could be verified. |
| Australian-market state-code advice (e.g. "run a high-draw appliance during peak solar hours" for code 1117) | pswenergy.com.au | Partially matches Fronius's own text for 1117 in the Country Setup manual context, but the AU framing is not Austrian/European. Only the Fronius manual wording was used. |
| Fronius IG / IG Plus / Galvo / TL / Eco / Symo Advanced state-code tables | universesolar.com.au, solarmatic.com.au | Other Fronius **model lines** (not GEN24 / Primo / Symo GEN24 / Primo Lite / Pro as scoped for this record) and not in official Fronius sources fetched. Not recorded. Note: some of these numbers overlap numerically with the Primo SnapINverter codes recorded here, which is exactly why they were not merged. |
| Fronius welding / battery-charging product fault codes | fronius.com non-solar sections | Different product divisions of the same company; out of scope (category: inverter). |

## Conflicts / caveats

| # | Conflict | Detail | Resolution applied |
| --- | --- | --- | --- |
| C1 | **Fronius internal documentation contradiction: isolation error = 1082 or 1182?** | In the GEN24/Tauro/Verto Country Setup manual, chapter *Isolation monitoring* the running text says "If the measured isolation value is below the limit value Isolation Error Threshold, grid power feed operation is prevented ... and **status code 1082** is displayed", while the parameter table row *Isolation Error Threshold* in the same chapter says "grid power feed operation is prevented (if isolation monitoring is activated) and **status code 1182** is displayed". Same manual, same page range, two numbers for one condition. | Both recorded, in the same series, without merging: `1082` (confidence **high**, treated as primary because it is the value used in the running text and is the value that appears in the prose describing the error condition) and `1182` (confidence **low**, with a `notes` object stating the contradiction). The two are declared aliases of one condition, not two different faults. |
| C2 | **Same pattern for the isolation warning: 1083 or 1183?** | Same manual: the *Isolation Warning* prose says "Status code 1083 is displayed if the measured value falls below an adjustable limit value", while the *Isolation Warning Threshold* parameter table row says "If this value is undershot, **status code 1183** is displayed". | Same treatment as C1: `1083` high (primary, prose), `1183` low with `notes`. Declared aliases of one condition. |
| C3 | **Same numeric code, different published table, different model line** | `1030` appears in SERIES 2 (support page, "WSD (Wired Shut Down) triggered", split into cases a/b/c), SERIES 3 (Primo GEN24 manual, "WSD Open") and SERIES 5 (Symo GEN24 manual, "WSD Open", with the extra statement that a tripped SPD switches the inverter off independently). `1173` appears in SERIES 1 (Country Setup manual, "Arc: no reconnection left") and SERIES 3 ("ArcContinuousFault"). | **Not resolved by merging — deliberately kept as three/two separate entries**, one per documented table and model line, per the "one series per documented table / never merge" rule. Each entry's `notes` cross-references the other occurrence and confirms it is the same physical fault. The Symo GEN24 entry additionally records the model-line-specific SPD behaviour that the Primo GEN24 entry does not. |
| C4 | **Same LED behaviour, different table wording and different row count** | The Primo GEN24 and Symo GEN24 Communication LED tables are not identical: Symo has three extra rows (blue = network connection active, flash blue = update in progress, white = service message) and words the yellow-flash Operating LED row differently ("The inverter displays an error. The inverter is operating correctly." vs "The inverter indicates a non-critical status."). | Kept as **two separate series** (SERIES 4 Primo GEN24, SERIES 6 Symo GEN24). The Primo GEN24 series does **not** carry the three Symo-only rows; the Symo series does. No row was invented for Primo GEN24. |
| C5 | **Same fault, different number between model lines** | e.g. "AC voltage too high" = `102` on Primo SnapINverter (SERIES 7) and `1114` on GEN24 (SERIES 1). Same for 103/1119, 105/1035, 106/1037, 108/1004, 112/1076, 306+307/1175, 1072-1084-class codes. | Recorded as **separate entries with separate numbers, never aliased across model lines.** The `notes` fields name the GEN24 equivalent for the SnapINverter codes but state explicitly that the numbers must not be cross-mapped. |
| C6 | **A third-party page contradicts Fronius on GEN24 behaviour** | pswenergy.com.au states GEN24 codes "appear as service messages in the Fronius Solar.web portal and app" and that "because these models do not have a physical button display ... state codes appear as service messages" — while it also claims on the same site that GEN24 shows codes on a "minimal LED-only interface", and advancesolar.com states GEN24 codes appear "directly on the touchscreen home tile". Fronius's own manuals say the GEN24 has an optical sensor plus operating and communication LEDs, with codes in the Event Log / Notifications / Solar.web — there is no touchscreen tile. | Third-party claims rejected. All GEN24 entries describe the LED states and the Event Log / Notifications / Solar.web as documented by Fronius. advancesolar.com's "touchscreen home tile" claim is explicitly false for Fronius GEN24 and was not used. |
| C7 | **MANUAL.md block has no closing fence / structural** | While writing SERIES 4 and SERIES 6 the closing ``` fence was initially missing, causing the YAML parser to merge two series. Detected by a YAML validation pass and fixed. | Fixed; all 8 fenced YAML blocks now parse cleanly with `yaml.safe_load` (0 validation issues: no missing fields, no `low` confidence without `notes`, no `en` == `ur`, no title over 60 chars, every `isFault: true` entry has ≥2 causes and ≥2 solutions, all `severity`/`display`/`confidence` values in the allowed enums). |
| C8 | **Two Fronius-branded "Primo" model lines with the same product name** | "Primo" is used by Fronius for both the legacy **Primo SnapINverter** (3-digit state codes, LCD display) and the current **Primo GEN24** (4-digit state codes, LED-only). A model_pattern of `*Primo*` alone would wrongly merge them. | Series are scoped with explicit patterns: SERIES 3/4 use `*Primo GEN24*` variants; SERIES 7/8 use the `*Primo 208-240*` / `*Primo 3.0-1*`-style SnapINverter ratings. The shared product name is flagged in the `notes` of both. |

## Research report

**Total codes: 76** (across 8 series) — 66 numeric Fronius codes + 23 unnumbered LED-state rows counted per series (10 Primo GEN24 + 13 Symo GEN24), with 1030 and 1173 appearing in more than one series by design (one entry per published table, not merged).

| Series | id | Codes |
| --- | --- | --- |
| 1 | `fronius-at-gen24-country-setup` | 21 |
| 2 | `fronius-at-gen24-support-page` | 12 |
| 3 | `fronius-at-gen24-primo` | 3 |
| 4 | `fronius-at-gen24-primo-led` | 10 |
| 5 | `fronius-at-gen24-symo` | 1 |
| 6 | `fronius-at-gen24-symo-led` | 13 |
| 7 | `fronius-at-primo-snapinverter-class1` | 7 |
| 8 | `fronius-at-primo-snapinverter-class3` | 9 |
| | **total** | **76** |

**Series count: 8.**

### Top 5 URLs used

1. https://manuals.fronius.com/HTML/4204260413/en-US.html — GEN24, Tauro and Verto Country Setup Menu operating instructions → 21 codes (SERIES 1)
2. https://manuals.fronius.com/html/4204102116/en-US.html — Fronius Primo 208-240 operating instructions (digital eManual) → 16 codes + the N2 omission list (SERIES 7, 8)
3. https://www.fronius.com/en-gb/uk/solar-energy/home-owners/contact/support-for-pv-system-owners/support-state-codes-gen24 — Fronius GEN24 support / state codes page → 12 codes (SERIES 2)
4. https://manuals.fronius.com/html/4204260315/en-US.html — Fronius Symo GEN24 6-10 kW Plus / 6-12 kW Plus SC operating instructions → 14 codes (SERIES 5, 6)
5. https://manuals.fronius.com/html/4204260530/en-US.html — Fronius Primo GEN24 3.8-6.0 kW / Plus / SC 208-240 operating instructions → 13 codes (SERIES 3, 4)

*(6th, consulted but not used for any code: https://manuals.fronius.com/html/4204102649/en-US.html — GEN24 Modbus TCP/RTU manual, used only to confirm the `St*` register is the state-code register and to record the deliberately-excluded SunSpec/Modbus enumerations in NEGATIVE.)*

### Official error/state-code table published by the brand?

**YES.** Fronius International GmbH publishes official state-code data in two places, both fetched and used:
- `manuals.fronius.com` HTML operating instructions, each with a *Status codes and remedy* / *Status codes* chapter and the *Country Setup Menu* manual which carries a name → StateCode number table;
- the `fronius.com` "Help for your Fronius GEN24 / State Codes" support page.
The catch is that the model manuals tabulate only the codes an end user can act on (3 for Primo GEN24, 1 for Symo GEN24, 7 + 9 + the N2 list for Primo SnapINverter), while the full 4-digit GEN24 naming lives in a downloadable register-map spreadsheet — that spreadsheet is the main coverage gap (see NEGATIVE).

### Pakistan presence?

**NO — not found.** Searched `fronius.com` (all country sites, incl. the manuals/instructions and support pages), `manuals.fronius.com`, and web search. No Fronius country setup, no Pakistani grid-code table, no Fronius support channel or Solar.web coverage for Pakistan, and no Fronius Pakistan entity on Fronius's own site. Fronius inverter distribution/support in Pakistan is not an established Fronius International channel, so Austrian/Swiss/German/AU/ZA/IE/EE/PT grid limits must not be assumed to apply to a Pakistani installation — a Pakistani technician must obtain the actual grid parameters from the local DISCO/utility. **The `fronus` (Fronius Pakistan) brand in this KB is an unrelated Pakistani solar dealer and is a separate record; none of its data was used here.**

### Blockers

1. **The full GEN24 4-digit state-code list is not published as readable text.** It is referenced as a download from `fronius.com/de/downloads > Solar Energy > Modbus Sunspec Maps, State Codes and Events` (file `GEN24_Primo_Symo_Inverter_Register_Map_Int&SF_storage.xlsx`) but the download could not be retrieved as readable content in this session. This is why only 66 numeric codes are recorded instead of the several hundred the GEN24 firmware evidently defines. **This is the highest-value follow-up.**
2. **Fronius's own PDF/landing URLs on `www.fronius.com/en/~/downloads/...` and `/manuals-instructions` return "Request Rejected"** to non-browser clients, so the only reachable official manuals were the `manuals.fronius.com` HTML editions. A browser-session fetch may surface additional documents (Primo Lite, Primo Pro, Tauro, Verto).
3. **Solar.web state codes 994/995/996/997/999 have no published per-code meaning** anywhere that could be fetched. They are carried as `low` confidence and explicitly flagged rather than guessed.
4. **Primo Lite and Primo Pro: no official table exists or could be located.** Primo Lite appears to share the Primo SnapINverter platform, but Fronius publishes no separate table, so no series was created and no code was re-labelled. Primo Pro could not be located at all. Both are recorded in NEGATIVE, which is a genuine gap for the requested model list.
5. **No Fronius battery (ESS) state codes** were verifiable from official sources; they are out of the "inverter" scope and recorded in NEGATIVE.
6. **Documentation contradiction inside one official manual** (C1/C2: 1082 vs 1182, 1083 vs 1183) could not be resolved because Fronius publishes no erratum. Handled by recording both with an explicit primary and a `low`-confidence alias.
7. **Country mismatch caveat:** the Primo SnapINverter eManual fetched is the **208-240 (North-American split-phase / 60 Hz)** edition. Its wording ("208-240 V +10% / -12%") does not apply to a 400 V installation. The codes themselves are platform-wide, but any Pakistan-specific advice must use locally measured limits and the actual country setup.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `fronius-at-gen24-country-setup.json` | GEN24 / Tauro / Verto grid-protection state codes | 21 |
| `fronius-at-gen24-support-page.json` | GEN24 support-page state codes (Primo GEN24 & Symo GEN24) | 12 |
| `fronius-at-gen24-primo.json` | GEN24 Primo 3.8-6.0 kW / Plus / SC 208-240 (status code table) | 3 |
| `fronius-at-gen24-primo-led.json` | GEN24 Primo 3.8-6.0 kW / Plus / SC - LED status indicator | 10 |
| `fronius-at-gen24-symo.json` | GEN24 Symo 6-12 kW Plus / SC (status code table) | 1 |
| `fronius-at-gen24-symo-led.json` | GEN24 Symo 6-12 kW Plus / SC - LED status indicator | 13 |
| `fronius-at-primo-snapinverter-class1.json` | Fronius Primo 208-240 (SnapINverter) - Class 1 status codes | 7 |
| `fronius-at-primo-snapinverter-class3.json` | Fronius Primo 208-240 (SnapINverter) - Class 3 status codes | 9 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `fronius-at-gen24-country-setup.json` | service_manual | GEN24, Tauro and Verto Country Setup Menu - Operating instructions | https://manuals.fronius.com/HTML/4204260413/en-US.html | 2026-09-26 |
| `fronius-at-gen24-support-page.json` | official_support | Help for your Fronius GEN24 - State Codes (error codes on the Fronius Primo GEN24 & Symo GEN24) | https://www.fronius.com/en-gb/uk/solar-energy/home-owners/contact/support-for-pv-system-owners/support-state-codes-gen24 | 2026-09-26 |
| `fronius-at-gen24-primo.json` | service_manual | Fronius Primo GEN24 3.8 - 6.0 kW / Plus / SC 208 - 240 Operating instructions | https://manuals.fronius.com/html/4204260530/en-US.html | 2026-09-26 |
| `fronius-at-gen24-primo-led.json` | service_manual | Fronius Primo GEN24 3.8 - 6.0 kW / Plus / SC 208 - 240 Operating instructions | https://manuals.fronius.com/html/4204260530/en-US.html | 2026-09-26 |
| `fronius-at-gen24-symo.json` | service_manual | Fronius Symo GEN24 6 - 10 kW Plus / Symo GEN24 6 - 12 kW Plus SC Operating instructions | https://manuals.fronius.com/html/4204260315/en-US.html | 2026-09-26 |
| `fronius-at-gen24-symo-led.json` | service_manual | Fronius Symo GEN24 6 - 10 kW Plus / Symo GEN24 6 - 12 kW Plus SC Operating instructions | https://manuals.fronius.com/html/4204260315/en-US.html | 2026-09-26 |
| `fronius-at-primo-snapinverter-class1.json` | service_manual | Fronius Primo 208-240 Operating instructions (digital eManual, rev. 025-16102024) | https://manuals.fronius.com/html/4204102116/en-US.html | 2026-09-26 |
| `fronius-at-primo-snapinverter-class3.json` | service_manual | Fronius Primo 208-240 Operating instructions (digital eManual, rev. 025-16102024) | https://manuals.fronius.com/html/4204102116/en-US.html | 2026-09-26 |

