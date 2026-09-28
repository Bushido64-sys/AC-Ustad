# ASSETS.md — fonts, icons, and why there are no images

**The app ships almost no assets. That is deliberate: everything in it is text, and text is
what works on a cheap LCD in sunlight with a cracked screen protector.**

---

## 1. Fonts — the only visual assets that matter

### Required files

```
app/src/main/res/font/ibm_plex_sans_regular.ttf
                            ibm_plex_sans_medium.ttf
                            ibm_plex_sans_semibold.ttf
                            ibm_plex_sans_bold.ttf
                            ibm_plex_sans_condensed_semibold.ttf
                            ibm_plex_mono_medium.ttf
                            ibm_plex_mono_semibold.ttf
```

- **Download the real IBM Plex files** (OFL). Do not rely on whatever is already in the
  project, and do not substitute Inter, Roboto, Noto or a system family — the typography is
  half of the design (RULE 10).
- Declare `FontFamily` in Compose **and** a `res/font/*.xml` family where a `TextView` is
  used, so both paths agree. Never set a global default family and rely on inheritance.
- Usage: Sans for all prose, Sans Semibold for titles and rows, **Mono for codes, model names
  and counts**, **Sans Condensed Semibold for section and category headers only**.
- Check a release build actually renders them — R8 can strip font resources
  (`DEPENDENCIES.md` §6).

### Size

Seven weights of IBM Plex is roughly 900 KB–1.2 MB. If that is unacceptable, cut
`mono_semibold` and `sans_medium`, and keep regular + semibold + mono. **Never** cut to a
single family: code legibility is the app's core value.

## 2. Icons — five, total

| Purpose | Icon | Where |
|---|---|---|
| Back | `ArrowBack` | every app bar |
| Search | `Search` | inside the search field (prefix) |
| Favourite | `Star` / `StarBorder` | code detail app bar, Saved nav item |
| External source | `OpenInNew` | the source line, only when a URL exists |
| Expand | `KeyboardArrowDown` | collapsed notes and source blocks |

Everything else in the app is **text**: severity chips, confidence badges, section headings,
counts, and the chevron on the home panels. No icon-in-a-rounded-square, no decorative
iconography, no emoji as bullets (RULE 11). Icon tint is always `ink` / `ink_invert`, never
the blue — blue means *selected or primary*, and an icon that changes colour for decoration
destroys that meaning.

## 3. There are no images, and no logo library

- **No brand logos.** Not one, for a real reason: 62 manufacturers, each with its own
  trademark, each needing to be fetched or bundled. Bundling them adds megabytes and a
  licensing problem, and fetching them needs `INTERNET` — which the app does not have and will
  never have (`PERMISSIONS.md` §2). **Brand rows are text only.** This is not a placeholder
  waiting to be filled; it is the design.
- **No screenshots** in the app. The About page is three lines of text.
- **No illustrations, no empty-state images, no hero image, no icons on the home panels.**
  An empty state is one sentence and a button (`PHASE_3_BROWSE.md` §7).
- **No app icon beyond the launcher icon** — a flat `#1668A8` square with `AU` in IBM Plex
  Sans Bold, white, 2dp white inset border. No gradient, no rounded-corner gloss, no
  drop shadow in the icon itself.
- **No QR/barcode scanning**, which is why `CAMERA` is not requested. A code is typed or
  chosen from a list of at most 106 rows — faster than aiming a camera at an LED in daylight.

## 4. Content that is *not* an asset

All 4418 codes, 320 model lines and 62 brand names live in the SQLite database, not in
`res/values/strings.xml` (`RULES.md` RULE 1). `strings.xml` holds only the ~60 UI labels, the
severity/confidence words, and the empty states. Do not migrate content into resources — the
content is data, and the data ships as a database.

## 5. APK budget

| Item | Size |
|---|---|
| `kb.sqlite` | ~8.8 MB |
| IBM Plex fonts | ~1 MB |
| Code + resources | ~1 MB |
| **Total** | **~11 MB** |

One asset, one copy. If the APK jumps in size, something has been duplicated — check for a
second `kb.sqlite` in `res/raw`, and check that no image library was pulled in
(`DEPENDENCIES.md` §2, RULE 19).
