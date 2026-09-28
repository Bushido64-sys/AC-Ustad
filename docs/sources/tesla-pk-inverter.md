# Tesla PK (inverter) — sources

Brand id: `tesla-pk` · Category: `inverter` · Researched: 2026-09-24
Official: https://tesla-pv.com · Tesla Pakistan solar brand (Karachi/Islamabad) since 1992
Support: **0321-8375278** (Tesla-PV.com listing) · Tesla Smart Android/iOS app (QR in install guide)

**Status: 143 codes across 6 series files — Infinity HLE 43 (high) + Infini VII 38 + Infini VIII 37 + Axpert VM 25 (medium); 2 empty series (HSC, other Infinity lines).**

**≠ Tesla Inc (tesla.com / EV brand).** This is PK solar inverter brand "Tesla" / Tesla-PV.

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `infinity-hle.json` | Infinity HLE / HLE-V 3/5KW hybrid | **43** (31 faults 01–09/11/21–27/31–33/41–44/51–53/55–58 + 12 warnings 61–77) — high |
| `infini-vii.json` | InfiniSolar VII / Infinity Infini VII 2/3/5/6KW | **38** (19F + 8 W-prefixed + parallel 60/71/72/73/80–86) — medium |
| `infini-viii.json` | InfiniSolar VIII / Infinity Infini VIII 6KW | **37** (19F + 8 W-prefixed + parallel 60/71/72/80–86 — no 73) — medium |
| `axpert-vm.json` | Axpert VM III / VMiii PRIME 1.5/3/5KW | **25** (16F 01–09/51–53/55/57–59 + 9 W-prefixed warnings) — medium |
| `hsc.json` | HSC series (roadmap) | 0 (no Tesla PK HSC manual/fault table found) |
| `tesla-infinity-other.json` | Infinity 15KW and other lines | 0 (brochure/spec pages only) |

### Badge evidence

