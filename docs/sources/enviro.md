# Enviro — sources

Brand id: `enviro` · Category: `ac` · Researched: 2026-09-23
Official site: https://enviro.com.pk
Support: after-sales (042) 111-88-44-22 · WhatsApp/phone +92 320 0845120 · info@enviro.com.pk · Kalma Tower, Garden Town, Lahore · service centers: https://enviro.com.pk/pages/service-centers

**Status: documented gap — no official fault-code table/service manual; exactly 1 verified code (E9, high).**

## Verified model lines (enviro.com.pk)

- Wall splits: Titan (EAC-12/18Titan), Iceberg (EAC-12/18/24IB), Classic (EAC-12/24CL), Alpha (EINV-12/18AS), Eco (EAINV-12/18/24E, EAC-12E), Marvel (EAC-18MS), Big Show (EAC-19TBS), A-Cool (EAC-12A Cool), H-Cool (EAC-18H Cool), Thunder/TQ MAX (EAC-12/18/24TQ), Glacier (EAC-12/18HU), ACE (EAC-12/18/24QY), TQ Grande (EAC-18TQ)
- Floor standing: Ultimate+ (EAC-48 Ultimate+ H&C, 4-ton, R410A, T3 inverter); warranty-listed TG GRANDE / TG GRANDE PLUS / DX GALAXY / DX GALAXY PLUS (AC type unconfirmed on page — likely floor-standing/large)
- No cassette/duct line confirmed.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Enviro Appliances official site — About / Contact / User Manuals / Warranty / Service Centers / product pages (Titan, Classic, A-Cool…) | official_support | https://enviro.com.pk | **Sole official code: E9 = refrigerant leakage detection** (on product pages); model roster; support contacts. User-manuals page JS-empty → **no fault-table PDF** (negative evidence) | 2026-09-23 |
| 2 | Retailer product listings repeating E9 (MNS, Zahid Brothers eStore, Delite, Subhan, Alfatah, R.D. Electronics, Hadi, OLX) | retailer_page | https://hadielectronics.com.pk/product/enviro-eac-48-ultimatehc-48000-btus-floor-standing-ac/ (representative) | Independent confirmations E9 = refrigerant leak detection (8+ listings) → confidence high | 2026-09-23 |
| 3 | ComparePrice.pk — Enviro TQ Grande (EAC-18 TQ) | retailer_page | https://compareprice.pk/product/enviro-1-ton-grande-series-eac-18-tq-dc-inverter-heat-cool-split-ac-grey-price/ | Confirms TQ Grande = EAC-18 TQ DC inverter H&C split. Capacity label quirk ("1 Ton" vs EAC-18) — spec error, ignore for codes | 2026-09-23 |
| 4 | Babar Electronics HVACR — Dailymotion x9qcs4c | video | https://www.dailymotion.com/video/x9qcs4c | **False lead:** title/tags say "Enviro TCL E0"; actual content = Haier E7 tutorial → keyword stuffing, excluded | 2026-09-23 |
| 5 | AYS Online — Orient AC error docs (exists) vs no Enviro equivalent | distributor_page | https://www.aysonline.pk/docs/orient-air-conditioner-error-codes/ | Negative evidence: PK retailers document Orient but **no Enviro error page exists** | 2026-09-23 |
| 6 | Generic Midea-family service manuals (Omnia ECO, Passion ECO P9MV, ACPro) | other | e.g. https://ac.inv-static.com/uploads/ac_mini_split/OMNIA%20ECO/OmniaEco_ServiceManual.pdf | Cross-brand reference only (E/F/P sets for **other** brands) — NOT imported into Enviro data | 2026-09-23 |
| 7 | Name-collision sweep ("Enviro" US PTAC/ductless, fan coils, fireplaces, solar inverters, controllers) | other | various | All excluded — wrong brand | 2026-09-23 |

## Conflicts log (resolved in data notes)

| Code / topic | Conflict | Resolution used |
|---|---|---|
| **E9** | Enviro official = refrigerant leak. Other brands map E9 to fan/sensor/indoor faults (Haier/Gree/TCL/Dawlance tables) | **Enviro's own mapping wins** (official + 8 retailers). Notes explicitly warn: do not import other brands' E9 |
| Leak code naming | OEM platforms use EC, EL 0C, or F0-adjacent for leak detection; Enviro publishes E9 | If an Enviro unit shows EC/EL 0C → **unverified for Enviro**, not automatically = E9. Related-code link to EC kept as hint only, EC **not** added as an Enviro entry |
| OEM E/F/P tables | Enviro likely rebrands OEM platform (import hints: Zhongshan Changhong) — generic tables *may* apply | **Not imported.** Zero Enviro-verified sources; would fabricate data. Hypothesis logged for future if a manual appears |
| Babar "Enviro E0" video | Description claims Enviro/TCL E0, content is Haier E7 | Excluded as keyword-stuffed false lead |
| EAC-18 capacity | ComparePrice labels EAC-18 "1 Ton" | Spec-page quirk, irrelevant to codes — do not trust that page for capacities |

## Exhaustiveness check (Protocol Phase D)

- ~22 searches + targeted fetches (official manuals/contact/service pages, YouTube, Scribd, manualslib, manual filetype:pdf, model codes EAC/EINV/EAINV, floor-standing TG Grande/DX Galaxy, Urdu queries).
- Final **6 successive searches returned no new Enviro codes**: (1) service manual filetype:pdf, (2) "Enviro" AC E0 Pakistan, (3) floor-standing TG Grande/DX Galaxy, (4) site:youtube.com, (5) E1/E2/E4/P1/F1 sweep, (6) enviro.com.pk troubleshooting/fault-blink — **Phase D stop condition met**.
- Codes covered: **1 entry** (E9, high). Official table: none. Service manual: none (user-manuals page empty).
- Known remaining gap: obtain an Enviro service/user manual (JS-loaded manuals or physical copy → image PDF → pdftoppm) and re-run Phase C; verify whether the OEM platform import hypothesis holds; check display channel for E9; check whether EC/EL 0C occur on any line.
- Note: Homage/EcoStar-style sibling-brand code transfer is **not** allowed without an Enviro-badged source.
