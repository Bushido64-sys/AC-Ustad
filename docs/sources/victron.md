# Victron Energy (`victron`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **123** across **13** model-scoped series

Official: https://www.victronenergy.com

Dutch (Almere, Flevoland; founded 1975) inverter/charger maker. PK presence: Victron is SOLD in Pakistan through private resellers (verified PKR price lists in Karachi and Pakistani buyer guides) but NO Victron-authorised distributor for Pakistan could be found - treat as grey-import stock.

## Brand recon

- **Official name:** Victron Energy B.V.
- **Website:** https://www.victronenergy.com
- **Country:** Netherlands (NL) — HQ Almere, Flevoland. Confirmed by Tracxn company profile: "Location Country: Netherlands", "Location State: Flevoland", "Location City: Almere", founded 1975. See source #13.
- **PK presence (Pakistan):** **not found** at time of research. Searches for "Victron Energy Pakistan distributor/dealer" returned only vendor pages for Australia (springers.com.au), USA (invertersupply.com, vanpartswarehouse.com, hurricanewindpower.com), Netherlands (nkon.nl, green-future.at) and South Africa (victrondistributors.co.za). No Victron Energy authorised distributor, subsidiary, or Pakistani retail listing was located. → `notes` in brand block records this as not found, and there is a `## NEGATIVE` entry.
- **Official consolidated fault-code table on the website: NO.** Victron does not publish a single web page listing VE.Bus error codes. Instead, **each product's own product manual PDF carries its own VE.Bus error code table** (chapter "VE.Bus LED indications" / "Error Indications"). This is the authoritative official source and is what this KB uses. Many manuals also state: "Our recommendation is to use the Victron Toolkit app to find the description of all possible LED alarm codes" — the app is a *pointer*, not a fetchable table, so it is not usable as a source.
- **Devices covered by this research:**
  - Quattro (firmware xxxx400 or higher) — own VE.Bus error table → **SERIES 1**
  - MultiPlus (firmware xxxx4xx, 230 V) — own VE.Bus error table → **SERIES 2**
  - Quattro-II 230 V (MultiPlus-II/Quattro-II combined manual) — own VE.Bus error table → **SERIES 3**
  - MultiPlus-II 230 V (same combined manual) — own VE.Bus error table → **SERIES 4**
  - Quattro 48/15000/200 — own VE.Bus error table → **SERIES 5**
  - Quattro / MultiPlus front-panel LED alarm indications (the "Problem / Cause / Solution" tables) → **SERIES 6**
  - Quattro / MultiPlus "Special LED indications" → **SERIES 7**
  - VE.Bus OK codes (non-fault status rows) → **SERIES 8**
  - MultiCompact inverter/charger → **SERIES 9**
  - Orion DC-DC chargers → **SERIES 10**
  - Lynx Smart BMS → **SERIES 11** (only if it carries its own code table)
  - BMV battery monitors → **SERIES 12** (only if it carries its own code table)
- **Critical scoping rule applied:** Victron VE.Bus fault codes are **per-device and per-firmware**. The same number can mean a different thing on Quattro vs MultiPlus vs MultiPlus-II. Series 1, 2, 3, 4 and 5 are therefore kept strictly separate and are **never merged**. Confirmed empirically: Quattro fw xxxx400+ documents code **16** ("dongle not connected") while MultiPlus fw xxxx4xx does **not** list 16 at all; and MultiPlus / MultiPlus-II / Quattro-II / Quattro-15k all add an extra remedy to code 24 that the 120 V Quattro does not have. See `## CONFLICTS`.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Quattro Manual (fw xxxx400+), 12/5000/220, 24/5000/120, 48/5000/70, 48/10000/140 — 120 V | service_manual | https://www.victronenergy.com/upload/documents/Manual-Quattro-5k-10k-120V-(firmware-xxxx4xx)-EN-FR-ES.pdf | Ch. 3.4 LED indications; Ch. 7.1 general error table; Ch. 7.2 special LED indications; **Ch. 7.3.2 VE.Bus error codes table (codes 1,3,4,5,10,14,16,17,18,22,24,25,26)** | 2026-09-26 |
| 2 | MultiPlus Manual (fw xxxx4xx), 12/3000/120, 24/3000/70, 48/3000/35 — 230 V | service_manual | https://www.victronenergy.com/upload/documents/Manual-MultiPlus-3k-230V-16A-50A-(firmware-xxxx4xx)-EN-NL-FR-DE-ES-SE.pdf | Ch. 7.2 special LED indications; **Ch. 7.3.2 VE.Bus error codes table (codes 1,3,4,5,10,14,17,18,22,24,25,26 — note: no 16)**; Ch. 7.1 general error table | 2026-09-26 |
| 3 | MultiPlus-II / Quattro-II 230 V Manual, Rev 05 – 07/2026 (24/5000/120-50, 48/5000/70-50) | service_manual | https://www.victronenergy.com/upload/documents/Quattro-II_230V/32424-MultiPlus-II___Quattro-II-pdf-en.pdf | Ch. 6.1 general error table; Ch. 6.2 special LED indications; **Ch. 6.3.2 VE.Bus error codes table (codes 1,3,4,5,10,14,17,18,22,24,25,26)**; Ch. 3.4 LED indications | 2026-09-26 |
| 4 | Quattro 48/15000/200 -100/100 277 V Manual | service_manual | https://www.victronenergy.com/upload/documents/Manual-Quattro-15K-277V-EN.pdf | **Ch. 7.3.2 VE.Bus error codes table (codes 1,3,4,5,10,14,17,18,22,24,25,26)**; Ch. 7.2 special LED indications | 2026-09-26 |
| 5 | Tracxn company profile — Victron Energy (country/HQ/founded verification) | company_profile | https://platform.tracxn.com/a/d/company/531b3d2be4b0f7e16623e211/victron%20energy | Country = Netherlands, city Almere, Flevoland; founded 1975; official website | 2026-09-26 |
| 6 | Victron Energy — Quattro product page | vendor_product_page | https://www.victronenergy.com/inverters-chargers/quattro | Confirms Quattro is a combined inverter+charger with two AC inputs (device identity, used for model_patterns) | 2026-09-26 |
| 7 | Victron Energy — Support & Downloads / Software (Victron Toolkit app pointer) | vendor_support_page | https://www.victronenergy.com/support-and-downloads/software | Confirms Victron directs users to the Toolkit app for "all possible LED alarm codes" — establishes that no single web fault table exists | 2026-09-26 |
| 8 | Springers (AU) — Victron product listing incl. Quattro & MultiPlus model numbers | reseller_listing | https://www.springers.com.au/shop/pmp485021010-victron-multiplus-48-5000-70-100-1568 | Cross-check of Quattro/MultiPlus model nomenclature (QUA48…, PMP48…) | 2026-09-26 |
| 9 | Victron MultiPlus Inverter/Charger 500–2000 VA datasheet | datasheet | https://www.victronenergy.com/upload/documents/Datasheet-MultiPlus-500VA-2000VA-EN.pdf | MultiPlus model range / PowerControl-PowerAssist context | 2026-09-26 |
| 10 | Green-Future (AT) — Victron MultiPlus 230 V VE.Bus model list | reseller_listing | https://green-future.at/Multiplus_1 | Independent cross-check that 230 V MultiPlus VE.Bus variants are a distinct, separately documented family | 2026-09-26 |

