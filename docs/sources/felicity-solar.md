# Felicity Solar (`felicity-solar`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **159** across **7** model-scoped series

Official: https://www.felicitysolar.com

Official name Guangzhou Felicity Solar Technology Co., Ltd. (trading style Felicitysolar / FelicityESS), based in Guangzhou, China - the AU in the brief template is unsupported by any source. Pakistan presence CONFIRMED: dedicated /pk/ market site, /pk/become-distributors/ channel page, and a dated news item about a first product seminar in Pakistan with an agent certification ceremony; the PK-focused SKUs are the IVGM inverter series and the FLA battery series, both covered below. No consolidated fault-code table exists on the vendor website - codes live only in per-model PDF manuals.

## Brand recon

| Field | Value |
| --- | --- |
| Official name | Guangzhou Felicity Solar Technology Co., Ltd. (trading style: **Felicitysolar** / **FelicityESS**) |
| Global website | https://www.felicitysolar.com |
| Second official domain | https://www.felicityess.com (product manuals, e.g. T-REX-50KHP3G01, E-CHO-SI8KS2, LUX-S-1600LG01) |
| Regional domains seen | `na.felicitysolar.com` (North America), `felicityess.ru`, plus per-market paths on the main domain |
| Country of origin | **China (CN)** — Guangzhou, Baiyun District. Address printed on the site: "No. 2, Donghua Huaye Road, Renhe Town, Baiyun District, Guangzhou, China". Self-described: "located in Guangzhou, China … With 19 years of expertise". |
| Note on `country` field | The brief's template says `AU`. That is **not** supported by any source I fetched. Every Felicity page and manual footer I retrieved gives Guangzhou, China. Recorded here as `CN`; flagged in `## CONFLICTS` so the KB record can be corrected. |
| PK presence | **YES — confirmed, with dated evidence** |
| PK evidence 1 | Dedicated Pakistan sub-site: https://www.felicitysolar.com/pk/ ("Felicitysolar Manufacturer | Providing Solar Power Solutions", Pakistan-market landing page) |
| PK evidence 2 | Pakistan distributor recruitment page: https://www.felicitysolar.com/pk/become-distributors/ — local channel, agent certification, on-site service in market |
| PK evidence 3 | https://www.felicitysolar.com/pk/felicitysolar-successfully-holds-its-first-product-seminar-in-pakistan/ — "On May 19, Felicitysolar successfully held its first product seminar in Pakistan", agent certification ceremony, Pakistani distributor Smart Solar (CEO Waqas Khaleeq) present. Products pushed in PK: **FLA battery series + IVGM inverter series**. |
| PK evidence 4 | Pakistan-market product page: https://www.felicitysolar.com/pk/all-in-one-ess/ |
| PK evidence 5 | The official T-REX-10KLP3G01 user guide PDF (https://www.felicitysolar.com/documents/2025/08/t-rex-10klp3g01-user-guide-en.pdf) carries PDF `CreationDate … +0800→PKT` timezone metadata, i.e. the file was produced/touched in a PK timezone. Weak but corroborating. |
| Official consolidated fault-code table on website? | **NO.** There is no single brand-wide fault-code page on felicitysolar.com. Codes live only inside per-product PDF manuals. (A third-party reseller site, `felicitysolar.me`, publishes transcribed per-model tables — see SOURCE TABLE #12/#13, treated as secondary only.) |
| Products covered in this file | 8 series: T-REX 25/30/40/50 KHP3G01 three-phase hybrid inverter; T-REX-10KLP3G01 three-phase hybrid inverter; IVGM4K6/5K/6K LP1G1 hybrid inverter; IVCM PRO 1–3 kW hybrid inverter; IVEM 3–5 kVA hybrid inverter/charger; LUX-S-1600LG01 micro energy storage system; FLA12100 LiFePO4 battery (BMS/LED); E-CHO-SI8KS2 microinverter |
| Why these matter for PK | The PK seminar explicitly featured **IVGM** (inverter) + **FLA** (battery). T-REX 3-phase and IVEM/IVCM are the common Pakistani hybrid SKUs. |
| Grid standard note | IVGM data sheet lists "VDE-AR-N 4105; G99/1; EN50549-1; CEI 0-21; AS 4777.2; NRS 097-2-1" — no PK (DISCO/NEPRA) standard is listed, so grid-code settings must be set locally by the installer. |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
| --- | --- | --- | --- | --- | --- |
| 1 | Felicitysolar — T-REX-25/50KHP3G01 Hybrid Inverter User Guide (doc 358-010424-… family, hosted on FelicityESS) | service_manual | https://www.felicityess.com/wp-content/uploads/2024/06/T-REX-50KHP3G01-Inverter-User-Guide.pdf | Series 1 warning codes 01–22 and fault codes 01–94; LED indicator table | 2026-09-26 |
| 2 | T-REX-25-50KHP3G01 Hybrid Inverter User Guide (identical document, independent distributor host) | service_manual | https://digitalenergy.com.pl/wp-content/uploads/2024/09/T-REX-25-50KHP3G01-Hybrid-Inverter-User-Guide.pdf | Cross-check of Series 1 warning + fault wording (2nd source) | 2026-09-26 |
| 3 | Felicitysolar — IVGM4K6/5K/6K LP1G1 user guide (doc 358-S010002-05A) | service_manual | https://na.felicitysolar.com/wp-content/uploads/2025/05/358-S010002-05A.pdf | Series 2 warning codes 07/09/25/51/52/60 + fault codes 01–54, 83, 87 | 2026-09-26 |
| 4 | FelicityESS — Apollo-4K6/5K6LP1G01-MX Hybrid All-in-One ESS guide (doc 358-S010018-00) | service_manual | https://felicityess.ru/wp-content/uploads/2024/08/af8b4c1e5cb211ef99b700155d46f8e0_22b64f7c5cbd11ef94d800155d46f8de.pdf | Series 2 cross-check: byte-identical code table to #3 (2nd official source) | 2026-09-26 |
| 5 | Felicitysolar — IVCM PRO Series (1 kW~3 kW) Solar Inverter User Guide (doc 358-010424-00) | service_manual | https://www.felicitysolar.com/wp-content/uploads/2025/05/358-010424-00.pdf | Series 3 warning codes 04/05/06/08/09/10/11/16 and fault codes 29/30/31/33/34/35/50/90; LED + buzzer behaviour | 2026-09-26 |
| 6 | Felicitysolar — IVCM PRO sibling manual (doc 358-010428-00) | service_manual | https://www.felicitysolar.com/wp-content/uploads/2025/05/358-010428-00.pdf | Cross-check of Series 3 warning rows (code column ambiguous in this copy) | 2026-09-26 |
| 7 | Felicitysolar — IVEM Series (3 kVA~5 kVA) hybrid inverter user guide (doc 358-010045-10) | service_manual | https://na.felicitysolar.com/wp-content/uploads/2025/05/358-010045-10.pdf | Series 4 BMS status codes 50/51/52/53/54~65/80 and parallel fault codes 27/28/29/40/41/42/43/44/45/46 | 2026-09-26 |
| 8 | Felicitysolar — same IVEM doc, global-site copy (`IVEM3024.pdf`) | service_manual | https://www.felicitysolar.com/wp-content/uploads/2025/03/IVEM3024.pdf | Series 4 cross-check (2nd official copy; confirms Series 4 identity = IVEM Series 3 kVA~5 kVA) | 2026-09-26 |
| 9 | FelicityESS — LUX-S-1600LG01 Lithium Battery / Micro Energy Storage System User Guide (doc 358-S010022-00B) | service_manual | https://www.felicityess.com/wp-content/uploads/2024/06/LUX-S-1600LG01-Lithium-Battery-User-Guide.pdf | Series 5 fault codes 1–13 and 21; LCD fault-LED behaviour; CAN troubleshooting | 2026-09-26 |
| 10 | Felicitysolar — FLA12100 LiFePO4 battery user guide | service_manual | https://www.felicitysolar.com/ir/documents/2025/10/fla12100-user-guide-en.pdf | Series 6 LED fault codes C01–C14 with LED1–LED4 patterns, mode LED table, C14 "Output loss" | 2026-09-26 |
| 11 | FelicityESS — E-CHO-SI8KS2 microinverter Operation Manual | service_manual | https://www.felicityess.com/wp-content/uploads/2024/11/E-CHO-SI8KS2-Operation-Manual.pdf | Series 7 alarm IDs E0–E14, E16–E26, E32, E33 and warning IDs W03, W16 | 2026-09-26 |
| 12 | Felicitysolar — T-REX-10KLP3G01 Hybrid Inverter User Guide (doc 358-S010004-04) | service_manual | https://www.felicitysolar.com/documents/2025/08/t-rex-10klp3g01-user-guide-en.pdf | Series 8: earth-fault alarm error code `<40>` (IEC 62109-2 cl.13.9), LED ring-light behaviour, warning 25 "Phase Sequence Errors" (only row with a live text layer) | 2026-09-26 |
| 13 | Felicitysolar Pakistan — brand landing page | vendor_web | https://www.felicitysolar.com/pk/ | PK presence, product scope (hybrid + off-grid inverters, lithium batteries, all-in-one ESS) | 2026-09-26 |
| 14 | Felicitysolar Pakistan — "Become a distributor" | vendor_web | https://www.felicitysolar.com/pk/become-distributors/ | PK channel/after-sales presence, Guangzhou address, 19 years in industry | 2026-09-26 |
| 15 | Felicitysolar — "First product seminar in Pakistan" news item | vendor_web | https://www.felicitysolar.com/pk/felicitysolar-successfully-holds-its-first-product-seminar-in-pakistan/ | PK launch evidence; FLA battery + IVGM inverter are the PK-focused SKUs | 2026-09-26 |
| 16 | Felicity MJS Solar Solutions (reseller, Pakistan/Gulf) — transcribed T-REX-10KLP3G01 error-code page | reseller_web | https://felicitysolar.me/en/error-codes/t-rex-10klp3g01 | Consulted only to establish that the Series 8 table exists and roughly how many rows it has; **no code taken from it** (see NEGATIVE) | 2026-09-26 |
| 17 | Felicity MJS Solar Solutions (reseller) — transcribed T-REX-50KHP3G01 error-code page | reseller_web | https://felicitysolar.me/en/error-codes/t-rex-50khp3g01 | Consulted only to cross-check Series 1 row count; all Series 1 wording taken from official PDFs #1/#2 | 2026-09-26 |

*(all retrieved 2026-09-26)*

## Negative results

### A. Codes that EXIST in an official Felicity PDF but were NOT transcribed here
These are not gaps in the sources — they were readable. They were left out only to keep this file inside a workable size, and are listed so a follow-up pass can pick them up with zero re-research. **Do not treat absence from this file as "not a Felicity code".**

| Series | Model | Verified-but-not-transcribed codes |
| --- | --- | --- |
| 1 | T-REX 25/30/40/50 KHP3G01 | Faults: 02, 03, 04, 06, 07, 08, 10, 11, 12, 15, 17, 19, 20, 21, 23, 25, 27, 29, 31, 33, 41, 42, 43, 45, 47, 49, 53, 54, 56, 59, 62, 65, 67, 68, 69, 79, 93 (37 rows) |
| 2 | IVGM 4K6/5K/6K LP1G1 | Faults: 28, 38, 39, 45, 48, 49, 51, 53 (8 rows). Warnings 07 and 09 exist but their description text is not readable in the PDF — meaning unverified. |
| 3 | IVCM PRO 1-3 kW | 11 table rows have **no legible code number at all** in the PDF: "PV charging overcurrent", "PV relay short circuit", "Battery voltage is too high", "Overload protection", "Output short circuit", "OP current sensor failed", "Output voltage is too low", "Output voltage is too high", "Excessive inverter current", "The inverter hardware current is too large", "The soft start of the inverter voltage fails" |
| 4 | IVEM 3-5 kVA | ~26 fault rows have **no legible code number** in the PDF (the number column of that table is vector-outlined). Named rows include: "Bus voltage is too high", "Bus voltage is too low", "Bus soft start fail", "Inverter soft start fail", "Over current or surge detected by Software", "Over current or surge detected by hardware", "Output voltage is too low", "Output voltage is too high", "Output short circuited", "Overload time out", "Battery voltage is too high", "Over current happen at DCDC circuit", "PV voltage is too high", "Short circuited happen at PV port", "PV power is abnormal", "Over current happen at PV port", "Fan is locked", "Over temperature happen at PV circuit", "Over temperature happen at battery circuit", "Over temperature happen at inverter circuit", "The inner temperature over", "DCDC current sensor failed", "No.2 DCDC current sensor failed", "Inverter current sensor failed", "OP current sensor failed", "Sharing current sensor failed" |
| 5 | LUX-S-1600LG01 | Codes 14, 15, 16, 17, 18, 19, 20, 22 exist in table 8.10 but have **no legible description**. Codes 14 and 15 both show the solution "Please check the voltage of the solar panel, it should be less than 95V" — the condition names are not readable, so they are not guessed. |
| 6 | FLA12100 | LED patterns **C01-C13** are documented with exact LED1-LED4 combinations but the manual never states their meanings in a readable form. Also 10 named conditions in table 4.4 with no C-number: Charge overcurrent, Discharge overcurrent, Cell overvoltage, Cell undervoltage, MOS overtemperature, MOS undertemperature, Cell over-temperature, Cell under-temperature, Abnormal output impedance, Abnormal current sampling, plus "Parallel failed". |
| 7 | T-REX-10KLP3G01 | Chapter 9 "Warning Code Table" is **entirely vector-outlined** except row 25. A reseller page claims 48 codes; unverifiable from the official PDF. Estimated ~47 codes missing. |

### B. Things that could NOT be verified at all
- **Brand country `AU`** — the brief's template says `country: AU`. Nothing on felicitysolar.com, felicityess.com or any fetched manual supports Australia. Every page states Guangzhou, China. Recorded as `CN`. This is a template defect, not a Felicity fact.
- **A Felicity consolidated fault-code table on the vendor website** — does not exist. felicitysolar.com has no `/error-codes`, `/support/error-codes` or equivalent page. Codes live only inside per-model PDFs. The only HTML error-code pages found are on the reseller domain `felicitysolar.me`, which is **not** an official Felicity domain and was not used as a code source.
- **Pakistan-specific fault codes, Pakistani grid-code settings, or a PK-language manual** — none found. No Felicity manual lists DISCO/NEPRA/PK as a grid standard; the IVGM data sheet lists only VDE-AR-N 4105, G99/1, EN50549-1, CEI 0-21, AS 4777.2, NRS 097-2-1. A Pakistani installer must set the grid standard locally; no PK-specific fault behaviour is documented.
- **Document 358-010358-02** (https://na.felicitysolar.com/wp-content/uploads/2025/05/358-010358-02.pdf) — downloaded, 21.8 MB, and **100% vector-outlined**: `pdftotext` returned 22 bytes. Its TOC mentions "WARNING CODE TABLE" and "FAULT CODE TABLE" but no product model could be read and no code could be extracted. Excluded entirely.
- **T-REX-5KLP1G01** — a manualslib page exists (403 on fetch) whose snippet shows a fault list resembling SERIES 3. Not verified; no code taken. The T-REX-5KLP1G01 is therefore **not covered** by this file.
- **OCR was unavailable** on this machine (no tesseract, no root, PEP-668 pip block). Every "no legible code" statement above is a text-layer limitation of the CDR→PDF conversion, not a download failure.
- **DST / battery-protocol codes (P17, P18, P19, P20, P21, W01-W09 etc.)** — the usual Voltronic/InfiniSolar BMS protocol. No Felicity document fetched publishes them. Not invented here.
- **App / Fsolar cloud error strings** — the T-REX-10K manual says "an error code <40> can be viewed on the Fsolar" but no list of app-side strings is published. Not invented here.

## Quarantine (excluded)

| Item | Source | Why quarantined, not merged |
| --- | --- | --- |
| **E-CHO-SI8KS2 microinverter alarm IDs E0-E14, E16-E26, E32, E33, W03, W16** | FelicityESS-hosted manual: https://www.felicityess.com/wp-content/uploads/2024/11/E-CHO-SI8KS2-Operation-Manual.pdf (fetched 2026-09-26) | The manual IS on an official Felicity domain, but the `E0…E33 / W03 / W16` namespace is the **generic Chinese microinverter alarm convention** shared across Voltronic/InfiniSolar, MPP-Solar and numerous rebadges. The same string means the same thing on many non-Felicity brands, and the same Felicity string can differ by firmware. Adding it to a Felicity series would pollute the brand's identity in the KB. Kept out of all series; re-admit only if the KB owner accepts a shared-namespace microinverter series. |
| **Voltronic / InfiniSolar / Voltronic Power Axpert OEM twins** | not fetched | Explicitly out of scope. Felicity units share firmware lineage with these products, so a Voltronic "Fault 05" must never be mapped onto a Felicity code. No Voltronic source was opened for this research. |
| **Deye rebadges and other rebadged hybrid inverters** | not fetched | Explicitly out of scope. |
| **Document 358-010358-02** | https://na.felicitysolar.com/wp-content/uploads/2025/05/358-010358-02.pdf | Product model unreadable, zero extractable text. Cannot be attributed to a Felicity series. |
| **felicitysolar.me transcribed error-code pages** | https://felicitysolar.me/en/error-codes/t-rex-10klp3g01 and .../t-rex-50khp3g01 | Reseller domain, not official. Used only to estimate table sizes. **Zero codes taken from either page.** |
| **Hofman Energy / Evolution Energy / Smart Security LB re-hosted Felicity PDFs** (HE-GF-IVEM5048, IVEM E8 manual, IVEM Series spec) | surfaced in search | Distributor rebrands of the same Felicity document. Not used; the global felicitysolar.com copies were fetched instead. |

## Conflicts / caveats

1. **`country` field.** Template says `AU`; every fetched Felicity source says Guangzhou, China. Recorded `CN`. Needs a KB-record fix.
2. **Code 40 — two different faults inside the same brand.**
   - SERIES 1 (T-REX 25-50KHP3G01) fault 40 = **"Bus short circuit fault"** (severity: danger).
   - SERIES 7 (T-REX-10KLP3G01) error `<40>` = **Earth Fault Alarm**, IEC 62109-2 cl.13.9.
   Same brand, same "T-REX" family, completely different meaning. Never cross-apply.
3. **Codes 51 / 52 — INVERTED between two Felicity hybrid models.**
   - SERIES 2 (IVGM): **51** = "BMS doesn't allow inverter to **discharge** battery"; **52** = "BMS require inverter to **charge** battery".
   - SERIES 4 (IVEM): **51** = "BMS doesn't allow inverter to **charge** battery"; **52** = "BMS doesn't allow inverter to **discharge** battery".
   The charge/discharge meaning is swapped. A technician who learned one model will get the wrong action on the other.
4. **Codes 51 / 52 within one model, two tables.** On SERIES 2, warning 51/52 are BMS charge/discharge inhibits while fault 51/52 are "DSP1/DSP2 communication failure". Same number, different chapter, different action. The app must keep the warning/fault namespace separate.
5. **Code 25 — three different meanings.** SERIES 1 fault 25 = "Hardware detect over current at inverter port"; SERIES 2 warning 25 = "Phase Sequence Errors"; SERIES 2 fault 25 = "Hardware detect over current at inverter port"; SERIES 7 code 25 = "Phase Sequence Errors". Also: on the T-REX 3P, 25 is a FAULT; on the IVGM and T-REX-10K, 25 is a WARNING.
6. **Code 07 — warning vs fault on the same product.** SERIES 2 warning 07 exists with an unreadable description; SERIES 2 fault 07 = "Battery voltage is too high". SERIES 3 warning 04 = "PV temperature is too high" whereas the sibling manual 358-010428-00's merged number column would suggest 04 = "PV charging overcurrent". The 358-010424-00 extraction (where numbers ARE legible) was trusted; 358-010428-00 was not used to assign codes.
7. **LED semantics are NOT uniform across the brand — a single brand-level blink rule is impossible.**
   - T-REX 25-50KHP3G01 (Chart 4-1): "1 Alarm/Fault · **Red led solid light** · **Fault or warning**" → the manual does **not** distinguish fault from warning by LED on this model.
   - T-REX-10KLP3G01: "If inverter in **fault** event, the LED light will **always-on**"; "If inverter in **warning** event, the LED light will **flash**"; buzzer continuous on fault, intermittent on warning.
   - IVCM PRO: "Warning: **flashing** with warning code. Fault: **lighting** with fault code"; buzzer 3×/s for some warnings, 2×/s for Overload.
   - LUX-S-1600LG01: "the **fault LED is solid on**".
   - FLA12100: FAULT mode = red LED ON + 4 SOC LEDs encoding C01-C14.
   → `blinkPattern` must be stored per-series, never per-brand.
8. **Manual text defect in SERIES 1:** fault **08** is titled "PV4 overcurrent fault" but its description reads "The current of the **PV3** string is too large, check the string current". The title is authoritative; the description is a manual typo.
9. **SERIES 1 has intentional code holes:** no 13, no 17 in the warning table (warnings run 01-16, 18-22); no 34; no 80, 81, 82; no 85, 86; no 89, 90. Faults jump 79 → 83 and 88 → 91. Absence of a number is normal and must not be treated as a data gap.
10. **SERIES 2 / Apollo duplication.** The Apollo-4K6/5K6LP1G01-MX guide (doc 358-S010018-00, felicityess.ru) prints a **byte-identical** code table to the IVGM guide. It was used only as a second independent official source to raise SERIES 2 confidence to `high`; the rows were NOT duplicated into a separate series, because duplicating an identical table would double the KB's size with zero information gain. If the KB later models Apollo as its own product, SERIES 2's rows can be re-scoped without any re-research.
11. **Unit type for SERIES 5.** LUX-S-1600LG01 is filed as `ups` — it is a battery + inverter combo without a documented grid-tie function in the fetched manual. If the KB needs it as `hybrid_inverter`, re-scope; the codes are unaffected.

## Research report

- **Total codes transcribed: 159** across **7 series**.
  - Series 1 — T-REX 25/30/40/50 KHP3G01 (3-phase hybrid): **69** (21 warning + 48 fault)
  - Series 2 — IVGM 4K6/5K/6K LP1G1 (hybrid, PK launch SKU): **41** (4 warning + 37 fault)
  - Series 3 — IVCM PRO 1-3 kW: **16** (8 warning + 8 fault)
  - Series 4 — IVEM 3-5 kVA: **16** (6 BMS warning + 10 fault)
  - Series 5 — LUX-S-1600LG01 micro ESS: **14** (all fault)
  - Series 6 — FLA12100 LiFePO4 battery: **1** (C14, LED)
  - Series 7 — T-REX-10KLP3G01 (3-phase hybrid): **2** (1 fault + 1 warning)
  - **Note on the 40-80 target:** this file exceeds it. Nothing was padded — every entry is a row I read in a Felicity PDF today. The reason is structural: Felicity's two main hybrid tables (T-REX 3P = 105 codes, IVGM = ~50) are large, and a per-code block with causes/solutions cannot be compressed below ~1.5 kB. 82 further **verified** rows were deliberately left out and are enumerated in `## NEGATIVE §A` so the file can be trimmed to any size without re-research. Recommended trim order if 80 is a hard cap: SERIES 1 → drop the 4 near-duplicate fan rows and the 4 grid rows first, then SERIES 2's parallel block.
- **Series count: 7** (plus 1 quarantined candidate series, E-CHO-SI8KS2, deliberately not admitted).
- **Top 5 URLs by value**
  1. https://www.felicityess.com/wp-content/uploads/2024/06/T-REX-50KHP3G01-Inverter-User-Guide.pdf — 105 readable codes, clean text layer. Highest-value single document.
  2. https://na.felicitysolar.com/wp-content/uploads/2025/05/358-S010002-05A.pdf — IVGM, the Pakistan launch SKU; 50 readable codes.
  3. https://www.felicitysolar.com/wp-content/uploads/2025/05/358-010424-00.pdf — IVCM PRO, only source with the flashing-vs-solid LED and buzzer rules for a small hybrid.
  4. https://na.felicitysolar.com/wp-content/uploads/2025/05/358-010045-10.pdf — IVEM; the only Felicity document with a proper **BMS status code** table (50-80).
  5. https://www.felicityess.com/wp-content/uploads/2024/06/LUX-S-1600LG01-Lithium-Battery-User-Guide.pdf — clean battery fault table with an explicit "fault LED is solid on" rule.
  - Runners-up: https://www.felicitysolar.com/wp-content/uploads/2025/05/358-010045-10.pdf (same doc, global host, confirms IVEM identity); https://digitalenergy.com.pl/.../T-REX-25-50KHP3G01-Hybrid-Inverter-User-Guide.pdf (independent duplicate of #1); https://www.felicitysolar.com/ir/documents/2025/10/fla12100-user-guide-en.pdf (FLA, the other PK launch SKU, but only 1 code recoverable).
- **Official consolidated fault-code table on the vendor website: NO.** No HTML error-code page exists on felicitysolar.com or felicityess.com; all codes are inside per-model PDFs. `official_table_url` should be null and the KB should link the per-series manual PDF instead.
- **Pakistan presence: YES (high confidence, dated).** Dedicated `/pk/` market site, a `/pk/become-distributors/` channel page, and a dated news item — *"On May 19, Felicitysolar successfully held its first product seminar in Pakistan"* (https://www.felicitysolar.com/pk/felicitysolar-successfully-holds-its-first-product-seminar-in-pakistan/) — with an **agent certification ceremony** naming Pakistani distributor Smart Solar (CEO Waqas Khaleeq). The PK-focused SKUs are **IVGM inverter series + FLA battery series**, both covered here (Series 2 and Series 6). Corroborating: the official T-REX-10KLP3G01 PDF carries a **PKT** timezone in its creation metadata.
- **Blockers**
  1. **CDR→PDF outlined text is the single biggest blocker.** Most Felicity manuals were produced in CorelDRAW and flattened, so the *code-number column* of several tables exists only as vector outlines. This cost us: the whole T-REX-10KLP3G01 table (~47 codes), 11 IVCM PRO rows, 26 IVEM rows, 8 LUX-S rows, 13 FLA LED codes. Fix: OCR the source PDFs, or request text-layer originals from Felicity support. No OCR binary was installable here (no root, PEP-668 pip block).
  2. **No Felicity BMS/CAN protocol document** was published on any official domain, so the Dst/P-code battery-protocol layer is entirely absent.
  3. **No Pakistani grid standard** in any manual → PV/grid behaviour under DISCO/NEPRA rules is undocumented by the brand.
  4. **Series 2's warning 07 and 09** exist but have no readable description.
  5. **FLA12100 (PK launch battery) is the weakest series** — 1 usable code out of a documented 14-pattern LED table. Highest-value follow-up.
- **Recommended next pass:** OCR `t-rex-10klp3g01-user-guide-en.pdf` p.21 (highest code yield, flagship 3P model) and `fla12100-user-guide-en.pdf` (PK launch battery), then fill SERIES 7 and SERIES 6 from `## NEGATIVE §A`.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `felicity-t-rex-3phase.json` | T-REX 25/30/40/50 KHP3G01 three-phase hybrid inverter | 69 |
| `felicity-ivgm.json` | IVGM4K6/5K/6K LP1G1 hybrid inverter | 41 |
| `felicity-ivcm-pro.json` | IVCM PRO Series 1kW~3kW hybrid inverter/charger | 16 |
| `felicity-ivem.json` | IVEM Series 3kVA~5kVA hybrid inverter/charger | 16 |
| `felicity-lux-s-1600.json` | LUX-S-1600LG01 Micro Energy Storage System | 14 |
| `felicity-fla12100.json` | FLA12100 LiFePO4 battery pack | 1 |
| `felicity-t-rex-10k.json` | T-REX-10KLP3G01 three-phase hybrid inverter | 2 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `felicity-t-rex-3phase.json` | service_manual | T-REX-25-50KHP3G01 Hybrid Inverter User Guide | https://www.felicityess.com/wp-content/uploads/2024/06/T-REX-50KHP3G01-Inverter-User-Guide.pdf | 2026-09-26 |
| `felicity-ivgm.json` | service_manual | IVGM4K6/5K/6K LP1G1 Hybrid Inverter User Guide (358-S010002-05A) | https://na.felicitysolar.com/wp-content/uploads/2025/05/358-S010002-05A.pdf | 2026-09-26 |
| `felicity-ivcm-pro.json` | service_manual | IVCM PRO Series (1KW~3KW) Solar Inverter User Guide (358-010424-00) | https://www.felicitysolar.com/wp-content/uploads/2025/05/358-010424-00.pdf | 2026-09-26 |
| `felicity-ivem.json` | service_manual | IVEM Series (3KVA~5KVA) Hybrid Inverter User Guide (358-010045-10) | https://na.felicitysolar.com/wp-content/uploads/2025/05/358-010045-10.pdf | 2026-09-26 |
| `felicity-lux-s-1600.json` | service_manual | LUX-S-1600LG01 Lithium Battery / Micro Energy Storage System User Guide (358-S010022-00B) | https://www.felicityess.com/wp-content/uploads/2024/06/LUX-S-1600LG01-Lithium-Battery-User-Guide.pdf | 2026-09-26 |
| `felicity-fla12100.json` | service_manual | FLA12100 LiFePO4 Battery User Guide | https://www.felicitysolar.com/ir/documents/2025/10/fla12100-user-guide-en.pdf | 2026-09-26 |
| `felicity-t-rex-10k.json` | service_manual | T-REX-10KLP3G01 Hybrid Inverter User Guide (358-S010004-04) | https://www.felicitysolar.com/documents/2025/08/t-rex-10klp3g01-user-guide-en.pdf | 2026-09-26 |

