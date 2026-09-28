# Rays — sources

Brand id: `rays` · Category: `ac` · Researched: 2026-09-23
Official site: https://rays.com.pk · Service: 0340 11119 25

**Status: ZERO-CODE documented gap — no published Rays AC fault code exists in any accessible source.**

## Verified model lines

- Roadmap: **EAS / SEA T3 inverter** — Wayback confirms SEA marketing pages on rays.com.pk; **EAS-1820** sold by Al-Fatah, Electro Gallery, Shophive (no manual, no codes).
- Al-Fatah product title hints at a **EuroAire OEM** platform — EuroAire domains dead; no EuroAire AC fault table searchable.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Rays official site (rays.com.pk) — products / manuals | official_support | https://rays.com.pk | **No AC product manuals or fault tables** (negative evidence) | 2026-09-23 |
| 2 | Wayback — rays.com.pk snapshots | other | web.archive.org (rays.com.pk) | **SEA marketing pages only, no PDFs** archived (negative evidence) | 2026-09-23 |
| 3 | Retailer listings — EAS-1820 (Al-Fatah / Electro Gallery / Shophive) | retailer_page | Al-Fatah / Electro Gallery / Shophive product pages | SKU existence; **no manual or codes**; Al-Fatah title hints EuroAire OEM | 2026-09-23 |
| 4 | EuroAire domains + EuroAire error-code searches | other | (dead domains) | **No searchable EuroAire AC fault table** | 2026-09-23 |
| 5 | Multi-brand PK error sites (AYS, Fully4world, Japan Electronics, ElectraFix, acerrorcode), YouTube, Scribd, `"Rays" AC error code Pakistan`, `"EAS-1820" manual PDF` | other | various | **No Rays page/entry/video/manual found** (negative evidence) | 2026-09-23 |

Excluded: foreign Rays/RAY brands, Mitsubishi/Gree/Haier/LG/Daikin hits from generic error-code queries.

## Conflicts log (resolved in data notes)

| Topic | Conflict | Resolution used |
|---|---|---|
| OEM identity | Al-Fatah title hints EuroAire; EuroAire dead | **Hint only** — no EuroAire table found; cannot import any OEM brand's codes onto Rays. codes[] stays empty |
| SEA vs EAS | Roadmap says SEA T3; retail shows EAS-1820 | Both retained as verified lines (marketing SEA, retail EAS) — not a code conflict |
| Any third-party "Rays codes" | Generic query results return other-brand tables | **Never imported** — brand-specific evidence required |

## Exhaustiveness check (Protocol Phase D)

- Exhaustive sweep: official site, Wayback PDF/AC filter, Al-Fatah/Electro Gallery/Shophive EAS-1820 pages, EuroAire domains + error queries, `"EAS-1820" manual PDF`, `"Rays" AC error code Pakistan`, multi-brand PK docs sites, YouTube, Scribd, acerrorcode A–Z.
- Final round returned only other-brand docs (Mitsubishi, Gree, Haier, LG, Sharp, Daikin, AC Pro) — **Phase D stop condition met; 0 new Rays codes**.
- Codes covered: **0 entries** (`codes: []` by design — validator allows empty array; do not invent placeholder codes).
- Known remaining gap: paper manual inside retail box; Rays service center 0340 11119 25; if EuroAire OEM is confirmed later **and** a EuroAire/Rays-badged manual appears, re-run Phases B–D before importing any platform table (Enviro-style "unverified until manual" gate).
- Field path until then: UAN/service number above; no self-diagnosis published.
