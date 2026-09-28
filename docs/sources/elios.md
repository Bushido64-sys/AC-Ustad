# Elios — sources

Brand id: `elios` · Category: `ac` · Researched: 2026-09-23
Official site: https://eliospk.com · Brand by **Kascon Technologies (Pvt) Ltd** (not "Kascon Electronics")
HQ: Plot 77, Street 10, I-9/2, Islamabad
Support: WhatsApp +92 333 5981711 · +92 321 8548557 · +92 324 8250610 · +92 308 1911579 · sales@eliospk.com · https://eliospk.com/support (support@elios.com.pk domain is **DNS-dead**)
Likely OEM/engineering partner: **Sabro Technologies** (same HQ address; app "Elios – By Sabro" `com.sabro.accontroller`; sabrotechnology.com = 404)

## Verified model lines (brochure)

- Wall 1.0T: Minima Grey, Blossom Edition · 1.5T: Noir Pro, Apex Silver, Alpine White · 2.0T Alpine line
- Floor standing: 2.0T and 4.25T (51K BTU) · Cassette: 2.0T and 4.25T
- R-410A, T3 twin-rotary, WiFi — one shared 18-page manual, no form-factor-specific tables

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | **Elios AC User Manual** (official, 18 pp, PDF, 2025-02-28) — "E/F/H SERIES ERROR" pp. 16–18 | **user_manual (official)** | **https://eliospk.com/assets/Elios%20AC%20User%20Manual-BfPsqmLX.pdf** | **Sole code source: 19 codes E0–E5, F0–F6, H0–H5** (code→short label only) on IoT touchscreen controller | 2026-09-23 |
| 2 | Elios AC Brochure (official, 7 pp) | official_support | https://eliospk.com/assets/Elios%20AC%20Brochure-C_B6JEBm.pdf | Model range wall/floor/cassette; **no codes** | 2026-09-23 |
| 3 | Elios official site (products/support/sitemap) | official_support | https://eliospk.com/ | Identity, contacts, manual/brochure links; **no codes on site** | 2026-09-23 |
| 4 | Elios – By Sabro (Play Store) | official_support | https://play.google.com/store/apps/details?id=com.sabro.accontroller | "Real-time error and warning alerts" claimed; **no glossary** in listing/screenshots | 2026-09-23 |
| 5 | Sabro Technologies LinkedIn | other | https://www.linkedin.com/company/sabro-technologies | OEM identity (address overlap, DC inverter 1–8.5T); no codes | 2026-09-23 |
| 6 | Wayback CDX eliospk.com | other | web.archive.org | 1 snapshot (2025-07-11), fetch 404 — **no older manual** | 2026-09-23 |
| 7 | **Canadian Elios service manuals** (Master Group, eliosprotech) | service_manual — **EXCLUDED (foreign)** | https://cdn.master.ca/documents/en/technical-bulletins/residential/elios/Elios_16_Service_Manual_en_CA.pdf | Full P-series tables for **Canadian** heat pumps — **must NOT merge into PK KB** | 2026-09-23 |
| 8 | Retailer/technician sites (AYS, ElectraFix, Japan Electronics, Fully4world) + YouTube/Facebook/Urdu/Scribd/manualslib | other | various | **Zero Elios-PK pages/videos/manual mirrors** (negative evidence) | 2026-09-23 |

Negative-evidence URLs: elios.com.pk DNS fail · sabrotechnology.com 404 · kascongroup.com empty shell · eliosprotech.com 403 (foreign anyway).

## Conflicts log (resolved in data notes)

No PK-vs-PK conflicts (only one PK source). **Cross-brand traps** — never import into Elios entries:

| Code | Elios PK (official) | Other-brand claim | Better supported for Elios |
|---|---|---|---|
| E1 | Coil sensor open/short | Haier PK indoor temp; Carrier comms; AUX room sensor | **Elios official** |
| E3 | Low refrigerant ("gas is less") | Haier PK indoor fan speed; Electrolux partially adjacent | **Elios official** |
| E4 | "ADS is not connected" (ADS undefined) | Haier EEPROM; Carrier room sensor; AC Pro exhaust high-temp | **Elios official** |
| E5 | Indoor coil overheat/motor | Dawlance high-voltage; Haier anti-frost adjacent | **Elios official** |
| F1 | Discharge temp limit | Haier PK IPM; Dawlance module | **Elios official** |
| F4 | Speed-limiting "See List" (list unpublished) | ACC internal fan; Fujielectric params | **Elios official** — foreign F4s must not fill the missing list |
| F5 | Outdoor motor/coil choked | Carrier outdoor fan speed | **Elios official** |
| H-band | **Entire H0–H5 = sensor/comms faults** | Most PK brands H=IPM/heating; **Canadian Elios uses P-series** | **Elios PK official** — never merge Canadian P-series or other H-bands |
| E2 dual string | Icing + motor collapsed | — | Kept as one medium entry (official ambiguity), not split into two codes |

## Exhaustiveness check (Protocol Phase D)

- ~38 searches; official manual found within first ~8. **After extraction, ~30 successive searches returned zero new Elios-PK codes** (YouTube sweep, F4 sub-list exact-phrase, Facebook tech posts, Play Store glossary, site: filters on all PK retailers) — **Phase D stop condition met**.
- Codes covered: **19 entries** = 15 high (unambiguous official labels) + 4 medium (E2 dual-string, E4 ADS undefined, F4 unpublished See-List, H4 IB undefined). Display all `controller` (IoT touchscreen).
- Negative confirmations: no second source for any code; no service manual; no LED blink / dual-8 / test-mode tables; third-party PK sites have zero Elios content.
- Known remaining gap: F4 speed-limiting sub-list; E4 "ADS" and H4 "IB" definitions; official causes/solutions (table is label-only — KB causes are flagged inference). If a service manual or support definition appears, re-run Phase C and re-grade medium→high / fill F4.
- Geyser manuals on eliospk.com are out of scope (not AC).
