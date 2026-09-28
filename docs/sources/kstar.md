# Kstar (`kstar`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **96** across **3** model-scoped series

Official: https://www.kstar.com

Chinese maker (Shenzhen, SZSE 002518) selling online UPS plus PV inverter and ESS under KSTAR New Energy. Kstar's own news page lists it as a top UPS brand in Pakistan and recommends it as an OEM for Pakistani UPS importers, so units are common in the local channel even though Kstar has no .pk site. No Kstar UPS fault table was readable (kstar.com is WAF-blocked), so every code below is from the KSTAR New Energy solar/ESS manuals.

## Brand recon

- **Official name**: Shenzhen Kstar Science & Technology Co., Ltd. (KSTAR). Solar/ESS arm sells as **KSTAR New Energy**. "Shenzhen Kstar Science & Technology Co Ltd ... better known as KSTAR, is a global energy technology company" — https://www.kstar.com/review/284.jhtml
- **Global website**: https://www.kstar.com (en). Also https://kstarnewenergy.com for PV inverter / ESS. Mirrors: `web.kstar.com`, `kstar.com`, locale paths `/cn /fr /ar /kr /es /ua`.
- **HQ country**: China (Shenzhen). Listed SZSE 002518.
- **kstar.com.pk**: **does not resolve** (`curl` → http=000, no DNS/connect). No Pakistani Kstar subsidiary site found. Local PK presence is via importers/distributors/OEM deals, not a `.pk` domain.

### PK presence — YES (evidence)
1. https://www.kstar.com/news/info/356.html — "Top UPS Manufactures in Pakistan" (Kstar's own news page, 2021-07-19): *"Kstar is the largest ups manufacturer in China and has supplied many UPS systems for Pakistan. Kstar is well known for making UPS & Inverter which are reliable and cost-effective."* and *"If you are starting up UPS business in Pakistan, we recommended you to choose Kstar as your OEM(original equipments supplier). Kstar is famous for its online UPS, especially at small capasity UPS."*
2. https://www.kstar.com/fr/newinformation/343.jhtml — "UPS battery price in Pakistan 2020": Pakistan-facing content, PK contact `sales@kstar.com`, Kstar FM/GFM/FML/FMH + deep-cycle solar battery lines offered into the PK market.
3. Secondary (see CONFLICTS / supporting notes): Kstar's own article ranks itself inside the Pakistan UPS brand list alongside APC, Inverex, Homage, CyberPower.

### Official fault-code table on kstar.com?
- **Partly.** Kstar publishes user manuals as PDFs on its own domain (`https://www.kstar.com/bocupload/...`), e.g. `4256-7232.pdf`. **Blocker:** the whole kstar.com origin returns **HTTP 403** to non-browser clients (WAF) from this environment, so the official PDFs could not be downloaded. Confirmed by `curl -sI` → `403` and by a full fetch returning a 3.6 KB WAF HTML stub instead of the PDF.
- kstar.com **Download Center** (`https://www.kstar.com/download.html`) is JavaScript-rendered; the static HTML contains no manual rows, so the official manual list could not be enumerated. Confirmed by fetching the page and the UPS sub-page.
- kstar.com **FAQ** (`https://www.kstar.com/indexproblem/index.jhtml`, `https://www.kstar.com/service/faq.html`) → also 403 here; contents unverified.
- Consequence: every code in this file comes from a Kstar-authored manual PDF or Kstar manual hosted on a third-party manual library that I actually fetched. See NEGATIVE.

### Product lines covered in this file
| Series | Unit type | Source basis |
|---|---|---|
| Memopower UDC One (UDC910xS/H) | ups | Kstar manual, manualslib |
| Memopower RT (UDC9101RTS) | ups | Kstar manual, manualslib |
| YDC9300 (3-phase-in/1-phase-out online) | ups | Kstar manual, manualslib |
| KSG series (PV inverter) | hybrid_inverter | Kstar manual, manualslib |
| BluE-S H5/H3 (residential ESS) | hybrid_inverter | Kstar manual PDF (mirror) |
| BP48100PF1A-G2 battery pack BMS | ups (BMS accessory) | Kstar manual, manualslib |

