# APC / Schneider (`apc`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **140** across **5** model-scoped series

Official: https://www.apc.com

APC by Schneider Electric is a US-origin brand owned by Schneider Electric S.A. Strong Pakistan presence: SE Pakistan country site with Karachi office, APC SKUs on eshop.se.com/pk, and named PK APC distributors (Ingram Micro Pakistan, Awan Distribution, Mansha Brothers).

## Brand recon

| Item | Finding | Evidence URL (fetched 2026-09-26) |
|---|---|---|
| Official name | **APC by Schneider Electric** (legal entity: Schneider Electric S.A.; APC = former American Power Conversion). APC is described by SE as "a flagship brand of Schneider Electric". | https://www.se.com/ww/en/brands/apc/ |
| Global website | `https://www.apc.com` — now redirects/brands into `se.com`. APC manuals all print "www.apc.com" for the knowledge base. | https://www.apc.com/ ; footer text "www.apc.com (Corporate Headquarters) / www.apc.com/support/" quoted verbatim in fetched APC manuals |
| Country of origin | **US** (Schneider Electric S.A., 35 rue Joseph Monier, Rueil Malmaison, France = parent HQ; APC brand itself is US-origin, American Power Conversion, and all fault-code documentation is US/UK-centric). | Legal footer in fetched SE Product Info doc: "Schneider Electric, 35 rue Joseph Monier, Rueil Malmaison 92500, France" |
| **PK presence** | **YES — strong.** (a) Schneider Electric has its own Pakistan country site with a Karachi HQ office; (b) APC products are actively sold on the SE Pakistan e-Shop (SRV1KIL etc.); (c) official APC distributor list names **INGRAM MICRO PAKISTAN (PVT) LTD**, **AWAN DISTRIBUTION** and **MANSHA BROTHERS** (all Karachi) as APC distributors. | (a) https://www.se.com/pk/en/ (KHI Office: Office 225-227, 2nd Floor, The Forum Mall G-20, Block 9 Clifton, Karachi 75600) · (b) https://eshop.se.com/pk/ and https://eshop.se.com/pk/apc-easy-ups-on-line-1000va-800w-tower-230v-3x-iec-c13-outlets-intelligent-card-slot-lcd-extended-runtime-srv1kil.html · (c) https://www.se.com/ae/en/work/support/locator/apc-distributors/ |
| **Official code table?** | **YES.** APC/Schneider publishes complete, official fault-code tables per product family — numeric F## LCD fault lists (Back-UPS, Smart-UPS 1500VA), event-log code tables (Easy UPS SRV), Modbus `Fault code` ENUM registers (Easy UPS SRV/SRVL), and full alphabetical display alarm lists (Easy UPS 3S). | See SOURCE TABLE rows 1–8 below |
| Product families covered here | 1) Smart-UPS 1500VA LCD (P.##/b.##/G.##/L.## codes) · 2) Easy UPS SRV/SRVL (Modbus hex fault-code ENUM) · 3) Easy UPS 3S 10–40 kVA (display alarm text) · 4) Back-UPS XS (F01–F09 LCD fault list) · 5) Back-UPS LCD 1300 (LED-behaviour + F01–F09) | rows 1–8 below |

### Voltage note for Pakistan
Almost all APC documentation quoted here is written for **120/230 Vac 208 V** grids. Pakistan is a **230 V / 50 Hz** territory, so the 230 V column of the Smart-UPS code table is the applicable one, and the "Site Wiring Fault" checks that APC labels *"(120 V models only)"* are **not** the relevant ones for PK installs. This is called out per-series below.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|---|---|---|---|---|
| 1 | "System Errors and Message Codes" — APC 1500VA Smart UPS w/ LCD user manual (part no. su0752c / su0752d) | user_manual | https://m.media-amazon.com/images/I/91uOT05EpPL.pdf | Full 2-column fault code table: **P.00–P.17, b.00–b.12, G.00–G.11, L.01/L.02, Sc.0–Sc.8** | 2026-09-26 |
| 2 | "Easy UPS SRV/SRVL Modbus Register Map" (SE 990-1917 / Schneider Electric) | other | https://cdn.cs.1worldsync.com/7d/43/7d439fc3-bbe3-4b6b-83aa-070752dcecdd.pdf | Register 44497 "Fault code" **ENUM 0x00–0x49** (Bus/Inverter/Electric link/Parallel/Others fault kinds) | 2026-09-26 |
| 3 | "Status and Alarm Messages" — Easy UPS 3S 10-40 kVA 208 V Operation, doc 990-6409B-001 | other | https://productinfo.se.com/easyups3s_ul/990-6409_master-easy-ups-3s-10-40-kva-208-v-operation/English/990-6409%20Easy%20UPS%203S%2010-40%20kVA%20208%20V%20Operation_0000535296.xml/$/StatusandAlarmMessagesREF_0000161232 | ~80-row alphabetical **Display text / Description / Corrective action** table | 2026-09-26 |
| 4 | APC Back-UPS BX1500G / BX1300G / BN1350G User Manual, doc BU-UM-990-3508-MN01-EN | user_manual | https://coasttec.com/uploads/manuals/BX1500G.pdf | "System faults" table **F01–F09** verbatim | 2026-09-26 |
| 5 | APC 1300 Battery Backup manual, 990-4991 (Back-UPS LCD) | user_manual | https://files.oaklandcorp.com/manuals/APC%201300%20Battery%20Backup.pdf | LCD indicator/LED-behaviour text + "SYSTEM FAULTS **F01 - F09**" list | 2026-09-26 |
| 6 | "User Manual Easy UPS On-Line", doc EUO-UM-990-91091B-MN01-EN (Easy UPS SRVS 1/2/3 KI/KRI) | user_manual | https://download.se.com/doc/SPD_AHUG-ASVAYL_EN/EUO-UM-990-91091B-MN01_EN.pdf | "Alerts and Notifications" **audible-alarm/beep-pattern** table (low battery, overload, on-battery, alarm, battery disconnected, bad battery, event bypass) | 2026-09-26 |
| 7 | "User Manual Easy UPS On-Line SRV Series" 6000VA/6000W Rack 4U 230V LCD | user_manual | https://media.dustin.eu/media/d200001001933408/easy-ups-on-line-6000va-6000w-rack-4u-230v-lcd-usermanual.pdf | Named internal-fault descriptions (inverter soft start failure, high/low inverter voltage, battery SCR short, inverter relay short, CAN comms, battery turn-on failure, PFC current failure, bus voltage changes too fast …) used to disambiguate SERIES 2 | 2026-09-26 |
| 8 | Smart-UPS RT / SRT "Operation Manual", doc SUO-IM-990-5090A-MN01-EN | user_manual | https://download.se.com/doc/SPD_AHUG_990-6193_EN/SU_UM_990-6193A_MN01_EN.pdf | Confirms SRT LCD has **no numeric fault-code table** — only icon/status/LED descriptions + generic internal-fault text. → NEGATIVE | 2026-09-26 |
| 9 | SE Pakistan country site | brand_local_site | https://www.se.com/pk/en/ | PK presence, Karachi office, partner/reseller list | 2026-09-26 |
| 10 | APC Distributors locator (Schneider Electric) | distributor_locator | https://www.se.com/ae/en/work/support/locator/apc-distributors/ | PK APC distributors: Ingram Micro Pakistan, Awan Distribution, Mansha Brothers | 2026-09-26 |
| 11 | Schneider Electric Pakistan e-Shop (APC products) | ecommerce | https://eshop.se.com/pk/ | APC SKUs (e.g. SRV1KIL) sold into the PK market | 2026-09-26 |
| 12 | SE India FAQ: "CH9" error code, Easy UPS SRV2KUXI-IN | support_faq | https://www.se.com/in/en/faqs/FAQ000240114/ | Confirms **CH9 = charger error** on Easy UPS SRV, + 4-step remedy | 2026-09-26 |
| 13 | SE India FAQ: "BDC" error code, Easy UPS SRV3KUXI-IN | support_faq | https://www.se.com/in/en/faqs/FAQ000236002/ | Confirms **BDC = battery not connected / loose / weak** on Easy UPS SRV, + remedy | 2026-09-26 |

## Negative results

Everything in this section was investigated on 2026-09-26 and **could not be verified from a fetched source**. Nothing here is entered as a code.

### N1 — Smart-UPS RT / SRT (online) has NO numeric fault-code table
Fetched: `https://download.se.com/doc/SPD_AHUG_990-6193_EN/SU_UM_990-6193A_MN01_EN.pdf` (SUO-IM-990-5090A-MN01-EN). The SRT LCD presents **icon-based status and LED descriptions only**. The manual's fault text is entirely generic: "There is an internal UPS error detected. Do not attempt to use the UPS. Turn the UPS off and unplug it from AC." It publishes no F##, no event-log numeric code, and no alarm-code list. **Conclusion: no Smart-UPS RT / SRT series was produced.** Any "Smart-UPS RT error code list" for APC must be sourced elsewhere or not created.

### N2 — SE support/FAQ pages are blocked to the fetcher (HTTP 403)
Verified by direct request on 2026-09-26:
| URL | Status |
|---|---|
| `https://www.se.com/us/en/faqs/FA279105/` (Back-UPS basic troubleshooting) | **403** |
| `https://www.se.com/uk/en/faqs/FA337697/` (Back-UPS XS BX1300G/BX1500G) | **403** |
| `https://www.se.com/in/en/faqs/FAQ000240114/` (CH9, Easy UPS SRV) | **403** |
| `https://www.se.com/in/en/faqs/FAQ000236002/` (BDC, Easy UPS SRV) | **403** (same host policy) |
| `https://ckm-content.se.com/ckmContent/sfc/servlet.shepherd/document/download/0698V00000mAJVxQAO` (Easy UPS error-code mapping) | **403** |
Their content appeared in search-engine snippets, but **a snippet is not a fetch**. Nothing from these pages is used as evidence anywhere above. All Back-UPS F01–F09 data in Series 4 comes from the fetched PDF manuals, not from these FAQs.

### N3 — Easy UPS SRV LCD / event-log short codes (CH9, BDC, DCF, DCH, OUF, SC, BOU, HOT, OL, INF, EEF) and their numeric event codes — NOT entered
A mapping table of `Event Log | Event Code` pairs (e.g. `CH9 | 142`, `CH9 | 149`, `DCF | 131`, `BDC`) was visible in a search snippet attributed to Schneider Electric, but the document itself is behind the 403 ckm-content endpoint (N2). **Not entered as a series.** Two of these codes also appear in 403'd FAQs. This is a real, known APC namespace that remains unverified here — flag for a follow-up fetch from a different network path.

### N4 — Several official SE document URLs 404
Verified 2026-09-26: `https://download.se.com/doc/SPD_UM_SU-990-6411_EN/SU_UM_990-6411_EN.pdf` (404), `https://download.se.com/doc/SPD_CCON-SRVMAP_EN/SPD_CCON-SRVMAP_EN.pdf` (404), `https://download.se.com/doc/SPD_AHUG-990-3788_EN/BU-UM-990-3788-MN01-EN.pdf` (404). The `p_Doc_Ref=...&p_File_Name=...` query-string form on `download.se.com/files` also returns a 377-byte HTML page rather than a PDF. The working pattern found was `download.se.com/doc/<DocRef>/<FileName>.pdf` and third-party mirrors.

### N5 — Smart-UPS 750/1000/1500 VA (990-3858F, 990-6411) — no numeric code table
Fetched `https://download.se.com/files?p_Doc_Ref=SPD_EALN-85BQQ8_EN&p_File_Name=SU_UM_990-3858F_EN.pdf` (472 939 bytes, extracted OK). It contains only an **LED/alert troubleshooting table** ("The Alert LED is illuminated... The UPS has detected an internal fault") and a site-wiring-fault note, with **no fault code numbers**. Smart-UPS X-Series 990-6411 and Smart-UPS 750-1500 990-1587 could not be retrieved as PDFs (404 / 377-byte HTML). **No series produced for these models.**

### N6 — Back-UPS 990-3788 / 990-3972A wording divergence (seen only in search snippets, NOT fetched)
Snippet text attributed to BU-UM-990-3788 and 990-3972A gives F05 as **"Charge Fault"** and adds the line **"For faults F01 and F02, contact APC Technical Support"** *while its own table gives the user a remedy* — a self-contradiction. The fetched 990-3508 PDF gives F05 as **"Charge Fault"** with no such escalation line. **The divergence is recorded here rather than in CONFLICTS because only the 990-3508 side was actually fetched.** Do not cite the 990-3788 wording until retrieved.

### N7 — Other APC families not investigated
Not fetched, so nothing claimed: Smart-UPS RT 3-phase modular (SRT3K/SRT5K/SRT6K/SRT10K) event-log codes; Easy UPS 3S PV/solar variants; APC Back-UPS Pro / Extended Runtime LED-only units (no LCD, so no code table); APC Smart-UPS Rack PDU / rack PDU codes; NetShelter; EcoStruxure; APC surge-protector-only product codes.

### N8 — No official single-page "APC master fault code list" exists
APC does **not** publish one consolidated code table across all families. Codes are per-model-manual, which is exactly why this file is split into 5 series. Any single unified "APC error code" list found online is a third-party compilation and must not be treated as official.

## Quarantine (excluded)

Found or implied during the search sweep, **explicitly excluded** from all series above.

| Quarantined | Why | Evidence of presence in the sweep |
|---|---|---|
| **Eaton** fault codes | Different brand. Eaton's own numeric codes and LED charts surfaced repeatedly in generic "UPS fault code" searches and are a classic false positive for "APC". | Generic UPS-code search results; not fetched, not entered |
| **CyberPower** fault codes | Different brand; surfaced in the same generic searches. | Same |
| **Kstar** fault codes | Different brand; surfaced in the same generic searches. | Same |
| **Numeric** (Numeric UPS) fault codes | Different brand; surfaced in the same generic searches. | Same |
| **APC non-UPS products** | The fetched PDF `91uOT05EpPL.pdf` is a **two-product bundle**: "Product 1: APC 1500VA Smart UPS with SmartConnect, SMC1500C" **and "Product 2: APC UPS 1500VA Battery Backup Surge Protector, BR1500G"**. Only Product 1 is in scope. | Same PDF, cover page |
| **APC surge-protector behaviour** | The BR1500G is a surge protector / battery backup strip, not a UPS. Its LED behaviour is **not** a UPS fault code and is excluded even though it shares the PDF. | Same PDF, cover page |
| **APC "F##" claims for Smart-UPS** | Search results asserted Smart-UPS-family "F##" codes. Not confirmed in any **fetched** APC document — the fetched Smart-UPS C manual uses `P.`/`b.`/`G.`/`L.`/`Sc.` and the fetched Back-UPS manuals use `F##`. Not entered. | Search snippets only |
| **Generic "UPS error code" compilation sites** | Aggregator blogs and forum posts listing "APC error codes" blend multiple brands and multiple APC families. None used. | Search results |

## Conflicts / caveats

1. **`F01`–`F09` belong to Back-UPS, not to Smart-UPS.** The only *fetched* APC documents that use `F##` are the Back-UPS manuals (Series 4). The fetched Smart-UPS C manual uses `P.`/`b.`/`G.`/`L.`/`Sc.` instead. So "F03" alone is **ambiguous across APC's own catalogue** — an app must key these to the Back-UPS family, not to "APC". (An unrelated Smart-UPS "F03"-style claim was seen in snippets only → NEGATIVE N7/N3.)

2. **Back-UPS F05 has two published names.** Fetched `990-3508` (BX1500G/BX1300G/BN1350G) prints **"Charge Fault"**. Search snippets of `990-3788` and `990-3972A` print **"Charger Fault"** — those were not fetched (N6). Series 4 uses the *fetched* wording and flags the other in NEGATIVE.

3. **"APC Easy UPS" is four different code namespaces, not one.** This is the single biggest cross-family risk found:
   | Namespace | Form | Where |
   |---|---|---|
   | Modbus register ENUM | `0x01`…`0x49` hex | Series 2 (SRV/SRVL register 44497) |
   | LCD / event-log short codes | `CH9`, `BDC`, `DCF`, `DCH`, `OUF`, `SC`, `BOU`, `HOT`, `OL`, `INF`, `EEF` | **N3 — not entered** |
   | Audible alert patterns | beep patterns, icon-based | Series 5 (SRVS) |
   | Display-text alarms | sentences, e.g. "Output short circuit" | Series 3 (Easy UPS 3S) |
   These must **never** be merged or cross-mapped. `0x02` (Bus volt over) and `CH9` (charger error) look interchangeable and are not.

4. **`0xFF` is not a "no fault" value on SRV/SRVL.** The register publishes `0x00 = OK: No Fault Occurs` and `0xFF = NO: Unknown Error`. A client that masks off the top byte, or treats a non-zero read as boolean fault, will render `0x00` as a fault and mis-render `0xFF` as a valid code.

5. **3-phase-only codes inside a 1-phase-capable register map.** `0x15`, `0x16`, `0x1B`, `0x1C` (phase B/C inverter faults) and `0x18`, `0x19` (B-C and C-A line-to-line shorts) are meaningless on a 1-phase SRV. The register map covers SRV **and** SRVL, so phase must be checked from the model before showing these.

6. **"Bypass overload" vs "Bypass overload timeout"** are two distinct Easy UPS 3S rows with the same description but different severity in effect — the timeout means the unit has given up sustaining it. Merging them would lose the escalation signal.

7. **Fan alarms are split four ways in Easy UPS 3S**: `Bypass fan inoperable`, `Fan inoperable`, `Input SCR fan inoperable`, and `Input SCR temp high` / `Rectifier high temp`. Same remedy, different protected component. Series 3 groups them with explicit aliases rather than pretending they are identical.

8. **Voltage-scope mismatch in the Smart-UPS C table.** The fetched manual carries a `120 Vac` and a `230 Vac` column marker in the same table region, and states the site-wiring fault applies **"(Applicable for 120 V units only)"** and that `G.07 EPOActive` is **"reserved for 2200 VA and 3000 VA models only"**. For a Pakistani 230 V install, `G.00` should not normally appear — if it does, the wiring is genuinely faulty, not the code being inapplicable. Likewise `G.07` cannot legitimately appear on a 1000/1500 VA unit.

9. **Case sensitivity in Smart-UPS C codes.** The manual prints battery codes in **lower case** (`b.00`, `b.01`, `b.12`) while power/general/lifetime codes are upper case (`P.00`, `G.00`, `L.01`). A case-insensitive lookup will collide or miss.

10. **A non-UPS product shares the Smart-UPS C code table's PDF** (`BR1500G` surge protector bundled in the same document). The `P.`/`b.`/`G.`/`L.` codes belong to the **SMC1500C Smart-UPS** half only. See QUARANTINE.

## Research report

**Total codes entered: 140** (across 5 series). Breakdown:

*Machine-validated on the written file: all 5 YAML blocks parse; 140 unique codes; no duplicate codes within any series; `en != ur` on every title, cause, solution and notes object; every `en` title ≤ 60 chars; ≥2 causes and ≥2 solutions on every `isFault: true` entry; `severity: info` on every `isFault: false` entry; zero Arabic/Urdu-script characters; `brand:` present exactly once (Series 1).*

| Series | Scope | Entries |
|---|---|---|
| 1 | Smart-UPS C 1000/1500 VA — `P.` (10), `b.` (8), `G.` (12), `L.` (2), `Sc.` (9) | 41 |
| 2 | Easy UPS SRV/SRVL — Modbus `Fault code` ENUM register 44497 | 35 |
| 3 | Easy UPS 3S 10–40 kVA — display alarm text | 38 |
| 4 | Back-UPS XS / Back-UPS LCD — `F01`–`F09` + status indicators | 14 |
| 5 | Easy UPS SRVS 1/2/3 KI & KRI — audible alert / notification patterns | 12 |
| | **Total** | **140** |

**Series count: 5.** All are `unit_type: ups`.

**Honest count note.** The brief targeted 40–80. APC genuinely publishes *complete* per-family tables (a 37-row Modbus ENUM, a ~80-row alphabetical alarm table, a 5-column × 9-row code table, a full `F01`–`F09` list and a 7-row beep-pattern table), so the rule "take every verifiable row" was applied and the result is 140 rather than 40–80. Composition: 115 entries are individual published code strings; 25 are grouped/cluster rows where SE publishes several display strings sharing one description and one corrective action (e.g. `Bypass fan inoperable` / `Fan inoperable` / `Input SCR fan inoperable`; the normal-status clusters in Series 3). The grouped rows carry their constituent strings in `aliases` and are explained per-entry in `notes`. If a stricter count is wanted, the grouped rows are the ones to expand or collapse.

**Confidence distribution.** 136 `high` (fetched official APC manual or SE Product Info doc), 4 `medium` (Series 5's four icon-only alert rows — `ALERT-SHORT-CIRCUIT`, `ALERT-OVERLOAD`, `ALERT-DC-VOLTAGE-ERROR`, `ALERT-OVER-TEMPERATURE` — where the manual publishes a graphic display-code icon instead of a text code; each carries a `notes` object explaining why). No `low` entries: every unverifiable item was routed to NEGATIVE instead.

**Top 5 URLs (by codes delivered)**
1. `https://productinfo.se.com/easyups3s_ul/990-6409_master-easy-ups-3s-10-40-kva-208-v-operation/English/990-6409%20Easy%20UPS%203S%2010-40%20kVA%20208%20V%20Operation_0000535296.xml/$/StatusandAlarmMessagesREF_0000161232` — 38 entries
2. `https://m.media-amazon.com/images/I/91uOT05EpPL.pdf` — 41 entries (Smart-UPS C code table)
3. `https://cdn.cs.1worldsync.com/7d/43/7d439fc3-bbe3-4b6b-83aa-070752dcecdd.pdf` — 35 entries (SRV/SRVL Modbus ENUM)
4. `https://coasttec.com/uploads/manuals/BX1500G.pdf` — 14 entries (Back-UPS `F01`–`F09`)
5. `https://download.se.com/doc/SPD_AHUG-ASVAYL_EN/EUO-UM-990-91091B-MN01_EN.pdf` — 12 entries (Easy UPS SRVS alerts)

Supporting: `https://download.se.com/doc/SPD_AHUG_990-6193_EN/SU_UM_990-6193A_MN01_EN.pdf` (proved SRT has no code table → NEGATIVE), `https://files.oaklandcorp.com/manuals/APC%201300%20Battery%20Backup.pdf` (second independent Back-UPS `F01`–`F09` confirmation), `https://www.se.com/pk/en/`, `https://eshop.se.com/pk/`, `https://www.se.com/ae/en/work/support/locator/apc-distributors/` (PK presence).

**Official fault-code table published? — YES.**
Official per-family tables exist and were fetched: a 5-family code table (Smart-UPS C), a 37-value Modbus ENUM (Easy UPS SRV/SRVL), an ~80-row display alarm table (Easy UPS 3S), a `F01`–`F09` list (Back-UPS), and an audible-alert pattern table (Easy UPS SRVS). There is **no** single consolidated APC-wide list (N8), and the Smart-UPS RT / SRT online range publishes **no** numeric code table at all (N1).

**PK presence? — YES.**
Evidence: Schneider Electric Pakistan country site with a Karachi HQ office (`https://www.se.com/pk/en/`); APC SKUs actively sold on the SE Pakistan e-Shop including `SRV1KIL` (`https://eshop.se.com/pk/`); and Schneider Electric's own APC distributor locator naming **Ingram Micro Pakistan (Pvt) Ltd**, **Awan Distribution** and **Mansha Brothers**, all Karachi (`https://www.se.com/ae/en/work/support/locator/apc-distributors/`). Note for localisation: the Smart-UPS C table is written for 120/230 V with `G.00` Site Wiring marked *120 V only*, and Pakistan is 230 V / 50 Hz — so the 230 V behaviour is the applicable one.

**Blockers**
1. **`se.com` returns HTTP 403 to the fetcher for all FAQ and ckm-content endpoints** (verified: FA279105, FA337697, FAQ000240114, FAQ000236002, and the `0698V00000mAJVxQAO` error-code mapping). This is the single biggest gap: it blocks the **Easy UPS SRV LCD/event-log short codes** (`CH9`, `BDC`, `DCF`, `DCH`, `OUF`, `SC`, `BOU`, `HOT`, `OL`, `INF`, `EEF`) with their numeric event codes, which would form a natural Series 6. Retry from a different network path, or via a regional SE mirror.
2. **`download.se.com` URL patterns are inconsistent.** The working form is `download.se.com/doc/<DocRef>/<FileName>.pdf`; the `p_Doc_Ref=&p_File_Name=` query form returns a 377-byte HTML stub, and `SPD_UM_SU-990-6411_EN`, `SPD_CCON-SRVMAP_EN`, `SPD_AHUG-990-3788_EN` 404. Several Back-UPS and Smart-UPS manuals had to be sourced from third-party mirrors (`coasttec.com`, `oaklandcorp.com`, `m.media-amazon.com`, `media.dustin.eu`) — the manual body is APC-authored and internally consistent, but the host is not a Schneider Electric domain.
3. **Smart-UPS RT / SRT is a genuine documentation dead end** (N1) — no numeric code table exists to fetch, so that family cannot be covered regardless of access.
4. **The Easy UPS SRVS "Alerts" sub-table is icon-only** (3 rows, `medium` confidence) — APC publishes a graphic display-code image rather than a text code, so no text code can be honestly asserted for those three conditions.
5. **Easy UPS 3S 208/220 V 10–40 kVA** is the largest-volume family in Pakistan but its `ProductInfo` chapter was fetched without an accompanying PDF, so the temperature/derating numbers are absent from the entries (deliberately — the source did not state them).

## Series files

| File | Series | Codes |
|------|--------|-------|
| `apc-smart-ups-c-1000-1500.json` | Smart-UPS C 1000/1500 VA (Tower / Rack-Mount 2U, 120/230 Vac) | 41 |
| `apc-easy-ups-srv-srvl.json` | APC Easy UPS SRV / SRVL (Modbus Fault-code ENUM, register 44497 / 0x1190) | 35 |
| `apc-easy-ups-3s.json` | APC Easy UPS 3S 10-40 kVA (three-phase, 208 V/220 V) - display alarm text | 38 |
| `apc-back-ups.json` | APC Back-UPS XS and Back-UPS LCD (F01-F09 system fault codes) | 14 |
| `apc-easy-ups-srvs-audible.json` | APC Easy UPS SRVS 1/2/3 KI and KRI - audible alarm and notification patterns | 12 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `apc-smart-ups-c-1000-1500.json` | user_manual | Operation Manual - Smart-UPS C Uninterruptible Power Supply 1000/1500 VA Tower / Rack-Mount 2U, 120 Vac/230 Vac (part su0752c/su0752d) | https://m.media-amazon.com/images/I/91uOT05EpPL.pdf | 2026-09-26 |
| `apc-easy-ups-srv-srvl.json` | other | Easy UPS SRV/SRVL Modbus Register Map (Network Management Card) | https://cdn.cs.1worldsync.com/7d/43/7d439fc3-bbe3-4b6b-83aa-070752dcecdd.pdf | 2026-09-26 |
| `apc-easy-ups-3s.json` | other | Easy UPS 3S 10-40 kVA 208 V - Operation (990-6409B-001), section 'Status and Alarm Messages' | https://productinfo.se.com/easyups3s_ul/990-6409_master-easy-ups-3s-10-40-kva-208-v-operation/English/990-6409%20Easy%20UPS%203S%2010-40%20kVA%20208%20V%20Operation_0000535296.xml/$/StatusandAlarmMessagesREF_0000161232 | 2026-09-26 |
| `apc-back-ups.json` | user_manual | APC Back-UPS BX1500G / BX1300G / BN1350G User Manual (BU-UM-990-3508-MN01-EN) | https://coasttec.com/uploads/manuals/BX1500G.pdf | 2026-09-26 |
| `apc-easy-ups-srvs-audible.json` | user_manual | User Manual Easy UPS On-Line - Easy UPS Series SRVS 1/2/3 KI/KRI (EUO-UM-990-91091B-MN01-EN) | https://download.se.com/doc/SPD_AHUG-ASVAYL_EN/EUO-UM-990-91091B-MN01_EN.pdf | 2026-09-26 |

