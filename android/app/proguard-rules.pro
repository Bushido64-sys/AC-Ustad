# The app uses raw SQLite with no reflection, no serialization library and no DI
# framework, so the default Android/Kotlin rules cover almost everything. The entries
# below are the two things R8 can genuinely get wrong for this app.

# Keep the bundled font files. A stripped font resource is the classic R8 casualty and
# the app would silently fall back to the system family, breaking the design.
-keepclassmembers class **.R$font { *; }
-keep class **.R$font { *; }

# Phase 9 §3: release builds carry no Log output. Debug keeps every Log (the
# search/detail failure logs are error paths, not hot paths); release strips them
# at the bytecode level so a forgotten debug line can never cost time on a cheap
# phone or leak a query string into logcat.
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}

# The shipped database is opened by path, never by class name, so no keep rules are
# needed for it. This entry exists only to make that explicit for a future reader.

# If a crash-reporting or analytics library is ever added, revisit this file - the app
# currently ships with neither (RULE 14).