Not covered / rejected: GTP 10–40kVA (bpee.com — not published by Kstar), FSP EPOS/MPlus (other brands, quarantined).

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|---|---|---|---|---|
| 1 | KSTAR official news "Top UPS Manufactures in Pakistan" | brand_official | https://www.kstar.com/news/info/356.html | PK presence evidence, brand positioning | 2026-09-26 |
| 2 | KSTAR official news "UPS battery price in Pakistan 2020" | brand_official | https://www.kstar.com/fr/newinformation/343.jhtml | PK presence, battery lines | 2026-09-26 |
| 3 | KSTAR company profile / About | brand_official | https://www.kstar.com/about_us.html | official name, HQ, listing | 2026-09-26 |
| 4 | KSTAR Download Center (UPS) | brand_official | https://www.kstar.com/download/uninterruptible_power_supply_datasheet.html | official-table existence check (JS-rendered, no rows) | 2026-09-26 |
| 5 | KSTAR service page | brand_official | https://www.kstar.com/service.html | support channels | 2026-09-26 |
| 6 | KSTAR official manual PDFs 4256-7232.pdf / 4256-7228.pdf (UPS) | user_manual | https://www.kstar.com/bocupload/2024/05/13/4256-7232.pdf | **BLOCKED** — kstar.com returns HTTP 403 to non-browser clients from this environment (WAF, 3.6 KB HTML stub). Not readable. Text only reachable via the search index's scraper; used solely to cross-check code 9 = Fan fault and the 01/02/15/16/17/18 group. | 2026-09-26 |
| 7 | KSTAR **New Energy** Download Center index (598 PDF links enumerated) | brand_official | https://www.kstarnewenergy.com/support.html | Full official manual list; this is the origin of rows 8-14. **Accessible (HTTP 200) while kstar.com is 403.** | 2026-09-26 |
| 8 | KSTAR KSG(25-40K-M6) User Manual EN Ver:1.0 (202505) | user_manual | https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_KSG(25-40K-M6)%20_EN_V1.0.pdf | **SERIES 2 source.** Table 8.1 Trouble shooting, F00-F32. Downloaded 6.3 MB, text extracted, diffed against rows 9-10. | 2026-09-26 |
| 9 | KSTAR KSG(25-40K) User Manual EN Ver:2.0 (202505) | user_manual | https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_KSG(25-40K)%20_EN_V2.0.pdf | **Independent confirmation of SERIES 2.** `diff` against row 8 fault table: byte-identical, so F24 = Isolated island Fault is confirmed on 2 official manuals. | 2026-09-26 |
| 10 | KSTAR KSG-(25-40)KT User Manual EN Ver:1.0 (202201) | user_manual | https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_KSG-(25-40)KT_EN_V1.0.pdf | **Same model numbers as row 8 but F24 = DSP Operation Fault and no F27.** Sole source of the SERIES 2 conflict. `diff` vs row 8: only F24 differs. | 2026-09-26 |
| 11 | KSTAR BluE-G (3000-6000)D-M1 User Manual EN Ver:1.0 (202108) | user_manual | https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_BluE-G%20(3000-6000)D-M1_EN_V1.0.pdf | BluE-G fault table — 20 codes, F24 = DSP Operation Fault, no F26, no F27, F07 threshold 500 KOhm. Verified, **not expanded** (see NEGATIVE). | 2026-09-26 |
| 12 | KSTAR BluE-G (1000-3200)S User Manual EN Ver:1.0 (202106) | user_manual | https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_BluE-G%20(1000-3200)S_EN_V1.0.pdf | BluE-G single-phase fault table — 22 codes, F24 = DSP Operation Fault, F26 present, F27 absent. Verified, **not expanded** (see NEGATIVE). | 2026-09-26 |
| 13 | KSTAR E3.68KS-D22 / E5KS-D22 / E6KS-D22 ESS Manual EN Ver:1.2 (202606) | user_manual | https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_E(3.68-6)KS-D22_EN_1.2.pdf | 8.3.1 Error Codes (27 F codes), 8.3.2 Alarm Codes (W00-W31), 8.3.3/8.3.5 BMS BIT tables. **Latest Kstar ESS line and the successor to SERIES 1's codes** — verified, **not expanded** (see NEGATIVE / CONFLICTS). | 2026-09-26 |
| 14 | KSTAR BluE-H5/H3 Energy Storage System Manual, 202203 Ver:3.0 | user_manual | https://media.adeo.com/media/3393641/media.pdf | **SERIES 1 source.** Official Kstar New Energy PDF (15 MB) on a third-party mirror: cover page reads "BluE-H5/H3 ENERGY STORAGE SYSTEM / Shenzhen Kstar New Energy Company Limited / Web:www.kstar.com". Ch 8.1 Alarm code W00-W25, 8.2 Error code F00-F32, ch 9 Fault Diagnosis table. | 2026-09-26 |
| 15 | KStar UDC9101S One User Manual — "Alarm Or Fault Reference Code" | user_manual | https://www.manualslib.com/manual/1452930/Kstar-Udc9101s-One.html?page=18 | **SERIES 3 source (p.18 table).** Direct fetch = HTTP 403; text captured via the search index's page scraper of this exact URL. | 2026-09-26 |
| 16 | KStar UDC9101S One User Manual — Troubleshooting | user_manual | https://www.manualslib.com/manual/1452930/Kstar-Udc9101s-One.html?page=19 | **SERIES 3 source (ch.4).** Cleanest code->LED sentences, used for the blinkPattern fields. Same 403 / scraper caveat. | 2026-09-26 |
| 17 | KStar UDC9101RTS Operation Manual — "Alarm or Fault reference code" | user_manual | https://www.manualslib.com/manual/1452929/Kstar-Udc9101rts.html?page=16 | Cross-check on SERIES 3 codes + the LED/beeper column. Same 403 / scraper caveat. | 2026-09-26 |
| 18 | KSTAR BP48100PF1A-G2 Installation/Operation/Maintenance Manual | user_manual | https://www.manualslib.com/manual/4560691/Kstar-Bp48100pf1a-G2.html | BMS error/fault flag bits (0x340). Same 403 / scraper caveat. Not expanded. | 2026-09-26 |
| 19 | KSTAR BluE-S H5/H3 manual, 2nd mirror | user_manual | https://solectric-energy.pl/wp-content/uploads/2022/08/KSTAR-BluE-S-H3-and-H5-series-Residential-ESS-Installation-Operation-Maintenance-Manual-v2.0.pdf | Independent v2.0 mirror of the SERIES 1 manual (confirms same code set at an earlier revision). | 2026-09-26 |
| 20 | tescomakademi.com UPS manual AGKK14640.pdf | unattributed_manual | https://tescomakademi.com/doc/AGKK14640.pdf | **REJECTED** — has a Kstar-shaped fault-code scheme (35-39, 40-44, 55-59, 85-89, 120-124, 130-134, 135-139, 145-149, 150-154, 155-159) but the string "Kstar" appears **nowhere** in the 41-page document. See QUARANTINE. | 2026-09-26 |
| 21 | Hikvision 1-3kVA rack UPS manual | other_brand_oem | https://assets.hikvision.com/prd/public/all/doc/m000162286/Usermanual-1-3KVA-RACK-UPS-220V.pdf | Contains a Kstar-Memopower-shaped alarm table (codes 9,26,27&28,29,30-41) but is branded Hikvision. See QUARANTINE. | 2026-09-26 |

