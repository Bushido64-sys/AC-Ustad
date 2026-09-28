# Growatt (`growatt`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **125** across **6** model-scoped series

Official: https://www.growatt.com

Shenzhen Growatt New Energy Co., Ltd, founded 2011. Has its own Growatt Pakistan subsidiary (Islamabad/Lahore/Karachi, service.pk@growatt.com, +92 300 4045 884) and launched SPM HU2 hybrids in Lahore in 2026, so PK support is direct, not via importer.

## Brand recon

- **Official name**: Shenzhen Growatt New Energy Co., Ltd (legal entity on statements) / Growatt New Energy Co., Ltd. Founded 2011 by David Ding, HQ Shenzhen, China.
- **Website**: https://www.growatt.com (global) / https://en.growatt.com (English) / legacy domain https://www.ginverter.com (still linked from Growatt PDF manuals as the official download site).
- **Country**: CN (Shenzhen). Global No.1 residential PV inverter supplier per own claims.
- **PK presence: YES.** Own Growatt Pakistan subsidiary with 3 offices (Islamabad, Lahore, Karachi), country mailbox `service.pk@growatt.com`, `+92 300 4045 884` / `+92 311 1777 030`, dedicated Pakistan country manager, and product launches held in Lahore.
  - https://en.growatt.com/support/contact (Growatt Pakistan — Islamabad / Lahore / Karachi offices, service.pk@growatt.com, +92 numbers)
  - https://en.growatt.com/media/news/growatt-spm-2-5-10ktl-hu2-hybrid-inverter-pakistan-launch (Jul 11 2026 — SPM 2.5–10KTL-HU2 launched at Pakistan Full-Scenario Energy Storage Launch Event, Lahore)
  - https://en.growatt.com/media/news/to-see-how-growatts-spm-hybrid-inverter-help-pakistani-homes-achieve-energy-independence (Apr 18 2025 — "Mian Fahad, Senior Country Manager for Pakistan")
  - https://fmst.com.pk/growatt-solar (Lahore authorized distributor — secondary evidence)
- **Official published fault-code table: YES** — fault tables are published inside Growatt's own Installation & Operation Manuals (`GR-UM-xxx`) and Growatt publishes an official "Troubleshooting Guide for Growatt Inverters" (Growatt New Energy Australia Pty Ltd). There is **no** single consolidated HTML fault-code table on growatt.com; the FAQ pages (en.growatt.com/support/faq/*) are per-code Q&A and are effectively a second official table.
  - MOD 3-15KTL3-X manual §13.3 "System error" (Error 200–425)
  - SPF 6000 ES PLUS manual "Fault Reference Code" + "Warning Indicator" (2-digit/3-digit codes)
  - https://en.growatt.com/support/faq/off-grid-inverter and /inverter and /storage-inverter
