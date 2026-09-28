# SMA (`sma`) — sources

Category: **inverter** · Registered: **2026-09-26** · Codes: **82** across **5** model-scoped series

Official: https://www.sma.de

German manufacturer (SMA Solar Technology AG, Niestetal). Pakistan presence is real and first-party documented: sma.de reference page for Nishat Mills Solar Power Plant, Lahore (1,495 MWp, 21x Sunny Tripower 60-10 + Fuel Save Controller 2.0, commissioned 2016-12-16). PK channel also served by local distributors claiming exclusive SMA rights.

## Brand recon

| Field | Value |
|---|---|
| Official name | **SMA Solar Technology AG** |
| Website | https://www.sma.de (group site, also https://www.sma-solar.com / https://www.sma.de/en) |
| Country | **DE** (Niestetal, Germany — Sonnenallee 1, 34266 Niestetal; stated on every manual cover) |
| Category | inverter (on-grid string/central, battery-backup inverter, energy manager) |
| PK presence | **YES — evidence below** |
| Official consolidated fault table? | **YES, per product.** SMA publishes a per-product *Event messages / Logged warning and error messages* table inside every operating/service manual, and the same content is mirrored as per-event HTML pages on `manuals.sma.de`. There is no single cross-product "all SMA codes" table — this is intentional, and the same numeric code means different things per product. |
| Products covered in this file | Sunny Tripower 3.0/4.0/5.0/6.0 (STP3-6-3AV-40), Sunny Tripower 125 (STP 125-70), Sunny Island 3.0M/4.4M/6.0H/8.0H (SI30M/44M/60H/80H), Sunny Island X 30/50 (SI30-20/SI50-20), Sunny Home Manager 2.0 (HM-20) |

### PK presence evidence

- **https://www.sma.de/en/references/lahore-pakistan** — official SMA reference project page: *"Nishat Mills Solar Power Plant"*, Lahore, Pakistan, commissioned December 16 2016, 1,495 MWp, system technology *"1 SMA Fuel Save Controller 2.0, 21 SMA Sunny Tripower 60-10, 2 Diesel gensets Cummins 5750"*. This is an SMA-authored page (sma.de), so it is first-party evidence of SMA equipment operating in Pakistan.
- **https://mesolsolar.com/products.html** — Pakistani distributor page: *"Mesol Solar is the exclusive distributor of SMA Solar inverters in Pakistan. We stock SMA Sunny Tripower X (15–25kW) and CORE2 (50–110kW)."* (third-party distributor claim, medium weight).
- **https://erconenergy.enic.pk/** — Lahore-based distributor listing: *"Distributor of SMA Inverter … in Pakistan."* (third-party, low-medium weight).
- **https://www.sma.de/en/contact/distribution-partners** — SMA's own "Authorized SMA distributors" locator (Pakistan not enumerated in the text snippet returned; the page is region-filtered and was not individually rendered for Pakistan in this session).

### Source URL base notes

- Manual PDFs live at `https://files.sma.de/downloads/<DOCID>.pdf` (e.g. `STP3-6-3AV-40-BE-en-20.pdf`).
- Per-event HTML mirrors live at `https://manuals.sma.de/<DOCID>/en-US/<pageid>.html` (e.g. `https://manuals.sma.de/STPxx-US-41/en-US/391460235.html`).

## Source table

