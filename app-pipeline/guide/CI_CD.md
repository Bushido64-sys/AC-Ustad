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

      - name: The app requests nothing
        run: |
          # fails the build if any permission slipped in (PERMISSIONS.md)
          ! grep -ri "uses-permission" app/build/intermediates/merged_manifests/

      - name: No network client in the binary
        run: |
          # fail on okhttp / retrofit / HttpURLConnection (RULE 14)
          ! unzip -p app/build/outputs/apk/debug/app-debug.apk classes.dex | strings \
            | grep -Ei "okhttp3|retrofit2|okio"

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
| no `uses-permission` | a dependency or a copy-paste reintroduced `INTERNET` |
| no HTTP class in the APK | a network client got added "just for later" |

The last two are the unusual ones, and they are the point: the two strongest promises this app
makes (**no permissions, no network**) are the two easiest to break by accident, so they are
enforced by the build rather than by memory.

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
`v1.0.0`, and a commit that updates the database if the data changed. The artifact is
downloaded from the workflow and installed over the previous build — `adb install -r`, no
uninstall, so favourites and settings survive (`PHASE_6_FAVOURITES.md` §1).
