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
  feature instead of a dead end.
- List, one row per brand: **name** (16sp semibold) + **code count** right-aligned in mono
  (`41 codes`).
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
  `code_count` in mono is) + count on the right.
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
- The search box here searches **this series' codes only**. If the query matches exactly one
  code, offer "Open E6" rather than making them tap a row.

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
- Source line shows `source_type` + `source_ref`, and a link **only** when `source_url` is
  present (26 codes have none — no dead button).
- The full-screen list of sources for the brand goes at the bottom of the Brands screen, not
  here.

### 4.6 Saved
- Rows: code (mono) + series + brand, star filled, unread dot when `is_read = 0`.
- Tapping marks it read and opens it. Row height 56dp.
- **Empty state:** one line, no illustration: *"Nothing saved yet. Tap the star on any code."*
- Write only to `favourites` (RULE 5).

### 4.7 Settings
Language (EN / UR — content only, per RULE 13) · Theme (System / Light / Dark) · Data version
from `meta.db_version` + `meta.generated_at` · Sources · About. Plain rows, 2dp borders, no
illustration, no social links.

## 5. Motion
120–180ms, `FastOutSlowIn` for the panel press, nothing else. No shared-element transitions,
no animated gradients, no skeleton shimmer (a 2dp-bordered panel with the code in mono reads
faster than a shimmer). Respect the system "remove animations" setting.

## 6. Copy rules
Sentence case everywhere. No exclamation marks, no emoji, no em-dash rhythm. Empty states are
one sentence and factual. Buttons are verbs: **Search**, **Show all**, **Show details**,
**Clear**. Never "Get Started", "Elevate", "Level up", "Seamless".