## Negative results

Things that are true gaps. Nothing here was invented; each item is a thing I looked for and either could not verify or deliberately did not expand.

### 1. The single biggest gap: NO Kstar **UPS** fault table could be directly fetched
This is the brand's core Pakistani category and it is the one thing I could not read from a first-party host.
- `kstar.com` returns **HTTP 403 Forbidden** to every non-browser client from this environment. Verified: `curl -sI https://www.kstar.com/bocupload/2024/05/13/4256-7228.pdf` → `403`, content-type `text/html`, 3 687-byte WAF stub. Same for `4256-7232.pdf`, for `/ar/`, `/es/`, `/fr/`, `/ua/`, `/kr/` paths, and for `/indexproblem/index.jhtml` and `/service/faq.html`.
- `manualslib.com` also returns **403** to direct clients (`curl` → 403, 5 839-byte stub). Series 3 therefore rests on the search index's page scraper of the exact manualslib URLs, not a direct fetch. It is the weakest-sourced series in this file and is marked `confidence: medium` per entry, with code `12` marked `low`.
- `teren.ua` (403), `manualzz.com` (403), `pdfcoffee.com` (403) all blocked.
- **Consequence:** the Kstar UPS manuals `4256-7228.pdf` and `4256-7232.pdf` (the "intelligent, single phase in single phase out, high frequency online UPS" line) were only reachable as search-index snippets. Those snippets are enough to confirm exactly two things, and both are already folded into Series 3: code `9` = Fan fault, and `01,02,15,16,17,18` = "A UPS internal fault has occurred." **No other UPS code was taken from them.**

