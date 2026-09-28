# Super General (AC) — sources

Brand id: `super-general` · Category: `ac` · Researched: 2026-09-24
Official brand: **Super General** (Super General Company LLC, Dubai UAE) · https://www.supergeneral.com
Official support: https://www.supergeneral.com/product-support — hotline/email only, **no published error table**, no LED blink maps
PK dealers: PakRef (Karachi, "official dealer") · Zahid Brothers · Gulf Electronics (Lahore) · Qasim Electronics · Yasir Electronics · Sohail Electronics · Opal Electronics
PK SKUs: SGS121i-PK / SGS181i-PK / SGS241i-PK · SGFS24i-PK floor · SGCA* cassette · SGA* window
HARD EXCLUSION: **Super General ≠ Super Asia** (PK coolers) ≠ O General/Fujitsu General ≠ General Cool Dubai dealer ≠ SGW washing machines

**Status: 33 codes across 5 series files.**

## Series / product map

| Series file | Scope | Codes |
|---|---|---|
| `super-general-uae-split-generic.json` | UAE split generic — AirChill troubleshooting guide | 11 |
| `super-general-accio-faq.json` | Accio marketplace FAQ (E1/E6/EC) | 3 |
| `super-general-forum-fixya.json` | Fixya forum user reports (F9, E0) | 2 |
| `super-general-fa-oem-table.json` | Persian OEM-shared 'Super general' table (Fadaktahvieh) | 16 |
| `super-general-pk-floor-standing.json` | PK/UAE floor-standing P10 (video) | 1 |

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Super General official site — Product Support | official_support | https://www.supergeneral.com/product-support | Confirmed support = phone/email only; no error table | 2026-09-24 |
| 2 | AirChill AC Maintenance — Super General error guide | technician_note | https://airchillac.com/super-general-ac-error-code/ | Split table 11 codes (E0–E10, EC, EL OC, Error) | 2026-09-24 |
| 3 | Accio marketplace FAQ 2026 | other | https://www.accio.com/plp/super-general-ac-error-code-list | E1, E6, EC (3 codes, secondary) | 2026-09-24 |
| 4 | Dubaifix.ae error article | technician_note | https://dubaifix.ae | UAE tech corroboration (if used) | 2026-09-24 |
| 5 | Fixya thread F9 | forum | https://www.fixya.com/support/t29902083-super_general | F9 indoor fan (model-varies note) | 2026-09-24 |
| 6 | Fixya E0/E9 user reports | forum | fixya.com Super General threads | E0 meaning untrusted (open) | 2026-09-24 |
| 7 | Fadaktahvieh Persian 'Super general' table | other | https://fadaktahvieh.ir (Persian table) | E1–E5, F1–F10, FA (16 codes, OEM-shared) | 2026-09-24 |
| 8 | Floor/console P10 video (Arabic title) | video | YouTube listing P10 مكيف دولاب جنرال سوبر | P10 existence + causes/fix (no full definition) | 2026-09-24 |
| 9 | Voltiats Super General codes | other | https://voltiats.com/super-general-air-conditioner-error-codes/ | Cross-check | 2026-09-24 |
| 10 | PakRef / Zahid Brothers / Gulf Electronics PK | retailer_page | pakref.com · estore.zahidbrothers.com · gulfelectronics.pk | PK SKU/recon; no error tables | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | **E1**: AirChill = comms vs Accio = comms vs Persian table = indoor coil sensor | Conflicts documented per-code + confidence tags; sources never merged into one meaning |
| 2 | **E3**: AirChill = comms (SGSi188) vs Persian = indoor coil sensor | Model-scoped notes; separate series files |
| 3 | **E4**: AirChill = indoor fan/anti-frost vs Persian = indoor fan | Partial agreement; notes |
| 4 | **E5**: AirChill = compressor overcurrent vs Persian = specialist/board | Conflicts logged |
| 5 | **F9**: Fixya = indoor fan (model-varies) vs Persian = compressor cover protection | Conflicts logged; low-medium |
| 6 | **E0**: AirChill = EEPROM/self-check vs Fixya = untrusted user report | AirChill wins for generic E0; Fixya noted |
| 7 | Persian table OEM-shared lineage | Medium confidence — may match other brands; brand-badged as 'Super general' section only |
| 8 | No LED blink maps for any SG source | All blinkPattern null — documented gap |

## Quarantine (excluded collisions / wrong product / other brand)

| Item | Why excluded |
|------|----------------|
| **Super Asia** (PK coolers/washers) | Entirely different company — hard exclusion |
| O General / Fujitsu General | Different brand |
| General Cool Electronics Trading LLC (generalcool.ae) | Dubai *dealer* selling Super General — not the brand |
| SGW washing machine codes | Wrong appliance (SGW = washer prefix) |
| Super General refrigerators/freezers/dishwashers/TVs/chillers | Out of AC scope |
| "Super Generel Asia" OLX typo listings | Junk |
| Other-brand tables on multi-brand pages | Never imported |
| Super General non-AC product manuals | Wrong category |

## Exhaustiveness (Phase D)

- **No official Super General error table** (product-support = hotline/email only).
- No brand-badged service manual/user manual with codes found.
- No LED blink maps for SG (all null).
- No PK-language code resources (AYs/JE/Maqsood/bijli negative for SG AC codes).
- Sources: UAE/tech aggregators + Persian OEM table + Fixya + one P10 video — 26 distinct codes is the source ceiling.
- Stop rule: successive batches yielded no new SG-badged codes.
- Open gaps: official SG service manual; PK retailer fault sheet; LED blink map.

## Offline follow-up paths

- supergeneral.com / Service_geco@gecouae.ae — request service manual with fault codes.
- PakRef / Zahid Brothers — ask for PK-market fault-code sheet.
- UAE dealers (General Cool etc.) — request in-box/service tables.
- Re-scan Persian Fadaktahvieh table for additional models/notes.