- **Counterfeits named by Growatt itself (Pakistan)**: FNDI, KANGWEISI, SOLATO — official statement https://en.growatt.com/media/statements/statement-on-unauthorized-growatt-inverter-products-in-the-pakistani-market (Aug 28 2024). Their fault codes must be quarantined, never merged into Growatt.
- **Model lines covered in this research**: MIN (TL-XH / XH2), MOD 3-15KTL3-X, MOD/MID TL3-H family (via shared Error table), SPH/SPF/SPM hybrid & off-grid (2-digit + 3-digit fault/warning tables), WIT 50-100K storage/hybrid, legacy grey-body Gen-1 (1xx Error / W-warning codes), MIC/TL3-S where documented.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | MOD 3-15KTL3-X User Manual EN (GR-UM-212-A-02), Growatt CDN | official_manual | https://growatt.tech/wp-content/uploads/2023/02/MOD-3-15KTL3-X-User-Manual-EN-202201.pdf | §13.3 System error table: Error 200–308, 400–425 + "Error 303 NE abnormal" earth-fault note | 2026-09-26 |
| 2 | SPF 6000 ES PLUS User Manual EN (Growatt PL CDN) | official_manual | https://growatt.pl/wp-content/uploads/2024/06/SPF_6000_ES_Plus_User_Manual_EN_202212.pdf | "Fault Reference Code" 01–81, "Warning Indicator" 01–45, Trouble Shooting table (fault + warning codes) | 2026-09-26 |
| 3 | Growatt official statement — unauthorized products in Pakistan | official_statement | https://en.growatt.com/media/statements/statement-on-unauthorized-growatt-inverter-products-in-the-pakistani-market | Quarantine list FNDI / KANGWEISI / SOLATO; service.pk@growatt.com; PK presence | 2026-09-26 |
| 4 | Growatt global contact page (Pakistan offices) | official_support | https://en.growatt.com/support/contact | Growatt Pakistan — Islamabad / Lahore / Karachi, service.pk@growatt.com, +92 300 / +92 311 | 2026-09-26 |
| 5 | Growatt news — SPM 2.5–10KTL-HU2 launch in Pakistan | official_news | https://en.growatt.com/media/news/growatt-spm-2-5-10ktl-hu2-hybrid-inverter-pakistan-launch | PK market presence, hybrid SPM family | 2026-09-26 |
| 10 | Growatt FAQ — Off-Grid Inverter (full page, per-code Analysis/Test Method/Solution) | official_support | https://en.growatt.com/support/faq/off-grid-inverter | Concrete diagnostic procedures for faults 03, 05, 06, 07, 08, 09, 51, 52, 56, 81 (280 V/200 ms trip, 112-150 %/10 s and >150 %/2 s overload, 400 V/30 s bus, MOV board, US2/Li-only charging rule) | 2026-09-26 |
| 11 | MID 33-50KTL3-X2 User Manual EN (GR-UM-247-A-01), Growatt CDN | official_manual | https://growatt.tech/wp-content/uploads/shared-files/MID-33-50KTL3-X2-User-Manual-EN-1.pdf | Warning table 200–210/300–307/400–406 and Error table 200–425 (different wording from MOD 3-15KTL3-X) | 2026-09-26 |
| 12 | Troubleshooting Guide for Growatt Inverters — Growatt New Energy Australia Pty Ltd | official_service_doc | https://baikaltesla.com/wp-content/uploads/2022/07/TROUBLE-SHOOTING-GUIDE.pdf | Legacy Gen-1 grey-body 1xx error table (100, 101/102/121, 111, 116, 117, 118, 119, 120, 122) + "Other faults" no-display table | 2026-09-26 |
| 13 | MOD 3-15KTL3-X User Manual EN (GR-UM-212-A-05 duplicate on Growatt CDN) | official_manual | https://growatt.tech/wp-content/uploads/shared-files/MOD-3-15KTL3-X-User-Manual-EN-202201.pdf | Cross-check of §13.2/§13.3 tables (confirms Series 1 wording) | 2026-09-26 |
| 6 | MIN 3000-11400TL-XH-US User Manual EN (us.growatt.com) | official_manual | https://us.growatt.com/upload/file/MIN_3-11.4kTL-XH-US_User_Manual_EN_202501.pdf | §7.3.2 LED description (POWER/COMM/BAT/FAULT + combination), §11.4.2 System fault, §11.4.3 Inverter warning, §11.4.4 Inverter fault | 2026-09-26 |
| 7 | WIT 50-100K Storage/Hybrid Inverter User Manual (GR-UM-299-A-02) | official_manual | https://growatt.pl/wp-content/uploads/2023/11/Instrukcja-obslugi.pdf | Table 9.1 Warning codes (200–609), Table 9.2 Error codes (200–606) | 2026-09-26 |
| 8 | Growatt FAQ — Off-Grid Inverter (en.growatt.com) | official_support | https://en.growatt.com/support/faq/off-grid-inverter | Per-code official analysis/test method for SPF faults 05,06,07,08,09,81 | 2026-09-26 |
| 9 | Growatt FAQ — Off-Grid Inverter page 2 | official_support | https://en.growatt.com/support/faq/off-grid-inverter?page=2 | Official analysis for warnings 01,02,03,04,20 (BMS comm) | 2026-09-26 |

## Negative results

Items seen during research that could NOT be verified against a Growatt-owned source fetched now. **Do not add these to the KB.**