### 2. Kstar UPS lines with NO usable fault table found at all
| Product line | Status |
|---|---|
| Memopower RT / UDC9101RTS (1-3 kVA rack) | Table exists in the manual and was seen, but the code-to-row alignment of the LED/beezer columns is unrecoverable from the flattened scrape. **Not expanded as a separate series** — it shares the UDC One code set, and merging them would violate the one-series-per-table rule. |
| Memopower RT II (MP9100RTII-0.9) 1-3 kVA | No fault table located. |
| Memopower 6-10 kVA | No fault table located. |
| YDC9300 / YDC9300-B / YDC9300-RT (3-phase-in 1-phase-out) | Fault *strings* seen ("DC Bus over voltage", "DC bus below voltage", "DC bus unbalance", "Soft start failed", "Rectifier Over Temperature", "Inverter Over temperature", "output overload", and a numeric **29** for output short circuit), but the full numbered table was never retrieved. **Not expanded.** |
| YDC3300, YMK3300, HIP3300E, HPM, STS100, Epower-H | Not investigated beyond confirming manuals exist. |
| 6K-H / 6kVA / 10kVA "K" long-backup and "S" standard units | Candidate PDF found but **brand attribution failed** — see QUARANTINE item 1. |
| 600 / GP800 / 1K line-interactive | No fault table located. |
| Nova / Star / Pro series (common Pakistani retail naming) | **No Kstar document found using these names.** These appear to be Pakistani importer/OEM house names, not Kstar model names. Do not invent them. |

### 3. Kstar lines verified but deliberately NOT expanded (would have blown past the code target)
All four of these were downloaded, text-extracted and read. They are real Kstar primary-source tables. They are listed here rather than expanded so the total stays reviewable, and so the reviewer can commission them as extra series if wanted.
- **BluE-G (1000-3200)S** — 22 codes (F00-F03, F04-F05, F06-F16, F18, F20-F24, F26, F32). URL in SOURCE TABLE row 12. F24 = DSP Operation Fault, F26 present, F27 absent, F07 threshold 500 KOhm.
- **BluE-G (3000-6000)D-M1** — 20 codes (same as above minus F26, F27). URL in row 11. F24 = DSP Operation Fault.
- **E3.68KS-D22 / E5KS-D22 / E6KS-D22 ESS (202606)** — 27 fault codes F00-F32 plus alarm codes W00-W31 plus two BMS BIT tables. URL in row 13. This is the **newest and most relevant ESS line** and is the natural next series to add.
- **BP48100PF1A-G2 battery pack BMS** — BMS error and fault bit tables (0x340 status/fault flag, BIT0-BIT15 warning flags). URL in row 18. Relevant only when a Kstar ESS reports a battery-pack code.

### 4. Could not verify / not attempted
- **kstar.com.pk does not exist** as a resolvable host (`curl` → http=000, no DNS answer). PK presence is evidenced only via Kstar's own editorial pages and importer channels, not a local Kstar entity. No PK Kstar distributor list, warranty page or support number was found.
- **kstar.com FAQ / problem index** (`/indexproblem/index.jhtml`, `/service/faq.html`) — 403, contents unverified. This may well contain a searchable fault list; it could not be read.
- **kstar.com Download Center** — the static HTML ships no manual rows (JavaScript-rendered), so the official UPS manual list could not be enumerated. Only the *shape* of the official table is confirmed.
- Whether Kstar's Pakistani units are the **same hardware/firmware** as the exported manuals. Nothing found. The E(3.68-6)KS-D22 manual is dated 2026-06 and post-dates most Pakistan-market units.
- No LED/beeper column was recovered for the KSG and BluE-G lines beyond the single status-indicator table (`ON` Checking, `ON` Generating, `Twinkle` grid-connecting, `Twinkle` Alarm, steady `Fault`), so those series use `display: controller` rather than guessed blink patterns.
- Kstar's own **warranty** terms per product line — not retrieved.

## Quarantine (excluded)

