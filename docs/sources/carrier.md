# Carrier (AC) — sources

Brand id: `carrier` · Category: `ac` · Roadmap row 46 · Researched: 2026-09-25
Official: https://www.carrier.com · AU/NZ & regional residential range sold by **AHI Carrier (Australia)** — public fault-code library: https://www.carrierair.com.au/installer-and-technical-support/fault-codes/ (24 official PDFs)
PK presence: **YES, no official PK site** — Smart Climate Solutions Pvt Ltd = "authorized distributor and service provider of Carrier in Pakistan" (climatesolutions.com.pk); dhabione.pk sells Carrier `38QHA018VSP` / `38QHA036VSP` inverter hi-wall splits; OLX PK lists Carrier window + inverter ACs. `carrier.com/commercial/en/pk/` → 404; **`carrier.com.pk` = Mubashar Electronics, Hall Road Lahore (stabilizers/solar) — name collision, quarantined**; `carrierpakistan.com` dead
Official published Carrier **residential AC** error table: **YES** — AHI Carrier AU library (PDFs 246/247 Elite, 249 SHV, shareddocs 45MHHAQ owner's manual)

**Status: 118 codes across 5 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `carrier-comfort22-45mhhaq.json` | Comfort 22 high-wall heat pump (45MHHAQ/37MHRAQ, R-454B) — hex codes `EC07`…`PC0L` + `----`/`DF`/`FC` status (indoor digital tube) | 34 |
| `carrier-multisplit-38gvm-40gvm.json` | Multi Split Ductless 38GVM/40GVM indoor display chart — `08`/`0A`/`F0`…`dn`, size-scoped notes (18K/24K/30K vs 36K/42K) | 56 |
| `carrier-elite-luvh-ep.json` | Elite hi-wall 42LUVH / Elite N + N-1 — E/P scheme (E0–E7, P0–P4; E4/P3 reserved) | 11 |
| `carrier-shv-inverter-ducted-7-1a.json` | SHV **inverter** ducted, manual Table 7-1a | 10 |
| `carrier-shv-fixed-ducted-7-1b.json` | SHV **fixed-speed** ducted, manual Table 7-1b — same letters, different meanings | 7 |

**Model-scoped — 22 digit overlaps across files, 0 identical meanings** (E1/E2/E3/E4/E5/E6/E7/E8/EE/F3/F4/P0 diverge per line; SHV 7-1a vs 7-1b split by inverter vs fixed-frequency nameplate). Never merged.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | AHI Carrier AU — Fault Codes index (24 official PDFs) | official_support | https://www.carrierair.com.au/installer-and-technical-support/fault-codes/ | Locator/titles for PDFs 246/247/249 (imported) and the 19 unread ones (NEGATIVE) | 2026-09-25 |
| 2 | Carrier 45MHHAQ High Wall Heat Pump Owner's Manual, Table 4 | user_manual | https://www.shareddocs.com/hvac/docs/1009/Public/08/45MHHAQ-01OM.pdf | Block 1 — all 34 rows (read via r.jina.ai) | 2026-09-25 |
| 3 | SplitAtlas — Carrier error codes (sourced to 45MHHAQ) | technician_note | https://splitatlas.com/error-codes/carrier | Line-by-line cross-check of Block 1 (31 + DF/FC) | 2026-09-25 |
| 4 | RemoveAndReplace — Carrier Split System error codes (38GVM/40GVM) | technician_note | https://removeandreplace.com/2017/08/11/carrier-split-system-air-conditioner-error-codes-and-troubleshooting/ | Block 2 — 56 indoor rows + size-split notes; numeric 13–57/C5 chart = open gap | 2026-09-25 |
| 5 | HowTo HVAC — Mini Split Error Code List (Carrier table, 54 rows) | technician_note | https://www.hvachowto.com/mini-split-error-code-list-and-meanings/ | Independent cross-check of 54/56 Block 2 rows (F0/H2 single-source → medium) | 2026-09-25 |
| 6 | Carrier Elite 42LUVH055N/065N/075N — Indoor Unit Error Display (official) | service_manual | https://toshiba-aircon.com.au/techdocs/api/files/246 | Block 3 N-series chart (11) + "E4 & P3 reserved" | 2026-09-25 |
| 7 | Carrier Elite 025N-1…075N-1 — PART7 Trouble Shooting (official) | service_manual | https://toshiba-aircon.com.au/techdocs/api/files/247 | Block 3 N-1 wording for E3/E5/E7/P0 (logged in notes) | 2026-09-25 |
| 8 | Carrier SHV Inverter Ducted & TSV Cassette owner's manual, Tables 7-1a/7-1b (official) | user_manual | https://toshiba-aircon.com.au/techdocs/api/files/249 | Blocks 4 + 5 (page image read where digital-tube font dropped) | 2026-09-25 |
| 9 | Ample Air — Carrier air conditioner fault codes | technician_note | https://ampleair.com.au/knowledge-base/carrier-air-conditioner-fault-codes/ | Independent re-typing of 7-1a/7-1b + both Elite charts | 2026-09-25 |
| 10 | Smart Climate Solutions — Carrier after-sales in PK | distributor_page | https://www.climatesolutions.com.pk/maximizing-ac-lifespan-expert-tips-from-carriers-after-sales-team-in-pakistan/ | PK authorized-distributor evidence | 2026-09-25 |
| 11 | Dhabione — Carrier 1.5 T split 38QHA018VSP | retailer_page | https://dhabione.pk/product/carrier-split-air-conditioners-1-5-ton-model-38qha018vsp-inverter-compressor-1-year-full-5-years-compressor-made-in-china/ | PK-sold model numbers (Cloudflare-gated; read via search index) | 2026-09-25 |
| 12 | OLX Pakistan — Carrier AC listings | retailer_page | https://www.olx.com.pk/ac-coolers_c1619/q-carrier-ac | PK market evidence (window, inverter split+tower) | 2026-09-25 |
| 13 | Carrier Middle East — Inverter 38QHA/42QHA High Wall | official_support | https://www.carrier.com/commercial/en/ae/products/residential/duct-free-systems/inverter/ | Regional residential range / PK hi-wall family | 2026-09-25 |
| 14 | Carrier 42LUVH055N Service Manual (Drive mirror, cover) | service_manual | https://drive.google.com/open?id=13RvztDMIydaENCBV-yp0dai4mTrWmHoN | Confirms LUVH = DC Inverter Hi-Wall (unitType split) | 2026-09-25 |
| 15 | airconditioningmanuals.com — Carrier index | other | https://www.airconditioningmanuals.com/carrier/ | Readable 42LUVH mirror after ManualsLib 403 | 2026-09-25 |
| 16 | HowTo HVAC — Carrier Mini Split Error Codes List (generic) | technician_note | https://www.hvachowto.com/carrier-mini-split-error-codes-list/ | **Nothing imported** — self-contradictory (see Quarantine) | 2026-09-25 |
| 17 | TRS Engineering — error-code index (JP) | technician_note | https://trseng.jp/errorcode/ | Confirmed **no Carrier page** exists there (negative) | 2026-09-25 |

Notes: carrierair.com.au PDFs need browser headers + referer (else 406); shareddocs/splitatlas/ampleair read through `r.jina.ai` (direct 403/timeout); ManualsLib + Studocu 403/bot-gated.

## Conflicts log

| ID | Conflict | Resolution |
|----|----------|------------|
| 1 | Same digits across product lines (E1…P0 in multi-split vs Elite vs SHV 7-1a vs 7-1b) | **5 separate series files — never merged**; all 22 overlaps verified title-divergent (0 identical meanings) |
| 2 | SHV Table 7-1a (inverter) vs 7-1b (fixed) share E2/E3/E7 wording but E4/E5/E6/E8 differ | Split by nameplate (inverter vs fixed-frequency) — two files, series notes state the rule |
| 3 | Block 2 F0/H2 — RemoveAndReplace alone lists them (HowTo HVAC 54-row chart omits) | `confidence: medium` + gap written in entry notes (per-code) |
| 4 | HowTo HVAC generic Carrier scheme contradicts its own 54-row table (E1 = communication vs high pressure; P4/P5/P6 duplicated) | **Quarantined in full** — self-contradictory, unattributed |
| 5 | RemoveAndReplace P0 typo disagreement (vs HowTo HVAC) | Logged in entry note; R&R wins (primary chart for this series) |
| 6 | Multi-split chart size-split (27/56 codes exist on one indoor-size side only) | Per-entry `notes` repeat the applicable sizes instead of pretending universality |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|--------------|
| `carrier.com.pk` (Mubashar Electronics, Hall Road Lahore) | **Name collision** — stabilizers/solar/UPS under the Carrier wordmark; not Carrier Corporation; no AC error content |
| `carrierpakistan.com` | Dead domain |
| Carrier chillers, VRF, AHU, fan coils, cooling towers, "Carrier Ducted CP", 10-ton rooftop | **Commercial scope** — row 46 = residential only |
| Toshiba Carrier (JV), Trane, Bryant, Payne, Comfortline, Ciata | **Other brand** — own schemes / sibling brands never merged |
| HowTo HVAC generic Carrier mini-split list (EO/E1…/F0…/P0…) | Self-contradictory + unattributed — not importable |
| SEO aggregators (hoffmannbros, homeessentialsguide, shopboldr, hvacinexpert, basicknowledgehub, urbanserviceplaza, appliancecodebase) | Conflicting meanings, no primary citation |
| Studocu 38QHA/42QHA tech spec | Bot-gated — only an "EC" search snippet visible; nothing imported |
| ManualsLib direct (42LUVH055N, 40MAHBQ…) | 403 — superseded by Drive/airconditioningmanuals mirrors |
| OLX PK classifieds | Market evidence only — no code tables from ads |
| Carrier Enterprise / 40MAQ NA ductless scheme (E0–E5/EC/F0–F5/P0–P4, Dr. Smart) | Not PK-confirmed + would collide with Block 1 digits — open follow-up, never guessed |
| Alpha / Alpha Inverter lamp-only tables (official PDFs 244/245) | Lamp patterns, no alphanumeric display codes — separate future lamp-pattern scope |
| Other-brand tables in SERPs (Daikin, LG, Gree, Midea, Mitsubishi, TCL…) | Other brand — never merged |

## Exhaustiveness (Phase D)

- Official Carrier residential AC error tables: **YES** — AHI Carrier AU library (24 PDFs); 4 tables fully imported (45MHHAQ OM, Elite 246/247, SHV 249) = 62 official rows + 56 multi-split rows from the RemoveAndReplace/HowTo HVAC pair.
- PK presence: **YES** (Smart Climate Solutions authorized distributor; dhabione/OLX models) — but **no PK-hosted error table and no official Carrier PK site**.
- Total 118 = Comfort 22 34 high + multi-split 56 (54 high / 2 medium) + Elite 11 high + SHV inverter 10 high + SHV fixed 7 high.
- Stop rule: final search rounds added no new Carrier-badged residential codes.
- Open gaps: **no readable table for the PK-sold `38QHA/42QHA` hi-wall** (Studocu bot-gated, Carrier AU QHC PDF = Type3 font) — biggest PK-relevant gap; 19 of 24 official AU PDFs unreadable (scans / digital-tube font, no tesseract/PyMuPDF installed) incl. official multi-split `1055`; multi-split numeric `13–57`/`C5` unit-address chart single-source (30 rows) — recorded not imported; Enterprise/40MAQ scheme not PK-confirmed; Alpha lamp-only tables pending.

## Offline follow-up paths

- Install `tesseract-ocr`/`pymupdf` → re-read the 19 unread Carrier AU PDFs (esp. `1055` official multi-split + `1040/1043` QHC for the PK QHA/QHC line).
- Smart Climate Solutions (climatesolutions.com.pk) — request a PK fault-code sheet for 38QHA/42QHA.
- Studocu QHA doc via logged-in session or paper copy in the box.
- SplitAtlas / Ample Air re-check after any manual revision.
