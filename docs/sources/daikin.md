# Daikin (AC) — sources

Brand id: `daikin` · Category: `ac` · Researched: 2026-09-24
Official: https://www.daikin.com (PK: MIA distributed, CKD since 2026 per recon — daikin.com/pk 404, daikin.com.pk 406)
PK channel: MIA (miahomes-class distributor recon) · retailers aysonline · japanelectronics · maqsoodandsons · gulfelectronics · bijlibazar · w11stop (recon)
Official published AC error table: **YES (global)** https://www.daikin.com/products/ac/services/error_codes (SM-TS3 "Simple Self-Diagnosis by Malfunction Code" PDFs — not PK-specific)

**Status: 83 codes across 3 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `daikin-pk-split-inverter.json` | Split inverter K-Series service manual codes (FTXS35-50K2V1B / RXS) | 28 |
| `daikin-pk-split-user-remote.json` | R32 split FTXC-C user manual + ARC remote codes (PK-sold Sensira class) | 14 |
| `daikin-global-smts3-ra-skyair.json` | SM-TS3 Simple Self-Diagnosis malfunction codes (Sky Air / RA, global) | 41 |

Model-scoped series files never merged where source families diverge (K-Series service vs FTXC-C user vs SM-TS3 global).

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Daikin Service Manual SiBE041213E — Inverter Pair Wall Mounted K-Series | service_manual | http://www.daikintech.co.uk/Data/Split-Sky-Air-Indoor/FTXS/2013/FTXS-K2V1B/FTXS35-50K2V1B_SM.pdf | 28 letter+digit self-diag codes (00, U0/U2/U4/UA, A1/A5/A6, C4/C9, E1-E8/EA, F3/F6, H0/H6/H8/H9, J3/J6, L3/L4/L5, P4) | 2026-09-24 |
| 2 | Daikin Room AC Operation Manual — R32 Split FTXC-C (Sensira) | user_manual | https://www.daikin.eu/content/dam/document-library/operation-manuals/ac/split/ftxc-c/FTXC-C_3P621306-1_Operation%20manual_English.pdf | 14 codes (A3, C5, C7, CC, E3, F8, FA, H3, J8, J9, U3, UF, UH, AH) + ARC remote code lists; PK-sold FTXC50DV1B/FTXC60DV1B class | 2026-09-24 |
| 3 | Daikin SM-TS3 — Simple Self-Diagnosis by Malfunction Code (List of Error Codes pp.1-6) | official_support | https://www.daikin.com/-/media/Project/Daikin/daikin_com/products/ac/services/error_codes/pdf/sm-ts3_p1-6_errorcode-pdf.pdf | 41 codes (A0/A7/A8/A9/AA/AF/AJ, C1/C6/CA/CJ, E0/E4/E9, H4/H5, J1/J2/J4/J5/J7/JA/JC, L0/L1/L6/L8/L9/LA/LC, P1/P3, U1/U5/U6/U7/U8/U9/UC/UE/UJ) — official global | 2026-09-24 |
| 4 | Daikin SM-TS3 — how to check (pp.7-8) | official_support | https://www.daikin.com/-/media/Project/Daikin/daikin_com/products/ac/services/error_codes/pdf/sm-ts3_p7-8_howtocheck-pdf.pdf | Checking procedure for SM-TS3 codes | 2026-09-24 |
| 5 | Daikin global error_codes landing page | official_support | https://www.daikin.com/products/ac/services/error_codes | Confirms official table exists (SM-TS3), scope = Sky Air / split families | 2026-09-24 |
| 6 | PK retailer / distributor recon | retailer_page | aysonline · maqsoodandsons · gulfelectronics · bijlibazar · w11stop | PK SKU confirmation (Sensira / split inverter class); no code tables on retailer pages | 2026-09-24 |
| 7 | daikin.com.ph / other-region support | official_support | (bot-walled / mirrors) | Corroboration attempts; not PK-scoped | 2026-09-24 |

Direct lg.com/daikin.com fetches via r.jina.ai when 403/404/406/Cloudflare-gated.

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | K-Series service manual codes vs FTXC-C user manual codes (digit divergence in overlap families) | **Separate series files — never merged** (model-scoped) |
| 2 | SM-TS3 global vs PK-local table | No PK-local table exists; global SM-TS3 = official for PK-sold Sky Air/split families (noted in brand notes) |
| 3 | F8 / FA no official cause lists in user manual | Confidence medium (documented per-code) |
| 4 | VRV module codes vs PK residential split | VRV mostly out of PK residential scope — quarantined (4d-style) |
| 5 | Per-count LED blink map absent everywhere | All blinkPattern null; documented as open gap (Daikin uses PCB LED blink counts not published in sources found) |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|--------------|
| Daikin Japan / US / India-only pages treated as PK tables | Region collision risk — used only to corroborate same code family |
| VRV / Sky Air VRF module codes not sold in PK residential channel | Out of PK residential scope (unless evidenced in PK sources) |
| Non-AC Daikin products (chiller/industrial unless PK-relevant split) | Out of scope slice |
| Other-brand tables on multi-brand pages | Never imported |
| TV/mobile/fridge/other appliance codes (any brand) | Wrong appliance |

## Exhaustiveness (Phase D)

- Official Daikin publishes error table: **YES (global SM-TS3)** — no PK-localized version (daikin.com/pk 404, daikin.com.pk 406).
- Service manual (K-Series) + user manual (FTXC-C) = high for their model families; SM-TS3 = high (official PDF).
- **No per-count LED blink map found in any source** (all blinkPattern null) — open gap.
- F8/FA official cause lists missing → medium.
- Stop rule: successive batches yielded no new Daikin-badged codes beyond the three series.
- Open gaps: PK-specific fault sheet (MIA distributor), LED blink count map PDF, full VRV set for PK commercial if needed.

## Offline follow-up paths

- MIA distributor (PK) — request PK fault-code sheet / service manual.
- daikintech.co.uk mirrors — full FTXC/K-Series service manual downloads for blink maps.
- Daikin global SM-TS3 PDF — re-fetch for updates; check SM-TS2/TS1 variants.
- YouTube PK technicians — field corroboration for Sensira / split inverter codes.
