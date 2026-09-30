# PROGRESS — AC Ustad app

**Read this first when resuming.** Last updated: 2026-09-30

> **Where we are:** Phases 1–8 and 11 are built and phone-checked. The knowledge base and the
> 20-document build guide are finished. Phase 11 is complete as a build — steps 1–6 of its 7,
> the seventh (per-brand source list) closed by decision — and its §6 group 5 has been run on a
> phone.
>
> **Nothing is half-finished and nothing is in flight.** `build app` and `verify data` are green
> on `main`, and the working tree is clean. (A commit hash is deliberately not written here:
> the last time one was, it was stale by two commits within a day.)
>
> **Next in §7 is Phase 9** — release signing, the performance pass and the pre-release
> checklist. Phase 10 stays blocked on a Play developer account.

> **2026-09-30:** the human reported §6 group 5 (28–37) passing on the phone, so Phase 11 moves
> to ✅. Then a source read — not a phone — found that all three `Text`s inside `RaisedPanel`
> inherited `onSurfaceVariant` = `ink_muted`: **4.15:1 on the meaning block in light mode**, so
> check 34 could not have been true on the build that was tested. `SeriesScreen`'s KDoc claimed
> the notes were full ink; the code never said so — the same failure as trap 15, a claim
> described as verified that was only reasoned about. It is now trap 24, fixed **in the
> component** where a call site cannot forget it, and pinned by `ContainerContentColorTest`.
> **Check 34 needs re-reading on the build that carries the fix** — the meaning block and the
> source line on a code detail, and the brand notes on a model screen, in light mode. One line
> of content colour, and exactly the kind of judgement no gate in this project can make.

> **2026-09-30 (later) — free-text search reaches descriptions.** The report was *"air leakage
> finds nothing"*: `code_fts` indexes **titles only**, so the phrase returned **0** app-wide
> while it sits in 12 fix steps, and `PHASE_5_SEARCH.md` §1 had been promising free text that
> searches "that series' descriptions" for months. It is trap 25. Fixed **app-side** — a third,
> `series_id` + `brand_id`-scoped step in `SearchDao.searchCodes`, run only when the input is
> not a code — **not** by reindexing, which would move the `kb.sqlite` bytes the APK hash check
> verifies. On the **brands** screen the same words now teach that fault words are searched
> inside a model instead of showing a dead list. **Needs a human:** inside Panasonic H/F,
> `air leakage` must return F91 and F97; on the brands screen the same words must show the
> teaching line. CI cannot open either screen.

> **2026-09-30 (still) — and it had to be visible to believe.** The same technician then said
> they had searched the **codes** box and read the feature as *not implemented there*. The
> search was running on that box all along (`SearchDao.searchCodes`, step three — the same
> scoped description scan); what left no trace was *which step returned nothing*, so a bare
> "No code matches" read as nothing having happened. The fix is not more search, it is the
> brands screen's habit of explaining itself: a **fault description** (the `isDescription`
> carve-out — multi-word, no digit) that finds nothing in a model now carries the scope line,
> *"Only <model> is searched — including what its codes mean, what causes them and how to fix
> them"*. A code still gets the headline alone. Pinned by `CodesEmptyStateTest`, which reads
> `CodesScreen.kt` because no test here can open a screen (trap 15). The phone checks for it
> moved to the note below, once the search learned to answer them with rows.

> **2026-09-30 (later still) — and then he wanted the words, not the explanation.** The
> sharpened report: *"i want search result with 'air leakage' in there title — titles words
> can be able to search and come up in results"*. Measured on the shipped database **no title
> in the knowledge base holds both `air` and `leakage`** (0 of 4,418), so step two's AND
> answered zero in all 320 model lines while *Refrigerant leakage detection* and *Anti-Cold
> Air Feature On* each hold one word of it. `searchCodes` gained a **fourth step**: the same
> index OR-joined (`SearchInput.ftsQueryAny`), asked only when the three precise steps have
> failed, never for a single word (there is nothing to loosen), never for a code that exists
> somewhere else (`isKnownCode` still sits ahead of it). **Needs a human:** Dawlance → Splits →
> `air leakage` = **2 rows (CF, E4)** where every build until now said 0; Panasonic → Modern
> H/F → still **F91 and F97** with **no** detail line, because the precise step wins; and
> Dawlance → Splits → `water pump` = **0 rows plus the detail line**, the only shape that
> empty state can still take.

