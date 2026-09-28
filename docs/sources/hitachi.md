# Hitachi (AC) — sources

Brand id: `hitachi` · Category: `ac` · Researched: 2026-09-24
Official: https://www.hitachiaircon.com (no hitachiaircon.com/pk page; PK channel via distributors — airextrading.com.pk bot-challenged, recon)
PK retailers: qasimelectronics-class / aysonline / maqsoodandsons / gulfelectronics / bijlibazar / w11stop (recon)
Official published AC error table: **YES (no single consolidated PK-facing one)** — https://www.hitachiaircon.com/au/resources/commercial-air-conditioning/alarm-code-troubleshooting-1 (The Solvers 01/02/03/08/31/35) + HK fault-code-inverter/fixed-speed downloads + official RPI-FSN3 §8.4 (documentation.hitachiaircon.com) + airCloud Alarm Code app (login-gated)

**Status: 206 codes across 7 series files — largest AC brand in KB.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `hitachi-pk-residential-timer-blink.json` | Residential indoor timer/fault LED blink-count map | 7 |
| `hitachi-pk-airhome-ld301-outdoor-blink.json` | airHome LD301 outdoor-unit LED blink map (300 CH / 400 DJ / 500 GH) | 14 |
| `hitachi-pk-light-commercial-abnml.json` | Light-commercial ABNML alarm codes (10.2 ALARM CODES heat pump + cooling only) | 15 |
| `hitachi-pk-residential-rac-service.json` | Residential RAC service codes (Snowflake mirror of Hitachi RAC meanings) | 9 |
| `hitachi-pk-set-free-air365-wired-alarm.json` | SET FREE air365 Max / Max Pro wired-controller alarm codes | 65 |
| `hitachi-pk-primairy-light-commercial.json` | Primairy light-commercial error messages bulletin (GB distributor) | 35 |
| `hitachi-pk-utopia-ivx-prime-wired-alarm.json` | Utopia / IVX / Prime R32 & R410A wired-controller alarm codes (SMGB0136) | 61 |

Multiple code schemes (blink-count / LD301 blink / ABNML numeric / E-prefix / hex alarm) — same digits across product lines kept in separate series files, **never merged**.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Hitachi SERVICE MANUAL Utopia / IVX / Prime R32 & R410A (SMGB0136 rev.0, 10/2021) §10.2.2.1 | service_manual | ultimateair.co.uk PDF (SERVICE-MANUAL-UTOPIA-IVX-AND-PRIME-R32-AND-R410A.pdf) | 61 wired-controller alarm codes (01-bF/EE hex+numeric) | 2026-09-24 |
| 2 | HITACHI Primairy Error Messages fault-code bulletin (GB) | distributor_page | logicool-ac.com PDF (GB-Primairy-AlarmCodes.pdf) | 35 codes (E1-E101 + numeric + FE/ER); corroborated yorknow.com UMC install guide §7 (31) | 2026-09-24 |
| 3 | Hitachi set-free service-manual mirror — SET FREE air365 Max/Max Pro wired alarm table | service_manual | set-free service-manual mirror | 65 alarm codes (0a/0b/...EE) — manufacturer meanings | 2026-09-24 |
| 4 | Hitachi AC Error Code Guide §6.0 (Scribd copy of service-manual chapter) | service_manual | scribd.com/document/904263507/Error-Codes-Hitachi-1 | 7 residential timer/fault LED blink counts (no/1/2/3/4/5/9 blinks) | 2026-09-24 |
| 5 | Snowflake Aircon — Hitachi error code guide (mirrors RAC service meanings) | technician_note | snowflakeaircon.sg/error-code/hitachi | 9 residential RAC numeric codes with repair guidance (medium) | 2026-09-24 |
| 6 | Snowflake Aircon — airHome LD301 outdoor LED blink table | service_manual | snowflakeaircon.sg (airHome 300/400/500 manual mirror) | 14 LD301 outdoor blink states (ld301-off, 2-16 blinks) | 2026-09-24 |
| 7 | airconwarehouse troubleshooting blogspot — light-commercial 10.2 ALARM CODES | service_manual | errorcodeairconditioning.blogspot.com hitachi-error-codes | 15 ABNML + unit ID codes with outdoor PCB LED1-4 patterns | 2026-09-24 |
| 8 | Official Hitachi AU — alarm-code-troubleshooting-1 (The Solvers 01/02/03/08/31/35) | official_support | hitachiaircon.com/au/resources/commercial-air-conditioning/alarm-code-troubleshooting-1 | Official corroboration for commercial numeric alarms | 2026-09-24 |
| 9 | Official RPI-FSN3 §8.4 fault codes | official_support | documentation.hitachiaircon.com/glb/en/vrf/rpi-fsn3/download/RPL0000003854_JCH | Official VRF fault table reference (Incapsula-gated, partial) | 2026-09-24 |
| 10 | HK fault-code-inverter-speed-series / fault-code-fix-speed-series downloads | official_support | hitachiaircon.com HK downloads | Official regional fault-code downloads (corroboration) | 2026-09-24 |

