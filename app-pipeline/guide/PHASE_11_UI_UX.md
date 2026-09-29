# PHASE_11_UI_UX.md — giving the app depth, hierarchy and a pulse

**Goal: stop the app looking like a wireframe, without inventing a single new colour.**

**Status: PLANNING ONLY. Nothing in this file has been built.**

---

## 0. Read this first, because it is the whole phase

The app has a **complete, measured, hand-checked colour system** and it uses almost none of it.

`Theme.kt` maps 37 colour bindings. `colors.xml` defines 20 colours. `DESIGN.md` §2 assigns every
one of them a role, and `design_tokens.xml` is the single source for all of it.

Then three layout primitives throw it away:

```kotlin
// ui/common/Components.kt — BOTH of them
color = Color.Transparent
```

**`BorderedPanel` and `BorderedRow` are transparent.** So every card, every list row, the meaning
block, the fix-step block and the causes block sit on **raw canvas**. And `surface` (#EFF6FB) and
`surface_alt` (#DCEAF5) are mapped into the colour scheme and **never used as a fill anywhere in
the app**.

| What a user actually sees today | What DESIGN.md §2 says it should be |
|---|---|
| Card / list row | `#EFF6FB` — "Card / list surface" |
| Meaning block, code block | `#DCEAF5` — "Raised surface" |
| Everything else | `#FFFFFF` — canvas ✓ |

**That is the entire reason it reads as bare.** It is not a missing design. It is a
**built-but-unused design**: about 40 lines of change in three files, zero new colours, zero new
decisions, and no rule broken.

**This phase is mostly applying decisions that have already been made and documented.**

---

## 1. The three surfaces

Exactly three levels. No fourth. A screen that uses all three correctly already looks designed.

| Level | Colour | Light | Dark | Use it for |
|---|---|---|---|---|
| **0 · Canvas** | `canvas` | `#FFFFFF` | `#0B1116` | The page. Lists sit directly on it |
| **1 · Surface** | `surface` | `#EFF6FB` | `#141D25` | Cards, list rows, panels — anything you could *tap* |
| **2 · Raised** | `surface_alt` | `#DCEAF5` | `#1B2833` | The one block that is quoted content: meaning, a source line |

**Rule 1: if it is tappable, it is level 1 or above. If it is quoted from the database, it is
level 2. If it is neither, it stays on the canvas.**

The 2dp ink border stays on every level-1 and level-2 container. The fill is *added*, not a
replacement — the border is what survives a washed-out LCD, and the fill is what creates
hierarchy on a good one. `PHASE_8` §6: *"never encode meaning in a subtle fill."* We are not
encoding meaning in the fill; the word and the border still do that.

### Measured contrast, computed not assumed

Every pairing below was calculated for this phase from the actual resource values.

| Pairing | Ratio | Bar | Verdict |
|---|---|---|---|
| ink on canvas (light) | 16.89:1 | 4.5 | ✅ |
| ink on surface (light) | 15.47:1 | 4.5 | ✅ |
| **ink on surface_alt (light)** | **13.77:1** | 4.5 | ✅ |
| **ink_muted on surface (light)** | **4.67:1** | 4.5 | ✅ **but only just** |
| **ink_muted on surface_alt (light)** | **4.15:1** | 4.5 | ❌ **BELOW AA — see §2** |
| white on `blue_600` | 5.87:1 | 4.5 | ✅ |
| ink on canvas (dark) | 17.46:1 | 4.5 | ✅ |
| ink on surface (dark) | 15.67:1 | 4.5 | ✅ |
| ink on surface_alt (dark) | 13.82:1 | 4.5 | ✅ |
| ink_muted on surface (dark) | 7.86:1 | 4.5 | ✅ |
| ink_muted on surface_alt (dark) | 6.93:1 | 4.5 | ✅ |

Nothing fails except one combination, and §2 fixes it.

---

## 2. Two findings that constrain the whole phase

### 2.1 Muted text may NOT go on the raised surface — light mode only

`ink_muted` on `surface_alt` is **4.15:1**, under the 4.5 AA text bar. In dark mode it is 6.93:1
and fine, which is exactly the kind of thing that passes review in a dark-mode screenshot and
fails on a cheap LCD in sunlight.

**So: on a raised surface, use full `ink`, never `ink_muted`.** Not a compromise, a rule, and it
is only 1–2 labels per screen. Anywhere muted text would land on a raised block, it either moves
up to the canvas or goes to full ink.

### 2.2 The 2dp hairline is invisible in sunlight — and the docs disagree

| Hairline on canvas | Ratio | Non-text bar (3.0) |
|---|---|---|
| light `#C9DCEA` on `#FFFFFF` | 1.41:1 | ❌ |
| dark `#24333F` on `#0B1116` | 1.47:1 | ❌ |

`DESIGN.md` §1 asks the bottom bar for a *"2dp top hairline."* `PHASE_8` §6 says the opposite
outright:

> *"No thin 1dp text-coloured separators for meaningful grouping — use a 2dp ink border or
> spacing."*

**`PHASE_8` wins, because it is the one written for a cheap LCD in sun.** The nav's top line is
structural grouping, not decoration. Change it to `outline` (ink) at 2dp, or drop it and use
spacing. A 1.4:1 line does not exist on the screen the user is holding.

**`hairline` stays available for exactly two decorative uses** — the `info` chip's border, where
`DESIGN.md` §3 already accepts 1.42:1 because the *word* is the signal, and nowhere else.

---

## 3. The change, by file

### 3.1 `ui/common/Components.kt` — the two lines that fix most of it

```kotlin
// BorderedPanel
color = MaterialTheme.colorScheme.surface

// BorderedRow
color = MaterialTheme.colorScheme.surface
```

Then add one new component, used only where §1 says level 2:

```kotlin
/** A raised block: quoted content from the database. Never muted text inside. See §2.1. */
@Composable
fun RaisedPanel(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = inkBorder(),
    ) { Column { content() } }
}
```

**That is the whole visual core of this phase.** Do it first, on its own commit, and put it on
the phone before doing anything else. If it does not look better, stop and rethink rather than
build the other ten items on a wrong foundation.

### 3.2 `ui/detail/CodeDetailScreen.kt` — where the design says "raised"

`DESIGN.md` §4.5 draws the meaning inside a **raised** block, and it is currently a transparent
`BorderedPanel`. Convert exactly these:

| Block | Level | Why |
|---|---|---|
| Meaning | **2 · raised** | Quoted content. It is the thing a technician reads |
| Fix steps, each step | 1 · surface | Tappable-feeling, and a step IS an instruction |
| Causes, each cause | 1 · surface | Same |
| The source line | **2 · raised** | It is a quotation from the database |

**No block gets both.** The most common way a designed screen goes back to looking generic is
nesting a surface inside a surface inside a surface. Three levels, one per role, done.

### 3.3 `ui/home/HomeScreen.kt` — the first screen anyone sees

The two category panels are the app's front door and are currently white on white. Level 1
surface, and one more thing `DESIGN.md` §4.1 asks for and the code does not do:

> `40 brands · 2139 codes` — **14sp muted, live from the DB**

The counts **are** already muted on a surface, so `ink_muted` on `surface` at 4.67:1 applies and
**passes** — but it is 0.17 above the bar. At system font scale 1.3 (`PHASE_8` §1) a 14sp muted
label is still AA, because scaling does not change contrast, so this is safe. Note it anyway: if
a future change pushes that text onto a raised block, it fails (§2.1).

### 3.4 `ui/browse/*.kt` and `ui/saved/SavedScreen.kt`

Rows go to level 1 automatically via `BorderedRow`. Two specific things:

- **`SeriesScreen`'s expandable `notes_en`** is quoted content → **raised**, not surface. It is
  the one place on that screen where the brand's own words appear, and it should read as a
  quotation.
- **The search field** is a control, not a container. Keep it on the canvas, or give it level 1
  and accept that it now looks like a row. Either is defensible; pick one and write it down.
  **Recommended: level 1**, so every tappable thing in the app looks the same.

### 3.5 `ui/AcUstadBottomNav.kt`

- The top line becomes 2dp `outline`, not `hairline` (§2.2)
- The bar itself stays on the **canvas** — it is chrome, not content, and a filled bar competes
  with the list above it
- The selected indicator keeps its hard shadow and stays `blue_600`

---

## 4. The token drift, which is a separate bug

`design_tokens.xml` defines **29 dimens**. The app uses **`R.dimen` exactly zero times** and
hardcodes **113 literal `.dp` values** in Kotlin.

`AI-AGENT-PROMPTS.txt` says of the token file: *"the ONLY place a colour, size or spacing is
defined."* Right now it is the only place they are **documented** and nowhere they are **used**.

Worse, one of the hardcoded values is not even a token: **12dp** is used for row padding and
appears in no token file. The scale is 4 / 8 / 16 / 24 / 32.

**Do not convert all 113 in one go.** That is a huge diff with no visible benefit and a real
chance of breaking a phone check. Instead:

1. Add the dimens that are missing — including `row_content_padding_v` (12dp) — so the scale is
   whole
2. Convert the **3 layout primitives** and the **new `RaisedPanel`** to `R.dimen` as part of §3
3. Convert the rest screen by screen, in the same commit as whatever else that screen is getting

The end state is the token file being load-bearing. The start state is "we know the scale."

---

## 5. Motion — currently zero

`DESIGN.md` §5 asks for **120–180ms `FastOutSlowIn` on the panel press**, and nothing else. There
is no animation anywhere in the app today. A screen that never moves reads as a screenshot.

Exactly three, and no more:

| Where | What | Spec |
|---|---|---|
| Panel and row press | A press-scale or alpha dip | 120ms `FastOutSlowIn` |
| Navigating between screens | Nothing. No shared element, no slide | — |
| The Saved swipe settle-back | **Already animated** — do not touch it | 160ms |

`DESIGN.md` §5: *"No shared-element transitions, no animated gradients, no skeleton shimmer."*
And every one of the three must respect the system "remove animations" setting, which Compose
does for free via `LocalViewConfiguration`.

**One motion, then stop.** Motion is the easiest thing in this project to overdo and the fastest
way to make a working tool feel like a toy.

---

## 6. Four structural fixes, because they are UI too

Not decoration. Each is a line in `DESIGN.md` that the code does not honour.

| # | Design says | Reality | Fix |
|---|---|---|---|
| 1 | §4.5 *"← chevron · series · brand · ☆ — the star is the only action in the top bar"* | The star is in the content body, and **`AcUstadAppBar` has no action slot at all** | Add one `action: @Composable (() -> Unit)?` to the app bar, move the star into it |
| 2 | §4.3 *"Back returns to the brands list with its query and scroll position intact"* | No list state is saved anywhere | `rememberSaveable` the `LazyListState` and the query per screen |
| 3 | §4.4 *"If the query matches exactly one code, offer 'Open E6' rather than making them tap a row"* | Not built | One extra row on a single result |
| 4 | §4.5 *"The full-screen list of sources for the brand goes at the bottom of the Brands screen"* | Not built | A trailing row on `BrandsScreen` |

**#1 is the important one.** It is why the detail screen's header looks unfinished, and it is
the one place the star is missing from where a thumb expects it. `PHASE_8` §3 says the star must
be in the app bar and not the row, *"so a thumb never covers the code while tapping."* The design
was right and the implementation drifted.

---

## 7. What this phase must NOT do

The list below exists because this is the phase most likely to break RULE 6–11 by accident.

- ❌ **No new colours.** Not one. All three surfaces already exist and are measured. If a screen
  needs a fourth, that is a conversation, not a hex value
- ❌ **No gradients, no third accent, no purple, no indigo** (RULE 6)
- ❌ **No shadows on cards.** `3dp 3dp 0` stays on the selected nav indicator and primary actions
  only. Filling a screen with shadows is how a designed app becomes a template (RULE 9)
- ❌ **No pill corners.** Level 1 and 2 are 4dp. Nothing here is 16dp
- ❌ **No muted text on a raised surface** (§2.1)
- ❌ **No icon on a surface that is not one of the five** (RULE 11)
- ❌ **No removing the 2dp ink border because the fill now does the work.** The border is the
  sunlight guarantee; the fill is the hierarchy
- ❌ **No nesting three surfaces deep** on one screen

---

## 8. The checks

Screen-level, all on a real phone, in **airplane mode**, in **both themes**:

- [ ] **The three levels are visibly different.** Card, raised block and canvas read as three
      things at arm's length. If they don't, §1 didn't work
- [ ] **Dark mode is checked, not assumed.** A surface that reads in light can vanish in dark
- [ ] The meaning block is clearly the *most* important thing on the detail screen
- [ ] Fix steps and causes read as instructions, not as a wall of text
- [ ] Every chip still shows its **word** first (RULE 8)
- [ ] The 2dp nav line is visible in sunlight. Walk outside. If you cannot see it, it is
      `hairline` again
- [ ] **No muted text on any raised block**, light mode especially
- [ ] A panel press is visible but instant — 120ms, not a bounce
- [ ] The star is in the **app bar** on the detail screen, and reachable one-handed
- [ ] Back from a filtered list returns to the same place in the list
- [ ] Font scale 1.3 on every screen: no clipped text, no overlap
- [ ] **Nothing else moved.** The six original `PROGRESS.md` §6 checks still pass — this phase
      must not break a screen it was not asked to touch

## 9. The order

1. **The two lines in `Components.kt` plus `RaisedPanel`** → put it on the phone. If the three
   levels do not read, **stop and rethink here**
2. **The detail screen** — the screen that matters most, and where `DESIGN.md` already says
   raised
3. **The app-bar action slot** (§6 #1) and the star move
4. Home, then the list screens
5. Motion — one press animation
6. §6 #2–4
7. The token drift, screen by screen, alongside whatever else each screen is getting

**Steps 1 and 2 are the phase. Everything after them is refinement.** If there is no time for
anything else, ship 1 and 2 and call it done.

## 10. A trap for whoever builds this

**A fill that is too subtle reads as a bug, not as restraint.** The instinct when a surface is
invisible in sunlight is to darken it, and then the next instinct is to add a shadow. Both are
wrong here, and both are what `PHASE_8` §6 is warning about.

If a surface is not visible in sunlight, the answer is a **stronger border or more contrast in
the text** — never a darker fill and never a shadow. The three levels here are deliberately
subtle; the ink border and the word are what carry the meaning.
