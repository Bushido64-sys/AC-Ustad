# PEL — sources

Brand id: `pel` · Category: `ac` · Researched: 2026-09-23
Official site: https://pel.com.pk · Support UAN: 042-111-102-103 · WhatsApp: 0300-1102103
No public PEL AC service (technician) manual or official error table found (`site:pel.com.pk` surfaces transformer/product docs only).

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Electra Fix (Rawalpindi/Islamabad) — PEL Inverter AC Error Codes: E0, FF & Common Troubleshooting Tips | technician_note | https://electrafix.pk/pel-inverter-ac-error-codes | **Primary detailed table:** E0–E5 (alias 5E), E8 · F0–F9 · FA · P2–P8 with causes/solutions. Title highlights FF as common (meaning not defined in body). Comments: L6 asked, unanswered; staff reply says "PEL inverter EC error does not occur"; P4 reconfirmed in staff reply | 2026-09-23 |
| 2 | Holife — Understanding Pel Inverter AC Error Codes (2024-10-23) | technician_note | https://holife.net/understanding-pel-inverter-ac-error-codes-49 | **Second full table, same wording** for E0–E5/E8, F0–F9, FA, P2–P8 — two agreeing sources → confidence high for the 25-code core | 2026-09-23 |
| 3 | YouTube — PEL Inverter AC P1 error (14 Jul 2022) | video | https://www.youtube.com/watch?v=V7G_i029k7k | Occurrence of **P1** on PEL inverter (no published meaning) | 2026-09-23 |
| 4 | YouTube — PEL Dc inverter ac P1 P4 error code outdoor pcb (18 Jun 2025) | video | https://www.youtube.com/watch?v=pIZjdINrsv4 | Field evidence **P1 + P4** on PEL outdoor PCB (P4 already in table; P1 not) | 2026-09-23 |
| 5 | YouTube — PEL Inverter AC All Error Codes How to setting Solve Solution | video | https://www.youtube.com/watch?v=MSAx2zEej5U | Existence of a PEL "all codes" explainer (description only; no extra codes extracted) | 2026-09-23 |
| 6 | Fixya — "My pel inverter ac showing FF error after sometime" (18 Oct 2018) | forum | https://www.fixya.com/support/t27117722-pel_inverter_ac_showing_ff_error_after | Real user report of **FF** on a PEL inverter (JS-gated; title only) | 2026-09-23 |
| 7 | Babar Electronics — Dawlance PEL DC inverter AC P4 error code outdoor PCB (Facebook video) | video | https://www.facebook.com/Babarelectronicshvacr1/videos/dawlance-pel-dc-inverter-ac-p4-error-code-ac-pcb-repairing-by-babar-electronics-/1459551709240096 | Corroborates **P4** on PEL hardware (shared platform with Dawlance) | 2026-09-23 |
| 8 | manuals.plus — PEL Packaged Air Conditioner User Manual (FD series Inverter) | user_manual | https://manuals.plus/m/a0b47e4eee2182a5eca12f0a9e1f732f70d63e235165040a693259ec0484a275_optim.pdf | Confirms PEL packaged/FD inverter line exists (operation manual — **no error-code table extracted**) | 2026-09-23 |
| 9 | PEL official eshop product pages (series inventory) | official_support | https://eshop.pel.com.pk/ | Series names for brand.json: InverterOn Jumbo DC Prime Plus WiFi T3 (H&C), Full DC, T3 | 2026-09-23 |
| 10 | Wikipedia — PEL (company) | other | https://en.wikipedia.org/wiki/PEL_(company) | Company context: Lahore HQ, Saigol Group, LG/Panasonic distributor (not codes) | 2026-09-23 |
| 11 | PEL official site (support/contact + transformer docs) | official_support | https://pel.com.pk/ | UAN/WhatsApp/contact; **no AC error table on site** | 2026-09-23 |
| 12 | ManualsLib — PEL Air Conditioner brand page | other | https://www.manualslib.com/brand/pel/air-conditioner.html | Lists PEL AC user manuals (not opened individually; no code table claimed) | 2026-09-23 |
| 13 | YouTube PCB-training channel titles (Sir Babar / related) — Dawlance PEL Orient F1, E5, P4, E1 on outdoor PCB | video | (channel listing via search) | Occurrence evidence only for F1/E5/E4/P4 already covered | 2026-09-23 |

## Conflicts log (resolved in data notes)

| Code | Conflict | Resolution used |
|---|---|---|
| P1 | Field videos show P1 on PEL; neither Electra Fix nor Holife table includes P1 (tables start at P2) | Included with **confidence low**; notes say meaning unpublished for PEL — do not copy another brand's P1 meaning. relatedCodes: P2, F7, P4 |
| FF | Electra Fix title + Fixya user report show FF on PEL; no source defines the meaning | Included with **confidence low**; meaning = not published; app must badge "verify on your model" |
| EC | Commenter asked about EC on the PEL article; Electra Fix staff replied "Pel inverter EC error does not occur" | **EC omitted** from data (brand does not use it per staff on a PEL-specific page) |
| L6 | Commenter asked about L6 on the PEL article; no answer | **L6 omitted** — question alone does not prove the code exists on PEL |
| E5 vs 5E | Same fault, different display | aliases: ["E5","5E"] |
| P1/P4 shared-platform | Same codes appear on Dawlance/Orient PCB repair videos | Kept as PEL field evidence; no cross-brand meaning import beyond what's noted |

## Exhaustiveness check (Protocol Phase D)

- 2+ independent sources compared: ✅ Electra Fix + Holife (identical 25-code core) + 4 video/forum sources for P1/FF/P4.
- Codes covered: E0, E1, E2, E3, E4, E5 (5E), E8 · F0–F9 · FA · P1, P2, P3, P4, P5, P6, P7, P8 · FF = **27 entries** (single series file).
- Confidence: 25× high (two agreeing tables) · 2× low (P1, FF).
- Blocked/failed sources (noted): pel.com.pk has no public AC error table; Fixya page JS-gated (title only); YouTube "all codes" description empty of extra codes; packaged AC PDF not code-bearing (or not extracted).
- Known remaining gap: official PEL service/user-manual error chart has not been found publicly — if one appears (e.g. ManualsLib deep link or image PDF), re-run Phase C and promote P1/FF confidence + verify omission of EC/L6.
- Two successive searches (FF meaning for PEL; aysonline/bijlibazar/maqsoodandsons/fully4world PEL tables) returned no new PEL codes beyond the set above — protocol Phase D stop condition met.
