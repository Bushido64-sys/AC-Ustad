# DESIGN.md — screen by screen

Read with `design_tokens.xml` open. No value in this file exists outside the token file.

---

## 1. The look, in one paragraph

Sharp-cornered panels with a 2dp ink border, white (or blue-black) canvas, IBM Plex Sans for
text and IBM Plex Mono for codes, and a single blue that means "this is selected / this is
the primary action". One alert hue, used only for severity. Hard `3dp 3dp 0` shadows, and only
under the primary button and the selected nav item. It should look like a well-made piece of
field equipment, not like a website.

## 2. Colour roles

| Role | Light | Dark |
|---|---|---|
| Canvas | `#FFFFFF` | `#0B1116` (blue-black, not black) |
| Card / list surface | `#EFF6FB` | `#141D25` |
| Raised surface (code block) | `#DCEAF5` | `#1B2833` |
| Hairline | `#C9DCEA` | `#24333F` |
| Text | `#0B1F2A` | `#F2F6F8` |
| Muted text | `#5A7183` | `#9FB3C0` |
| Primary action | `#1668A8` + white text | `#1668A8` + white text |
| Accent border | `#2A86C7` | `#6FB4DE` |

Every pair above is measured: body text 16.9:1 light / 17.5:1 dark, muted 5.1:1 / 8.8:1,
white-on-primary 5.9:1. All AA or better.

**The one trap:** white text on the light blue `#6FB4DE` is **2.27:1 and fails.** Light blue is
for accents, borders and fills that carry *ink*. White text only ever goes on `#1668A8` or
`#B32D0C`. (`RULES.md` RULE 7)

## 3. Severity ladder — one hue, five steps

Every chip carries its **word**. Measured ratios in brackets.

Each ratio below is **text-on-fill** unless the word *border* is used. Borders are held to the
3:1 non-text bar, not the 4.5:1 text bar.

| `severity` | Look | Text ratio (≥4.5) | Border vs its own fill (≥3.0) | Word (EN / UR) | Meaning |
|---|---|---|---|---|---|
| `danger` | solid `#B32D0C` fill, **white** text, 2dp `#7E1F06` border | white on `#B32D0C` **6.37** | `#7E1F06` on `#B32D0C` 1.58 — border is decorative here; the solid fill already reads as a block | DANGER / KHATRNAK | wrong part, wiring, or a damaged board — do not power-cycle repeatedly |
| `stop_pro` | `#FFE7DF` fill, **ink** text, 2dp `#D03F10` border | ink on `#FFE7DF` **14.27** | `#D03F10` on `#FFE7DF` **4.03** | STOP / BAND KARO | professional attention needed; 60% of all faults |
| `check_restart` | `#FFF0EA` fill, **ink** text, 2dp `#E24A1E` border | ink on `#FFF0EA` **15.20** | `#E24A1E` on `#FFF0EA` **3.61** | CHECK / DEKHO | check one thing, then restart |
| `self_clear` | `#E6EAED` fill, **ink** text, no border | ink on `#E6EAED` **13.96** | n/a | CLEARS / APNE AAP | clears itself on restart |
| `info` | no fill, **ink** text, 2dp `#C9DCEA` border | ink on canvas **16.89** | `#C9DCEA` on canvas 1.42 — outline only, never the sole cue (the word is) | INFO / MALOOMAT | indicator, parameter or setting, not a fault |

Every **text** pair is AA or better. The two borders below 3:1 (`danger` 1.58, `info` 1.42) are
acceptable because neither chip relies on its border to be visible: `danger` is a solid fill,
`info` is an outline on the canvas where the chip is the only element — and both carry the
word, which is the actual signal (RULE 8).

Dark mode: danger becomes `#FF6A3D` fill with `#0B1116` text [6.67:1]; `stop_pro` and
`check_restart` become `#3A1A10` surfaces with `#FFB49E` text [9.19:1] and keep their
coloured borders.

Because 2321 of the 3849 faults are `stop_pro`, the chip must read as *informative* at a
glance, not as an alarm. Danger is the only solid fill in the whole app.

`confidence` is a separate, quieter chip — outline only, no fill: HIGH / MED / LOW
(3565 / 718 / 135). Show it on the detail screen only, never in lists.

`is_fault = 0` rows (569, 13%) are indicators, parameters and self-clear entries. Give them a
muted 2dp left rail instead of a severity chip, and the word INDICATOR, so a technician never
reads `Parameter P003` as a fault.

## 4. Screens

### 4.1 Home — choose the machine
Full-bleed, two panels stacked, each the full width with a 2dp ink border and 4dp radius.