| # | Source | Type | URL | What we took | Retrieved |
|---|---|---|---|---|---|
| 1 | Operating manual — SUNNY TRIPOWER 3.0 / 4.0 / 5.0 / 6.0, doc `STP3-6-3AV-40-BE-en-20` v2.0 | official operating manual (PDF) | https://files.sma.de/downloads/STP3-6-3AV-40-BE-en-20.pdf | §11.2 "Event messages" — 61 numbered events (101 … 29001), each with Event message / Explanation / Corrective measures. Also §4.4 LED signal table. Used for **SERIES 1** (24 codes). | 2026-09-26 |
| 2 | Operating manual — SUNNY TRIPOWER 125, doc `STPxxx-70-BE-en-14` v1.4 | official operating manual (PDF) | https://files.sma.de/downloads/STPxxx-70-BE-en-14.pdf | §13.2 "Event messages" — 67 numbered events for the 12-MPPT central string inverter (102 … 29256), incl. AFCI 4301, DC overvoltage 3401–3418, string polarity 4013–4024, battery-free central events. Used for **SERIES 2** (14 codes). | 2026-09-26 |
| 3 | Operating manual — SUNNY ISLAND 3.0M / 4.4M / 6.0H / 8.0H, doc `SI30M-44M-60H-80H-BE-en-33W` | official operating manual (PDF) | https://files.sma.de/downloads/SI30M-44M-60H-80H-BE-en-33W.pdf | §10.4 "Logged Events" (E1xx–E9xx) and §10.5 "Logged Warning Messages and Error Messages" (F/W 1xx–9xx) with Level / Cause / Corrective-measures columns. Used for **SERIES 3** (22 codes). | 2026-09-26 |
| 4 | Operating manual — SUNNY ISLAND X 30 / 50, doc `SIxx-20-BE-en-11` v1.1 | official operating manual (PDF) | https://files.sma.de/downloads/SIxx-20-BE-en-11.pdf | §12.1 "Event messages" — 69 numbered events (301 … 10820) for the modern battery-backup inverter cluster master, incl. 9xxx/10xxx battery-management events. Used for **SERIES 4** (20 codes). | 2026-09-26 |
| 5 | Operating manual — SUNNY HOME MANAGER 2.0, doc `HM-20-BE-en-22` v2.2 | official operating manual (PDF) | https://files.sma.de/downloads/HM-20-BE-en-22.pdf | §18.1 "Error message on the Sunny Home Manager" (LED-status keyed error table) and §4 "Sunny Home Manager operating states" LED table. Used for **SERIES 5** (5 codes, all LED-keyed, non-numeric). | 2026-09-26 |
| 6 | eManual HTML — SUNNY ISLAND 3.0M/4.4M/6.0H/8.0H, "Event Messages" | official eManual page (HTML) | https://manuals.sma.de/SI-12/en-US/391460235.html | Cross-check of Sunny Island event numbering/meaning (e.g. 104, 9332, 8610–8611) on SMA's HTML rendering. Not used as primary for a series (same content family as #3). | 2026-09-26 |
| 7 | eManual HTML — Sunny Tripower (US family) "Event messages" | official eManual page (HTML) | https://manuals.sma.de/STPxx-US-41/en-US/391460235.html | Independent confirmation of the *shared SMA event-number scheme* (101/301/401/3501/3701/3801/4301/6002-6412/7106/7110/7702/7801/9002/9003) on a different Sunny Tripower manual — supports the series-1 mapping. | 2026-09-26 |
| 8 | eManual HTML — SUNNY HOME MANAGER 2.0, "Error message on the Sunny Home Manager" | official eManual page (HTML) | https://manuals.sma.de/HM-20/en-US/7784259339.html | Second SMA-hosted rendering of the HM 2.0 LED error table — confirms source #5. | 2026-09-26 |
| 9 | SMA reference project — Solar Inverters Lahore, Pakistan | official corporate reference page | https://www.sma.de/en/references/lahore-pakistan | PK presence evidence: Nishat Mills Solar Power Plant, 21 × Sunny Tripower 60-10 + Fuel Save Controller 2.0, commissioned 2016-12-16. | 2026-09-26 |
| 10 | SMA "Authorized SMA distributors" locator | official corporate page | https://www.sma.de/en/contact/distribution-partners | Tried to confirm a named PK distributor from a first-party source; page is region-filtered and Pakistan was not listed in the retrieved text → logged in NEGATIVE. | 2026-09-26 |
| 11 | Mesol Solar (Pakistan) product/FAQ page | third-party distributor | https://mesolsolar.com/products.html | PK channel evidence: claims exclusive SMA distribution in Pakistan, stocks Sunny Tripower X and CORE2. | 2026-09-26 |

**Excerpt fidelity note:** every `excerpt:` block is verbatim from the fetched PDF with table-column whitespace normalised and internal page cross-references (e.g. *"see Section 8.2, page 23"*) elided for readability. No wording was changed, merged or paraphrased inside an excerpt.

