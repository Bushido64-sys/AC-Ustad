# PERMISSIONS.md — only what the ads need, and nothing else

**The source manifest declares zero permissions. The merged manifest carries only
what the AdMob SDK merges in: `INTERNET`, `ACCESS_NETWORK_STATE`,
`com.google.android.gms.permission.AD_ID` and the
`android.permission.ACCESS_ADSERVICES_*` family (Topics, attribution — the Privacy
Sandbox APIs the SDK measures and targets with), for Google's ad servers and
nothing else. That is a decision (ADS.md), not drift.**

---

## 1. The manifest, in full

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <application
        android:name=".UstadApp"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/Theme.AcUstad">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:configChanges="uiMode|orientation|screenSize|screenLayout|density"
            android:theme="@style/Theme.AcUstad">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

**There is no `<uses-permission>` element in this project's source, and there must never
be one typed.** `INTERNET`, `ACCESS_NETWORK_STATE` and `com.google.android.gms.permission.AD_ID`
reach the merged manifest from the AdMob SDK's own manifest — the app talks to Google's
ad servers through the SDK and makes
no network call of its own. No `READ_EXTERNAL_STORAGE`, no `CAMERA`, no location, no
`WRITE_EXTERNAL_STORAGE`, no `POST_NOTIFICATIONS`, no `VIBRATE`, no `QUERY_ALL_PACKAGES`.

### The one entry that will appear anyway

The merged manifest produced by the build contains exactly one permission that nobody wrote:

```
com.acustad.app.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION
```

The Android Gradle Plugin injects it from AGP 8 onwards. It is a **signature-level permission
the app defines for itself**, so that `registerReceiver` is safe on Android 13+ without
`RECEIVER_EXPORTED`. It grants the app access to nothing, is never shown to a user, and does
not appear in the installed-app permission list in a form that means anything to a technician.

The CI gate in `CI_CD.md` therefore allows the AGP self-permission, `INTERNET`,
`ACCESS_NETWORK_STATE`, `AD_ID` and the `ACCESS_ADSERVICES_*` family — and fails on
everything else. If you see anything outside that set, something genuinely wrong
was added.

## 2. Why everything else stays out

| Permission | Why it is not needed |
|---|---|
| storage read/write | the app's only file is its own cache copy of its own database |
| `POST_NOTIFICATIONS` | no notifications, no reminders, no background work. An error-code reference has nothing to notify about |
| location | a code's meaning depends on the brand and model, never on where you are |
| contacts / phone / SMS | nothing is shared or sent, ever |

## 3. Design consequences you must respect

- **No user-generated sharing.** No `Intent.ACTION_SEND`, no "share this fix with a colleague".
  It would need `FileProvider` and it would leak the database path. Copying a fix step is
  enough, if it is ever wanted.
- **No external browser for sources without asking.** A `source_url` opens in the browser via
  `ACTION_VIEW`, which hands the URL to another app. That is the user's explicit tap, it is
  the user's browser, and it requires no permission — but never auto-open, and never
  auto-fetch. 26 codes have no `source_url` and must show no link at all.
- **No clipboard write** without an explicit tap, if you ever add copy-to-clipboard for a code
  or a fix step. `ClipData` needs no permission, but Android 13+ shows a system toast, so do
  not surprise the user with it.
- **No file export**, no "share database", no backup file. Backups go through Android's own
  `allowBackup` and nothing else.

## 4. How to verify (do this, do not assume)

```bash
# installed permissions must be exactly what the ads need (dumpsys lists them
# individually, including each ACCESS_ADSERVICES_* member)
adb shell dumpsys package com.acustad.app | grep -A8 "requested permissions"

# the SOURCE manifest must contain no uses-permission (the merged one carries
# what the SDK brings — see §1)
grep -i "uses-permission" app/src/main/AndroidManifest.xml
```

The first must show nothing outside the §1 set; the
second must return nothing. Put both in `CI_CD.md` so a dependency that quietly
adds anything else fails the build instead of shipping.

## 5. If you ever think you need one

Stop. Ask the human first, in writing, with the specific screen and the specific reason. The
answer will almost always be a design change rather than a permission. A technician who is
asked for a permission they do not understand stops trusting the app, and then it does not
get used — which defeats the entire project.
