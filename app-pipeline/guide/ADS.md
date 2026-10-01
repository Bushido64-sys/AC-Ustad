# ADS.md — advertising in AC Ustad

**Status: BUILT 2026-10-01 (AdMob, test units in debug, owner IDs in release).
Phone-checked: not yet — §7 below is the checklist.**

The staged trial/paid/kill-switch plan (`archive/PHASE_10_MONETISATION.md`)
was superseded by decision 2026-10-01: ads ship as a straight feature. This
file is the whole spec — formats, placements, the rules that keep the account
alive, and the future work.

---

## 1. The inventory

| Format | Unit | Debug serves | Where |
|---|---|---|---|
| App ID | owner ID in `ads.properties` | Google demo `~3347511713` | manifest placeholder |
| Adaptive banner | owner banner unit | demo `/9214589741` | one pinned slot, every screen |
| App open | owner app-open unit | demo `/9257395921` | cold start + foreground return |
| Interstitial | owner interstitial unit | demo `/1033173712` | exit + return-to-top, capped |
| Rewarded | owner rewarded unit | demo `/5224354917` | save wall, 4th save on |
| Native advanced | owner native unit | demo `/2247696110` | code list, detail, models, Settings |

Demo IDs are Google's, read off the official test-ads page — never invented.
Owner IDs live only in the git-ignored `android/ads.properties` (release) and
the owner's scratch `Unit-ID.txt` (never committed, git-ignored). Neither file
may ever be quoted, pasted, or committed anywhere — the repository is public,
and a unit ID in git history is there permanently. **Real IDs never appear in
a debug build:** Google counts that as fake traffic and can close the account
without warning.

---

## 2. How it is wired

- SDK: `play-services-ads` 23.6.0 (legacy; Next-Gen migration is §8) + UMP
  3.1.0, via the version catalog. 25.x was tried and reverted: its bundled
  Kotlin modules carry newer metadata than the project's Kotlin 2.0.21 reads,
  and `compileDebugKotlin` fails on every one of them. 23.6.0 has every format
  this file needs. The SDK ships its own R8 rules; nothing extra was needed.
- `AdsManager` (`ui/ads/`) is the only door to the SDK. Screens never import
  GMA classes. `UstadApp.onCreate` initialises MobileAds and preloads app-open
  — before the first foreground transition, which is the only way a cold-start
  ad can exist. UMP consent runs in `MainActivity` and gates personalised loads
  from there; the SDK honours it for everything after. **No ad call anywhere
  throws into the UI** (2026-10-01 crash audit): init, consent, every preload,
  every show, pool callbacks, banner factory and native bind are all wrapped —
  a failed ad is an empty slot and a callback's unhappy branch, never an
  exception. The pool refuses late callbacks after dispose, so a screen that
  left cannot be crashed by an ad arriving for it.
- `AppOpenManager` registers activity callbacks in `UstadApp` and shows on
  every 0 → 1 foreground transition. `AdsManager.fullscreenShowing` refuses
  while another full-screen ad is up, so formats never stack.
- Preloads: interstitial + rewarded at init, app-open after consent, banners
  and natives per placement. Every show path ends in its callback — shown,
  capped, missing or failed — so callers never hang.
- StrictMode (debug, penaltyLog) logs the SDK's main-thread IO as noise. It
  is detector noise, not a violation: the alternative is initialising ads off
  the main thread against the SDK's contract.

---

## 3. Placements

**Banner.** One slot for the whole app, pinned between the `NavHost` and the
bottom bar — not one slot per screen. Identical on every screen including
detail (below the content, never inside it): one request, zero layout shift,
no per-screen wiring to forget. The slot reserves the ad's own adaptive
height before anything loads; an unfilled ad is empty space of the same size.
Refresh 30s, server-side; mediated UIs keep their own refresh off.

**App open.** Cold start after the first frame, and every return from
background. Silent when unready — startup never waits.

**Interstitial.** Exit (system back on Home, then finish) and return-to-top
(Browse tab from deep in the path, unwinds under the dismissed ad). Capped:
one per 3 minutes in persisted prefs, so the two can never stack. Never on
the way down — brand → model → code → fix steps carries no ad of any kind.

**Rewarded (save wall).** The first 3 saves are free (`FREE_SAVES` in
`AdIds.kt`, unit-tested). The 4th save opens a warm popup — *"You have saved
3 codes. Watch a short ad to keep saving. It keeps AC Ustad free."* — with
"Watch ad" and "Not now", both real ways out. Earned → the held save writes.
Mid-watch failure → the save is forgiven and writes (by decision: a reward
interrupted by the network is not the user's fault). Unstarring is always
free and never passes the wall.

**The one fail-closed exception.** No ad to show (offline, no fill) → the
save waits and a one-line popup says saving needs a connection. By explicit
decision: the owner preferred an honest block over a silent free save. The
first 3 saves always work offline; only the walled saves need the network.
Answers — codes, meanings, fix steps — never need it, on any screen.

**Native.** Code list every 5th row from #11 (`nativeSlotAfterPositions`,
unit-tested), detail bottom below Source, models-list bottom, Settings
bottom. Unfiltered lists only on the code screen: a search narrows to the
codes asked for, and no ad sits between a question and its answer. Unfilled
slots emit nothing — never a blank card.

