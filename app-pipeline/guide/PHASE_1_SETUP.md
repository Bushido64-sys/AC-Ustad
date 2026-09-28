# PHASE_1_SETUP.md — project skeleton, fonts, database in assets

**Goal: an app that opens, shows the 2 categories, and reads 62 brands from the shipped
database — on a physical phone, no emulator.**

---

## 1. Create the project

- Package `com.acustad.app`, app name `AC Ustad`, minSdk 26, targetSdk/compileSdk latest
  stable, Kotlin + Jetpack Compose, `build.gradle.kts`, version catalog.
- Package name is final. `com.acustad.app` — not `com.ac.ustad`, not a `.dev` suffix.
- `applicationId` must equal `namespace`. One module, `app`. No multi-module.
- Enable Compose, `buildFeatures.compose = true`, and set the Compose compiler extension for
  the Kotlin version you choose (see `DEPENDENCIES.md` for the compatible pairing).

## 2. Put the database in assets

```
app/src/main/assets/db/kb.sqlite     ← copy from app-pipeline/db/kb.sqlite
```

8.8 MB, 9 tables. **Copy it. Never regenerate it, never edit it, never duplicate it**
(RULE 5, RULE 19). Keep the shipped file byte-identical so `db_version` stays meaningful.

Verify the copy:

```bash
sha256sum app-pipeline/db/kb.sqlite app/src/main/assets/db/kb.sqlite
```

Both must print the same hash. Add it to `PROGRESS.md` when done.

## 3. Copy the database out of assets, read-only

The asset cannot be opened in place, so copy to cache on first launch, then open read-only
(`DATA_SCHEMA.md` §5). Delete and re-copy if the cached file is older than the asset or if
`openDatabase` throws. Do not add migrations, triggers or WAL.

## 4. Fonts — IBM Plex, bundled

```
app/src/main/res/font/ibm_plex_sans_regular.ttf
                            ..._medium.ttf
                            ..._semibold.ttf
                            ..._bold.ttf
                            ibm_plex_mono_medium.ttf
                            ibm_plex_mono_semibold.ttf
                            ibm_plex_sans_condensed_semibold.ttf   ← category headers only
```

- **Download them.** Do not rely on a font already in the project, and do not substitute
  Inter, Roboto or a system family for any of them (`RULES.md` RULE 10).
- Declare them in Compose as `FontFamily` objects; never set a global `Typography` with a
  system default. Every `TextStyle` in the app names a family.
- Mono is for **codes, counts and model numbers only**. Never for paragraphs.
- Condensed is for **section and category headers only**, to buy horizontal room.
- Set font family in XML *and* Compose, and use a `res/font` XML family where practical so
  `TextView` fallbacks stay consistent.

## 5. Theme

- Light **and** dark, both hand-built from `design_tokens.xml` — not stock Material Light/Dark
  schemes, not dynamic colour. The palette is the product.
- Put the light values in `res/values/colors.xml` and the dark values in
  `res/values-night/colors.xml` from the same token file.
- Follow the system theme by default; Settings offers System / Light / Dark.
- `windowBackground` = canvas colour so launch does not flash white in dark mode.
- Status bar: canvas background, ink icons (light) / light icons (dark). No accent behind it.

## 6. Manifest

`PERMISSIONS.md` covers this fully, and the short version is: **no `uses-permission` tags at
all.** `android:allowBackup="true"`, no `usesCleartextTraffic` games, `supportsRtl="true"`.

## 7. Version / build wiring

- `versionCode` increments on every release; `versionName` as `1.0.0`.
- `BuildConfig.DB_VERSION` from `meta.db_version` in the database — Settings shows it, so a
  support request can be answered from one screenshot.
- Debug builds are the only artefact the human installs. No Play release in this phase.

## 8. First screens

`HomeScreen` with the two category panels, wired to the real counts from the database:
40 AC brands / 2139 codes and 22 inverter brands / 2279 codes (read them at runtime, never
hard-code). Tapping a category pushes the Brands screen; tapping a brand row does nothing yet.

## 9. Checks before moving on

- [ ] `./gradlew assembleDebug` green
- [ ] `adb install` on a physical device, app launches, no crash on cold start
- [ ] Dark mode toggle in developer settings renders both themes, no white flashes
- [ ] IBM Plex visibly applied to all text, mono on codes
- [ ] `sha256sum` of the asset matches the source database
- [ ] APK is the only build output; `.gradle/` and IDE files are git-ignored
- [ ] `PROGRESS.md` updated and committed with the message format from `RULES.md` RULE 20

## 10. Traps
- **Emulator.** Never. No emulator, no AVD, not once (`AGENTS.md` §7).
- **Dynamic colour** will overwrite the entire palette on Android 12+. Do not enable it.
- **Stock Material colours** (purple by default) will leak in through any component you do
  not restyle. Audit every default.
- **A second copy of the database** somewhere in `res/raw` or `src/main/assets` — check the
  APK contents if the build suddenly gets 9 MB heavier.
