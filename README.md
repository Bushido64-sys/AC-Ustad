# AC Ustad — AC & Inverter Error Code Knowledge Base

Offline knowledge base of **error/fault codes** for air conditioners and solar/UPS inverters,
focused on **Pakistani market brands first**, then international brands sold in Pakistan.
This data will power a future Android app for AC/inverter technicians ("AC Ustad").

---

## What this repository is

- **Research + structured data only.** No Android app code lives here (yet).
  The app will be built later from build files (e.g. `agent.md`, `Design.md`, phases 1–10)
  that the project owner will supply. When those arrive, start from **`docs/PROGRESS.md`**.
- Every brand is **one folder** containing its error codes, organized **series/model-wise**
  as JSON files (Android-friendly; DOCX/PDF can be exported later for sharing/printing).
- Every code entry is bilingual: **English + Roman Urdu** (Roman Urdu is the primary
  display language for technicians; English kept for official terms and search).

## Why JSON (not DOCX)

DOCX cannot be read natively inside an Android app (Apache POI is effectively broken on
Android). JSON is parsed instantly, validates against a schema, and loads into an Android
Room/SQLite database for offline search. Zoom/font-size/"field mode" are **app UI features**
that work regardless of the data format.

## Folder structure

```
.
├── README.md                     ← you are here
├── docs/
│   ├── PROGRESS.md               ← LIVE status: where work stopped, what's next (start here to resume)
│   ├── BRAND-ROADMAP.md          ← ordered list of every brand + phase + ☐/☑ status
│   ├── SCHEMA.md                 ← field definitions, severity levels, language rules
│   ├── AUTHORING-GUIDE.md        ← how to write one brand's files correctly
│   ├── RESEARCH-PROTOCOL.md      ← source checklist so no error code is left behind
│   └── sources/<brand>.md        ← traceable list of sources used per brand
├── schema/brand.schema.json      ← JSON Schema every data file must pass
├── data/
│   ├── index.json                ← list of all brands (app's brand picker data)
│   ├── ac/<brand>/               ← e.g. data/ac/dawlance/
│   │   ├── brand.json            ← brand metadata + list of series files
│   │   └── <series>.json         ← one file per series/model line = all its codes
│   └── inverter/<brand>/         ← e.g. data/inverter/inverex/
├── tools/validate.py             ← validates all data files against the schema
├── app-pipeline/                ← APP-READY PACKAGE (SQLite + JSON + data contract)
│   └── guide/                   ← 20-document Android build guide (AGENTS.md is the entry point)
├── research-raw/                ← source PDFs/screenshots kept out of the data tree
├── AI-AGENT-PROMPTS.txt        ← prompts for the reviewer & builder AI agents
└── exports/                     ← generated DOCX/PDF (future; not committed)
```

## How the Android app consumes this

**Ready now** — `app-pipeline/` is the app-ready package, and `app-pipeline/guide/` is the
finished 20-document build guide. Start at `app-pipeline/guide/AGENTS.md`.

1. Copy `app-pipeline/db/kb.sqlite` → `app/src/main/assets/db/kb.sqlite` and open it
   **read-only** (only the `favourites` table is ever written).
2. `brands` (62 companies, `categories` JSON) → category / brand list.
3. `series` (320 model lines, `model_patterns` for nameplate matching) → series list.
4. `codes` (4418 rows) + `causes` / `solutions` → code list and code detail
   (title/meaning/causes/solutions in **en + Roman Urdu**, severity badge, confidence, source).
5. `code_fts` (+ `aliases`) → search. **Search is scoped to the level you are on:**
   brands page searches brand names, model page searches model names, code page searches codes
   **inside that series only**. There is deliberately **no global code search**, because one
   code string means different things per brand (`E6` alone appears on 16 brands, `E1` on 20)
   and grouping them still leaves the technician guessing which machine they are standing in
   front of. Exact/prefix code lookups go through `aliases`; free text goes through `code_fts`
   with the input quoted (454 of 2139 code strings crash FTS unquoted).
6. `data-manifest.json` → data version, row counts and SHA-256 of each generated file.

`python3 app-pipeline/build_kb.py` regenerates the whole package from `data/` after any change.
`AI-AGENT-PROMPTS.txt` in the repo root contains ready-to-paste prompts for the reviewer and
builder AI agents, and points at the guide.

## Commands

```bash
# One-time setup (creates .venv with jsonschema)
python3 -m venv .venv && .venv/bin/pip install jsonschema

# Validate every JSON data file against the schema (run before marking any brand done)
.venv/bin/python tools/validate.py
```

## Conventions (summary — details in docs/SCHEMA.md)

- Brand folder names: lowercase, no spaces (`dawlance`, `inverex`, `crown-micro`).
- Series file names: lowercase-kebab (`powercon-inverter.json`).
- Error codes are stored **exactly as displayed** (`E1`, `F0`, `H6`, `CH53`, `07`,
  `LED: 6 blinks` variants go in `aliases`).
- `severity`: `info` | `self_clear` | `check_restart` | `stop_pro` | `danger`.
- Every entry carries a `source` (manual/URL) for traceability.
- Informal states (defrost `FH`/`DF`, filter-clean `CF`) are `isFault: false`, not errors.

## Legal note

Error codes and their factual meanings are facts (not copyrightable); manual prose/designs are.
Always **paraphrase** causes/solutions in our own words, attribute the source, never bundle
manufacturer PDFs or logos, and include an app disclaimer: unofficial, independent, not
affiliated with any manufacturer; lethal voltages — qualified technicians only.

## Project status

See **`docs/PROGRESS.md`** for live status and **`docs/BRAND-ROADMAP.md`** for the full
brand queue (Pakistani brands first, international later).
