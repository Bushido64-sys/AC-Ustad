# PROGRESS — AC Ustad app

**Read this first when resuming.** Last updated: 2026-09-29

> **Where we are:** Phases 1–4, 6 and the Phase 8 language toggle are built and have been through
> the §6 phone checks. The knowledge base and the 20-document build guide are finished.
> **A Settings screen and theme override are written but have never been compiled** — §7 item 2.

> **2026-09-29:** the human ran the §6 checks on a real phone and reported them passing, so the
> Phase 4, 6 and 8 screens move to ✅. That is a human result, not a CI result — CI still cannot
> open a screen (trap 15). It is recorded here because §1 defines ✅ as a phone check and the
> human is the only party that can perform one.

---

## 1. Phase status

**What ✅ means here: the screens in that phase have been opened on a real phone and the §6
checks for it have passed.** It does **not** mean the code compiles, that the tests pass, or
that CI is green — all three were true for Phase 4 while its screen was dead on the device. A
green gate proves the app builds; only a human proves a screen works. See trap 15.

| Phase | State | What actually works |
|---|---|---|
| 1 · Setup | ✅ phone-checked | Skeleton, bundled database, 7 IBM Plex fonts, light + dark themes, launcher icon, home screen with live counts |
| 2 · Data layer | ✅ phone-checked | 4 DAOs, immutable models, one repository, favourites writes, stale-id sweep |
| 3 · Browse | ✅ phone-checked | Home → brands → model lines → codes, with scoped search on every list |
| 4 · Code detail | ✅ phone-checked 2026-09-29 | Severity + meaning → numbered fix steps → causes → notes → source, with a working star. Was marked ✅ while being **completely dead on a phone** — see trap 15. The build was green throughout; only a human tapping a code found it. Fixed and now re-verified on the device |
| 5 · Search polish | 🟡 | The **data layer** is done and tested; the empty-search teaching state is not built |
| 6 · Saved screen | ✅ phone-checked 2026-09-29 | The Saved list, swipe-to-remove with Undo that restores the original position, and a bottom nav on the two top-level screens |
| 7 · Offline & updates | 🟡 | **Trap 16 fixed** — a new APK now always re-stages the database, keyed on `versionCode` and not file length. The offline *promises* all hold and four CI gates enforce them. The Settings *screen* that carries the §6 content is **coded, unbuilt** — §7 item 2 |
| 8 · Accessibility & Roman Urdu | 🟡 | The **EN/UR content toggle works, persists, and passed the §6 group-3 phone checks 2026-09-29** (it is partial-toggle-safe: one repository per process). **Settings and the theme are coded but never compiled** — see §7 item 2. Still open: the font-scale / TalkBack pass |
| 9 · Hardening & release | ⬜ | Release signing, the perf pass, the full release checklist |
| 11 · UI/UX | ⬜ **planned, not started** | **The app looks bare because `BorderedPanel` and `BorderedRow` use `Color.Transparent`** — `surface` and `surface_alt` are mapped in the theme and never used as a fill. The design exists and is measured; the implementation ignores it. Plan: `app-pipeline/guide/PHASE_11_UI_UX.md`. Fixes it with **zero new colours** |
| 10 · Monetisation | ⬜ **not started** | **Planning only.** Free-with-ads → trial → paid, all on Play. The plan is written: `app-pipeline/guide/PHASE_10_MONETISATION.md`. Nothing is built until a Play developer account exists |

## 1a. Starting a session

**One file, at the repository root: `AI-AGENT-PROMPTS.txt`.** It is a map, not a brief. Read
it and it tells you what else to read and what to do next.

The whole message the human has to send is:

    read AC-Ustad/AI-AGENT-PROMPTS.txt

Nothing is filled in. That file then routes a session with no memory of this conversation to
`§1`, `§4`, `§5` and `§7` of this file, to `app-pipeline/guide/AGENTS.md` and `RULES.md`, and
from there to whichever of the 20 build documents the task actually needs. If no task is named,
it defaults to the first item in §7.

This section used to hold a second copy of a longer prompt, and the two copies immediately
began to disagree. One copy, in one place, is the whole point.

Three rules for whoever picks this up, human or AI:

