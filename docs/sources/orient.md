# Orient — sources

Brand id: `orient` · Category: `ac` · Researched: 2026-09-23
Official site: https://orient.com.pk · Support UAN: 0800-ORIENT / 0800-11635
No public Orient service (technician) manual PDF found.

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Orient official blog "Some Basic Air Conditioner Error Codes and What they Mean?" (BlueEast, 17 Jun 2020) — live URL now redirects to homepage; **full table recovered via Internet Archive** | official_support | https://web.archive.org/web/20250123161111/https://orient.com.pk/blogs/updates/some-basic-air-conditioner-error-codes-and-what-they-mean (original: https://orient.com.pk/blogs/updates/some-basic-air-conditioner-error-codes-and-what-they-mean) | **Official:** E0, E1, E2, E3, E4, E5, E8, Eb · F0, F1, F2, F3, F4, F6, F8, F9 (16 codes). Also general causes list + reset advice (board memory erase, not just on/off) | 2026-09-23 |
| 2 | AYS Online — Orient Inverter AC Error Code List | distributor_page | https://www.aysonline.pk/docs/orient-air-conditioner-error-codes | E0–E5, E8, Eb match official wording; **F-series diverges** (F0 low gas, F1 indoor PCB comms, F2 indoor coil, F3 outdoor ambient, F4 outdoor pipe); F5 discharge sensor; F6/F8/F9 grouped overload/overcurrent/high-temp; FA compressor current; FC 4-way valve; P0 module under-voltage; P1 indoor–outdoor PCB comms; P9 display↔main board | 2026-09-23 |
| 3 | Electra Fix (Rawalpindi/Islamabad) — Orient Inverter AC Error Code List | technician_note | https://electrafix.pk/orient-inverter-ac-error-code-list-f1-e-common-troubleshooting-tips | E0–Eb (E6=compressor overload, E7=outdoor fan — unique); **F0–F9 match official wording**; F5 discharge sensor; F7 outdoor coil sensor (conflicts mbsmpro voltage) | 2026-09-23 |
| 4 | mbsmpro / pro.mbsm.pro — ORIENT Inverter AC Error Codes (E1–L3 technical guide, Jan 2026) | technician_note | https://mbsmpro.com/orient-inverter-ac-error-codes/ · https://www.pro.mbsm.pro/orient-inverter-ac-error-codes · PDF: https://mbsmpro.com/?mbsm_pdf=955 | **Largest extended map:** E6 sliding door (cabinet), E9 humidity, EA zero-crossing, Eb=EEPROM; F5 compressor top cover, F7 over/under voltage, FA suction sensor, Fb indoor DC motor (FS), FC 4-way, Fd outdoor zero-crossing; P2–P9; L0–L3 | 2026-09-23 |
| 5 | Scribd "AERVAI1221FA Error Code" (snippet only) — multi-brand/OEM-style list | forum | https://www.scribd.com/document/898236125/AERVAI1221FA-Error-Code | Snippet: FA suction temperature sensor · Fb indoor DC motor (Floor Standing) · FC four-way valve · Fd outdoor fan zero-crossing — corroborates #4 FA/Fb/FC/Fd | 2026-09-23 |
| 6 | BijliBazar — Orient AC Error Code List 2026 | retailer_page | https://www.bijlibazar.com/orient-error-code-list-2026 | Page present; search snippet only (partial rows: E3 evaporator, gas/filter/voltage themes) — no unique codes extracted beyond above | 2026-09-23 |
| 7 | PakWheels forum thread "orient inverter error code ec" | forum | https://www.pakwheels.com/forums/t/orient-inverter-error-code-ec/250786 | Real PK user report of **EC** on Orient inverter (meaning not stated in thread title; multi-brand convention = compressor-class protection) | 2026-09-23 |
| 8 | YouTube — "Orient Dc Inverter AC E3 Error Trace Fault And Repair" | video | https://www.youtube.com/watch?v=FqAZAD-X4Ss | Occurrence evidence: E3 (and F1 mentioned in related titles) on Orient DC inverter hardware | 2026-09-23 |
| 9 | bulao.pk — E8 Error on Orient Inverter AC | technician_note | https://bulao.pk/how-can-we-resolve-the-e8-error-on-your-orient-inverter-ac | PK service content corroborating E8 display↔PCB class on Orient | 2026-09-23 |
| 10 | Facebook technician group post "Orient Inverter AC Error Codes" | forum | https://www.facebook.com/groups/508463944810088/posts/1194988402824302 | Occurrence/post only (full table not scrapable) | 2026-09-23 |
| 11 | AUX service manual PDF (OEM partner of Orient — **not Orient-badged**) | service_manual | https://www.ac-maintenance-dubai.com/wp-content/uploads/2019/05/Aux-AC-Manual-PDF.pdf (HTTP 403 to bots on re-fetch) | OEM context only: AUX tables are known to rhyme with Orient's E/F set; used as corroboration note, **not** as primary Orient source | 2026-09-23 |
| 12 | fully4world Orient error page (from earlier research pass) | technician_note | https://fully4world.com/orient-air-conditioner-error-codes/ (now 404) | Earlier pass reported E6/E9/EA/F5/F7/Fb/P2–P9/L0–L3 themes — now superseded by #4 (mbsmpro) which restates the same extended set | 2026-09-23 |
| 13 | Orient official product collections (series inventory) | official_support | https://orient.com.pk/collections/all-air-conditioners | Series list for brand.json (Ultron eComfort DC Inverter, DC Inverter, Floor Standing, …) | 2026-09-23 |

