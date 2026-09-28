# Panasonic (AC) — sources

Brand id: `panasonic` · Category: `ac` · Researched: 2026-09-24
Official: https://www.panasonic.com (direct fetch 403 — read via r.jina.ai) · MEA PK portal has no code article
PK channel: **PEL distributes Panasonic AC in PK** (roadmap note) · retailers aysonline · japanelectronics · maqsoodandsons · gulfelectronics · bijlibazar · w11stop (recon)
Official published AC error table: **YES (not PK-localized)** — https://www.panasonic.com/ph/air-solutions/learn-more/panasonic-air-conditioner-error-codes-using-the-self-diagnosis-function.html (mirrors: `/vn/en/`, `/kh/hvac/`, `/my/`)

**Status: 75 codes across 2 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `panasonic-hf-self-diagnosis.json` | Modern H/F self-diagnosis codes (all current residential splits, PK XKF/YE/PC/C class) | 55 |
| `panasonic-legacy-inverter-es.json` | Legacy inverter S/E codes (S01-S07 sensors, E01-E13 drive/protection) | 20 |

Modern H/F vs legacy S/E **never merged** — digits collide with quarantined wired-remote E-family (E01-E09 different meanings).

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Official Panasonic — self-diagnosis error-code page | official_support | https://www.panasonic.com/ph/air-solutions/learn-more/panasonic-air-conditioner-error-codes-using-the-self-diagnosis-function.html | H/F code names (source of truth); mirrors /vn/en/, /kh/hvac/, /my/ | 2026-09-24 |
| 2 | Panasonic CS-E7JKEW service manual (PH-AAM0810051C2) §16.3/§16.4 | service_manual | https://www.panasonicproclub.com/uploads/CZ/catalogues/rac/service-manual/CS-E7JKEW_SM_PHAAM0810051C2.pdf0on.pdf | Per-code causes / check locations for modern H/F | 2026-09-24 |
| 3 | Legacy Panasonic Error Code PDF (dealer CDN, ncpdn) | other | https://cdn1.npcdn.net/userfiles/27434/download/.../Panasonic_Error_Code-01.pdf | Legacy S01-S07 + E01-E13 (medium) | 2026-09-24 |
| 4 | Panasonic NA operating manual RE9SKUA/RE12SKUA (Nov 2025) | user_manual | https://iaq.na.panasonic.com/hubfs/.../Operating-Manual_RE9SKUA_RE12SKUA...pdf | Second official corroboration for H/F names | 2026-09-24 |
| 5 | Panasonic ftp service manual CS-E9NKUAW 9k-12k | service_manual | https://ftp.panasonic.com/heatairconditioner/servicemanual/cs-e9nkuaw_9k-12k_sm.pdf | Service-manual corroboration | 2026-09-24 |
| 6 | PEL / PK retailer pages | retailer_page | pkl-class · aysonline · maqsoodandsons · gulfelectronics · bijlibazar · w11stop | PK SKU confirmation (PEL distributes); no code tables | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Modern H/F vs legacy inverter E-family digit collision (E01-E09) | **Separate series files — never merged**; wired-remote E-family quarantined entirely |
| 2 | H51, H58 source conflicts (official page vs service manual wording) | Documented per-code in `notes` |
| 3 | 16 official-name-only codes without published causes | `confidence: medium` + inference flagged in `notes` |
| 4 | No PK-model service manual | MY/US/NA manuals sharing the same scheme used; noted |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|--------------|
| Wired-remote / central-control E-family (E01-E09 different meanings) | Collides with legacy inverter digits — quarantined, not merged |
| PACi / commercial P-codes | Out of PK residential scope (unless PK-relevant) |
| Panasonic TV / mobile / fridge / washer codes | Wrong appliance |
| Other-brand tables on multi-brand pages | Never imported |
| Non-AC Panasonic products (semiconductor, battery, etc.) | Out of scope |

## Exhaustiveness (Phase D)

- Official Panasonic publishes error table: **YES (self-diagnosis page, regional mirrors)** — no PK-localized version (panasonic.com 403 direct; MEA PK portal no code article; PEL/retailers publish none).
- Modern H/F (55): official page + CS-E7JKEW service manual + NA manual — high where dual-official, medium for name-only.
- Legacy S/E (20): single legacy compile — medium.
- Stop rule: successive batches yielded no new Panasonic-badged codes beyond the two series.
- Open gaps: PK-model service manual, PK-local official page, full ECOi/VRF commercial set if needed later.

## Offline follow-up paths

- PEL (PK distributor) — request PK fault-code sheet / service manual.
- panasonic.com via r.jina.ai — re-fetch self-diagnosis page for updates.
- Panasonic proclub / ftp.panasonic.com — full service manuals (XKF/YE PK models).
- ManualsLib retry via r.jina.ai if 403.
