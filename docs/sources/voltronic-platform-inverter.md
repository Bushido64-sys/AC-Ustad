# Voltronic Platform (OEM reference, inverter) — sources

Brand id: `voltronic-platform` · Category: `inverter` · Researched: 2026-09-24
Official: https://voltronicpower.com · Voltronic Power (Taiwan OEM) — **not a consumer brand; shared reference for local rebadges**
Support: via voltronicpower.com (no PK consumer line)

**Status: 84 codes across 10 series files (8 coded tables + 2 by-design empty pointers). Two canonical code spaces — Table A (Axpert/MKS) and Table B (InfiniSolar) — never merged. Table C = variant deltas in separate files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `table-a-axpert-vm.json` | Table A — Axpert VM / MKS numeric (VM III canonical) | 32 |
| `table-b-infini.json` | Table B — InfiniSolar hybrid + parallel | 38 |
| `table-c-vmii-premium.json` | Table C — Axpert VM II Premium (mirror) | 2 |
| `table-c-v-pf1.json` | Table C — Axpert V PF1 | 5 |
| `table-c-mks-1-5.json` | Table C — Axpert MKS 1–5KVA | 2 |
| `table-c-mks-ii.json` | Table C — Axpert MKS II 5KVA | 3 |
| `table-c-mks-service.json` | Table C — Older MKS service tables | 1 |
| `table-c-infini-wp.json` | Table C — InfiniSolar VII WP (mirror) | 1 |
| `table-c-b2p-parallel-note.json` | Table C — VII-2P split-phase parallel (revision note) | 0 (note-only) |
| `aliases-knox-tesla.json` | Local rebadge pointer (Knox/Tesla/others) | 0 (by design) |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Axpert VM III manual-20200904 (official) | user_manual | https://voltronicpower.com/content/download/Manual/Axpert%20VM%20III_manual-20200904.pdf | **Table A canonical**: faults 01–09/51–53/55/57–59 (**no fault 10 on VM III**) + W-warnings + blank-code rows (EQ/BAT_NC) + BMS 60/69/70/71/BMS_COMM | 2026-09-24 |
| 2 | Hybrid 2/3/5/6KW InfiniSolar VII manual-20210726 (official) | user_manual | https://voltronicpower.com/content/download/Manual/InfiniSolar%20VII%202KW-3KW-5KW-6KW%20manual-20210726.pdf | **Table B canonical**: faults 01–12/51–53/55–58 + W-warnings + parallel 60/71/72/73/80–86 | 2026-09-24 |
| 3 | InfiniSolar VII 2/3KW manual-20180124 (official) | user_manual | voltronicpower.com | Revision cross-check (parallel 73/86 scope) | 2026-09-24 |
| 4 | InfiniSolar VII-2P-6KW manual-20201207 (official) | user_manual | voltronicpower.com | 2P parallel set 60/71/72/80–85 (**no 73/86**) + BMS §6 — note-only series | 2026-09-24 |
| 5 | Axpert V PF1 manual (official) | user_manual | https://voltronicpower.com/content/download/Manual/Axpert%20V_PF1_manual.pdf | Adds fault 56 + W11–W14; drops W16/W32 vs VM III | 2026-09-24 |
| 6 | Axpert MKS / MKS-1-5KVA manual (official) | user_manual | http://voltronicpower.com/content/download/Manual/Axpert%20MKS-MKS-1-5KVA%20manual.pdf | Adds fault 56 + W11 (same events as V PF1, different source) | 2026-09-24 |
| 7 | Axpert MKS II 5KVA manual-20190703 (official) | user_manual | voltronicpower.com | Faults 10/11/12 with MKS II meanings (10=PV over current, 11=PV over voltage, 12=DC/DC over current) | 2026-09-24 |
| 8 | Axpert VM II Premium 3KW (motoma.cn mirror) | user_manual | https://motoma.cn/fr/wp-content/uploads/2023/08/Axpert-VM-II-Premium-3KW-User-Manual.pdf | Table C variant deltas (wording) → medium (mirror-hosted) | 2026-09-24 |
| 9 | InfiniSolar VII WP manual (220v.com.ua mirror) | user_manual | https://220v.com.ua/files/qpower/InfiniSolar%20VII%20WP%20manual.pdf | Warning W19 printed as **BP** on WP panel → medium | 2026-09-24 |
| 10 | Axpert VM III service manual (segensolar mirror) | service_manual | portal.segensolar.co.za | Service-perspective cross-check | 2026-09-24 |
| 11 | Axpert MKS 1~3KVA service manual 20130611 (pdfcoffee mirror) | service_manual | pdfcoffee.com | Older service faults 10 fan locked / 11 main relay / 12 AC over current / 13 AC over voltage → medium | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Table A vs Table B same digits (01–12, 51–58, W-family, 60/71) with different meanings | **Two canonical spaces, separate files — never merged** |
| 2 | Table C same-digit collisions (fault 11 MKS II vs MKS service; fault 56 V PF1 vs MKS 1–5; W11; W32; 10/12) | Split into **separate Table C series files** per source manual; in-file dedup suffixes `-2` only if needed |
| 3 | BMS 60/71 (Table A) vs parallel 60/71 (Table B) | Owned by different tables — brand rebadge files pick their table; notes on every platform ref row |
| 4 | Official VM III has **no fault 10** (roadmap correction) | Roadmap/notes corrected; fault 10 lives in MKS II / Infini / service tables only |
| 5 | Blank official code cells (equalization, battery not connected, BMS comm lost) | Stored as **symbolic ids** `EQ` / `BAT_NC` / `BMS_COMM` — do not treat as printed numbers (notes explain) |
| 6 | VII 73/86 present on 20210726, absent on 2P-6KW 20201207 | Revision-scoped: 2P parallel in note-only series; field showing 73/86 → use table-b-infini |
| 7 | energypower.gr copy | 403 — unused |
| 8 | Local PK rebadges (Tesla, Knox, Ziewnic, Inverex voltronic, Crown Elego, Fronus PV…) | Keep **their own brand files**; this entry is OEM lookup/reference only (`aliases-knox-tesla.json` empty by design). `platform_ref` binding not in schema yet — offline mapping backlog |

## Exhaustiveness (Phase D)

- All official voltronicpower.com manual PDFs covering VM/VM II/VM III/MKS/MKS II/V PF1/InfiniSolar VII (+ WP, 2P) extracted; mirrors used only where official links moved.
- Two service-manual mirrors read for older MKS deltas.
- Stop rule met: table C variants fully partitioned by source; remaining deltas logged as revision notes.
- Offline follow-up: energypower.gr (403) retry; any newer VM IV / InfiniSolar VIII manuals when published.

## Offline follow-up paths

- voltronicpower.com downloads — watch for VM IV / VIII manuals.
- Mirror retry: energypower.gr.
- Local rebadge in-box manuals confirm which table a specific SKU ships.