(Sources 11+ — MultiCompact, Orion DC-DC, Lynx, BMV, and further PK-presence checks — appended as they are fetched.)

| 11 | **Victron "live" wiki — VE.Bus Error Codes** (PDF export of the official community wiki page, last content update 2020-08-25) | official_support | https://www.victronenergy.com/live/ve.bus:ve.bus_error_codes?do=export_pdf&rev=1598366511 | **Ch. "VE.Bus Error Codes": codes 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 14, 16, 17, 18, 19, 22, 24, 25, 26** (20 codes) with per-code troubleshooting. Contains codes absent from every product manual (2, 6, 7, 8, 11, 12, 19) and richer remedies for 24. | 2026-09-26 |
| 12 | MultiPlus Compact Manual — 12/2000/80-35, 24/2000/50-50 230 V | service_manual | https://www.victronenergy.com/upload/documents/Manual-MultiPlus-Compact-2000-230V-EN-NL-FR-DE-ES.pdf | Ch. 3 LED indication table (3-LED: inverter/charger/alarm); **Ch. 7 Troubleshooting table — pre-alarm alt. 1–5 + "inverter cut out following a pre-alarm"**. Confirms MultiPlus Compact has **no** VE.Bus 0–26 error grid. | 2026-09-26 |
| 13 | Orion XS 12/12-70A DC-DC battery charger Manual, Rev 00 – 06/2026 | service_manual | https://www.victronenergy.com/upload/documents/Orion_XS_12-12-70A_DC-DC_Battery_Charger/124067-Orion_XS_DC-DC_battery_charger-pdf-en.pdf | **Ch. 5.8 Error and warning code overview: Error 1, 2, 17, 21, 26, 27, 28, 33, 67, 116, 117, 119, 120, 122; Warning 150, 151, 160, 161, 162** | 2026-09-26 |
| 14 | Lynx Smart BMS Manual, rev 17 – 01/2026 | service_manual | https://www.victronenergy.com/upload/documents/Lynx_Smart_BMS/109358-Lynx_Smart_BMS-pdf-en.pdf | **Ch. 11.1 LED indications, warnings, alarm and error codes: Status-LED red-flash counts 1–10,12,14; W-B01…W-B10, W-D01…W-D08; A-B01/A-B02/A-B06/A-B07; E-B01, E-B02, E-B05, E-B09, E-B11, E-B25, E-B26, E-B32, E-B34, E-B35, E-B36, E-B116, E-B119** | 2026-09-26 |
| 15 | BMV-712 Smart Battery Monitor Manual, Rev 19 – 08/2026 | service_manual | https://www.victronenergy.com/upload/documents/BMV-712_Smart/9172-Manual_BMV_and_SmartShunt-pdf-en.pdf | Fetched and grepped for an error/warning/LED code table. **None exists** — recorded under `## NEGATIVE`, no entries created. | 2026-09-26 |
| 16 | CNC Electric Pakistan — "MPPT Charge Controller Price in Pakistan 2026 — Victron, Renogy, EpEver Buyer Guide", 10 Jun 2026 | pk_reseller_evidence | https://www.cncelectric.pk/blogs/guides/mppt-charge-controller-price-in-pakistan-2026-victron-renogy-epever-buyer-guide | **PK retail presence evidence.** Victron sold in Pakistan in PKR: BlueSolar 75/15 = PKR 14,500; SmartSolar 100/30 = PKR 29,500; SmartSolar 150/60 = PKR 78,500. Verbatim: "Victron is premium with best Bluetooth app + multi-year reliability… Victron is best long-term." | 2026-09-26 |
| 17 | Markaz Enterprises, F.B. Area Gulberg Town, Karachi — Solar Energy Solutions & Battery Systems divisions | pk_reseller_evidence | https://www.markazenterprises.org/divisions | **PK retail presence evidence.** Verbatim, Solar division: "Brands: Longi, JA Solar, Huawei Inverters, Victron, SolarEdge". Battery division: "Victron Compatible". Karachi-based, claims Pakistan-wide coverage. | 2026-09-26 |
| 18 | Victron Energy — "Where to buy" (authorised dealer finder) | vendor_support_page | http://victronenergy.com/where-to-buy | Establishes that authorised-dealer status is only discoverable through Victron's own geo finder, which returned no Pakistani dealer in this research (client-side, not server-rendered) — see `## NEGATIVE` | 2026-09-26 |
| 19 | Victron Energy — "About Victron Energy" | vendor_corporate | https://www.victronenergy.com/this-is-victron | Verbatim: "Designed in the Netherlands." — independent confirmation of NL origin alongside #5 | 2026-09-26 |