> **2026-09-30 (last) — a word search that answered nothing was the index, not the data.**
> The report: *"i search 'indoor' in Hitachi's air365 MAX / Max Pro model's code list page and
> it doesn't appear — the 01 code which has 'indoor' in its title; tested others with their
> title words too but never worked."* The row was there all along: `01` is titled *Indoor-unit
> float-switch protection activated*, and **31 of that model's 65 codes** say "indoor"
> somewhere — the LIKE path returns all 31 in about 3 ms locally, index or no index. What broke
> was the **order** of `searchCodes` and the `catch` that was not there — FTS5 is a compile-time
> option of SQLite and Android's system
> build does not promise it, so `code_fts MATCH` throwing took the whole search down with it,
> *including the `LIKE` step that never needed an index*. `CodesViewModel` then drew the
> failure as an ordinary empty list. It is trap 26, fixed in the DAO (catch, log, fall through)
> and in the ViewModel (log instead of swallow). **Needs a human:** Hitachi → SET FREE air365
> Max / Max Pro → `indoor` must show **31 rows with `01` among them**; the same word in any
> other model must return its titles rather than nothing; and if the index really is missing on
> that phone, `adb logcat -s SearchDao` shows the warning while search still works.

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
| 5 · Search polish | ✅ phone-checked 2026-09-29 | The **teaching empty state** (2026-09-29): a code typed on the brands screen explains the scoping rule with a **measured** brand count, and offers a way forward. The count is a query — `16` is written nowhere in the app. **Added 2026-09-30, not yet on a phone:** free text inside a model now also searches that model's descriptions (trap 25), and the brands screen teaches fault words — both are in the note at the top and in §6's group 1 |
| 6 · Saved screen | ✅ phone-checked 2026-09-29 | The Saved list, swipe-to-remove with Undo that restores the original position, and a bottom nav on the two top-level screens |
| 7 · Offline & updates | ✅ | **Trap 16 fixed** — a new APK always re-stages the database, keyed on `versionCode` and not file length. The offline *promises* all hold and **five** CI gates now enforce them, including the network/ads gate that used to be documented and not written (trap 23) |
| 8 · Accessibility & Roman Urdu | ✅ phone-checked 2026-09-29 | The EN/UR toggle works, persists and is partial-toggle-safe. **Settings + the theme override are built and passed the §6 group-4 checks**, including the trap-22 Light-mode fix. Still open: the font-scale / TalkBack pass |
| 9 · Hardening & release | ⬜ | Release signing, the perf pass, the full release checklist |
| 11 · UI/UX | ✅ **phone-checked 2026-09-30** | **Built 2026-09-29, steps 1–6 of the guide's 7; §6 group 5 (28–37) reported passing on the phone 2026-09-30, with check 34 to be re-read on the build carrying trap 24 — see the note at the top.** `BorderedPanel` and `BorderedRow` were passing `Color.Transparent`; both now fill with `surface`, and the new `RaisedPanel` (level 2, `surfaceVariant`) is on the detail screen's meaning and source line where `DESIGN.md` §4.5 already said *raised*. `R.dimen` went from **zero uses to four**, so the token file is load-bearing rather than decorative. **Zero new colours.** **Also built in the same pass, all from `PHASE_11_UI_UX.md`:**
   - **The star moved into the app bar** (§6 #1, and the one the guide calls important).
     `AcUstadAppBar` had **no action slot at all**; it has exactly one now, and the star is
     extracted to a shared `StarToggle` so the body and the bar cannot disagree. It sat in the
     content body, which is precisely where a thumb is when the phone is held in one hand.
   - **The nav's top line is `outline` at 2dp, not `hairline`** (§2.2). Measured 1.41:1 light and
     1.47:1 dark against a 3.0 bar — a structural line that does not exist in sunlight.
   - **`SeriesScreen`'s brand notes are raised** (§3.4), and their text is full ink — but that
     was a **reasoned-about** claim until 2026-09-30: the `Text` named no colour, so it inherited
     `onSurfaceVariant` = `ink_muted`, which is 4.15:1 on `surface_alt` in light mode (6.93:1 in
     dark, so a dark-mode review would never have caught it). Same for the detail screen's meaning
     and source line. It is true now because `RaisedPanel` passes `contentColor = onSurface` for
     every child. **See trap 24.**
   - **One press animation, 120ms** (§5): an alpha dip to 0.88 on tappable panels and rows, with
     the Material ripple suppressed because the dip *is* the specified press feedback. An alpha
     dip and not a scale, because a scale would expose a sliver of canvas at the card's edge.
   - **Back keeps your place** (§6 #2): `rememberSaveable` list state, keyed on the route
     argument, on all three browse lists. `remember` would lose the position at exactly the
     moment the app returns, because a popped back stack entry is destroyed.
   - **"Open E6" on an exact single match** (§6 #3, `DESIGN.md` §4.4). The one primary action in
     the app, so the only other `hardShadow`. On an exact match only — a prefix match must never
     offer to open something.
   - `R.dimen` uses: **0 → 4** (`border_width`, `radius_card`, `row_min`, `radius_chip`; the
     count was written here as 5 and is 4). **Carries three live constraints now: (a) the theme
     override is real and must stay real — no colour may go back behind a `values-night`
     qualifier (trap 22), (b) the Settings screen is a third surface and gets the same pass as
     the rest, and (c) `RaisedPanel` must keep stating `contentColor = onSurface`, because the
     inherited default is 4.15:1 (trap 24), pinned by `ContainerContentColorTest`** |
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
   release can move them. `python3 app-pipeline/check_app_sql.py` re-checks 75 of them.
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

Each of these was found by the build failing, by querying the real database, or — trap 24 — by
reading the source against a rule a document had already written. They are the
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
21. **`Modifier.align` is a `ColumnScope` extension, and a composable's content lambda is not a
    `ColumnScope`.** Trap 13, a second time and a different method. `PanelColumn` is an ordinary
    composable that opens a `Column` *inside itself*, so a lambda passed to it — `PanelColumn {
    TextButton(Modifier.align(Alignment.Start)) }` — has no `ColumnScope` receiver in scope and
    `align` does not resolve. `HomeScreen`'s failed block makes the identical call and compiles,
    because that one sits **directly** inside a `Column { }`. So the working and broken shapes are
    again indistinguishable unless you check which one opened the Column. This is now a
    **compile** error rather than the silent off-by-one of trap 13, which is the one good thing
    about it: CI caught it on the first push and the fix was one `Row`.
    **The general rule: a scope-provided modifier only resolves where that scope is the receiver.
    An ordinary composable that wraps a layout does not pass its scope on to its content.**
22. **A night-qualified `values-night/` colour folder cannot be overridden, and it fails looking
    exactly like an override that was forgotten to wire up.** Reported on 2026-09-29 as "the light
    mode isn't working". It was not a wiring bug at all: the dark palette lived in
    `res/values-night/colors.xml`, and Android picks `values/` versus `values-night/` **from the
    phone's night setting**, with no way to know that Settings says Light. So `ThemeMode.LIGHT`
    did switch to `lightColorScheme(...)` — and every value inside it still came from the night
    folder: canvas `#0B1116` on a "light" background. The user saw a control that did nothing.
    **The trap is general: a resource qualifier is a *system* setting, not an app setting.** The
    moment the app grows a user-facing control that disagrees with the system, anything read
    through a qualifier silently stops responding. Fixed by moving the dark palette to
    `res/values/colors_dark.xml` under the guide's own token names and selecting in code
    (`Theme.kt`, and `ustadColor` for `Severity.kt`); `values-night/` now exists for the platform
    window background alone, because that really is painted before any Compose code runs. Five
    assertions in `PaletteContractTest` hold the arrangement, including *"no `values-night`
    colour file may exist"*. **`PHASE_11` MUST NOT put a colour back behind a qualifier** — see
    §1's Phase 11 row.
23. **A class name in a dex is `Lpkg/Class;` and the trailing semicolon is part of the token.**
    Written without it, `Ljava/net/Socket` also matches `Ljava/net/SocketException;` and
    `Ljava/net/SocketTimeoutException;` — which is a very common *transitive* reference in
    libraries the app never calls. The first version of the network gate failed the build for
    exactly this reason, and nothing in the app had changed. The general trap is worse than the
    bug: **a gate that can fail for a reason unrelated to what it is checking will be disabled**,
    and a disabled gate is worse than no gate, because the guide still claims it exists. Two
    rules came out of it, and both are now how the step is written:
    - **match the full descriptor**, semicolon included, so a prefix cannot match a longer name;
    - **print what was matched as a check annotation, not to the log** — a log an agent cannot
      read is the same as no diagnosis, and guessing which library matched would have been this
      project's own "a check reporting a conclusion it could not see" failure. The hard-fail
      set is now only libraries whose mere presence is a deliberate decision, and the platform
      types that a library can reference without the app calling them are report-only.
    **Worth stating plainly, because it is what actually enforces RULE 14: the *permissions*
    gate is the real protection.** Zero declared permissions means zero network, since
    `INTERNET` is a normal permission and cannot be had for free. The class gate is the early
    warning that a library is arriving which brings its own networking.

24. **A `Surface` picks its text colour from its own fill, so a component that does not say it
    hands every child the one colour the design forbids.** `RaisedPanel` filled with
    `surfaceVariant` passed no `contentColor`, so Material3 derived
    `contentColorFor(surfaceVariant)` = `onSurfaceVariant` = **`ink_muted`**, and every `Text`
    inside it that named no colour rendered **4.15:1 in light mode** — under the 4.5 AA bar, on
    exactly what `PHASE_11` §2.1 and check 34 protect: the detail screen's **meaning** (the most
    important text in the app), its **source line**, and `SeriesScreen`'s **brand notes**. Dark
    mode is 6.93:1 and passes, so a dark-mode review cannot see it; the build is green; no test
    opens the screen. **What makes it a trap rather than a bug:** the `SeriesScreen` KDoc said
    the notes were *"full ink, not muted"* and this file said the same — a claim described as
    verified that was only reasoned about, the same failure as trap 15, this time in a KDoc that
    read like a fix. Fixed 2026-09-30 **in the component**: `contentColor =
    MaterialTheme.colorScheme.onSurface` on all three containers, so a call site cannot forget
    it, plus `ContainerContentColorTest`, which reads the source and fails if the line goes.
    **The general rule: a default that the design forbids must be overridden where the container
    is defined, not remembered at every use — and a rule written only in a KDoc is a rule that
    has not been implemented.**

25. **Free-text search reads titles only, while the guide promises the descriptions.** `code_fts`
    holds exactly three columns — `code_norm`, `aliases`, `titles` — and never `meaning`,
    `causes` or `solutions`, so `"air" AND "leakage"` returned **0** app-wide while the phrase
    sits in 12 fix steps. `PHASE_5_SEARCH.md` §1 had been promising free text that searches
    "that series' descriptions" for months, so the report the user filed (*"air leakage finds
    nothing"*) was correct and the search really was broken. **What makes it a trap rather than
    a bug:** the obvious fix is to reindex `code_fts` with the other columns, which changes
    `kb.sqlite` and therefore the `data-manifest.json` sha256 that `build-app.yml` verifies —
    a data release nobody asked for — and `bm25(code_fts, 10.0, 1.0, 3.0)`, whose weights were
    written for title/meaning/solution, would silently start weighing different columns.
    Fixed 2026-09-30 **app-side instead**: a third step in `SearchDao.searchCodes`, scoped by
    `series_id` + `brand_id` (RULE 3), over the same haystack a person reads, and reached only
    when the input is not a code — so `E6` still never touches it. Pinned by
    `check_app_sql.py`, which now *reads* `DESCRIPTION_SQL` out of `SearchDao.kt` rather than
    copying it, and by `SearchDaoContractTest`. **The general rule: when the data contract and
    the promise disagree, change the code that reads the data — not the data.**

26. **A search box that answers every code and no word is an index failure, not a data gap.**
    Reported 2026-09-30: `indoor` in Hitachi's SET FREE air365 Max / Max Pro showed nothing,
    although code `01` is titled *Indoor-unit float-switch protection activated* and **31 of
    that model's 65 codes** say "indoor" somewhere. The data was fine. FTS5 is a
    **compile-time option of SQLite** and Android's system build does not promise it, so
    `code_fts MATCH` can throw on a phone — and `searchCodes` runs its steps in order, so one
    throw aborted the **whole** search, including `codesDescription`, which is plain `LIKE` and
    never needed an index. Two things hid it: the throw itself, and
    `runCatching {}.getOrDefault(emptyList())` in `CodesViewModel`, which renders a failure as
    an honest-looking "No code matches". Fixed app-side: `codesText` catches `SQLException`,
    logs `Log.w` and returns an empty list so the LIKE step still runs; the ViewModel now logs
    instead of swallowing. Pinned by `check_app_sql.py` (31 rows through the LIKE path alone,
    `01` among them) and a source-reading test. **The general rule: a step that is an
    optimisation must never be able to fail the steps after it.**

## 5. The gates, and what each one is for

| Gate | Protects |
|---|---|
| `verify data` / `tools/validate.py` | the knowledge base validates against the schema |
| `verify data` / `contentSha256` | a data change cannot ship without a rebuild. Byte-comparing `kb.sqlite` does **not** work: SQLite versions produce different file layouts for identical data |
| `verify data` / `check_app_sql.py` | **77 assertions** running the app's real SQL against the real database. The only way to test SQL, since `android.database.sqlite` is a stub off-device. Includes the detail query's **column order** (after trap 15), the teaching-state count and its `code_norm` guard, the description search (trap 25) — whose SQL is **read out of `SearchDao.kt`**, not retyped, so the checker and the app cannot drift — and the LIKE-only fallback for a device without FTS5 (trap 26) |
| `build app` / compile + lint | 0 lint errors |
| `build app` / unit tests | **113** tests (20 search input, 17 teaching-state, 15 models, 9 schema, 8 staging, **9 search-SQL contract**, 7 theme, 6 language, 6 toggle-guard, 5 palette-contract, 4 Settings formatters, **2 row-label**, **2 codes-empty-state**, 3 content-colour — counted off the `@Test` annotations, 2026-09-30), including all 2,139 code strings and the FTS quoting. Note what this does and does not prove: every one of them runs off-device, and **not one opens a screen**; the decision-function and SQL assertions, and every source-reading test — the 3 added with trap 24, the 9 in `SearchDaoContractTest` (traps 25 and 26), the 2 in `RowCountLabelTest` and the 2 in `CodesEmptyStateTest` — all still cannot see a pixel |
| `build app` / permissions | the app ships with nothing but AGP's own self-permission. **This is what actually enforces RULE 14** — zero permissions means zero network, since `INTERNET` is a normal permission |
| `build app` / database hash | the APK cannot carry a stale database. On-device re-staging is a separate rule — see trap 16 |
| `build app` / APK size | catches a duplicated 9 MB database or an accidental image library |
| `build app` / no network, ads or analytics library | **Added 2026-09-29** — the gate `CI_CD.md` §1 had documented for months without existing. Reads every dex in the APK. Written *before* any ad SDK, because a gate added after the code it forbids has to fight the code. See trap 23 |
| `build app` / compiler error lines | puts the compiler's own `e:` lines on the **check itself**, not only in a log. GitHub's log download endpoint needs admin rights, so without this step a compile failure is visible only as "exit code 1" and needs a personal access token to diagnose. Runs `if: failure()`, cannot weaken a gate, needs no secret |

`check_app_sql.py` imports the canonical rule from `build_kb.py` rather than restating it. Two
copies of that rule would drift, and a lower-case copy would quietly break every code search.

## 6. Test on the phone — the thirty-seven checks

**A green build does not get a phase marked ✅. This section does.** Every screen in a phase has
to be opened here, on a real phone, by a human, before the phase counts as done. Phase 4 sat in
§1 marked ✅ for days with all four CI gates green while its screen was dead on the device,
because no automated test ever opened it. Re-run these after **any** change that touches a
screen, not only when a phase closes.

**Group 1 — the six original checks (browse, detail, search):**

1. **Airplane mode on.** The app must be fully usable. If anything needs a network, it is a bug.
2. Home shows **AC 31 brands / 1,723 codes** and **Inverter 33 brands / 2,695 codes**. If these
   numbers are wrong, the database is not the validated one.
3. AC → **Hitachi** first in the list. It sorts by **code** count desc (206 codes), so on a
   brand row it reads **7 models** — brand rows count models, model rows count codes (swapped
   2026-09-30). The number under the name must be a model count on this screen and a code
   count on the next one, or they are back to front.
4. Solar inverter → **Dawlance** → `inverter-split` → **41 codes**. Not 200, and not "41
   models", which is what this row said until 2026-09-30. That row is the proof the two-part
   scoping works.
5. Tap a code: severity chip, meaning, **numbered** fix steps starting at 1, then causes, then
   source. No block with no data is rendered — `panasonic/…/H00` has no causes, so there is no
   causes section.
6. Star a code, force-stop, reopen, open it again: **the star is still filled.**

Then: dark mode, and search `e1` in lowercase inside a model — it must find `E1`.

**Added to group 1 on 2026-09-30 (trap 25) — the words a technician actually types:**

`air leakage` is a *fault*, not a code, so it skips the code lookup and must reach the
descriptions. Two screens, two different right answers, and neither is a number CI can check
without opening a screen.

- Inside **AC → Panasonic → Modern H/F self-diagnosis**, search `air leakage`: **F91 and F97**
  come back (2 rows). Nothing else does.
- Inside **Solar inverter → FoxESS → H1(G2)/AC1(G2)**, the same words: **Iso Fault** and
  **Res Cur HW Fault** (2 rows).
- On the **brands** screen — the one that only searches brand names — type `air leakage`: it
  must say *"air leakage" describes a fault, not a brand* and point at a model, **not** show an
  empty list. Type `E6` instead: the teaching line must stay silent and the scoping
  explanation must appear as before, because `E6` is a real code.
- **A model that does *not* use those words** — **Dawlance → Splits — Inverter & Fixed-Speed
  (shared platform)**, search `water pump`: **0 rows**, and the empty state must carry the
  second line, *"Only … is searched — including what its codes mean, what causes them and how
  to fix them"*, not the bare headline. That second line is what tells a technician the
  description search exists in this box. A one-word code typed the same way must leave the
  empty state to the headline alone.
- **The loose step, still in that model:** search `air leakage` → **2 rows, `CF` and `E4`**
  (*Anti-Cold Air Feature On*, *Refrigerant Leakage*), because no title in the whole database
  holds both words and the fourth step shows the titles that hold one. One screen up in
  **Panasonic → Modern H/F** the same words must still be **F91 and F97 only** — the precise
  step wins, the loose one must not push extra rows in front of it.

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

> **All nine passed 2026-09-29**, on build `0b1b4c0`, reported by the human. That includes the
> Light-mode fix for trap 22, which was found by this very round: the first attempt at group 4
> came back "the light mode isn't working", and the cause was a `values-night/` colour folder
> that no automated test in this project could have seen. Two of the nine failed on the first
> pass and were fixed; the rest passed first time. **A human result, not a CI result.**

19. The bar has **three** tabs — Browse, Saved, Settings — and the Settings tab opens the
    Settings screen. Only Saved has an icon; Browse and Settings are text with the icon slot
    reserved, and all three labels sit on one baseline.
20. **Settings** shows, in order: Language, Theme, a data block reading **Version 2026-09-26** and
    a build date of **2026-09-28**, then About. About introduces the app and then says what it
    does for the reader. **Revised 2026-09-29:** the Sources panel and the database-size line
    were removed on the user's instruction, so this check no longer expects them — see §8. What
    must still be true is that the data block survives the removal, and that About contains no
    claim about size, coverage or brand count.
21. **The data version is read from the database, not typed in.** It must match the version Home
    shows in its subtitle, and both must match `meta.kb_version`. A number that is a literal in
    `strings.xml` rather than a query is exactly the kind of claim this project has been wrong
    about before — and the size line that used to sit below it was removed precisely because it
    was a second number to keep honest.
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

**Group 5 — Phase 11, the visual pass. Reported passing on the phone 2026-09-30, with check 34
outstanding on the build carrying trap 24 — see the note at the top.**
Every check here is a **visual judgement**, and not one automated test in this project can make
one. The surface hierarchy is the phase; if the three levels do not read, stop rather than
building on them.

28. **The three levels are visibly different** — card, raised block and canvas, at arm's length.
    This is the single check the whole phase rests on.
29. **The star is in the app bar** on the code-detail screen, reachable one-handed, and the thumb
    does not cover the code. It was in the content body until `282b035`.
30. **A panel press is a 120ms dip and nothing else** — no bounce, no ripple, no shadow moving.
31. **Back from a model returns to the same scroll position with the search text intact** on all
    three browse lists. A jump to the top means `rememberSaveable` is not saving.
32. **An exact code match in a model offers one blue "Open E6" row** above the single result. A
    prefix match must **not** offer it.
33. **The nav's top line is visible in sunlight.** Walk outside. It is 2dp ink now, not the
    1.41:1 hairline that did not exist.
34. **No muted text on any raised block**, light mode especially — `ink_muted` on `surface_alt`
    is 4.15:1. The SeriesScreen brand notes are the one place this was fixed.
35. **Dark mode, checked and not assumed**, on the detail screen: meaning block, fix steps,
    causes, source line. A surface that reads in light can vanish in dark.
36. **The meaning block is the most important thing on the detail screen** — that was the point
    of making it raised.
37. **Nothing else moved.** The §6 checks 1–27 still pass, and the six original checks are
    re-run in full.

## 7. Do these next, in this order

> **Next is item 5 — Phase 9.** Items 1–4 are all closed (item 1 closed 2026-09-30), so the
> first *open* item in this list is the one that ships. Item 6 stays blocked on a Play account.

**Phase 7 is not a feature phase, and that is why it is not above — but it is not empty either.**
Most of it is a list of things **not to build**: the whole point is that the app never mentions
the network, and you satisfy that by writing no code at all. `DEPENDENCIES.md` §2 already
records the absence of every library Phase 7 §1 bans, and four of its nine §7 checks are
permanent CI gates that are green on every push (no permissions, no HTTP client, exactly one
database copy, asset hash matches the manifest). Those need a gate, not a phase, and they have
one.

**The one genuine defect it had is now fixed (trap 16). What remains:**

1. ~~**Phase 11 is built through §6 and is waiting on a human.**~~ — **done 2026-09-30.** §6
   group 5 (28–37) was reported passing on the phone, so Phase 11 is ✅ in §1, with check 34
   recorded there as needing one re-read on the build carrying trap 24. The phase's two
   remaining items were both closed on purpose and stay closed:
   - **§6 #4 — the brand's source list at the bottom of Brands: SKIPPED by decision, 2026-09-29.**
     The human chose to close the phase without it. It needs a new route, a new DAO query and a
     new `check_app_sql.py` assertion, and it collides with a decision taken the same day (the
     global coverage panel came off Settings). `DESIGN.md` §4.5 and `PHASE_11` §6 both now record
     it as an override, so neither document quietly claims it exists. The per-code source still
     shows on the code's own page, which is where a technician checking one answer looks.
   - **§7 — the remaining token drift: also closed.** 112 hardcoded `.dp` values left in the
     source (counted 2026-09-30). **Invisible** — not one pixel changes, and no phone check can
     see it done. It is
     documentation debt, not a defect, and it converts screen by screen whenever a screen is
     being changed anyway. `R.dimen` went 0 → 4 uses this phase, which is enough that the token
     file is no longer a comment.
2. ~~**The Settings screen, and the third bottom-nav item with it.**~~ — **done and
   phone-checked 2026-09-29** (all nine of §6 group 4 passed, after the trap-22 Light-mode fix). It was written before item 1, which is the one ordering
   mistake in this list: the Settings screen is a new surface and `PHASE_11` is going to change
   what every surface looks like, so it will probably need a second pass. What exists:
   - `ui/settings/SettingsScreen.kt` + `SettingsViewModel.kt`, and `NavTab.SETTINGS` with
     `Routes.SETTINGS`. The bar shows on Home, Saved and Settings.
   - The **EN/UR toggle moved out of Home** into it. `HomeViewModel.language`/`setLanguage` went
     with it rather than being left behind as dead code.
   - The **theme** is `ThemeMode { SYSTEM, LIGHT, DARK }` on the shared repository, persisted,
     resolved once in `MainActivity` **above** the navigation graph — so the first frame is
     already correct and a configuration change cannot reset it. `themeFrom()` defaults to SYSTEM,
     never to LIGHT.
   - **Phase 7 §6's content**: data version from `meta.kb_version`, the build date, Sources, and
     three About lines.
   - **It did not compile on the first push** — `Modifier.align` needs a `ColumnScope` that
     `PanelColumn` does not hand to its content. One `Row`, one push, now **trap 21**.
   - **`build app` is green on 755f8e1**: compile, lint, unit tests, no-permissions, database
     hash, APK size, artifact. That is the whole gate list and it is a **compile** result, not a
     screen result.
   - **§6 group 4's nine checks have not been run.** Until they are, this screen is exactly what
     trap 15 describes: a green build and no human ever having opened it.
3. ~~**Phase 5's teaching empty state.**~~ — **built 2026-09-29, CI green, not phone-checked.**
   A code typed on the *brands* screen now explains the scoping rule with a **measured** brand
   count and offers "Show all brands". Three outcomes, not one: many brands, exactly one brand
   (which is named), and not-a-code (which says nothing). The action is deliberately **not** the
   guide's "Search models instead" — models are scoped to a brand, so there is no model search to
   send someone to until they have picked one, and an action pointing at a search they cannot
   perform is a worse dead end. Recorded in `DESIGN.md` §4.2 rather than quietly substituted.
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

**The stray CI gap is closed.** `CI_CD.md` §1 documented a no-HTTP-client step that did not exist
in `build-app.yml`; it is written and green as of `4620c5e`. It took two attempts and the second
one taught something worth keeping — the first version failed the build for a reason that had
nothing to do with the app, because a class name in a dex needs its trailing semicolon or
`Ljava/net/Socket` also matches `Ljava/net/SocketException;`. **A gate that can fail for an
unrelated reason gets disabled, and a disabled gate is worse than none, because the guide still
claims it exists.** Trap 23 has the whole story; the doc is corrected to match what ships.

## 8. Things deliberately not built yet

- **The bottom nav has 3 items.** `PHASE_6` §5 wants Browse / Saved / Settings, and the Settings
  screen arrived on 2026-09-29, so the tab arrived with it. `NavTab` is a closed enum, so adding
  it was one enum value and one branch. **Only Saved carries an icon** — Browse and Settings keep
  the icon slot reserved so the three labels share a baseline, and a gear would have been a sixth
  icon in an app whose whole budget is five (RULE 11).
- The bar is shown on **Home, Saved and Settings only**, and hidden while drilling into brands,
  models, codes and detail. Those screens are one continuous descent and the bar is a way out to
  a different top-level place, not a way down.
- **A Settings screen now exists** (2026-09-29, CI-green, unphone-checked — see §7 item 2).
  It holds the EN/UR and theme switches, the data version, and About. No search box, no "reset
  app", no cache controls, no social links.
- **Two things `PHASE_7` §6 asks for are NOT on it, by the user's decision on 2026-09-29, and
  this is the record of that override rather than a quiet omission:**
  - **The Sources panel is gone.** §6 wanted brand and model coverage shown, "so 'my model is
    missing' is answerable by pointing at a real gap." The reasoning for dropping it is on
    screen rather than in a document: a list of 62 brands and 320 model lines is a fact about
    the app, and a technician with a broken unit in front of them is looking for a fact about
    *their* machine. The per-code source is still shown, on the code's own page, which is where
    a claim of provenance belongs. `DESIGN.md` §4.7 still lists Sources and **is now wrong**;
    Phase 11 should settle it rather than leave two documents disagreeing.
  - **The database size is gone**, and so are `KbRepository.dataSizeBytes()` and
    `formatDataSize()`. The size was measured rather than typed, so it was never *false* — but
    it was a second number on a settings screen, about a file the user cannot act on.
  - **The data version stays**, and is the only data fact on the screen: it is the one that
    tells a technician whether the answers they are currently reading are current.
- **The theme override is built.** `AcUstadTheme(dark = …)` already took a null/true/false; the
  work was the value on the shared repository and resolving it in `MainActivity` above the graph.
  `SYSTEM` is the default and resolves through `isSystemInDarkTheme()`; an unrecognised stored
  value falls back to `SYSTEM` and never to `LIGHT`, so a stale preferences file cannot override
  the phone's own setting. **It only became real once the dark palette moved out of
  `values-night/` — see trap 22. Do not let a colour go back behind a qualifier.**
- No search box on the Saved screen. Six rows; PHASE_6 §4 says no grouping and no folders.

## 8a. Two things that are debt, not defects, so nobody re-investigates them

**Five string resources are declared and never referenced** (checked 2026-09-29, unchanged from
before this session): `action_search`, `empty_brand_no_codes`, `empty_favourites_hint`,
`unit_cause_one`, `unit_cause_many`. They are not bugs — every screen that would want them has a
working equivalent. `empty_brand_no_codes` is genuinely redundant: the brand → series path
renders `empty_series_no_codes` instead. **Not worth a commit on their own**; delete them
alongside whatever next touches that file, or leave them, since an unused string is a lint
warning at worst.

**A scripted import insert is not a check.** Two builds in this session were lost to the same
family: a missing import, a duplicate import, a positional argument after a named one, and a
`Boolean` where a `ToggleableState` was required — none of which the script that *added* those
imports would have caught. The script anchors on a line it has not verified, so when the anchor
is missing it does **nothing and reports success**. Before pushing, sweep `main/` for all four
of: a symbol used with no import, an import used nowhere, a duplicated import, and an argument
list that mixes positional after named. `getValue` is excluded from the "unused" test by
necessity — it is an implicit operator for `by` and never appears in a body (trap 8).

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

> ### ⚠️ A token was pasted into this chat on 2026-09-29. Revoke it.
>
> A fine-grained PAT was pasted into the conversation to fetch CI logs. Two things came out of it
> and both are worth keeping:
>
>  - **It returned `401 Bad credentials`, so it was already invalid** — revoked, truncated when
>    copied, or mistyped. It was written to `~/.config/ac-ustad/ci-token`, used, and the file has
>    since been deleted. It never reached the repository: the secret scan in `verify-data.yml`
>    passed on every push that session.
>  - **It was not needed.** The repository is public, so run status, job steps and the compiler's
>    own `e:` annotations are all readable **unauthenticated**. Every diagnosis in this file's
>    history was made that way.
>
> **A token pasted into a chat is burned whether or not it works.** Revoke it
> (`Settings → Developer settings → Personal access tokens`) and write a new one to the path
> above. The next session should assume **no token exists** and not ask for one until it has
> actually tried without.

**The token must never be written into this repository**, and there are two independent reasons
rather than one: the `verify data` secret scan fails any push containing it
(`.github/workflows/verify-data.yml`), and the repo is public, so it would be in the open
internet and in git history permanently. If the token is ever pasted into a chat, a file or a
commit, **revoke it** (`Settings → Developer settings → Personal access tokens`) and write a new
one to the path above. It needs no scopes beyond read access to this one repo.
