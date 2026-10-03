# AC Ustad — Offline AC & Solar Inverter Error Code Reference

A free, offline reference app for air-conditioner and solar-inverter technicians in Pakistan. A unit throws a code (E6, F4, Error 200, a blinking LED) and the app tells you what it means, why it happens, and what to do about it — in English or Roman Urdu, with no signal. All content ships inside a bundled database; nothing is fetched at runtime. Ad-supported only, no account, no analytics.

## What this repository is

Everything: the researched knowledge base, the Android app, the iOS-independent build pipeline, the CI gates, and the store submission docs.

## Quick map

```
.
├── AI-AGENT-PROMPTS.txt   <- START HERE. One-file resume map: read it, it routes you.
├── android/               <- THE ANDROID APP (Kotlin + Compose). CI builds it; the phone tests it.
│   ├── PROGRESS.md        <- APP STATUS: phases, traps, checks, next tasks. Always current.
│   └── app/               <- source + the app's copy of the shipped database
├── app-pipeline/          <- APP-READY PACKAGE: kb.sqlite, JSON, data contract, 20-doc build guide
│   └── guide/             <- AGENTS.md (entry point), RULES.md, ADS.md, DESIGN.md, ...
├── data/                  <- researched source of truth (JSON, never edited by hand)
├── schema/                <- JSON Schema every data file must pass
├── tools/validate.py      <- validates all data files against the schema
├── docs/                  <- knowledge-base research docs (PROGRESS, sources, FAQ)
├── research-raw/          <- source PDFs/screenshots, out of the data tree, not in git
├── exports/               <- generated DOCX/PDF artifacts (regenerable, not committed)
├── ac-ustad-impo-docs/    <- store-listing copy + owner checklist (AMAZON_DESCRIPTION, STORE_SUBMISSION)
├── .github/workflows/     <- verify-data.yml, build-app.yml (debug CI), build-release.yml (signed release)
└── local-secrets/         <- YOUR working copies of keys/secrets. Gitignored; never commit.
```

## App status (2026-10-03)

- All build phases (1–9, 11) are built and phone-checked. Ads (AdMob) shipped as a straight feature — debug fills Google's demo units, release is wired for real units.
- The app is **submitted to the Amazon Appstore** (5-day review estimate 2026-10-03).
- Release ads are not flowing yet because the AdMob account is still in verification — `app-ads.txt` is live at `https://bushido64-sys.github.io/app-ads.txt` and the pending piece is Google's crawl/approval, not a code bug.
- Google Play target is next: once AdMob serves real ads on the release APK, a Play Console account can ship the same app with the same real units.

Read `android/PROGRESS.md` for traps, CI gates, and the phone test checklist.

## Build / CI

| Workflow | Trigger | What it proves |
|---|---|---|
| `verify data` | every push/PR | the KB validates: schema, reproducible build, row counts, secret scan |
| `build app` | every push/PR | debug compile, lint, 115 unit tests, permission/network/database-hash/size gates |
| `build-release` | manual (`Run workflow`) | signed release APK: R8, apksigner v2 verified, no demo ad IDs, `ac-ustad-release` artifact |

Developers: `python3 -m venv .venv && .venv/bin/pip install jsonschema`, validate with `.venv/bin/python tools/validate.py`. No Android SDK on a dev machine — CI is the compiler, the phone is the test rig.

## Publish URLs

- Privacy policy: `https://bushido64-sys.github.io/AC-Ustad/PRIVACY.html`
- App ads: `https://bushido64-sys.github.io/app-ads.txt`
- Landing: `https://bushido64-sys.github.io/`

## Conventions (short — full rules in `app-pipeline/guide/RULES.md`)

- Never invent content; every code the user sees comes from `kb.sqlite` and must carry English + Roman Urdu causes and fix steps.
- A code is `(brand, series, code)`, never the code string alone.
- Search is scoped to the current level; no global code search. This is the product.
- The database is read-only except `favourites`. Colors/sizes come only from `design_tokens.xml`.
- Zero permissions get declared — what AdMob merges in is the floor and the ceiling.

## Legal

Error codes and factual meanings are facts; marketing prose is ours. Sources are attributed, manufacturer PDFs are never committed, and the app carries an unofficial-independent disclaimer — qualified technicians only.

---

Should the repo be public? **It can stay public**, and here's why: nothing harmful is in it if you keep the discipline — keys, tokens, and the real unit IDs live in `local-secrets/` (gitignored) and `~/.config/ac-ustad/`, neither tracked. Public buys you portfolio visibility and an easy Pages site. If you ever want the research notes and docs page locked away, flip it to private in Settings → Danger Zone; the app and JSON still work offline either way. The one rule that overrides this: if any secret ever reaches the history, rotate it.
