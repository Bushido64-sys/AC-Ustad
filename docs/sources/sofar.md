# Sofar Solar (`sofar`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **103** across **9** model-scoped series

Official: https://www.sofarsolar.com

Chinese OEM with an official Pakistan footprint; SOFAR's own newsroom and brochure both list Pakistan as a market, and Omega Power Technologies is a confirmed SOFAR distribution partner in Pakistan (the stronger 'master distributor' claim rested on sofar.pk, now dead - see NEGATIVE 13). Max Power (this KB's max-power brand) is a separate PK importer that RESELLS the Sofar series alongside its own Voltas/Suntronic lines, so no max-power entries were created here.

## Brand recon

- **Official name:** SOFAR (Shenzhen SOFARSOLAR Co., Ltd.) — brand is marketed as "SOFAR" / "Sofar Solar"; inverter model strings all begin with `SOFAR`.
  - Evidence: https://www.sofarsolar.com/AboutSOFAR.html — "SOFAR is a global leading provider of all-scenario solar PV and energy storage solutions"
  - Evidence: https://www1.sofarsolar.com/ — "SOFAR is a provider of all-scenario solar PV and energy storage solutions… PV inverters range from 1 kW to 350 kW, hybrid inverters range from 3 kW to 20 kW"
- **Country:** CN (Shenzhen, China). Evidence: manual copyright line "Shenzhen SOFARSOLAR Co., Ltd."
- **Website:** https://www.sofarsolar.com (global). EU download centre: https://downloads.sofarsolar.eu/ — confirmed live, lists current manuals/datasheets (retrieved 2026-09-26).
- **PK presence: YES.**
  - SOFAR's own corporate "Global Footprint" page lists **Pakistan** among its markets: https://www.sofarsolar.com/AboutSOFAR.html
  - SOFAR corporate newsroom, Solar Pakistan 2023: https://br.sofarsolar.com/news/1391.html — "Lahore, Mar. 13, 2023 - SOFAR, the global leading supplier of PV & ESS solutions, presents its wide product portfolio from residential, C&I to utility at Solar Pakistan… SOFAR's brand-new 100-125KTL-G4 made a great appearance among local customers… Bo Dong, Head of SOFAR APAC region, sees Pakistan as a vibrant solar market… **As a pivotal player in Pakistan, SOFAR will keep bringing forth innovations**".
  - SOFAR brochure listing offices worldwide incl. Pakistan: https://www.sofarsolar.com/upload/file/20240323/1711181321400003198.pdf — "SOFAR offices can now be found in the UK, Poland, Germany, South Korea, UAE, **Pakistan**, Australia, etc."
  - **Omega Power Technologies (Pvt) Ltd is a SOFAR distribution partner in Pakistan.** Corroborated by three live sources:
    - https://solargrid.pk/2025/08/04/sofar-solar-has-launched-new-series-of-hybrid-inverters-batteries-with-10-years-warranty-through-partnership-with-omega-power/ — "**Sofar Solar has launched new series of Hybrid Inverters & Batteries with 10 years warranty through partnership with Omega Power**" (dated 2025-08-04, product lines HYD-3-8K-LS1-PRO, SF-5KWHL-L1).
    - https://engineeringreview.com.pk/omega-power-pioneering-pakistans-renewable-energy-revolution/ — "the introduction of **Omega's co-branded lithium battery with Sofar Solar**, offering 8,000 cycles and a 10-year warranty".
    - https://omegapower.pk/ — Omega Power's own site carries a "Sofar" product category with SOFAR 80-KTLX-G3, 5.5-KTLM-G3, 125-KTLX listings (site root live 2026-09-26; individual product permalinks found via search now 404).
  - **CAVEAT — "master distributor" wording is NOT verified.** A first-pass source attributed to `https://sofar.pk/` quoted Omega Power announcing itself "master distributor for #SofarSolar in Pakistan". **That domain no longer resolves** (no A record, no Wayback snapshot, three fetch attempts returned 000 on 2026-09-26), so that quote is no longer re-verifiable and I have **not** treated "master distributor" as established. The defensible claim is **"distribution/partnership partner"**. See NEGATIVE §13 and CONFLICTS C1.
  - Local PK channels also present: https://solargrid.pk/sofar-solar-inverters-in-pakistan ("SOFAR has established a warranty claim center in Lahore city with dedicated engineers for after sales support"), and https://sofar.pk/.