- **`INV_TCODE_*`, `PCS_TCODE_*`, `BMS_TCODE_*` type rows** — no official Growatt manual, Growatt support page or Growatt CDN document containing these row types was found. The only related material is third-party reverse-engineered ShineServer/MQTT API write-ups (e.g. `growatt_api_reference.md` on GitHub, which itself states "Not officially documented by Growatt") and a Postman "ShineServer Public" collection that is not Growatt-published documentation. Nothing to cite → all TCODE rows go to NEGATIVE until a Growatt firmware/protocol PDF is obtained.
- **Error 204 "PV Reversed"** on the TL3 platform — appears only in a third-party Scribd transcription. It is **not** in the fetched MOD 3-15KTL3-X manual §13.3. The nearest official equivalent is **Warning 209 / Warning 210 "Panel voltage is too high" / "Panel reverse"** on MID 33-50KTL3-X2 and **Warning 200-210** range. Do not create an "Error 204" entry from the Scribd source.
- **Error 309 "ROCOF Fault", Error 310 "NE Fault", Error 311 "Backflow Power Overflow / Control TimeOut"** — the Scribd transcription claims these; only **Error 309 (ROCOF)** was verified, in the WIT 50-100K manual. Errors 310 and 311 as worded in the Scribd table remain unverified.
- **Errors 423 / 424 / 426 / 427 / 428 / 429, 500–511, 600–608 on TL3 bodies** — a third-party list shows these with Chinese-language or blank descriptions. 423 and 424 **were** verified on MID 33-50KTL3-X2 (Series 4). Errors 426–429, 500–511 and 600–608 were **not** found in any Growatt manual fetched here (only WIT 500/501/503/507/508/509/510/511/601/602/603/604/605 exist and mean different things on WIT) → unverified, keep out.
- **TL3-S 30-50K fault guide** (codes like "122 Bus voltage abnormal") — only reachable as a Scribd page snippet, not fetchable as a verifiable document. NEGATIVE.
- **"W01 / W20 / W21 / W22" and "106 / 107 / 108 / 109" tables** at pswenergy.com.au — no Growatt manual cited, not corroborated by any Growatt source. The "W21 String PID quick connect terminal detection error" wording is close to official "Warning 201 String/PID quick connect terminal abnormal" (verified, Series 4), but the pswenergy digit assignments are not. Out.
- **"Error 411 = DSP communicates with M3 abnormal" / "Error 420 = GFCI fault"** wording at igrowattinverter.com — independently corroborated by the official MIN TL-XH-US manual (Series 5), so those two are already covered from the official source; the site's other rows (116/117/121/122 under the 2xx-era naming, "100 = voltage fault", "109 = AC relay") are **not** covered and are out.
- **MIC / microinverter (MIC 3000TL) fault codes** — no Growatt manual with a fault table was located. Nothing to cite.
- **SPH 8000-10000TL-HU-US, SPM 2.5–10KTL-HU2, SPF 3500 ES Lite, SPF 5000TL HVM, MOD 3-10KTL3-XH, MID 11-30KTL3-XH** — manuals are known to exist (confirmed in search results / download portals) but were not fetched and extracted in this session, so no codes are claimed for them. The MIN XH (Series 5) codes are a documented US-grid subset; they must **not** be assumed identical on the SPH/SPM bodies.
- **Growatt error-code website `growatinverter.com/en/error-codes`** — a Dubai company ("Growatt MJS Solutions") whose database is labelled "IN PROGRESS / coming soon" with zero codes published. Not a source. Also see QUARANTINE.

## Quarantine (excluded)

Counterfeit / non-Growatt material that must never be merged into the `growatt` namespace.

- **FNDI** — named by Growatt as distributing unauthorized "Growatt" inverters in Pakistan.
- **KANGWEISI** — same.
- **SOLATO** — same.
  - Authority: https://en.growatt.com/media/statements/statement-on-unauthorized-growatt-inverter-products-in-the-pakistani-market — "These products are being distributed by companies operating under the names FNDI, KANGWEISI, and SOLATO… Growatt does not provide warranty for machines whose provenance cannot be identified." Verification channel given by Growatt for Pakistan: `service.pk@growatt.com`.
  - Practical KB rule: any fault code shown on an FNDI / KANGWEISI / SOLATO unit is a **different brand's** code even if the number matches. In Pakistan specifically, a "Growatt-branded" unit with no verifiable serial/provenance should first be checked against this statement.