Fault-code schemes found during research that are **NOT Kstar's** and must never be merged into the Kstar KB. Each was fetched and each carries a Kstar-shaped or Kstar-adjacent code system, which is exactly why they are dangerous.

1. **AGKK14640.pdf (tescomakademi.com) — unattributed UPS, Kstar-shaped codes.** `https://tescomakademi.com/doc/AGKK14640.pdf`, 41 pages, downloaded. Models 6KH/10KH/15KH/20KH (long-backup) and 6KS/10KS (standard). Carries the exact style of grouped Kstar-style fault codes: *"35-39 Inverter forbidden / over temperature"*, *"40-44 over temperature"*, *"55-59 Negative power fault"*, *"85-89 Bus short"*, *"120-124 Inverter fault"*, *"130-134 Inverter relay opened"*, *"135-139 Rectifier fault"*, *"145-149 Fan fault"*, *"150-154 EPO"*, *"155-159 SPS abnormal"*. **`grep -i kstar` over the whole extracted text returns nothing.** The document carries only P/N `15-018177-00` and no brand mark. It is a Kstar-platform document with the branding stripped, or an unrelated OEM. **Quarantined: unverifiable brand attribution.** If Kstar support ever confirms this P/N is theirs, these 10 grouped codes can be added — note the *grouped* 5-wide numbering (x5-x9) does not appear in any Kstar-branded table in this file.
2. **Hikvision 1-3 kVA rack UPS** — `https://assets.hikvision.com/prd/public/all/doc/m000162286/Usermanual-1-3KVA-RACK-UPS-220V.pdf`. Contains a Memopower-shaped alarm table with codes 9, 26, 27&28, 29, 30, 31, 32, 33, 34, 35 plus "Alarm code is shown as '9' and FAULT led light. Fan fault." **Branded Hikvision, a different company.** Its 27&28 = "Mains Input reverse & Bypass Input reverse" rows are a Kstar-*adjacent* variant, not a Kstar document. Quarantined. This is the strongest evidence in the file that Kstar hardware is OEM'd into other brands' products — which is also why a Pakistani technician may see a "Kstar-looking" code on a non-Kstar badge.
3. **GTP Series 10-40 kVA** — `https://bpee.com/assets/images/pdf/GTP_Series_10_40kVA_User_Manual.pdf`. Large fault table (002, 003, 004, 005, 007, 00A, 00C, 00E, 011, 012, 013, 014, 016, 019, 01D, 063, 01E, 041, 044, 047, 04A, 04D, 051, 054, 057, 05A, 0CF, 09A, 09D and alarm codes 103-109, 142-145, 14B-14F, 150, 151, 162-164). **No Kstar branding** — published by bpee.com. Quarantined.
4. **FSP EPOS10K(L) and FSP MPlus** — `https://www.fsp-group.com/download/user_manual/EPOS10K(L)_UserManual.pdf`, `https://www.fsp-ps.de/download/ico/709/MPlus-manual-HV20K-20160519R.pdf`. FSP is a **different brand**. Their fault tables (01, 02, 04, 06, 1B-1C, 21, 23-25, 31-33, 42, 43, 46-48, 67-69, 6C-6F, 71-73) are numerically close enough to Kstar's to be a real mis-mapping risk. Quarantined.
5. **Eaton, APC, CyberPower, Numeric, MaxPower** — not fetched, not used. Recorded here only because the brief requires them excluded, and because Kstar's Pakistan article (row 1 of SOURCE TABLE) names APC, Inverex, Homage and Cyber Power as *competing* brands, which is a second reason not to cross-pollinate.
6. **Voltronic / InfiniSolar OEM twins** — **excluded by rule.** Kstar and Voltronic are separate companies. Kstar's own NEP (BluE-S / BluE-H5/H3) hybrid inverters and BluE-PACK LFP batteries use a *different* code scheme from Voltronic/InfiniSolar's `W##/E##` inverter codes and `L##/C##` battery codes, even where the topology is similar. No Voltronic code appears anywhere in this file.

## Conflicts / caveats

