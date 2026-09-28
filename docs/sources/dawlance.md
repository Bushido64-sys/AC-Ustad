# Dawlance — sources

Brand id: `dawlance` · Category: `ac` · Researched: 2026-09-23
Official site: https://www.dawlance.com.pk · Service UAN: 021-111-11-7359
User manual search: https://www.dawlance.com.pk/support/user-manuals (returns 403 to bots)

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Dawlance Mega T+ 15/30 Inverter official user manual F01000019200 (p.28 ERROR SIGNALS table — image-only PDF, extracted by rendering page to PNG and reading visually) | user_manual | https://gsim2hwnpbvwtwmb1dg11z6.blob.core.windows.net/media/documents/F01000019200_MDM2_USER_MANUAL_FILE_en_GB.pdf | **Official:** E1 (RUN×1, indoor temp sensor), E2 (RUN×2, indoor pipe sensor), E6 (RUN×6, indoor fan motor), E4 (display, refrigerant leakage) | 2026-09-23 |
| 2 | Dawlance Powercon official user manual F01000000065 (p.15 Display Code Meanings — rendered visually; table is icons, no E/F list) | user_manual | (official Azure blob, id F01000000065) | **Official display states:** cF anti-cold air, dF defrosting, SC self-cleaning, FP 8°C heating; ON/OFF timer flashes | 2026-09-23 |
| 3 | Dawlance Gallant FS Inverter 45 owner's manual F01000000160 (fully scanned, 8 pp — all pages rendered and reviewed) | user_manual | (official Azure blob, id F01000000160) | Confirms same display-legend pattern; no numeric error table; service UAN page | 2026-09-23 |
| 4 | AysOnline — Dawlance Inverter AC Error Codes | distributor_page | https://www.aysonline.pk/docs/dawlance-inverter-ac-error-codes | Table: DF, E1, E3, E4, E5/E8 (communication section), F0, F1, F2, F3, P1, P2, EC, E9 + reset procedure + DIY/technician split | 2026-09-23 |
| 5 | Maqsood & Sons (Islamabad service shop) — Dawlance AC Error Codes | technician_note | https://maqsoodandsons.com/dawlance-ac-error-codes/ | E1, E2, E3, E4, E5, E6, E8, F0, F1, F2, F3, P1, P2, EC + service frequency/cost table | 2026-09-23 |
| 6 | BijliBazar — Dawlance Inverter AC Error Codes 2026 | retailer_page | https://www.bijlibazar.com/dawlance-inverter-ac-error-codes-2026 | Table: DF, E1, E3, E4, E5(ODU fan), F0, F1, F2, P1, P2, EC, E8, F3, E9, H3, H5, P4 + tags C1/H1/H6/L1/L3 | 2026-09-23 |
| 7 | SolarNevs — Dawlance Inverter AC Error Codes List (cites official PDF F01000019200) | retailer_page | https://solarnevs.com/pk/guides/fixes/dawlance-inverter-ac-error-codes-list | Independent transcription of official E1/E2/E6/E4 + blink map (cross-check of #1) | 2026-09-23 |
| 8 | Scribd — "Dawlance Inverter AC Error Codes List" (doc 888657969; full page blocked — search snippets only) | forum | https://www.scribd.com/document/888657969/New-Text-Document | Range claim "E0 to F5 and EH"; snippet meanings: E5 overload, E6 communication, E8 indoor fan, E9 water flow, F0 indoor room… | 2026-09-23 |
| 9 | Forever Tech Hindi/English YouTube (E0, E1, E3, E4, E5, F1, F4/F6, F6/F7/F8/FC, EC — Dawlance-specific titles) | video | youtube.com (channel Forever Tech) | Occurrence evidence for E0, E7(n/a), F4, F6, F7, F8, FC, EC on Dawlance hardware | 2026-09-23 |
| 10 | Babar Electronics HVACR YouTube/FB (Dawlance E1, E5, E7, F1, F4, P2, P4, C4 tagged PCB repair) | video | youtube.com (channel Babar Electronics) | Occurrence evidence: E7, P4, C4 + PCB-class practice for E5/F1 | 2026-09-23 |
| 11 | PakWheels forum — user "umans": Dawlance Inspire Plus Inverter shows EC; helpline said circuit-related | forum | https://www.pakwheels.com/forums/t/orient-inverter-error-code-ec/250786 | Real PK user confirmation of EC on Dawlance | 2026-09-23 |
| 12 | TikTok discover — "EC53 dawlance" (ambient sensor, multi-brand) | video | tiktok.com/discover/ec-dawlance-ac-inverter-error | EC53 occurrence tag + typical ambient-sensor meaning (multi-brand) | 2026-09-23 |
| 13 | Dawlance official product pages (series/model inventory) | official_support | https://www.dawlance.com.pk/inverter-split-ac , https://www.dawlance.com.pk/floor-standing-air-conditioners | Series list for brand.json (Powercon, Enercon, Sprinter, Aura, Elegance, Chrome, Mega, Glamour+, Gallant+, …) | 2026-09-23 |

## Conflicts log (resolved in data notes)

| Code | Conflict | Resolution used |
|---|---|---|
| E4 | Official manual = refrigerant leakage vs PK blogs = indoor fan motor | Primary = official (refrigerant leakage); notes carry alternate; confidence high with conflict note |
| E5 | Communication (AysOnline/Maqsood) vs ODU fan (BijliBazar) vs overload (Scribd) | Primary = communication (majority + video practice); confidence medium |
| E6 | Official = indoor fan (RUN×6) vs Maqsood = outdoor fan vs Scribd = communication | Primary = official; confidence high |
| E9 | Low pressure (AysOnline/BijliBazar) vs water flow (Scribd) | Primary = low pressure; confidence medium |
| F0 | ODU fan (AysOnline) vs general ODU protection (Maqsood) vs gas system (BijliBazar) | Primary = outdoor fan (most specific); confidence medium |
| F1 | Table says outdoor module (AysOnline/Maqsood/BijliBazar-table) vs BijliBazar-prose says indoor sensor comms | Primary = outdoor module (table + videos) |
| E8 | Blogs display↔PCB vs Scribd indoor fan protection | Primary = display↔PCB; confidence high |

## Exhaustiveness check (Protocol Phase D)

- 2+ independent sources compared: ✅ official manual + 3 PK blogs + videos.
- Codes covered: DF, CF, SC, FP (official display states) · E0–E9 · EC · F0–F8 · FC · EH · H1, H3, H5, H6 · C1, C4 · L1, L3 · P1, P2, P4 · EH00, EC51, EC52, EC53 = **38 entries** (inverter-split.json: 37, floor-standing.json: FC duplicate for FS context).
- Codes considered but excluded as cross-brand-only (not Dawlance-evidenced): Orient's P0/FA/F9/Eb set, Haier/Gree/Midea maps, generic AC Pro blink tables.
- Blocked sources (noted, not fully readable): Scribd 888657969, dawlance.com.pk/support/user-manuals (403), Facebook posts (400), device.report (Cloudflare).
- Known remaining gap: any *service* (technician) manual for Dawlance with a full E/F/P table has not been found publicly — only user manuals. If one appears, re-run Phase C for this brand and bump entries' confidence.