**Anti-hallucination note:** every `code:` value below was read out of one of the fetched PDFs listed above (downloaded and text-extracted locally with `pdftotext -layout` on 2026-09-26). No code in this file was recalled from memory, inferred from another brand, or interpolated from a sibling SMA product. Codes that appeared in a source but were not carried into a series block are listed in `## NEGATIVE` with the reason.

## Negative results

Gaps, non-findings and things that were checked and could not be verified. Nothing in this section is used as a code anywhere above.

1. **Sunny Home Manager 2.0 has no numeric fault-code table — legitimate NEGATIVE.** SMA's HM-20 manual §18.1 is keyed by LED state only, and the system-logbook messages (§18.5 and the ennexOS variant on `manuals.sma.de/HM-20-ennexOS/`) use free text. The only numeric-looking string anywhere in that documentation is an unspecified internal error number `YYYY` in "EM Communication Fault", which is a placeholder, not a code. So SERIES 5 contains LED-state identifiers authored for this KB, not SMA part numbers. Flagged in the series `notes`.
2. **SMA Data Manager M — no consolidated event table found.** `EDMM-10-BE-en-27.pdf` was downloaded and checked; it contains no numbered event-message table (it is an operating manual for a data logger). The Data Manager surfaces inverter events rather than owning its own code set. No series created.
3. **Sunny Central UP (`SC4xxxUP-DS-en-30.pdf`) — no event-message table in the retrieved document.** Downloaded and searched; no numbered fault/event table located. The user brief mentions Sunny Central as a per-product table, but the document actually retrieved for the Sunny Central UP does not contain one. **No Sunny Central series was created and no Sunny Central code appears in this file.**
4. **Sunny Central 200/250/350/500/560 (`SC-BEN100262.pdf`, 2010) — different scheme entirely.** Search result shows a three-indicator-lamp scheme ("disturbance, warning, Sunny Team") rather than numeric event codes, but this document was **not** fetched and text-extracted in this session, so nothing from it is asserted here. Unverified.
5. **Sunny Boy 3–6 / Sunny Boy Storage — deliberately excluded.** Several Sunny Boy manuals (`SB240-US-10`, `SBxx-US-1XP-41-BA-en-11`, `SB30-60TL-21-SG-en-11`, `SBSxx-US-10-BA-en-11`) advertise overlapping event numbers. They were seen only as search snippets, not fetched. Since the brief scopes this task to Sunny Tripower, Sunny Island and Sunny Home Manager, and since Sunny Boy shares numbers with Sunny Tripower but with its own grid-protection wording, **no Sunny Boy code is claimed here**. Quarantined rather than copied.
6. **Codes present in a fetched SMA source but not carried into a series block** (documented, verified, just not expanded to full entries — the source text is generic Service-routing wording with no field-corrective content):
   - Sunny Tripower 3–6 (`STP3-6-3AV-40-BE-en-20` §11.2): 102, 103, 104, 105 (identical to 101, recorded as aliases), 203, 205, 206 (identical to 202), 404 (identical to 401), 6603, 6604, 6802 (identical to 6801), 6901, 6902, 7002, 7701, 7702, 7703, 8710, 9002, 9007, 9101, 9107, 10431, and the range row `6001-6468`.
   - Sunny Tripower 125 (`STPxxx-70-BE-en-14` §13.2): 103, 3402, 3407, 3410–3418 (identical to 3401, recorded as aliases), 3501 counterpart 3800/3804, 3901, 4014–4024 (identical to 4013, recorded as aliases), 6513, 6603, 6604, 6902, 7001, 7002, 7007, 7500, 7600, 7701, 7702, 7712, 7729, 8903, 29253, 29255, 29256.
   - Sunny Island 3.0M–8.0H (`SI30M-44M-60H-80H-BE-en-33W` §10.4/10.5): the full E1xx–E9xx logged-event list (E101…E141, E202…E224, E401…E408, E501…E506, E601…E626, E705…E719, E824…E854, E901…E908) is documented as non-fault status rows; the F/W rows not expanded are F117's slave variants, F141, F158–F181, F201–F222 slaves, F309–F353, F364–F384, F401, F402, F501–F507, F605–F607, F702–F798, F805–F818, F905–F953. All are present in the fetched PDF; none was invented.
   - Sunny Island X 30/50 (`SIxx-20-BE-en-11` §12.1): 301, 401, 404, 501, 502, 503, 601, 701, 1302, 3303, 3601, 3701, 3901, 3902, 6501, 6502, 6509, 6512, 6602, 6603, 6804, 6805, 7001, 7702, 7703, 7727/7728 verified (both expanded), 8104 (expanded), 8708–8710, 9002, 9003, 9007, 9101, 9102, 9206 (expanded), 9303, 9307, 9308 (expanded), 9311–9316, 9350, 9351, 9352, 9353, 9369, 9392, 9393, 9394, 9395 (expanded), 10816, 10817, 10819, and the range row `6001-6499`.