### C1 — F24 has TWO different meanings on the same Kstar model numbers (**high impact**)
| Source | F24 means | F26 | F27 | F07 threshold |
|---|---|---|---|---|
| KSG(25-40K-M6) EN Ver:1.0, 202505 — row 8 | **Isolated island Fault** | present (IGBT Fault) | present (N line-to-earth voltage is high) | over 50 KOhm |
| KSG(25-40K) EN Ver:2.0, 202505 — row 9 | **Isolated island Fault** | present | present | over 50 KOhm |
| KSG-(25-40)KT EN Ver:1.0, 202201 — row 10 | **DSP Operation Fault** | present | **absent** | over 500 KOhm |
| BluE-G (1000-3200)S, 202106 — row 12 | **DSP Operation Fault** | present | absent | over 500 KOhm |
| BluE-G (3000-6000)D-M1, 202108 — row 11 | **DSP Operation Fault** | **absent** | absent | over 500 KOhm |
| BluE-S 5000D/3680D (BluE-H5/H3) — row 14 | **not defined** (table jumps F20 → F32) | not defined | not defined | over 2 MOhm |
`diff` of the row 8 and row 9 fault tables is clean (identical); `diff` of row 8 vs row 10 differs on **F24 only**, plus the F07 threshold. So this is a genuine firmware/manual-generation split, not a typo. **Rule for the KB: on a KSG 25-40K, treat F24 as a grid-islanding event first; only if the grid is healthy and F24 persists across a restart, treat it as a DSP fault.**

### C2 — F07 insulation threshold differs by product line (and 50 KOhm looks like a typo)
- KSG 25-40K M6 / Ver 2.0: **over 50 KOhm**
- KSG-(25-40)KT Ver 1.0, BluE-G 1000-3200S, BluE-G 3000-6000D-M1: **over 500 KOhm**
- BluE-S 5000D/3680D (BluE-H5/H3): **over 2 MOhm** ("If it is smaller than 2MΩ, check PV string for ground fault")
- E(3.68-6)KS-D22 ESS: **below 2 MΩ is a ground fault**
A factor of 10 between two manuals of the *same model family* (50 vs 500 KOhm) is almost certainly a dropped zero in the 202505 M6 manual. **Rule: measure and report the reading; do not quote Kstar's threshold to a customer as gospel. Use 500 KOhm as the working figure for KSG and 2 MOhm for the BluE-S ESS.**

### C3 — Code 29 LED behaviour differs between two Kstar UPS manuals
- UDC9101S One, ch.4 Troubleshooting: *"Alarm code is shown as '29' and FAULT led light."*
- Kstar 4256-7228/4256-7232 UPS manual: *"Alarm code is shown as '9' and FAULT led light."* (code 9, not 29) and separately *"Alarm code is shown as '01,02,15,16,17,18' A UPS internal fault has occurred."*
- UDC9101RTS reference table (same 29): maps 29 to a **blinking** Fault LED.
Resolution used in Series 3: `blinkPattern: "FAULT LED lit, continuous beep"` with a note that the blink state is disputed. **Rule: treat as "FAULT LED active", never quote a blink count.**

### C4 — W10 is spelled two different ways in one manual
- Ch. 8.1 Alarm code list: `W10 GFCI Over`
- Ch. 9 Fault diagnosis table: `CFCI Over`
Same code, same row position, inconsistent spelling inside a single Kstar manual. Both aliases recorded on the entry.

### C5 — The Kstar **ESS** code set was renumbered wholesale
The 2022 BluE-H5/H3 manual (Series 1) and the 2026 E3.68KS-D22/E5KS-D22/E6KS-D22 manual (row 13) are both "hybrid inverter + LFP battery" products from the same company, and the same physical symptom has a different code:
| Symptom | Series 1 (202203) | E-D22 (202606) |
|---|---|---|
| Soft start timeout | F00 | F00 (same) |
| Inverter output shorted | F01 | F01 (same) |
| GFCI sensor | F02 | F02 (same) |
| **Grid-tie relay failure** | **F09 Bypass Relay Fault** | **F14 Grid Relay Fault** |
| **Output/EPS relay failure** | **F19 EPS Relay Fault** | **F09 Bypass Relay Fault** |
| **Inverter output overcurrent** | **F10 INV Curr Over** | **F10 INV Curr Over** (same) |
| **Leakage current high** | **F20 Alway Over Load** (alarms) / W10 | **F20 Always Over Load** |
| **Internal comms failure** | **F32 SCI Fault** | **F32 DSP ARM SCI Fault** |
| Parallel-system codes | none | F22-F26 added |
| EPS air switch | none | F24 EPS Air Switch Abnormal |
**F09 changed meaning completely between the two ESS generations.** This is the highest-risk mapping in the whole file for a Pakistani installer holding a 2023-2024 unit. **Rule: always ask the installer to confirm the model (BluE-S 5000D/3680D vs E3.68KS-D22 vs E5KS-D22 vs E6KS-D22) and the manual revision before quoting a code meaning.**

