# PHASE_8_ACCESSIBILITY_URDU.md — readable in sunlight, usable with gloves, in Roman Urdu

**Goal: usable by a 45-year-old fitter on a cheap LCD panel, in bright sun, with dirty hands,
wearing gloves — in either language.**

---

## 1. Type sizes are floors, not suggestions

| Role | Size | Family | Line height |
|---|---|---|---|
| Code (detail) | 20sp | IBM Plex Mono Bold | 26sp |
| Code (list) | 18sp | IBM Plex Mono Medium | 24sp |
| Title | 20sp | IBM Plex Sans Semibold | 26sp |
| Body / meaning / steps / causes | **16sp minimum** | IBM Plex Sans | **24sp minimum** |
| Section head | 18sp | IBM Plex Sans Condensed Semibold | 24sp |
| Chip / label | 14sp bold | IBM Plex Sans Bold | 18sp |
| Caption, source, counts | 14sp | IBM Plex Sans / Mono | 20sp |

- Muted text is `#5A7183` (5.09:1), **never** grey-400/500 (RULE 10).
- No italics anywhere — Roman Urdu must not look like a disclaimer.
- No justified text, no letter-spacing tweaks on body copy, no all-caps body.
- Test at system scales 1.0, 1.15, 1.3. At 1.3, a two-line row becomes three lines: the row
  must grow, not clip. **Never** cap a fix step or a meaning at two lines with an ellipsis.
- Honour the system font scale everywhere; never `sp` with a hard multiplier.

## 2. Roman Urdu rules for the content

The content ships in Roman Urdu already: `title_ur`, `meaning_ur`, `notes_ur` on `codes`, and the `ur`
column of `causes` and `solutions`.
The app must not "improve" it, transliterate it, or convert it to Urdu script.

- 24sp line height minimum, and generous 12–16dp vertical spacing between numbered steps.
- Longer, simpler words, one instruction per step.
- Punctuation is `.` and `,` only. Keep the content exactly as the database has it.
- The content toggle is **content only**: title, meaning, notes, causes, solutions and the
  severity/confidence words. Brand names, model numbers, unit-type labels and every UI label
  stay **English** — a technician reads a model number in English by habit. (RULE 13)
- Do not add a `values-ur` strings file. There is no Urdu-script UI.
- Test the longest content in both languages: a 359-character Urdu meaning, a 270-character
  step, a 725-character note, a 1,450-character series note.

## 3. Touch

- 48dp minimum in both axes; list rows ≥ 56dp; the star 48dp (RULE 16).
- At least 8dp between adjacent targets.
- Every gesture has a visible tap equivalent. Swipe-to-delete has Undo.
- The code list must be operable one-handed: the star is in the app bar, not in the row, so a
  thumb never covers the code while tapping.

## 4. Screen reader

Compose semantics, in reading order:

1. Severity chip: contentDescription = **"STOP, professional attention needed"** — the word
   first, because colour is meaningless to a screen reader.
2. Code: "E6".
3. Title.
4. Meaning.
5. "How to fix, 4 steps", then each step as its own node.
6. "Possible causes, 3", then each cause.
7. Source.

- Rows announce `"E6, Compressor drive overcurrent, STOP"`.
- Expand/collapse: `stateDescription`, and announce "notes expanded" / "notes collapsed".
- Decorative icons (chevron, clear button in an empty field) → `null` contentDescription.
- Touch target announcements must not be a separate node.
- Verify on TalkBack, not just visually: a screen-reader pass that reads a code's meaning
  without sighted navigation is the real test.

## 5. Contrast — measured, not assumed

| Pairing | Ratio | Verdict |
|---|---|---|
| ink `#0B1F2A` on canvas `#FFFFFF` | 16.89:1 | AAA |
| ink on surface `#EFF6FB` | 15.47:1 | AAA |
| muted `#5A7183` on canvas | 5.09:1 | AA |
| white on primary `#1668A8` | 5.87:1 | AA |
| white on light blue `#6FB4DE` | **2.27:1** | **FAILS — banned pairing** |
| ink on `#6FB4DE` | 7.45:1 | AAA |
| dark ink `#F2F6F8` on `#0B1116` | 17.46:1 | AAA |
| dark muted `#9FB3C0` on `#0B1116` | 8.76:1 | AAA |
| danger white on `#B32D0C` | 6.37:1 | AA |
| `stop_pro` ink on `#FFE7DF` | 14.27:1 | AAA |
| dark danger ink on `#FF6A3D` | 6.67:1 | AA |

Every text pair in the app is AA or better. The only failing combination is white on light
blue, and RULE 7 bans it.

## 6. Sunlight and cheap panels

- Contrast is not enough on a washed-out LCD: **never encode meaning in a subtle fill.** Every
  chip has a 2dp border, and the strongest severity (`danger`) is the only solid fill in the
  app.
- Severity is colour **plus** word **plus** border **plus** position (always the same slot).
  Four redundant cues, so no single failure hides the urgency.
- No thin 1dp text-coloured separators for meaningful grouping — use a 2dp ink border or
  spacing.

## 7. Reduced motion, RTL, and the rest

- Respect the system "remove animations" setting; no animation is load-bearing.
- `supportsRtl="true"`, and test the app bars and chips mirrored even though all content is
  LTR.
- No flashing, no auto-playing anything, no colour-only state anywhere.
- Minimum API 26 means TalkBack and switch access are the baseline, not extras.

## 8. Checks

- [ ] System font scale 1.0 / 1.15 / 1.3 on every screen: no clipping, no overlap
- [ ] Display size large and small
- [ ] TalkBack completes a code end to end without visual navigation
- [ ] All chips announce their **word** first
- [ ] Every tappable element ≥ 48dp, rows ≥ 56dp
- [ ] Every text pair ≥ 4.5:1 in both themes (audit, don't eyeball)
- [ ] Longest meaning, longest step, longest note, in EN and UR
- [ ] Reduced motion on → no missing state changes
- [ ] Gloves: home → code detail in ≤ 4 taps
