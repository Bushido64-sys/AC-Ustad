# Must Power (`must-power`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **121** across **5** model-scoped series

Official: https://www.mustpower.com

OEM is Must Energy (Guangdong) Technology Co., Ltd., Foshan/Shenzhen, China; the brand is also written MUST, Must Energy or Must Power, and older manuals print the legacy name Shenzhen Must Energy Technology Co., Ltd. Must is the OEM behind several Pakistani-facing brands and product lines, including the PV1800/PH1800 hybrids sold in Pakistan by Must Power Energy (Lahore) - a PEC-registered Pakistani company and the sole Pakistan distributor for MUST Energy.

## Brand recon

- **Official name (OEM):** MUST Energy (Guangdong) Technology Co., Ltd. — the manufacturer. Consumer-facing brand is written **MUST / Must Power / Must Energy**. Address: Building 8, South China Power Innovation Science Park, No.115 Zhangcha 1st Road, Foshan (Guangdong), China; Shenzhen branch office. Hotline +86 755 83658583. Source: https://www.mustpower.com/contact-us-2/ and https://www.mustpower.com/
- **Legacy corporate name seen on older datasheets:** "Shenzhen Must Energy Technology Co., Ltd." (e.g. https://www.fenk.com.ar/wp-content/uploads/2019/11/Folleto-PH1800-PLUS.pdf) and "MUST ENERGY © China Headquarters". Both refer to the same OEM group; treat as one brand.
- **Country:** CN (Foshan/Shenzhen, Guangdong). Confirmed on mustpower.com contact page and mustenergy.com download footer.
- **Primary website:** https://www.mustpower.com (EN) and https://www.mustpower.cn (中文). Secondary/export portal: https://www.mustenergy.com
- **Official manual download portal (this is the authoritative OEM document library):** https://sw.mustpower.com/Manual/ — an open h5ai directory listing. Folders include `001 PV_Solar.Inverter`, `003 PH_On.Off.Grid.Inverter`, `005 HBP_ESS`, `007 Battery`. This proves the official OEM publishes per-model-line user manuals.
- **PK presence: YES.** Evidence:
  - https://mustpowerenergy.com/ — "Must Power Energy", F20-21 Upper Ground, Harmain Centre, Mall Rd, **Lahore, Pakistan**; +92 326 8163395; sells "Must Power" solar inverters + lithium batteries. Fetched and read 2026-09-26.
  - https://mustpowerenergy.com/wp-content/uploads/2025/03/Must-Power-Catalogue-_o.pdf — Pakistan company catalogue (MUSTPOWERENERGY (PAKISTAN) head office, Mall Road Lahore) that reproduces MUST Energy (Guangdong) spec tables verbatim and lists the Chinese factory address; brand site `www.mustpower.pk`.
  - https://suoerpks.com/product/must-hybrid-inverter-ph1800pro-6kva/ — Lahore-based Pakistani solar vendor listing "Must Hybrid Inverter 5kw" = **PH1800 PRO** 6KVA, with MUST's PH1800 PRO feature list copied verbatim. Direct evidence that PH1800 units are retailed in Pakistan under the Must name.
  - Secondary: https://mustpowerenergy.com/catalogue copy at mustsolar.com (mustsolar.com is MUST's own export site) is NOT PK — do not use as PK evidence.
- **Official fault/warning code table: YES.**
  - OEM publishes a "Fault Reference Code" table + "Warning Indicator" table in the user manual of every PV1800 / PH1800 model line, and a separate "Fault code display" table (codes 80–90) in the *PV1800-PH1800 Parallel Installation Guide* (doc no. 420-00235-03).
  - Official (image-only, no text layer) copies confirmed present at `https://sw.mustpower.com/Manual/001 PV_Solar.Inverter/4200-010022-02A1 PV1800 2-5.5KW VHM MPPT 80A 250V T1.2,.pdf` and `.../003 PH_On.Off.Grid.Inverter/4200-010030-02A1 PH18 2-5.5KW VHM 80A 250V T1.2,.pdf`. Those two official PDFs are **scanned images with no text layer** and no OCR is available in this environment, so the machine-readable text used below comes from byte-identical/equivalent distributor mirrors of the same OEM documents (each mirror's PDF `Title` metadata carries the OEM internal drawing number, e.g. `4200-00333-00 …PH18 3-5KW VHM…cdr`, which ties the mirror to the OEM's own document control system).
- **Model lines in scope for this KB (documented, with a code table found):**
  1. **PV1800 VHM / VPK** — off-grid hybrid, 2–5.5 kW, 12/24/48 V battery, 145 V or 250 V PV. OEM doc `4200-010022-02A1 PV1800 2-5.5KW VHM MPPT 80A 250V`. (→ SERIES 1)
  2. **PV1800 ECO** — 2 kW (400 V PV) / 2.5–5.5 kW (450 V PV). OEM doc `4200-010052-01A1 PV18 2KW-ECO 400V 80A MPPT` and `4200-010053-0000 PV18 2.5-5.5KW-ECO 450V 100A MPPT`. (→ SERIES 2)
  3. **PH1800 / PH18 3–5 kW VHM (on/off-grid)** and PH1800 Plus / PRO 3–5.5 kW. OEM doc `4200-010030-02A1 PH18 2-5.5KW VHM 80A 250V`, `4200-010018-04A1 PH1800 3-5.2KW PRO 100A`. (→ SERIES 3)
  4. **PV1800 & PH1800 parallel operation** (DIP-switch CAN bus, 2–3 units). OEM doc `420-00235-03 PV1800-PH1800 Parallel Installation Guide`. (→ SERIES 4)
  5. **PV1800 2K/3K HM** — service-manual fault→board-repair table (different semantics: same numbers point at main-board sections, not user symptoms). (→ SERIES 5)
- **Deliberately OUT of scope / quarantined (other Must model lines, not PV1800/PH1800):** HBP1800, HBP1500, HBP3000, HBP3300, EP1800 / EP1800-PRO / EP18-5048, EP3000, EP3300, PV1500, PV3000, PV3300, PV3600, PV3900, PV2900, PV1900, PV5000, PV1100, PH1100, PH1600, PH1900, PH3000, PH5000, PH5900, PC1800F, PI1500, UPS, LP/HV/EH/EM battery & ESS lines.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|---|---|---|---|---|
| 1 | MUST Energy official site — Contact | official_corporate | https://www.mustpower.com/contact-us-2/ | Legal entity name, Foshan/Shenzhen address, hotline, emails | 2026-09-26 |
| 2 | MUST Energy official site — home | official_corporate | https://www.mustpower.com/ | Brand positioning, product families (PV1800 PRO, PH1100 …) | 2026-09-26 |
| 3 | MUST Energy official site — PV1800 PRO (2-6.2 kW) | official_product | https://www.mustpower.com/product/pv1800-pro-2-6-2kw/ | PV1800 PRO model codes + spec grid (12/24/48 V, 400–500 V Voc) | 2026-09-26 |
| 4 | MUST Energy official site — inverter & charge index | official_product | https://www.mustpower.com/inverter-charge/ | Model-line names: "PV1800 VPM II Series (1~5.5KW)", "PV1800 PRO Series", "PH1800 LV Series (3KW)" | 2026-09-26 |
| 5 | MUST official manual portal root (h5ai index) | official_download | https://sw.mustpower.com/Manual/ | Folders `001 PV_Solar.Inverter`, `003 PH_On.Off.Grid.Inverter` | 2026-09-26 |
| 6 | MUST official manual portal — PV18 folder listing | official_download | https://sw.mustpower.com/Manual/001%20PV_Solar.Inverter/ | OEM drawing numbers for PV1800 VHM / VPM II / ECO / PRO manuals | 2026-09-26 |
| 7 | MUST official manual portal — PH18 folder listing | official_download | https://sw.mustpower.com/Manual/003%20PH_On.Off.Grid.Inverter/ | OEM drawing numbers for PH18 / PH1800 PRO / Plus manuals | 2026-09-26 |
| 8 | MUST official manual — PV1800 2-5.5KW VHM (`4200-010022-02A1`) | official_manual_image_only | https://sw.mustpower.com/Manual/001%20PV_Solar.Inverter/4200-010022-02A1%20PV1800%202-5.5KW%20VHM%20MPPT%2080A%20250V%20T1.2%2C.pdf | Downloaded (14.6 MB) but **scanned image PDF, no text layer** → used only to prove the official table exists | 2026-09-26 |
| 9 | MUST official manual — PH18 2-5.5KW VHM (`4200-010030-02A1`) | official_manual_image_only | https://sw.mustpower.com/Manual/003%20PH_On.Off.Grid.Inverter/4200-010030-02A1%20PH18%202-5.5KW%20VHM%2080A%20250V%20T1.2%2C.pdf | Downloaded (14.9 MB) but **scanned image PDF, no text layer** → proves official table exists for PH18 | 2026-09-26 |
| 10 | MUST official manual — PH1800 3-5.2KW PRO (`4200-010018-04A1`) | official_manual_image_only | https://sw.mustpower.com/Manual/003%20PH_On.Off.Grid.Inverter/4200-010018-04A1%20PH1800%203-5.2KW%20PRO%20100A%20T1.4%2C.pdf | Downloaded (17.5 MB), image-only → proves official table exists for PH1800 PRO | 2026-09-26 |
| 11 | *User Manual PV18 3-5KW VHM* (MUST, mirror) | user_manual | https://freeray.ma/wp-content/uploads/2022/01/Manuel-PV1800-VHM-3-4-5KW-VHM.pdf | SERIES 1: full Fault Reference Code table (30 rows), Warning Indicator table (14 rows), LED indicator table, TROUBLESHOOTING table | 2026-09-26 |
| 12 | *PV18 2-3KW VHM* (MUST, mirror) — OEM title `420-00343-00 说明书 PV18 2-3KW VHM off-grid MPPT` | user_manual | https://waveinverter.co.nz/download/PV18-24v-3KW-VHM.pdf | Independent confirmation that the VHM code table is identical across PV18 VHM sizes | 2026-09-26 |
| 13 | *User Manual PV18 3048 LHM 3KW 120VAC* (MUST, mirror) — OEM title `4200-010330-00A1 说明书,PV1800,3KW,120VAC` | user_manual | https://www.ecozaque.com/wp-content/uploads/2020/10/MANUAL-PV18-3048-LHM-3KW-120VAC.pdf | Independent confirmation of the same table on the 120 V PV1800 LHM line | 2026-09-26 |
| 14 | MUST PV1800 VHM 24 V 3 kW manual (HTML render) | user_manual_html | https://manuals.plus/ae/1005005476950878 | Cross-check of PV1800 VHM fault table + LED table (page blocks direct fetch 403; content confirmed via search index of same URL) | 2026-09-26 |
| 15 | *PV18 2KW-ECO 400V 80A MPPT* (MUST, mirror) — OEM title `4200-010052-01A1 说明书,PV18,2KW-ECO,400V 80A MPPT` | user_manual | https://fixer.com.ua/storage/files/fd/fd135adf5b66ffba863484535be81f7d.pdf | SERIES 2: ECO fault table (**divergent 03 / 52 / 58**), warning table, ECO TROUBLESHOOTING table (95/150 Vac limits, L/N reversed) | 2026-09-26 |
| 16 | *PV1800 PWM 1-3KW only-solar* (MUST, mirror) — OEM title `420-00268-03 说明书 PV1800 PWM 1-3KW only solar` | user_manual | https://one-sun.ru/upload/is_custom_manual/8eb/8ebbbd35d8d56db4e7af38f36020825f.pdf | PV1800 PWM variant fault+warning table (confirms shared numbering across PV1800 sub-variants) | 2026-09-26 |
| 17 | *说明书 PH18 3-5KW VHM on-off-grid MPPT* (MUST, mirror) — OEM title `420-00333-00 …PH18 3-5KW VHM on-off-grid MPPT…` | user_manual | https://www.megasolar.com.ua/wp-content/uploads/2025/04/alfa.solar-must-pv18-5048-vhm-5kvt.pdf | SERIES 3: PH1800-family fault table (30 rows) + warning table (14 rows) | 2026-09-26 |
| 18 | *PV1800-PH1800 Parallel Installation Guide* (`420-00235-03`) (MUST) | other | https://www.fenk.com.ar/wp-content/uploads/2019/11/PV1800-PH1800-solar-inverter-parallel-installtion-guide.pdf | SERIES 4: parallel fault codes 80–90 + per-code solutions + DIP-switch/CAN ID table | 2026-09-26 |
| 19 | *Service Manual PV18-2K/3K HM* (MUST) — HTML render | service_manual_html | https://usersmanualguide.com/must/inverter/pv1800-2k-hm/user-manual/qllq | SERIES 5: fault-number → main-board repair-section table (01,02,03,06,08,09,52,56,57,58,72 + "No LCD display") | 2026-09-26 |
| 20 | *Service Manual PV1800 2K HM* (MUST) | service_manual_html | https://www.manualslib.com/manual/1880760/Must-Pv1800-2k-Hm.html | Independent confirmation of the SERIES 5 fault→repair mapping | 2026-09-26 |
| 21 | Must Power Energy (Pakistan) — About | distributor_pk | https://mustpowerenergy.com/about-us/ | PK presence: Lahore HQ, PEC-registered, sells Must Power inverters + Li batteries | 2026-09-26 |
| 22 | Must Power Energy (Pakistan) — company catalogue | distributor_pk | https://mustpowerenergy.com/wp-content/uploads/2025/03/Must-Power-Catalogue-_o.pdf | PK entity + MUST Energy (Guangdong) spec tables + mustpower.pk | 2026-09-26 |
| 23 | Suoer Pakistan — Must Hybrid Inverter PH1800 PRO | distributor_pk | https://suoerpks.com/product/must-hybrid-inverter-ph1800pro-6kva/ | PH1800 PRO retailed in Lahore, PK under the Must name | 2026-09-26 |
| 24 | Must Energy official export site (download centre) | official_corporate | https://www.mustenergy.com/download | Confirms no separate PK-specific code table; global manual set | 2026-09-26 |

## Negative results

Nothing below could be verified from a URL fetched on 2026-09-26. It is recorded so nobody re-derives it from memory.

1. **PH1800 PRO / PH1800 Plus / PH1800 MPK Plus own fault table — NOT VERIFIED.** The official PDFs exist on the OEM portal (`4200-010018-04A1 PH1800 3-5.2KW PRO 100A T1.4,.pdf`, 17.5 MB; `4200-010357-07A1 PH18 2-5.5KW T1.7,.pdf`, 15.2 MB; `4200-010339-07A1 PH1800 3-5.2KW PRO 80A T1.7,.pdf`) but every one of them is a **scanned image PDF with an empty text layer** (`pdftotext` returns 0 characters) and no OCR engine is installed in this environment. A third-party mirror of the PH1800 MPK Plus manual at `https://electro100.ua/files/attach_files/MUST PH1800 MPK Plus Series PH18-4K MPK Plus.pdf` returns **HTTP 403**. **Therefore SERIES 3 relies on the `420-00333-00 PH18 3-5KW VHM` manual, NOT on a PH1800 PRO/Plus manual.** Do not claim PRO/Plus-specific codes.
2. **PV1800 PRO / PV1800 PRO II / PV1800 PRO 3.6-6K own fault table — NOT VERIFIED.** Official PDFs (`4200-010004-07A1 PV1800 3-5.2KW PRO 80A`, `4200-010048-0000 PV1800 PRO 100A 3-5.2KW`, `PV18 3.6-6K PREM MUST T1.4 ECO.pdf`) are image-only scans. No text could be read.
3. **PV1800 VPM II official manual — NOT USED.** `https://deps.ua/downloads/Documentation/Must-Power/PV1800-VPM-II/Must-Power_PV1800-VPM-II_User-manual_EN.pdf` exceeds the 5 MB fetch limit and could not be read directly. Its table text was visible only through a search index, so it is **not cited as a code source anywhere**. VPM II is therefore scoped into SERIES 2 by *name pattern only* — its code rows were **not** independently confirmed. Treat VPM II wording as unverified until that PDF is read.
4. **No LED blink-pattern code table exists for any Must PV1800/PH1800 model.** The only LED documentation found is a 3-indicator state table (AC/INV green, CHG yellow, FAULT red) with solid-vs-flashing semantics, and an LCD note ("Warning: flashing with warning code. Fault: lighting with fault code."). **No per-code flash counts were found, so no entry in this file carries a `blinkPattern` field.** Do not invent blink patterns.
5. **No battery / BMS / EMS fault code table found.** The only BMS material located is LCD *settings*, not codes: Program 40 "BMS communication" (whether the converter keeps charging/discharging when BMS communication is faulted) and Program 41 "Battery protocol" (range 0-31; Pylontech = 08; other vendors such as Dyness, HRESYS, Lakepower, Maxli, Ultracell, Luxpower appear in a Must support screenshot). **No BMS error code list is published.** No separate battery-series block was created.
6. **No audible-alarm / buzzer beep-pattern table for Must PV1800/PH1800.** Some Axpert-derived manuals from other brands publish beep patterns; the Must documents fetched do not. Quarantined beep patterns from other brands are listed in QUARANTINE.
7. **No PK-specific Must fault code table.** `mustpower.pk` and `mustpowerenergy.com` publish product pages and a catalogue only — no codes, no download centre.
8. **No published firmware-version list**, even though fault 88 requires all parallel units to run the same firmware. There is no way to state which versions are compatible.
9. **`https://manuals.plus/ae/1005005476950878` returns HTTP 403 on direct fetch.** Its PV1800 VHM table content was confirmed only through a search index, so it is listed in the SOURCE TABLE as a cross-check only, and no entry relies on it. The LED semantics actually used came from the fetched freeray.ma PDF.
10. **No dry-contact / relay output alarm-to-code mapping table** was found for PV1800 or PH1800.
11. **"AC Ustad" is a Pakistani solar-installation knowledge base, not a Must publication.** No Must document uses that name; no code came from it.

## Quarantine (excluded)

**Rule: none of the following may be merged into any Must Power series.** Several of these are Axpert-derived designs whose numeric tables look almost identical to Must's, which is exactly the trap this KB must avoid.

- **Axpert-derived / same-family non-Must manuals** (identical-looking 01-09, 11, 51-58, warning 01/03/04/07/10 tables with beep patterns): Steca Solarix PLI (`https://steca.es/wp-content/uploads/2020/03/Solarix_PLI_Manual_EN_Z05.pdf`), FlinEnergy / Steca PLI 5000-48 (`https://solcelle.dk/Datablad/321486_BA_EN.pdf`), MasterPower Omega (`https://www.masterbattery.es/manuales/Omega_Water_Pump_15K.pdf`, `.../Omega_LS`), Masterbattery MF-OME-PRO (`https://www.masterbattery.es/manuales/MANUAL-MF-OME-PRO-6.2KV5.pdf`), generic "3KVA-5KVA Inverter/Charger Manual" and "1.5KVA-3KVA Inverter-Charger Manual" (Scribd). **Their beep patterns, warning numbering (01/03/04/07/10) and code 09/11 meanings differ from Must's.**
- **Solar-pump inverter code sets** (E01-E18 / A01-A17 naming): FlinEnergy FlinFlow 2.2 kW and 15 kW pump manuals (`https://flinenergy.com/wp-content/uploads/2025/03/FlinFlow-2.2kW-Solar-Pump-Inverter-Manual.pdf`). Completely different scheme; not applicable to PV1800/PH1800.
- **Name collision — "PH1800" is also a Xantrex model.** Xantrex PowerHub 1800 (`https://assets.northerntool.com/products/457/documents/manuals/457000-2.pdf`, `https://xantrex.manymanuals.com/power-adapters-inverters/ph1800/user-manual-2245`) uses **E01-E09** style codes with completely different meanings. Never route an Xantrex "PH1800" to this brand.
- **Other Must model lines with their own (unfetched or out-of-scope) code tables — quarantine by model, not by brand:** HBP1800 (`https://must-ukraine.com/wp-content/uploads/docs/usergied_MUST-HBP18-1012-1kW.pdf`), HBP1800 1-3 kW, HBP1500, HBP3000, HBP3300, EP1800 / EP18-5048 (`https://deps.ua/downloads/Documentation/Must-Power/EP18-5048/MUST_EP18-5048_manual.pdf`, `https://baikaltesla.com/wp-content/uploads/2025/04/MUST-EP1800-PRO_Series_Manual_ENG.pdf`), EP3000, EP3300, PV1500, PV3000, PV3300, PV3600, PV3900, PV2900, PV1900, PV5000, PV1100, PH1100 (`https://deps.ua/downloads/Documentation/Must-Power/PH1100/Must-Power_PH110_User-manual_EN.pdf`), PH1600, PH1900, PH3000, PH5000, PH5900, PC1800F, PI1500, UPS, and the LP/HV/EH/EM battery & ESS families. Note the EP1800 warning table is **already known to differ** (it lists only 61, 62, 63, 64, 67, 70, 77 — no 72-76), so PV1800/PH1800 warning rows must not be copied onto it.
- **Must-branded but re-badged Pakistan product lines** sold by mustpowerenergy.com under different names: **Lionex** (three-phase hybrid, 48 V, CAN BMS), **Magnum Series** (MP-OPTIMUM-DUAL PV 5500 / 8500, 450-500 V PV), **Optimum Series** (PV 500 V, 90-430 V MPPT), **Export Series** (MP-EXPORT-10KWL3 / 15KWL3 three-phase export inverters). These are different hardware with their own code tables; they are **not** PV1800/PH1800 even though Must Energy is the OEM. Quarantined.
- **Must PV1800 2K/3K HM vs PH1800 PLUS 4K/5K service-manual section numbers** — the same fault number points at different board sections in the two documents (e.g. fault 06 → "3.3" in the PV1800 HM manual vs "3.4" in the PH1800 PLUS 4K/5K manual). Section numbers must never be copied across models.
- **Non-Must Pakistani vendors' own re-brand codes.** `suoerpks.com` (Suoer Pakistan, Lahore) sells a Must PH1800 PRO as a "Suoer" listing; any Suoer-branded fault code is Suoer's, not Must's.

## Conflicts / caveats

1. **Same number, different meaning — PV1800 VHM vs PV1800 ECO / VPM II (the most important conflict in this file).**
   - `03`: VHM = "Battery voltage is too high" only. ECO = "Battery voltage is too high **or AC input L/N wires are reversed**", and the ECO troubleshooting row adds "AC input L/N wires are reversed — Check AC input."
   - `52`: VHM = "Inverter bus voltage is too low". ECO = "Inverter bus voltage is too low **or component temperature is to high**".
   - `58`: VHM = "Inverter output voltage is too low". ECO = "Inverter output voltage is too low **or component temperature is to high**".
   - **Resolution:** kept as separate series (SERIES 1 vs SERIES 2). Never fall back from an ECO/VPM-II unit to SERIES 1 wording for 03, 52, 58 — a reversed AC input on ECO shows as 03, not as a separate code.
2. **Same number, different meaning — PV1800 (VHM) vs PH1800 (PH18 3-5 kW VHM).** The brief warns these differ, and that is true **in general** (PH1800 is the grid-tie/sell-capable sibling, so PV1800 ECO's grid-related wording must never be applied to a PH1800). **However, the two tables I actually fetched are word-for-word identical, including 03, 52 and 58.** I have recorded that identity honestly rather than inventing a difference the sources do not show. Consequence for the KB: PH1800 03 is battery-over-voltage only — the "AC L/N reversed" reading is an ECO/VPM-II-only behaviour.
3. **Source-attribution conflict on the "3-5KW VHM" manual.** One OEM document (`420-00333-00`, internal title `…PH18 3-5KW VHM on-off-grid MPPT…`) is distributed under four different names: `Manuel-PV1800-VHM-3-4-5KW-VHM.pdf` (freeray.ma, PV1800), `User-Manual-PV18-3-5KW-VHM-off-grid-MPPT.pdf` (device.report, PV18), `alfa.solar-must-pv18-5048-vhm-5kvt.pdf` (megasolar, PV18-5048) and the OEM title (PH18). PV and PH variants of this hardware appear to share one manual. **Resolution:** SERIES 1 and SERIES 3 quote the source whose filename names their own model, and both record the ambiguity in `notes` and in this section. Do not treat them as independent confirmations of each other.
4. **Troubleshooting-window conflict for code 06/58.** PV1800 VHM manual: "Output abnormal (Inverter voltage below than **202Vac** or is higher than **253Vac**)". PV1800 ECO manual: "Output abnormal (Inverter voltage below than **95Vac** or is higher than **150Vac**)". Same code, different acceptance window per model — a technician who applies the ECO window to a VHM unit will chase a non-existent fault.
5. **Fault-table vs service-table semantics for 01, 02, 03, 06, 08, 09, 52, 56, 57, 58, 72.** In SERIES 1/2/3 these numbers name a *user-visible symptom*; in the SERIES 5 service manual the same numbers name a *main-board repair section* (e.g. 57 = "To replace the control board" with no symptom; 08 = restart, then replace control boards if it repeats). Two separate series are used so these are never presented interchangeably.
6. **Warning 72 severity conflict across documents.** In the user manuals 72 is a warning ("Solar charger stops due to low battery", isFault false), while the PV1800 2K/3K HM service manual answers 72 with "To replace the control board" — i.e. the same number is a soft warning in one document and a board-replacement item in another. SERIES 5 records it as `isFault: true` deliberately, and the `notes` field on that entry warns about it.
7. **LED semantics — do not invert.** User manual LCD note: "Warning: flashing with warning code. Fault: lighting with fault code." LED table: red **Solid On** = "Fault occurs in the inverter"; red **Flashing** = "Warning condition occurs in the inverter." These two agree. A third-party render groups several fault codes under the heading "Buzzer beeps continuously and red LED is on" while simultaneously listing warning codes, so the safe rule for the app is: **solid red = fault, flashing red = warning**, exactly as the OEM states.
8. **Company-name drift in sources.** Older documents print "Shenzhen Must Energy Technology Co., Ltd." (and even `sales@mustups.com` / `www.mustups.com` on the PH1800 Plus leaflet); current documents print "Must Energy (Guangdong) Technology Co., Ltd." / "MUST Energy (GUANGDONG) TECHNOLOGY CO., LTD". Same OEM. Not a code conflict, but do not treat the two names as different brands when deduplicating sources.
9. **Unverified scope creep avoided.** The PV1800 VPM II table seen via a search index matched the ECO wording, but because that PDF could not be fetched it is **not** cited. VPM II model patterns are listed in SERIES 2 for routing only; a build step must not treat SERIES 2 wording as VPM II-confirmed.

## Research report

- **Total documented code entries: 121** across 5 series.
  - SERIES 1 — PV1800 VHM/VPK: 43 entries (31 faults 01-58 + 12 warnings 61-77)
  - SERIES 2 — PV1800 ECO (divergent rows only): 12 entries
  - SERIES 3 — PH1800 / PH18 3-5 kW VHM (Plus/PRO line): 43 entries (31 faults + 12 warnings)
  - SERIES 4 — PV1800 & PH1800 parallel: 11 entries (80-90)
  - SERIES 5 — PV1800 2K/3K HM service mapping: 12 entries (11 codes + "No LCD")
  - **Distinct codes after de-duplication: 55** (43 in the 01-77 range + 11 in the 80-90 range + 1 "No LCD" symptom). The 121 figure exceeds the 40-80 target because the non-merge rule forces PV1800 and PH1800 to each carry their own full 43-row OEM table, and because the service-manual and parallel tables are separate documents. If a hard cap is needed, dropping SERIES 2 and SERIES 5 (24 entries) gives 97 entries / 55 distinct codes; sampling SERIES 3 down to the 12 rows that differ in real-world service frequency would bring entries to ~80 with no loss of distinct codes.
- **Series count: 5**
- **Top 5 URLs (by code yield):**
  1. https://freeray.ma/wp-content/uploads/2022/01/Manuel-PV1800-VHM-3-4-5KW-VHM.pdf — 43 entries (SERIES 1 full PV1800 VHM table + LED table + troubleshooting table)
  2. https://www.megasolar.com.ua/wp-content/uploads/2025/04/alfa.solar-must-pv18-5048-vhm-5kvt.pdf — 43 entries (SERIES 3 PH1800-family table; OEM doc `420-00333-00`)
  3. https://fixer.com.ua/storage/files/fd/fd135adf5b66ffba863484535be81f7d.pdf — 12 entries (SERIES 2 ECO divergent rows + ECO 95/150 Vac window; OEM doc `4200-010052-01A1`)
  4. https://www.fenk.com.ar/wp-content/uploads/2019/11/PV1800-PH1800-solar-inverter-parallel-installtion-guide.pdf — 11 entries (SERIES 4, codes 80-90 with per-code solutions; OEM doc `420-00235-03`)
  5. https://usersmanualguide.com/must/inverter/pv1800-2k-hm/user-manual/qllq — 12 entries (SERIES 5 service-manual board-repair mapping)
  (Supporting: https://sw.mustpower.com/Manual/ — official portal proving the OEM tables exist for PV1800 VHM, PH18 VHM and PH1800 PRO.)
- **Blockers:**
  1. **All official `sw.mustpower.com` manuals are scanned images with no text layer**, and no OCR engine is available here. This is the single biggest blocker: it is why PH1800 PRO/Plus, PV1800 PRO and PV1800 PRO II have no verified table of their own, and why PV1800 VHM / PH1800 VHM are sourced from distributor mirrors whose PDF metadata carries the OEM drawing numbers.
  2. `electro100.ua` PH1800 MPK Plus manual mirror returns HTTP 403; `manuals.plus` returns HTTP 403.
  3. `deps.ua` PV1800 VPM II PDF exceeds the 5 MB fetch cap — the VPM II variant remains unverified.
  4. No PK-local Must document publishes any fault codes; PK mapping must rely on the global OEM manuals plus the distributor evidence below.
  5. No LED blink-count table and no BMS/EMS code table exist in any Must document fetched — both are permanently NEGATIVE unless Must publishes them.
- **Official fault/warning code table published by the OEM: YES.** "Fault Reference Code" + "Warning Indicator" tables in every PV1800/PH1800 user manual, plus a separate "Fault code display" table (80-90) in the PV1800-PH1800 Parallel Installation Guide (`420-00235-03`). Official copies confirmed downloadable from https://sw.mustpower.com/Manual/ (`001 PV_Solar.Inverter`, `003 PH_On.Off.Grid.Inverter`).
- **PK presence: YES.** https://mustpowerenergy.com/about-us/ (Must Power Energy, Harmain Centre Mall Road, Lahore; sells Must Power inverters and lithium batteries), https://mustpowerenergy.com/wp-content/uploads/2025/03/Must-Power-Catalogue-_o.pdf (Pakistan entity + MUST Energy (Guangdong) spec tables + mustpower.pk), https://suoerpks.com/product/must-hybrid-inverter-ph1800pro-6kva/ (PH1800 PRO 6 kVA retailed in Lahore under the Must name).
- **Build-step warnings:** (a) never let a lookup fall through from an ECO/VPM-II unit to the PV1800 VHM wording for 03/52/58; (b) never let a customer-facing screen show SERIES 5 board-repair wording as a code's meaning; (c) PH1800 03 is battery-over-voltage only — the "AC L/N reversed" reading is ECO/VPM-II only; (d) `ur` fields are Latin-script Roman Urdu throughout and every `ur` differs from its `en`; (e) VPM II is in SERIES 2's `model_patterns` for routing only and must be treated as unverified until its official PDF is readable.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `must-power-pv1800-vhm.json` | PV1800 VHM (off-grid hybrid inverter/charger 2-5.5kW) | 43 |
| `must-power-pv1800-eco.json` | PV1800 ECO (400V/450V high-voltage-PV off-grid hybrid) | 12 |
| `must-power-ph1800-vhm.json` | PH1800 (PH18 3-5kW VHM on/off-grid hybrid, Plus / PRO variants) | 43 |
| `must-power-pv1800-ph1800-parallel.json` | PV1800-PH1800 Parallel Installation Guide fault codes 80-90 | 11 |
| `must-power-pv1800-hm-service.json` | PV1800 2K/3K HM (service manual fault-to-main-board mapping) | 12 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `must-power-pv1800-vhm.json` | user_manual | User Manual - HYBRID SOLAR INVERTER 3KW-5KW (PV1800 VHM / PV18 2-5.5KW VHM MPPT 80A 250V, OEM doc 4200-010022-02A1) | https://freeray.ma/wp-content/uploads/2022/01/Manuel-PV1800-VHM-3-4-5KW-VHM.pdf | 2026-09-26 |
| `must-power-pv1800-eco.json` | user_manual | User Manual - PV18 2KW-ECO 400V 80A MPPT (OEM doc 4200-010052-01A1) | https://fixer.com.ua/storage/files/fd/fd135adf5b66ffba863484535be81f7d.pdf | 2026-09-26 |
| `must-power-ph1800-vhm.json` | user_manual | User Manual - HYBRID SOLAR INVERTER 3KW-5KW, PH18 3-5KW VHM on-off-grid MPPT (OEM doc 420-00333-00) | https://www.megasolar.com.ua/wp-content/uploads/2025/04/alfa.solar-must-pv18-5048-vhm-5kvt.pdf | 2026-09-26 |
| `must-power-pv1800-ph1800-parallel.json` | other | PV1800-PH1800 Solar Inverter Parallel Installation Guide (OEM doc 420-00235-03) | https://www.fenk.com.ar/wp-content/uploads/2019/11/PV1800-PH1800-solar-inverter-parallel-installtion-guide.pdf | 2026-09-26 |
| `must-power-pv1800-hm-service.json` | service_manual | Service Manual PV18-2K/3K HM Inverter/Charger (PV1800 2K/3K HM) | https://usersmanualguide.com/must/inverter/pv1800-2k-hm/user-manual/qllq | 2026-09-26 |

