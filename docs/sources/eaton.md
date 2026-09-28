# Eaton (`eaton`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **94** across **7** model-scoped series

Official: https://www.eaton.com

US/Finnish power-quality maker; no eaton.com/pk country site, but Eaton UPS is actively sold and serviced in Pakistan through authorised distributors (Greaves Pakistan, BroadPeak International) and IT retailers such as Toprated Lahore.

## Brand recon

- **Official name:** Eaton Corporation plc (power quality / backup power division publishes manuals under "Eaton" / "Eaton Power Quality Oy"). Legal entity: Eaton Corporation plc, Irish plc, HQ Dublin, Ireland; US operating HQ Cleveland, Ohio.
- **Website:** https://www.eaton.com (global). Country sites use `https://www.eaton.com/<cc>/...` (e.g. `/us/`, `/gb/`, `/ae/`).
- **Country of origin for the UPS business:** USA (Cleveland, Ohio) and Finland (Eaton Power Quality Oy, Finland) — the numeric A/F fault-code system comes from the Finnish/European 9E/93E/93PM platform software.
- **PK presence: YES (channel/distributor, no eaton.com country site).**
  - No `eaton.com/pk` country site exists (both `https://www.eaton.com/pk/en-gb.html` and `https://www.eaton.com/pk/en-gb/catalog/backup-power-ups-surge-it-power-distribution.html` returned no content on 2026-09-26).
  - Evidence of active Pakistan market presence:
    - Greaves Pakistan (Pvt) Ltd — "EATON UPS SERVICES" page, https://gfg.com.pk/gppl/our-services/ups/ (Karachi HQ + Islamabad/Lahore/Peshawar/Faisalabad/Multan offices). Eaton's own LinkedIn announcement (2023-04-17) states "Greaves Pakistan is an Authorized Distributor of Eaton Power Quality Products in Pakistan" and records 93E UPS commissioning at Frontier Constabulary FC Hospital Peshawar — https://www.linkedin.com/posts/ahmed-raza-naqvi-03534636_eaton-activity-7053667574403710976-ZHhx
    - BroadPeak International FZE LLC appointed official Eaton distributor in Pakistan (2026-04) — https://www.linkedin.com/posts/3218462227_broadpeakinternational-eaton-distributionpartnership-activity-7447525453919358977-51u-
    - Greaves Pakistan promoting Eaton 93T UPS in Pakistan (2026-02) — https://www.linkedin.com/posts/greaves-pakistan_eatonups-greavespakistan-powerprotection-activity-7431973299741630464-recz
    - Local retail channel: Toprated (Pvt) Ltd, Lahore — "Eaton 9PX and 93PM UPS systems", Eaton 9PX 6000i listed with nationwide PK delivery — https://toprated.pk/products/brand/eaton and https://toprated.pk/products/eaton-9px-6000
