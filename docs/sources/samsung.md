# Samsung (AC) — sources

Brand id: `samsung` · Category: `ac` · Researched: 2026-09-24
Official: https://www.samsung.com/pk (WindFree™, wall-mounted; no current window line found on PK site)
PK retailers: aysonline · japanelectronics · maqsoodandsons · gulfelectronics · bijlibazar · w11stop (recon)
Official published AC error table: **YES (partial)** https://www.samsung.com/pk/support/home-appliances/check-out-the-displayed-error-codes-on-samsung-indoor-unit-air-conditioner/ (7+ codes + CF FAQ)

**Status: 91 codes across 4 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `samsung-pk-indoor-display-official.json` | Official Samsung PK indoor display codes (partial table + CF FAQ) | 10 |
| `samsung-pk-split-inverter.json` | Residential split inverter service-manual E-codes (AR/AC types sold in PK) | 43 |
| `samsung-windfree-r32.json` | WindFree R-32 split inverter C-codes (AR9500T family) | 33 |
| `samsung-cassette-led-blink.json` | Cassette / 360° cassette LED colour-blink patterns | 5 |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Official Samsung PK indoor display error codes | official_support | https://www.samsung.com/pk/support/home-appliances/check-out-the-displayed-error-codes-on-samsung-indoor-unit-air-conditioner/ | CF, Cl, dF, E1 21/22/54/63, E5, E6, E7 (partial official PK table) | 2026-09-24 |
| 2 | Samsung AC026MNJDKH/EU service manual | service_manual | ManualsLib AC026MNJDKH p.38 | Self-diagnostic E-code table | 2026-09-24 |
| 3 | Samsung AC052MNADKH/EU service manual | service_manual | ManualsLib AC052MNADKH p.36 | LED + E-code table | 2026-09-24 |
| 4 | Samsung HCADKH service manual | service_manual | ManualsLib HCADKH p.48 | E-code table | 2026-09-24 |
| 5 | SplitAtlas Samsung error codes index | other | https://splitatlas.com/error-codes/samsung | C-series R-32 WindFree/split tables (aggregator, cross-checked vs manuals) | 2026-09-24 |
| 6 | Snowflake Aircon Samsung blinking light guide | other | (manual-derived third-party) | Cassette LED colour-blink patterns | 2026-09-24 |
| 7 | Ample Air Samsung fault codes | other | (manual-derived third-party) | Cassette LED corroboration | 2026-09-24 |
| 8 | samsung.com/pk product/support recon | official_support | https://www.samsung.com/pk | WindFree / wall-mounted line confirmation; no full master table | 2026-09-24 |

ManualsLib pages fetched via r.jina.ai when Cloudflare-gated.

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | **C121 vs E121** — similar digits different code spaces | Kept **unmerged** (separate series files; C = R-32 WindFree, E = service-manual split) |
| 2 | SplitAtlas is aggregator, not official | Medium confidence; cross-checked against Samsung E-code manuals where possible |
| 3 | Cassette LED blink guide is third-party (manual-derived) | Medium confidence; not official PDF — noted per-code |
| 4 | Official PK table is partial (7 codes only) | Cannot claim full coverage — service manuals fill E-codes, C-codes via SplitAtlas |
| 5 | Dual-segment display codes ("E1 21") vs single E101-style | Preserved as displayed; not collapsed into E1 |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|----------------|
| Samsung TV / mobile / fridge / washer codes | Wrong appliance |
| Other-brand tables on multi-brand pages | Never imported |
| Non-Samsung service manuals in search hits | Other brands |
| Samsung non-AC product support errors | Out of AC scope |

## Exhaustiveness (Phase D)

- Official PK table exists but is **partial** (indoor display subset only) — no full official PK master table/PDF.
- Service manuals (AC026/AC052/AM072/HCADK) = high for E-codes; SplitAtlas = medium for C-codes (WindFree R-32).
- No full LED blink map PDF for all models — cassette colour-blink only (third-party).
- "Smart Comfort" branding not confirmed on samsung.com/pk; no current window models on PK site (negative).
- Stop rule: successive batches yielded no new Samsung-badged codes beyond the four series.
- Open gaps: full official PK master table; complete LED blink map PDF; Smart Comfort confirmation.

## Offline follow-up paths

- samsung.com/pk support — request full PK fault-code sheet / service manual.
- ManualsLib full downloads for AC026/AC052/HCADK — visual verify blink maps.
- SplitAtlas re-fetch for any C-codes beyond the 33.
- Samsung Members / SmartThings PK community — field reports.
