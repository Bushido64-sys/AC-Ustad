# PHASE_9_HARDENING_RELEASE.md — performance, then ship

**Goal: the app is fast on a cheap phone, and every release is a green build with a tested
APK.**

---

## 1. Performance budgets

| Metric | Budget |
|---|---|
| Cold start → first frame | < 150 ms |
| Cold start → home with live counts | < 400 ms |
| Open a series (worst case, 106 codes) | < 120 ms |
| Open a code detail | < 80 ms |
| Exact alias lookup | < 5 ms (measured 0.2 ms) |
| Prefix alias lookup | < 20 ms (measured 2 ms) |
| Star/unstar | < 50 ms, visibly instant |
| EN/UR toggle | 0 queries, < 16 ms |
| Scroll | no frame over 16 ms in a 106-row list |

Profile before optimising. If a budget is missed, find out where the time actually goes and
write it down.

## 2. The wins that are actually available

- The brand and series tables are 382 rows total. **Load them once, filter in memory.** Do not
  page, do not re-query per keystroke. This alone makes search feel instant.
- Only the current series' codes are ever loaded (≤ 106 rows).
- Detail is one joined query for both languages — no second query on toggle.
- `codes.display` is pre-joined, so list rows need no per-row query.
- One process-wide database handle; open lazily, never per screen.
- `R.string` / `@Immutable` data classes; no reflection in the list path.

`StrictMode` + `Trace` in debug to keep the main thread clean. No ProGuard surprises: keep
proguard rules for Room-free raw SQLite (nothing needed) and for the font resources.

## 3. Memory

- Full brand + series + one series' codes ≈ well under 2 MB of objects.
- Never hold the whole `codes` table (4418 rows) plus all 11776 solutions in memory at once.
- Release build: no `Log` left in hot paths, `isLoggable` guards, R8 enabled with
  `minifyEnabled true` and no `-dontoptimize`.

## 4. Stability

- Every screen handles loading, empty and error (`RULES.md` RULE 17). Especially empty: 8
  brands and 8 series have 0 codes, and 569 rows are non-fault indicators.
- No `!!`, no `lateinit` on a database field, no `Cursor` left open, no leaked coroutine scope.
- A corrupt cache file must recover by re-copying, once, then fail visibly
  (`PHASE_2_DATA_LAYER.md` §3).
- If the asset is missing, fail loudly at launch rather than showing a permanently empty app.

## 5. The pre-release checklist

Run this list every release, in order. An unchecked box is not shipped.

**Data**
- [ ] `.venv/bin/python tools/validate.py` → 0 errors, 0 warnings
- [ ] `python3 app-pipeline/build_kb.py` → rebuilds cleanly, no diff
- [ ] Asset hash matches `app-pipeline/data-manifest.json`
- [ ] `SELECT COUNT(*)` = 62 brands / 320 series / 4418 codes / 3849 fault rows

**Build**
- [ ] `./gradlew clean assembleDebug` green from a clean checkout
- [ ] `./gradlew lint test` green, zero lint errors
- [ ] `versionCode` incremented
- [ ] Release APK installs **over** the previous build with no uninstall

**Behaviour on a physical device** (no emulator, ever)
- [ ] Install → home shows real counts
- [ ] AC → brand → model → code → fix steps, in 4 taps
- [ ] Search `E6` on a series: exactly that series' E6; 0 results from another brand
- [ ] All 2139 code strings searched without a crash (`TESTING.md`)
- [ ] Favourite survives force-stop and a database re-copy
- [ ] EN/UR toggle on a code, brand names still English
- [ ] Airplane mode: everything works
- [ ] Dark mode: every screen, no white surfaces, no pure black
- [ ] Font scale 1.3: no clipping on the longest content
- [ ] `dumpsys package` shows **no permissions**
- [ ] No network traffic (watch `dumpsys netstats` or airplane mode)

**Design**
- [ ] No purple, no gradient, no pill, no blur, no soft shadow on cards
- [ ] Exactly 5 icons in the whole app
- [ ] Every severity chip shows its word
- [ ] No white text on `#6FB4DE`
- [ ] The build is not the generic-AI look (RULE 10 read once more)

## 6. Release mechanics

- CI builds the APK; the human downloads the artifact and installs it. No Play Store in scope.
- One commit per phase, message per RULE 20, `PROGRESS.md` updated in the same change.
- Tag releases `v1.0.0`.
- Keep the data commit separate from code commits, so a data-only update is one reviewable
  change.

## 7. After release

- Watch for crash reports the human describes in plain words — there is no crash reporting
  service in the app (RULE 14), so the report *is* the bug tracker.
- Re-run `TESTING.md` after any data update: a code's severity or steps may have changed.
- If a model is missing, add it to the knowledge base, validate, rebuild the data, bump the
  version. Do **not** patch around it in app code.
