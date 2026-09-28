# Kenwood — sources

Brand id: `kenwood` · Category: `ac` · Researched: 2026-09-23
Official site: https://www.kenwoodpakistan.pk
No public Kenwood AC service (technician) manual or official error-code table found.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Electra Fix — Kenwood Inverter AC Error Code List: E, F, P Series, 6, 19, 36 | technician_note | https://electrafix.pk/kenwood-inverter-ac-error-code-list | **Primary + only source for numeric codes:** full E/F/P tables + deep sections on **6** (voltage protection), **19** (outdoor PCB drive error), **36** (serial indoor–outdoor comms). Reset + general tips | 2026-09-23 |
| 2 | Japan Electronics (Rwp/Islamabad) — Error Code List of Kenwood AC in 2024 (8 Jul 2024) | retailer_page | https://japanelectronics.com.pk/blogs/all/error-code-list-of-kenwood-ac | Second full table — **identical wording** to #1 for E0/E1/E3–E5/EC/EE · F0–F6 · P0/P1/P3/P4/P6/P7. Does **not** list E2 (gap, not conflict) | 2026-09-23 |
| 3 | AYS Online — Kenwood Inverter AC Error Codes List Pakistan | distributor_page | https://www.aysonline.pk/docs/kenwood-air-conditioner-error-codes-pakistan | Third full table — same set **plus E2** (indoor pipe sensor) with cause/solution columns. Reset procedure. Cloudflare sometimes gates the page | 2026-09-23 |
| 4 | Fully4world — Troubleshoot E4 error in Kenwood (24 Oct 2023) | technician_note | https://fully4world.com/troubleshoot-e4-error-in-kenwood/ | Technician field note: **E4 = blower fan motor** (mouse-bite wiring) — conflicts with tables' return-air sensor; kept in E4 causes/notes | 2026-09-23 |
| 5 | Dailymotion — KENWOOD DC Inverter AC Error 20 Complete Explained (17 Apr 2025) | video | https://www.dailymotion.com/video/x9i1rs4 | Occurrence of numeric **Error 20** on Kenwood DC inverter (meaning not in written sources) | 2026-09-23 |
| 6 | YouTube — Kenwood Inverter AC All Error Codes List \| E1, E2, E3, F1 … (1 Feb 2026) | video | https://www.youtube.com/watch?v=Kl84UrSzT2c | Corroboration of common set (E1/E2/E3/F1 in title); description empty of extra codes | 2026-09-23 |
| 7 | AYS PDF — Kenwood inverter ac how to use (error-series overview) | other | https://assets-global.website-files.com/66f3dd622f3a5a287584dacb/67b9814c598c19f29b932e2e_nikujazosatukirusodi.pdf | Confirms E/F/P series grouping language for Kenwood (no unique codes) | 2026-09-23 |
| 8 | Kenwood Pakistan official product pages (series inventory) | official_support | https://www.kenwoodpakistan.pk/pages/air-conditioner · https://www.aysonline.pk/brand/kenwood | Series names: splits, floor standing, cassette; e/eSMART/Glory/Luxury Pro/Ultra/Superia models — **no error table** | 2026-09-23 |
| 9 | Kenwood official site / estore | official_support | https://estore.kenwoodpakistan.com/products/air-conditioners/ | Product catalog for brand.json notes only | 2026-09-23 |

## Conflicts log (resolved in data notes)

| Code | Conflict | Resolution used |
|---|---|---|
| E2 | JE table omits E2; EF + AYS include it (indoor pipe sensor) | **Included** (2 of 3 tables). JE omission = table gap, not a contradiction. Confidence high |
| E4 | Tables (EF/JE/AYS) = return-air temperature sensor vs Fully4world = blower fan motor (wire bite) | **Primary = tables** (return-air sensor). Fully4world kept in causes + notes as model/PCB alternate. Confidence high with notes |
| E5 | All three tables = indoor coil sensor. EF narrative section “Error Code E5” wrongly describes indoor–outdoor communication (duplicate of E1) | **Primary = tables** (indoor coil). Notes record EF body error so future sessions do not “fix” E5 to comms |
| F6 vs E5 | Both listed as indoor coil sensor class | Both kept (firmware display map may use either). relatedCodes cross-linked |
| EC | Some brands use EC = compressor overload; Kenwood tables = low refrigerant | **Kenwood tables win** (low gas). Notes warn not to import other brands’ EC |
| P2 / P5 / P8 / P9 / F7–F9 / E6–E8 | Not present in any Kenwood source searched | **Omitted** (not missing data — no evidence). AUX/OEM tables exist for similar platforms but are not Kenwood-badged |
| Error 20 | Video title only | Included confidence **low**, meaning unpublished, verify badge |

## Exhaustiveness check (Protocol Phase D)

- 2+ independent sources compared: ✅ Electra Fix + Japan Electronics + AYS (three agreeing core tables) + Fully4world/Dailymotion/YouTube for edge codes.
- Codes covered: E0, E1, E2, E3, E4, E5, EC, EE · F0–F6 · P0, P1, P3, P4, P6, P7 · 6, 19, 36, 20 = **25 entries** (single shared-platform series file; no FS-specific codes found).
- Confidence: 21 high (3-source core: E×8 + F×7 + P×6) · 3 medium (6, 19, 36 — Electra Fix deep sections only) · 1 low (20 — video title, meaning unpublished) = 25.
- Blocked/failed: kenwoodpakistan.pk has no public error table; fully4world article is behind a DEMO gate (snippet only); AYS page sometimes Cloudflare-gated (full text recovered on re-fetch); no AC service manual PDF (JVCKenwood global manuals are unrelated car-audio/appliance).
- Known remaining gap: Kenwood **user/service manual** fault chart not found publicly — if located (image PDF → pdftoppm technique), re-run Phase C and upgrade 6/19/36/20 confidence; verify whether P2/P5/F7+ exist on any line.
- Two successive searches (numeric 20 meaning; E6/E7/E8/F7/F8/P2/P5/P8/P9; floor-standing/eImperial lists) returned **no new Kenwood codes** beyond the set above — protocol Phase D stop condition met.
