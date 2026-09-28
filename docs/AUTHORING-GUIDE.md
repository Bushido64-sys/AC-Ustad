# Authoring Guide — adding one brand end-to-end

Read after `SCHEMA.md` + `RESEARCH-PROTOCOL.md`. This is the mechanical "how to write
the files" for a single brand. Do **one brand fully** before starting the next.

## 0. Pick the brand

Take the next brand with status ☐ in `docs/BRAND-ROADMAP.md`. Flip it to 🔬
`researching` in both the roadmap and `docs/PROGRESS.md` before starting.

## 1. Create folders & source log

```bash
# AC brand example:
mkdir -p data/ac/dawlance
# Inverter brand example:
mkdir -p data/inverter/inverex
# Source log:
touch docs/sources/dawlance.md   # use brand id (lowercase kebab)
```

## 2. Write `brand.json`

See `SCHEMA.md`. Fill `website`, `notes` (1–2 sentences: who they are in Pakistan),
and list every series you will create. Update it as series files land.

```json
{
  "schemaVersion": 1,
  "brand": {
    "id": "dawlance", "name": "Dawlance", "category": "ac",
    "country": "PK", "website": "https://dawlance.com.pk",
    "notes": "Pakistani brand (Karachi, since 1980), part of Arçelik.",
    "updatedAt": "2026-09-23",
    "sourceNotes": "See docs/sources/dawlance.md"
  },
  "series": []
}
```

## 3. Research & extract (the long part)

Follow `RESEARCH-PROTOCOL.md` Phases A–C. Practical tips:

- Start with the **broadest PK-wide code table** you can find (distributor/blog pages
  list E0–F9 style sets quickly), then go deeper **series by series** with service manuals.
- One JSON file per series/model line. If a brand shares one PCB/code set across many
  lines (common for local brands), use a single `generic.json` with `modelPatterns: ["*"]`.
- Keep notes while researching: conflicting codes, PK-only models, LED-blink models.

### File skeleton (series file)

```json
{
  "schemaVersion": 1,
  "brandId": "dawlance",
  "series": {
    "id": "powercon-x",
    "name": "Powercon X (Inverter)",
    "category": "ac",
    "unitType": "split",
    "modelPatterns": ["*"],
    "notes": ""
  },
  "codes": []
}
```

### Code skeleton (bilingual)

```json
{
  "code": "E1",
  "aliases": ["E1"],
  "title": { "en": "High Pressure Fault", "ur": "High pressure ki fault" },
  "meaning": {
    "en": "System pressure exceeded the high-pressure trip point.",
    "ur": "System ka pressure limit se zyada ho gaya hai, unit ne protection mein band kar di hai."
  },
  "causes": [
    { "en": "Outdoor condenser blocked or dirty", "ur": "Outdoor unit ka coil/gaanda filter block hai" },
    { "en": "Outdoor fan not running", "ur": "Outdoor ka fan chal nahi raha (capacitor/motor)" }
  ],
  "solutions": [
    { "en": "Power off and inspect the outdoor unit for blockage", "ur": "Power off karke outdoor ki safai karein, koi rukawat to nahi" },
    { "en": "Check outdoor fan and capacitor", "ur": "Outdoor fan aur capacitor check karein" },
    { "en": "If pressure still high, check gas charge and sensor wiring", "ur": "Pressure phir bhi zyada ho to gas charge aur sensor wiring check karein" }
  ],
  "severity": "stop_pro",
  "display": "indoor",
  "isFault": true,
  "relatedCodes": ["E3"],
  "source": {
    "type": "distributor_page",
    "title": "Dawlance inverter AC error codes",
    "url": "https://example.com",
    "retrieved": "2026-09-23"
  }
}
```

## 4. Validate & register

```bash
python3 tools/validate.py          # must print 0 errors
```

Then update:

1. `brand.json` → `series[]` filled in; `brand.updatedAt` refreshed.
2. `data/index.json` → brand entry with correct `category`, `path`, `status: "done"`.
3. `docs/BRAND-ROADMAP.md` → ☑ + date + code count.
4. `docs/PROGRESS.md` → append a "Completed" entry (counts + any caveats).
5. `docs/sources/<brand>.md` → final row count matches codes written.

## 5. Common mistakes to avoid

- **Urdu script** instead of Roman Urdu → rejected by convention (SCHEMA.md).
- Copying manual prose verbatim → copyright risk; always paraphrase.
- Putting codes in the wrong series file → check grouping rule in SCHEMA.md.
- Missing `source` on entries → validation fails.
- Marking `FH`/`DF` (defrost) as a fault → should be `isFault: false`, `severity: info`.
- Forgetting `data/index.json` update → app brand list won't show the brand.

## 6. When the owner supplies build files (agent.md / Design.md / phases)

Resume exactly from `docs/PROGRESS.md`:
- Data work continues per this guide (same schema).
- App build consumes `data/` as described in root `README.md` ("How the Android app
  will consume this"). If the build files assume different paths/fields, **extend the
  schema additively** and note the mapping in `PROGRESS.md` — don't break existing data.
