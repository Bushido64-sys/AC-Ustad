# Hisense (AC) — sources

Brand id: `hisense` · Category: `ac` · Researched: 2026-09-24
PK licensee / assembler: **Tri-Angels Electronics Pvt. Ltd. (Karachi)** — residential AC plant since 2021 (100k units/yr)
Official: https://www.hisense.com.pk (WordPress; 29 pages, **none** error-code) · https://tri-angels.com.pk (HTTP 406 from research network)
PK lines: split inverter 12/18DC60HC, 12/18/24TV60HC, 12/18TG75HC, 12/18TQ60HC, 18TF60HC, HC18SRTQ60T3, CC12SRKB60T3; floor 24FS-CPA, 24UR4RJJ1; cassette 1.5/2/4T; VRF 6.2–22.5T
Official published full error table: **No** — only how-to PDF `https://gcss.hisense.com:8080/gcss/static/file/Howtoidentifytheunitalarmcode.pdf` (examples 19/03/31/04/01)

**Status: 98 codes across 3 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `hisense-t1-floor-standing.json` | T1 floor-standing numeric LED codes (service manual; indoor vs outdoor sheets) | 77 |
| `hisense-split-ef-export.json` | Split E/F letter codes (export / multi-source tables) | 18 |
| `hisense-portable.json` | Portable Hisense AC (not PK retail split/floor line) | 3 |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Hisense T1 Series Floor Standing AC Service Manual V3.2 (pp.71–92) | service_manual | https://www.manualslib.com/manual/2199820/Hisense-T1-Series.html?page=71 | Outdoor 1–32,40,42–50,63,66,67,91–97; Indoor 31–41,51–62,64,65,72–74,80–89,98,F0–F3,FE,ER; driver sheets | 2026-09-24 |
| 2 | Hisense AS-09CR4SVDTD5 Service Manual (pp.52–54) | service_manual | https://www.manualslib.com/manual/1898344/Hisense-As-09cr4svdtd5.html?page=52 | E2, E4, EA, 1, 33, 34, 38, 39, 41–43 | 2026-09-24 |
| 3 | Hisense AS-18UR4STVUH1 Service Manual (pp.22–28) | service_manual | https://www.manualslib.com/manual/1278642/Hisense-As-18ur4stvuh1.html?page=22 | Outdoor LED 5–15; indoor 33–41,1,2,20 | 2026-09-24 |
| 4 | Hisense AS-36HR4SDKVT Service Manual (p.33) | service_manual | https://www.manualslib.com/manual/2741485/Hisense-As-36hr4sdkvt.html?page=33 | E2, E4, EA, 1, 20 | 2026-09-24 |
| 5 | Arlington HVAC "38-Code Decoder" | other | https://www.arlingtonairconditioningheating.com/hisense-aircon-error-codes/ | 01,02,11,13,14,33,34,36,38,39,41–43; E1–E6,E8,E9,EA,EE; F0–F6,FE + numeric table | 2026-09-24 |
| 6 | R-Pro Error Codes (29 Hisense codes) | other | https://www.r-pro.app/error-codes/hisense/ | 01,02,11,13,14,33,34,36,38,39,41,43; E1–E6,E8,E9,EA,EE; F0–F6,FE (agrees with #5) | 2026-09-24 |
| 7 | Smart AC Solutions (updated 2026-06-28) | other | https://smartacsolutions.com/hisense-air-conditioner-error-codes/ | E0–E5,EE; portable FL/P1/E9; numeric service-manual wording | 2026-09-24 |
| 8 | Scribd 783903271 Hisense LED lamp table | other | https://www.scribd.com/document/783903271/Hisense-AC-Error-code-list | 0–22,24,26,27,33,34,36,38,39,41 lamp patterns (image-derived) | 2026-09-24 |
| 9 | Scribd 857657310 Hisense error codes (Tamirci) | other | https://www.scribd.com/document/857657310/Hisense-Air-Conditioner-Error-Codes | 0–19,33–41,F0–F3,FE,ER,E2,E4,EA + inquiry method | 2026-09-24 |
| 10 | Official Hisense "How to identify unit alarm code" PDF | official_support | https://gcss.hisense.com:8080/gcss/static/file/Howtoidentifytheunitalarmcode.pdf | Method only; example alarms 19,03,31,04,01 | 2026-09-24 |
| 11 | HvacInExpert Hisense codes | other | https://hvacinexpert.com/hisense-ac-error-codes-list-and-fixes/ | Conflicts only — **quarantined** (H6/U4) | 2026-09-24 |
| 12 | ApplianceCodeHub | other | https://appliancecodes.com/hisense-air-conditioner-error-codes/ | Quarantined (self-generated) | 2026-09-24 |
| 13 | AC Guided | other | https://acguided.com/hisense-air-conditioner-error-codes/ | Quarantined (self-contradictory) | 2026-09-24 |
| 14 | CLIMAPARTS | other | https://climaparts.app | Quarantined (vague) | 2026-09-24 |
| 15 | ErrBase | other | https://errbase.com/hisense/airconditioner | Quarantined (E5 only, conflicts) | 2026-09-24 |
| 16 | Fully5world YouTube (titles via oembed) | video | youtube.com/-gQGaoJZA6Y · vw3FCttizUA | Title-only: "error code 39", "error code P1" (low) | 2026-09-24 |
| 17 | Fully4world Facebook F1 video | video | (Facebook) | F1 = **Changhong Ruba** — quarantine | 2026-09-24 |
| 18 | Teleco Alert / Reporter PK (2021-03-12) | other | telecoalert.com/2021/03/12/hisense-residential-ac-manufacturing-facility-in-karachi/ | Tri-Angels plant recon (no codes) | 2026-09-24 |
| 19 | Scribd 671296183 Hisense Service Manual | service_manual | scribd.com/document/671296183 | TOC only — error table at p.46 **not in preview** (open gap) | 2026-09-24 |
| 20 | shopify Troubleshooting.pdf / manuals.plus / studylib | other | (various) | Dead ends | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | **Indoor vs outdoor numeric sheets** — same digit different meaning (e.g. outdoor 1 = outdoor ambient; indoor 33 = room sensor; outdoor has no 33 on T1) | Confirmed in T1 series notes: always identify which unit's LED before decoding. Indoor/outdoor stay distinguishable via `display` field. |
| 2 | hvacinexpert E3/E4/E5/F0/F1/F2 meanings vs service manuals | Service manuals win; hvacinexpert quarantined |
| 3 | hvacinexpert H6/U4 as Hisense codes | Not in any Hisense service manual — Gree/Midea contamination, quarantined |
| 4 | appliancecodehub 1/2/33/34/35/5/E1 | Self-generated, contradicts T1 manual — quarantined |
| 5 | acguided E4/E5/EA/FC/19/E19 self-contradictory | Quarantined |
| 6 | climaparts F5/F8/E9 vague | Quarantined |
| 7 | errbase E5 single line | Quarantined |
| 8 | Fully4world Facebook F1 | Changhong Ruba video — quarantine |
| 9 | P1 portable full-tank vs Fully5world title "DC inverter P1" | Portable meaning retained in portable series with conflict note; split P1 not imported without meaning |
| 10 | arlington/r-pro "33" = compressor discharge for another inverter family vs T1 indoor 33 = room sensor | Model-scoped; T1 notes say confirm model |
| 11 | scribd table code 2 = compressor exhaust on some splits vs T1 outdoor 2 = outdoor coil sensor | Notes on code 2; model-scoped, never merge |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|----------------|
| hvacinexpert H6/U4 + contested E/F meanings | Not in Hisense manuals; Gree/Midea codes |
| appliancecodehub / acguided / climaparts / errbase | Self-generated or conflicting |
| Fully4world Facebook F1 | Changhong Ruba, wrong brand |
| Generic E1–E8 EP lists (pickcomfort etc.) | Content-farm boilerplate, no model citation |
| Hisense TV / mobile / refrigerator codes | Wrong appliance |
| Other brands on multi-brand pages | Never imported as Hisense |

## Exhaustiveness (Phase D)

- **No PK retailer or brand site publishes Hisense AC error table** (gulfelectronics, japan electronics, pakref, daraz, hisense.com.pk WP pages, maqsood — negative).
- Official consumer/support sites publish no full table; only gcss how-to PDF.
- FB groups: posts ask for codes but contain none.
- r.jina.ai rate-limits; manualslib download Cloudflare-blocked; websearch MCP 429 (worked around via r.jina.ai + DDG).
- Stop rule: successive batches yielded no new Hisense-badged codes beyond the three series.
- Open gap: Scribd 671296183 error table at p.46 not in preview; tri-angels.com.pk 406.

## Offline follow-up paths

- Scribd 671296183 full download → visual read of p.46 table.
- Tri-Angels (Karachi) — request PK fault-code sheet / service bulletin.
- ManualsLib full PDFs for T1 / AS-09CR4 / AS-18UR4 / AS-36HR4 — re-fetch for blink-map verify.
- gcss.hisense.com how-to PDF — expand if more examples exist behind login.
