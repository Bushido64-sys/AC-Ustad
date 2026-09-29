# PHASE_10_MONETISATION.md — getting paid without wrecking the app

**Status: PLANNING ONLY. Nothing in this file has been built.**

The one gate: **we do not start until the Google Play developer account exists.** Everything here
assumes Play, because Play is what makes the hard version block real instead of a polite
suggestion.

---

## 0. The shape of it, in one screen

| Stage | What the user gets | What we get | When |
|---|---|---|---|
| **A** | Everything free, with ads | Ad revenue | Now, once Play access exists |
| **B** | Everything free, with ads, for a set number of days | Reviews, word of mouth, a user base | A trial window after Stage A |
| **C** | Everything free forever, no ads | A one-off payment per user | When the trial ends |
| **D** | The same app, licensed to a company | An annual fee per company | Much later, or never |

Stages A and B are the same build. Only the **date** changes.

**What we are actually selling.** The free app is a tool that solves a real problem for a real
person: a technician standing in front of a broken unit with no signal needs the meaning and the
fix. The paid version is not a better tool — it is the same tool with the ads taken out. Anyone
who tells you otherwise is selling something this app does not have.

---

## 1. What this changes, and why it gets written down

Adding ads adds two permissions that the project has spent its whole life refusing:

| Was | Becomes |
|---|---|
| `RULES.md` RULE 14: "makes **no network call**... **no ads**, no accounts, no login" | Ads are allowed. **No accounts and no login stay forbidden.** |
| `PERMISSIONS.md` §1: "**There is no `<uses-permission>` element... and there must never be one**" | Exactly two: `android.permission.INTERNET` and `android.permission.ACCESS_NETWORK_STATE` |
| The CI gate in `build-app.yml` allows **one** permission name | It allows that one **plus exactly these two** |
| The app asks for nothing, and says so | The app still **sends nothing of the user's** — no account, no analytics, no crash reports, no uploads. It only talks to Google's ad servers. |

**This is a decision, not an accident, and it is recorded here rather than slipped in.** The
project's own rule is: *if the database and a document disagree, the database wins — and say so.*
The same discipline applies to a rule being overridden. If you are ever unsure whether something
was approved, this section is the answer.

### The honest cost

The app's strongest selling point is that it asks for nothing and watches nobody. AdMob breaks
that, partly. A technician choosing between two AC-repair apps may pick the one that does not
track them, and their employer may ban the one that does.

This is accepted, not minimised. It is why the offline guarantee below is written so carefully,
and why **no accounts** is still forbidden: it is the part of the privacy promise we can keep.

---

## 2. The one thing that never changes

> **The knowledge base never needs the network. Ads and licensing may fail; nothing a
> technician needs ever does.**

Concretely, this is a promise we can be held to:

- Airplane mode → every code, every brand, every fix step, Saved, and the EN/UR toggle all work.
- No ad request may ever block, delay, or change the app's layout. If an ad fails, the slot is
  simply empty.
- If the config file in §3 cannot be fetched, the app still opens. **Always.**
- If Play's licence check cannot be reached, a paying user keeps working for a grace period (see
  §5).

The existing test in `PROGRESS.md` §6 — *"Airplane mode on. The app must be fully usable"* —
stays exactly as it is, and now means something stronger: usable **with no ads, no billing, and
no network at all**.

The word **"Works offline"** on the home screen stays true and is not changed.

---

## 3. Stage A — free, with ads

### The ad format, and why

| Format | Where | Verdict |
|---|---|---|
| Bottom banner | Bottom of the brand / model / code / Saved lists | ✅ Yes. Always visible, never surprises you. |
| Interstitial | **On leaving the app only** | ✅ Yes, once, on exit |
| Interstitial | Between brands → models → codes | ❌ **No.** This is the single biggest uninstall driver for a utility app. A technician three taps from a fix will not forgive it. |
| "Native" ads blended into the lists | — | ❌ No. It would put invented content in a list of real error codes. That is the worst thing this app could do. |

