# AC Ustad — Android app

The Gradle project. It lives in `android/` inside the main repository, so the researched
knowledge base and the app that reads it are versioned together.

Offline error-code lookup for air conditioners and solar inverters.
Kotlin · Jetpack Compose · `minSdk 26` · light + dark · **zero permissions** · **zero network**.

---

## How you build and test this

**CI builds. The phone tests.** There is no Android SDK on the development machine and no
emulator — ever (`AGENTS.md` §7). A local build needs roughly 10 GB of SDK that this machine
does not have, so it is not an option.

1. Push to `main`.
2. Wait for the **build** check to go green.
3. Open the run → **Artifacts** → download **`ac-ustad-debug`**.
4. Unzip → `app-debug.apk` (~12 MB).
5. Install it on the phone:
   - tap the APK (allow *Install unknown apps*), or
   - `adb install -r app-debug.apk` — the **`-r` matters**, it keeps your saved codes.
6. Open the app and walk the six checks in `PROGRESS.md` §Testing.

Screen mirroring while testing: `scrcpy`.

## What CI refuses to let through

| Gate | What it catches |
|---|---|
| `assembleDebug lintDebug testDebugUnitTest` | compile errors, lint, unit tests |
| no `uses-permission` in the merged manifest | a dependency quietly adding `INTERNET` |
| database SHA + row counts + `PRAGMA integrity_check` | shipping the wrong database |
| APK size ceiling 25 MB | a duplicated database or an accidental image library |

## Where things come from

The knowledge base is the parent of this directory: `data/` holds the researched source of
truth, `app-pipeline/` the generated package, and this app carries one copy of its output at
`android/app/src/main/assets/db/kb.sqlite`. **If you change the data, rebuild it and copy the
new database across — never regenerate it here.** `build-app.yml` fails the build if the copy
in assets stops matching `app-pipeline/data-manifest.json`.

## ## The build guide

`app-pipeline/guide/` in this repository holds the 20 documents this app is built
from: `AGENTS.md` (read this first), `RULES.md` (20 hard rules), `DESIGN.md`,
`design_tokens.xml`, `DATA_SCHEMA.md`, and `PHASE_1`…`PHASE_9`.

Two things to know before you write UI code:

- **`DATA_SCHEMA.md` had column names that do not match the shipped database.** The real
  schema is pinned by `app/src/test/.../SchemaContractTest.kt`, and the test file is the
  authority when the two disagree.
- Colours live only in `res/values*/colors.xml`. Do not hard-code a hex in Kotlin.

## Current state

Phase 1 complete: project skeleton, database, fonts, theme, and a home screen that reads
live brand and code counts from the database. No browse, detail, search or favourites screens
yet. See `PROGRESS.md`.