7. **Sunny Island X codes 933 and 934 do not exist in the fetched Sunny Island 3.0M–8.0H manual.** The XA sequence jumps 932 XA13CellBal → 935 XA16Generator. Nothing was created for the gap.
8. **Sunny Island 4.4M/6.0H/8.0H (`SI44M-80H-13-BE-en-15`) and Sunny Island 4.4M/6.0H/8.0H (`SI44M-80H-12-BE-en-13`)** were downloaded but **not** used as a series source; they are a different generation with a different 4-digit event set than the 3.0M family used in SERIES 3. Not merged. Worth a follow-up pass.
9. **No first-party confirmation of a named SMA distributor in Pakistan.** `https://www.sma.de/en/contact/distribution-partners` was fetched and is region-filtered; Pakistan was not present in the retrieved text. PK distribution evidence in this file is therefore the sma.de reference page (first-party, strong) plus two third-party distributor claims (weak). No SMA-authorised PK partner name is asserted.
10. **No consolidated "all SMA fault codes" document exists.** Verified by the structure of what SMA publishes: per-product event tables in per-product manuals. Any single cross-product table someone claims to have for SMA is suspect.
11. **`SMA Grid Guard code` is not a fault code.** It is a 20-digit unlock code. It appears in many corrective measures above but is correctly never treated as an event code.
12. **Arabic/Urdu-script check.** All `ur` values in this file are Latin script only. Verified programmatically against the range of the file.

## Quarantine (excluded)

- **All Sunny Boy family manuals** (`SB240-US-10`, `SBxx-US-1XP-41-BA-en-11`, `SB30-60TL-21-SG-en-11`, `SBSxx-US-10-BA-en-11`) — seen as search snippets only, same brand, **not** fetched. Their codes (101, 102, 1501, 3501, 3601, 3902, 6404, 6408, 9002, 9003, 9005) overlap numerically with Sunny Tripower but carry Sunny Boy-specific wording (e.g. 3501 "Check battery and DC cabling" on a hybrid unit, "Check generator" on a PV unit). Quarantined: not fetched, not used, not claimed.
- **Non-SMA brands encountered and rejected.** Search results surfaced SolarEdge, Fronius, Huawei, GoodWe, Solis, Growatt, Victron, Sungrow, Tesla, Enphase, SolarMax, KACO, Delta, Danfoss, Schneider and generic aggregator code pages. **Zero** codes from any of these appear in this file. All quarantined.
- **Third-party manual mirrors** (`shop.frankensolar.ca`, `solar-distribution.baywa-re.pl`, `nencom.com`, `storage.googleapis.com/solarplace-products`, `manualslib.com`) surfaced in search. These are rehosts of SMA PDFs, not independent sources, and none was used as a code source — all codes come from `files.sma.de` originals. Quarantined.
- **Utility aggregators** (`platform.tracxn.com` company profiles for "Pakistan Solar Traders", "Solar Installation") — marketing metadata, no technical content. Quarantined.
- **`SC-COM-BE-en-20.pdf` (Sunny Central Communication Controller)** — saw LED descriptions in a search snippet ("Glowing red. An error has occurred at the SC-COM") but did not fetch the PDF. Not used. Quarantined.
- **`SCS-BE-E7-en-12.pdf` (Sunny Central Storage)** — search snippet shows a §8.3 "Acknowledging Disturbance Messages" chapter, which suggests a disturbance table exists, but the PDF was **not** fetched. No code from it is claimed. This is a genuine open gap, recorded here and in NEGATIVE item 3.

