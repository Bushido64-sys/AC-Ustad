// Root build file. Plugins are declared here with `apply false` so each module
// picks up a single, already-resolved version from the catalog.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