- **Official consolidated fault-code table on eaton.com: NO.** Eaton publishes fault tables *inside per-model user manuals* only. There is no single brand-level "Eaton fault code list" page. Eaton's support site does have a "Problem or Alarm Code" lookup field (https://www.eaton.com/us/en-us/support/backup-power-ups-surge-it-power-distribution/post-sale-customer-support.html) but it routes to per-model documents, not a code table.
- **Product families covered in this file:**
  1. 9E 6–20 kVA (online, numeric A/F codes)
  2. 9E 1–3 kVA (online, numeric A/F codes — *different* set from 6–20 kVA)
  3. 9PX / 9PX Gen2 700 VA–11 kVA (online, LCD condition names + LED/beep table)
  4. 5P 650–1550 i (line-interactive, LCD status table)
  5. 5P Gen2 rack/tower (line-interactive, LED + beep table)
  6. 93E 15–80 kVA (online 3-phase, MIMIC status-indicator LED table)
  7. Ellipse PRO 650–1600 VA (line-interactive, LCD icon / troubleshooting table)

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Eaton 9E UPS 6–20 kVA — "Eaton 9E UPS - Installation and user manual" (§4.4 Trouble shooting, §4.5 Alarm codes) | user_manual | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton_9e_ups/Eaton%209E%20UPS%20-%20Installation%20and%20user%20manual.pdf | Full alarm code table (A007…AC20) and fault code table (F002…F811) + A900/A004/F004/F805 action rows | 2026-09-26 |
| 2 | Eaton 9E 1kVA–3kVA UPS / 9E EBM — "Advance user guide" (§7 Troubleshooting / Alarm codes) | user_manual | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton_9e_ups/eaton-9e-1kva-3kva-ups-eaton-9e-ebm-advance-user-guide.pdf | Separate alarm table (A107,A612,A502,A604,A60D,A80E,A810) + two fault tables incl. F007/F500/F308/F806/F208/F613/F816 | 2026-09-26 |
| 3 | Eaton 9PX 3–6k / 8k / 11k UPS & EBM — Installation and user manual (§8.1 Typical alarms and faults) | user_manual | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton-9px-ups/user-guides/eaton-9px-31-6k,8k,11k-userguides-en.pdf | 11 LCD conditions with LED on/off + beep cadence (Battery mode, Battery low, No battery, Battery fault, short runtime, Bypass mode, Power overload, UPS overtemperature, UPS does not start, Input/Output bad wiring, MBP disconnected) | 2026-09-26 |
| 4 | Eaton 9PX Gen2 5–11 kVA — Advanced user manual (§8.1 Typical alarms and faults) | user_manual | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton-9px-gen2-emea/9px-gen2-resources-emea/eaton-9px-5-11-kva-gen2-advanced-user-manual-en-us.pdf | Gen2 variant of the 9PX table (corroborates rows; overtemperature beep differs: continuous vs 3 s) | 2026-09-26 |
| 5 | Eaton 5P UPS — Installation and user manual 620-00082-02-i (§5.1 Troubleshooting) | user_manual | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton-5p-ups/user-guides/eaton-5p-ups-installation-and-user-manual.pdf | 5-row operation-status table: Batteries disconnected, Overload, End of battery life, Event (e.g. Remote Power OFF), UPS fault | 2026-09-26 |
| 6 | Eaton 5P Gen2 UPS — rack models user manual DSD-5P2208 (§8.1 Typical alarms and faults) | user_manual | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/5P-Gen2-UPS---EMEA/eaton-5p-gen2-ups-emea-resources/eaton-5pgen2-rack-user-manual-en-us.pdf | 11-row LED/beep table for 5P Gen2 (Battery mode, Battery low, No battery, Battery fault, short runtime, Power overload, UPS overtemperature, UPS does not start / RPO) | 2026-09-26 |
| 7 | Eaton 93E UPS 15–80 kVA (380/400/415 V) — Installation and Operation Manual 614-01975-00 (Table 6-1 Status Indicators, Table 6-5 Typical System Status Messages, §6.2.2 System Events) | user_manual | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/au-products/eaton-93e/Eaton_93E_15_80_kVA_Manual_Rev_1_0.pdf | 93E MIMIC status-indicator LED table (Normal/Battery/Bypass/Alarm) + horn behaviour + typical status messages | 2026-09-26 |
| 8 | Greaves Pakistan (Pvt) Ltd — "EATON UPS SERVICES" | distributor_page | https://gfg.com.pk/gppl/our-services/ups/ | PK presence / authorised distributor evidence (after-sales, commissioning, AMCs) | 2026-09-26 |
| 9 | Toprated (Pvt) Ltd Lahore — Eaton brand page (9PX / 93PM UPS) | retailer_page | https://toprated.pk/products/brand/eaton | PK presence for 9PX and 93PM, nationwide delivery | 2026-09-26 |
| 10 | GDF Technologies (Canada) — "Eaton UPS Alarm & Fault Codes: Troubleshooting Guide" | third_party_guide | https://www.gdftech.com/en/eaton-ups-alarm-fault-codes-troubleshooting-guide/ | Cross-check only. Contains code→meaning mappings that **contradict** the official Eaton manuals (see CONFLICTS). No code accepted from here. | 2026-09-26 |

*(Series blocks appended below as each product family is finished.)*

## Negative results

Things looked for that could NOT be verified from an Eaton source fetched on 2026-09-26. Nothing below was turned into an entry.