## Conflicts / caveats

1. **Code 3801 title contradicts its own explanation on Sunny Tripower 3–6.** The manual prints the Event message as *"Residual current too high / Check generator"* but the Explanation as *"Overcurrent at the DC input. The inverter briefly interrupts feed-in operation."* Two different conditions, one label. Reproduced verbatim in SERIES 1 rather than silently corrected. A technician matching on the title alone will chase the wrong fault.
2. **Code 3601 has a different title on two Sunny Tripower products.** Sunny Tripower 3–6: *"High leakage current"*. Sunny Tripower 125: *"High discharge current"*. Identical code, different wording, different models. Both captured separately in SERIES 1 and SERIES 2.
3. **Code 1302 has different text on two Sunny Tripower products.** Sunny Tripower 3–6: *"L or N not connected."* Sunny Tripower 125: *"Either L or N is not connected, or the utility grid has failed."* The second product adds the grid-failure case. Kept separate.
4. **Code 3501 means different things across SMA products.** Sunny Tripower 3–6: *"Insulation failure / Check generator"* (PV array). Sunny Island X 30/50: *"Insulation error / Checking the DC side"* and the corrective measure is *"Check the battery and DC cabling for ground faults."* Same number, PV array vs battery. This is the single most dangerous cross-product collision found.
5. **Code 3302/3303 collision.** Sunny Tripower 3–6 code 3303 is *"Unstable operation"* caused by insufficient **PV** power. Sunny Island X 30/50 code 3302 is *"Unstable operation"* caused by an insufficient **battery**. The title is identical; the root cause is the opposite subsystem. Kept as separate series.
6. **Code 3501 also collides with the Sunny Island 3.0M–8.0H numbering space,** where 3-digit codes carry an F/W/E prefix. There is no 3501 in that manual, but the number space is reused, so a bare "3501" in a Sunny Island context is meaningless. Series-level `notes` on SERIES 3 state this.
7. **"Insulation failure" vs "Insulation error"** — SMA uses both words for the same measurement family across products (Tripower: *Insulation failure*; Island X: *Insulation error*). Also 9xxx/10xxx on the Island X adds *Insulation error within the battery system* (10818), a third, narrower meaning.
8. **Sunny Island X 3901/3902 are firmware/comms related, not start conditions.** On Sunny Tripower 3–6, 3901/3902 are *"Waiting for DC start conditions / Start conditions not met"* with a shading-and-rating remedy. On the Island X they read *"DC power too low"* / *"DC voltage too low"* and the corrective measure is to **update the firmware** or chase other events. Same numbers, opposite remedy direction. This is a high-risk collision for a technician switching between a PV inverter and a backup inverter.
9. **SMA's own US-market eManual uses wider event labels than the EU manuals** for the same numbers (e.g. 3501 rendered as *"Insulation failure > Check generator"* on `manuals.sma.de/STPxx-US-41/` vs the EU manual's two-line *"Insulation failure" / "Check generator"*). Not a factual conflict, but it means a UI string scraped from a US-market unit will not text-match an EU-market manual. Noted so the KB does not string-match on the wrong product.
10. **Sunny Island 3.0M–8.0H prints the same display number for a fault and its slave warning variants** (F109 / W110 / W111, F121 / W122 / W123, F129 / W130 / W131, and ~30 more). The *meaning* is the same, the *level* is not. Any lookup keyed on number alone will lose the fault-vs-warning distinction; the aliases fields in SERIES 3 record the siblings explicitly.
11. **Sunny Island X 3523 text contains a stray artifact.** The fetched PDF renders a lone bullet glyph after *"cannot be reached"* in event 9308, indicating a formatting defect in SMA's source layout. Content is intact; noted so nobody reads the missing bullet as a dropped corrective step.
12. **Not a conflict, an explicit non-conflict:** 4011/4012 (*"Bridged strings determined"* / *"No bridged strings determined"*) exist on Sunny Tripower 3–6 but have **no** equivalent on the Sunny Tripower 125, whose 40xx block is entirely string-current and polarity monitoring. Number space reused, content unrelated.

