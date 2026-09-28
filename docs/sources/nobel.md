# Nobel — sources

Brand id: `nobel` · Category: `ac` · Researched: 2026-09-23
Official site: https://nobelpakistan.com · Support: **0800-NOBEL (0800-66235)** · support@nobelpakistan.com

**Status: ZERO-CODE documented gap — units advertise "Self-Diagnose Function" but no fault table is published anywhere.**

## Verified model lines (nobelpakistan.com)

- **18T3 DC Inverter** (official product page; compressor = **GREE FTZ-AN125AFBA-A** rotary; advertises Self-Diagnose)
- NSAC18T, NSAC18VTL, NCAC30, NFA60T3 (self-diagnose advertised per product copy)
- Old category URL `/product-category/air-conditioner/` = **404**; current category only lists 18T3
- Remote IR protocol decoded as **Mirage KKG29AC1** (Arduino forum) — platform identity unresolved (Gree compressor + Mirage remote ≠ confirmed Gree code platform)

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Nobel PK official site + AC category + 18T3 product page + contact | official_support | https://nobelpakistan.com/product-category/ac/ · https://nobelpakistan.com/products/18t3-dc-inverter/ | Model roster; "Self-Diagnose Function" claim; support contacts; **no manuals, no fault codes** | 2026-09-23 |
| 2 | Nobel UAE catalog PDF | official_support | http://nobel.ae/upload/category_catalogues/zZQwAheYkQLL1JMGiNCwgaYb8jCIAj4NsqsOrpJl.pdf | Specs only — **no codes** | 2026-09-23 |
| 3 | fully4world / electrafix / aysonline / japanelectronics site: searches | retailer_page / other | various | **No Nobel-badged error pages** (negative evidence) | 2026-09-23 |
| 4 | ManualsLib / Scribd / YouTube EN+Urdu | other | various | **No Nobel AC manuals or fault walkthroughs** (negative evidence) | 2026-09-23 |
| 5 | Arduino forum — remote decode KKG29AC1 MIRAGE | forum | arduino.cc | Remote protocol identity (not an error table) | 2026-09-23 |
| 6 | YoReparo (Spanish) "F8" 2013 | forum | yoreparo.com | **Foreign/different Nobel brand — excluded** | 2026-09-23 |

## Conflicts log (resolved in data notes)

| Topic | Conflict | Resolution used |
|---|---|---|
| Gree-compressor → Gree codes? | Roadmap "Gree-compressor based"; 18T3 uses GREE FTZ compressor | **≠ Gree platform.** Gree E/F/P tables that appear in "Nobel AC error code" searches belong to **Gree/Orient/AUX/Kenwood** — hard rule: never imported; codes[] stays empty |
| Self-Diagnose vs no table | Product pages advertise self-diagnose; no public table | Gap logged — codes likely exist in-box/on-display but unpublished online; request manual via 0800-NOBEL |
| Dead official URL | `/product-category/air-conditioner/` 404 | Noted; live category = `/product-category/ac/` (18T3 only) |
| Foreign Nobel hits | nobel.bg heat pumps, YoReparo F8, Nobu/Smith+Nobel | Excluded entirely |

## Exhaustiveness check (Protocol Phase D)

- ~8+ successive search rounds after first hits: EN + Urdu, site: filters (nobelpakistan, fully4world, electrafix, aysonline, japanelectronics), manuals, Scribd, YouTube, forums — **zero Nobel-badged code tables**; official site fully crawled (no hidden downloads) — **Phase D stop condition met**.
- Codes covered: **0 entries** (`codes: []` by design — advertised self-diagnose does not authorize inventing codes).
- Known remaining gap: in-box user/service manual for 18T3 + NSAC/NCAC/NFA series; contact 0800-NOBEL / support@nobelpakistan.com. If a table appears (photo of in-box manual → transcribe), re-run Phases B–D and populate.
- Residual risk: search-engine rate limits during part of research — small chance of a buried non-indexed PDF.
