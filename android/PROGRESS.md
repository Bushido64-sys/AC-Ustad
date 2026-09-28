# PROGRESS — AC Ustad app

**Read this first when resuming.** Last updated: 2026-09-28

> **Where we are:** Phases 1–4 and 6 are built and green. The app installs and runs. The knowledge
> base and the 20-document build guide are finished. **Nothing is half-finished.**

---

## 1. Phase status

| Phase | State | What actually works |
|---|---|---|
| 1 · Setup | ✅ | Skeleton, bundled database, 7 IBM Plex fonts, light + dark themes, launcher icon, home screen with live counts |
| 2 · Data layer | ✅ | 4 DAOs, immutable models, one repository, favourites writes, stale-id sweep |
| 3 · Browse | ✅ | Home → brands → model lines → codes, with scoped search on every list |
| 4 · Code detail | ✅ | Severity + meaning → numbered fix steps → causes → notes → source, with a working star |
| 5 · Search polish | 🟡 | The **data layer** is done and tested; the empty-search teaching state is not built |
| 6 · Saved screen | ✅ | The Saved list, swipe-to-remove with Undo that restores the original position, and a bottom nav on the two top-level screens |
| 7 · Offline & updates | ⬜ | Mostly satisfied already; no verification pass yet |
| 8 · Accessibility & Roman Urdu | ⬜ | Sizes, contrast and semantics are in; the EN/UR toggle itself does not exist yet |
| 9 · Hardening & release | ⬜ | Release signing, the perf pass, the full release checklist |

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
3. **Watch for the four failure modes this project actually produced:** a column name written
   from memory instead of read from the schema; a claim described as "verified" that was only
   reasoned about; a text-based check reporting a conclusion it could not see (the dead-code
   sweep, the self-matching secret scan, and `getValue`, an implicit operator that never
   appears in the source); and a duplicated instruction drifting from the thing it documents -
   which is why the prompt now lives in exactly one file.

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
  repo/        KbRepository.kt — the UI's ONLY door to the database
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
12. **Every view model builds its own `KbRepository`** (`HomeViewModel`, `SavedViewModel`,
   `CodeDetailViewModel`, …), so each one holds a private `contentLanguage` StateFlow, its own
   `Mutex` and its own `SQLiteDatabase` handle to the same file. It works for Phase 6 — the
   saved list is re-read on arrival precisely *because* the detail screen writes through a
   different instance. **It will not do for the EN/UR toggle**, which has to reach every screen
   at once, so Phase 8 needs one shared instance.
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

## 5. The gates, and what each one is for

| Gate | Protects |
|---|---|
| `verify data` / `tools/validate.py` | the knowledge base validates against the schema |
| `verify data` / `contentSha256` | a data change cannot ship without a rebuild. Byte-comparing `kb.sqlite` does **not** work: SQLite versions produce different file layouts for identical data |
| `verify data` / `check_app_sql.py` | **40 checks** running the app's real SQL against the real database. The only way to test SQL, since `android.database.sqlite` is a stub off-device |
| `build app` / compile + lint | 0 lint errors |
| `build app` / unit tests | 42 tests, including all 2,139 code strings and the FTS quoting |
| `build app` / permissions | the app ships with nothing but AGP's own self-permission |
| `build app` / database hash | the APK cannot carry a stale database |
| `build app` / APK size | catches a duplicated 9 MB database or an accidental image library |
| `build app` / compiler error lines | puts the compiler's own `e:` lines on the **check itself**, not only in a log. GitHub's log download endpoint needs admin rights, so without this step a compile failure is visible only as "exit code 1" and needs a personal access token to diagnose. Runs `if: failure()`, cannot weaken a gate, needs no secret |

`check_app_sql.py` imports the canonical rule from `build_kb.py` rather than restating it. Two
copies of that rule would drift, and a lower-case copy would quietly break every code search.

## 6. Test on the phone — the six checks

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

**And the six checks for the Saved screen (Phase 6):**

7. Star a code, then open **Saved** from the bar: the row is there, and the tab's star is
   filled. Go back to Browse — the star stays filled.
8. Swipe a row left: it goes, a bar appears, **Undo** puts it back **in the same position**,
   not at the top. Swipe a short distance and let go: nothing happens.
9. Tap a row's star instead of swiping: same result, same Undo bar.
10. Tap the row itself: the code's detail screen opens, with the right brand and model.
11. Star three codes in a row fast. The order is newest first, and none of them is lost or
    duplicated.
12. Saved with nothing starred shows the one-line empty state and no bar.

## 7. Do these next, in this order

1. **Phase 8's EN/UR toggle.** `KbRepository.setContentLanguage()` and `ContentLanguage.pick()`
   exist and are wired into every state flow, but nothing calls `setContentLanguage`. Both
   languages are already loaded on every query, so the toggle re-renders with **no new query**.
   Read trap 12 first: every view model builds its **own** `KbRepository`, so the language
   currently lives per-screen and the toggle needs one shared instance to reach all of them.
2. **Phase 5's teaching empty state.** When a code is typed on the *brands* screen the search
   correctly finds nothing; the screen must then explain why, in one line, with a way forward.
3. **The Settings screen, and the third bottom-nav item with it.** The bar has Browse and
   Saved only — see §8.
4. **Phase 9 release signing** — only when the feature set stops changing.

## 8. Things deliberately not built yet

- **The bottom nav has 2 items, not 3.** `PHASE_6` §5 wants Browse / Saved / Settings, but
  PROGRESS §8 of the original plan put the Settings *screen* in Phase 8. A third tab with
  nothing behind it is a dead end, so the tab arrives with its screen. `NavTab` is a closed
  enum, so adding it is one enum value and one branch.
- The bar is shown on **Home and Saved only**, and hidden while drilling into brands, models,
  codes and detail. Those screens are one continuous descent and the bar is a way out to a
  different top-level place, not a way down.
- No Settings screen: it holds the EN/UR and theme switches (Phase 8) and the data version.
- No theme override: `AcUstadTheme(dark = ...)` already takes a null/true/false, so the
  wiring is a Settings row.
- No dark-mode force: the app follows the system today, which is the correct default.
- No search box on the Saved screen. Six rows; PHASE_6 §4 says no grouping and no folders.

## 9. Environment facts

- Repo: `git@github.com:Bushido64-sys/AC-Ustad.git`, private, branch `main`.
- The Android app lives in `android/` **inside** the same repository as the knowledge base.
- `verify data` and `build app` both run on every push to `main`.
- The artifact is 12 MB and is retained for 30 days.
- A personal access token was used to read CI logs; revoke it when convenient
  (Settings → Developer settings → Personal access tokens).
