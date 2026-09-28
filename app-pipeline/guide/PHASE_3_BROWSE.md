# PHASE_3_BROWSE.md — categories → brands → model lines

**Goal: a technician reaches the right model line in at most three taps, by reading, not by
hunting.**

---

## 1. The path

```
Home (AC | Inverter)  →  Brands  →  Model lines  →  Codes
```

Every arrow is a filtered list. There is no tree view, no accordion, no drawer, no bottom-sheet
category picker. The data is small enough that three short lists beat every clever alternative.

## 2. Home

Two full-width bordered panels, 16dp apart, per `DESIGN.md` §4.1. Each shows the category
name, the live brand and code counts, and one chevron. Tapping pushes the Brands screen for
that category.

Counts come from the database at runtime. `brand_count` and `code_count` per category are
grouped in one query (`PHASE_2_DATA_LAYER.md` §4) and refreshed whenever the database is
opened. **Never hard-code "40 brands / 2139 codes".**

## 3. Categorising brands into AC and Inverter

**There is no `brands.unit_type`.** Unit type lives on `series` (`split` 62,
`hybrid_inverter` 109, `on_grid_inverter` 47, `generic` 36, `ups` 35, `off_grid_inverter` 14,
`ducted` 7, `floor_standing` 5, `cassette` 3, `window` 2), and `brands.categories` is a JSON
array of `'ac'` / `'inverter'`.

**Use `brands.categories` for the category split** — it is one clean JSON array, indexed
logic you can express in SQL:

```sql
SELECT COUNT(*) FROM brands WHERE categories LIKE '%"ac"%'        -- 31
SELECT COUNT(*) FROM brands WHERE categories LIKE '%"inverter"%'  -- 33
```

Those sum to **64, not 62**, because 2 brands sit in both. That is correct: they must appear
in both lists. For code counts use `series.category` ('ac' 99 series / 1,723 codes,
'inverter' 221 / 2,695), which sums to exactly 4,418.

Keep any unit-type mapping in one Kotlin file:

| Category | `unit_type` values |
|---|---|
| Air conditioner | `split`, `ducted`, `floor_standing`, `cassette`, `window`, `packaged` |
| Solar inverter | `hybrid_inverter`, `on_grid_inverter`, `off_grid_inverter` |
| Both (appear in the **AC** list, and also searchable from inverter — see note) | `ups`, `generic`, `solar` |
| Unclassified | `*` — assign deliberately; never drop a brand silently |

Note: UPS/inverter brands such as APC, Eaton, CyberPower, Kstar, Numeric, Must Power and
Felicity sit under **Solar inverter** because that is where a technician looks for an
inverter, even though the hardware is a UPS. Document any exception in the mapping file.
Every one of the 62 brands must appear in at least one category — verify with a unit test
that the two lists union to 62.

## 4. Brands screen

- App bar: category name, back chevron. No search icon in the app bar — the search **field** is
  already on screen.
- **Persistent search field at the top, searching brand names only** (RULE 3). Debounce 180ms,
  `KeyboardOptions(capitalization = Words)`, IME action Search.
- List sorted by `code_count` descending, then name. This puts the model lines with the most
  codes first, which is what a technician actually needs.
- Row: brand name 16sp semibold, `code_count` right-aligned in Plex Mono with the word
  `codes` (`41 codes`; `1 code`). 56dp minimum, 16dp horizontal padding, 2dp ink border,
  radius 4dp. No icon, no logo, no chevron.
- Row separation: a 1dp hairline **inside** the border, not a gap between cards. A stack of
  identical cards with rounded corners and shadows is exactly the generic-AI look
  (`RULES.md` RULE 10).
- **Show all**: render the first 16, then a full-width text button **Show all 40**. Brands are
  already searchable, so this only reduces initial work.
- **8 brands** have `code_count = 0`, and **65 of the 320 model lines** (one in five) do too.
  Keep brands visible, muted ink, no count emphasis; tapping shows the empty state. These are
  researched brands and models with no codes published — a fact about the world, not an error.
- Tap → push Series for that brand.

## 5. Series screen

- App bar: brand name, back chevron.
- Below it, the brand `notes` **collapsed** behind a `Show details` text control. These notes
  are long (median 485, p90 777, max 1,099 chars) and explain model coverage — useful, but
  not something to dump over the list.
- **Search field searching model lines only.**
- Row: model name 16sp, `code_count` in mono on the right. Same row treatment as brands.
- Sort by `code_count` descending, then name.
- **65 model lines have no codes.** Tapping one shows the empty state; it is not a crash and
  not a bug.
- Tap → push Codes for that series.

## 6. Back navigation must feel free

- Back from a filtered list returns to the parent **with the query and scroll position
  intact**. Losing your place because you checked a spelling is the fastest way to make an app
  feel cheap.
- The back chevron is one of the five permitted icons (`RULES.md` RULE 11), 48dp touch
  target, ink colour — never the blue, which means "selected/primary".
- System back must behave identically to the chevron.

## 7. States

| State | What to render |
|---|---|
| Loading | a 2dp-bordered panel with the category name in mono — **no shimmer** (`DESIGN.md` §5) |
| Empty after search | *"No brand matches 'shrp'."* + **Clear** — factual, one line |
| Empty codes in a series | *"Growatt SHARP series has no published codes."* + the series note if present. 65 series are in this state |
| Brand with 0 codes | as above, and the row was already muted |
| Error | one line + **Try again** |

## 8. Checks

- [ ] Union of both category lists == 62 brands (unit test)
- [ ] Sum of per-category code counts == 4418
- [ ] Search on the brands screen never returns a code
- [ ] Sorting puts the highest `code_count` first in both lists
- [ ] A 0-code brand shows a real empty state, no crash, no blank list
- [ ] Back preserves query + scroll
- [ ] Every row ≥ 56dp, every target ≥ 48dp
- [ ] Dark mode: no white surfaces, no black text on dark
