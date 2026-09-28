# Electrolux (AC) — sources

Brand id: `electrolux` · Category: `ac` · Researched: 2026-09-24
PK site: https://electroluxpakistan.com/ (HTTP 403 on automated fetch; Lux Air T3 + Active Air ELSAC lines)
PK retailers (models only, no codes): japanelectronics.com.pk · maqsoodandsons.com · gulfelectronics.pk · elux.com.pk · friendshome.pk · jalalelectronics
PK lines: **Lux Air T3** ELSAC 12K/18K/24K LUX AIR T3 INV · **Active Air** ELSAC 12K/18K/24K ACTIVE AIR INV
Official published AC error table: **YES (Electrolux Ireland)** https://support.electrolux.ie/support-articles/article/air-conditioner-ac-error-messages (36 codes)

**Status: 43 codes across 7 series files (36 IE split + 4 portable UK + 3 PK video-title + 4 empty gap series).**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `electrolux-official-ie-ac.json` | Official Electrolux IE split/inverter error table (36 codes) | 36 |
| `electrolux-portable-uk.json` | Official Electrolux UK portable AC per-code articles | 4 |
| `electrolux-video-title-pk.json` | PK technician YouTube title-level only (E6/F1/E5) | 3 |
| `electrolux-lux-air-pk.json` | Lux Air T3 PK line — documented gap (no published codes) | 0 |
| `electrolux-active-air-pk.json` | Active Air PK line — documented gap (no published codes) | 0 |
| `electrolux-vega-user-manual.json` | VEGA series user manual — symptom-only (no codes) | 0 |
| `electrolux-ksv-user-manual.json` | KSV / Kelvinator AU user manual — symptom-only (no codes) | 0 |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Electrolux IE — Air conditioner (AC) error messages | official_support | https://support.electrolux.ie/support-articles/article/air-conditioner-ac-error-messages | Full 36-code split/inverter table (malfunction + code + solution): E2–E6,E8,H4,H6,C5,LP,L3,L9,Fo,F1–F6,F8,F9,FH,H1,H3,H5,HC,EE,PH,PL,U7,Po,P1–P3,LU,EU | 2026-09-24 |
| 2 | Electrolux UK — Portable AC code P1 (PI) | official_support | https://support.electrolux.co.uk/support-articles/article/portable-air-conditioner-displays-code-p1-pi | P1 = full bottom tray; drain steps | 2026-09-24 |
| 3 | Electrolux UK — Portable AC code L3 | official_support | https://support.electrolux.co.uk/support-articles/article/portable-air-conditioner-displays-code-l3 | L3 = condenser DC fan motor failure (portable) | 2026-09-24 |
| 4 | Electrolux UK — Portable AC code E7 | official_support | https://support.electrolux.co.uk/support-articles/article/portable-air-conditioner-displays-code-e7 | E7 = indoor motor failure (portable) | 2026-09-24 |
| 5 | Electrolux UK — Portable AC code E4 | official_support | https://support.electrolux.co.uk/support-articles/article/portable-air-conditioner-displays-code-e4 | E4 = display PCB ↔ main PCB communication fault (portable) | 2026-09-24 |
| 6 | Electrolux PK — Lux Air T3 / Active Air product pages | distributor_page | https://electroluxpakistan.com/ | PK model IDs (ELSAC *); no error table (403) | 2026-09-24 |
| 7 | Japan Electronics PK — Electrolux AC category | retailer_page | https://japanelectronics.com.pk/ | PK availability / models; no codes | 2026-09-24 |
| 8 | Maqsood & Sons PK — Electrolux AC | retailer_page | https://maqsoodandsons.com/ | PK models; no codes | 2026-09-24 |
| 9 | Gulf Electronics PK — Electrolux AC | retailer_page | https://gulfelectronics.pk/ | PK models; no codes | 2026-09-24 |
| 10 | Electrolux VEGA series user manual | user_manual | local:raw/vega_um.pdf (THD static) | Confirmed **no** code table (symptom troubleshooting only) | 2026-09-24 |
| 11 | Electrolux KSV/Kelvinator user manual (AU) | user_manual | local:raw/ksv_um.pdf (kelvinator.com.au) | Confirmed **no** code table | 2026-09-24 |
| 12 | Manualbox — Electrolux EACS-24HG53-A | user_manual | https://manualbox.com/en/model/electrolux-eacs-24hg53-a.html | Claims shared split codes; PDF fetch failed (HTTP 000) — **not used** | 2026-09-24 |
| 13 | YouTube — The technoboy, E6 non-inverter fix | video | https://www.youtube.com/watch?v=ZuiQEIW0OHc | Title-level: E6 communication (PK context) | 2026-09-24 |
| 14 | YouTube — E6/F1 inverter fix | video | https://www.youtube.com/watch?v=Jwxrb17I4mQ | Title-level: F1 inverter sensor context | 2026-09-24 |
| 15 | YouTube Shorts — E5 | video | https://www.youtube.com/shorts/a3DluyWXayc | Title-level: E5 overcurrent (PK) | 2026-09-24 |
| 16 | Scribd mirror — Electrolux AC Error Code Guide | other | https://www.scribd.com/document/429153245/AC-Error-Messages | Partial mirror of IE table (corroboration only) | 2026-09-24 |
| 17 | manua.ls — Electrolux ECU7677CSW portable UM | user_manual | https://www.manua.ls/electrolux/ecu7677csw/manual | Snippet: P1 = water tank full (portable; matches UK P1) | 2026-09-24 |
| 18 | Home Depot THD static PDF — unbranded troubleshooting | other | images.thdstatic.com catalog PDF | Downloaded for comparison → **quarantined** (no Electrolux badge) | 2026-09-24 |
| 19 | electrolux-ui.com — Midea OEM service manual SER_MSC(H) | service_manual | electrolux-ui.com DocumentDownLoad | Electrolux-named portal hosting Midea manual; fetch failed (000) — **not used**; lineage lead only | 2026-09-24 |
| 20 | AU support hub E0–F3 article | official_support | supporthub.electrolux.com.au | 404; snippet only | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Portable P1/L3/E4 meanings vs split P1/L3/E4 (IE table) | Separate portable series — **never merge** (P1 portable full tray ≠ split P1 meaning) |
| 2 | IE table vs Scribd mirror | Scribd = partial corroboration only; IE official wins |
| 3 | AU supporthub E0–F3 snippet vs IE table | AU URL 404; IE table remains source of record |
| 4 | AUX/Electrolux OEM claim (quickfixforce lore) | **Unconfirmed** — do not import AUX tables |
| 5 | PK Lux Air / Active Air have no codes | Empty gap series by design (not fabrication) |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|----------------|
| Frigidaire (Electrolux AB US) error tables | Different regional brand entity — not verified for PK/IE Electrolux splits |
| Unbranded THD troubleshooting PDF | No Electrolux badge — comparison only |
| Electrolux laundry / dishwasher / fridge "Electrolux" codes | Wrong appliance |
| Midea OEM PDFs on electrolux-ui.com (without body badge) | Lineage lead only — not imported as Electrolux |
| Airwell / Mitsubishi / other OEM search hits | Other brands |
| SEO farms / content-farm E-lists | Not Electrolux-primary |
| Shared-board videos (Kenwood/Electrolux/Dawlance/PEL Orient Changhong Ruba multi-brand) | Platform-shared — only Electrolux-badged or Electrolux IE/UK official imported |

## Exhaustiveness (Phase D)

- **Official Electrolux Ireland publishes a full 36-code AC error table** — primary source recovered.
- **No official Electrolux PK error-code table** (electroluxpakistan.com 403; no PK support article for Lux Air / Active Air T3).
- **No Electrolux-badged split service manual** (E/F/U/C + LED blink map) found in 8+ search batches — biggest gap.
- VEGA (US) and KSV (AU) user manuals: zero error codes (symptom-based only).
- manualbox EACS PDF unreachable (HTTP 000); AU supporthub E0–F3 URL 404.
- Stop rule met: last two search batches produced no new Electrolux-badged AC codes.
- Open gaps: PK Lux Air/Active Air fault tables; Electrolux-badged service manual; AUX OEM confirmation.

## Offline follow-up paths

- electroluxpakistan.com — request PK fault-code sheet / service bulletin (403 may be bot-block; try browser).
- support.electrolux.ie article — re-fetch full table for any codes beyond the 36.
- manualslib Electrolux AC pages (403) — retry via r.jina.ai or download for blink maps.
- manualbox EACS-24HG53-A PDF — retry fetch; if Electrolux-badged, add to split series.
- Electrolux-badged service manual search (EACS/ECS/Lux Air) — offline follow-up.
