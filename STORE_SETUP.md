# STORE_SETUP — before uploading to the store

What has to happen, in order, before AC Ustad goes to the Amazon Appstore.
Sections marked **(assistant)** are my work in this repo. Sections marked
**(you)** need the owner's hands (secrets, toggles, uploads).

Current state: `versionCode = 2`, `versionName = "1.0.0"`, package
`com.acustad.app`. CI (`build-app.yml`) builds **debug only** — there is no
release build yet.

---

## 0. Gate: natives must verify first (you + assistant)

Do not build the store release while natives are still broken — the release
bakes ad behaviour in with R8, and Amazon review + AdMob both care that ads
render correctly.

- Install the latest debug artifact, open a model list with **more than 11
  codes** (slots start after position 11), search empty, wait ~45s.
- Expected: real ad cards, or `failed code=N` (a fill problem, not a code
  problem), or `loading…`.
- `NATIVE DEBUG: slot N: destroyed` must never appear on a visible screen.
- A short list (≤ 11 codes) shows `list too short (N codes)` on DEBUG —
  that is the placement policy working, not a bug.

Hard-won rules from the 2026-10-02 debug session (follow them or waste hours):

- **Test fill ONLY on working internet.** An offline phone fills nothing — not
  even banners — and every empty slot looks like a code bug. Prove internet
  first (`ping 8.8.8.8` via adb, or a banner test ad on screen).
- **Verify the artifact by the RUN's commit hash, not the file name.** Every
  CI artifact zip is named `ac-ustad-debug` — check the Actions run title
  shows the intended commit before downloading. A wrong install once cost a
  full trace cycle (caught via log-format mismatch).
- **Wait for green CI before installing.** Poll the Actions runs via the
  GitHub API; a red `build app` means the phone build is stale. (No `gh` CLI
  on this machine; compile errors surface via check-runs annotations.)
- Current status when this list resumes: natives root cause still open —
  see `android/PROGRESS.md` handoff entry (2026-10-02) for the proven facts,
  the open paradox, and the exact next steps. Do not start the release work
  below until the gate above passes.

## 1. Release keystore (assistant generates, you store)

- **What:** a file holding the signing key + certificate the release APK is
  signed with. Amazon *re-signs* with its own cert at distribution, so this
  key matters less here than on Google Play — but the build must still be a
  valid signed release, and the key is reusable for Play/sideload later.
- **How:** generated with `openssl` (no JDK on this machine): RSA-2048,
  self-signed, 25-year validity, PKCS12 (`.p12`, accepted by `apksigner`).
- **Handoff:** 4 values go to you privately (never in repo/chat logs beyond
  that one message — save them in a password manager):
  1. `RELEASE_KEYSTORE_B64` — base64 of the `.p12`
  2. `RELEASE_STORE_PASS` — keystore password
  3. `RELEASE_KEY_ALIAS` — key alias
  4. `RELEASE_KEY_PASS` — key password
- **When used:** only by the release workflow (step 3). Debug builds use
  Android's built-in debug key — you never touch the keystore otherwise.

## 2. Code changes (assistant)

1. `android/app/build.gradle.kts`
   - `adsId()`: env-var override first — `AD_APP_ID`, `AD_BANNER`,
     `AD_INTERSTITIAL`, `AD_REWARDED`, `AD_NATIVE`, `AD_APP_OPEN` —
     then `ads.properties`, then demo fallback. Real IDs never touch git.
   - `signingConfigs.release`: `storeType = "PKCS12"`, reads the four
     `RELEASE_*` env vars; applies only when they exist, so debug CI is
     untouched.
2. `.github/workflows/build-release.yml` (new, `workflow_dispatch` only)
   - Same SDK/lint/test gates as `build-app.yml`, plus `assembleRelease`,
     `apksigner verify` (v2 signature required), and a gate asserting the
     release APK contains **no demo IDs**
     (`ca-app-pub-3940256099942544` must be absent).
   - Artifact: `ac-ustad-release`, 30-day retention.
3. Privacy (see §4): fix `PRIVACY.md`, add `PRIVACY.html` at repo root,
   add in-app "View full policy" link (+1 EN string, ACTION_VIEW).
4. Docs: `CI_CD.md` §3 (secrets) + §7 (release flow), `ADS.md` (real-ID
   swap via CI), `PROGRESS.md` entry. Commit per RULE 20.

## 3. GitHub Secrets (you)

Repo → **Settings → Secrets and variables → Actions** → add all 10
(names must match exactly):

| Secret | Value |
|---|---|
| `AD_APP_ID` | real app ID (from local `android/ads.properties`) |
| `AD_BANNER` | real banner unit |
| `AD_INTERSTITIAL` | real interstitial unit |
| `AD_REWARDED` | real rewarded unit |
| `AD_NATIVE` | real native unit |
| `AD_APP_OPEN` | real app-open unit |
| `RELEASE_KEYSTORE_B64` | from §1 handoff |
| `RELEASE_STORE_PASS` | from §1 handoff |
| `RELEASE_KEY_ALIAS` | from §1 handoff |
| `RELEASE_KEY_PASS` | from §1 handoff |

## 4. Privacy policy + GitHub Pages (assistant + you)

**Assistant:** dedup/fix `PRIVACY.md` (remove stale "requests no
permissions" + duplicate sections, fix "Changes"), add `PRIVACY.html`
(same content, minimal styling).

**You — enable Pages (one-time, ~1 min):**

1. Repo → **Settings → Pages**.
2. **Build and deployment → Source:** "Deploy from a branch".
3. **Branch:** `main`, **folder:** `/ (root)` → **Save**.
4. Wait 1–3 min. Policy is live at:
   `https://bushido64-sys.github.io/AC-Ustad/PRIVACY.html`
5. Check it loads in a browser (incognito, so no login caching fools you).

Fallback if Pages ever breaks (works with zero setup, uglier URL):
`https://raw.githubusercontent.com/Bushido64-sys/AC-Ustad/main/PRIVACY.html`

The in-app "View full policy" link points at the Pages URL, so enable
Pages before shipping the release build.

## 5. Build + spot-check (you)

1. Actions → **build-release** → Run workflow → wait for green.
2. Download `ac-ustad-release`, install over the debug build on the phone.
3. Spot-check: opens offline, codes read, one banner visible, no crash.
   (Full ADS.md §7 re-run only if ad code changed since last pass.)

## 6. Amazon upload (you)

- New app submission with the release APK (`versionCode = 2` is fine for
  the **first** upload).
- Paste the Pages privacy URL into the listing's privacy field.
- Answer the ads/data questionnaires truthfully: contains ads (AdMob),
  no account, no analytics, INTERNET only for ads (see PERMISSIONS.md).
- **Every later upload: bump `versionCode` by ≥1** (one-line change in
  `android/app/build.gradle.kts`), or Amazon rejects it.

## 7. AdMob listing link (you, after Amazon approves)

Paste the live Amazon listing URL into AdMob (account verification) so
real ads serve. Until then, production units may return no fill.

## 8. Order of operations (checklist)

- [ ] Natives verified on phone (§0)
- [ ] Keystore generated + 4 values stored (§1)
- [ ] Code changes merged (§2)
- [ ] 10 Secrets added (§3)
- [ ] `PRIVACY.html` committed + Pages enabled + URL loads (§4)
- [ ] Release built green + spot-checked (§5)
- [ ] Amazon submission + privacy URL (§6)
- [ ] AdMob listing link pasted (§7)
