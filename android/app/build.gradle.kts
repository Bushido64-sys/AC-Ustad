import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

// AdMob IDs. Debug builds use Google's demo units (read this session off the
// official test-ads page), so a debug build can never generate invalid traffic
// against the owner's account. Release reads the git-ignored ads.properties;
// if it is absent (CI, a fresh clone) release falls back to the demo units
// with a warning rather than failing the build — CI never builds release.
val adsProps = Properties().apply {
    val file = rootProject.file("ads.properties")
    if (file.exists()) file.inputStream().use(::load)
}
fun adsId(key: String, demo: String): String {
    val envKey = when (key) {
        "appId" -> "AD_APP_ID"
        "banner" -> "AD_BANNER"
        "interstitial" -> "AD_INTERSTITIAL"
        "rewarded" -> "AD_REWARDED"
        "native" -> "AD_NATIVE"
        "appOpen" -> "AD_APP_OPEN"
        else -> key.uppercase()
    }
    // CI and store builds inject real unit IDs through the environment; the
    // local ads.properties remains the fallback for a dev machine; demo units
    // are the last resort so a release can never hard-fail the machine.
    return (System.getenv(envKey) ?: adsProps.getProperty(key) ?: demo).also {
        if (it == demo && System.getenv(envKey) == null && adsProps.getProperty(key) == null) {
            logger.warn("using demo $key unit: no $envKey env var and no ads.properties")
        }
    }
}

// Demo units from https://developers.google.com/admob/android/test-ads.
val DEMO_APP_ID = "ca-app-pub-3940256099942544~3347511713"
val DEMO_BANNER = "ca-app-pub-3940256099942544/9214589741"
val DEMO_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"
val DEMO_REWARDED = "ca-app-pub-3940256099942544/5224354917"
val DEMO_NATIVE = "ca-app-pub-3940256099942544/2247696110"
val DEMO_APP_OPEN = "ca-app-pub-3940256099942544/9257395921"

android {
    namespace = "com.acustad.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.acustad.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.0"

        // No testInstrumentationRunner yet: TESTING.md adds Compose UI tests in the phase
        // that needs them. The database tests run on the JVM against the real kb.sqlite.
        vectorDrawables { useSupportLibrary = false }
    }

    signingConfigs {
        // Applied only when all four secrets exist (CI release job); debug
        // builds and local dev keep Android's debug signing untouched.
        val releaseKeystore = System.getenv("RELEASE_KEYSTORE_B64")
        if (releaseKeystore != null && System.getenv("RELEASE_STORE_PASS") != null &&
            System.getenv("RELEASE_KEY_ALIAS") != null && System.getenv("RELEASE_KEY_PASS") != null
        ) {
            maybeCreate("release").apply {
                storeFile = file("release.keystore")
                storeType = "PKCS12"
                storePassword = System.getenv("RELEASE_STORE_PASS")
                keyAlias = System.getenv("RELEASE_KEY_ALIAS")
                keyPassword = System.getenv("RELEASE_KEY_PASS")
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ""
            isMinifyEnabled = false
            manifestPlaceholders["adsAppId"] = DEMO_APP_ID
            buildConfigField("String", "AD_UNIT_BANNER", "\"$DEMO_BANNER\"")
            buildConfigField("String", "AD_UNIT_INTERSTITIAL", "\"$DEMO_INTERSTITIAL\"")
            buildConfigField("String", "AD_UNIT_REWARDED", "\"$DEMO_REWARDED\"")
            buildConfigField("String", "AD_UNIT_NATIVE", "\"$DEMO_NATIVE\"")
            buildConfigField("String", "AD_UNIT_APP_OPEN", "\"$DEMO_APP_OPEN\"")
        }
        release {
            // Phase 9: R8 on for the release build. The phone test loop installs the debug
            // APK, so this changes nothing about CI (which builds debug only) or daily
            // testing — it hardens the artifact that ships as v1.0.0. Raw SQLite needs no
            // keep rules (see proguard-rules.pro); fonts are kept explicitly.
            isMinifyEnabled = true
            isShrinkResources = true
            if (System.getenv("RELEASE_KEYSTORE_B64") != null) {
                signingConfig = signingConfigs.getByName("release")
            }
            manifestPlaceholders["adsAppId"] = adsId("appId", DEMO_APP_ID)
            buildConfigField("String", "AD_UNIT_BANNER", "\"${adsId("banner", DEMO_BANNER)}\"")
            buildConfigField(
                "String",
                "AD_UNIT_INTERSTITIAL",
                "\"${adsId("interstitial", DEMO_INTERSTITIAL)}\"",
            )
            buildConfigField("String", "AD_UNIT_REWARDED", "\"${adsId("rewarded", DEMO_REWARDED)}\"")
            buildConfigField("String", "AD_UNIT_NATIVE", "\"${adsId("native", DEMO_NATIVE)}\"")
            buildConfigField("String", "AD_UNIT_APP_OPEN", "\"${adsId("appOpen", DEMO_APP_OPEN)}\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        // AGP 8+ does not generate BuildConfig unless asked. UstadApp reads
        // BuildConfig.DEBUG for its debug-only StrictMode (PHASE_9 §2).
        buildConfig = true
    }

    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "/META-INF/DEPENDENCIES",
                "/META-INF/LICENSE*",
            )
        }
        // The database is stored in assets/ and copied to the cache directory at first
        // launch, so it is never memory-mapped and needs no special handling here.
    }

    lint {
        // The critical rules are enforced by the workflow script (no permission, database
        // hash, APK size), not by lint, because those must not be able to be silenced.
        // Lint keeps its default behaviour for genuine code errors.
        abortOnError = true
        warningsAsErrors = false
        // Advisories that would otherwise block a first build without protecting anything:
        // the launcher icon is intentionally adaptive-only (minSdk 26, so anydpi-v26 covers
        // every device), and unused dimens are documentation of the type scale.
        disable += setOf("GradleDependency", "IconMissingDensityFolder", "UnusedResources")
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // Compose is versioned as one unit by the BOM. Never pin these individually.
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    // One of the five permitted icons: the back arrow. (RULES.md RULE 11)
    implementation(libs.androidx.compose.material.icons.core)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Raw SQLite only - no Room. The shipped database is read-only and nothing on device
    // needs migrating (DEPENDENCIES.md §2).
    implementation(libs.androidx.sqlite)
    implementation(libs.androidx.sqlite.ktx)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)

    // Ads, by decision (ADS.md). Debug serves Google's demo units; release serves
    // the git-ignored ads.properties. UMP is the consent flow the ads require.
    implementation(libs.google.gma.ads)
    implementation(libs.google.ump)
}