## Conflicts log (resolved in data notes)

| Code | Conflict | Resolution used |
|---|---|---|
| E6 | mbsmpro = sliding/door fault (cabinet) vs Electra Fix = compressor overload | Primary = per-file: floor-standing.json = sliding door (mbsmpro); inverter-split.json = compressor overload (Electra Fix) + cross-notes. Confidence medium |
| Eb | Official = "Indoor Unit PCB Error" vs mbsmpro = indoor EEPROM | Primary = official (PCB error, broader); notes carry EEPROM reading. Confidence high |
| F0 | Official/Electra Fix = outdoor fan motor vs AYS = low refrigerant level | Primary = official outdoor fan; notes carry AYS gas reading. Confidence high |
| F1 | Official/Electra Fix = outdoor module protection vs AYS = indoor PCB communication | Primary = official outdoor module; notes carry AYS. Confidence high |
| F2 | Official/Electra Fix = PFC protection vs AYS = indoor coil sensor | Primary = official PFC; notes carry AYS. Confidence high |
| F3 | Official/Electra Fix = compressor start/out-of-step vs AYS = outdoor ambient sensor | Primary = official compressor start; notes carry AYS. Confidence high |
| F4 | Official/Electra Fix = outdoor discharge sensor vs AYS = outdoor pipe sensor | Primary = official discharge sensor; notes carry AYS. Confidence high |
| F5 | AYS/Electra Fix = discharge temperature sensor vs mbsmpro = compressor top-cover protection | Primary = discharge sensor (2 sources); notes carry top-cover. Confidence medium |
| F6 | Official = outdoor room sensor vs AYS = grouped overload/overcurrent/high-temp (F6/F8/F9) | Primary = official outdoor room/ambient sensor; notes carry AYS grouping. Confidence high |
| F7 | Electra Fix = outdoor coil sensor vs mbsmpro = over/under voltage protection (no official F7) | No official F7; two mutually exclusive single sources — primary = over/under voltage (mbsmpro detail + aligns with P2 voltage class); notes carry Electra Fix. Confidence low |
| F8 | Official = outdoor PCB↔module communication vs AYS = grouped overload | Primary = official; notes carry AYS. Confidence high |
| F9 | Official = outdoor PCB error vs mbsmpro = outdoor EEPROM vs AYS = grouped high-temp | Primary = official outdoor PCB; notes carry others. Confidence high |
| FA | AYS = compressor current error vs mbsmpro+scribd = suction temperature sensor | Primary = suction temperature sensor (2 agreeing sources); notes carry AYS compressor-current. Confidence medium |
| P0 vs L0 | AYS P0 = module under-voltage vs mbsmpro L0 = module under-voltage | Both kept as published on their sources (platform/series difference); cross-relatedCodes. Confidence medium each |
| P1 vs E5 | AYS P1 = indoor–outdoor PCB communication vs official E5 = same class | Both kept (firmware/display-series difference possible); relatedCodes E5. Confidence medium |
| EC | PakWheels shows EC on Orient; no Orient-published meaning | Occurrence = Orient-specific forum; meaning = multi-brand compressor-class convention — confidence low, notes say verify |

## Exhaustiveness check (Protocol Phase D)

- 2+ independent sources compared: ✅ official (Wayback) + AYS + Electra Fix + mbsmpro + Scribd snippet + forum/video.
- Codes covered: E0, E1, E2, E3, E4, E5, E6, E7, E8, E9, EA, Eb, EC · F0–F9, FA, Fb, FC, Fd · P0–P9 · L0–L3 · LE01 = **43 series-file entries** (42 unique codes; E6 appears in both split + floor-standing files per Dawlance cross-context pattern; Fb lives only in floor-standing.json).
- Official core = 16 codes (E0–E5/E8/Eb, F0–F4/F6/F8/F9) — all confidence high.
- Blocked/failed sources (noted): live official blog URL (redirects — used Wayback), AUX PDF 403 on re-fetch, fully4world 404, BijliBazar full text not scrapable (snippet only), Facebook group post not scrapable.
- Known remaining gap: Orient-branded **service manual** with full table has not been found publicly — if one appears, re-run Phase C and bump F5/F7/FA/P-series confidence.
- Two successive searches (E6/E9/EA/Fb floor-standing; L0–L3/P-series; maqsoodandsons/bijlibazar Orient) returned no new Orient codes beyond the set above — protocol Phase D stop condition met.
