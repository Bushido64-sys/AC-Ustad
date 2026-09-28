# TESTING.md — what must pass before the app is called done

**Testing this app is mostly about the data, not the widgets.** 4418 codes and 2139 distinct
code strings are the real surface area; the UI is seven screens.

---

## 1. Test layers

| Layer | Tool | What it protects |
|---|---|---|
| Unit | JUnit + `kotlinx-coroutines-test` | `normalise()`, `ftsQuery()`, severity mapping, language resolution, category mapping |
| Data | JUnit against the **real** `kb.sqlite` on the JVM (Robolectric or instrumentation) | every query returns what the UI expects |
| Instrumented | Compose UI tests on a physical device | navigation, star persistence, theming, font scale |
| Manual | the human, on a real phone | sunlight, gloves, real code on a real unit |

There is no mocking of the database in the data tests. A fake would only test the fake.

## 2. The data contract tests — the important ones

Assert the shipped numbers. If these fail, the build is wrong, not the test.

```kotlin
assertEquals(62,  db.one("SELECT COUNT(*) FROM brands"))
assertEquals(320, db.one("SELECT COUNT(*) FROM series"))
assertEquals(4418, db.one("SELECT COUNT(*) FROM codes"))
assertEquals(10450, db.one("SELECT COUNT(*) FROM causes"))
assertEquals(11776, db.one("SELECT COUNT(*) FROM solutions"))
assertEquals(7707, db.one("SELECT COUNT(*) FROM aliases"))
assertEquals(3849, db.one("SELECT COUNT(*) FROM codes WHERE is_fault = 1"))
assertEquals(569,  db.one("SELECT COUNT(*) FROM codes WHERE is_fault = 0"))
```

- Every `severity` value is one of the five; every `confidence` value is one of three; the
  severities total 4418; the confidences total 4418.
- **Both category lists union to 62 brands, and their code counts sum to 4418**
  (`PHASE_3_BROWSE.md` §3). This is the test that stops a brand from silently disappearing.
- Every `codes.series_id` resolves, every `codes.uid` is unique, and every
  `uid == brand/series/code`.
- **8 brands and 65 series** have `code_count = 0` — assert they exist and are queryable.
- `SUM(brands.code_count) = SUM(series.code_count) = COUNT(codes) = 4418`.
- `SELECT COUNT(*) FROM codes co JOIN series s ON s.id = co.series_id` = **7036**, not 4418 —
  `series.id` is not unique on its own. Assert the composite join returns 4418, so nobody
  ships the inflated version.
- `panasonic` / `H00` has no causes — assert it and assert the UI hides the block.
- 26 codes have a null `source_url` — assert none of them is rendered with a link.
- Every fault row has ≥2 causes and ≥2 solutions (averages 2.47 and 2.76). Every
  `solutions.idx` starts at 1 and is contiguous per `code_id`. **There is no `step_no` column.**
- 1715 of 2139 code strings are unique to one brand, 359 are shared — assert the ambiguity
  exists, so nobody ever "fixes" it by merging.

## 3. Search tests — the crash suite

This is the highest-value test set in the project.

- [ ] **Loop every one of the 2139 distinct code strings**: search it inside its own series.
      Assert **no exception** and ≥1 result. This is the test that would have caught the FTS
      bug before a user did.
- [ ] Every code string containing `;`, `+`, `-`, `*`, `(`, `)`, `"`, `'`, `/` → no crash.
- [ ] `ftsQuery("a\"b")` produces a valid quoted string.
- [ ] All 7707 `alias_norm` values resolve to their own code via the exact lookup.
- [ ] `E1`, `E3`, `E6`, `F4` inside a series → exactly 1 result each.
- [ ] **A code from brand X returns 0 results from brand Y** — the anti-global-search guard.
      Fail this test if someone ever removes the `series_id` filter.
- [ ] A description word ("compressor") in the free-text path returns hits, all inside the
      current series.
- [ ] Debounce: 5 keystrokes in 500 ms → 1 query.
- [ ] Empty query restores the full list.

## 4. UI tests

- [ ] Home shows counts matching the database.
- [ ] AC → brand → model → code → detail in 4 taps.
- [ ] Tap back from a filtered list: query and scroll position preserved.
- [ ] The brands screen search box never returns a code; typing `E6` shows the explanatory
      empty state.
- [ ] A 0-code brand shows the empty state and does not crash.
- [ ] Detail: sections appear in the order severity+meaning → fix steps → causes → notes →
      source; a section with no data is absent, not blank.
- [ ] Steps are numbered 1..n in order and not truncated.
- [ ] EN/UR toggle: content changes, **brand and series names do not**, and no new query
      fires (assert with a query counter).
- [ ] Star/unstar persists across a process restart.
- [ ] Dark and light themes: no unstyled default colours anywhere.
- [ ] Severity chip renders the correct word for all five values.

## 5. Accessibility and scale tests

- [ ] Font scale 1.0 / 1.15 / 1.3 and display size small/large: no clipped or overlapping text
      on every screen.
- [ ] TalkBack: reads a code end to end, chip **word** first, steps as separate nodes.
- [ ] Every tappable element ≥48dp, rows ≥56dp (assert with a layout inspection, not by eye).
- [ ] Contrast: assert the token pairs against the measured table in
      `PHASE_8_ACCESSIBILITY_URDU.md` §5. In particular assert white text is **never** placed
      on `#6FB4DE` (2.27:1) — make that a unit test on the colour-mapping function.
- [ ] Reduced-motion setting → no lost state change.

## 6. Performance tests

Budgets are in `PHASE_9_HARDENING_RELEASE.md` §1. Assert:

- [ ] exact alias lookup < 5 ms
- [ ] prefix lookup < 20 ms
- [ ] 106-row code list opens < 120 ms
- [ ] EN/UR toggle issues 0 queries
- [ ] StrictMode: no disk or network on the main thread (this also proves the no-network rule)

## 7. Integrity and anti-regression tests

- [ ] APK contains **no** `<uses-permission>` (merge the manifest and assert).
- [ ] APK contains **no** `INTERNET` permission and no HTTP client class
      (`okhttp3`, `retrofit2`, `HttpURLConnection`) — grep the release APK's class list.
- [ ] The asset's SHA-256 matches `app-pipeline/data-manifest.json`.
- [ ] The APK contains exactly one `kb.sqlite`.
- [ ] No `CO`, `gitignore`d: `.gradle/`, `build/`, IDE files are absent from the repo.
- [ ] `tools/validate.py` → 0 errors, 0 warnings.

## 8. The manual test that matters most

On a real phone, with the app installed from the APK, with a real unit in front of you:

1. Open the app in airplane mode. 2. Go to the brand and model of the machine you are looking
at. 3. Find the code it is showing. 4. Read the severity word. 5. Do the first fix step.
6. Star it. 7. Force-stop, reopen, find it in Saved.

If any step needs a second thought, the app has failed — regardless of what the tests say.
