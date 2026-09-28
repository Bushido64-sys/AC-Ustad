# PROGRESS — AC Ustad app

**Read this first when resuming.** Last updated: 2026-09-28

> **Where we are:** Phases 1–4 are built and green. The app installs and runs. The knowledge
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
| 6 · Favourites screen | ⬜ | The star works and persists; the Saved list and bottom nav do not exist |
| 7 · Offline & updates | ⬜ | Mostly satisfied already; no verification pass yet |
| 8 · Accessibility & Roman Urdu | ⬜ | Sizes, contrast and semantics are in; the EN/UR toggle itself does not exist yet |
| 9 · Hardening & release | ⬜ | Release signing, the perf pass, the full release checklist |

## 1a. Resume prompt — for a fresh session with no memory of this one

The conversation is disposable; this file is not. A new session starts with zero context, so
give it pointers rather than recollection:

```
Working on the AC Ustad Android app. Read ~/AC-Ustad/android/PROGRESS.md first -
sections 1 (phase table), 4 (the nine traps), 5 (what each CI gate protects) and
7 (next tasks in order). Verify anything you state against the database or
app-pipeline/check_app_sql.py before relying on it. Next task: <name it here>.
```

Three rules for whoever (or whatever) picks this up:

1. **The database wins.** If this file, the build guide and `kb.sqlite` disagree, the
   database is right — and say so instead of quietly working around it.
2. **Do not trust a number that has not been read.** Every count here was queried, but a
   data release can move them. `python3 app-pipeline/check_app_sql.py` re-checks 40 of them.
3. **Watch for the four failure modes this project actually produced:** a column name written
   from memory instead of read; a claim described as "verified" that was only reasoned about;
   a text-based check reporting a result it could not actually see; and an implicit-operator
   import (`getValue`) that no textual search can detect.

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
  ui/
    AcUstadAppBar.kt      title + at most one action
    AcUstadNavHost.kt     the whole graph, routes carry slugs only
    common/               BorderedPanel/Row, SeverityChip, SearchField, StarIcon, Severity
    home/                 HomeScreen
    browse/               Brands, Series, Codes screens + view models
    detail/               CodeDetailScreen + view model
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

## 5. The gates, and what each one is for

| Gate | Protects |
|---|---|
| `verify data` / `tools/validate.py` | the knowledge base validates against the schema |
| `verify data` / `contentSha256` | a data change cannot ship without a rebuild. Byte-comparing `kb.sqlite` does **not** work: SQLite versions produce different file layouts for identical data |
| `verify data` / `check_app_sql.py` | **40 checks** running the app's real SQL against the real database. The only way to test SQL, since `android.database.sqlite` is a stub off-device |
| `build app` / compile + lint | 0 lint errors |
| `build app` / unit tests | 32 tests, including all 2,139 code strings and the FTS quoting |
| `build app` / permissions | the app ships with nothing but AGP's own self-permission |
| `build app` / database hash | the APK cannot carry a stale database |
| `build app` / APK size | catches a duplicated 9 MB database or an accidental image library |

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

## 7. Do these next, in this order

1. **Phase 6, the Saved screen.** The star already writes and survives a restart; only the list,
   the swipe-to-remove with Undo, and the three-item bottom nav are missing. The DAO and
   `KbRepository.favouriteItems()` are done and unused.
2. **Phase 8's EN/UR toggle.** `KbRepository.setContentLanguage()` and `ContentLanguage.pick()`
   exist and are wired into every state flow, but nothing calls `setContentLanguage`. Both
   languages are already loaded on every query, so the toggle re-renders with **no new query**.
3. **Phase 5's teaching empty state.** When a code is typed on the *brands* screen the search
   correctly finds nothing; the screen must then explain why, in one line, with a way forward.
4. **Phase 9 release signing** — only when the feature set stops changing.

## 8. Things deliberately not built yet

- No bottom navigation: 3 items belong to Phase 6.
- No Settings screen: it holds the EN/UR and theme switches (Phase 8) and the data version.
- No theme override: `AcUstadTheme(dark = ...)` already takes a null/true/false, so the
  wiring is a Settings row.
- No dark-mode force: the app follows the system today, which is the correct default.

## 9. Environment facts

- Repo: `git@github.com:Bushido64-sys/AC-Ustad.git`, private, branch `main`.
- The Android app lives in `android/` **inside** the same repository as the knowledge base.
- `verify data` and `build app` both run on every push to `main`.
- The artifact is 12 MB and is retained for 30 days.
- A personal access token was used to read CI logs; revoke it when convenient
  (Settings → Developer settings → Personal access tokens).