1. **The database wins.** If this file, the build guide and `kb.sqlite` disagree, the
   database is right - and say so instead of quietly working around it.
2. **Do not trust a number that has not been read.** Every count here was queried, but a data
   release can move them. `python3 app-pipeline/check_app_sql.py` re-checks 40 of them.
3. **Watch for the six failure modes this project actually produced:**
   - a column name written from memory instead of read from the schema;
   - **a claim described as "verified" that was only reasoned about.** Phase 4 sat in the table
     below marked done for days, with a green build and a green test suite, and the screen was
     dead on the device. A green build proves the app *compiles and its tests pass*. It does not
     prove the screen renders. Only a human tapping a code found this one;
   - a text-based check reporting a conclusion it could not see (the dead-code sweep, the
     self-matching secret scan, and `getValue`, an implicit operator that never appears in the
     source);
   - a duplicated instruction drifting from the thing it documents - which is why the prompt now
     lives in exactly one file;
   - **an exception swallowed into a user-facing claim.** `runCatching { }.getOrNull()` turned a
     cursor crash into "This code is not in the knowledge base", i.e. a fault became a fact about
     the data, and the message was actively misleading while looking entirely reasonable;
   - **an off-by-one column index.** `detailById` read index 20 on a 20-column cursor. Those
     fields are read by name now, so it cannot recur.

## 2. The daily loop

```bash
# 1. change code, then
git push origin main
# 2. wait for the green 'build app' check
# 3. open the run -> Artifacts -> download ac-ustad-debug (a .zip)
# 4. on the phone:
adb install -r app-debug.apk     # the -r keeps saved codes
# 5. walk the six checks in section 6
```

There is **no Android SDK on this machine and no emulator, ever.** A local build needs ~10 GB
and there is 1.7 GB free. CI builds; the phone tests.

## 3. Where the code lives

```
android/app/src/main/java/com/acustad/app/
  data/        KbDatabase, Io, CatalogDao, CodeDao, SearchDao, FavouritesDao, SearchInput
  model/       Models.kt — every read model, ContentLanguage, CategoryId, ScopedSeries
  repo/        KbRepository.kt — the UI's ONLY door to the database, and a
                PROCESS SINGLETON (get() only) so the content language reaches
                every screen. Persists the language in SharedPreferences.
                ToggleGuard.kt — drops a repeat star/unsave inside 400ms
  ui/
    AcUstadAppBar.kt      title + at most one action
    AcUstadBottomNav.kt   Browse / Saved, NavTab — shown on the top-level screens only
    AcUstadNavHost.kt     the whole graph, routes carry slugs only
    common/               BorderedPanel/Row, SeverityChip, SearchField, StarIcon, Severity,
                          hardShadow (the 3dp 3dp 0 offset bar)
    home/                 HomeScreen
    browse/               Brands, Series, Codes screens + view models
    detail/               CodeDetailScreen + view model
    saved/                SavedScreen + view model (one instance, shared with the bottom bar)
                          ContentLanguageToggle lives in common/, not here
  ui/theme/    Color/Type live in res/values/colors.xml — never hard-code a hex in Kotlin
```

## 4. Traps that will bite anyone

Each of these was found by the build failing, or by querying the real database. They are the
reason several comments in the code look defensive.

1. **`series.id` is unique only within a brand.** `inverter-split` is shared by **14** brands. A
   query that binds only `series_id` returns 200 rows instead of 41. Every scoped query binds
   `brand_id` too, and `ScopedSeries` exists so a DAO signature cannot forget it.
2. **`alias_norm` is stored UPPER-CASED.** 5,562 of 7,707 rows contain capitals. Lower-casing
   the query returns **0 rows** for `e1`, `e6`, `f4`. Use `SearchInput.canon()`.
3. **FTS terms must be quoted AND AND-joined.** Quoting prevents the crash (454 of 2,139 code
   strings throw unquoted). AND-joining prevents the phrase trap: `"inverter fault"` as one
   phrase returns **10** hits, as `"inverter" AND "fault"` returns **89**.
4. **`causes.idx` and `solutions.idx` are 0-based.** Verified 0..n-1 with no gaps. The number a
   technician reads is `index + 1` — `BilingualText.displayNumber` does that in one place. The
   guide said 1-based; it was wrong.
