# DEPENDENCIES.md — the dependency list, and what is deliberately missing

**Principle: every dependency must buy something this app needs. If you cannot name the
screen it improves, do not add it.**

---

## 1. What is in the build

| Dependency | Why it is here |
|---|---|
| `androidx.core:core-ktx` | `NotificationCompat` not needed, but `ContextCompat`, `ActivityResult` basics |
| `androidx.activity:activity-compose` | `ComponentActivity`, `setContent`, `enableEdgeToEdge` |
| `androidx.compose.*` (BOM) | the whole UI |
| `androidx.compose.material3:material3` | `Text`, `Surface`, `Scaffold`, `Snackbar`, `TextField` primitives — **restyled to the tokens, never used with default colours** |
| `androidx.compose.material:material-icons-core` | **the only 5 icons**: ArrowBack, Search, Star, StarBorder, OpenInNew / KeyboardArrowDown |
| `androidx.lifecycle:lifecycle-runtime-compose` | `collectAsStateWithLifecycle` |
| `androidx.lifecycle:lifecycle-viewmodel-compose` | screen state holders |
| `androidx.navigation:navigation-compose` | the 7 screens, with typed route args carrying `uid` |
| `androidx.sqlite:sqlite-ktx` | `SupportSQLiteOpenHelper`, `Cursor` extensions — **the only data dependency** |
| `org.jetbrains.kotlinx:kotlinx-coroutines-android` | `Dispatchers.IO`, debounce, `Flow` |
| `com.google.android.gms:play-services-ads` | the ad SDK — banner, app open, interstitial, rewarded, native (ADS.md). The only dependency allowed a network call |
| `com.google.android.ump:user-messaging-platform` | the consent flow the ads require |
| IBM Plex Sans / Sans Condensed / Mono (font files) | the typography. Bundled, not a dependency — see `ASSETS.md` |

**All Compose artifacts from a single BOM.** Never mix BOM versions; never let a transitive
dependency pull a second Compose runtime.

## 2. What is deliberately NOT in the build

| Not included | Why |
|---|---|
| **Room** | 4418 read-only rows, 9 tables, no writes except `favourites`, no on-device migrations. An ORM would add codegen, a migration story and a dependency for zero benefit (`PHASE_2_DATA_LAYER.md` §1) |
| **Hilt / Dagger / Koin** | one database handle, no network layer, no repository graph. Constructor wiring in one `Application` class is enough. **If you add DI, it must not add a code-generation step to the build** |
| **Retrofit / OkHttp / Ktor / any other HTTP client** | the app makes no network call of its own (RULE 14). The ad SDK brings its own stack — that is the documented exception (ADS.md), and no second one is allowed. If an HTTP client appears in the graph, the architecture has been broken |
| **Glide / Coil / Picasso** | **no images are displayed.** Zero bitmaps, zero image loaders. `ASSETS.md` explains why |
| **Firebase / analytics / crash reporting** | no telemetry leaves the phone (RULE 14) |
| **DataStore / preferences library** | two settings: content language and theme. `SharedPreferences` is enough, or a `MutableStateFlow` in memory with `SharedPreferences` for persistence |
| **WorkManager** | no background work exists (RULE 15) |
| **Accompanist / Google Fonts provider** | fonts are bundled as files; a runtime font downloader would need a network call |
| **Navigation with saved state complexity** | 7 screens, one stack. `navigation-compose` is enough; do not build a multi-backstack |

## 3. Version pairing that actually works

Pick one pairing and check it against the current stable release of the Android Gradle Plugin
at build time — do not mix a Kotlin compiler extension with a mismatched Compose compiler.

```
Kotlin 2.x  →  the Compose Compiler Gradle plugin (org.jetbrains.kotlin.plugin.compose)
              applied alongside the Kotlin plugin. No composeOptions.kotlinCompilerExtensionVersion.
```

If you are on Kotlin 1.9.x instead, then `composeOptions { kotlinCompilerExtensionVersion = "…" }`
is the mechanism. **One or the other, never both.** Verify with `./gradlew build` on a clean
checkout before writing any app code.

`minSdk 26`, `compileSdk`/`targetSdk` = latest stable, `jvmTarget` matched to the Kotlin
version. Enable `buildFeatures.compose = true` and `compose = true` in the Compose options
where the AGP version requires it.

## 4. Version catalog

Use `gradle/libs.versions.toml` for every version. No inline version strings in
`build.gradle.kts`. Group names: `androidx`, `compose`, `kotlin`, `ksp` (unused — no
annotation processing at all, which is a direct benefit of dropping Room and Hilt).

## 5. Adding a dependency

Before you add one, answer in the commit message:

1. Which screen gets better because of it?
2. What does it cost in APK size, build time and method count?
3. Does it need a permission, a network call, or a code-generation step?

If (3) is yes to any, the answer is no.

## 6. R8 / ProGuard

- `minifyEnabled true` for release, `shrinkResources true`.
- No `-keep` rules are needed for raw SQLite or the model classes (they are not reflected
  over), but **keep the font resources** and check the release APK actually renders IBM Plex
  — a stripped font resource is a classic R8 casualty.
- Verify a release build, not just debug, before every release (`PHASE_9`).