### Ad placement rule

One banner, in a **fixed-height slot** at the bottom of a scrolling list. The slot is always the
same size whether or not an ad loaded, so a slow or failed ad never pushes the content around.
A slow ad must not cost a technician a tap on the fix step.

### The three ways people lose an AdMob account

These are the mistakes worth writing down, because each one is silent:

1. **Real ad IDs in a debug build.** Google counts that as fake traffic and can close the
   account with no warning. Debug builds must use Google's **official test unit IDs** — look
   them up in Google's AdMob documentation by name rather than from memory, and keep them in
   code, not in a note. The real IDs go in the release build only.
2. **Real IDs committed to git.** **This repository is public.** The real ad unit IDs and app ID
   live in build-type resources or a git-ignored properties file, never in the source.
3. **Clicking your own ads.** Never, in any build, for any reason.

### Size and permissions

| | |
|---|---|
| APK size today | 12.7 MB |
| Ad SDK adds | roughly 1.5–2.5 MB |
| CI size gate | 25 MB — **still passes**, no change needed |
| Permissions added | `INTERNET`, `ACCESS_NETWORK_STATE` — both merged in by the SDK's own manifest, so they appear even though we never type them |

### What the app says about itself

The line *"Works offline"* stays. There is no honest way to keep a *"requests no permissions"*
claim, so the app must stop implying one. One plain line, no marketing tone, on the Home screen
and wherever the app describes itself:

> **No account. Nothing about you is collected. Ad networks may see your device and IP address.**

### The privacy policy

AdMob requires one, with a URL. It must be truthful and it must match this app: no account, no
analytics, no crash reporting, no data uploaded by us. It is a public page, so **do not put
anything in it that is not true**, and do not use it to collect contact details we do not need.

---

## 4. The fail-safe — one file, edited by hand

This is the whole mechanism. There is no server to run and no vendor account to maintain.

**A public GitHub repository containing one file, served by GitHub Pages.** Create a new repo —
the old one was deleted — and the URL never changes afterwards.

`https://<user>.github.io/<repo>/config.json`:

```json
{
  "min_version_code": 2,
  "trial_ends_at": "2026-11-27",
  "update_url": "https://play.google.com/store/apps/details?id=com.acustad.app",
  "message_en": "AC Ustad is complete. Update to keep using it.",
  "message_ur": "AC Ustad mukammal ho gaya hai. Istemal ke liye update karein."
}
```

| Key | What it means |
|---|---|
| `min_version_code` | Any app older than this is blocked. **This is the kill switch. Edit this one number.** |
| `trial_ends_at` | When the free period ends, for the message and for any countdown |
| `update_url` | Where the Update button goes — the Play listing |
| `message_en` / `message_ur` | The blocking dialog, in both languages |

### How the app uses it — fail open, always

1. The app renders **immediately**. The network is never on the startup path.
2. A background fetch starts, with a **3 second** timeout.
3. The result is cached in `SharedPreferences`.
4. The cached `min_version_code` is compared with the running `versionCode`.
5. **Below the minimum** → the blocking dialog.
6. **The fetch failed** → use the last cached value.
7. **Never fetched at all** → use a harmless bundled default, and let the app run.

**It fails open, never closed.** A technician in a basement who cannot reach the server must
still be able to read the code in front of them. Failing closed would break the app for the one
person it exists for, in exchange for enforcing a rule against someone who is not breaking
anything.

### On a hard block, honestly

| ✅ It can | ❌ It cannot |
|---|---|
| Block anyone who opens the app **with a connection** | Reach someone in airplane mode |
| Force an update on people who are not looking for a workaround | Stop someone who edits one line of code |
| Survive a wipe or reinstall | Revoke an APK that is already installed |

**The real enforcement is Play's, not ours.** The paid stage in §5 gates content behind a Play
Billing licence that Google checks server-side. That is genuinely unbypassable. The config file
in this section is a speed bump against the casual majority — which is most people, and is worth
having.