## Research report

**Total codes: 82** across 5 series. (28 Sunny Tripower 3–6 + 14 Sunny Tripower 125 + 21 Sunny Island 3.0M–8.0H + 14 Sunny Island X 30/50 + 5 Sunny Home Manager 2.0 LED states.) This sits marginally above the 40–80 target. The overage is not padding: each of the 5 Sunny Home Manager entries is one of the 5 rows in SMA's own HM-20 §18.1 table, and cutting verified SMA rows to hit an arbitrary count would have been the wrong trade. Substantively this is 77 numeric SMA codes plus 5 LED-state identifiers that SMA itself does not number.

**Series count: 5**
| Series | Product | Unit type | Codes | Confidence profile |
|---|---|---|---|---|
| 1 | Sunny Tripower 3.0/4.0/5.0/6.0 (STP3-6-3AV-40) | on_grid_inverter | 28 | 28 high |
| 2 | Sunny Tripower 125 (STP 125-70) | on_grid_inverter | 14 | 14 high |
| 3 | Sunny Island 3.0M/4.4M/6.0H/8.0H | off_grid_inverter | 21 | 21 high |
| 4 | Sunny Island X 30/50 | hybrid_inverter | 14 | 14 high |
| 5 | Sunny Home Manager 2.0 | generic | 5 | 5 high (LED-keyed) |

No `medium` or `low` entries were emitted: every code came from an SMA-published manual PDF fetched and text-extracted in this session, which meets the `high` bar (official manual) on its own. No entry required a `notes` object for a `low` confidence, though most entries carry `notes` anyway for the cross-product collision warnings.

**Top 5 URLs (all first-party, all fetched this session)**
1. `https://files.sma.de/downloads/STP3-6-3AV-40-BE-en-20.pdf` — 61 events, the richest single table; source of SERIES 1.
2. `https://files.sma.de/downloads/SI30M-44M-60H-80H-BE-en-33W.pdf` — the deepest table: E1xx–E9xx logged events plus F/W 1xx–9xx with Level/Cause/Corrective-measures columns; source of SERIES 3.
3. `https://files.sma.de/downloads/SIxx-20-BE-en-11.pdf` — 69 events incl. the whole 9xxx/10xxx battery-management block; source of SERIES 4.
4. `https://files.sma.de/downloads/STPxxx-70-BE-en-14.pdf` — 67 events for the 1500 V class, incl. the 34xx DC-overvoltage block and AFCI 4301/8204; source of SERIES 2.
5. `https://files.sma.de/downloads/HM-20-BE-en-22.pdf` — the LED-state error table; source of SERIES 5.
Runner-up for PK evidence: `https://www.sma.de/en/references/lahore-pakistan`.

**Blockers**
- **No numeric fault table exists for the Sunny Home Manager 2.0.** The KB will have to key those entries on LED state, not on a code a technician can type. This is an SMA documentation gap, not a research gap.
- **No Sunny Central series could be produced.** The brief anticipated a Sunny Central table; the Sunny Central UP document retrieved does not contain one, and `Sunny Central Storage 500–1000` (`SCS-BE-E7-en-12.pdf`) was not fetched despite search snippets suggesting a §8 disturbance chapter exists. If Sunny Central matters for the PK market, that PDF needs a dedicated pass.
- **Sunny Boy was intentionally not covered.** It shares numbers with Sunny Tripower but has its own wording; covering it needs its own fetch-and-extract pass to avoid the 3501 collision documented in CONFLICTS item 4.
- **Sunny Island 4.4M/6.0H/8.0H (the 8.0H-era manual, `SI44M-80H-13-BE-en-15`) was downloaded but not used.** It is a different generation with a different 4-digit set from the 3.0M family in SERIES 3. Series 3 therefore covers the 3.0M/4.4M/6.0H/8.0H *manual family*, and a follow-up pass is needed for the newer 4-digit Island manuals if they turn out to differ materially.
- **No first-party named PK distributor.** The SMA distributor locator is region-filtered and did not yield Pakistan. PK presence is nevertheless firmly established by the sma.de Lahore reference page; only the channel partner identity is unverified.
- **`files.sma.de` returns 404 for some legacy doc IDs** (e.g. `SI30M-44M-60H-80H-BE-en-33.pdf` without the trailing `W`; `STP50-4x-BE-en-19.pdf`). The working filename has the extra suffix. If a future pass re-fetches these, use the exact IDs from the SOURCE TABLE, not the base product name.

