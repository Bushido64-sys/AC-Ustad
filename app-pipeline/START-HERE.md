# START-HERE.md — read this first

**What this is:** an Android-ready package of **4418 verified error/fault codes** for air-conditioners
and solar inverters, in **English + Roman Urdu**, built from real manufacturer manuals.

**Who it is for:** a field technician in Pakistan. They open the app, the AC or inverter is showing
some code (`E6`, `F4`, `Error 200`, a blinking LED…), and they need to know *what it means* and
*what to do about it* — quickly, offline, in their own language.

---

## 1. The three rules that matter most

1. **A code is identified by `(brand → series → code)`, never by the code alone.**
   `E6` exists on **16 different brands** with 16 different meanings, and on Carrier alone it means
   three different things depending on the model line. Showing "E6 = room sensor" without the brand
   would send a technician to the wrong part. **359 distinct code strings appear on more than one brand.**

2. **Never invent content.** If something is not in the data, it does not exist — do not guess a
   meaning, do not invent a code, do not fill a blank with plausible text. The data was researched
   code-by-code from manuals, and every entry carries where it came from.

3. **Never edit the data files.** They are generated. If something is wrong, fix it at the source
   (`data/`) and re-run `build_kb.py`, or report it — do not hand-edit `kb.json` / `kb.sqlite`.

---

## 2. What is in this folder

| File | What it is | Use it for |
|---|---|---|
| **`db/kb.sqlite`** | The full database, 8.8 MB, ready to ship | Copy to `app/src/main/assets/databases/kb.sqlite` and open read-only |
| `db/schema.sql` | The same tables as readable SQL | Understanding / writing queries |
| **`kb.json`** | All 4418 codes in one flat file (8.8 MB), brand + series inlined | If you prefer JSON over SQLite, or for a quick look |
| `brands.json` | 62 brands → their series (0.3 MB) | Browse trees, counts, category grouping |
| `search-index.json` | Every code **and alias** → which brands/series use it (4.3 MB) | Building your own search without SQL |
| `data-manifest.json` | Version, build date, row counts, SHA-256 of every file | Showing "data updated on …" in the app, integrity checks |
| `build_kb.py` | The generator that produces all of the above | Re-run after any data change |
| `DATA-CONTRACT.md` | **Exact** tables, columns, allowed values, counts, and the behaviours the app must support | Building against the data |

Read `DATA-CONTRACT.md` before writing any code against this data.

---

## 3. Where the data came from

`data/` in the parent folder is the researched knowledge base: 62 brand folders, 320 model-line
files, each code with a `source` (manual, support page, distributor page) and a `confidence`
grade. `build_kb.py` reads that and produces this folder. It never writes to `data/`.

**Verified quality** (checked by the generator on every run, see `data-manifest.json → qualityChecks`):
0 codes with fewer than 2 fix steps · 0 fault codes without causes · 0 untranslated fields ·
0 non-Latin characters · 0 duplicate ids. Knowledge-base validator: **0 errors, 0 warnings**.

---

## 4. The one thing to get right in the search

A user typing `E6` must see something like:

```
16 brands use "E6" — pick your brand
  Carrier · SHV Fixed-Speed Ducted   →  Outdoor unit malfunction
  Daikin  · K-Series split inverter  →  Compressor lock / start-up failure
  Dawlance · Inverter & Fixed-Speed  →  Indoor fan motor fault
  …and 13 more
```

Not a single answer, and never a merged/averaged one. Same for aliases: typing `200` must find
`Error 200` (Growatt) and `200` (Sungrow) — different brands, different meanings.

---

## 5. Favourites

The `favourites` table ships **empty** — saved codes belong to the user's phone, not to the
shipped database. The app writes to it locally; it must never write to `brands`, `series`,
`codes`, `causes`, `solutions`, `aliases` or `code_fts`.

---

## 6. Known gaps — intentional, not bugs

- **8 brands have 0 codes** (`waves`, `rays`, `super-asia`, `nobel`, `max-power`, `trion`,
  `maestro`, `numeric`). They are registered on purpose as "we looked, nothing is published"
  entries, so the app can still show the brand and say the codes aren't documented. Do **not** hide
  them, and do not invent codes for them.
- **26 codes have no source URL** (type + title + date are present). Tapping "source" on those
  should show the document name without trying to open a link.
- **1 non-fault row has no causes** (Panasonic `H00` = "no fault stored") — correct by design.
- **The same code can mean different things on different model lines of the same brand.** This is
  deliberate research policy (never merge), not an error to be cleaned up.