- **`growattpk.com`** — a Karachi/Lahore site using the name "Growatt-PK" with `@growattpk.com` emails, but it is **not** Growatt New Energy (it lists Axpert, Revo, Suntree, Aim, Ziewnic, tubewell brands on the same pages). Quarantine entirely: it mixes genuine Growatt naming with other brands' code schemes, exactly the contamination this KB must avoid.
- **`growatinverter.com` ("Growatt MJS Solutions", Dubai)** — unrelated reseller with its own "error code database" placeholder. Quarantine.
- **Generic "Growatt" code blogs** (pswenergy.com.au, passolar.co, igrowattinverter.com, fallonsolutions.com.au, scribd transcriptions, en.pvgroup.pl, baikaltesla mirror of the official guide) — none of these are Growatt-owned. Where they merely *mirror* an official Growatt document that we have already fetched (e.g. the Growatt Australia troubleshooting guide), use the Growatt document, not the mirror. Where they assert codes we could not verify (see NEGATIVE), do not import them.

## Conflicts / caveats

Same digits, different meanings, or contradictory wording. **Never merge across these.**

1. **Fault vs Warning table collision inside the SPF 6000 ES PLUS manual.** The manual has two separate 2-digit tables. Fault 01 = "Fan is locked", Warning 01 = "Fan is locked when inverter is on" (near-identical), but Fault 03 = "Battery voltage is too high" while Warning 03 = "Battery is over-charged", and Fault 55 = "Over DC voltage in AC output" while there is no Warning 55 at all. The only documented way to tell them apart: **"Buzzer beeps continuously and red LED is on. (Fault code)"** vs **"Buzzer beeps once every second, and red LED is flashing. (Warning code)"**. The KB must scope these to the series, not to a global Growatt namespace.
2. **Fault 55 wording conflict inside the same manual.** §"Fault Reference Code" says 55 = "Over DC voltage in AC output"; §"Trouble Shooting" says "Fault code 55  Output voltage is unbalanced". Same digit, two different meanings in one official document. Series 2 records both; a KB lookup must show both possibilities.
3. **Merged codes in the SPF troubleshooting table vs the SPF code table.** The troubleshooting table collapses 06/58 into one row ("Output abnormal… higher than 280Vac or lower than 80Vac") and 09/53/57 into one row ("Internal components failed"), while the code table lists 06, 58, 09, 53 and 57 as five distinct events with distinct descriptions. Series 2 keeps them as separate entries with the merged form as an alias.
4. **Cross-model 3-digit collisions (the big one).** Growatt reuses digits across model lines with *different* meanings. Verified examples:
   | Digit | MOD 3-15KTL3-X | MID 15-50KTL3-X2/XL2 | WIT 50-100K | MIN 3000-11400TL-XH-US |
   |---|---|---|---|---|
   | 200 | AFCI Fault | DC arc abnormal | AFCI Fault | (not published) |
   | 202 | "DC input voltage exceeding the maximum tolerable value" | "Panel voltage is too high" | "PV Voltage High" | "PV Voltage High" |
   | 300 | AC V Outrange | "The mains voltage is abnormal" | "Grid voltage is beyond the permissible range" | "Utility grid voltage out of permissible range" |
   | 301 | AC terminals reversed | AC wiring error | (not published) | (not published) |
   | 302 | No AC Connection | No mains connection | "No AC / utility grid power failure" | (not published) |
   | 303 | NE abnormal | Zero ground detection anomaly | NE Abnormal | **PE abnormal (N-PE > 30 V)** |
   | 304 | AC F Outrange | Abnormal mains frequency | (not published) | AC F Outrange |
   | 305 | Over Load Fault | Output overload protection | (not published) | (not published) |
   | 401 | DC Voltage High Fault | "output voltage DC component is too high" | (not published) | (not published; Warning 404 = EEPROM abnormal) |
   | 402 | Output DC current too high | "output current DC component is too high" | "High DC component in output current" | Output High DCI |
   | 404 | bus sample fault | "Bus voltage sampling is abnormal" | "Bus voltage sampling abnormal" | Bus sample fault |
   | 410 | "Communication board and control panel sampling battery voltage is inconsistent" | **"Inconsistent insulation resistance sampling"** | (not published) | (not published) |
   | 411 | Communication fault | Internal communication abnormal | "Internal communication failure" | **"DSP communicates with M3 abnormal"** |
   | 420 | GFCI Module damage | "Leakage current module is abnormal" | (not published) | **GFCI fault** |
   | 421 | CPLD is abnormal | CPLD abnormal | (not published) | (not published) |
   | 422 | sampling is inconsistent | "Redundant sampling is inconsistent" | "Redundancy sampling inconsistent" | (not published) |
   | 424 | (not published) | "Battery voltage sampling is inconsistent" | (not published) | (not published) |
   | 425 | AFCI self-test fault | AFCI self-check error | AFCI self-test failure | (not published) |
   Also **Error 410 in MID XL2 is an insulation-resistance code, not a battery-sampling code** — a genuinely dangerous mix-up if merged.