Incapsula on hitachiaircon.com/documentation.hitachiaircon.com; yorknow + manualslib 403; airextrading.com.pk bot-challenge; airCloud Alarm Code content behind app login — all offline follow-ups documented.

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Same numeric digits across residential RAC / light-commercial / SET FREE / Utopia-IVX-Prime lines | **Never merged** — separate series files (5.3 resolved by series scoping) |
| 2 | Primairy bulletin vs UMC adapter mapping (both readings) | Both kept, flagged per-code in `notes` (5.3b) |
| 3 | Residential RAC numeric codes: model service manuals vs Hitachi Price List 2009 | Never merged (5.3c) |
| 4 | Timer blink vs LD301 outdoor blink (different LEDs) | Separate blink files (indoor timer vs outdoor LD301) |
| 5 | VRF RPI-FSN3 / SET FREE / Utopia lines vs PK residential | PK-relevant commercial kept; pure VRF module codes beyond official tables documented as gaps |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|--------------|
| Hitachi IT / storage / elevator / server / power-system codes | Wrong Hitachi company (not air) |
| Hitachi US/JP-only product schemes (DaikinONE-style non-AC) | Wrong product line |
| Generic multi-brand "E1 high-pressure" tables (FB) | Other-brand scheme; conflicts with official Hitachi |
| Other-brand service manuals surfaced in search (LG PDF, MHI tables, etc.) | Never imported |
| Non-AC appliance codes (any brand) | Wrong appliance |
| Altherma / hydronic / chiller water-side codes (if hit SM-TS3-like tables) | Out of PK air-side scope |

## Exhaustiveness (Phase D)

- Official Hitachi publishes error tables: **YES but no single consolidated PK-facing page** (AU Solvers, HK downloads, RPI-FSN3, airCloud app login-gated). No hitachiaircon.com/pk; no Urdu table.
- 7 series files: 7 timer blink + 14 LD301 outdoor blink + 15 ABNML commercial + 9 residential RAC + 65 SET FREE wired + 35 Primairy + 61 Utopia/IVX/Prime = **206 entries** (109 alphanumeric + 7 timer blink + 14 LD301 blink + rest wired-controller codes).
- Severity split: stop_pro 129 / check_restart 72 / danger 4 / info 1. Confidence: high 105 / medium 101 / low 0.
- Stop rule: successive batches yielded no new Hitachi-badged codes beyond the seven series.
- Open gaps: PK-local fault sheet, airCloud Alarm Code app content, full RPI-FSN3 VRF set (Incapsula), RAK/RAC PDF truncated portions, Protection-Codes PDF never retrieved.

## Offline follow-up paths

- airextrading.com.pk / MIA-class PK distributor — request PK fault sheet (bot-challenged offline).
- documentation.hitachiaircon.com — re-fetch RPI-FSN3 §8.4 and Protection-Codes PDF behind Incapsula.
- airCloud Alarm Code app — login, export alarm list.
- ManualsLib/yorknow 403 — retry via r.jina.ai or Scribd mirrors.
- PK YouTube (3 deleted videos) — re-search for RAC/service blink field corroboration.