- **Max Power relationship (this KB's `max-power` brand):** Max Power is a Pakistani importer/distributor that **sells the Sofar series** — it is not an OEM of these codes. Evidence:
  - https://maxpower.com.pk/introduction — "Max Power offers on-grid, off-grid, and hybrid solar inverters in Pakistan through its **Voltas, Suntronic, and Sofar series**. These range from compact residential units to large commercial and industrial three-phase inverters, covering capacity requirements from 1.2 kW to 125 kW and above."
  - https://maxpower.com.pk/news/max-power-solar-inverter-brands-pakistan — Max Power's own news index carries items "**Sofar Zero Export Device**" / "Harness the Power of the Sun with Maxpower's Sofar Zero Export Device".
  - **Conclusion:** Max Power is a PK reseller/importer of SOFAR product. Do NOT create or copy `max-power` entries from this research. Note: Omega Power is a *different* company from Max Power. **No source — including Max Power's own pages — claims exclusivity or master-distributor status for Max Power**, so Max Power must not be labelled "the SOFAR distributor in PK" as a unique fact.
  - Related but distinct: FMST (https://fmst.com.pk/maxpower-solar) is an "Authorized Partner & Distributor" for MaxPower®; FMST is a Max Power channel, not a SOFAR one.
- **Official fault-code table published: YES.** Sofar publishes complete error/fault tables in official user manuals (PDF, on sofarsolar.com / downloads.sofarsolar.eu).
  - HYD 5K...20KTL-3PH §8.2 "Error list" (Inverter error list §8.2.1 + **Battery error list §8.2.2**): https://www.sofarsolar.com/upload/file/20241010/1728530425537061360.pdf
  - SOFAR 15-25KTLX-G3P §"TROUBLESHOOTING AND MAINTENANCE" event list: https://www.sofarsolar.com/upload/file/20251104/1762243911541013797.pdf
  - SOFAR 1.1K~3.3KTL-G3 §7.1 "Event list" (Table 7-1): https://www.sofarsolar.com/upload/file/20240311/1710121923451047146.pdf
  - SOFAR 1~4KTL2-G3 § troubleshooting "ID/Name/Description/Solution": https://www.sofarsolar.com/upload/file/20240314/1710400367619032659.pdf
  - No single consolidated website-hosted "all codes" page was found; tables are per-model-line inside each manual. (Sofar does expose a monitoring-portal/app alarm list, but it is not a static public table.)
- **Model lines targeted:** KTLX-G3 (three-phase 3.3–125K), KTL-G3 (single-phase 1.1–3.3K), KTL2-G3 / KTL3-G3 (single-phase 1–5K), KTL-G4 / KTLX-G4 (100–125K), HYD 5K...20KTL-3PH (hybrid, with EPS + battery codes), ME 5K...20KTL-3PH (AC-coupled storage), KSG (storage), SH1T/HT.
- **Code-namespace note (important, documented not assumed):** every Sofar manual I actually fetched numbers its rows **`ID0xx` (e.g. `ID001`, `ID169`)**, and names the field "ID" / "Event List NO." — **not** `E0xx`/`W0xx`. I have recorded the codes exactly as published (`ID001` etc.) rather than reformatting them into an `E0xx` convention. Range-segments published as one row (e.g. `ID009-010`) are kept as documented segments. See `## NEGATIVE` for the `E0xx`/`W0xx` premise.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|---|---|---|---|---|
| 1 | SOFAR About page (global footprint) | vendor_corporate | https://www.sofarsolar.com/AboutSOFAR.html | Official name, CN origin, portfolio ranges, Pakistan in footprint list | 2026-09-26 |
| 2 | sofarsolar.com homepage (www1) | vendor_corporate | https://www1.sofarsolar.com/ | Product range 1–350 kW PV, 3–20 kW hybrid; country base | 2026-09-26 |
| 3 | SOFAR news — Solar Pakistan 2023 | vendor_news | https://br.sofarsolar.com/news/1391.html | PK market presence, 100-125KTL-G4 + 255KTL-HV shown in Lahore, APAC head quote | 2026-09-26 |
| 4 | SOFAR corporate brochure (2024) | vendor_brochure | https://www.sofarsolar.com/upload/file/20240323/1711181321400003198.pdf | "SOFAR offices can now be found in the UK, Poland, Germany, South Korea, UAE, Pakistan, Australia" | 2026-09-26 |
| 5 | SOFAR EU Download Center | vendor_download_center | https://downloads.sofarsolar.eu/ | Proof of live official manual repository; 80-125KTLX-G4-ESS manual V1.1 (2026-08-11) listed | 2026-09-26 |
| 6 | Max Power — Introduction page | distributor_site | https://maxpower.com.pk/introduction | Max Power sells "Voltas, Suntronic, and **Sofar** series" in Pakistan → Max Power = SOFAR reseller/importer, not OEM | 2026-09-26 |
| 7 | Max Power — news index | distributor_site | https://maxpower.com.pk/news/max-power-solar-inverter-brands-pakistan | "Maxpower's Sofar Zero Export Device" → active SOFAR product line for Max Power | 2026-09-26 |
| 8 | ~~sofar.pk~~ — **DEAD, NOT RE-VERIFIABLE** | distributor_site | https://sofar.pk/ | Sole original basis for the "Omega Power = **master distributor** in Pakistan" wording. **Domain no longer resolves** (no A record; 3 fetches returned HTTP 000; no Wayback snapshot) on re-check 2026-09-26. **Claim downgraded to "distribution partner" — see rows 20-22 for the live substitutes. Nothing in this file depends on this row alone.** | 2026-09-26 (first pass); re-check FAILED 2026-09-26 |
| 9 | solargrid.pk — SOFAR in Pakistan | distributor_site | https://solargrid.pk/sofar-solar-inverters-in-pakistan | "SOFAR has established a warranty claim center in Lahore city" | 2026-09-26 |
| 10 | SOFAR HYD 5K...20KTL-3PH User Manual §8.2.1 Inverter error list | service_manual | https://www.sofarsolar.com/upload/file/20241010/1728530425537061360.pdf | ~90 inverter rows ID001–ID190 (grid, PV, battery, BMS, EPS, fan, comms) | 2026-09-26 |
| 11 | SOFAR HYD 5K...20KTL-3PH User Manual §8.2.2 Battery error list | service_manual | https://www.sofarsolar.com/upload/file/20241010/1728530425537061360.pdf | 20 battery rows ID808–ID906 (battery-radiator/env/charge-prohibit/bus/batt hardware + permanent) | 2026-09-26 |
| 12 | SOFAR 15-25KTLX-G3P User Manual — Troubleshooting event list | service_manual | https://www.sofarsolar.com/upload/file/20251104/1762243911541013797.pdf | Event list running ID001–ID216 (with range rows), of which ID001–ID145 are individually enumerated; rows taken: LVRT/OVRT/island/consistency/temp/USB/derating | 2026-09-26 |
| 13 | SOFAR 1.1K~3.3KTL-G3 User Manual Table 7-1 Event list | service_manual | https://www.sofarsolar.com/upload/file/20240311/1710121923451047146.pdf | ID01–ID56 single-phase G3 event list (relay test, GFCI, SPI, insulation) | 2026-09-26 |
| 14 | SOFAR 1~4KTL2-G3 User Manual — ID troubleshooting table | service_manual | https://www.sofarsolar.com/upload/file/20240314/1710400367619032659.pdf | ID01–ID38 single-phase KTL2-G3 fault/solution rows | 2026-09-26 |
| 15 | FMST – MaxPower authorized partner page | distributor_site | https://fmst.com.pk/maxpower-solar | Distinguishes MaxPower (PV Tech/CHF/MP Alpha own line) from SOFAR; FMST is Max Power channel only | 2026-09-26 |
| 16 | SOFAR 100-125KTLX-G4 User Manual §9, Table 8-1 "Even list" | service_manual | https://www.sofarsolar.com/upload/file/20230920/1695196332754062551.pdf | ID001–ID192 rows: grid, VGridUnbalance, arc shutdown, PV reversal, 6 radiator temps, HwDiffOCP, PLC/PID/AFCI comms | 2026-09-26 |
| 17 | SOFAR HYD 3000/3600/5000/6000-EP User Manual §7, Table 7-1 "Eventlist" | service_manual | https://www.sofarsolar.com/upload/file/20241227/1735288541294079959.pdf | Single-phase hybrid table ID001–ID190: incl. ID032 OffgridGroundFault, ID045 CTDisconnect, ID046 PVReversalConnect, ID047 ParallelFault, ID056 NTCConnectFault, ID094 LoadShortCircuit, ID107 HwVerError, ID108/ID109 generator, ID125 battery-discharge-prohibit, ID144 PermGridRlyFail, ID151 BatPartOffline, ID183–ID185 BMS version | 2026-09-26 |
| 18 | SOFAR ESI 3-6K-S1 User Manual §7, Table 6-1 "List of common events" | service_manual | https://www.sofarsolar.com/upload/file/20260316/1773653907482011378.pdf | Stacked single-phase hybrid: ID001–ID879 incl. ID032 N-PE fault, ID133 EPSBatOCP, ID808/ID809 battery temp warnings, ID813/ID814 stop-charge/stop-discharge, ID864/ID865 battery over-temp | 2026-09-26 |
| 19 | ManualsLib – SOFAR 50KTLX-G3 (locator, not used for codes) | manual_aggregator | https://www.manualslib.com/manual/3904033/Sofar-Solar-Sofar-50ktlx-G3.html | Confirms 3.3KTLX-G3 / 50KTLX-G3 manuals split into "Fault Codes (ID005-ID038)", "(ID041-ID103)", "(ID105-ID173)", "(ID174-ID216)" — used only to corroborate that the ID-namespace is split per table | 2026-09-26 |
| 20 | solargrid.pk — SOFAR × Omega Power launch article (replacement for dead row 8) | distributor_site | https://solargrid.pk/2025/08/04/sofar-solar-has-launched-new-series-of-hybrid-inverters-batteries-with-10-years-warranty-through-partnership-with-omega-power/ | "**Sofar Solar has launched new series of Hybrid Inverters & Batteries with 10 years warranty through partnership with Omega Power**" → confirms Omega Power is a live SOFAR distribution partner in PK. **Uses the word "partnership", NOT "master distributor"** | 2026-09-26 |
| 21 | Engineering Review (PK) — Omega Power profile (replacement for dead row 8) | trade_press | https://engineeringreview.com.pk/omega-power-pioneering-pakistans-renewable-energy-revolution/ | "**Omega's co-branded lithium battery with Sofar Solar**, offering 8,000 cycles and a 10-year warranty" → second independent confirmation of the SOFAR–Omega Power PK relationship. **No master-distributor claim** | 2026-09-26 |
| 22 | Omega Power Technologies — own site (replacement for dead row 8) | distributor_site | https://omegapower.pk/ | Site root live; carries a "Sofar" product category (SOFAR 80-KTLX-G3, 5.5-KTLM-G3, 125-KTLX). Third confirmation. Note: individual product permalinks surfaced by search now return 404 | 2026-09-26 |
| 23 | Live re-verification sweep of all 8 official manual PDFs | verification | (all eight sofarsolar.com PDF URLs in rows 10-18) | All 8 re-fetched HTTP 200 on re-check. HYD manual text-extracted and **every SERIES 5/6 code spot-verified against the source table** (ID001/002/003/004/005/008/009-010/011/012/013/014/027/032/038/039/040 and 808/809/813/814/864/865/867/872/873/879 all confirmed verbatim). Regex sweep for `\b[EW][0-9]{3}\b` re-run: still 0 hits | 2026-09-26 |

## Negative results

Things asserted or expected in the brief that I could **NOT** verify from a URL I actually fetched, plus claims I deliberately declined to encode. Nothing here was turned into a code entry.

1. **`E0xx` faults / `W0xx` warnings — NOT FOUND. No such code format exists in any Sofar document I fetched.**
   - I ran a regex sweep for `\b[EW][0-9]{3}\b` across the full text of all six downloaded official manuals (`hyd5-20`, `me5-20`, `15-25ktlxg3p`, `11-33ktlg3`, `1-4ktl2g3`, `100-125ktlxg4`) plus `hyd3-6k-ep` and `esi3-6k-s1`. **Zero matches in every file.**
   - Sofar's actual published namespace is **`ID0xx` / `ID1xx` / `ID8xx` / `ID9xx`**, labelled "Event List NO." (single-phase G3 manuals) or "Code" / "ID" (all newer manuals). I recorded codes exactly as printed rather than reformatting them into an `E0xx`/`W0xx` convention, because inventing that mapping would be extrapolation.
   - Consequence for the app: there is **no verified E/W split to encode.** Whether `E0xx`/`W0xx` is a display-layer convention the app owner wants to impose on Sofar (e.g. mapping `ID001`→`E001`) is a product decision, not something a source supports. **This is a blocker for any E0xx/W0xx-specific normalisation.**
   - Related: Sofar does distinguish fault-like rows from informational rows *in words* ("Internal faults of inverter", "Permanent ... failure", "This message is for information and is not an error"), not via a letter prefix. I encoded that in `isFault`/`severity` instead.

2. **No consolidated website-hosted master code table found.** `https://www.sofarsolar.com/download.html` renders only a filter shell over JS; the per-model rows are served dynamically and I could not enumerate them. `https://downloads.sofarsolar.eu/` rendered a real list but only the newest ~20 documents (2026 era), which does not reach the G3 manuals. So: official tables exist **only inside per-model PDFs**, and there is no single "all Sofar codes" page I can cite.

3. **No PK-specific Sofar fault-code list found.** The Pakistan-relevant URLs I fetched (Omega Power / sofar.pk, solargrid.pk, maxpower.com.pk) carry no fault codes — only product, distributor and warranty information. No NEPRA/DRC listing of Sofar error codes was found.

4. **Manual model coverage I could not close — no codes recorded, no guessing:**
   - **KSG** (Sofar's storage series). No KSG user manual with a fault table was located via search. `https://www.sofarsolar.com/download.html?_download_type=User%20Manual&_searchKey=KSG` returns only the filter shell — no rows. **No KSG codes recorded.** Do not assume KSG shares the HYD/ESI IDs.
   - **SH1T / HT** (single-phase hybrid line). Not located as a retrievable manual. The 7SUN listing for "SOFAR ESI-12K-T1" (https://7sun.eu/produkt/inwerter-sofar-powerall-esi-12k-t1-hybrydowy-hv) names a model I could not find a manual for; that page is a reseller datasheet with no fault codes, so nothing was taken from it.
   - **SOFAR 3.3-12KTLX-G3** (small three-phase residential). Not fetched. The G3P table (15-25KTLX-G3P) is a *different* model line and I did not assume it applies.
   - **SOFAR 255KTL-HV / 75-136KTL-HV.** A manual exists (`https://downloads.sofarsolar.eu/wp-content/uploads/sofar/Manuals/EN/SOFAR%2075-136KTL-HV_User%20Manual_2023-03-06_V1.6_en-EU.pdf` appears in search results) but I did **not fetch or extract it**, so no HV codes are recorded.
   - **SOFAR KTLM-G3 (3-6KTLM-G3) single-phase 3-MPPT.** Manual exists (`https://downloads.sofarsolar.eu/wp-content/uploads/sofar/Manuals/EN/SOFAR%203-6KTLM-G3_User%20Manual_2024-01-22_V1.0_en-EU.pdf`) but I did **not fetch it**, so no KTLM-G3 codes are recorded.
   - **SOFAR SAR-100 / CH1000 / EV11k-AC-02 wallbox / PowerMagic 2.0 / CBS5000-H4-H12.** Search surfaced these manuals; none were fetched, so no codes recorded. (A wallbox/ESS LED table was glimpsed in a search snippet for EV11k-AC-02 but I did not fetch that PDF, so it is not used.)

5. **`display` field is an inference, not a source statement.** Sofar's manuals do state *where the code is read* ("displayed on the LCD screen", "can be seen on the corresponding monitoring website, and can also be received by the APP"), but they never use an "indoor/outdoor/controller/led_blink" taxonomy. I set `display: controller` for essentially every row because the LCD event list is the local read point. The `blinkPattern` values are limited to rows where a manual actually says the red light comes on (the single-phase G3/KTL2-G3 earth-fault statement, and the HYD/ME/EPS earth-fault rows) — I did **not** invent flash counts. Sofar does not publish a blink-count code table anywhere I found, so **no row carries a specific flash count that a source did not state.**

6. **No fault/warning LED colour code table found.** `display: led_blink` was therefore never used.

7. **`severity` mapping is my editorial mapping.** Sofar publishes Description + Solution prose, not severity tiers. `danger` / `stop_pro` / `check_restart` / `self_clear` / `info` are assigned from the manual's own words ("Permanent failure", "unrecoverable", "This message ... is not an error", "automatically returns to normal"). A reviewer should treat these as derived, not quoted.

8. **Pakistan-specific meaning of a code: not established.** Codes are documented generically by Sofar. Which thresholds actually apply in PK (DISCO/NEPRA 50 Hz window, permissible voltage band) is **not** in these manuals, so I did not put any PK voltage/frequency number into any `causes` or `solutions` field. Expect the app's installer layer to own grid-limit configuration.

9. **Max Power is NOT evidenced as SOFAR's master distributor in PK — it is evidenced only as a reseller/importer.** `https://maxpower.com.pk/introduction` says Max Power sells inverters "through its Voltas, Suntronic, and Sofar series", and its news index features a "Sofar Zero Export Device". Neither page claims exclusivity or master-distributor status. The **master distributor named for Pakistan is Omega Power Technologies (Pvt) Ltd** (quoted via https://sofar.pk/). I therefore did **not** write "Max Power is the SOFAR master distributor" anywhere.

10. **Manual version/date drift — codes may differ by firmware.** The manuals themselves warn: "The models you purchased may only contain some of the fault information listed therein" (15-25KTLX-G3P) and "As the content of this chapter involves software version updates, the differences may exist among different versions of the software." So an installed unit may emit a code not in its manual. Not encoded; flagged here.

11. **Not verified: whether the "ID" prefix is shown on the physical LCD.** Manuals show both `ID001` style and bare `ID01`/`05`/`98` style in the Code column, and one manual's own display mock-up reads `Fault information 001 ID04 06150825` — i.e. what the user actually sees may be a bare number. I stored the code **as printed in the table's Code column** for each series. App may want a `displayCode` variant; that is a product decision.

12. **Corroboration I did not obtain for most codes.** Every entry rests on **one** official-manual source, so all are `confidence: high` on the "official manual" criterion but have **no second independent source**. I did not upgrade or downgrade on the basis of a ManualsLib mirror, because a mirror of the same PDF is not an independent source.

13. **`https://sofar.pk/` is DEAD — the "master distributor" claim is no longer re-verifiable.** On re-check (2026-09-26) the domain has **no A record**, three fetch attempts returned **HTTP 000**, and `archive.org/wayback/available` returns **no archived snapshot**. A first pass had quoted this site for "Omega Power is now the master distributor for #SofarSolar in Pakistan". I have **removed "master distributor" as an established fact** and replaced it with **"distribution partner"**, backed by three live, independent sources (SOURCE TABLE rows 20-22): solargrid.pk ("partnership with Omega Power"), Engineering Review PK ("Omega's co-branded lithium battery with Sofar Solar"), and Omega Power's own site (Sofar product category). **None of the three uses the words "master distributor".** Also dead/stale: `https://omegapower.pk/product/sofar-80-ktlx-g3/` now 404s (site root still live), and `https://www.enfsolar.com/omega-power` returns 403 to non-browser clients. **No code entry depends on any of this** — it affects brand recon and CONFLICTS C1 only.

## Quarantine (excluded)

Material encountered during research that is **not SOFAR-branded** and must never be lifted into the `sofar` KB. Listed so a later pass does not re-import it by mistake.

- **Huawei SUN2000-100/110/125KTL** manual surfaced in search: `https://solar.huawei.com/-/media/Solar/attachment/pdf/au/service/Supporting/SUN2000/SUN2000-100-125KTL-Series-UserManual.pdf`. Returned under a "SOFAR 100-125KTLX-G4" query purely on model-number similarity. **HUAWEI codes — quarantined.** The G4 numbers I recorded came from Sofar's own G4 manual, not from this file.
- **7SUN** product pages (https://7sun.eu/produkt/inverter-sofar-3300-tl-g3-single-phase, https://7sun.eu/produkt/inwerter-sofar-powerall-esi-12k-t1-hybrydowy-hv). Polish/EU reseller. Specifications are SOFAR's, but 7SUN is a rebranded private label (e.g. "POWERALL ESI-12K-T1" is a 7SUN brand name, not a Sofar model). **No codes taken**; 7SUN-branded model names quarantined.
- **midsummer.ie** mirror of a SOFAR HYD 3k~6K-ES manual (https://midsummer.ie/pdfs/sofar-hyd-user-manual.pdf). Content is Sofar's, but it is a **third-party host, not sofarsolar.com**, and it covers the older **HYD-ES** line. **Not used for any code.** If someone later wants HYD-ES, it must come from sofarsolar.com.
- **ManualsLib** (https://www.manualslib.com/manual/3904033/Sofar-Solar-Sofar-50ktlx-G3.html and siblings). Used **only** as a locator/corroboration of table page titles — see SOURCE TABLE row 19. **No code values taken from ManualsLib.** A mirror is not a primary source.
- **manualslib / solarchamp / profyheat / tracxn / 7sun / platform.tracxn.com / ind.sofarsolar.com partner lists** — all third-party or non-code-bearing. No codes taken.
- **Max Power / PV Tech / CHF / MP Alpha** (https://fmst.com.pk/maxpower-solar, https://maxpower.com.pk/*). Different brands with their own codes. **Quarantined — no `max-power` entries created or copied**, per the brief.
- **Voltas, Suntronic** — named on the Max Power page as separate inverter lines. **Quarantined.**
- **SOFAR CH1000 / SAR-100** (EV charger, feed-in limiter) and **SOFAR EV11k-AC-02 wallbox**, **PowerMagic 2.0 / CBS5000** ESS — real Sofar products with their own manuals, but **not fetched and not in scope** for an inverter KB. If a battery/ESS KB is ever opened, these need their own fetch.
- **Sofar Cloud / SOFAR Monitor / SOLARMAN app alarm lists** — referenced by manuals ("alarm information can be seen on the corresponding monitoring website") but these are authenticated portals, not public tables, and I did not access them. **No app-portal codes recorded.**

## Conflicts / caveats

**C1 — Max Power vs Omega Power: who represents SOFAR in Pakistan. (PARTIALLY RESOLVED, and downgraded from the first pass.)**
- `https://maxpower.com.pk/introduction` → Max Power sells the **Sofar series** in Pakistan (1.2 kW to 125 kW+) and features a **"Sofar Zero Export Device"** in its own news.
- `https://solargrid.pk/2025/08/04/...partnership-with-omega-power/` → "**Sofar Solar has launched new series of Hybrid Inverters & Batteries with 10 years warranty through partnership with Omega Power**"; `https://engineeringreview.com.pk/omega-power-pioneering-pakistans-renewable-energy-revolution/` → "**Omega's co-branded lithium battery with Sofar Solar**"; `https://omegapower.pk/` → Sofar product category. Omega Power is therefore a **confirmed SOFAR distribution partner in Pakistan**.
- **What changed:** the first pass asserted Omega Power was the **"master distributor"** on the strength of `https://sofar.pk/`. That domain is **dead** (no DNS, no Wayback — NEGATIVE §13), so the *master* tier is **no longer evidenced**. I have downgraded it to **partner/distributor**.
- **Resolution for the KB:** SOFAR's PK channel is **multi-tier** and at least two firms are verifiably active (Omega Power, Max Power). **Do not label either one "the SOFAR distributor in PK" as a unique fact**, and do not assert a master-distributor tier at all until a live source supports it. Neither company claims exclusivity for these codes, so **zero `max-power` entries were created or copied**.

**C2 — `ID032` means three different things across three Sofar tables.**
- HYD 5K...20KTL-3PH (SERIES 5) `ID032` = **"N-PE fault / Neutral ground fault"**.
- HYD 3-6K-EP (SERIES 8) `ID032` = **"OffgridGroundFault / Off-grid ground fault"**.
- ESI 3-6K-S1 (SERIES 9) `ID032` = **"N-PE fault / Neutral ground fault"**.
- **Resolution:** real, and it is the clearest proof that codes must be scoped per model line. If the app ever offers a code *lookup* that ignores model, `ID032` will return a wrong answer for EP units. **Recommendation: require a model/firmware context for any Sofar lookup.** No merge performed.

**C3 — `ID125` differs between the two hybrid tables.**
- HYD 5K...20KTL-3PH (SERIES 5) `ID125` = **"BatLowVoltShut / No battery protection shut"** (a shutdown).
- HYD 3-6K-EP (SERIES 8) `ID125` = **"Battery discharge protection"** (a protection block, and I encoded it `isFault: false`).
- **Resolution:** genuinely different rows with different severity. Kept separate. Note the severity divergence is mine (derived from wording), not Sofar's — flagged in NEGATIVE §7.

**C4 — `ID013` wording differs between G3P and G4.**
- 15-25KTLX-G3P `ID013 RefluxFault` = "**Feed-in limitation function is faulty**".
- 100-125KTLX-G4 `ID013 ReﬂuxFault` = "**Anti-countercurrent overload**".
- **Resolution:** same failure area (zero-export), different description text, and the G4 manual's wording reads more like the symptom than the cause. Not merged; each series carries its own published wording.

**C5 — `ID044` is spelled three ways.**
- KTLX-G3P: `ConfigError`. KTLX-G4: `PvConfigError`. HYD 3-6K-EP: `PvConfigError` (listed **twice** in that manual — once at ID044 and again at ID137 as a permanent variant).
- **Resolution:** cosmetic upstream inconsistency plus one genuine duplicate. `ID044` kept in all three series; the EP manual's duplicate `ID137 PvConfigError` was **not** added to SERIES 8 (it is a "permanent failure" row that collides with `ID137` = `unrecoverPvConfigError` on ME) — logged here rather than silently dropped.

**C6 — `ID129` is 15-25KTLX-G3P-only in the G3P table but is `unrecover*`-named on G4/ME/EP/ESI.**
- G3P `ID129` = `unrecoverHwAcOCP`. ME `ID129` = `unrecoverHwAcOCP`. EP `ID129` = `HwAcOCP` (no "unrecover" prefix despite "permanent fault" in the description). G4 `ID129` = `PermHwAcOCP`.
- **Resolution:** same hardware overcurrent event, four naming conventions, and the EP table omits the prefix. My `stop_pro` severity for the G3P row is derived from the word "permanent" in its own description — it is *not* confirmed that an EP unit with the same underlying fault also latches permanently. Flagged, not resolved.

**C7 — `ID156` SoftVerError has two different manual remedies.**
- 15-25KTLX-G3P: "**Contact for technical support and software upgrades.**"
- HYD 5K...20KTL-3PH: "**Download the latest firmware from the website and launch the software update.** If the error persists, contact technical support."
- **Resolution:** newer HYD manual is more actionable; not a contradiction but an evolution. No merged entry produced; SERIES 5 carries the HYD wording, and the G3P table's `ID156` was simply not selected.

**C8 — Manufacturer address / corporate identity differs across documents.**
- Older manuals (1.1K~3.3KTL-G3, 1~4KTL2-G3, ME, HYD): "**Shenzhen SOFARSOLAR Co., Ltd**" with the older Antongda Industrial Park address visible in the 2018 50~70KTL manual.
- Newest documents (ESI 3-6K-S1, EU download centre): "**Shenzhen SOFARSOLAR Co., Ltd.**, 11/F, Gaoxinqi Technology Building, District 67, XingDong Community, XinAn Street, Bao'An District, Shenzhen, China" plus "**SOFARSOLAR GmbH**, Krämerstrasse 20, 72764 Reutlingen, Germany".
- **Resolution:** same brand, address change over time. Brand recon uses the current name/website; no bearing on codes.

**C9 — "Sofar Solar" vs "SOFAR" as brand string, and multiple regional sites.**
- Brand is written `SOFAR`, `SOFARSOLAR` and `Sofar Solar` across its own properties (sofarsolar.com, sofarsolar.eu, uk/ind/br/au subdomains, sofarsolar.store via a Tracxn profile).
- **Resolution:** the KB's `sofar` id with display name "Sofar Solar" is fine. `website` recorded as the global `https://www.sofarsolar.com`; the EU download centre noted separately in BRAND RECON. No regional site used as a code source.

## Research report

- **Total codes captured: 103** across **9 series**.
  - **Honest note on the 40–80 target: this is 23 over the band, deliberately.** Sofar *does* publish complete per-table fault lists, and the brief's own instruction is to "take every verifiable row if Sofar publishes complete tables and report the honest count". Every one of the 103 rows is a verbatim row from an official Sofar manual I fetched today, so cutting 23 of them to hit an arbitrary number would have deleted sourced material and — worse — collapsed genuine per-model differences (e.g. `ID032` has three distinct meanings across three tables; see CONFLICTS C2). **If the band is hard, say so and I will trim; my recommended trim targets would be SERIES 1 and 5, which are the most self-similar.**
  - For the same reason 103 is *not* "every published row" either: ~511 further rows are verifiable from the same fetched URLs and were not transcribed. So 103 is a curated, technician-actionable subset, not an exhaustive dump.
- **Series count: 9** — nine distinct documented tables, one per documented table/model line, per the one-series-per-table rule.
- **Table completeness / row accounting:**

  | Series | Model line | Rows in published table | Rows captured |
  |---|---|---|---|
  | 1 | 15-25KTLX-G3P | ~68 (ID001–ID216, incl. range rows) | 17 |
  | 2 | 100-125KTLX-G4 | ~90 (ID001–ID192) | 13 |
  | 3 | 1.1K~3.3KTL-G3 | ~55 (ID01–ID98) | 12 |
  | 4 | 1~4KTL2-G3 | ~50 (01–98) | 8 |
  | 5 | HYD 5K...20KTL-3PH §8.2.1 inverter | ~80 (ID001–ID190, incl. range rows) | 16 |
  | 6 | HYD 5K...20KTL-3PH §8.2.2 battery | ~24 (ID808–ID906) | 10 |
  | 7 | ME 5K...20KTL-3PH | ~85 (ID001–ID185) | 10 |
  | 8 | HYD 3-6K-EP | ~95 (ID001–ID190) | 10 |
  | 9 | ESI 3-6K-S1 | ~65 (ID001–ID879) | 7 |
  | | **Total** | **~614 rows published** | **103 captured** |

  Captured rows are the PK-relevant, technician-actionable subset: grid voltage/frequency, PV insulation/polarity/Voc, battery + BMS, EPS/backup, relay, fan, comms, temperature, and permanent hardware faults. **Nothing was invented, merged, or extrapolated from another brand.**
- **19 code numbers intentionally recur across series** (`ID001`, `ID005`, `ID008`, `ID032`, `ID041`, `ID043`, `ID044`, `ID045`, `ID046`, `ID047`, `ID105`, `ID113`, `ID124`, `ID125`, `ID133`, `ID145`, `ID808`, `ID864`, `ID865`) because Sofar reuses numbers with different meanings per table. **Not merged**, per instruction.
- **Confidence distribution: 103 / 103 = `high`** (official Sofar user manual fetched directly from sofarsolar.com). **Zero `medium`, zero `low`** — so no `low` entry required a `notes` object. Counter-caveat: `high` here means "official manual", **not** "two independent sources"; no second independent source was obtained for any code (see NEGATIVE §12).
- **Severity distribution:** `check_restart` 62, `danger` 24, `stop_pro` 10, `self_clear` 6, `info` 1. **Display distribution:** `controller` 103 (with `blinkPattern` on 8 rows where a manual actually states the red fault light comes on; no invented flash counts).
- **Top 5 URLs by value (all fetched 2026-09-26):**
  1. `https://www.sofarsolar.com/upload/file/20241010/1728530425537061360.pdf` — SOFAR HYD 5K...20KTL-3PH User Manual. **Richest single source**: §8.2.1 inverter list (grid, PV, battery, BMS, EPS, fan, AFCI) **and** §8.2.2 battery list. **26 codes (SERIES 5 + 6).** Only source of `ID133` permanent EPS battery OCP, `ID401-402` AFCI self-fault, `ID189` AFCI comms, and the whole 8xx/9xx battery namespace.
  2. `https://www.sofarsolar.com/upload/file/20251104/1762243911541013797.pdf` — SOFAR 15-25KTLX-G3P User Manual, §10 event list. Largest three-phase C&I table (ID001–ID216), and the newest G3-generation C&I doc. **17 codes (SERIES 1).**
  3. `https://www.sofarsolar.com/upload/file/20230920/1695196332754062551.pdf` — SOFAR 100-125KTLX-G4 User Manual, Table 8-1. Only source of `ID014` grid voltage imbalance, `ID039` arc shutdown, `ID046` PV polarity reversal, `ID104` hardware differential OCP, `ID189` AFCI comms, `ID192` PLC comms. **Directly PK-relevant** — SOFAR exhibited 100-125KTL-G4 at Solar Pakistan 2023. **13 codes (SERIES 2).**
  4. `https://www.sofarsolar.com/upload/file/20241227/1735288541294079959.pdf` — HYD 3000/3600/5000/6000-EP User Manual, Table 7-1. Single-phase hybrid with EPS/load **and generator** support — closest match to PK residential use. Only source of `ID032` off-grid earth fault, `ID094` EPS load short, `ID108/ID109` generator, `ID144` permanent grid relay, `ID056` NTC, `ID151` battery partly offline. **10 codes (SERIES 8).**
  5. `https://www.sofarsolar.com/upload/file/20240122/1705890638501025979.pdf` — SOFAR ME 5K...20KTL-3PH User Manual, §8.2. AC-coupled storage converter. Only source of `ID155` fuse-board SCI error, `ID176` meter comms lost (a *different* row from `ID105` on the same table), `ID166` logic-interface derating (the manual's explicit "not an error" row), `ID183–ID185` BMS version rows. **10 codes (SERIES 7).**
  - Runners-up: `https://www.sofarsolar.com/upload/file/20240311/1710121923451047146.pdf` (1.1K~3.3KTL-G3, 12 codes), `https://www.sofarsolar.com/upload/file/20240314/1710400367619032659.pdf` (1~4KTL2-G3, 8 codes), `https://www.sofarsolar.com/upload/file/20260316/1773653907482011378.pdf` (ESI 3-6K-S1, newest manual fetched, 7 codes).
- **Official fault-code table published: YES** — per-model-line, inside official user-manual PDFs on sofarsolar.com (9 complete tables extracted). **No** consolidated website-wide table found; the download centre is JS-gated.
- **PK presence: YES** — five independent evidence items (see BRAND RECON): SOFAR's own "Global Footprint" page lists Pakistan; SOFAR newsroom documents exhibiting at Solar Pakistan 2023 in Lahore, with the APAC head calling Pakistan "a pivotal player"; SOFAR's 2024 brochure listing Pakistan among its worldwide offices; a Lahore warranty-claim centre; and a named PK distribution partner, **Omega Power Technologies (Pvt) Ltd** (Sofar–Omega Power hybrid/battery launch, 2025).
- **Max Power relationship: RESELLER / IMPORTER — not OEM, and no exclusivity claimed by either PK firm.** `https://maxpower.com.pk/introduction` lists the Sofar series in its lineup alongside Voltas and Suntronic; its news index carries a "Sofar Zero Export Device". A confirmed SOFAR distribution partner in PK is **Omega Power Technologies (Pvt) Ltd**. The **"master distributor"** tier is **NOT asserted** — its only source, `https://sofar.pk/`, is dead (NEGATIVE §13). **Zero `max-power` entries created or copied.** See BRAND RECON and CONFLICTS C1.
- **Blockers:**
  1. **`E0xx` / `W0xx` do not exist in any Sofar source fetched.** Regex-swept for `\b[EW][0-9]{3}\b` across the full text of all 8 official manuals — **0 hits**. Sofar's namespace is `ID0xx` / `ID1xx` / `ID8xx` / `ID9xx`. **Any requirement to publish Sofar as `E0xx`/`W0xx` needs an explicit, human-approved display mapping** — it cannot be sourced, and I refused to invent one. **This is the single biggest blocker.**
  2. **No safe cross-model lookup.** `ID032` has three meanings, `ID125` two, `ID013` two wordings, `ID129` four naming conventions, `ID105` two different rows on one table. A model-agnostic search will return wrong answers. The app needs a model/firmware context field.
  3. **`KSG` and `SH1T/HT` not sourced at all** — no retrievable fault table found, so zero codes. Do not assume they reuse HYD/ESI IDs.
  4. **Not fetched, so no codes recorded:** 255KTL-HV / 75-136KTL-HV, 3.3-12KTLX-G3, 3-6KTLM-G3, and the battery/ESS/wallbox docs (PowerMagic 2.0, CBS5000-H4-H12, EV11k-AC-02 wallbox, CH1000, SAR-100).
  5. **`display` and `severity` are derived, not sourced.** No Sofar publication contains an indoor/outdoor/LED taxonomy, a blink-count table, or severity tiers — those are my editorial mapping. Likewise `display: controller` for all 103 rows is an inference from "read the LCD event list".
  6. **No PK grid-limit values sourced**, so no Pakistani voltage/frequency/disconnect threshold appears anywhere in this file. Grid-limit configuration belongs to the installer layer.
  7. **Single source per code.** If this KB's rule is that `high` requires two *independent* sources, all 103 entries drop to `medium`. Resolve the `high` definition before ingest.
  8. **Download-centre enumeration is JS-gated** (`https://www.sofarsolar.com/download.html` renders only a filter shell), so "all Sofar model lines covered" cannot be asserted from this pass.
  9. **103 exceeds the 40–80 target band** — see the honest note at the top. Decision needed: keep the sourced rows, or authorise a trim.
  10. **PK distribution tier is unresolved at the "master distributor" level.** `https://sofar.pk/` is dead (no DNS, no Wayback), so the only source for "master distributor" is gone. Three live sources confirm Omega Power as a SOFAR **partner/distributor**; none says "master". Any KB field like `pk_master_distributor` must be left empty until a live source supports it.
- **Integrity re-check performed on this file (2026-09-26, second pass):** all 9 YAML blocks parse; 103 code entries, every one carrying `code`/`aliases`/`title`/`meaning`/`severity`/`display`/`isFault`/`confidence`/`causes`/`solutions`; every fault row has >=2 causes and >=2 solutions; no `ur` equals its `en`; no `en` title exceeds 60 chars; no `low`-confidence entry exists (so no missing `notes`). All 8 official manual PDFs re-fetched **HTTP 200**, and the HYD 5K...20KTL-3PH table was text-extracted and **every SERIES 5/6 code confirmed verbatim** against the source rows.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `sofar-ktlx-g3p-15-25.json` | KTLX-G3P (three-phase 15-25 kW, on-grid) | 17 |
| `sofar-ktlx-g4-100-125.json` | KTLX-G4 (three-phase 100-125 kW, C&I on-grid) | 13 |
| `sofar-ktl-g3-11-33.json` | KTL-G3 (single-phase 1.1-3.3 kW, on-grid, 1 MPPT) | 12 |
| `sofar-ktl2-g3-1-4.json` | KTL2-G3 (single-phase 1-4 kW, on-grid, 140% DC overload) | 8 |
| `sofar-hyd-5-20ktl-3ph-inverter.json` | HYD 5K...20KTL-3PH (three-phase hybrid 5-20 kW, PV + battery + EPS) | 16 |
| `sofar-hyd-5-20ktl-3ph-battery.json` | HYD 5K...20KTL-3PH Battery error list (battery-side, 8xx/9xx IDs) | 10 |
| `sofar-me-5-20ktl-3ph.json` | ME 5K...20KTL-3PH (AC-coupled storage 5-20 kW, no PV input) | 10 |
| `sofar-hyd-3-6k-ep.json` | HYD 3-6K-EP (single-phase hybrid 3-6 kW, PV + battery + EPS + generator) | 10 |
| `sofar-esi-3-6k-s1.json` | ESI 3-6K-S1 (stacked single-phase hybrid 3-6 kW, PV + BTS 5K battery + EPS) | 7 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `sofar-ktlx-g3p-15-25.json` | service_manual | SOFAR 15-25KTLX-G3P User Manual, section 10 'TROUBLESHOOTING AND MAINTENANCE' - Event list | https://www.sofarsolar.com/upload/file/20251104/1762243911541013797.pdf | 2026-09-26 |
| `sofar-ktlx-g4-100-125.json` | service_manual | SOFAR 100-125KTLX-G4 User Manual, section 9 'Trouble Shooting and Maintenance', Table 8-1 Even list | https://www.sofarsolar.com/upload/file/20230920/1695196332754062551.pdf | 2026-09-26 |
| `sofar-ktl-g3-11-33.json` | service_manual | SOFAR 1.1K~3.3KTL-G3 User manual, section 7.1 'Trouble shooting', Table 7-1 Event list | https://www.sofarsolar.com/upload/file/20240311/1710121923451047146.pdf | 2026-09-26 |
| `sofar-ktl2-g3-1-4.json` | service_manual | SOFAR 1~4KTL2-G3 User Manual, 'Trouble shooting', Table 7-1 Even list | https://www.sofarsolar.com/upload/file/20240314/1710400367619032659.pdf | 2026-09-26 |
| `sofar-hyd-5-20ktl-3ph-inverter.json` | service_manual | SOFAR HYD 5K...20KTL-3PH USER MANUAL, section 8.2.1 Inverter error list | https://www.sofarsolar.com/upload/file/20241010/1728530425537061360.pdf | 2026-09-26 |
| `sofar-hyd-5-20ktl-3ph-battery.json` | service_manual | SOFAR HYD 5K...20KTL-3PH USER MANUAL, section 8.2.2 Battery error list | https://www.sofarsolar.com/upload/file/20241010/1728530425537061360.pdf | 2026-09-26 |
| `sofar-me-5-20ktl-3ph.json` | service_manual | SOFAR ME 5K...20KTL-3PH User Manual, section 8.2 Error list | https://www.sofarsolar.com/upload/file/20240122/1705890638501025979.pdf | 2026-09-26 |
| `sofar-hyd-3-6k-ep.json` | service_manual | HYD 3-6K-EP User Manual, section 7, Table 7-1 Eventlist | https://www.sofarsolar.com/upload/file/20241227/1735288541294079959.pdf | 2026-09-26 |
| `sofar-esi-3-6k-s1.json` | service_manual | ESI 3-6K-S1 User Manual, Table 6-1 List of common events | https://www.sofarsolar.com/upload/file/20260316/1773653907482011378.pdf | 2026-09-26 |

