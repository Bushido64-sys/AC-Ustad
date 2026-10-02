# Your store-upload checklist — the parts only you can do

Everything on the coding side is done and green. What is left needs your
accounts and your hands. Do these in order.

## 0. Confirm ads on the phone (already passed, but re-check if the phone was off)

- The phone must be on **working internet** — an offline phone fills nothing,
  not even banners, and empty slots look like code bugs.
- Open a model list with **more than 11 codes**, leave the search box empty,
  wait ~45 seconds. You should see real ad cards.

## 1. Add the 10 GitHub Secrets

Repo → **Settings → Secrets and variables → Actions** → New repository secret.
Names must match exactly:

| Secret | Where to find the value |
|---|---|
| `AD_APP_ID` | your local `android/ads.properties` |
| `AD_BANNER` | same file |
| `AD_INTERSTITIAL` | same file |
| `AD_REWARDED` | same file |
| `AD_NATIVE` | same file |
| `AD_APP_OPEN` | same file |
| `RELEASE_KEYSTORE_B64` | `~/.config/ac-ustad/STORE_SECRETS.txt` |
| `RELEASE_STORE_PASS` | same |
| `RELEASE_KEY_ALIAS` | same (`ac-ustad`) |
| `RELEASE_KEY_PASS` | same as the store password |

## 2. Enable GitHub Pages (one time, ~1 min)

Repo → **Settings → Pages** → Source: *Deploy from a branch* → Branch `main`,
folder `/ (root)` → Save. Wait 1–3 minutes, then open
`https://bushido64-sys.github.io/AC-Ustad/PRIVACY.html` in an incognito window.
It must load — the app's "View full policy" button and the Amazon listing
both point there.

## 3. Build the release

Actions → **build-release** → *Run workflow* → wait for green. Download the
`ac-ustad-release` artifact, install it over the debug build on the phone
(`adb install -r` keeps your saved codes), and spot-check: opens offline,
codes read, one banner, no crash.

## 4. Amazon Appstore submission

- New app submission with the release APK (`versionCode = 2` is fine for the
  first upload).
- Privacy URL: the Pages link above.
- Ad / data questionnaires, answered truthfully: contains ads (AdMob), no
  account, no analytics, network only for ads. The app needs **no Amazon-
  specific SDK** — AdMob is already wired; on Fire tablets (no Google Play
  services) ads simply stay empty while everything else keeps working.
- **Every later upload:** bump `versionCode` by at least 1 in
  `android/app/build.gradle.kts`, or Amazon rejects it.

## 5. After Amazon approves

Paste the live Amazon listing URL into AdMob (account verification). Until
then, production ad units may return no fill — expected, not a bug.

## Your signing key

- The keystore file lives at two places (they are the same file):
  - `android/app/release.keystore` (inside the repo folder, git-ignored —
    never committed)
  - `~/.config/ac-ustad/release.p12` (outside the repo)
- Password + alias + base64 are in `~/.config/ac-ustad/STORE_SECRETS.txt`.
- **Back it up now** (password manager + a second copy somewhere safe).
  Amazon re-signs the APK it distributes with its own certificate, but
  keeping your own key is what lets you sign for Google Play or sideload
  later, and it is not recoverable if lost.

That's it. When the 10 secrets and Pages are done, tell me and I will run
`build-release` and watch it go green.