### Optional hardening, later, probably never

Signing the config with an HMAC so people cannot edit *our* config to unblock their own copy.
Not worth the complexity: anyone determined can patch the check itself, so the config is not the
weak link. Revisit only if it ever becomes a real cost.

---

## 5. Stage B — the trial, and the day it ends

### The trial is a date, not a user count

The launch message says *"the first 600 technicians get it free for 2 months."* That is
**advertising, not a rule.** The rule is a single date.

There is no way to count 600 users without an account, a device identifier, or a server — and
all three cost more than they earn, and all three break promises this app has made. The number
is there to create urgency in the announcement. The date is what the code enforces.

| | The number 600 | The date |
|---|---|---|
| Where it lives | The advertisement | The code |
| Enforceable | No | Yes |
| Cost to keep honest | Accounts, or tracking | Nothing |
| Breaks a promise | Yes | No |

### How long

Decided when the phase starts, not now. The honest trade-off:

| Length | Good for | Bad for |
|---|---|---|
| 30 days | Plenty of ad revenue fast, less word of mouth | Feels rushed; few people tell their friends |
| 60 days | Long enough to actually use it a few times and recommend it | — |
| 90 days | Maximum reviews, maximum word of mouth, the best version of the network effect | Two or three months of ad revenue given up |

**Pick a weekday for the end date, not a date that lands on a Sunday**, and give at least
**two weeks of visible warning** in the app before it. A user who finds out on the day is a
user who feels betrayed; a user who was told twice is a user who pays.

### The last two weeks

A few days before `trial_ends_at`, the app shows a one-line notice, in the current language, on
the Home screen:

> **7 days left in the free period. After that, AC Ustad costs [price] to keep using.**

No dialog. No countdown timer. No sad face. One line, and then the app gets out of the way.

### The day itself

Flip one number in `config.json`:

```json
"min_version_code": 3
```

Commit. Every free user on an old build, next time they open the app with a connection, gets the
blocking dialog with an Update button. No new APK. No store submission. No email list.

### The blocking dialog

- **One screen. One button: Update.** No second button, no "remind me later" — this is the whole
  point of a hard block.
- The message in the current language, EN and Roman Urdu, from the config file.
- It must **never be a dead end**: if the Play listing is not reachable, the dialog says so in
  one line rather than spinning forever.
- **It must never brick a user with a broken machine.** A technician who is mid-job and the app
  locks is the exact user this product exists for. If the paid route genuinely cannot be
  reached — no Play account, no card, no network — the honest design allows the already-open code
  to stay readable. Decide this before shipping the block, not during an incident.

---

## 6. Stage C — paid, on Play

### Charging through Play, not by hand

| | Sideloaded APK | **Play Store** |
|---|---|---|
| Take a payment | Manual bank transfer, licence keys, receipts by hand | ✅ Play Billing |
| Pakistani Rupees, tax, refunds, chargebacks | All yours, by hand | ✅ Google handles it |
| Stop a re-signed copy | Impossible | ✅ Play Licensing blocks it automatically |
| Force a hard version block | Only the app's own word for it | ✅ Server-side licence gate |

**So the paid version is a Play release, and the paid version is gated on a licence.** No
purchase, no premium content. That is the hard block, and it is Google enforcing it.

### One-time purchase, not a subscription

A technician does not want a subscription to look up an error code. A one-off purchase fits the
use and is far easier to sell to someone who is being paid by the day.

**The pricing decision is not made here.** What decides it:

- What a single avoided wrong-part trip is worth to a company. That is the real number.
- What the alternative apps charge, if any.
- That a technician pays personally is rare — their employer usually does. See §7.

**Be honest with yourself about this:** "remove the ads" is a donation-shaped product with no
extra features, and donations convert worse than a paid-upfront purchase. A paid-upfront app
with a free trial usually sells better than free-with-ads-then-unlock. The ads are the trial
vehicle, not the business.

