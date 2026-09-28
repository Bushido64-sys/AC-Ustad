# PHASE_7_OFFLINE_AND_UPDATES.md — offline is the only mode

**Goal: the app never mentions the network, and a data update ships as an ordinary new APK
version. No sync engine, no downloader, no migration path in the app.**

---

## 1. There is no online state

The whole knowledge base is a 8.8 MB SQLite file in `assets/`. That is the entire content
layer. Therefore:

- No "you are offline" banner. No sync status. No retry button. No spinner that waits for a
  network that does not exist.
- No "Update data" button. There is nothing to pull.
- No `ConnectivityManager`, no `WorkManager` sync job, no `HttpURLConnection`, no Retrofit, no
  analytics, no crash reporting, no ads (RULES 14, RULE 15).
- The only reason the APK is 9 MB rather than 4 MB is that the answers are on the phone. That
  is the design, and it is the selling point: a technician in a basement with no signal gets
  the same app.

If a future version wants remote content, that is a new product decision for the human, not a
feature to add in passing.

## 2. How a data update ships

1. The knowledge base improves; `tools/validate.py` reports 0 errors / 0 warnings; the app data
   build regenerates `kb.sqlite` with a higher `meta.db_version`.
2. Bump the APK `versionCode`/`versionName`.
3. Commit the new `app/src/main/assets/db/kb.sqlite` (one file, one commit).
4. CI builds; the human installs the new APK over the old one.
5. On launch the app compares the asset's `db_version` with the cached copy, and re-copies when
   they differ (`PHASE_2_DATA_LAYER.md` §3).

**`favourites` is deliberately keyed on `code_uid`** so it survives this swap intact
(`PHASE_6_FAVOURITES.md` §1). That is the entire "migration" story: there isn't one.

## 3. Storage discipline

- One database. Never a second copy, never a converted format, never JSON alongside SQLite.
  9 MB duplicated three times is 27 MB of someone's phone for nothing (RULE 19).
- `cacheDir` is correct for the working copy: the system may clear it, and the app recovers by
  re-copying from the asset on next launch. Do not put it in `filesDir`, which would only grow.
- Never write to `assets/` or `res/`.
- The data manifest records the SHA-256 of the shipped database. If the asset's hash does not
  match `app-pipeline/data-manifest.json`, the build is wrong — check it in CI.

## 4. Launch behaviour

Cold start must reach real content fast. Target: first frame < 100 ms, home with live counts
< 300 ms on a mid-range phone from a warm cache, and the app must be fully usable
immediately after install (no first-run sync, no "preparing database" gate).

- Copy the asset on first launch only; thereafter open the cache directly.
- `StrictMode` in debug to prove nothing touches disk on the main thread.
- Keep a single process-wide database handle; do not reopen per screen.

## 5. Battery and storage

- No background work at all, so no battery cost from the app beyond the screen.
- No `WorkManager`, no alarms, no notifications, no services. The app has no background
  presence whatsoever.
- Measured after install: ~9 MB app data. Report it plainly in the About section — technicians
  are asked about storage, and an honest number builds trust.

## 6. What to put in Settings

- **Data version** from `meta.db_version`, and `meta.generated_at` as the build date.
- **About**: what the app is, that it works offline, that it never asks for a permission or
  uploads anything, and the size of the data. Three short lines, no marketing tone.
- **Sources**: brand and model coverage, so a user can see what is and is not covered — and
  so "my model is missing" is answerable by pointing at a real gap.

## 7. Checks

- [ ] Airplane mode: every screen, search, favourite and detail works
- [ ] Fresh install → first launch usable with no network, ever
- [ ] Cache file deleted while the app is closed → re-copied on next launch, no error
- [ ] Replaced asset with a higher `db_version` → re-copied, favourites intact
- [ ] APK contains exactly one copy of the database
- [ ] Asset hash matches the data manifest
- [ ] `adb shell dumpsys package` shows **no** requested permissions
- [ ] No network permission, no `HttpURLConnection` anywhere in the source
- [ ] No battery entries in `dumpsys batterystats` after an hour of use