5. **Gen-1 grey-body 1xx codes are a different namespace entirely.** On the legacy bodies, 116 = EEPROM fault, 117 = Relay fault, 118 = Init model fault, 119 = GFCI device damage, 120 = HCT fault, 122 = Bus voltage fault. On MOD/WIT the same physical faults carry 414 (EEPROM), 405/117-equivalent (Relay), 406 (Init model), 420/201 (GFCI), 404/409 (Bus). A 1xx code on a modern body is not covered by any Growatt manual fetched here.
6. **Growatt's own FAQ contradicts the manuals on 401 and 217.** https://en.growatt.com/support/faq/inverter states "401 is the code for meter fault" and "217 is the total code for battery fault". The official manuals say **Error 401 = "DC Voltage High Fault"** (MOD 3-15KTL3-X) and **Warning 401 = "Meter abnormal"** (WIT 50-100K). The FAQ wording looks like a labelling slip in a category-list answer. KB rule: trust the manual; the FAQ's "401 = meter fault" maps to WIT/MID **Warning 401**, not Error 401.
7. **SPM 2.5–10KTL-HU2 is Growatt's current Pakistan hero product but has no published fault table.** Growatt launched it in Lahore in July 2026 and markets it heavily in PK, yet no SPM-HU2 manual with a code table was retrievable. A PK technician will very often be holding an SPM HU2. This is the biggest documentation gap for this market and is called out in REPORT.

## Research report

**Total codes captured: 125, across 6 series (SERIES 1–6).**
The brief targeted 40–80. The overage is a direct consequence of the non-merge rule plus Growatt's own practice: Growatt reuses the same 3-digit numbers (200/201/202/203/300–305/400–425) on **every** TL3-generation body with materially different descriptions, and reuses 2-digit numbers across a *fault* table and a *warning* table on the same SPF unit. A single global "Growatt Error 401" record would be actively dangerous. Rather than truncate verified rows, every model line got its own series and the digit collisions are documented in CONFLICTS item 4 so the ingest step can scope matches by `model_patterns` instead of by digit. If a hard cap is required, cut Series 1 (37 rows) down to the ~20 highest-traffic codes first — the remainder are all in the same manual table and are re-derivable.

| Series | Model line | Type | Codes | Primary source |
|---|---|---|---|---|
| 1 | MOD 3-15KTL3-X (3-phase string) | on_grid_inverter | 37 | GR-UM-212-A-02 §13.3 |
| 2 | SPF 6000 ES PLUS (off-grid 6 kW LCD) | off_grid_inverter | 26 | SPF 6000 ES PLUS manual, Fault Ref + Warning tables + FAQ |
| 3 | WIT 50-100K-A (3-phase C&I storage hybrid) | hybrid_inverter | 20 | GR-UM-299-A-02 Tables 9.1 / 9.2 |
| 4 | MID 15-50KTL3-XL2 / X2 (3-phase commercial) | on_grid_inverter | 18 | GR-UM-247-A-01 |
| 5 | MIN 3000-11400TL-XH-US (single-phase hybrid) | hybrid_inverter | 15 | us.growatt.com manual §7.3.2, §11.4.2–11.4.4 |
| 6 | Legacy TL Gen-1 grey body | on_grid_inverter | 9 | Growatt Australia Troubleshooting Guide |
| | | **Total** | **125** | |