### The offline cost of a paid licence

Play's licence check is a network call. A paying user therefore gets an **offline grace period**
— the app keeps working fully offline for a period, and needs one online check to extend it.

This is the honest tradeoff, and it is the right one:

- The **free** app works offline forever, with no licence at all.
- The **paid** app works offline, and goes online occasionally, on its own, in the background,
  saying nothing.

Choose the grace window on the Play Console. Long enough that a technician working in a basement
for a fortnight is never stopped. **Never block a user over content they are actively reading
because a licence check timed out.** Fail open here too, and let the grace period carry it.

---

## 7. Stage D — companies, much later

Probably never, and that is a fine outcome.

The shape of it: an AC importer, a service company, or a chain sells licences to its own
technicians. They deploy the app to their people. The **app is still the same app** — still
offline, still no accounts, still nothing collected.

The pitch is not features, it is the privacy position, and it is a genuinely strong one:

> **Your technicians' browsing is nobody's business. No accounts, no tracking, no data leaving
> the phone, and it works where there is no signal.**

Which is only worth anything if the free app keeps its promise. Which is why **no accounts and
no analytics are still forbidden**, even after the ads are in.

---

## 8. Marketing copy

Bilingual, because the app is bilingual. The rules for all of it are the ones already in
`DESIGN.md` §6: sentence case, no exclamation marks, no emoji, no em-dash rhythm, no
"Get Started", buttons are verbs.

### The launch announcement

> **AC Ustad is free for two months.**
>
> Error codes for 62 air conditioner and solar inverter brands, in English and Roman Urdu.
> Works with no signal. No account, and nothing you do in it is collected.
>
> The first 600 technicians to start now get the free period. After that it costs [price] and
> there are no subscriptions.

Note what is **not** in it: a claim of no permissions, a claim of no data at all, or anything
that promises the ads are invisible. All three would be false.

### The trial-ending notice

> **[N] days left in the free period. After that, AC Ustad costs [price] to keep using it.**

### The update dialog

> **This version of AC Ustad has ended.**
>
> Update to keep using the app. Your saved codes come with you.

EN and Roman Urdu for each. The exact Roman Urdu is written in Stage A, alongside the strings
file, and reviewed by someone who reads it — a machine translation of a technician's instructions
is worse than no translation.

---

## 9. CI changes this needs

Every one of these is a deliberate, reviewed change. None is a "just this once" bypass.

| Gate | Change | Why |
|---|---|---|
| `build app` / **no permissions** | Allow `android.permission.INTERNET` and `android.permission.ACCESS_NETWORK_STATE` in addition to the one AGP injects | The ad SDK merges them in whether we like it or not |
| `build app` / **APK size** | No change. 25 MB ceiling, ~15 MB expected | Already verified by arithmetic |
| **No network-client gate** | **It does not exist and must be built** | ⚠️ See below |
| `verify data` / secret scan | No change | Ad IDs are not secrets, but real IDs still stay out of a public repo |

### ⚠️ A missing gate that this work exposes

`CI_CD.md` §1 documents a CI step that fails the build if the APK contains an HTTP client —
`okhttp3`, `retrofit2`, `okio`. **That step was never implemented.** `build-app.yml` has no such
check.

It has never mattered, because the app has no network code to catch. The moment an ad SDK lands,
the build guide claims a protection the build does not have. `PERMISSIONS.md` §4 says to verify
and not to assume; this is a case of the documentation being ahead of reality.

**Build it before the ads, not after.** It will pass the moment it is written — the app has no
HTTP client — and from then on it is the thing that notices if anything ever adds one by
accident.

---

## 10. Risks we are choosing to accept

Written down so that, if one of these bites, it was a decision and not a surprise.