- **HLE (high):** Scribd titles `Tesla Infinity HLE V Inverter Manual` (related list on HLE-V doc), `Tesla Infinity Hle User Manual` (**948187237**), `Tesla-Infinity-HLE-final-manual-18kw-Hybrid-Tesla-New` (**696423711**), installation guide doc **948187237** / **812271469** (`TESLA-INFINITY-HLE-15-KW-INSTALLATION-MANUAL`); install-guide body references **TESLA logo** on main page + Tesla Smart App QR. Tesla-PV.com HLE brochures (Islamabad 0321-8375278).
- **Infini VII/VIII (medium):** Voltronic-class OEM manuals (`ivii_wb` / `iviii_wb`) have **no Tesla string**; association via Tesla Infinity 6kW Scribd listing + Maxsolar "Tesla Infinity InfiniSolar VII" brochure. OEM-twin rule → medium.
- **Axpert VM III (medium):** manual is generic Axpert; association via Scribd doc **896972628** `VMiii PRIME Tesla-61425` (description: "TESLA VMiii PRIME latest 8000pv datasheet", user Ali-Faizur-Rehman) → Tesla SKU association → medium.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Infinity HLE-V hybrid inverter manual (Scribd) | user_manual | https://www.scribd.com/document/634110548 | HLE-V fault table 31F + 12W (high — Tesla Infinity listing corroboration) | 2026-09-24 |
| 2 | Tesla Infinity Hle User Manual (Scribd) | user_manual | https://www.scribd.com/document/948187237 | Tesla-badged HLE title + install-guide Tesla logo/App references (high for brand) | 2026-09-24 |
| 3 | Tesla-Infinity-HLE-final-manual-18kw (Scribd) | user_manual | https://www.scribd.com/document/696423711 | Tesla-badged HLE 18KW listing (brand corroboration) | 2026-09-24 |
| 4 | Tesla Infinity HLE installation manual (Scribd) | user_manual | https://www.scribd.com/document/812271469 | TESLA-INFINITY-HLE-15-KW install guide (brand + model line) | 2026-09-24 |
| 5 | Tesla-PV.com (official site / brochures) | official_support | https://tesla-pv.com | Brand existence, HLE product line, contact 0321-8375278 | 2026-09-24 |
| 6 | Hybrid 2/3/5/6KW InfiniSolar VII-class manual (ivii_wb) | user_manual | file:/tmp/opencode/tesla-pk/ivii_wb.pdf | Infini VII codes 19F+8W+parallel (medium — no Tesla badge in PDF) | 2026-09-24 |
| 7 | Hybrid 6KW InfiniSolar VIII-class manual (iviii_wb) | user_manual | file:/tmp/opencode/tesla-pk/iviii_wb.pdf | Infini VIII codes (medium — parallel lacks 73 vs VII) | 2026-09-24 |
| 8 | Axpert VM III 1.5/3/5KW manual | user_manual | file:/tmp/opencode/tesla-pk/axpert_vmiii.pdf | VM III table 16F+9W (medium) | 2026-09-24 |
| 9 | VMiii PRIME Tesla-61425 (Scribd) | other / SKU listing | https://www.scribd.com/document/896972628 | Tesla SKU association for Axpert VM table (medium import license) | 2026-09-24 |
| 10 | Maxsolar Tesla Infinity InfiniSolar VII brochure | retailer_page | file:/tmp/opencode/tesla-pk/maxsolar_tesla_infini_vii.pdf | Tesla + InfiniSolar VII co-listing (platform hint → medium) | 2026-09-24 |
| 11 | Tesla Inc (tesla.com) / Tesla energy pages | other — **EXCLUDE** | https://www.tesla.com | Different company — hard-excluded | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | "Tesla" = EV brand (Tesla Inc) in global search | **Hard rule:** Tesla PK / Tesla-PV / Tesla Infinity ≠ Tesla Inc. Never import Tesla Powerwall/Megapack codes. |
| 2 | HLE-V manual title says "Infinity HLE-V" without Tesla in one Scribd doc (634110548) | Brand corroboration from related Tesla Infinity HLE listings (948187237, 696423711) + Tesla-PV brochures → HLE series **high**. |
| 3 | Infini VII/VIII PDFs have zero Tesla badge | OEM-twin rule: Tesla Infinity 6kW Scribd listing + Maxsolar Tesla brochure → **medium**, not high. Per-code notes record no-badge. |
| 4 | Infini VII parallel includes 73 (AC output setting different); VIII does not | Model-scoped — parallel lists kept separate per series file (do not merge). |
| 5 | Axpert VM III manual is generic Voltronic; Tesla link only via Scribd 896972628 | Medium via SKU listing `VMiii PRIME Tesla-61425`; not high (no Tesla-badged full manual body). |
| 6 | HSC series on roadmap with zero evidence | Zero-code gap series — documented, not fabricated. |
| 7 | "Infinity" as sub-brand vs InfiniSolar OEM name | Series ids: `infinity-hle` (Tesla-badged HLE), `infini-vii`/`infini-viii` (OEM twins), `axpert-vm` (VM SKU). Notes record naming. |
| 8 | HLE warnings use 61–77; hybrid core uses 01–19 W-codes | Different firmware families — separate series files; do not cross-map W01↔61. |

## Exhaustiveness (Phase D)

- Queries: tesla-pv.com, Tesla Infinity HLE / HLE-V / Infini VII / Infini VIII / VMiii PRIME / HSC manuals (Scribd, ManualsLib, Wayback), Tesla Smart app references, Maxsolar Tesla brochures, Tesla Inc exclusion sweeps.
- Stop rule met: HLE full table extracted (high); Infini/Axpert OEM tables extracted (medium via SKU/listing badges); HSC + other Infinity lines zero published tables.
- Highest-value unread: Tesla-badged full InfiniSolar VII/VIII manual if published (would promote medium→high); HSC manual if released; HLE 15KW full fault table (install guide only so far).

## Offline follow-up paths

- **0321-8375278** (Tesla-PV.com) — request HSC fault table, Tesla-badged Infini VII/VIII service manual, HLE 15KW code list.
- Tesla-PV.com contact form / Islamabad dealer network.
- In-box paper manuals from dealer units (HLE, Infini, VMiii PRIME).
- Tesla Smart app support channel for app-reported fault strings.