```
┌──────────────────────────────────────────┐
│  AC Ustad                                 │
│  Error codes, 62 brands. Works offline.  │  ← 14sp muted, never a subtitle flourish
├──────────────────────────────────────────┤
│ ┌──────────────────────────────────────┐ │
│ │  AIR CONDITIONER                     │ │  ← 20sp semibold
│ │  40 brands · 2139 codes              │ │  ← 14sp muted, live from the DB
│ │                              [ → ]   │ │  ← the only icon: a chevron
│ └──────────────────────────────────────┘ │
│ ┌──────────────────────────────────────┐ │
│ │  SOLAR INVERTER                       │ │
│ │  22 brands · 2279 codes              │ │
│ │                              [ → ]   │ │
│ └──────────────────────────────────────┘ │
└──────────────────────────────────────────┘
```

- No hero image, no gradient, no marketing copy, no "Get Started". Two choices, or get out of
  the way.
- Counts come from `brands`/`codes` at runtime. Never hard-code them.
- No search box here. A global code search is forbidden (RULE 3).

### 4.2 Brands (scoped to the chosen category)
- **Search box, top, persistent:** searches **brand names only**. Type `sh` → Sharp, Showell.
  Type `E6` → **no results**, and the empty state explains why: *"Codes are searched inside a
  model, because E6 means something different on 16 brands."* This turns the rule into a
  feature instead of a dead end. Type `air leakage` → also no results, but fault words are a
  *right question in the wrong box*, so the empty state says so instead of staying silent:
  *'air leakage' describes a fault, not a brand. This box searches brand names only.* The
  action button stays **Show all brands** in both cases; the guidance is in the detail line
  (added 2026-09-30, PROGRESS trap 25).
- List, one row per brand: **name** (16sp semibold) + **model count** right-aligned in mono
  (`7 models`). It is `brands.series_count`, not `code_count`: the question on this screen is
  how much is *inside* the brand, and one level down the model row answers the same question
  with codes. A brand with **0 codes** stays muted whatever its model count.
- Sort by `code_count` **desc**. It is the single highest-value decision on this screen.
- 8 brands have 0 codes: keep them visible but muted, and tapping one shows the empty state
  with the brand's `notes_en` if present. They are honest data, not errors.
- Row height 56dp+, full-width 2dp ink border, hard shadow on nothing.
- No icons. No logos — see `ASSETS.md`.

### 4.3 Series (scoped to one brand)
- Header: brand name, 20sp semibold, plus `notes_en` collapsed behind a `Show details` text
  control (max 725 chars, p90 202 — it must not flood the screen).
- **Search box searches model lines only.**
- Row: model name in IBM Plex Sans (these are model numbers, so mono is *not* required, but
  `code_count` in mono is) + **code count** on the right (`41 codes`). One screen up the brand
  row shows model counts instead — the two swapped on 2026-09-30, so each row answers the
  question that screen is asking.
- Back returns to the brands list **with its query and scroll position intact**.

### 4.4 Codes (scoped to one model line)
The busiest screen: up to 106 rows in one series. Serialised list, **never a grid** — code
strings run from `P003` to `LED1 x1 blink; LED2 off; LED3 off` and a fixed-width grid would
clip them.

```
┌──────────────────────────────────────────────┐
│  Search this model                    ← 14sp │  ← placeholder names the scope
├──────────────────────────────────────────────┤
│  ┌────────────────────────────────────────┐  │
│  │ [STOP]  E6                    2 lines  │  │  ← chip left, code mono 18sp
│  │         Compressor drive overcurrent   │  │  ← title 16sp, may wrap
│  │                                        │  │
│  │ [CHECK] Error 200                      │  │
│  │         DC bus voltage out of range    │  │
│  └────────────────────────────────────────┘  │
└──────────────────────────────────────────────┘
```

- Use the pre-joined `display` column (`code · mean title`) — do not re-join by hand.
- Row: severity chip (left) · code in Plex Mono (18sp) · title beneath (16sp, max 2 lines
  with ellipsis). 56dp min, grows to fit.
- `is_fault = 0` rows: muted left rail, no filled chip.
- The search box here searches **this model line only** — its codes, and (when the input is
  not a code anywhere in the knowledge base) its own descriptions, meanings, notes, causes and
  fix steps, so `air leakage` finds the two codes whose fix steps say it (`PHASE_5_SEARCH.md`
  §2, PROGRESS trap 25) — and, when the words together appear nowhere in this model, the same
  index OR-joined still returns the titles that hold one of them: `air leakage` in Dawlance's
  Splits gives `CF` and `E4`, because no title in the whole database holds both words. The
  precise answer always outranks the loose one. If the query matches exactly one code, offer
  "Open E6" rather than making them tap a row.
- That third step is invisible when it returns nothing — a technician who searched a model
  and read a bare "no code matches" concluded it was never implemented there. So a **fault
  description** that finds nothing now gets a second line saying what was searched (added
  2026-09-30):

  > **No code matches "water pump" in this model.**
  > Only Splits — Inverter & Fixed-Speed (shared platform) is searched — including what its
  > codes mean, what causes them and how to fix them. Words that appear only in another model
  > are not shown here.

  A real code that this model lacks (`E6`) still gets the headline alone: it needs no
  explanation, it simply is not here. Same `isDescription` carve-out as §4.2.

