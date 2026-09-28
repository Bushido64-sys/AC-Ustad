# Prompt for the builder AI

Copy everything below the line into the builder AI, in the `AC-Ustad-app` repository, with
`app-pipeline/guide/` from the `AC-Ustad` repository available to read.

---

Build the AC Ustad app: an offline error-code lookup for air conditioners and solar
inverters. Kotlin, Jetpack Compose, minSdk 26, light and dark, zero permissions, zero
network. 62 brands, 320 model lines, 4,418 codes, all in a bundled read-only SQLite database.

Read these first, in this order, and follow them literally:
  1. `AGENTS.md`   — master instructions and read order
  2. `RULES.md`    — 20 hard rules
  3. `DESIGN.md` + `design_tokens.xml` — before any UI
  4. `DATA_SCHEMA.md` — before any database code
  5. `API_REFERENCE.md`, `DEPENDENCIES.md`, `PERMISSIONS.md`, `ASSETS.md`, `CI_CD.md`
  6. the phase file for the phase you are working on, then `TESTING.md`

## Build and test

There is NO Android SDK on this machine and NO emulator. Never try to start one. `./gradlew
assembleDebug` must be run by pushing to GitHub and using the CI artifact. To test a build:
download the `ac-ustad-debug` artifact, install with `adb install -r`, and walk the six checks
in `PROGRESS.md`.

## Where the truth is

- The database schema is pinned by `app/src/test/.../SchemaContractTest.kt`. If the guide's
  `DATA_SCHEMA.md` disagrees with that test, the TEST is right.
- Colours live only in `res/values/colors.xml` and `res/values-night/colors.xml`. Never
  hard-code a hex in Kotlin.
- A code is identified by (brand, series, code) — the `codes.uid` column, e.g.
  `growatt/growatt-mod-tl3x/E6`. Never key anything on the code string alone: `E1` is on 20
  brands, `E3` on 21, `E6` on 16, `F4` on 15.
- Search is scoped to the level you are on and there is NO global code search.
- Never pass raw input to an FTS `MATCH`: 454 of 2,139 code strings crash that way. Use
  `SearchInput.ftsQuery`.
- The database is read-only. Only `favourites` is writable, and it is keyed on `code_id`.

## Non-negotiables

Zero permissions, zero network, no analytics. No Room, no Hilt, no image loader, no
dependency you cannot justify in the commit message. Exactly five icons: back, search, star,
external link, expand. No images of any kind, no brand logos, no gradients, no pills, no
soft shadows. Severity always shows its word as well as its colour. Body text 16sp minimum,
24sp line height, 48dp touch targets, 56dp rows. Detail screen order: severity + meaning,
then numbered fix steps, then causes. The EN/UR switch changes content only — brand names,
model names and UI labels stay English.

## How to work

One phase at a time. At the end of each phase push, wait for CI, read the failure if there
is one, fix it, and only then move on. Update `PROGRESS.md` in the same commit. Commit
messages: `type(scope): plain words`, e.g. `feat(codes): scoped code search in a model line`.

Report what you built, what you verified by actually running it, and anything you chose not
to do and why. Do not claim a phase is finished without having run its checks.
