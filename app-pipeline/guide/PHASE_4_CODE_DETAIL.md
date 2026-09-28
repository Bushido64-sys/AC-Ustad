# PHASE_4_CODE_DETAIL.md — the screen the whole app exists for

**Goal: a technician reads the severity, does the fix, and can describe it on the phone —
without scrolling back and forth.**

Product decision, locked: **severity + meaning first, then the FIX STEPS, then causes.**
A technician standing in front of the unit wants the action, not the essay.

---

## 1. Layout, top to bottom

| # | Element | Spec |
|---|---|---|
| 1 | App bar | back chevron · **series name** (mono, 14sp) · **brand name** (14sp) · star 48dp |
| 2 | Severity chip | `DESIGN.md` §3 — fill + text + 2dp border, always with the word |
| 3 | Code | IBM Plex Mono 20sp bold, `letterSpacing 0.02`, `textScale` honoured |
| 4 | Title | IBM Plex Sans 20sp semibold, `title_en` / `title_ur` |
| 5 | Meaning block | 16sp / 24sp line height, on `blue_100` (light) / `surface_alt_dark`, 2dp border, 4dp radius, 16dp padding |
| 6 | **HOW TO FIX** | 18sp semibold head + `4 steps` count · numbered list from `solutions.idx` (1–n), 16sp |
| 7 | POSSIBLE CAUSES | 18sp semibold head + count · numbered list 1–n |
| 8 | Notes | collapsed at 180 chars, `Show more` / `Show less` text control |
| 9 | Source | `source_type` + `source_title` (e.g. *service manual · ABC Inverter p.42*) + a link **only** if `source_url` exists. **There is no `source_ref`.** |
| 10 | Confidence | outline-only chip `HIGH`/`MED`/`LOW`, detail screen only |

## 2. Fix steps — the part that must be perfect

- Numbered **1…n**, from `solutions.idx` (contiguous, starts at 1). **There is no `step_no`
  column.** Never a bullet, never an unnumbered paragraph: a technician says "step 2" out loud.
- Layout: the number sits in a 28dp square with a 2dp border on the left, the text wraps
  beside it. The number must not be inside the sentence's left margin, or long wrapped lines
  become unreadable.
- 16sp, 24sp line height, `space_sm` between steps, 2dp border around the whole block.
- Steps average 2.76 per fault and run 83 characters (max 270) — expect 3–4 lines for the long
  ones. Never truncate a step: a half-sentence instruction is worse than scrolling.
- Roman Urdu steps need the same or more room. Test at 20sp and at 1.3× system font scale.

## 3. Causes

Average 2.47 per fault, same numbered treatment, same typography, `space_md` below the fix
block. A visible rule or extra spacing must separate "do this" from "why it happened" so the
two are never confused. Same-language switching applies to both.

## 4. Data cases — no placeholders

| Case | Rule |
|---|---|
| no solutions (`panasonic` `H00`) | hide the whole HOW TO FIX block. No gap, no "No solutions available", no invented step |
| no causes (same row) | hide the causes block entirely |
| no `notes` | hide the block |
| no `source_url` (26 codes) | show the source line as plain text. No link icon, no disabled button |
| `is_fault = 0` (569 rows) | a muted 2dp left rail and the word INDICATOR instead of a severity chip |
| very long note (max 725) | collapsed at 180 chars |
| empty `meaning` | skip the block, do not show a title with nothing under it |

An empty block is invisible. A placeholder is a lie about the data.

## 5. Favourite

- Star in the app bar, 48dp target, one of the five permitted icons.
- Unstarred: 2dp ink outline, ink tint. Starred: filled `#1668A8` with a white star.
- Toggle writes to `favourites` only (RULE 5), as `(code_id, created_at)`, then refreshes the list
  flow. Optimistic update, rollback on failure. There is no uid to store and no `is_read` to set.
- The saved row also stores the series, brand and titles, so the Saved list renders with no
  queries and survives a database swap.

## 6. Content language

One query loads both languages (`PHASE_2_DATA_LAYER.md` §4); the toggle re-renders instantly
with no new query. It switches title, meaning, notes, causes, solutions and the severity /
confidence **words** (DANGER → KHATRNAK). It does **not** touch the series name, the brand
name or any UI label — a technician reads those in English (RULE 13).

Roman Urdu specifics: no italics, no justified text, 24sp minimum line height, 16sp minimum
size, and keep punctuation minimal — the data's Roman Urdu uses `.` and `,` and nothing else.

## 7. States

Loading: the 2dp-bordered panel with the code in mono. Never a blank screen, never a spinner
in the middle of the content. Error: one line, **Try again**. Missing code: a plain
"This code is not in this database" state — a technician may have mistyped a variant, so also
offer a search scoped to this series.

## 8. Checks

- [ ] Severity chip colour + word correct for all five values, both themes
- [ ] Fix steps numbered 1..n in order, none truncated
- [ ] `panasonic` `H00` renders with no causes and no solutions block, and no gaps
- [ ] A code with no `source_url` shows a source line with no link
- [ ] Toggle EN/UR: no new query, instant, brand/series names unchanged
- [ ] Star survives force-stop and a database re-copy
- [ ] System font scale 1.0 / 1.15 / 1.3: no clipped text, no overlap
- [ ] TalkBack reads chip word + code + title + steps in order
- [ ] Dark mode: all 9 blocks legible, no pure black
- [ ] Long note collapses at 180 chars and expands fully