**Brand-level / infrastructure gaps**
1. **No consolidated Eaton fault-code table exists on eaton.com.** Eaton publishes fault tables only inside per-model user manuals. The support site's "Problem or Alarm Code" box (https://www.eaton.com/us/en-us/support/backup-power-ups-surge-it-power-distribution/post-sale-customer-support.html) routes to per-model documents; it is not a code table. → official table: **NO**.
2. **No `eaton.com/pk` country site.** `https://www.eaton.com/pk/en-gb.html` and `https://www.eaton.com/pk/en-gb/catalog/backup-power-ups-surge-it-power-distribution.html` both returned no content. All PK evidence is third-party channel (distributor/retailer), not Eaton-published.
3. **No PK-specific model list, manual or local code differences found.** No evidence that Eaton ships a different code set into Pakistan.

**Per-family gaps (documented tables are incomplete)**
4. **9PX / 9PX Gen2:** the manual gives named LCD conditions only. The actual **"Fault: xx" event-log strings** stored in the front-panel Fault log are NOT printed anywhere in the manual — the manual only gives "Out. short circuit" as an illustrative example. The full list of event/fault strings could not be obtained and was **not** invented.
5. **9PX: no LED colours published.** The manual says "LED is On" / "Beep continuous" but never says which LED or which colour. `blinkPattern` was written from the beep cadence only; no colour was invented except the one place the manual states a fault is signalled by a **red** LED ("Faults are announced by a continuous beep and red LED").
6. **5P gen 1 (650–1550 i):** no LED colours and no beeper cadences are published — only a 5-row text table. No blink patterns could be given.
7. **5P Gen2 tower manual has no fault table at all.** The ANZ tower manual (eaton-5pgen2-tower-user-manual-en-gb-anz.pdf) section 8 contains only "Silencing the alarm" and "Service and support" — no "Typical alarms and faults" table. The rack manual (used here) is the only Gen2 table found. → 5P Gen2 **tower** units are a coverage gap.
8. **93E 15–80 kVA: no per-alarm message list.** The MIMIC Events screen text and the History Log messages are documented only as four *categories* ("alarms, notices, status, and commands"). The actual per-alarm strings for 93E/93PM/93PS live in the Network-M2 / NMC alarm-log chapter (eaton-network-m2-user-guide-547) which is UPS-model-agnostic and was **not** verified per-model, so no code was taken from it.
9. **93E 300–500 kVA** manual was downloaded and checked: Table 6-1 is word-for-word the same as the 15–80 kVA manual. It adds **no** new codes. Not made a separate series (would be padding).
10. **Ellipse PRO: zero numeric fault codes** in the manual. No blink pattern is given for LCD icon 12 beyond "audible alarm beeps continuously". Icons 10 (AVR mode) and 15–17 (measurements) are labelled in the figure but have no fault interpretation and were not made up.
11. **9E 6–20 kVA: codes A004 and A900 appear only in the §4.4 troubleshooting table**, not in the §4.5 code table. The manual gives them no description text of their own (A004's meaning is only implied by the row "THE TEMPERATURE INSIDE THE UPS IS TOO HIGH"). A004 is therefore `confidence: medium`. A900 is documented by its action text ("the maintenance bypass function is active") so it is `high`.
12. **9E 6–20 kVA: the F004 fault is listed only in the §4.5 fault table**, while §4.4 pairs it with alarm A004 — so "A004 F004" as a *pair* is the manual's own grouping, not two separate documented alarm/fault entries.

**Families deliberately NOT researched (out of the brief's named scope, no codes taken)**
13. **5PX (non-Gen2)** — no dedicated fault table verified. 5P Gen2 was verified instead. Real coverage gap for the older 5PX 1000/1500/2200/3000.
14. **9SX** — the 9PX manual says 9PX and 9SX share the platform, but no 9SX manual was fetched and no 9SX codes were assumed.
15. **93PM, 93PS, 91PS, 9355, 9395, Power Xpert 9395P, 9130/9155, BladeUPS, E20/E30, Powerware legacy** — not researched; no codes taken. The A/F letter scheme seen in the 9E manuals is *not* assumed to extend to these.
16. **Eaton Matrix standalone inverter and Telecom Inverter System** — search surfaced official Eaton alarm-code tables for these, but they are **inverter** families, not the UPS families in scope for this KB, so nothing was ingested.

## Quarantine (excluded)

Non-Eaton or non-authoritative material encountered during research. **No code below was ingested into any SERIES block.**

| Item | URL | Why quarantined |
|------|-----|-----------------|
| GDF Technologies — "Eaton UPS Alarm & Fault Codes: Troubleshooting Guide 2026" | https://www.gdftech.com/en/eaton-ups-alarm-fault-codes-troubleshooting-guide/ | Unofficial third-party (Canadian service company). Subject is Eaton, but the F-code→meaning mapping **contradicts** the official Eaton 9E manual on 6 of 13 codes. See CONFLICTS §4. Nothing accepted. |
| Scribd — "Eaton UPS Alarm and Fault Codes Guide" (doc 658688743) | https://www.scribd.com/document/658688743/Error-Code-Description-UPS-EATON | Paywalled rehost of the same content as the official Eaton 9E manual. Not a source. |
| Manual mirrors: manualslib.com, manualspro.net, easymanua.ls, usersmanualguide.com, manuzoid.com, avsmanual-style aggregators | (various) | Third-party rehosts. Where their text matched, the **official Eaton PDF was downloaded instead** and used. Zero codes taken from mirrors. |
| notice-facile.com — ELLIPSE PRO 650/850/1200/1600 page | https://www.notice-facile.com/en/manual/14826/eaton+ellipse-pro-1200-fr-_f | Contained a clean rendering of the Ellipse PRO fault table. **Rejected as a source**; the official Eaton multi-language manual was downloaded from eaton.com instead and the entries built from it. |
| usersmanualguide.com / manuzoid.com — Eaton 93E user manual | (various) | Rehosts of the Eaton 93E manual. Official Eaton PDF used instead. |
| ups-info.ru (RU), iqrc.pl (PL), lcms.cz (CS) — Eaton 9E manuals | https://www.ups-info.ru/upload/iblock/047/04755a2989c58261147d0cb82147f505.pdf · https://iqrc.pl/templates/images/files/10701/1709066845-eaton-9e-6-20kva-usermanual-pl.pdf · https://lcms.cz/labrulez-bucket-strapi-h3hsga3/EMEA_PQ_9_E6k_VA_20k_VA_Manual_CS_CZ_64764d59db.pdf | **Same-brand** (Eaton) foreign-language translations. Fetched only to confirm the A/F code table is not a translation artefact. No code taken from them. |
| APC / Schneider Electric codes | — | **Never retrieved.** No APC/Schneider code was fetched, read or copied at any point in this research. |
| CyberPower codes | — | **Never retrieved.** |
| Kstar codes | — | **Never retrieved.** |
| Numeric (Numeric UPS) codes | — | **Never retrieved.** |
| Caterpillar MMU/SEBU UPS status messages | https://avsmanual.com/i.caterpillar/282124-mmu-master-messages-ups-120-ups-150-ups-250-ups-300-ups-301/ | Different brand entirely (Caterpillar). Appeared in search for "Eaton UPS messages". Explicitly excluded. |
| Eaton G3 ePDU six-digit OS/ePDU alarm numbers (03073, 201100, 205100 …) | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/au-products/eaton-g3-managed-epdu/Final_0278_R1_G3_ePDU_TS_Manual.pdf | Genuine Eaton document, but this is a **PDU / rack power**, not a UPS. Numeric scheme is unrelated to the UPS A/F codes. Excluded from all UPS series. |
| Eaton Matrix / Telecom Inverter System alarm tables | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/eaton-matrix-standalone-inverter-manual-c117-en.pdf · https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/eaton-telecom-inverter-system-installation-and-operation-guide-en.pdf | Eaton, but **inverter** products (DC/AC telecom inverter), not UPS. Out of the `ups` category scope. |

## Conflicts / caveats

**1. A80E / A810 semantics are swapped between the two 9E manuals. This is the most dangerous conflict in this file.**
- 9E 6–20 kVA manual: `A80E` = "Overload: load > 105%"; `A810` = "Load percentage greater than the user threshold set".
- 9E 1–3 kVA manual: `A80E` = "Overload Prealarm"; `A810` = "Overload alarm (load percentage > 105%)".
- So the code that means **">105% of rated"** is **A80E** on a 9E 6–20 kVA and **A810** on a 9E 1–3 kVA. Resolution: both entries are scoped to their own series and reproduced exactly as printed. **Never match a 9E code without first confirming the kVA rating.**

**2. Same fault number, different wording across the two 9E manuals (scope by kVA, do not merge).**
| Code | 9E 6–20 kVA | 9E 1–3 kVA |
|------|-------------|-----------|
| A107 | Cable connection error | Cable connection error (Input Line and Neutral inversion) |
| A60D | Batteries missing or Battery Box missing or not connected | Battery not connected |
| F004 | Dissipator over temperature | **listed twice**: "Inner temperature high" (minor) and "UPS Over temperature fault" (major) |
| F300 / F301 | Capacitor bank overvoltage | DC Bus too high |
| F302 / F303 | Capacitor bank undervoltage | DC Bus too low |
| F304 | Unbalanced capacitor bank | DC Bus unbalanced |
| F305 | Failed capacitor bank soft start | DC Bus Failure on soft start |

**3. Codes present on only one 9E size.**
- Only on 6–20 kVA: A007, A10A, A806, A802, AC20, A900, A004, F002, F811.
- Only on 1–3 kVA: A502, A604, A612, F007, F208, F308, F500, F613, F806, F816.
- **A806 (alarm) on 6–20 kVA vs F806 (fault) on 1–3 kVA** — the *same physical condition* (EPO active) is a non-fatal alarm on one size and a fault on the other. Scoped separately.

**4. gdftech.com's Eaton F-code table is materially wrong and was rejected.** Comparing their table against the official 9E manual:
| Code | gdftech says | Official Eaton 9E manual says |
|------|--------------|------------------------------|
| F305 | Inverter overvoltage | Failed capacitor bank soft start |
| F303 | Capacitor bank **over**voltage | Capacitor bank **under**voltage |
| F70C | Short circuit | Inverter **under**voltage (short circuit is F805) |
| F70D | Failed inverter soft start | Inverter **over**voltage (soft start is F704) |
| F808 | Inverter relay fault | Output overload fault |
| F811 | DC bus discharge failure, "do not open the chassis" | Negative output power |
Their claim that the A-code table "consolidates the codes of 9E, 93PM, 93PS" is also **not supported** by any Eaton document fetched. Their page is therefore used for nothing.

**5. 9PX vs 9PX Gen2 beeper conflict.** For "UPS overtemperature", the 9PX 3–6k/8k/11k manual says **"1 beep every 3 seconds"** while the 9PX Gen2 5–11 kVA manual says **"Beep continuous"**. Every other row matches. Resolution: one series, Gen2 difference written into the `blinkPattern` of that entry and into the series `notes`.

**6. 5P gen 1 vs 5P Gen2 threshold conflict.** Gen 1 5P overload is "greater than **105 %** of nominal"; Gen 2 5P overload is "greater than **100 %** of nominal". Also gen 1's "End of battery life" row is renamed "Battery fault" in Gen 2, and gen 2 adds LED/beeper detail that gen 1 omits. Resolution: separate series (`eaton-5p`, `eaton-5p-gen2`); never merge.

**7. 5P gen 1 lacks a fault table on Gen 2 tower models.** The Gen 2 **tower** manual has no "Typical alarms and faults" table at all, only the Gen 2 **rack** manual does. So SERIES 5's `blinkPattern` values are verified for rack models only. Flagged in NEGATIVE §7.

**8. Third-party aggregator pages agreed with the official manuals.** Where mirror sites (manualslib, manualspro, easymanua.ls, notice-facile, usersmanualguide) were seen, their A/F tables and Ellipse PRO fault rows matched the official Eaton PDFs byte-for-byte in meaning. No conflict arose, and no code was taken from the mirrors.

## Research report

**Total codes: 94 across 7 series.** This is **above the 30–70 target** and that is deliberate, not padding:
- Two of the seven series (9E 6–20 kVA = 25, 9E 1–3 kVA = 26) are the *only* Eaton small-UPS families that publish a real numeric code table, and they are 51 of the 94 codes. Dropping them would have removed the single most useful thing found.
- Four of the codes-per-pair entries (`F300`/`F301`, `F302`/`F303` in each 9E manual) are the same description on two sensing rails; splitting them is how the manual prints them, and a KB lookup needs both keys.
- The 9PX and 9PX Gen2 tables were **merged into one series** on purpose (11 identical rows, one beep discrepancy) specifically to avoid double-counting.
- The 93E 300–500 kVA manual was checked and **deliberately not** made a series (identical table, no new codes).
- No code was invented, extrapolated, or carried across brands. Everything unverifiable is in NEGATIVE.

**Series count: 7**
| # | id | Codes | Basis |
|---|----|-------|-------|
| 1 | `eaton-9e-6-20k` | 25 | numeric A/F table + troubleshooting codes |
| 2 | `eaton-9e-1-3k` | 26 | different numeric A/F table |
| 3 | `eaton-9px` | 11 | LCD conditions + LED/beep table (9PX + 9PX Gen2) |
| 4 | `eaton-5p` | 5 | gen-1 operation-status table |
| 5 | `eaton-5p-gen2` | 9 | Gen 2 LED/beep table (rack manual) |
| 6 | `eaton-93e` | 10 | MIMIC status-indicator + status-message tables |
| 7 | `eaton-ellipse-pro` | 8 | LCD icon + Problem/Diagnostic/Solution table |

**Top 5 URLs (all official Eaton, all PDFs downloaded and text-extracted on 2026-09-26)**
1. https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton_9e_ups/Eaton%209E%20UPS%20-%20Installation%20and%20user%20manual.pdf — 9E 6–20 kVA, 25 codes
2. https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton_9e_ups/eaton-9e-1kva-3kva-ups-eaton-9e-ebm-advance-user-guide.pdf — 9E 1–3 kVA, 26 codes
3. https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton-9px-ups/user-guides/eaton-9px-31-6k,8k,11k-userguides-en.pdf — 9PX, 11 codes
4. https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/au-products/eaton-93e/Eaton_93E_15_80_kVA_Manual_Rev_1_0.pdf — 93E, 10 codes
5. https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton-5p-ups/user-guides/eaton-5p-ups-installation-and-user-manual.pdf — 5P gen 1, 5 codes
*(runners-up: 5P Gen2 rack manual → 9 codes; Ellipse PRO multi-language manual → 8 codes; 9PX Gen2 → corroboration only.)*

**Blockers**
1. **No single official Eaton fault-code table to validate against.** Every entry is scoped to exactly one manual. There is no cross-check, so a code that was mis-transcribed in a manual would go undetected — mitigated only by the 9PX/9PX Gen2 cross-check and by the third-party mirrors agreeing.
2. **A80E/A810 semantic swap between 9E kVA sizes** (CONFLICTS §1) is a genuine Eaton documentation defect, not a transcription error. A Pakistani tech who reads A80E on a 3 kVA and A810 on a 10 kVA will get the wrong meaning if the model is not confirmed.
3. **Eaton's 9PX "Fault: xx" event-log strings are unpublished.** The most common field question for a 9PX ("what does Fault: xx mean?") cannot be answered from Eaton documentation. 9PX/5P/5P Gen2/93E/Ellipse all rely on named conditions because the numeric/string fault list is not in print.
4. **5P Gen2 tower models and the whole 5PX gen-1 family are uncovered** (NEGATIVE §7, §13).
5. **93E/93PM/93PS per-alarm text not obtainable** from the published manuals (NEGATIVE §8).
6. **No PK-local source at all.** No Eaton Pakistan page, no PK model list, no PK-language manual. If Eaton ships a Pakistan-specific firmware with different codes, it is invisible from here.
7. **eaton.com blocks/times out on `curl` and on some `webfetch` calls**; all PDFs had to be pulled with `wget` + `pdftotext -layout`. Layout-based text extraction duplicated lines in the 93E tables, so that table was de-duplicated by hand.

**Official consolidated fault-code table: NO.** Eaton has no brand-level code table. Seven per-model manual tables were used instead.

**Pakistan presence: YES (channel only).** No `eaton.com/pk` site exists. Evidence: Greaves Pakistan (Pvt) Ltd "EATON UPS SERVICES" — https://gfg.com.pk/gppl/our-services/ups/ (authorised distributor of Eaton Power Quality Products, with 93E commissioning at FC Hospital Peshawar per Eaton's own LinkedIn post 2023-04-17); BroadPeak International FZE LLC appointed Eaton distributor in Pakistan (2026-04); Toprated (Pvt) Ltd Lahore selling Eaton 9PX 6000i and 93PM UPS with nationwide delivery — https://toprated.pk/products/brand/eaton. In-market families for this KB: **93E/93T and 93PM (3-phase, hospital/institutional) and 9PX (server/IT)**. The small-VA families (5P, 5P Gen2, Ellipse PRO) and the 9E are residential/SME-channel and were not seen in the PK evidence found.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `eaton-9e-6-20k.json` | 9E 6-20 kVA (online UPS 6-20kVA, 3:1 and 1:1) | 25 |
| `eaton-9e-1-3k.json` | 9E 1-3 kVA (online UPS 1-3kVA + 9E EBM) | 26 |
| `eaton-9px.json` | 9PX / 9PX Gen2 (online UPS 700VA-11kVA) | 11 |
| `eaton-5p.json` | 5P 650-1550 i (line-interactive UPS, tower/rack/wall) | 5 |
| `eaton-5p-gen2.json` | 5P Gen2 (line-interactive UPS, tower and rack models 550-3000 VA class) | 9 |
| `eaton-93e.json` | 93E 15-80 kVA (3-phase online UPS, 380/400/415 V) | 10 |
| `eaton-ellipse-pro.json` | Ellipse PRO 650/850/1200/1600 VA (line-interactive UPS, AVR) | 8 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `eaton-9e-6-20k.json` | user_manual | Eaton 9E UPS - Installation and user manual (6-20 kVA) | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton_9e_ups/Eaton%209E%20UPS%20-%20Installation%20and%20user%20manual.pdf | 2026-09-26 |
| `eaton-9e-1-3k.json` | user_manual | Eaton 9E 1kVA-3kVA UPS - Eaton 9E EBM - Advance user guide | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton_9e_ups/eaton-9e-1kva-3kva-ups-eaton-9e-ebm-advance-user-guide.pdf | 2026-09-26 |
| `eaton-9px.json` | user_manual | Eaton 9PX 3-6k / 8k / 11k UPS and EBM - Installation and user manual | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton-9px-ups/user-guides/eaton-9px-31-6k,8k,11k-userguides-en.pdf | 2026-09-26 |
| `eaton-5p.json` | user_manual | Eaton 5P UPS - Installation and user manual (620-00082-02-i) | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton-5p-ups/user-guides/eaton-5p-ups-installation-and-user-manual.pdf | 2026-09-26 |
| `eaton-5p-gen2.json` | user_manual | Eaton 5P Gen2 UPS - user manual, rack models (DSD-5P2208) | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/5P-Gen2-UPS---EMEA/eaton-5p-gen2-ups-emea-resources/eaton-5pgen2-rack-user-manual-en-us.pdf | 2026-09-26 |
| `eaton-93e.json` | user_manual | Eaton 93E UPS 15-80 kVA (380/400/415 V) Installation and Operation Manual 614-01975-00 | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/au-products/eaton-93e/Eaton_93E_15_80_kVA_Manual_Rev_1_0.pdf | 2026-09-26 |
| `eaton-ellipse-pro.json` | user_manual | Eaton Ellipse PRO UPS 650/800/1200/1600 VA - Installation and user manual (multiple languages) | https://www.eaton.com/content/dam/eaton/products/backup-power-ups-surge-it-power-distribution/backup-power-ups/eaton-ellipse-pro-ups/Eaton%20Ellipse%20PRO%20UPS%20-%20650-800-1200-1600%20VA%20-%20Installation%20and%20user%20manual%20(multiple%20languages).pdf | 2026-09-26 |

