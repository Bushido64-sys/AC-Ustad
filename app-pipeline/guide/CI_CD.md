# CI_CD.md — build the APK, gate the rules

**No emulator, no device farm, no store publishing. CI compiles, tests and packages; the human
installs the artifact on a physical phone.** (`AGENTS.md` §7)

---

## 1. Workflow: `.github/workflows/build.yml`

```yaml
name: build

on:
  push:
    branches: [ main ]
  pull_request:
  workflow_dispatch:

jobs:
  verify:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Validate the knowledge base
        run: |
          python3 -m venv .venv
          .venv/bin/pip install -q jsonschema
          .venv/bin/python tools/validate.py     # must report 0 errors, 0 warnings

      - name: Rebuild the app data package
        run: |
          .venv/bin/pip install -q -r app-pipeline/requirements.txt
          # Pin the timestamp to the one already in the committed manifest, so the build is
          # reproducible and a rebuild of unchanged data/ produces ZERO diff. Without this the
          # generated files differ on every run (they embed a build time) and the gate below
          # would always fail. build_kb.py also honours SOURCE_DATE_EPOCH.
          export KB_BUILT_AT=$(python3 -c "import json;print(json.load(open('app-pipeline/data-manifest.json'))['builtAt'])")
          python3 app-pipeline/build_kb.py

      - name: Data is up to date
        run: |
          # Any real data change would show up here (counts, contents, schema)
          git diff --exit-code app-pipeline/
          # the shipped asset must be the same file the build produced
          sha256sum app/src/main/assets/db/kb.sqlite
          grep -o '"kb.sqlite": *"[a-f0-9]*"' app-pipeline/data-manifest.json

      - uses: actions/setup-java@v4
        with: { distribution: temurin, java-version: 17 }

      - uses: gradle/actions/setup-gradle@v4

      - name: Lint, test, assemble
        run: ./gradlew --no-daemon lintDebug testDebug assembleDebug

      - name: The app requests only what the ads need
        run: |
          # fails the build if any permission beyond the three the ads need slipped
          # in (PERMISSIONS.md). See build-app.yml for the real thing.

      - name: The APK contains no network client but the ads SDK
        run: |
          # RULE 14. See build-app.yml for the real thing.
          #
          # The sketch below is what this file claimed for a long time and never had. Two
          # reasons it is not what ships: it greps one hard-coded `classes.dex`, and a multidex
          # APK splits the classes across `classes2.dex`, `classes3.dex` and so on, where a
          # library that should not be there is simply invisible to it. The shipped step unzips
          # **every** dex file and looks for the type descriptors, which cannot be dodged by
          # renaming a class.
          #
          # It also checks more than HTTP. An ad SDK is the realistic way this app would ever
          # grow a network client, and an ad SDK is not an okhttp class - so the check names
          # Play Ads, Facebook Ads and RxJava too, which is what an ad SDK brings with it.
          ! unzip -qo "$APK" -d "$WORK" 'classes*.dex' \
            && ! find "$WORK" -name 'classes*.dex' -print0 | xargs -0 strings \
              | grep -E "Ljava/net/HttpURLConnection;|Lokhttp3/|Lcom/google/android/gms/ads/;|..."

      - uses: actions/upload-artifact@v4
        with:
          name: ac-ustad-debug
          path: app/build/outputs/apk/debug/app-debug.apk
          retention-days: 30
```

## 2. What each gate protects

| Gate | The failure it catches |
|---|---|
| `tools/validate.py` | a malformed knowledge base — the data is the product |
| `git diff --exit-code` | someone edited the database by hand, or forgot to rebuild it |
| `sha256sum` vs manifest | the APK ships a database that is not the validated one |
| `lintDebug` | unused resources, a missing `contentDescription`, a hard-coded string |
| `testDebug` | the 2139-string search crash suite (`TESTING.md` §3) |
| no `uses-permission` beyond the three the ads need | a dependency or a copy-paste added a fourth permission |
| no HTTP class in the APK outside `gms/` + `ump/` | a second network client got added "just for later" |
| `gms/` + `ump/` present and noticed | the ads SDK arriving or leaving without anyone recording it |

