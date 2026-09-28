# Crown Micro (inverter) — sources

Brand id: `crown-micro` · Category: `inverter` · Researched: 2026-09-24
Official (global): https://crownmicroglobal.com · Legacy PK: crown-micro.com/pk-en
Support: legacy portal **0423-5788144** / info@crownmicro.com.pk

**Status: 40 codes across 6 series files — Xavier 26 (18 faults + 7 W + W16) + Elego 14; 4 empty/gap series (Nero II, Nexus-quarantine, UPS, other).**

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `xavier.json` | Xavier hybrid/off-grid 1.2–5.6KVA + 3/3.6/4/5.6KW | 26 (01–10, 12, 51–53, 55, 57–59 + W01/W02/W03/W04/W07/W10/W15/W16) |
| `elego.json` | Elego hybrid IP21/IP65/IP66 6–15KW, Oyster, ELO-VI | 14 (01/02/05/07 + 60/71/72/80–86) |
| `nero-ii.json` | Nero II | 0 (gap — only 0-byte catalog placeholder filename) |
| `nexus.json` | Nexus (**QUARANTINE — not Crown Micro**) | 0 (different company: Crown Group automotive, Karachi) |
| `ups.json` | UPS CMUX/CMUS/CMUO offline/online | 0 (brochures only) |
| `other.json` | Arceus / Nova / Voltmore / Relevo / VMGI / CM12KTL / Ricardo | 0 (lines exist; tables not extracted — open gaps) |

Xavier/Elego = Voltronic-class tables from **official Crown PDFs**. Field "F09"/"F51" = same as "09"/"51". Elego parallel numbers align with Voltronic twin event list.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Crown Micro Xavier 3KW User Manual (official PDF) | service_manual | https://crownmicroglobal.com/wp-content/uploads/2024/01/Xavier-3KW-User-Manual.pdf | Xavier base fault table 01–07 + W-set (high, official) | 2026-09-24 |
| 2 | Crown Micro Xavier 3.6/5.6KW manual (official PDF) | service_manual | https://crownmicroglobal.com/wp-content/uploads/2022/12/Crown-micro-Xavier-3.6KW5.6KW.pdf | Fault 10 (PV OC) + overload 105% variant (high) | 2026-09-24 |
| 3 | Crown Micro Xavier 4KW manual (official PDF) | service_manual | https://crownmicroglobal.com/wp-content/uploads/2022/12/Crown-Micro-XAVIER-4KW.pdf | Internal group 08/09/51–59 rows (high) | 2026-09-24 |
| 4 | Crown Micro Elego 6KW IP65 manual (ManualsLib mirror p34/p49) | user_manual | https://www.manualslib.com/manual/3071325 | Elego base 01/02/05/07 + parallel 60/71/72/80–86 (high) | 2026-09-24 |
| 5 | Xavier 3KVA troubleshooting (ManualsLib p30, TS variant) | user_manual | https://www.manualslib.com/manual/3064131 | Fault 12 temp sensor (model-scoped, high) | 2026-09-24 |
| 6 | SolarNevs — Crown inverter error codes guide | distributor_page | https://solarnevs.com/pk/guides/fixes/crown-inverter-error-codes | W16 (AC input >280V during BUS soft start) — **low**, not confirmed in official PDF excerpt; DC-offset claim for 55 loses to official "unbalanced" | 2026-09-24 |
| 7 | crownmicroglobal.com / crown-micro.com product pages + catalogs | official_support | crownmicroglobal.com | Series lineup confirmation; Nero II only 0-byte placeholder filename | 2026-09-24 |
| 8 | crownsolarenergy.com "Nexus" | other — **EXCLUDE** | crownsolarenergy.com | Crown Group (automotive) Karachi — separate company; zero Crown Micro-badged evidence | 2026-09-24 |
| 9 | Elego 10–15KW / Oyster full manuals | user_manual | manualslib (403 on fetch) | Partial — open gap | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Model drift across Xavier wattages: fault 10 only on 3.6/4/5.6KW; fault 12 only on 3KVA TS; overload 110% (3KVA) vs 105% (3.6KW) | Per-code notes record model scope; rows kept with scoped meanings |
| 2 | SolarNevs 55 = "DC offset" vs official Crown 55 = "output unbalanced" | **Official Crown wording wins** (high); SolarNevs claim logged and dropped |
| 3 | W16 only on SolarNevs, not in official PDF excerpt | Confidence **low** + verify notes |
| 4 | Field codes written "F09"/"F51" in forums vs manual "09"/"51" | Same code — aliases noted in series; not double-counted |
| 5 | "Nexus" search results → crownsolarenergy.com | Different company (Crown Group automotive) — quarantined, empty series with warning note |
| 6 | Nero II in printed catalog with 0-byte PDF placeholder | Zero evidence — empty gap series |
| 7 | Elego parallel 60/71/72/80–86 vs Voltronic twin list | Exact event-list alignment + Crown-badged manual → high (badge present) |

## Exhaustiveness (Phase D)

- Queries: crownmicroglobal.com manuals (Xavier/Elego/Nero/Nova/Arceus), "Crown Micro fault code", Crown CMUX/CMUS UPS codes, crown-micro.com Wayback, ManualsLib, Scribd, SolarNevs, YouTube, crown solar energy trap filtering.
- Stop rule met: official Xavier + Elego PDFs extracted; Nero II/UPS/other lines have zero published tables.
- Highest-value unread: Elego 10–15KW/Oyster full tables (ManualsLib 403); Nero II if ever released; official UPS legacy instructions.

## Offline follow-up paths

- Legacy PK portal **0423-5788144** / info@crownmicro.com.pk — request Elego 10–15KW fault tables, Nero II manual, UPS instructions.
- crownmicroglobal.com contact form.
- In-box paper manuals from dealer units.