### C6 — F24/F26/F27 presence is not consistent inside the KSG family either
- BluE-G 3000-6000D-M1 has **no F26**; BluE-G 1000-3200S and both KSG manuals **do**.
- Only KSG 25-40K M6 / Ver 2.0 has **F27**. The KT Ver 1.0 manual for the same models does not.
A code that is absent from a manual is not evidence the fault cannot occur — it means the manual is silent. Do not present an absent code as "impossible".

### C7 — Kstar's Pakistan article contradicts itself on market position
Kstar's own page (SOURCE TABLE row 1) states Kstar is *"the largest ups manufacturer in China"* and *"has supplied many UPS systems for Pakistan"*, and recommends Kstar as an OEM for new Pakistani UPS businesses. Separately, Kstar has been ranked **No. 6** (IHS, 2019), **No. 5** (IHS Markit, per About page) and **No. 4 / No. 5** (Omdia, modular UPS 2020 and 2022) by different research houses in different years. None of this changes the fault data, but any Pakistani-facing copy should not repeat the "largest in China" claim as fact. **PK presence is confirmed; market-ranking claims are not.**

## Research report

**Total codes: 96** across **3 series**.

| Series | id | unit_type | Codes | Faults | Warnings/status | Source strength |
|---|---|---|---|---|---|---|
| 1 | `kstar-blue-s-3680d-5000d` | hybrid_inverter | 47 (F00-F20, F32 = 21 + W00-W25 = 26) | 21 | 26 | Official Kstar New Energy manual (mirror PDF) — strong |
| 2 | `kstar-ksg-25-40k` | on_grid_inverter | 26 (F00-F16, F18, F20-F24, F26, F27, F32) | 26 | 0 | Official KSTAR New Energy manual, confirmed byte-identical on a 2nd official manual — strong |
| 3 | `kstar-memopower-udc-one` | ups | 23 (01, 09, 12, 13, 15-20, 26, 29-37, 39-41) | 21 | 2 | Kstar manual on manualslib, **captured via search-index scraper (direct fetch 403)** — weakest |

Target was 25-60. **Delivered 96, which is above target.** No padding was added: every entry is a row in a fault/alarm table that was actually retrieved, and each series transcribes one documented table complete rather than sampling it. Series 1 is large because Kstar splits a single product into a 21-row error table and a 26-row alarm table and both are useful to a technician. If a tighter set is wanted, Series 1's 26 W-codes are the natural thing to drop (they are `isFault: false, severity: info`), which would give **70 codes / 3 series**; dropping Series 3 would give **73 / 2 series**; dropping both gives **47 / 1 series**, comfortably in range but with no UPS coverage at all. **Recommend keeping all 3** — Series 3 is the only `unit_type: ups` block and the brand is filed under `ups`.

**Top 5 URLs**
1. `https://www.kstarnewenergy.com/support.html` — the official KSTAR Download Center index. 598 PDF links enumerated, and the only Kstar host that is **not** WAF-blocked. This is the single most valuable URL for this brand.
2. `https://media.adeo.com/media/3393641/media.pdf` — official KSTAR BluE-H5/H3 ESS manual, 202203 Ver:3.0. Full source of Series 1 (47 codes).
3. `https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_KSG(25-40K-M6)%20_EN_V1.0.pdf` — source of Series 2 (26 codes).
4. `https://www.manualslib.com/manual/1452930/Kstar-Udc9101s-One.html?page=18` — source of Series 3 (23 codes), the only UPS table.
5. `https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_E(3.68-6)KS-D22_EN_1.2.pdf` — the newest and most PK-relevant Kstar ESS manual (verified, not expanded). **Recommended next series to add.**

Supporting: `https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_KSG(25-40K)%20_EN_V2.0.pdf` and `https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_KSG-(25-40)KT_EN_V1.0.pdf` (the F24 conflict pair), `https://www.kstar.com/news/info/356.html` (PK presence).