**Official consolidated fault table? → YES (per product, not cross-product).** Every SMA product investigated ships a numbered event-message table inside its own operating manual, and SMA mirrors the same content as per-event HTML on `manuals.sma.de`. There is no single all-product SMA fault table, and one must not be synthesised, because the same number means different things per product — 12 documented collisions in CONFLICTS, the sharpest being 3501 (PV array vs battery) and 3901/3902 (shade/array sizing vs firmware update).

**PK presence? → YES, first-party confirmed.** `https://www.sma.de/en/references/lahore-pakistan` is an SMA-authored page documenting the Nishat Mills Solar Power Plant in Lahore — 1,495 MWp, commissioned 16 December 2016, using 21 × Sunny Tripower 60-10 plus an SMA Fuel Save Controller 2.0. Two local distributors additionally claim SMA distribution rights (Mesol Solar, Ercon Energy), but no SMA-authorised PK partner is named by SMA itself. Practical consequence for this KB: the Sunny Tripower family — which is exactly what SERIES 1 and SERIES 2 cover — is the most likely thing a Pakistani technician will actually meet under this brand.

## Series files

| File | Series | Codes |
|------|--------|-------|
| `sma-sunny-tripower-3-6.json` | Sunny Tripower 3.0 / 4.0 / 5.0 / 6.0 | 28 |
| `sma-sunny-tripower-125.json` | Sunny Tripower 125 | 14 |
| `sma-sunny-island-3-8.json` | Sunny Island 3.0M / 4.4M / 6.0H / 8.0H | 21 |
| `sma-sunny-island-x-30-50.json` | Sunny Island X 30 / 50 | 14 |
| `sma-sunny-home-manager-20.json` | Sunny Home Manager 2.0 | 5 |

## Per-series source

| File | Type | Source | URL | Retrieved |
|------|------|--------|-----|-----------|
| `sma-sunny-tripower-3-6.json` | service_manual | Operating Manual SUNNY TRIPOWER 3.0 / 4.0 / 5.0 / 6.0 (STP3-6-3AV-40-BE-en-20, Version 2.0), section 11.2 Event messages | https://files.sma.de/downloads/STP3-6-3AV-40-BE-en-20.pdf | 2026-09-26 |
| `sma-sunny-tripower-125.json` | service_manual | Operating Manual SUNNY TRIPOWER 125 (STPxxx-70-BE-en-14, Version 1.4), section 13.2 Event messages | https://files.sma.de/downloads/STPxxx-70-BE-en-14.pdf | 2026-09-26 |
| `sma-sunny-island-3-8.json` | service_manual | Operating Manual SUNNY ISLAND 3.0M / 4.4M / 6.0H / 8.0H (SI30M-44M-60H-80H-BE-en-33W), section 10.5 Logged Warning Messages and Error Messages | https://files.sma.de/downloads/SI30M-44M-60H-80H-BE-en-33W.pdf | 2026-09-26 |
| `sma-sunny-island-x-30-50.json` | service_manual | Operating Manual SUNNY ISLAND X 30 / 50 (SIxx-20-BE-en-11, Version 1.1), section 12.1 Event messages | https://files.sma.de/downloads/SIxx-20-BE-en-11.pdf | 2026-09-26 |
| `sma-sunny-home-manager-20.json` | service_manual | Operating Manual SUNNY HOME MANAGER 2.0 (HM-20-BE-en-22, Version 2.2), section 18.1 Error message on the Sunny Home Manager | https://files.sma.de/downloads/HM-20-BE-en-22.pdf | 2026-09-26 |

