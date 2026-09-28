# Stabimatic (inverter) — sources

Brand id: `stabimatic` · Category: `inverter` · Researched: 2026-09-24
Official PK: https://stabimatic.com · STABIMATIC Co. Ltd (UK branding; PK retail)
Support: info@stabimatic.com

**Status: 18 codes across 5 series files — ZERO numeric fault codes published; all diagnostics are symbolic LED/buzzer/LCD IDs synthesized from official wording.**

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `gemini.json` | Gemini Series UPS (LED/LCD) | 7 (LED_/BEEP_ symbolic IDs) |
| `onl.json` | ONL Series online UPS | 1 (LCD_ONL_FAULT) |
| `gs.json` | GS Series UPS | 6 (GS_ LEDs + named alarms) |
| `sp-avr.json` | SP Series AVR (status LEDs) | 4 (status only — reclassified as AVR, not UPS faults) |
| `msi-pro.json` | MSI PRO hybrid inverter 1200/2400VA | 0 (gap — product confirmed, no manual exists) |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Gemini 650 official datasheet (PowerHouse Express CDN) | user_manual | https://powerhouseexpress.com.pk/cdn/shop/files/stabimatic-gemini-650-gemini-series-ups.pdf | INDICATORS + ALARM wording → LED_/BEEP_ symbolic IDs | 2026-09-24 |
| 2 | ONL-1000/3000 official datasheet | user_manual | https://powerhouseexpress.com.pk/ | LCD FAULT indicator only — no numeric sub-codes | 2026-09-24 |
| 3 | Stabimatic official site via Wayback (gsseries.html etc.) | official_support | https://web.archive.org/web/*/stabimatic.com | GS label-level LED/LCD + named alarms (no blink periods published → medium) | 2026-09-24 |
| 4 | electro.pk / short Gemini listings | retailer_page | electro.pk etc. | Corroboration + overload-beep wording conflict (logged per-code) | 2026-09-24 |
| 5 | w11stop MSI PRO listing (Wayback) | retailer_page | w11stop (Wayback) | MSI PRO 1200/2400VA product confirmed — **no manual/datasheet/code table anywhere** (negative) | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Gemini overload beep: full 1250 PDF vs short/electro.pk wording differ | Conflict logged in per-code `notes`; both wordings preserved as notes |
| 2 | "Fault codes" search returns other brands' numeric tables | Never imported — Stabimatic publishes no numeric codes |
| 3 | SP series looks like UPS in search results | **Reclassified as AVR** (Automatic Voltage Regulator) — status LEDs only, not fault codes |
| 4 | MSI PRO hybrid listed in roadmap as if it had codes | Product confirmed via Wayback w11stop; zero manuals → explicit negative-evidence empty series |

## Exhaustiveness (Phase D)

- Official site + Wayback CDX + PowerHouse Express CDN + retailer sweep: no numeric code table exists for any Stabimatic line.
- Stop rule met: trailing searches return only other UPS brands' tables.
- Offline follow-up: request MSI PRO manual via info@stabimatic.com; field units may ship in-box sheets.

## Offline follow-up paths

- info@stabimatic.com — request MSI PRO + Gemini code sheet.
- In-box paper manuals from PK retail units.