5. **`favourites` is `(code_id INTEGER PK, created_at TEXT)`.** Two columns. No `code_uid`, no
   `is_read`, no copied names. It is keyed on an id a data release can renumber, so
   `sweepStaleIds()` runs when the database is first opened.
6. **A model name cannot go in a route.** Real names contain `/` and brackets: *"Splits —
   Inverter & Fixed-Speed (shared platform)"*. Routes carry slugs; screens read names from the
   database. `URLEncoder` is not the fix — it turns a space into `+` that navigation never
   decodes back.
7. **`causes.idx`/`solutions.idx` are 0-based, and `StarBorder` only exists in
   `material-icons-extended`** (~10 MB for one glyph). The star is drawn instead.
8. **`getValue` is an implicit operator.** `val x by flow` needs that import even though the
   word never appears in the file. An automated unused-import sweep removed it once and broke
   five screens.
9. **AGP injects one permission nobody wrote**: `DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION`.
   The gate allows exactly that name and fails on any other.
10. **The saved list's own query needs the two-part series join, like every other one.** Three
   saved rows — Dawlance, EcoStar and Elios, all `series_id = 'inverter-split'` — come back as
   3 rows with 3 different model names. The one-part join returns **42** for the same three.
   Verified against the real database, not reasoned about.
11. **`DESIGN.md` §4.6 says tapping a saved row "marks it read".** It cannot: the shipped
   `favourites` table is `(code_id, created_at)` and there is no read state to set. The
   database wins, so a tap opens the code and does nothing else. No unread dot, no `is_read`,
   and `PHASE_6` §3 already says the same thing — the two documents disagree with each other
   and both agree with the database.
12. **One `KbRepository` per PROCESS, or the EN/UR toggle half-works.** This used to be the
   worst bug in the app, because it did not look like one: every view model built its own
   `KbRepository`, so each held a private `contentLanguage` flow. Flipping the toggle changed the
   one screen whose repository you happened to be on and nothing else — and because each held
   its own `SQLiteDatabase` handle, a star written from the detail screen was invisible to the
   Saved screen's list until something forced a re-read. `KbRepository.get(context)` is now the
   only way to obtain one and the constructor is private, so a second copy is not expressible.
   **If a new screen needs data, take the shared repository. Never construct one.**
13. **`Modifier.weight` needs a `RowScope` in scope, and a function call does not inherit one.**
   `AcUstadBottomNav.NavItem` was a plain top-level composable that took a `Modifier` and called
   `modifier.weight(1f)` in its own body. That does not compile: `weight` is declared inside
   `RowScope`, not on `Modifier`. It is now a `RowScope` extension. The trap is that the rest of
   the app uses the identical call **and compiles** — `CodesScreen` and `SavedRow` both put
   their `weight` inside an inner `Row { }` content lambda, which does carry the receiver. So the
   broken shape is indistinguishable from the working one unless you check where the `Row` is.
14. **The saved list's join needs `c.is_fault`, or a saved indicator is shown as a fault.** 569 of
   the 4,418 codes are indicators and parameters, and `dawlance/DF` is one of them with
   `severity = 'info'`. Without the column the Saved row renders a severity chip for it. It is
   selected last, so it cannot shift the indices the DAO already reads; the full index map is in
   `FavouritesDao`'s KDoc.
15. **Phase 4 was marked green and was not. The code-detail screen never worked.** `CodeDao.detailById`
   read `meaning_en` from index 9, `meaning_ur` from 10, and so on — every field after the
   summary was off by one — and finished at `brandId = c.getString(20)` on a **20-column** cursor,
   where the last valid index is 19. `getString(20)` throws. `CodeDetailViewModel` then wrapped the
   read in `runCatching { }.getOrNull()`, so the exception became `detail == null`, and the screen
   renders null as **"This code is not in the knowledge base."** Every code in the app was
   unreachable, and a crash presented itself as missing data. Found on a phone, not in CI: the
   build was green the whole time, because the test suite never opened a detail screen.
   Two separate faults, and either alone would have been survivable:
   - the twelve detail fields are now read **by column name**, so an off-by-one is impossible;
   - a failed read is now a **different state** from an absent code, so a thrown exception can
     never again be reported as a fact about the data.

