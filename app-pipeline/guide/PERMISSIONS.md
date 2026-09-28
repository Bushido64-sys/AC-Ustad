# PERMISSIONS.md — the app requests none

**This app declares zero permissions. That is a feature, and the strongest privacy position
available to a technician app that a working person is asked to trust with their phone.**

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

**There is no `<uses-permission>` element, and there must never be one.** No `INTERNET`, no
`ACCESS_NETWORK_STATE`, no `READ_EXTERNAL_STORAGE`, no `CAMERA`, no location, no
`WRITE_EXTERNAL_STORAGE`, no `POST_NOTIFICATIONS`, no `VIBRATE`, no `QUERY_ALL_PACKAGES`.

## 2. Why each of those stays out

| Permission | Why it is not needed |
|---|---|
| `INTERNET` | the knowledge base is bundled; there is no server (RULE 14) |
| `ACCESS_NETWORK_STATE` | nothing checks connectivity — there is no online state (RULE 15) |
| `CAMERA` | **no images, no scanning, no OCR.** Codes are typed or picked from a list (`ASSETS.md`) |
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
# installed permissions must be an empty list
adb shell dumpsys package com.acustad.app | grep -A5 "requested permissions"

# the merged manifest must contain no uses-permission
grep -i "uses-permission" app/build/intermediates/merged_manifests/*/AndroidManifest.xml
```

Both must return nothing. Put both in `CI_CD.md` so a dependency that quietly reintroduces
`INTERNET` fails the build instead of shipping.

## 5. If you ever think you need one

Stop. Ask the human first, in writing, with the specific screen and the specific reason. The
answer will almost always be a design change rather than a permission. A technician who is
asked for a permission they do not understand stops trusting the app, and then it does not
get used — which defeats the entire project.