---

## 4. The native compliance contract

Researched off Google's native policies before building (badged, AdChoices,
validator), because a violation disables serving or closes the account:

- the **"Ad" badge sits on top**, rendered by the app outside the SDK view —
  it cannot be covered or cropped by a creative, because it is not part of
  one. Minimum 15px holds by construction (label style).
- the **AdChoices slot is registered** on every card, so the SDK overlay
  always has a place to live. Minimum 32×32dp holds.
- the **CTA verb button is the only tappable-looking thing**, because it is
  the only tappable thing: the card itself is not clickable and never
  navigates. Only headline, media and CTA are registered with the SDK.
- **no content overlap**, ever — app rows never cover the ad and the ad
  never covers rows.
- text colours bind from the theme at runtime, not from XML: a static
  `textColor` would freeze the light palette into dark mode (trap 22).
- each placement passes **Google's native validator on test ads** before any
  real ID serves it (§7). A placement that fails the validator does not ship.

---

## 5. What the app says about itself

The *"requests no permissions"* claim died the commit the SDK arrived —
deliberately and in the open, not by drift:

- Settings Privacy panel + `PRIVACY.md`: *"No account and no analytics. The
  app shows ads to stay free: ad networks may see your device and IP
  address."*
- `PERMISSIONS.md` + RULE 14 rewritten as decisions: INTERNET and
  ACCESS_NETWORK_STATE, merged in by the SDK's own manifest and never typed,
  for Google's ad servers and nothing else. No accounts, no analytics, no
  crash reporting — still forbidden, still enforced.
- Play listing copy must never promise "no ads", "no permissions", or
  "nothing leaves the phone". All three are now false.

---

## 6. CI gates (all rewritten as decisions in the ads commit)

| Gate | Change |
|---|---|
| permissions | allows AGP self-permission + the PERMISSIONS.md §1 set (INTERNET, ACCESS_NETWORK_STATE, AD_ID, FOREGROUND_SERVICE, WAKE_LOCK, ACCESS_ADSERVICES_*), fails on anything else |
| network/ads library | `gms/` + `ump/` + `firebase/installations/` + `firebase/annotations/` moved from hard-fail to known-allowed-with-notice; okhttp, retrofit, apache-http, facebook-ads, firebase analytics/messaging/crashlytics/remote-config, rxjava, crashlytics, sentry still hard-fail. A diagnostic notice lists every firebase subpackage in the APK, so the next call is made from evidence |
| APK size | unchanged (25 MB ceiling; SDK adds ~2 MB) |
| compile + lint + tests | unchanged; `AdsLogicTest` pins the wall count and slot math |

---

## 7. Phone checklist for the ads build

- [ ] Banner visible, bottom-pinned, on Home / brands / models / codes / Saved / Settings; content never shifts when it loads late or fails.
- [ ] Airplane mode: every answer works, banners are empty slots, 4th save shows the needs-connection popup, first 3 saves work.
- [ ] Star 3 codes free; 4th shows the wall popup with both buttons working; "Watch ad" (test ad) → save writes; mid-watch kill → save forgiven.
- [ ] App open on cold start (test creative) and on return from background; never over an interstitial/rewarded.
- [ ] Exit back on Home → interstitial (test) → app closes. Browse tab from deep → interstitial → Home.
- [ ] Native validator: zero issues on all four spots (test ads show the validator notification).
- [ ] Dark mode on every ad-bearing screen; font 1.3; UR labels still English.
- [ ] `dumpsys package` shows nothing outside the PERMISSIONS.md §1 set.

---

## 8. Future work (not this build)

- **Bidding adapters** (Meta, Unity, Mintegral…): needs their accounts first.
  Console-side mediation groups + Pakistan eCPM floors can land without an
  app change; adapters that ship an SDK need a dependency + a gate review
  each. Initialise-then-load order in `AdsManager` already satisfies what
  bidding adapters require.
- **Real-ID swap**: replace `ads.properties` values — zero code change. Keep
  debug on demo units forever.
- **app-ads.txt + transparent seller info**: owner's domain work, outside the
  codebase. DSPs bid less without it.
- **GMA Next-Gen migration**: legacy is in maintenance mode. Migrate when a
  feature needs it, not before — the legacy API is stable and fully
  mediated.
- **Reward tuning**: wall count, cap length, and rewarded-for-banner-free are
  one-constant changes in `AdIds.kt` + `AdsManager`.
