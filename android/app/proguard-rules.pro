# The app uses raw SQLite with no reflection, no serialization library and no DI
# framework, so the default Android/Kotlin rules cover almost everything. The entries
# below are the two things R8 can genuinely get wrong for this app.

# Keep the bundled font files. A stripped font resource is the classic R8 casualty and
# the app would silently fall back to the system family, breaking the design.
-keepclassmembers class **.R$font { *; }
-keep class **.R$font { *; }

# The shipped database is opened by path, never by class name, so no keep rules are
# needed for it. This entry exists only to make that explicit for a future reader.

# If a crash-reporting or analytics library is ever added, revisit this file - the app
# currently ships with neither (RULE 14).
