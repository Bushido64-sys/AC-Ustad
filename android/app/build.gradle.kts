import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.acustad.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.acustad.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        // No testInstrumentationRunner yet: TESTING.md adds Compose UI tests in the phase
        // that needs them. The database tests run on the JVM against the real kb.sqlite.
        vectorDrawables { useSupportLibrary = false }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ""
            isMinifyEnabled = false
        }
        release {
            // R8 on, but do NOT enable it for the first signed release: the phone test loop
            // uses the debug APK. Turn on minify once the app is feature-complete and
            // verified on a release build (PHASE_9 §3).
            isMinifyEnabled = false
            isShrinkResources = false
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
}