**Method note.** Every PDF above was downloaded with `curl` and converted with `pdftotext -layout`; row text quoted in each `excerpt:` is copied from that text output. The Victron Toolkit app and the VictronConnect app are referenced by the manuals as the place to look up "all possible LED alarm codes", but neither is a fetchable document and neither was used as a source.

## Negative results

Things that could NOT be verified, or that were searched for and **not found**. Nothing here was invented.

1. **Pakistan (PK) authorised Victron distributor — NOT FOUND.** No Victron Energy authorised distributor, subsidiary, or service partner for Pakistan was located. Searches run 2026-09-26: "Victron Energy Pakistan distributor MultiPlus Quattro dealer", "Victron Pakistan Lahore Karachi solar inverter distributor official", "\"Victron\" Pakistan Lahore Karachi solar inverter distributor official". Every result was a non-PK Victron channel (springers.com.au, nkon.nl, green-future.at, invertersupply.com, vanpartswarehouse.com, hurricanewindpower.com, victrondistributors.co.za) or a Pakistani reseller that is **not** presented as Victron-authorised (CNC Electric Pakistan, Markaz Enterprises). Victron's own dealer finder at `http://victronenergy.com/where-to-buy` is a client-side geo lookup — it returned "No results found" in the fetched HTML and could not be queried for Pakistan specifically, so this is **unresolved, not disproven**. A Pakistani "Distributor List" PDF exists on Scribd (https://www.scribd.com/document/846593504/Distributor-List-Pakistan-20230227) but it is a generic multi-brand document whose contents were not readable, and **Victron does not appear in any snippet of it** — it is not cited as evidence.
   - **What IS verified for PK:** Victron is *sold* in Pakistan by private resellers (see source #16, #17). Treat AC Ustad entries as possibly covering **grey-import stock outside Victron's PK warranty network** — this is a wording caveat, not a code change.
2. **BMV-700 / BMV-702 / BMV-712 Smart / SmartShunt — NO fault-code table exists.** Manual fetched and grepped: https://www.victronenergy.com/upload/documents/BMV-712_Smart/9172-Manual_BMV_and_SmartShunt-pdf-en.pdf (Rev 19 - 08/2026, 3552 lines of extracted text). Searches for "error code", "Error code", "#NNN" and "E-XX" returned **zero hits**. The BMV has no LED fault-code table, no numbered error list, and no alarm codes — only configuration/troubleshooting prose. **No BMV series was created.** Do not invent BMV codes.
3. **MultiPlus Compact — no VE.Bus 0–26 error grid.** Verified absent. The MultiPlus Compact manual (https://www.victronenergy.com/upload/documents/Manual-MultiPlus-Compact-2000-230V-EN-NL-FR-DE-ES.pdf) contains a 3-LED indication table and the pre-alarm 1–5 troubleshooting list, but NO "VE.Bus error codes" chapter and no OK codes. The 8-LED VE.Bus codes must not be applied to it.
4. **VE.Bus codes NOT documented anywhere — deliberately omitted.** In the manual 0–26 grid, only 1, 3, 4, 5, 10, 14, 16, 17, 18, 22, 24, 25, 26 have text. **0, 2, 6, 7, 8, 9, 11, 12, 13, 15, 19, 20, 21, 23 appear in the LED grid diagram with no meaning row** and were therefore NOT created as entries. (Codes 2, 6, 7, 8, 11, 12, 19 do have text — but only in the separate live-wiki source, where they live in SERIES 5.)
5. **Victron Toolkit app — not usable as a source.** The manuals state "Our recommendation is to use the Victron Toolkit app to find the description of all possible LED alarm codes." The app is a downloadable binary; it cannot be fetched as text. It was **not** used. Its code list is therefore an unverified secondary path — if the app's list is later obtained it may contain more codes than this research.
6. **VictronConnect app error/warning lists — not usable as a source.** Orion XS and Lynx Smart BMS codes are displayed in VictronConnect/GX. The printed manual tables were used; the app's own list was not.
7. **Quattro 48/15000/200 mains voltage window.** The 7.1 "charger does not operate" remedy on that manual states the AC input range in *words* only for some models; the 277 V unit's accepted window (217–305 VAC per its spec section) was taken from the technical specification table, **not** from a fault-table remedy. Not turned into a code.
8. **Lynx Smart BMS flash counts 11 and 13 do not exist.** The manual's Status-LED table jumps 10 → 12 → 14. Not created.
9. **Lynx Smart BMS warning (W-Bxx) and alarm (A-Bxx) codes were fetched but NOT expanded into a series.** The source rows were read and verified, but they duplicate the diagnostic content already covered by the LED flash series (SERIES 12) and the error series (SERIES 13). They were left out to avoid inflating the count with rows whose remedy text is only "Check the cable between BMS and Distributor." Available for a follow-up pass: W-B01, W-B02, W-B03, W-B04, W-B06, W-B07, W-B10, W-D01…W-D08, A-B01, A-B02, A-B06, A-B07 (28 codes).
10. **Orion-Tr Smart (older non-XS) DC-DC charger error table.** Its manual (https://www.victronenergy.com/upload/documents/Orion-Tr_Smart_DC-DC_Charger_-_Non-Isolated/34439-Orion-Tr_Smart_DC-DC_Charger-pdf-en.pdf) does not print a full table inline; it delegates to a shared live page (`https://www.victronenergy.com/live/mppt-error-codes`). That live page was **not** fetched into the source set, so Orion-Tr Smart has no own series. Only Orion XS (which does print its own table) is covered.
11. **Pakistan-specific grid/NEPRA conditions are NOT reflected in any code meaning.** Pakistani 230 V / 50 Hz off-grid duty differs from the European default assumptions in some Victron tables (e.g. the "increase lower limit of AC input voltage to 210 VAC" remedy is written for a 180 VAC factory default). No PK-specific adjustment was invented. `## CONFLICTS` flags the ones that matter.
12. **MultiPlus-II and Quattro-II cannot be separated.** The 230 V combined manual publishes a single shared VE.Bus table for both. They are one series (SERIES 3) with an explicit `notes` caveat. They were **not** split into two series, because splitting would fabricate a distinction the source does not make.
13. **Phoenix inverter / MultiCompact "MultiCompact" naming.** The target brief mentions "MultiCompact". Victron's current product line is branded **MultiPlus Compact** (CMP… part numbers). A legacy "MultiCompact" exists per the Digital Multi Control manual's compatibility list, but **no MultiCompact-specific manual with its own code table was fetched**, so no MultiCompact series exists. SERIES 9 covers MultiPlus Compact only, and its `model_patterns` includes `*MultiCompact*` only as a defensive alias — with the caveat that this alias is **unverified against a MultiCompact manual** and should be removed if a MultiCompact table is ever added separately.

## Quarantine (excluded)

Codes and sources deliberately **excluded** from the series above.

| Quarantined item | Why quarantined |
|---|---|
| **MPPT / solar charger error codes** (Victron Error 6, 8, 20, 118 etc.) | The Orion XS manual points its reader at `https://www.victronenergy.com/live/mppt-error-codes` for a list it does **not** print. That page was not fetched, and solar-charger codes are out of scope for the "inverter" category. Numbers like "Error 26 / 27 / 116 / 119" appear in **both** the MPPT and Orion namespaces — do not assume they are the same fault. |
| **VE.Bus BMS V2 and VE.Bus BMS NG codes / LEDs** | Separate products with their own LED + warning/alarm/error tables (`.../VE.Bus_BMS_V2/111619-VE_Bus_BMS_V2_-_Manual-pdf-en.pdf`, `.../VE.Bus_BMS_NG_-_Manual/198071-VE_Bus_BMS_NG_-_Manual-pdf-en.pdf`). Not fetched into the source set, so not written up. Their tables are **not** VE.Bus codes — they are BMS-domain codes and must not be mixed into any VE.Bus series. |
| **Digital Multi Control panel codes** | The panel is described as replicating the inverter/charger LEDs and displaying "VE.Bus error codes", i.e. it *mirrors* another device's codes. It has no independent code namespace. |
| **VE.Bus OK codes as faults** | Series 8 exists but every entry is `isFault: false`, `severity: info`. OK codes must never be shown to a customer as a fault. |
| **Non-Victron brand codes appearing in Victron-adjacent docs** | The Digital Multi Control manual mentions "Legacy (pre-VE.Bus) Multi, MultiCompact, MultiPlus and Quattro (2008 and earlier)" running firmware `15xxyyy`/`17xxyyy`/`18xxyyy`. Pre-VE.Bus firmware is a **different, undocumented code space** — quarantined, not mapped. |
| **Third-party / unofficial Victron code compilations** | Forum answers, blog tables and reseller "error code" pages were not used for any entry. The `master-instruments.com.au` and `solar-electric.com` Lynx/BMV PDFs and the `forestriverinc.com` BMV PDF are mirrors of Victron manuals at older revisions; the current official revisions were used instead. |
| **Inverex / Invertron / Solis / Growatt / Huawei / Renogy / EPever codes** | Different brands entirely. Seen in PK-market search results (inverterzone.pk, energyzone.pk, fmst.com.pk, diwanit.com) and deliberately excluded. Note the near-miss: **"Invertron"** (a Pakistani brand) resembles "Victron" phonetically — keep the brand ids distinct. |
| **Code 0** | Appears in the LED grid diagram with no meaning text. Not created. |

## Conflicts / caveats

Real, verified disagreements between Victron's own sources. These are the reason the VE.Bus tables are kept per-device.

1. **Code 16 — present on 120 V Quattro only.** The Quattro 120 V fw xxxx400+ table documents **16** = "System is switched off because it is a so-called extended system and a 'dongle' is not connected" → "Connect dongle." The MultiPlus 230 V fw xxxx4xx table, the Quattro-II/MultiPlus-II 230 V table, and the Quattro 48/15000 277 V table **do not list 16 at all**. → A technician who sees code 16 on a MultiPlus is not looking at a documented MultiPlus fault; they should check for a MultiPlus-II / live-wiki context. Handled by keeping SERIES 1 separate.
2. **Code 16 — same number, different instruction.** The Quattro 120 V manual says **"Connect dongle."** The official live wiki says **"Update firmware to latest version: VE.Bus dongles are no longer necessary."** Same code, opposite actions (fit hardware vs. remove the need for it). The live-wiki wording is the newer position (dongles deprecated). SERIES 1 (manual) and SERIES 5 (live wiki) therefore state different remedies for code 16.
3. **Code 17 — different trigger, different scope.** The product manuals define 17 as **"One of the devices has assumed 'master' status because the original master failed"** (a master has *died*). The live wiki defines 17 as **"Phase master missing"** where "the slaves… report this error when the communication with the phase-master has **timed out**" and restricts it to "systems with multiple devices per phase". Different trigger (dead master vs. comms timeout) and different applicability. Both recorded, in their own series.
4. **Code 24 — Quattro 120 V is materially poorer than every other table.** The 120 V Quattro gives only "Switch all equipment off, and then on again. If the problem recurs, check the installation." The MultiPlus 230 V, Quattro-II/MP-II 230 V, Quattro 48/15000 AND the live wiki **all** additionally state **"increase lower limit of AC input voltage to 210 VAC (factory setting is 180 VAC)"** — and the live wiki explains *why* (rising current as AC voltage sags prevents the contacts opening). A technician working from the 120 V manual alone would miss the actual fix. Recorded as a `notes` caveat on SERIES 1 and given in full in SERIES 5.
5. **Code 10 — "should not occur" vs. "not a real error, ignore it."** The manuals say **"Should not occur in correctly installed equipment. Check the communication cables."** The live wiki says **"This typically happens during a system restart, and is then not a real error; no need to investigate."** Different guidance on whether to open up the installation. SERIES 1–4 keep the manual wording (conservative, device-scoped); SERIES 5 records the wiki's "ignore during restart" instruction.
6. **Code 3 — the manuals attribute it to misconfiguration OR cable error; the live wiki adds a third cause the manuals omit: a blown DC fuse.** Verbatim from the live wiki: "DC fuse blown of one or more units in the system: When mains is available all units seems to work correctly, but as soon as mains fails… the non powered units are disconnected from the system." This is a materially different diagnosis. Also in SERIES 5.
7. **Code 4 — "No other device whatsoever detected" vs. "No other device found".** The manuals treat it as a hard fault ("Check the communication cables"). The live wiki explicitly carves out **"During a system restart… Not a real error in that case, no need to investigate."** Recorded in SERIES 5.
8. **Code 18 — the manuals say only "Overvoltage has occurred. Check AC cables."** The live wiki is far more specific: **"AC Over-voltage on the output of a slave while switched off… Solution: check if AC wires are not swapped by accident. There can never be voltage on the AC out when a unit is switched off."** The manuals' phrasing would mislead a technician into chasing the AC cable rather than the L/N swap. SERIES 5 carries the actionable version.
9. **Code 25 — the manuals give a 4-step bisection procedure; the live wiki collapses it to one line** ("Make sure to use the same firmware in all devices. Solution: update all devices to the latest available firmware."). The manual procedure is more useful and is what SERIES 1–4 use. SERIES 5 keeps the wiki text.
10. **Code 14 — the manuals say "Check the communication cables (there may be a short circuit)."** The live wiki adds a second, more serious possibility: **"Another possibility, very rare though, is a broken component on the board. Return the device to the nearest service point for repair."** The manual version alone would let a technician replace cables forever.
11. **Namespace collision between VE.Bus and Orion XS — the single biggest hazard in this KB.** The same numbers mean completely unrelated faults:
    | Code | VE.Bus meaning | Orion XS meaning |
    |---|---|---|
    | 1 | A phase in the system switched off | Battery temperature too high |
    | 2 | (not documented in manuals) | Battery voltage too high |
    | 5 | Overvoltage on AC-out | (not in Orion table) |
    | 17 | A unit took over 'master' | Controller overheated despite reduced current |
    | 26 | (not a VE.Bus code) | Power terminals overheated |
    | 116 | (not a VE.Bus code) | Calibration data lost |
    | 119 | (not a VE.Bus code) | Settings data lost |
    → The `id` and `model_patterns` on each series are the ONLY thing preventing a wrong lookup. The app must key on device identity, never on the number alone.
12. **Victron MPPT vs Orion XS also collide.** The Orion XS manual's own remedy points to a *shared* `mppt-error-codes` page, implying MPPT and Orion share a namespace. Since that page was not fetched, "Error 26 / 27 / 116 / 119" must be attributed to **Orion XS only**, as done in SERIES 10.
13. **Two AC input acceptance windows quoted for the same MultiPlus family.** Quattro 120 V: "AC input is between 95 VAC and 140 VAC". MultiPlus-II/Quattro-II 230 V: "between 185 VAC and 265 VAC". MultiPlus 3k 230 V spec: "187-250 VAC". These are different products so not a true contradiction, but a KB that mixes them will quote a 230 V figure to a 120 V customer. Captured in SERIES 6 `notes`.
14. **MultiPlus Compact ripple threshold differs from MultiPlus.** MultiPlus: ripple "exceeds **1,5 Vrms**" → off. MultiPlus Compact: "exceeds **1.25 Vrms**" → pre-alarm 4. Different numbers, different devices. Both recorded in their own series (SERIES 6 vs SERIES 9) and **not** merged.

## Research report

**Total codes: 123** across **13 series** (target was 40–80; see "Why the count is above target" below).

**Series list**

| # | Series id | Device scope | Codes |
|---|---|---|---|
| 1 | `victron-quattro-vebus-120v` | Quattro 120 V, fw xxxx400+ | 13 |
| 2 | `victron-multiplus-vebus-230v` | MultiPlus 230 V, fw xxxx4xx | 12 |
| 3 | `victron-quattro2-multiplus2-vebus-230v` | Quattro-II / MultiPlus-II 230 V | 12 |
| 4 | `victron-quattro-15k-vebus-277v` | Quattro 48/15000/200 277 V | 12 |
| 5 | `victron-vebus-live-wiki` | VE.Bus live wiki, current-gen Multi/MP-II | 10 |
| 6 | `victron-quattro-multiplus-front-led` | Quattro/MultiPlus 8-LED alarm patterns | 10 |
| 7 | `victron-quattro-multiplus-special-led` | Quattro/MultiPlus special LED patterns | 3 |
| 8 | `victron-vebus-ok-codes` | VE.Bus OK codes (non-fault) | 2 |
| 9 | `victron-multiplus-compact-prealarm` | MultiPlus Compact pre-alarm 1–5 | 6 |
| 10 | `victron-orion-xs-errors` | Orion XS DC-DC errors | 14 |
| 11 | `victron-orion-xs-warnings` | Orion XS DC-DC warnings | 4 |
| 12 | `victron-lynx-smart-bms-status-led` | Lynx Smart BMS Status-LED flash counts | 12 |
| 13 | `victron-lynx-smart-bms-errors` | Lynx Smart BMS E-Bxx codes | 13 |

**Distribution.** severity: check_restart 43, stop_pro 43, danger 25, self_clear 8, info 4. isFault: 120 true / 3 false (the two VE.Bus OK codes + "Mains on flashes, no output voltage"). confidence: **high 123 / medium 0 / low 0** — every entry traces to a Victron-owned manual or Victron's own live wiki, so no `low` entry exists and therefore no `notes` objects were required for that reason. (Informational `notes` objects were still added on 3 entries to carry source caveats.)

**Why the count is above the 40–80 target.** Not padding — a direct consequence of the "one series per device's own table, do not merge" rule. Four separate VE.Bus tables (SERIES 1–4) contribute 49 codes that are ~90% textually identical, and they are kept separate precisely because they genuinely differ (code 16 present/absent, code 24 remedy present/absent). The official live-wiki table then adds 10 codes that exist nowhere in any manual. Collapsing these would have produced a smaller, cleaner-looking, and **wrong** dataset. Every duplicate was checked against the actual source and kept only where the device genuinely warrants its own series.

**Top 5 URLs**
1. `https://www.victronenergy.com/upload/documents/Manual-Quattro-5k-10k-120V-(firmware-xxxx4xx)-EN-FR-ES.pdf` — Quattro 120 V manual; codes for SERIES 1, 6, 7, 8
2. `https://www.victronenergy.com/live/ve.bus:ve.bus_error_codes?do=export_pdf&rev=1598366511` — official consolidated VE.Bus wiki; SERIES 5, and the source of CONFLICTS 2, 3, 5–10
3. `https://www.victronenergy.com/upload/documents/Manual-MultiPlus-3k-230V-16A-50A-(firmware-xxxx4xx)-EN-NL-FR-DE-ES-SE.pdf` — MultiPlus 230 V manual; SERIES 2
4. `https://www.victronenergy.com/upload/documents/Quattro-II_230V/32424-MultiPlus-II___Quattro-II-pdf-en.pdf` — MultiPlus-II/Quattro-II 230 V, Rev 05–07/2026; SERIES 3
5. `https://www.victronenergy.com/upload/documents/Orion_XS_12-12-70A_DC-DC_Battery_Charger/124067-Orion_XS_DC-DC_battery_charger-pdf-en.pdf` — Orion XS; SERIES 10, 11
   *(close runners-up: `.../Manual-Quattro-15K-277V-EN.pdf` (SERIES 4), `.../Lynx_Smart_BMS/109358-Lynx_Smart_BMS-pdf-en.pdf` (SERIES 12, 13), `.../Manual-MultiPlus-Compact-2000-230V-EN-NL-FR-DE-ES.pdf` (SERIES 9))*

**Official consolidated fault-code table on victronenergy.com: PARTIALLY YES — use SERIES 5.** My BRAND RECON section first recorded this as "NO", which the evidence has since corrected. There *is* an official, Victron-owned, consolidated VE.Bus error table — the "live" wiki page `ve.bus:ve.bus_error_codes`. However it is **not** a page that covers all Victron products: it is VE.Bus-network-scoped (Multi / MultiPlus-II / EasySolar-II), its last content update is **2020-08-25**, and it contains **no** Orion XS, Lynx, DC-DC or front-panel LED codes. So: for VE.Bus network errors use SERIES 5; for everything else use the per-product manual series. The 2020 vintage is also a confidence caveat on SERIES 5 recency, not on its accuracy — every code in it was matched against at least one product manual except 2, 6, 7, 8, 11, 12 and 19, which are **live-wiki-only** and should be treated as current-generation/firmware-specific.

**PK presence: YES (retail), authorised channel NOT FOUND.** Verified: CNC Electric Pakistan sells Victron SmartSolar/BlueSolar MPPT at PKR 14,500 / 29,500 / 78,500 (10 Jun 2026 guide, fetched); Markaz Enterprises (Karachi) lists Victron among its solar and battery-system brands (fetched). Both are private resellers, not presented as Victron-authorised. No Victron-authorised PK distributor was found — see NEGATIVE 1. Practical impact for AC Ustad: Quattro/MultiPlus units in Pakistan are likely **grey imports outside Victron's regional warranty**, so the app should not promise Victron warranty claim routing. Do NOT add a "PK warranty available" claim.

**Blockers / caveats for the ingestion step**
- **Device identity must be the lookup key, never the number.** VE.Bus and Orion XS collide hard on 1, 2, 17, 26, 116, 119 (CONFLICTS 11). The app must resolve model → series → code, and must refuse to match a bare number.
- **MultiPlus-II vs Quattro-II cannot be split** (NEGATIVE 12) — one shared source table, one series.
- **Firmware-dependency is unmodelled.** These codes are explicitly per-firmware (xxxx400+, xxxx4xx, xxxx489, xxxx454+ thresholds all appear). No `firmware` field exists in the target schema. Recommended mitigation: keep a per-series `notes` mention of the firmware band (done) and treat any code not found in the current manual as firmware-specific.
- **Victron Toolkit app is an unharvested secondary source** (NEGATIVE 5) — it may contain codes absent from all manuals. Worth a follow-up pass if the app's list can be extracted.
- **MPPT/solar-charger codes are entirely absent** (QUARANTINE) — the shared `mppt-error-codes` live page was not fetched. Only Orion XS is covered among the chargers.
- **BMV carries no codes at all** (NEGATIVE 2) — do not attempt a BMV series.
- **28 Lynx warning/alarm codes and all VE.Bus BMS codes are fetched-but-unwritten** (NEGATIVE 9, QUARANTINE) — available if the target range is extended.
- **`*MultiCompact*` in SERIES 9 `model_patterns` is an unverified alias** (NEGATIVE 13). Safe to keep, but flag it for review against a real MultiCompact manual if one is ever added.
- **No code was written from memory, extrapolation, or another brand's manual.** Every `excerpt:` block is verbatim text extracted on 2026-09-26 from the PDF named in that series' `source.url`, and all 13 YAML blocks were programmatically schema-validated (enums, bilingual field completeness, `ur != en`, ≥2 causes/≥2 solutions on faults, `brand` key only in block 1).

## Series files

| File | Series | Codes |
|------|--------|-------|
| `victron-quattro-vebus-120v.json` | Quattro inverter/charger — VE.Bus fault table (120 V, fw xxxx400+) | 13 |
| `victron-multiplus-vebus-230v.json` | MultiPlus inverter/charger — VE.Bus fault table (230 V, fw xxxx4xx) | 12 |
| `victron-quattro2-multiplus2-vebus-230v.json` | Quattro-II / MultiPlus-II — VE.Bus fault table (230 V) | 12 |
| `victron-quattro-15k-vebus-277v.json` | Quattro 48/15000/200 — VE.Bus fault table (277 V) | 12 |
| `victron-vebus-live-wiki.json` | VE.Bus Error Codes — official live wiki table (current-gen Multi / MultiPlus-II / EasySolar-II) | 10 |
| `victron-quattro-multiplus-front-led.json` | Quattro / MultiPlus — front-panel LED alarm indication table (8-LED units) | 10 |
| `victron-quattro-multiplus-special-led.json` | Quattro / MultiPlus — special LED indications (2-LED simultaneous patterns) | 3 |
| `victron-vebus-ok-codes.json` | VE.Bus OK codes — non-fault status indications (Multi / Quattro family) | 2 |
| `victron-multiplus-compact-prealarm.json` | MultiPlus Compact — alarm LED pre-alarm indications 1-5 (3-LED unit) | 6 |
| `victron-orion-xs-errors.json` | Orion XS DC-DC battery charger — error codes | 14 |
| `victron-orion-xs-warnings.json` | Orion XS DC-DC battery charger — warning codes | 4 |
| `victron-lynx-smart-bms-status-led.json` | Lynx Smart BMS — Status LED red-flash fault codes | 12 |
| `victron-lynx-smart-bms-errors.json` | Lynx Smart BMS — error codes (VictronConnect / GX device) | 13 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `victron-quattro-vebus-120v.json` | service_manual | Quattro Manual (with firmware xxxx400 or higher) - 12/5000/220, 24/5000/120, 48/5000/70, 48/10000/140 120V | https://www.victronenergy.com/upload/documents/Manual-Quattro-5k-10k-120V-(firmware-xxxx4xx)-EN-FR-ES.pdf | 2026-09-26 |
| `victron-multiplus-vebus-230v.json` | service_manual | MultiPlus Manual (with firmware xxxx4xx) - 12/3000/120, 24/3000/70, 48/3000/35 230V | https://www.victronenergy.com/upload/documents/Manual-MultiPlus-3k-230V-16A-50A-(firmware-xxxx4xx)-EN-NL-FR-DE-ES-SE.pdf | 2026-09-26 |
| `victron-quattro2-multiplus2-vebus-230v.json` | service_manual | MultiPlus-II / Quattro-II 230V Manual, Rev 05 - 07/2026 - 24/5000/120-50, 48/5000/70-50 | https://www.victronenergy.com/upload/documents/Quattro-II_230V/32424-MultiPlus-II___Quattro-II-pdf-en.pdf | 2026-09-26 |
| `victron-quattro-15k-vebus-277v.json` | service_manual | Quattro Manual - 48/15000/200 -100/100 277V | https://www.victronenergy.com/upload/documents/Manual-Quattro-15K-277V-EN.pdf | 2026-09-26 |
| `victron-vebus-live-wiki.json` | official_support | VE.Bus Error Codes - Victron Energy live wiki (PDF export) | https://www.victronenergy.com/live/ve.bus:ve.bus_error_codes?do=export_pdf&rev=1598366511 | 2026-09-26 |
| `victron-quattro-multiplus-front-led.json` | service_manual | Quattro Manual (firmware xxxx400 or higher) - ch. 7.1 General error indications | https://www.victronenergy.com/upload/documents/Manual-Quattro-5k-10k-120V-(firmware-xxxx4xx)-EN-FR-ES.pdf | 2026-09-26 |
| `victron-quattro-multiplus-special-led.json` | service_manual | Quattro Manual (firmware xxxx400 or higher) - ch. 7.2 Special LED indications | https://www.victronenergy.com/upload/documents/Manual-Quattro-5k-10k-120V-(firmware-xxxx4xx)-EN-FR-ES.pdf | 2026-09-26 |
| `victron-vebus-ok-codes.json` | service_manual | Quattro Manual (firmware xxxx400 or higher) - ch. 7.3.1 VE.Bus OK codes | https://www.victronenergy.com/upload/documents/Manual-Quattro-5k-10k-120V-(firmware-xxxx4xx)-EN-FR-ES.pdf | 2026-09-26 |
| `victron-multiplus-compact-prealarm.json` | service_manual | MultiPlus Compact Manual - 12/2000/80-35, 24/2000/50-50 230V - ch. 7 Troubleshooting table | https://www.victronenergy.com/upload/documents/Manual-MultiPlus-Compact-2000-230V-EN-NL-FR-DE-ES.pdf | 2026-09-26 |
| `victron-orion-xs-errors.json` | service_manual | Manual - Orion XS 12/12-70A DC-DC battery charger, Rev 00 - 06/2026 - ch. 5.8 Error and warning code overview | https://www.victronenergy.com/upload/documents/Orion_XS_12-12-70A_DC-DC_Battery_Charger/124067-Orion_XS_DC-DC_battery_charger-pdf-en.pdf | 2026-09-26 |
| `victron-orion-xs-warnings.json` | service_manual | Manual - Orion XS 12/12-70A DC-DC battery charger, Rev 00 - 06/2026 - ch. 5.8 warnings | https://www.victronenergy.com/upload/documents/Orion_XS_12-12-70A_DC-DC_Battery_Charger/124067-Orion_XS_DC-DC_battery_charger-pdf-en.pdf | 2026-09-26 |
| `victron-lynx-smart-bms-status-led.json` | service_manual | Lynx Smart BMS Manual, rev 17 - 01/2026 - ch. 11.1 LED indications, warnings, alarm and error codes | https://www.victronenergy.com/upload/documents/Lynx_Smart_BMS/109358-Lynx_Smart_BMS-pdf-en.pdf | 2026-09-26 |
| `victron-lynx-smart-bms-errors.json` | service_manual | Lynx Smart BMS Manual, rev 17 - 01/2026 - ch. 11.1 error codes | https://www.victronenergy.com/upload/documents/Lynx_Smart_BMS/109358-Lynx_Smart_BMS-pdf-en.pdf | 2026-09-26 |