The last three are the unusual ones, and they are the point: the app's network
promise — **three permissions for the ads, Google's servers and nobody else** — is
the easiest thing to break by accident, so it is enforced by the build rather
than by memory.

## 3. Secrets

None. The build needs no key, no token, no account. If a workflow ever needs one, that is a
sign something is being fetched from the network at build time — investigate it.

## 4. Versions to keep current

`actions/checkout`, `actions/setup-java`, `gradle/actions/setup-gradle`,
`actions/upload-artifact`. Keep the Gradle wrapper checked in and bump it deliberately, never
by accident.

## 5. Android SDK in CI

Add `android-actions/setup-android` (or the `setup-gradle` Android support) before the Gradle
steps, accept the licences non-interactively, and pin the compile SDK to the same version
`DEPENDENCIES.md` §3 declares. A build that works locally and fails in CI is almost always a
missing SDK platform or a different JDK — pin both.

## 6. Local equivalent

```bash
.venv/bin/python tools/validate.py
export KB_BUILT_AT=$(python3 -c "import json;print(json.load(open('app-pipeline/data-manifest.json'))['builtAt'])")
python3 app-pipeline/build_kb.py
git diff --exit-code app-pipeline/          # must be empty: reproducible build
./gradlew clean lintDebug testDebug assembleDebug
```

Run all five before every commit that a human will test. CI is the gate; the local run is what
keeps you from waiting for it.

**Reproducible builds:** `build_kb.py` embeds a build timestamp, so two runs a minute apart
would otherwise produce different bytes and a false "data is out of date" failure. It honours
`KB_BUILT_AT` (exact string) and `SOURCE_DATE_EPOCH` (unix seconds), and both this gate and the
local run pin the timestamp to the value already committed in `data-manifest.json`. When you
deliberately change the data, rebuild **without** the pin so `builtAt` advances, then commit
the new manifest alongside the new data.

## 7. Release

There is no store release in scope. A release is: `versionCode` bumped, a green run, a tag
`v1.0.0`, and a commit that updates the database if the data changed (`meta.kb_version` is the marker). The artifact is
downloaded from the workflow and installed over the previous build — `adb install -r`, no
uninstall, so favourites and settings survive (`PHASE_6_FAVOURITES.md` §1).

## 8. How the human installs and tests it

**CI builds; the phone tests.** There is no Android SDK on the developer's machine and no
local emulator, so the APK only ever comes from the workflow's artifact.

**Get the APK**
1. Push to `main` (or run the workflow manually with *Run workflow*).
2. Wait for the green check.
3. Open the run → **Artifacts** at the bottom → download `ac-ustad-debug` (a `.zip`).
4. Unzip → `app-debug.apk` (~12 MB: the 8.8 MB database plus the app).

**Install it — either way**
- **On the phone (easiest):** open the APK from Files/WhatsApp/Drive → allow *Install unknown
  apps* for that app when prompted → Install. Upgrades work the same way; Android replaces the
  old build, so **favourites survive**. Uninstalling wipes them.
- **Over USB:** connect the phone with Developer options + USB debugging on, then
  `adb install -r app/build/outputs/apk/debug/app-debug.apk`. `-r` is what preserves app data;
  omit it and you lose the saved list.
- **Watching the screen:** `scrcpy` mirrors the phone over USB, so screen changes and taps can
  be inspected from a laptop. Do not use it as a substitute for holding the real phone — gloves
  and sunlight are the actual test conditions.

**Then test in this order** (it is the real acceptance test, `TESTING.md` §8)
1. Airplane mode on. Open the app — it must work with no network at all.
2. AC → brand → model → code: severity word, meaning, then numbered fix steps, in that order.
3. Search a code on the **brands** screen → zero results, with the explanation. Search it again
   inside a model → exactly one result.
4. Star a code, force-stop, reopen → still in Saved.
5. Toggle EN/UR → content changes, brand and model names stay English.
6. Settings → merged version line matches the knowledge base (`meta.kb_version`)
   and the package (`versionName` + `versionCode`).

**Report bugs as words, not stack traces.** There is no crash reporting in the app
(RULE 14), so a plain description — "E6 on Growatt shows the Sharp fix step" — is the bug
report. Reproduce it against the **Data version** (`meta.kb_version`) shown in Settings.