| Risk | How bad | What we do about it |
|---|---|---|
| Ad revenue is small in a low-CPM market | The most likely outcome | Validate against real numbers after Stage A before spending more effort. Volume is the only lever we have. |
| The ads cost us users who trust the app | Real, and permanent | Keep the offline guarantee exactly. Never add accounts, analytics or crash reporting to "make it up". |
| The kill switch cannot reach an offline user | Certain, and unfixable | Accepted. Enforce the paid stage through Play's licence instead. |
| The config file's host goes down | Rare | Fail open. The app is unaffected. |
| AdMob changes its rules or pricing | Possible | The ads are one module. Swapping networks is a contained change, which is why the ad code must not touch the rest of the app. |
| A paid user is blocked by a licence check that timed out | Bad | Fail open, use the grace period, never block a user mid-job. |
| Ad IDs leak from a public repository | Low | Build-type resources only. Check before the first release. |

---

## 11. What we are NOT doing, ever

This is the longer and more important list, and it does not shrink when money arrives.

- ❌ No accounts, no login, no email, no phone number
- ❌ No analytics, no crash reporting, no telemetry of any kind
- ❌ No uploading anything a user did to us
- ❌ No ad SDK that also behaves like analytics. If one is ever proposed, it is a conversation,
  not a dependency.
- ❌ No "recommended" codes ordered by an ad network
- ❌ No changing the error-code data, the brand list, or the severity of anything, for money
- ❌ No interstitials during browsing
- ❌ No ad in the fix steps, or anything a technician might read as part of the answer
- ❌ No making the offline path depend on a licence check, in the free app, ever

That last one is the test. **If a change makes the app need the internet to answer a question
about a code, it does not ship.**

---

## 12. The checklists, per stage

**Before anything: the gate**

- [ ] Google Play developer account created and paid for
- [ ] AdMob account created, payments profile set up for Pakistan
- [ ] A public privacy-policy page exists, is truthful, and is linked from Play
- [ ] `PHASE_10` reviewed with a decision on the trial length and the end date

**Stage A — free with ads**

- [ ] `INTERNET` and `ACCESS_NETWORK_STATE` added to the CI allow-list, in the same commit
- [ ] `PERMISSIONS.md` and `RULES.md` RULE 14 rewritten, not bypassed
- [ ] The missing no-network-client CI gate built, and green before the SDK is added
- [ ] Real ad IDs in release-only resources; nothing secret committed
- [ ] Debug builds use Google's official test unit IDs
- [ ] Banner slot is fixed height; a failed or slow ad changes nothing
- [ ] Interstitial shows on exit only, once
- [ ] The offline test in `PROGRESS.md` §6 still passes with ads in the build
- [ ] The app's own description makes no claim that is now false
- [ ] APK size still under the CI ceiling
- [ ] Roman Urdu strings written and reviewed by a human reader

**Stage B — trial ends**

- [ ] Two weeks of in-app warning shown, in the current language
- [ ] The end date is a weekday
- [ ] `min_version_code` raised in `config.json` and committed
- [ ] A user who cannot reach the update URL sees a message, not a spinner
- [ ] The "never brick a user mid-job" decision is written down and implemented

**Stage C — paid**

- [ ] One-time purchase, Play Billing
- [ ] Purchased state cached locally; works offline
- [ ] Grace period long enough for a real job
- [ ] Ads gone in the paid build; the offline path unchanged
- [ ] Purchased users' saved codes survive every upgrade

---

## 13. The two things most likely to go wrong quietly

1. **The ads break the offline guarantee** — a spinner on launch, a layout that jumps when an ad
   fails, a "check your connection" message. The single most valuable test in this whole phase is
   the existing one: **airplane mode, every screen, every code.** If that ever needs a caveat,
   we got it wrong.
2. **The paid block bricks someone who is mid-job.** They are standing in front of an air
   conditioner with the panel open, the app says *update*, and there is no signal and no card.
   Decide what happens in that moment **before** the block ships, and write the decision down
   where the next person will find it.