16. **A network kill switch and a fully-offline app cannot both be real.** The app's headline
   promise is that it works in airplane mode; a version check needs a network. So any such switch
   **fails open**: an unreachable server means the app runs, always. A closed door is a failure
   for exactly the technician standing in front of a broken unit with no signal — and it enforces
   nothing against anyone determined, because airplane mode is a one-tap bypass. What *can* be
   enforced is Play's: content behind a Play Billing licence is checked by Google, server-side,
   and is genuinely unbypassable. Read `PHASE_10_MONETISATION.md` before proposing either one.
   Also: a sideloaded APK **cannot be revoked** once installed, whatever the app's code says.
17. **A length check answers "is this file damaged", not "is this file current". FIXED
   2026-09-29.** `KbDatabase.stageFile()` re-copied the asset only when
   `target.length() != asset.available()`. A data release whose `kb.sqlite` came out the same
   number of bytes as the one already in `cacheDir` was never picked up: the app updated, the
   new APK carried the corrected answers, and the technician kept reading the old ones —
   silently, with nothing in a log. The length check was good at its real job, catching a copy
   interrupted by a kill. It was simply answering the wrong question. `shouldRestage()` in
   `Staging.kt` now re-stages when the `versionCode` recorded in `cacheDir/kb.stamp` differs
   from the running APK's. The app cannot read `meta.kb_version` out of an asset without
   extracting 8.8 MB per launch, and does not need to: the data lives **inside** the APK, so new
   data means a new APK, and `PHASE_7` §2 step 2 already requires `versionCode` to be bumped.
   Eight tests, and the second one is the exact bug — same length, new version must copy.

