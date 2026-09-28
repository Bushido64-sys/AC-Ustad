# Maestro (inverter) — sources

Brand id: `maestro` · Category: `inverter` · Researched: 2026-09-24
Official PK: https://www.abt.com.pk · Maestro Solar & UPS / Maestro Refined Power = Associated Business Technologies (Abt), Lahore (est. 1998)
Support: info@abt.com.pk · +92 42 35409229

**Status: 0 codes across 3 series files — zero-code documented gap (no Maestro-badged fault table anywhere online).**

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `solar-inverters.json` | Voltronic-named SKUs (VM / MKS 3KW / Infini V II / AXPERT) | 0 (gap — OEM hint only) |
| `ups.json` | Maestro Intex Smart UPS / Thunder (350VA–10KVA, since 1998) | 0 (gap) |
| `ongrid.json` | On-grid (Growatt carried) | 0 (gap — Growatt tables stay under future `growatt` brand) |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | abt.com.pk product pages + Wayback CDX | official_support | https://www.abt.com.pk | Product lines confirmed (solar inverters w/ Voltronic names, Intex/Thunder UPS, Growatt on-grid carry); **no Maestro-badged fault table** (full CDX negative) | 2026-09-24 |
| 2 | Wayback CDX full-domain sweep | other | web.archive.org | Zero archived PDFs with code tables | 2026-09-24 |
| 3 | ManualsLib / aggregator / YouTube sweeps | other | — | Zero Maestro code pages | 2026-09-24 |
| 4 | Voltronic Power OEM manuals (VM/MKS/Infini/Axpert) | user_manual | voltronicpower.com | **Quarantined as OEM** — product names on Abt site are hints only, no explicit "Maestro runs this table" badge on a Maestro doc | 2026-09-24 |
| 5 | Growatt manuals | service_manual | growatt | Stay under future `growatt` brand entry — not imported here | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Olimpia Splendid "Maestro" AC (Italy) | Name collision — hard-excluded |
| 2 | Pololu Maestro (robotics), FlexRadio Maestro, Elmo Maestro | Name collisions — hard-excluded |
| 3 | Site names VM/MKS/Infini/Axpert products | OEM **hints** logged in series notes — not imported (platform-hint rule) |
| 4 | Growatt carried as a brand on Abt site | Separate future brand entry — not Maestro's code space |

## Exhaustiveness (Phase D)

- Official site + full Wayback CDX + aggregators + YouTube: no Maestro-badged fault table exists online.
- Stop rule met: only OEM (Voltronic/Growatt) tables appear in trailing searches.
- Offline follow-up: info@abt.com.pk / +92 42 35409229 — request Intex/Thunder UPS manual + any solar inverter fault appendix.

## Offline follow-up paths

- info@abt.com.pk — request manuals for Intex Smart UPS / Thunder / solar SKUs.
- Lahore office visit with model plate photo.
- In-box paper manuals from dealers.