### 4.5 Code detail — action first
Order is fixed by product decision: **the technician wants the action, not the essay.**

```
┌──────────────────────────────────────────────┐
│ ←  MOD 3-15KTL3-X          GROWATT     ☆    │  ← back chevron · series · brand · star
├──────────────────────────────────────────────┤
│  [STOP]  E6                                 │  ← severity chip, then the code, 20sp mono
│  Compressor drive overcurrent               │  ← title_en / title_ur, 20sp semibold
│  ┌────────────────────────────────────────┐  │
│  │ The drive reports a DC overcurrent     │  │  ← meaning, 16sp / 24sp line height
│  │ while starting the compressor.         │  │
│  └────────────────────────────────────────┘  │
│                                              │
│  HOW TO FIX                        4 steps  │  ← section head + count, 18sp
│  ┌────────────────────────────────────────┐  │
│  │ 1  Isolate the unit at the DC          │  │  ← numbered, Plex Sans 16sp
│  │    disconnect switch.                  │  │
│  │ 2  Measure DC bus voltage; below      │  │
│  │    380 V indicates a failed capacitor  │  │
│  │    bank.                                │  │
│  │ 3  ...                                 │  │
│  └────────────────────────────────────────┘  │
│                                              │
│  POSSIBLE CAUSES                     3       │
│  1  Compressor winding short to ground      │
│  2  DC bus capacitor bank degraded          │
│  3  ...                                      │
│                                              │
│  Notes                              ▾       │  ← collapsed (p90 202, max 725)
│  Source                            ▾       │  ← service_manual · manual p.42  [↗]  │
└──────────────────────────────────────────────┘
```

- **Fix steps come before causes.** Causes are reference material; steps are the job.
- The star is the only action in the top bar. Starred = fill `#1668A8` + white, unstarred =
  2dp ink outline, 48dp touch target.
- Section order: severity+meaning → **HOW TO FIX** → POSSIBLE CAUSES → Notes → Source.
- Hide any block whose data is empty. `panasonic` `H00` has no causes: the causes block is
  simply absent, with no gap and no placeholder.
- Numbers matter: they are what a technician says on the phone ("try step 2").
- Source line shows `source_type` + `source_title`, and a link **only** when `source_url` is
  present (26 codes have none — no dead button).
- ~~The full-screen list of sources for the brand goes at the bottom of the Brands screen, not
  here.~~ **Not built, by decision 2026-09-29.** The per-code `source_type` / `source_title` /
  `source_url` still show on **this** screen, which is where a technician checking a specific
  answer wants them. What is missing is the per-brand roll-up at the bottom of Brands, and the
  same day that the global coverage panel came off Settings — so the line above is now an
  override rather than a description. It needs a new route and a new query; it is a judgement
  call, not an oversight.

### 4.6 Saved
- Rows: code (mono) + series + brand, star filled. There is no `is_read` column, so there is
  no unread dot — the saved row has no brand/series/title of its own, so join to render.
- Tapping marks it read and opens it. Row height 56dp.
- **Empty state:** one line, no illustration: *"Nothing saved yet. Tap the star on any code."*
- Write only to `favourites` (RULE 5).

### 4.7 Settings
Language (EN / UR — content only, per RULE 13) · Theme (System / Light / Dark) · Data version
from `meta.kb_version` + `meta.built_at` · About. Plain rows, 2dp borders, no illustration, no
social links.

**Revised 2026-09-29, and it is a deliberate override rather than an omission.** The original
list ended `… + meta.built_at` · Sources · About`, and two items are gone:

- **Sources is not on the screen.** The intent was that coverage be visible, so "my model is
  missing" has an answer. A coverage list is a fact about the app, and the person opening
  Settings has a machine in front of them and wants a fact about that machine. Provenance
  belongs on the code's own page, where it is — every code carries the source it was read from,
  and the detail screen shows it. The panel was measured before it was dropped, and it was
  correct; it was simply answering a question nobody was asking.
- **No size line.** The database size was measured at runtime rather than typed, so it could
  never be *wrong* — but it is a number about a file the user cannot act on, and it put a
  second claim-to-be-trusted on the screen next to the one that matters.

The **data version stays**: it is the single fact on this screen that tells a technician whether
the answers they are reading right now are current. That is the standard every item here is now
held to — *does this change what the user does?* — and coverage and file size failed it.

## 5. Motion
120–180ms, `FastOutSlowIn` for the panel press, nothing else. No shared-element transitions,
no animated gradients, no skeleton shimmer (a 2dp-bordered panel with the code in mono reads
faster than a shimmer). Respect the system "remove animations" setting.

## 6. Copy rules
Sentence case everywhere. No exclamation marks, no emoji, no em-dash rhythm. Empty states are
one sentence and factual. Buttons are verbs: **Search**, **Show all**, **Show details**,
**Clear**. Never "Get Started", "Elevate", "Level up", "Seamless".
