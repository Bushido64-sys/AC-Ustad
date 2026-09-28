# Fronus PK (inverter) — sources

Brand id: `fronus` · Category: `inverter` · Researched: 2026-09-24
Official: https://www.fronus.com (WWW.FRONUS.COM in manual) · **FRONUS SOLAR ENERGY (WASIQ TRADERS)** — Pakistan
Support: UAN **042-111-111-140** · info@fronus.com

**Status: 145 codes across 7 series files — X1-Genki 106 (high) + X3 MIC 25 (medium) + PV hybrid 14 (medium); 4 empty/quarantine series (X3-Genki, Meta, Infineon-Reborn alias, Neo/Venus quarantine).**

**≠ Austrian Fronius (fronius.com).** This is PK Fronus / Wasiq Traders.

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `x1-genki.json` | X1-Genki-3K/3.7K/4K/4.6K/5K/6K off-grid hybrid | **106** (INSTALL 20 + PV 4 + BAT 6 + INV 46 + BMS 29 + COM 1) — high |
| `x3-mic.json` | X3 MIC / X3 MIC G2 hybrid | **25** named CamelCase faults (GridVoltFault, IsoFault, … FanWarning) — medium |
| `pv-hybrid.json` | PV-2200 1.5KW / PV-5200 4.2KW / Infineon Reborn | **14** (01/02/03/05–09, 51–53, 55, 57, 58) — medium (image TS table) |
| `x3-genki.json` | X3-Genki 10.5/15.5KW three-phase | 0 (107-page PDF — no Fault/Diagnosis bytes in text layer) |
| `meta.json` | Meta series (roadmap) | 0 (no Fronus-badged Meta table) |
| `infineon-reborn.json` | Infineon Reborn (roadmap alias) | 0 (codes live in `pv-hybrid.json` — PV-2200 manual is Infineon Reborn product) |
| `neo-venus.json` | Neo / Venus three-phase names | 0 (**QUARANTINE** — downloaded Neo manual is SolaX-branded, no Fronus badge) |

### Badge / cipher notes

- **genki6.pdf**: copyright page = "FRONUS SOLAR ENERGY (WASIQ TRADERS)" + WWW.FRONUS.COM; manual is "integral part of X1-Genki series" listing all six models → **high**.
- **x3mic.pdf**: text layer is **+31 printable-ASCII cipher**; decoded line 1 = `X3 MIC G2` + info@fronus.com + UAN 042 111 111 140 → Fronus-branded official manual; medium (single source + cipher-OCR risk).
- **pv5200.pdf / pv2200.pdf**: image-only PDFs (36/32 pages); troubleshooting table read via `pdftoppm -png -r 150` page 34 → Voltronic-class numeric subset; medium (image read, single source).
- **x3genki10.pdf**: bytes search for `Fault Code` / `Diagnosis` / `Grid Lost` all -1 in text layer → zero-code gap (possible image-only fault page).
- **solax_x3_neo.pdf**: SolaX Power service contacts, no Fronus string in fault section → quarantine (do not import SolaX named-fault list).

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Fronus X1-Genki series user manual (official PDF) | service_manual | file:/tmp/opencode/fronus/genki6.pdf | 106 fault codes across INSTALL/PV/BAT/INV/BMS/COM groups (high) | 2026-09-24 |
| 2 | Fronus X3 MIC G2 user manual (official PDF, +31 cipher decoded) | service_manual | file:/tmp/opencode/fronus/x3mic.pdf | 25 named CamelCode faults + FanWarning (medium) | 2026-09-24 |
| 3 | Fronus PV-5200 4.2KW hybrid product manual (image PDF) | user_manual | file:/tmp/opencode/fronus/pv5200.pdf | Troubleshooting table p34 (14 numeric codes, medium) | 2026-09-24 |
| 4 | Fronus Infineon Reborn PV-2200 1.5KW product manual (image PDF) | user_manual | file:/tmp/opencode/fronus/pv2200.pdf | Same table family as PV-5200 (product alias for Infineon Reborn) | 2026-09-24 |
| 5 | Fronus X3-Genki 10.5/15.5KW manual | user_manual | file:/tmp/opencode/fronus/x3genki10.pdf | ZERO — no fault table in text layer (negative) | 2026-09-24 |
| 6 | SolaX X3-Neo manual (downloaded during Neo search) | other — **EXCLUDE** | https://www.solaxpower.com | QUARANTINE — SolaX-branded, not Fronus; named-fault list not imported | 2026-09-24 |
| 7 | fronus.com site / product pages | official_support | https://www.fronus.com | Series lineup confirmation; no separate online fault-table page found | 2026-09-24 |
| 8 | Austrian Fronius (fronius.com) error lists | other — **EXCLUDE** | https://www.fronius.com | Different company — hard-excluded from PK Fronus brand | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | "Fronus" vs "Fronius" spelling collision | **Hard rule:** PK Fronus ≠ Austrian Fronius. Never import Fronius.com E-codes into `fronus` brand. |
| 2 | X3 MIC text layer is cipher-shifted (+31) | Decoded before parse; medium confidence notes cipher-OCR risk on every X3 MIC code. |
| 3 | Genki code `STARTUP_CONDITION_FAILL` / `BAT_TYPR_CFG_ERR` look like typos | Source-row typos **preserved** (do not "fix" field codes) — noted in per-code `notes`. |
| 4 | Genki `DCBUS_SW_UVP` row text says "overvoltage" for UVP code | Keep code as-is; meaning flags likely source typo (under-voltage expected by name). |
| 5 | Neo manual has a full named-fault list but is SolaX-branded | Quarantine — zero Neo codes; empty series with warning note. |
| 6 | Infineon Reborn is both a roadmap series name and the PV-2200 product line | Codes stored once in `pv-hybrid.json`; `infineon-reborn.json` is a zero-code alias to avoid double-counting. |
| 7 | PV-2200/5200 tables are troubleshooting subsets (no 04/10/11/12/56 etc.) | Series note: **absence ≠ no such code** — full Voltronic set not claimed for these models. |
| 8 | BMS_* codes (29) are battery-pack faults, not inverter-only | Kept under X1-Genki (manual documents them for PACE BMS link); `display: controller` (LCD shows them). |

## Exhaustiveness (Phase D)

- Queries: fronus.com manuals, X1-Genki / X3 MIC / X3 Genki / PV-2200 / PV-5200 / Meta / Neo / Venus / Infineon Reborn PDFs, +31 cipher decode of x3mic, pdftoppm visual read of PV manuals, SolaX Neo trap filtering, Austrian Fronius exclusion.
- Stop rule met: official X1-Genki full table + X3 MIC full table + PV TS subset extracted; remaining series zero published tables or quarantined.
- Highest-value unread: X3-Genki image-only fault page (if any); Meta series manual if released; full PV-2200 non-TS appendix pages.

## Offline follow-up paths

- UAN **042-111-111-140** / info@fronus.com — request X3-Genki fault table, Meta manual, full PV-5200 code list (not just troubleshooting subset).
- fronus.com contact / Wasiq Traders dealer network.
- In-box paper manuals from dealer units (X1-Genki, X3 MIC).
