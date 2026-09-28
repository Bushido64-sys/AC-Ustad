# Research Protocol — "No code left behind"

Follow this checklist for **every brand**, one brand at a time. Goal: collect **every**
error/fault code the brand can display in Pakistan, with bilingual meaning/causes/solutions
and a traceable source.

## Phase A — Brand recon (before collecting codes)

- [ ] Confirm official brand spelling, PK website, and product categories (AC / inverter types).
- [ ] List **all series/model lines sold in Pakistan** (from official site, PK retailers:
      priceoye, daraz, aysonline, japanelectronics, gulfelectronics, w11stop, pakref…).
- [ ] Note which lines are **inverter** vs conventional, heat-cool vs cool-only,
      split / floor standing / cassette / ducted / solar-hybrid.
- [ ] Create `docs/sources/<brand>.md` and log every URL as you go.

## Phase B — Source collection (search until nothing new appears)

Collect, in priority order:

1. **Official support / error-code pages** (HTML tables, interactive tools).
2. **Service/technical manual PDFs** — the goldmine: full error tables, LED blink maps,
   troubleshooting flowcharts. Search: `"<brand> <series> service manual pdf"`,
   `"<brand> AC error codes"`, `"<brand> fault code table"`.
3. **User manuals** — shorter code tables (good cross-check).
4. **Pakistani distributor/dealer pages & blogs** (these often document PK-specific
   models): e.g. `aysonline.pk/docs/...`, `maqsoodandsons.com`, brand's `.com.pk` blog.
5. **YouTube service/repair videos** (Pakistani technicians showing codes live) —
   titles like `"<brand> AC E4 error solution"`.
6. **Forums / Facebook technician groups / OLX service posts** — cross-check odd codes.
7. For inverters: **official firmware/fault manuals**, `invertererrorcodes.com`,
   vendor PDFs, Pakistani solar-dealer pages (w11stop, kamalsolar, alladin…).

Stop when 2+ successive searches across these channels produce **no new codes**.

### Source log template (`docs/sources/<brand>.md`)
```markdown
# <Brand> — sources
| # | Source | Type | URL | What we took | Retrieved |
|---|--------|------|-----|--------------|-----------|
| 1 | Dawlance inverter AC codes (aysonline) | distributor_page | https://... | E1–E6, F0–F9 | 2026-09-23 |
```

## Phase C — Extraction (per code)

For each code found anywhere:

- [ ] Record `code` **exactly as displayed** (+ every variant into `aliases`).
- [ ] Decide series file (see SCHEMA.md grouping rule).
- [ ] Write `title`/`meaning`/`causes`/`solutions` in **English + Roman Urdu**.
      **Paraphrase** — never copy manual paragraphs verbatim.
- [ ] Assign `severity`, `display`, `isFault`; add `blinkPattern` if LED-based.
- [ ] Attach `source` (best/primary source for that entry).
- [ ] Note conflicts between sources in the series file `notes` or source log.

### Roman Urdu writing rules
- Latin script, sentence case, natural technician speech.
- Keep English technical nouns: `capacitor`, `choke`, `gas pressure`, `remote`, `PCB`,
  `sensor`, `thermistor`, `outdoor`, `indoor`, `flare nut`, `ampere`.
- Be direct and actionable: `"Fan na chal raha ho to capacitor check karein."`
- No Arabic-Urdu script, no Romanizations that confuse search (`gas` not `gaas`).

## Phase D — Completeness cross-check (before marking done)

- [ ] Compare your code list against **at least 2 independent sources** — any code in a
      source that's missing from our files must be added or explicitly noted as
      "not applicable to PK models" in the source log.
- [ ] Run `python3 tools/validate.py` → 0 errors.
- [ ] Spot-check: every code has non-empty `en` AND `ur` for title/meaning,
      ≥2 causes, ≥2 solutions (exceptions: pure `info` entries).
- [ ] Update `data/index.json` (`status: "done"`), `docs/BRAND-ROADMAP.md` (☑),
      `docs/PROGRESS.md` (append entry), and the source log final row count.

## Phase E — Hand-off hygiene

- Commit-free workflow is fine, but `docs/PROGRESS.md` must always answer:
  **what's done, what's half-done, what's next** — so a fresh session can take over
  without asking questions.