**Official fault-code table published by Kstar? — YES, but only on the New Energy domain.**
Kstar publishes fault-code tables, and they are authoritative. Two problems: (a) they live on **kstarnewenergy.com** for PV inverter / ESS and on **kstar.com** for UPS, and kstar.com is 403 to non-browser clients, so the *UPS* tables are effectively unreachable; (b) the kstar.com Download Center is JavaScript-rendered and ships no manual rows, so the UPS manual list cannot be enumerated even in a browser session from this environment. For this brand, work from `kstarnewenergy.com/support.html`, not `kstar.com/download.html`.

**PK presence? — YES.**
Evidence: Kstar's own site, `https://www.kstar.com/news/info/356.html` — *"Kstar is the largest ups manufacturer in China and has supplied many UPS systems for Pakistan"* and *"If you are starting up UPS business in Pakistan, we recommended you to choose Kstar as your OEM (original equipments supplier). Kstar is famous for its online UPS, especially at small capacity UPS."* Plus `https://www.kstar.com/fr/newinformation/343.jhtml` — "UPS battery price in Pakistan 2020" with a PK-facing `sales@kstar.com` contact and Kstar FM/GFM/FML/FMH + deep-cycle solar battery lines. **No `.pk` domain** (`kstar.com.pk` does not resolve) and no local Kstar entity found; presence is via importers/distributors and OEM deals.

**Blockers**
1. **`kstar.com` is 403 to non-browser clients** (WAF). Blocks every Kstar UPS manual, the FAQ/problem index, and the Download Center listing. Tried: `www`, `/ar/`, `/es/`, `/fr/`, `/ua/`, `/kr/`; plain and browser User-Agents, referer spoofing, HTTP and HTTPS.
2. **`manualslib.com` is 403 to direct clients.** Series 3 therefore rests on the search index's scraper output, not a direct fetch. **This is the one series I would not ship to production without re-verification against a directly-downloaded PDF.** Its code 12 additionally has a recoverable pairing ambiguity and is marked `confidence: low` with notes.
3. **LED/beeper columns of the UDC One reference table did not survive flattening.** Only 6 of 23 codes got a `blinkPattern`; the other 17 use `display: controller` and say so in their notes. Quoting a blink count for those 17 would be fabrication.
4. **`terén.ua`, `manualzz.com`, `pdfcoffee.com` all 403** — three further Kstar BluE-S mirrors unreachable.
5. **The Kstar UPS manuals that would close the gap** (`4256-7228.pdf`, `4256-7232.pdf`) were only ever seen as snippets. If a browser-session fetch or a distributor copy can be obtained, Series 3 should be rebuilt and extended, and the code-9 / 01-02-15-16-17-18 group re-verified.
6. **Firmware-vs-manual revision is unresolvable per unit.** CONFLICT C1 and C5 both turn on which manual generation a given unit shipped with, and nothing was found that lets a technician determine that from the unit itself. The KB should ask for the model string and manual version on every ticket.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `kstar-blue-s-3680d-5000d.json` | BluE-S 5000D / 3680D hybrid inverter (BluE-H5/H3 ESS) | 47 |
| `kstar-ksg-25-40k.json` | KSG 25-40K three-phase grid-tied PV inverter | 26 |
| `kstar-memopower-udc-one.json` | Memopower UDC One series online UPS (UDC9101S / UDC9101H tower, 1-3kVA) | 23 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `kstar-blue-s-3680d-5000d.json` | user_manual | KSTAR BluE-H5/H3 Energy Storage System - Installation, Operation & Maintenance Manual (202203 Ver:3.0), hybrid inverter BluE-S 5000D/3680D | https://media.adeo.com/media/3393641/media.pdf | 2026-09-26 |
| `kstar-ksg-25-40k.json` | user_manual | KSTAR User Manual KSG(25-40K-M6) EN Ver:1.0 (202505) | https://www.kstarnewenergy.com/DOWNLOADS/User%20Manual_KSG(25-40K-M6)%20_EN_V1.0.pdf | 2026-09-26 |
| `kstar-memopower-udc-one.json` | user_manual | KSTAR UDC9101S One User Manual (Memopower udc one series), section 3-5 Alarm or Fault reference code, p.18 | https://www.manualslib.com/manual/1452930/Kstar-Udc9101s-One.html?page=18 | 2026-09-26 |

