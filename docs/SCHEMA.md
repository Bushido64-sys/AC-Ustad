# Data Schema Guide

Authoritative field definitions for all files under `data/`. The machine-readable version
is `schema/brand.schema.json` (validate with `python3 tools/validate.py`).

## Language rule (IMPORTANT)

Every user-facing text field is an object with language keys:

| Key | Meaning | Example |
|---|---|---|
| `en` | English (official terms, search) | `"Compressor high-pressure protection"` |
| `ur` | **Roman Urdu** — primary display language for technicians | `"System ka pressure bohat zyada ho gaya hai"` |

- `ur` MUST be **Roman Urdu** (Latin script), simple words a Pakistani field technician
  understands. Mix in English technical terms naturally (`capacitor`, `gas`, `PCB`, `remote`).
  Example: `"Pehle outdoor ka fan check karein, fan na chal raha ho to capacitor badlein."`
- Do NOT use Urdu script (اردو) yet — a future `ur_script` key can be added without breaking
  the schema.
- Keep `en` accurate to manufacturer wording where known (short title style).

## Files

### `data/index.json`
```json
{
  "schemaVersion": 1,
  "updatedAt": "2026-09-23",
  "brands": [
    { "id": "dawlance", "name": "Dawlance", "category": "ac", "path": "data/ac/dawlance/brand.json", "status": "done" }
  ]
}
```
- `category`: `"ac"` | `"inverter"` (future: `"ac"`, `"inverter"` only for now).
- `status`: `"planned"` | `"researching"` | `"done"`.

### `data/<category>/<brand>/brand.json`
```json
{
  "schemaVersion": 1,
  "brand": {
    "id": "dawlance",
    "name": "Dawlance",
    "category": "ac",
    "country": "PK",
    "website": "https://dawlance.com.pk",
    "notes": "Pakistani brand (Karachi), part of Arçelik group.",
    "updatedAt": "2026-09-23",
    "sourceNotes": "See docs/sources/dawlance.md"
  },
  "series": [
    { "id": "powercon-x", "name": "Powercon X (Inverter)", "file": "powercon-x.json", "models": ["H&C 22"] }
  ]
}
```

### `data/<category>/<brand>/<series>.json` — the code file
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
    "notes": "Codes verified across 2022–2025 models."
  },
  "codes": [ { ...code object... } ]
}
```
- `unitType`: `"split"` | `"floor_standing"` | `"cassette"` | `"ducted"` | `"window"` |
  `"solar_hybrid_ac"` | `"hybrid_inverter"` | `"on_grid_inverter"` | `"off_grid_inverter"` |
  `"ups"` | `"vfd"` | `"generic"`.
- `modelPatterns`: glob-ish strings technicians can match against nameplates (e.g. `"DL*"`).
  Use `["*"]` when codes apply to the whole series.

## Code object

| Field | Required | Type | Notes |
|---|---|---|---|
| `code` | ✅ | string | Exactly as shown on display/remote: `E1`, `F0`, `H6`, `CH53`, `07`, `P0`, `LED: 6 blinks` |
| `aliases` | ✅ (may be `[]`) | string[] | Other writings of the same fault: `["E1","1"]`, `["CH07","07","7"]` |
| `title` | ✅ | `{en, ur}` | Short label, ≤ 60 chars English |
| `meaning` | ✅ | `{en, ur}` | 1–2 sentences: what the code means |
| `causes` | ✅ (may be `[]`) | `[{en, ur}]` | Ordered most-likely-first, 2–6 bullets |
| `solutions` | ✅ (may be `[]`) | `[{en, ur}]` | Step-by-step, ordered, safe-first; 2–8 bullets |
| `severity` | ✅ | enum | see below |
| `display` | ✅ | enum | Where the code shows: `indoor` \| `outdoor` \| `remote` \| `controller` \| `led_blink` \| `unknown` |
| `isFault` | ✅ | boolean | `false` for informational states (defrost `FH`/`DF`, filter `CF`, self-clean `CI`) |
| `relatedCodes` | ❌ | string[] | Codes worth cross-checking (`E1` often pairs with `E3`) |
| `blinkPattern` | ❌ | string | LED blink descriptions when relevant: `"6 flashes, 3s pause"` |
| `confidence` | ❌ | enum | `high` (official or 2+ sources agree) \| `medium` (1 solid source) \| `low` (video/tag/unverified meaning) |
| `notes` | ❌ | `{en, ur}` | Source conflicts / model-dependence warnings (e.g. "official manual says X, PK blogs say Y") |
| `source` | ✅ | object | `{ "type", "title", "url"?, "retrieved" }` — see below |

### `severity` enum (drives colored badges in the app)

| Value | Meaning | UI suggestion |
|---|---|---|
| `info` | Not a fault; status/info (`isFault` usually `false`) | gray |
| `self_clear` | Often clears after power cycle / transient event | green |
| `check_restart` | Check basics (filter, power, remote), then restart | yellow |
| `stop_pro` | Needs diagnosis; keep running may damage unit | orange |
| `danger` | Lethal voltage / gas / fire risk — stop and call qualified tech | red |

### `confidence` (optional but strongly recommended)

| Value | Rule |
|---|---|
| `high` | Official manual/support page, OR ≥2 independent sources agree |
| `medium` | Exactly 1 credible Dawlance-specific (or brand-specific) source |
| `low` | Video title only, SEO tag only, cross-brand guess, or meaning unverified |

`low`-confidence entries MUST explain the gap in `notes` so the app can show a
"verify on your model" badge.

### `source.type` enum
`service_manual` | `user_manual` | `official_support` | `distributor_page` |
`retailer_page` | `video` | `forum` | `technician_note` | `other`

## Series grouping rule (which file does a code go in?)

1. If the code appears only on one model line → that series file.
2. If the same codes serve the whole brand (common with local brands that share PCBs)
   → create a series called `generic` / `universal` with `modelPatterns: ["*"]`.
3. Never duplicate the same `code`+`aliases` set inside one file; across files is OK
   if meanings genuinely differ by series (note it in `notes`).

## Naming conventions

- Brand folder & `brand.id`: lowercase kebab: `dawlance`, `crown-micro`, `solax-power`.
- Series `id` & file name: lowercase kebab: `powercon-x.json`, `nitrox.json`, `generic.json`.
- Display names keep brand casing: `"Powercon X"`, `"NitroX"`.

## Adding fields later

- Additive optional fields are safe (app must ignore unknown keys).
- Renaming/removing fields requires bumping `schemaVersion` and updating `docs/SCHEMA.md`,
  `schema/brand.schema.json`, and `tools/validate.py`.
