# LG (AC) — sources

Brand id: `lg` · Category: `ac` · Researched: 2026-09-24
Official: https://www.lg.com/pk (Dual Inverter, Art Cool; Multi F(DX) split; Multi V VRF enterprise)
PK distributors: Muller & Phipps (M&P) noted in recon (see research.md) · retailers aysonline · japanelectronics · maqsoodandsons · gulfelectronics · bijlibazar · w11stop (recon)
Official published AC error table: **YES** https://www.lg.com/us/support/help-library/lg-air-conditioner-guide-to-error-codes--20155047719306 (US help library; Kenya/Nigeria mirrors; **no PK-localized version found**)

**Status: 79 codes across 5 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `lg-pk-split-universal.json` | Universal split fault codes (indoor 01-10, outdoor 21-105, HL/CL/Po) | 43 |
| `lg-pk-rac-support-guide.json` | RAC CH codes from LG official help library guide to error codes | 13 |
| `lg-pk-multi-fdx.json` | Multi F(DX) multi-split fault codes (CH01-CH61, Lo) | 16 |
| `lg-pk-single-zone-hvac.json` | Single zone wall-mounted HVAC common error codes (DFS-TP-AH-001-US) | 4 |
| `lg-pk-multi-v-partial.json` | Multi V VRF CH codes (partial, PK-relevant commercial only) | 3 |

Numeric split codes vs CH## family kept in separate series files (different display/product lines) — never merged.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | LG Air Conditioning — Universal Split Fault Codes Sheet (Macedo/Orion, Oct 2010) | service_manual | https://www.mbsm.pro/wp-content/uploads/2026/01/LG_Split_Systems_Fault_Codes.pdf | Primary series: indoor 01–10 + HL/CL/Po, outdoor 21–105, LED tens/units scheme, per-code troubleshooting | 2026-09-24 |
| 2 | LG Air Conditioner Guide to Error Codes (LG USA Help Library) | official_support | https://www.lg.com/us/support/help-library/lg-air-conditioner-guide-to-error-codes--20155047719306 | CH04/CH05/CH07/CH38/CH54/CH61/CH66/CH10/CH67/CH90/CH91/CH93/CH237 (official, high) | 2026-09-24 |
| 3 | LG Air Conditioning Multi F(DX) Fault Codes Sheet (Macedo, Nov 2007) | service_manual | https://www.orionair.co.uk/PDF/lg%20multi%20fault%20codes.pdf | Multi-split CH01/C1… aliases, fascia red/green flash maps, CH05 vs outdoor 53, Lo test mode | 2026-09-24 |
| 4 | LG Defining Common Error Codes — LS091HSV/LS121HSV (DFS-TP-AH-001-US) | official_support | lg.com tech paper PDF | Numeric 12/31/5/53 single-zone wall-mount (official, model-scoped) | 2026-09-24 |
| 5 | Ample Air — LG air conditioner CH fault codes | technician_note | https://ampleair.com.au/knowledge-base/lg-air-conditioner-ch-fault-codes/ | CH11/CH42/CH43 Multi V partial (technician KB, medium) | 2026-09-24 |
| 6 | LG Africa/Kenya/Nigeria support mirrors | official_support | lg.com/africa support CT20080061 | CH family corroboration (no PK version) | 2026-09-24 |
| 7 | LG PK product/support recon | official_support | https://www.lg.com/pk | Dual Inverter / Art Cool / Multi V lineup; 403 on help-library | 2026-09-24 |
| 8 | PK retailer pages (aysonline, maqsoodandsons, gulfelectronics, bijlibazar, w11stop) | retailer_page | various | PK SKU/series confirmation only — no code tables on retailer pages | 2026-09-24 |

ManualsLib / lg.com fetched via r.jina.ai when Cloudflare/403-gated.

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Numeric universal-split codes (01-10/21-105) vs CH## official family | Kept in **separate series files** (different display lines / product generations) — never merged |
| 2 | CH05 in Multi F(DX) sheet vs outdoor 53 in universal sheet | Documented per-code in notes; separate files |
| 3 | No PK-localized official table (lg.com/pk 403/stubs) | US/Africa official mirrors used as same code family; noted medium-high |
| 4 | Single-zone DFS tech paper numeric codes (12/31/5/53) collide digits with universal split codes | Separate `lg-pk-single-zone-hvac.json` file — model-scoped, never merged |
| 5 | Most Multi V VRF module codes out of PK residential scope | Quarantined; only 3 CH codes evidenced in Ample Air KB kept as `lg-pk-multi-v-partial` (medium) |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|--------------|
| LG TV / mobile / fridge / washer codes (incl. 4E water-supply contamination on shared pages) | Wrong appliance |
| Other-brand tables on multi-brand pages (Haier/Gree/Dawlance/Koppel mixes) | Never imported |
| ausairconditioners / generic HVAC lookalike tables colliding with LG meanings | AU retailer blog, not LG-documented |
| Multi V VRF GHP-R410A/R22 P8xx / E92x hydro module families | Commercial modules not evidenced in PK residential channel |
| Wired-remote numeric duplicates of CH/E codes double-counted | Documented as notes in series B rather than double-counted |
| Samsung TV/mobile/fridge etc. (other brands) | Other brand |

## Exhaustiveness (Phase D)

- Official LG publishes error table (US help library CH family) — **no PK-localized version** (lg.com/pk 403/stubs).
- Service manual PDFs (Universal Split + Multi F(DX)) = high for numeric split + Multi F(DX) CH.
- No full LED blink-count map for all PK models — LED tens/units scheme described in Universal Split sheet; fascia red/green flash maps in Multi F(DX).
- Stop rule: successive batches yielded no new LG-badged codes beyond the five series.
- Open gaps: PK-localized official table; full Multi V VRF code set for PK commercial channel; complete LED blink map PDF.

## Offline follow-up paths

- lg.com/pk support — request PK fault-code sheet / service manual (403 offline).
- ManualsLib full downloads for LS091HSV/LS121HSV and Art Cool / Dual Inverter manuals — visual verify blink maps.
- M&P distributor — PK technician booklet.
- Multi V enterprise support channel (samsunghvac-equivalent LG channel) for VRF CH codes.
