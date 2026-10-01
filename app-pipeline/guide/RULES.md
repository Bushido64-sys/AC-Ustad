# RULES.md — Hard constraints

Non-negotiable. A build that breaks these is worse than a build that is missing a feature.
Each rule states **why**, so you can tell the difference between a rule and a preference.

---

## RULE 1 — Never invent content
Every word a user reads about a code comes from the database (`en` or `ur`).
No hard-coded meanings, code lists, severities, brand names, model names or fix steps.
No "example text" that looks like real data. If a field is null, hide it.
*Why:* 4418 codes were researched from manuals. A made-up fix step could send a technician
to the wrong part.

## RULE 2 — A code is (brand, series, code). Never just the code.
Use the `uid` column: `growatt/growatt-mod-tl3x/Error 200`.
`E1` is on 20 brands / 30 series entries, `E3` on 21, `E6` on 16, `F4` on 15 — 359 distinct
code strings appear on more than one brand. Never merge, never average, never show one and
hide the rest.
*Why:* the same code means a room sensor on one brand and a compressor lock on another.

## RULE 3 — Search is scoped to the current level
Brand page searches brands · series page searches model lines · code page searches codes in
that series only. **No global code search.** Never add one, and never let a code search
escape the series the user is inside.
*Why:* this is the feature. It makes it impossible to show a technician the wrong meaning.

## RULE 4 — Never pass raw input to an FTS MATCH
454 of the 2139 code strings throw if used raw — `BLINK-RUNNING` → `no such column: RUNNING`;
`LED1 x1 blink; LED2 off; LED3 off` → `syntax error near ";"`.
Code lookups go through `aliases.alias_norm` after `normalise()`. Free text goes through
`code_fts` with the input wrapped in double quotes. See `DATA_SCHEMA.md` §7.
*Why:* a crash on a common search term makes the app feel broken.

## RULE 5 — The database is read-only
Never write to `brands`, `series`, `codes`, `causes`, `solutions`, `aliases`, `code_fts`.
`favourites` is the only writable table. Open the shipped DB read-only.
*Why:* the database is replaced wholesale on every app update; user data must survive it.

## RULE 6 — Two colours, and the alert hue is one of them
Blue is the only structural/brand colour. The signal hue (orange-red) is used **only** for
severity and alerts. Neutrals are tints of blue, never pure grey, never pure black.
No purple, no indigo, no gradients, no third accent. See `design_tokens.xml`.
*Why:* a calm interface with one alert colour reads instantly in sunlight.

## RULE 7 — White text only on the deep blues and the deep signal
`#1668A8` and `#B32D0C` are the only fills that carry white text. On the light blue
`#6FB4DE` use ink `#0B1F2A` (7.45:1). White on `#6FB4DE` is 2.27:1 and **fails**.
*Why:* the most common way a "clean light UI" becomes unreadable.

## RULE 8 — Severity is never colour alone
Every severity shows a **word** (`DANGER`, `STOP`, `CHECK`, `CLEARS`, `INFO`) in addition to
its fill. 60% of all faults are the same level (`stop_pro`), so colour cannot be the signal.
*Why:* colour-blind users, sunlight, and cheap LCD panels.

## RULE 9 — Neo-brutalism with restraint
2dp ink borders on cards, list rows, inputs and chips. Corner radius 0–4dp, never pill.
Hard shadow `3dp 3dp 0` on **primary actions and the selected state only** — never on every
card (that is the generic look). No blur, no soft shadow, no glass anywhere.
*Why:* restraint is the difference between "designed" and "a template".

## RULE 10 — No generic-AI look
Banned: purple/indigo gradients · Inter or Roboto for everything · grey-400/500 body text ·
three identical feature cards in a row · icon-in-rounded-square · emoji as bullets · a soft
shadow on every surface · large radii · "Get Started"/"Elevate your…" copy · a floating pill
above a heading · em-dashes in every sentence.
*Why:* this is a working tool, not a landing page. It should look like an instrument.

## RULE 11 — Five icons, total
Back, search, favourite (outline/filled), external link, expand chevron. Nothing else is an
icon. Severity and confidence are **text chips**, not icons. No decorative iconography.
*Why:* icon-everywhere is the single biggest "AI made this" signal.

## RULE 12 — Body text is readable, always
16sp minimum for meaning and fix steps, 20sp line height minimum (Roman Urdu needs it),
no italics, no letter-spacing changes, no justified text, no all-caps for body.
*Why:* the content is long-form (fix steps average 83 characters, up to 270).

## RULE 13 — Content toggle is not a UI translation
`EN / UR` switches only code content (title, meaning, causes, solutions, notes) and the
severity/confidence words. UI labels, brand names and model names stay English.
*Why:* a technician reads brands and models in English; the content is what needs Roman Urdu.

## RULE 14 — Three permissions for the ads, network for Google's ad servers only, nothing else
The app declares **exactly three permissions — `INTERNET`, `ACCESS_NETWORK_STATE` and
`com.google.android.gms.permission.AD_ID` (Advertising ID) — merged in by the AdMob SDK's
own manifest and never typed** — and makes **no network call of its own**. No accounts,
no login, no analytics, no crash reporting, no uploads.
Everything the knowledge base needs is in the bundled database.
*Why:* the app must work in a basement with no signal, and the only network traffic
a technician's phone should ever carry for this app is the ads that keep it free.
The three permissions are a recorded decision (ADS.md), not drift — and the CI gates
fail the build on any fourth permission or any non-ads network library.

## RULE 15 — Offline is the only mode
No "you appear to be offline" state exists, because there is no online state. Do not build
sync, retry, or refresh affordances for content.
*Why:* there is nothing to sync.

## RULE 16 — Touch targets
Minimum 48dp in both dimensions for anything tappable. List rows are at least 56dp because
this app is used with gloves on. Gestures always have a visible tap equivalent.
*Why:* hands are cold, dirty, and in a hurry.

## RULE 17 — Every screen has its three states
Loading, empty, error — designed, not afterthought. Especially the **empty** state: 8 brands
legitimately have 0 codes and must say so plainly instead of showing a broken list.
*Why:* the most-looked-at screen in the app is a search that found nothing.

## RULE 18 — Never break a working build
A green build outranks a new feature. If a change breaks the build, revert or fix it before
doing anything else. Never leave a half-migrated schema.
*Why:* the human tests on a physical phone; a broken build stops all progress.

## RULE 19 — The repo stays lean
Do not commit build outputs, `.gradle/`, IDE files, or duplicated data. The database is
committed once as an asset; never copy it into `src/`, `assets/` twice, or a second format.
*Why:* a 9 MB binary that gets duplicated is a 9 MB problem per copy.

## RULE 20 — Commit format
`<type>(<scope>): <what changed in plain words>` — e.g.
`feat(codes): scoped code search inside a model line`
Allowed types: `feat`, `fix`, `refactor`, `perf`, `test`, `docs`, `chore`.
Every commit updates `PROGRESS.md` in the same change.
*Why:* the commit log is how the project is read six months later.