18. **The palette was built and then thrown away by two lines.** `BorderedPanel` and
   `BorderedRow` both set `color = Color.Transparent`, so every card, every row and the meaning
   block sit on raw canvas. `surface` (#EFF6FB) and `surface_alt` (#DCEAF5) are mapped into the
   colour scheme and **used as a fill nowhere in the app**. This is why the app reads as a
   wireframe while `DESIGN.md` §2 describes a finished design. The whole fix is about forty
   lines and invents nothing — see `PHASE_11_UI_UX.md`. **The trap is general: a colour system
   that is defined, measured and documented is not the same as one that is used.**
19. **`ink_muted` on `surface_alt` is 4.15:1 — under AA.** In dark mode it is 6.93:1 and fine,
   which is exactly how this kind of thing survives a dark-mode screenshot review and fails on a
   cheap LCD in sunlight. Muted text may go on `surface` (4.67:1, only just) but **never on the
   raised surface**. Measured for Phase 11, not assumed.
20. **The 2dp `hairline` is 1.41:1 against the canvas and effectively does not exist in sun.**
   `DESIGN.md` §1 asks the bottom nav for one; `PHASE_8` §6 forbids thin text-coloured
   separators for meaningful grouping. **`PHASE_8` wins** — it is the document written for a
   cheap LCD in bright sun. Structural lines are 2dp `outline`. `hairline` survives only as the
   `info` chip border, where `DESIGN.md` §3 already accepts it because the *word* is the signal.

## 5. The gates, and what each one is for

| Gate | Protects |
|---|---|
| `verify data` / `tools/validate.py` | the knowledge base validates against the schema |
| `verify data` / `contentSha256` | a data change cannot ship without a rebuild. Byte-comparing `kb.sqlite` does **not** work: SQLite versions produce different file layouts for identical data |
| `verify data` / `check_app_sql.py` | **43 assertions** running the app's real SQL against the real database. The only way to test SQL, since `android.database.sqlite` is a stub off-device. Includes the detail query's **column order**, added after trap 15 |
| `build app` / compile + lint | 0 lint errors |
| `build app` / unit tests | **72** tests as of 2026-09-29 (58 + 14 for the theme round-trip and the two Settings formatters), including all 2,139 code strings and the FTS quoting. The 14 are **written, not yet run** — CI is the authority until the `build app` check goes green |
| `build app` / permissions | the app ships with nothing but AGP's own self-permission |
| `build app` / database hash | the APK cannot carry a stale database. On-device re-staging is a separate rule — see trap 16 |
| `build app` / APK size | catches a duplicated 9 MB database or an accidental image library |
| `build app` / compiler error lines | puts the compiler's own `e:` lines on the **check itself**, not only in a log. GitHub's log download endpoint needs admin rights, so without this step a compile failure is visible only as "exit code 1" and needs a personal access token to diagnose. Runs `if: failure()`, cannot weaken a gate, needs no secret |

`check_app_sql.py` imports the canonical rule from `build_kb.py` rather than restating it. Two
copies of that rule would drift, and a lower-case copy would quietly break every code search.

## 6. Test on the phone — the twenty-seven checks

**A green build does not get a phase marked ✅. This section does.** Every screen in a phase has
to be opened here, on a real phone, by a human, before the phase counts as done. Phase 4 sat in
§1 marked ✅ for days with all four CI gates green while its screen was dead on the device,
because no automated test ever opened it. Re-run these after **any** change that touches a
screen, not only when a phase closes.

**Group 1 — the six original checks (browse, detail, search):**

1. **Airplane mode on.** The app must be fully usable. If anything needs a network, it is a bug.
2. Home shows **AC 31 brands / 1,723 codes** and **Inverter 33 brands / 2,695 codes**. If these
   numbers are wrong, the database is not the validated one.
3. AC → **Hitachi** first in the list (93 codes, sorted by count desc).
4. Solar inverter → **Dawlance** → `inverter-split` → **41 codes**. Not 200. That row is the
   proof the two-part scoping works.
5. Tap a code: severity chip, meaning, **numbered** fix steps starting at 1, then causes, then
   source. No block with no data is rendered — `panasonic/…/H00` has no causes, so there is no
   causes section.
6. Star a code, force-stop, reopen, open it again: **the star is still filled.**

Then: dark mode, and search `e1` in lowercase inside a model — it must find `E1`.

**Group 2 — the six Saved-screen checks (Phase 6):**

7. Star a code, then open **Saved** from the bar: the row is there, and the tab's star is
   filled. Go back to Browse — the star stays filled.
8. Swipe a row left: it goes, a bar appears, **Undo** puts it back **in the same position**,
   not at the top. Swipe a short distance and let go: nothing happens.
9. Tap a row's star instead of swiping: same result, same Undo bar.
10. Tap the row itself: the code's detail screen opens, with the right brand and model.
11. Star three codes in a row fast. The order is newest first, and none of them is lost or
    duplicated.
12. Saved with nothing starred shows the one-line empty state and no bar.

**And one that guards the data, not the UI:** a saved **non-fault** code — an indicator or a
parameter — must show a muted rail and the word INDICATOR, never a severity word. `dawlance/DF`
is a real one: `is_fault = 0` with `severity = 'info'`.

**Group 3 — the EN/UR content toggle (Phase 8).** The failure this catches is a *partial*
toggle: it changing one screen and not the others, which is exactly what trap 12 describes and
what no automated test can see. **The toggle moved from Home to Settings on 2026-09-29** — tap
the **Settings** tab, not Home.

13. On **Settings**, tap **UR**. Go to Browse → AC → any brand → any model → the code list:
    **titles are Roman Urdu.** Tap a code: **meaning, fix steps and causes are Roman Urdu.** The
    severity word is too — STOP becomes BAND KARO.
14. **Brand names and model names stay English** through the whole journey. Growatt is Growatt.
    This is RULE 13 and it is the thing most likely to be quietly broken.
15. Open **Saved** while in UR: saved rows show the **Roman Urdu** title. This is the screen that
    the old per-screen repository would have missed.
16. Switch back to **EN** and walk the same path: everything returns to English.
17. **Force-stop and reopen the app.** Still in **UR**. A toggle that forgets on every launch is
    not a toggle.
18. Airplane mode, cold start, straight into a code: still in UR, and the app opens instantly
    with no visible language switch.

**Group 4 — Settings, and the theme (Phase 8, added 2026-09-29).** A new screen that no test
opens is exactly the situation §1's rule was written about, so it gets checks before it is
called done.

19. The bar has **three** tabs — Browse, Saved, Settings — and the Settings tab opens the
    Settings screen. Only Saved has an icon; Browse and Settings are text with the icon slot
    reserved, and all three labels sit on one baseline.
20. **Settings** shows the two controls, then a data block reading **Version 2026-09-26** and a
    build date of **2026-09-28**, then Sources, then About. The coverage line reads
    **62 brands, 320 model lines, 4418 codes**, and the About line states a size.
21. **Every number is read from the database.** Change nothing and the numbers must match the
    home screen's brand counts; a number that is a typed-in literal rather than a query is
    exactly the kind of claim this project has been wrong about before.
22. **Theme → Dark.** The whole app repaints immediately, including the already-visible Home and
    Saved screens — a partial re-theme means the value was not read from the shared repository.
23. **Theme → Light** while the *phone* is in dark mode. The app stays light: an explicit choice
    beats the system, and it stays light after a force-stop and reopen.
24. **Theme → System** and flip the phone's system theme. The app follows.
25. Pull a stored theme by setting Light, then **browse away and back**: Settings still reads
    Light, and the tab's selected state is right.
26. The Settings screen in **UR** is still **English in its labels** — "Theme", "System", "Data"
    do not change. Only code content changes. (RULE 13)
27. Airplane mode: Settings opens and shows the data block. Nothing on it needs a network.

## 7. Do these next, in this order

**Phase 7 is not a feature phase, and that is why it is not above — but it is not empty either.**
Most of it is a list of things **not to build**: the whole point is that the app never mentions
the network, and you satisfy that by writing no code at all. `DEPENDENCIES.md` §2 already
records the absence of every library Phase 7 §1 bans, and four of its nine §7 checks are
permanent CI gates that are green on every push (no permissions, no HTTP client, exactly one
database copy, asset hash matches the manifest). Those need a gate, not a phase, and they have
one.

**The one genuine defect it had is now fixed (trap 16). What remains:**

1. **Phase 11's first two steps: the surface hierarchy, then the detail screen.** They are about
   forty lines, they add no colours, and they are the difference between a wireframe and a
   finished app. Do these before the Settings screen — the Settings screen is a new surface and
   should be built on the corrected one, not on a transparent panel. `PHASE_11_UI_UX.md` has the
   order.
2. ~~**The Settings screen, and the third bottom-nav item with it.**~~ — **coded 2026-09-29,
   NOT yet built or phone-checked.** Written before item 1, which is the one ordering mistake in
   this list: the Settings screen is a new surface and `PHASE_11` is going to change what every
   surface looks like, so it may well need a second pass. What exists now:
   - `ui/settings/SettingsScreen.kt` + `SettingsViewModel.kt`, and `NavTab.SETTINGS` with
     `Routes.SETTINGS`. The bar shows on Home, Saved and Settings.
   - The **EN/UR toggle moved out of Home** into it. `HomeViewModel.language`/`setLanguage` went
     with it rather than being left behind as dead code.
   - The **theme** is `ThemeMode { SYSTEM, LIGHT, DARK }` on the shared repository, persisted,
     resolved once in `MainActivity` **above** the navigation graph — so the first frame is
     already correct and a configuration change cannot reset it. `systemFrom` defaults to SYSTEM,
     never to LIGHT.
   - **Phase 7 §6's content**: data version from `meta.kb_version`, the build date, Sources, and
     three About lines.
   - **§6 group 4 is nine new phone checks.** A screen no test opens is the exact situation this
     file's own rule exists to prevent, so it is not marked done until they are walked.
   - Still to verify: it has never been compiled. There is no SDK on this machine, so **push and
     read the compiler's `e:` lines off the `build app` check** before believing any of it.
   - Note the deliberate deviation: the language and theme rows share one `ChoiceGroup`
     composable, so they cannot drift apart, and no sixth icon was added for the Settings tab
     (RULE 11).
3. **Phase 5's teaching empty state.** When a code is typed on the *brands* screen the search
   correctly finds nothing; the screen must then explain why, in one line, with a way forward.
4. ~~**The remaining phone checks**~~ — **done 2026-09-29.** The human ran `PHASE_7` §7 (airplane
   mode, cache deleted while closed, a replaced asset with a higher `kb_version`, battery stats)
   and `PHASE_8` §8 (font scale 1.0 / 1.15 / 1.3, TalkBack reading a code end to end, longest
   content in both languages) on the device, and they pass. Re-run §6 after **any** change that
   touches a screen — it is not a one-time gate.
5. **Phase 9 release signing** — only when the feature set stops changing.
6. **Phase 10 monetisation** — and it is **blocked, not next**. It needs a Google Play developer
   account, and everything in it is written down in
   `app-pipeline/guide/PHASE_10_MONETISATION.md` already. Do not start it early and do not
   re-derive it; read the phase file. It supersedes RULE 14 and `PERMISSIONS.md`, and that
   supersession is deliberate and written down rather than a quiet workaround.

**One thing to fix in the next session regardless of phase:** `CI_CD.md` §1 documents a CI step
that fails the build if the APK contains an HTTP client class, and **that step does not exist in
`build-app.yml`**. It has never mattered because the app has no network code. The moment an ad
SDK lands, the guide claims a protection the build does not have. Build that gate *before* the
ads — it will pass the moment it is written.

## 8. Things deliberately not built yet

- **The bottom nav has 3 items.** `PHASE_6` §5 wants Browse / Saved / Settings, and the Settings
  screen arrived on 2026-09-29, so the tab arrived with it. `NavTab` is a closed enum, so adding
  it was one enum value and one branch. **Only Saved carries an icon** — Browse and Settings keep
  the icon slot reserved so the three labels share a baseline, and a gear would have been a sixth
  icon in an app whose whole budget is five (RULE 11).
- The bar is shown on **Home, Saved and Settings only**, and hidden while drilling into brands,
  models, codes and detail. Those screens are one continuous descent and the bar is a way out to
  a different top-level place, not a way down.
- **A Settings screen now exists** (2026-09-29, unbuilt and unphone-checked — see §7 item 2).
  It holds the EN/UR and theme switches and the data version, and nothing else. It has no search
  box, no "reset app", no cache controls and no About/links page beyond the three lines
  `PHASE_7` §6 asks for.
- **The theme override is wired but unverified.** `AcUstadTheme(dark = …)` already took a
  null/true/false; the work was the value on the shared repository and resolving it in
  `MainActivity` above the graph. `SYSTEM` is the default and resolves through
  `isSystemInDarkTheme()`; an unrecognised stored value falls back to `SYSTEM` and never to
  `LIGHT`, so a stale preferences file cannot override the phone's own setting.
- No search box on the Saved screen. Six rows; PHASE_6 §4 says no grouping and no folders.

## 9. Environment facts

- Repo: `git@github.com:Bushido64-sys/AC-Ustad.git`, branch `main`. **It is public, not
  private** — `GET /repos/…` answers unauthenticated. PROGRESS used to say private; the API is
  the authority and it says public. That matters for everything below: a token in this repo is
  a token on the open internet, and `verify data` will fail the push that tries it.
- The Android app lives in `android/` **inside** the same repository as the knowledge base.
- `verify data` and `build app` both run on every push to `main`.
- The artifact is 12 MB and is retained for 30 days.

### Reading a failed CI run

**Usually you do not need a token.** The `build app` workflow has a step that copies the
compiler's own `e:` lines out of `build.log` and prints them as check annotations, so the reason
a build broke is on the check itself in the Actions UI. Read it there first.

To read the rest of the log, the GitHub log-download endpoint needs **admin rights**, so an
unauthenticated agent cannot do it. The token for that lives **outside the repository**, at:

```
~/.config/ac-ustad/ci-token      # mode 600, one line, never committed
```

```bash
TOK=$(cat ~/.config/ac-ustad/ci-token)
curl -s -H "Authorization: Bearer $TOK" \
  "https://api.github.com/repos/Bushido64-sys/AC-Ustad/actions/runs/<run-id>/logs" -o runlog.zip
```

**The token must never be written into this repository**, and there are two independent reasons
rather than one: the `verify data` secret scan fails any push containing it
(`.github/workflows/verify-data.yml`), and the repo is public, so it would be in the open
internet and in git history permanently. If the token is ever pasted into a chat, a file or a
commit, **revoke it** (`Settings → Developer settings → Personal access tokens`) and write a new
one to the path above. It needs no scopes beyond read access to this one repo.