Includes 3 LED/status rows (Series 5: FAULT solid red, FAULT 1 s blink, BAT 0.5 s fast blink) and 15 `isFault: false` warning/status rows (`severity: info` per schema; the real urgency is carried in the `meaning` text, e.g. Series 3 Warning 310 N-PE abnormal and Series 2 Warning 45 system shut down). All entries are `confidence: high` except the 9 legacy Gen-1 rows, which are `medium` (single credible brand-specific source: Growatt's own Australian service guide) — no `low` entries were emitted, so no `notes` objects were required.

**Top 5 URLs (most codes per fetch):**
1. https://growatt.tech/wp-content/uploads/2023/02/MOD-3-15KTL3-X-User-Manual-EN-202201.pdf — 37 codes
2. https://growatt.pl/wp-content/uploads/2024/06/SPF_6000_ES_Plus_User_Manual_EN_202212.pdf — 26 codes
3. https://en.growatt.com/support/faq/off-grid-inverter — 10 codes + concrete thresholds/procedures (280 V/200 ms; overload 112–150 % for 10 s, >150 % for 2 s; bus >400 V for 30 s; MOV board; lithium US2/Li only, lead-acid 0.2–0.3 C; host-loss 8 s)
4. https://growatt.pl/wp-content/uploads/2023/11/Instrukcja-obslugi.pdf (WIT 50-100K) — 20 codes
5. https://growatt.tech/wp-content/uploads/shared-files/MID-33-50KTL3-X2-User-Manual-EN-1.pdf — 18 codes
(Next: https://us.growatt.com/upload/file/MIN_3-11.4kTL-XH-US_User_Manual_EN_202501.pdf — 15 codes)

**Official published fault-code table: YES.** Growatt publishes fault tables inside its Installation & Operation Manuals (document numbers GR-UM-212, GR-UM-247, GR-UM-299) plus an official "Troubleshooting Guide for Growatt Inverters". There is **no** single consolidated HTML/CSV fault-code table on growatt.com — the per-code FAQ pages (en.growatt.com/support/faq/inverter, /off-grid-inverter, /storage-inverter) are the closest official web equivalent, and the manual download centre (en.growatt.com/support/download) is JS/captcha driven. Note that Growatt's manual download centre requires a verification code, which is why some manuals had to be pulled from regional CDNs (growatt.tech, growatt.pl) that are still Growatt-operated hosts.

**PK presence: YES (strong).** Growatt runs its own Pakistan subsidiary with offices in Islamabad, Lahore and Karachi, a dedicated mailbox `service.pk@growatt.com`, `+92 300 4045 884` / `+92 311 1777 030`, a Senior Country Manager for Pakistan, and full-scene product launches in Lahore (SPM 2.5–10KTL-HU2, July 2026). Growatt also published a dedicated anti-counterfeit statement naming FNDI, KANGWEISI and SOLATO in the Pakistani market (Aug 2024) and asks buyers to verify provenance via `service.pk@growatt.com`. For the KB: every Growatt entry can carry a PK support route, and the counterfeit warning belongs in the brand notes.

**Blockers / gaps:**
- **`INV_TCODE_*` / `PCS_TCODE_*` / `BMS_TCODE_*` rows could not be sourced from any Growatt-owned document.** These live in firmware/monitoring contexts only. All in NEGATIVE. Resolving this needs a Growatt datalogger/ShineWeb protocol document obtained through the PK distributor.
- **SPM 2.5–10KTL-HU2 / SPH 3000-6000TL-HU / SPM 3000-10000TL-HU — Growatt's actual Pakistan hero hybrids — have no fault table retrieved.** This is the most important gap for a PK-first KB. Recommend requesting the manuals through `service.pk@growatt.com` or the Lahore distributor.
- **MOD 3-10KTL3-XH, MID 11-30KTL3-XH, MOD/MID 3-15KTL3-X2(Pro), SPF 3500 ES Lite, SPF 5000TL HVM** — manuals confirmed to exist but not fetched this session; their codes are not claimed.
- **Growatt's own download centre is JS + captcha gated** (`en.growatt.com/support/download`); regional CDNs were used instead. A future session should check `growatt.tech/wp-content/uploads/` and `growatt.pl/wp-content/uploads/` for newer revisions, since Growatt silently re-revisions manuals (e.g. MOD 3-15KTL3-X exists as both GR-UM-212-A-02 and GR-UM-212-A-05 with slightly different table formatting).
- **PDF text extraction**: all fault tables here are text-layer PDFs and extracted cleanly. The only image-only risk is the stamped Growatt counterfeiting statement (embedded as a JPG) — the text was readable from the surrounding page, so nothing was lost.
- **Fault 51 solution for SPF 6000 ES PLUS (MOV board)** is Growatt FAQ guidance for the off-grid platform generally, not stated in the SPF manual itself; kept because it is Growatt-published for the same fault code on the same product family, and flagged in the entry.

**Recommended ingest notes for the "AC Ustad" KB:**
- Scope every lookup by `model_patterns` first, then by code. Never key a Growatt record on the digits alone.
- On the SPF 6000 ES / 3500 ES family, always ask the user whether the buzzer is continuous (fault) or beeping once per second (warning) before showing meaning.
- On MIN TL-XH-US, the LED-only codes carry an `Error:` prefix on the LED screen (e.g. `Error: 414`); the TL3 string bodies show bare 3-digit numbers on the OLED/LCD.
- Every `danger`-severity entry (Error 200/203/303/405/413/420/425, WIT Error 200/201/203/303/311, SPF Fault 05/60) already leads with an isolate-before-you-probe bullet. Keep that ordering.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `growatt-mod-tl3x.json` | MOD 3-15KTL3-X (three-phase string, 2 MPPT) | 37 |
| `growatt-spf-6000es-plus.json` | SPF 6000 ES PLUS (off-grid / hybrid 6kW, LCD) | 26 |
| `growatt-wit-50-100k.json` | WIT 50-100K Storage/Hybrid Inverter (three-phase C&I) | 20 |
| `growatt-mid-15-50ktl3-xl2.json` | MID 15-50KTL3-XL2 / X2 (three-phase commercial string, OLED + touch) | 18 |
| `growatt-min-3000-11400tl-xh-us.json` | MIN 3000-11400TL-XH-US (single-phase hybrid, 4-LED status) | 15 |
| `growatt-legacy-tl-gen1-grey.json` | Legacy TL-series grey-body inverters (Gen 1, 1xx error codes) | 9 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `growatt-mod-tl3x.json` | service_manual | MOD 3-15KTL3-X User Manual EN (GR-UM-212-A-02) | https://growatt.tech/wp-content/uploads/2023/02/MOD-3-15KTL3-X-User-Manual-EN-202201.pdf | 2026-09-26 |
| `growatt-spf-6000es-plus.json` | service_manual | SPF 6000 ES PLUS User Manual EN | https://growatt.pl/wp-content/uploads/2024/06/SPF_6000_ES_Plus_User_Manual_EN_202212.pdf | 2026-09-26 |
| `growatt-wit-50-100k.json` | service_manual | WIT 50-100K Storage/Hybrid Inverter User Manual (GR-UM-299-A-02) | https://growatt.pl/wp-content/uploads/2023/11/Instrukcja-obslugi.pdf | 2026-09-26 |
| `growatt-mid-15-50ktl3-xl2.json` | service_manual | MID 33-50KTL3-X2 User Manual EN (GR-UM-247-A-01) | https://growatt.tech/wp-content/uploads/shared-files/MID-33-50KTL3-X2-User-Manual-EN-1.pdf | 2026-09-26 |
| `growatt-min-3000-11400tl-xh-us.json` | service_manual | MIN 3000-11400TL-XH-US User Manual EN | https://us.growatt.com/upload/file/MIN_3-11.4kTL-XH-US_User_Manual_EN_202501.pdf | 2026-09-26 |
| `growatt-legacy-tl-gen1-grey.json` | service_manual | Troubleshooting Guide for Growatt Inverters (Growatt New Energy Australia Pty Ltd) | https://baikaltesla.com/wp-content/uploads/2022/07/TROUBLE-SHOOTING-GUIDE.pdf | 2026-09-26 |

