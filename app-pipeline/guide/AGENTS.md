# AGENTS.md — Master instructions for the app-builder agent

**Project: AC Ustad — AC & Solar Inverter Error Code Knowledge Base**
Package `com.acustad.app` · Kotlin + Jetpack Compose · minSdk 26 · light + dark · fully offline

---

## 1. What you are building

A technician opens the app while standing in front of a broken air-conditioner or solar
inverter. The unit is showing a code: `E6`, `F4`, `Error 200`, `ID013`, or a blinking LED.
The app tells them **what it means** and **what to do about it**, in plain English or
Roman Urdu, with no signal and no account.

The data is finished and verified: **62 brands · 320 model lines · 4418 codes** · every
fault code has **at least 2 causes and 2 fix steps** in both languages. Your job is only to
present it well. You are not adding data, guessing meanings, or expanding scope.

## 2. Where everything is

```
app-pipeline/START-HERE.md        what the data is
app-pipeline/DATA-CONTRACT.md     the authority on data shape
app-pipeline/db/kb.sqlite         the database (9 tables, 8.8 MB) — copy to assets
app-pipeline/guide/               THESE 20 documents — you follow them in order
data/                             researched knowledge base (source of truth, never edit)
```

## 3. Read order — do not skip

1. `AGENTS.md` (this file)
2. `RULES.md` — hard constraints, violations are worse than missing features
3. `DESIGN.md` + `design_tokens.xml` — before any UI code
4. `DATA_SCHEMA.md` — before any database code
5. `API_REFERENCE.md` — before anything that "fetches" (almost nothing)
6. `DEPENDENCIES.md` — before editing `build.gradle.kts`
7. `PERMISSIONS.md` — before touching `AndroidManifest.xml`
8. `ASSETS.md` — before referencing any image or font
9. `CI_CD.md` — before touching `.github/workflows/`
10. The phase file for the phase you are working on, then `TESTING.md` at the end of it

## 4. The five rules you will get wrong if you are not told twice

1. **A code is identified by (brand, series, code).** Never key anything on the code string
   alone. `E1` exists on 20 brands, `E3` on 21, `E6` on 16, `F4` on 15 — with different
   meanings every time. 359 distinct code strings appear on more than one brand.
2. **Search is scoped to the level you are on.** Brand page searches brands. Series page
   searches model lines. Code page searches codes *within that series only*. There is no
   global code search, and you must not add one.
3. **Never put raw user input into an FTS `MATCH` query.** 454 of the 2139 code strings
   crash SQLite if passed raw (`BLINK-RUNNING`, anything containing `;` or `+`). See
   `PHASE_5_SEARCH.md` and `DATA_SCHEMA.md` §7.
4. **The database is read-only.** The only table you may ever write is `favourites`.
5. **Never invent content.** Every string a user reads about a code comes from the database
   (en or ur). Do not hard-code meanings, code lists, brand names, severities or fix steps.
   If the data has no value for something, hide that element — do not write placeholder text
   that looks like data.

## 5. The one design decision that makes this app different

Most of the time a code exists on **exactly one brand** (1715 of 2139 code strings). So the
app is a **narrow, confident path**, not a search engine:

```
AC or Inverter  →  Brand  →  Model line  →  Code  →  Fix steps
```

Every screen has a search box, but it only ever searches the children of that screen. If a
technician types `E6` while looking at a Carrier model line, they get Carrier's E6 and
nothing else. That is the whole point — a technician must never be shown two brands'
meanings for the same code and have to guess which applies.

## 6. Language rule (easy to get wrong)

The app has a **content language toggle: EN / UR (Roman Urdu)**. It is **not** a UI
translation switch.

| Switches with the toggle | Always English, never translated |
|---|---|
| code title, meaning, causes, solutions, notes | every UI label (buttons, menus, hints) |
| severity chip word, confidence badge word | **brand names** (Growatt, Sharp, Carrier) |
| | **series / model names** (MOD 3-15KTL3-X) |
| | unit-type labels (Hybrid Inverter, UPS, Split) |

Reason: a technician reads brand names, model numbers and app options in English anyway, and
the Roman Urdu content is what they need in their own speech. Do not add a `values-ur`
strings file.

## 7. Environment

- Terminal only. No Android Studio. Gradle via `./gradlew`.
- **No emulator, ever.** The human installs the APK on a physical phone and tests.
- GitHub Actions builds a debug APK on every push; the artifact is downloaded and installed.
- The repo must stay clean: no generated files, no caches committed (see `RULES.md`).
- Every commit ends by updating the **app project's** own `PROGRESS.md` so a fresh session knows
  where the build stands. (This knowledge-base repo has its own separate
  `docs/PROGRESS.md` — do not edit that one from the app build; it tracks data research.)

## 8. How you work

Work one phase at a time, in the order `PHASE_1` → `PHASE_9`. At the end of each phase:
run the checks that phase lists, run `TESTING.md`'s checks for it, confirm `./gradlew build`
is green, commit with the format in `RULES.md`, update `PROGRESS.md`. Never move on with a
red build. Never claim a phase is done without having run its checks.

If the design docs and the database disagree, **the database wins** — and you say so in your
report instead of quietly working around it.
