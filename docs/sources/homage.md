# Homage — sources

Brand id: `homage` · Category: `ac` · Researched: 2026-09-23
Official site: https://homage.pk · Support: 021-111-764-111, info@rni.com.pk, 23 service centers (homage.pk/pages/service-center)

**Status: documented gap — no official Homage AC error-code table exists; 30+ searches found exactly 1 low-confidence community code (F1).**

## Verified model lines (homage.pk)

- Inverter: Element (HES-*), eSmart/Smart Crystal (HCS/HSC-*), Homage Classic, Smart Cool, Prestige (HPS-*)
- Non-inverter: Grande
- No floor-standing / cassette line found (wall splits only). "HHC" line unverified — do not list.
- Corporate: Homage is a brand of R&I Electrical Appliances (Pvt.) Ltd — same company distributing Kenwood in Pakistan. One forum user reported a Kenwood AC with "exactly same appearance" as a Homage model → shared platform **plausible but unconfirmed**.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Homage official — Air Conditioner / Inverter collection / product pages | official_support | https://homage.pk/pages/air-conditioner | Model line inventory only — **no error table, no downloadable AC manual** | 2026-09-23 |
| 2 | Homage official — FAQ / Safety / Contact / Service Centers | official_support | https://homage.pk/pages/faq | Support channels — no fault chart | 2026-09-23 |
| 3 | AYS Online — Homage AC error codes | retailer_page | https://www.aysonline.pk/docs/homage-air-conditioner-error-codes/ | **404 — page does not exist** (logged as negative evidence) | 2026-09-23 |
| 4 | iPhonewired thread on Kenwood F1 video — user comments | forum | https://iphonewired.com/common-problems/997965/ | **Sole Homage AC code found:** user reports Homage F1; another reply contests meaning (see conflicts) | 2026-09-23 |
| 5 | QAI SEO junk page — "Homage inverter fault codes" | other | https://qai.luxurygoodsinsider.com/pndk/homage-inverter-fault-codes/ | Mentions E4/E5/"5E" with **no meanings** — unusable spam, not cited in data | 2026-09-23 |
| 6 | Scribd multi-brand lists / YouTube / forums / Reddit / Pakwheels / Urdu queries | other | multiple | Exhaustiveness sweep — **no Homage AC list found anywhere** | 2026-09-23 |
| 7 | Homage UPS/inverter manuals (Fault 01, Fault 4, E21, 12/13…) | other | scribd.com, howpk.com, muaazahmad.com | **Out of scope** — UPS fault codes, different product category; deliberately excluded from AC data | 2026-09-23 |

## Conflicts log (resolved in data notes)

| Code | Conflict | Resolution used |
|---|---|---|
| F1 (Homage) | Comment A: outdoor ambient temperature sensor. Reply B: communication error. | **Neither confirmed.** Comment A favored only because Kenwood (same R&I company) maps F1→ambient sensor, and every brand's communication code is E1-class — but kept **confidence low**, meaning labeled "reported", notes demand model verification |
| F1 cross-brand | F1 meaning diverges by brand: Kenwood/Carrier/Reconnect = ambient sensor; Orient = PCB/module; Haier = IPM; Midea-family = outdoor PCB/IPM | **No analogy import allowed** — Homage F1 must be verified on the exact model; recorded so future sessions do not "upgrade" confidence via Kenwood data |
| E4 / E5 / 5E (Homage) | Only the SEO-spam page names them for Homage, with no definitions | **Omitted from data** (spam source, no meaning). Logged here so they are not re-added without a real source |
| Kenwood platform transfer | R&I makes both brands → Kenwood's 25-code table could transfer to Homage | **NOT imported.** Shared platform unconfirmed; importing would fabricate Homage data. If a Homage manual later proves the platform, re-run Phase C |

## Exhaustiveness check (Protocol Phase D)

- ~30+ successive searches (English/Urdu, site:homage.pk, site:fully4world, site:electrafix, site:scribd, site:aysonline, model codes HES/HCS/HSC/HPS, YouTube, manual-PDF guesses) returned no new Homage AC codes after the single F1 comment — **Phase D stop condition met**.
- Codes covered: **1 entry** (F1, low). Official table: none exists. Retailer page: 404. Service manual: none located.
- Known remaining gap: if anyone obtains a Homage AC service/user manual with a fault chart (image PDF → pdftoppm technique), re-run Phase C; verify whether the unit shares Kenwood's platform (would unlock the full E/F/P set) and re-check E4/E5 from the spam page against the real chart.
- Note for future sessions: Homage **UPS** fault-code pages rank high in search — ignore them for `category: ac`.
