# Homage (inverter) — sources

Brand id: `homage` · Category: `inverter` · Researched: 2026-09-24
Official: https://homage.pk · Support: **021-111-764-111** · Vertex manual: 021-171-766-1711
Corporate: R&I Electrical Appliances (Pvt) Ltd (also Kenwood PK) — **no confirmed OEM platform** for inverters.

**Status: 29 codes (legacy 25 + vertex 4) across 5 series files; 3 empty gap series (Bolt, Apex/HAS, HQS).**
No official Homage-hosted fault table. AC sibling log: `docs/sources/homage.md`.

## Series / product map

| Series file | Models | Codes |
|---|---|---|
| `legacy-ups.json` | Tron Duo/Uno, Hexa, Octa, Innova, Axiom, Neon | 25 (bare Fault/E/F + 2 LED patterns) |
| `vertex-lcd.json` | HVS-12/24/30/5014SCC, HVP-5015SCC | 4 (zero-padded 00/01/02/08) |
| `bolt-hbs.json` | HBS-xx16/17SCC | 0 (gap) |
| `apex-hybrid.json` | HAS-32/40/60/80xxSCC (Apex) | 0 (gap) |
| `hqs-ongrid.json` | HQS-10/15/20/2518SCC (Quantum) | 0 (gap) |

Legacy bare `Fault N` vs Vertex `Fault 0N` = different UIs — **do not merge** without a manual.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Homage official inverter hub / collections / product pages | official_support | https://homage.pk/pages/inverter | Series lineup (Bolt/Vertex/Apex/HQS/Tron…); **no manual downloads** (negative) | 2026-09-24 |
| 2 | Maaz Electronics beep/LED guide | technician_note | https://maazelectronics.pk/your-inverter-has-been-telling-you-whats-wrong-all-along-you-just-didnt-know-the-language/ | Fault 4=fan; LED-3BEEP overload; LED-ALARM overtemp | 2026-09-24 |
| 3 | HowPk — Fix Fault 4 | technician_note | https://howpk.com/how-to-fix-fault-4-error-in-inverters-without-repairing/ | Fault 4 fan + terminals (medium) | 2026-09-24 |
| 4 | Muaaz Ahmad Tron Duo review + comments | forum | https://muaazahmad.com/homage-inverter-tron-duo-review/ | Fault 5 meaning (author); existence 0/11/12/13/15/16 | 2026-09-24 |
| 5 | FB group snippet Fault 8 = over charged | forum | facebook.com groups (SERP) | Fault 8 meaning | 2026-09-24 |
| 6 | FB irfan.aazad.2025 Fault 7 | technician_note | facebook.com (SERP) | Fault 7 existence/vague meaning | 2026-09-24 |
| 7 | YouTube Hexa 2004 Fault 3 · Axiom 1002 Fault 4 · Neon Fault 5 · Vertex 1214 Fault 01/02/08 · Innova F1/F02 · Octa E09/A56 · Fault 0 | video | youtube.com / youlibs | Existence + title-level claims (low) | 2026-09-24 |
| 8 | FB UPS Repairing Shop | forum | facebook.com/ups.repairing | Existence 0/3/6/A56/E09 | 2026-09-24 |
| 9 | Scribd HVS-1214 / HVS-5014 / TronDuo manuals | user_manual | scribd.com/docs 641789922, 869006123, 674197827 | Manuals exist; **fault tables blocked** — open gap | 2026-09-24 |
| 10 | DibPak HOMAGE-Product.pdf / ARY Sahulat | distributor_page / retailer_page | dibpak.com · arysahulatbazar.pk | Model confirmation only | 2026-09-24 |
| 11 | SEO-spam farm (luxurygoods*, chatgptzen, website-files PDFs, img1.wsimg, accio.ai, downloadfalas weebly) | other — **REJECT** | various | Keyword stuffing only (E21/P3/E35/Fault01-per-manual/F9/E61) — **never cited** | 2026-09-24 |
| 12 | Voltronic PV1800 service manual (platform cross-check only) | service_manual | scribd.com/document/539203153 | Not cited as Homage | 2026-09-24 |
| 13 | Wayback CDX + official brochure PDFs | official_support | web.archive.org | Product catalogs only — **zero fault tables**; some PDFs xref-corrupt | 2026-09-24 |
| 14 | AYS / ManualsLib / manuals.plus / archive.org sweeps | other | — | **Zero** Homage inverter code pages | 2026-09-24 |

## Conflicts log

| ID | Conflict | Resolution |
|---|----------|------------|
| 1 | Fault 4 = fan (Maaz) vs fan+terminals (HowPk) | Fan lead + terminals secondary → medium |
| 2 | Fault 01 = fan lock claimed only by SEO-spam "per manual" | Spam quarantined → confidence **low**, meaning unverified |
| 3 | Fault 5 battery-high only from Tron Duo author reply | medium, model-scoped notes |
| 4 | Fault 8 overcharged (FB) vs battery&charging (Vertex videos) | Compatible family → medium on legacy, low on Vertex (title-level) |
| 5 | Bolt art `AxpertMaxII-…` | Rebrand **hint only** — Voltronic table NOT imported |
| 6 | HAS/HQS possibly Deye (weak ENF) | Deye F-codes NOT imported; empty series |
| 7 | F07 existence vs Deye F-07 DC/DC softstart | Homage entry = existence only + trap note |
| 8 | Fault 0 vs Fault 00 (legacy vs Vertex) | Separate series files |
| 9 | Name traps: HOMAG CNC, Hoymiles HAS, Homage e-bike | Hard-excluded |
| 10 | R&I/Kenwood AC F1 temptation | Different product class — not imported |

## Exhaustiveness (Phase D)

- ~45+ query batches (EN+Urdu, site:, filetype:, YouTube, Scribd, Facebook, Wayback CDX).
- Stop rule met: final batches (HAS/HQS codes, F-code variants, archive.org manuals) → no new Homage-badged codes.
- Highest-value unread sources: Scribd HVS-1214 (doc 641789922), HVS-5014 (869006123), TronDuo (674197827); Google Drive HVS-1214 PDF + Hybrid Manual zip (PAKFONES bit.ly/2GwwcUS) — need human/browser login.
- Offline paths: helpline 021-111-764-111; Vertex 021-171-766-1711; in-box paper manuals; dealer service sheets.

## Untrusted (do not promote without new evidence)

luxurygoodsinsider/trends, chatgptzen, cdn.prod.website-files.com Homage PDFs, img1.wsimg.com, accio.ai, downloadfalas weebly, mnc/lkw/qai/jsj/tlm subdomains — recycled generic China-UPS text.
