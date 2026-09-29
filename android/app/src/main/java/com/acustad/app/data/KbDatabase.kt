package com.acustad.app.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.core.content.pm.PackageInfoCompat
import java.io.File
import java.io.InputStream

/**
 * Opens the bundled knowledge base.
 *
 * Contract, in order of importance:
 *
 *  1. **Only `favourites` is ever written.** Every other table is read-only by discipline
 *     (RULES.md RULE 5). A single connection is opened READ-WRITE because the saved list has
 *     to be writable and two connections to the same file only invite lock contention. The
 *     read DAOs physically contain no write statements, which is the actual guarantee — not
 *     the connection flag.
 *  2. **Nothing here may run on the main thread.** Callers suspend and switch to
 *     `Dispatchers.IO`.
 *  3. A missing, truncated or corrupt cache copy is repaired by re-copying from the asset
 *     exactly once, then surfaced as a visible failure. An app that is silently empty forever
 *     is worse than one that says it cannot open its data. (PHASE_2_DATA_LAYER.md §3)
 */
class KbDatabase private constructor(private val appContext: Context) {

    @Volatile
    private var handle: SQLiteDatabase? = null

    /** The open connection. Callers must already be on a background thread. */
    suspend fun open(): SQLiteDatabase {
        handle?.let { return it }
        return synchronized(this) {
            handle ?: openLocked().also { handle = it }
        }
    }

    private fun openLocked(): SQLiteDatabase {
        val file = stageFile()
        return try {
            openReadWrite(file)
        } catch (first: Exception) {
            // A corrupt or half-written cache copy is the one failure worth recovering from
            // silently: delete it and try exactly once more.
            Log.w(TAG, "open failed (${first.message}); re-copying once", first)
            File(file.parentFile, DB_NAME).delete()
            val replacement = stageFile()
            try {
                openReadWrite(replacement)
            } catch (second: Exception) {
                throw IllegalStateException(
                    "could not open $ASSET_PATH after re-copying: ${second.message}", second
                )
            }
        }
    }

    private fun openReadWrite(file: File): SQLiteDatabase {
        val db = SQLiteDatabase.openDatabase(file.path, null, SQLiteDatabase.OPEN_READWRITE)
        // Prove the copy is real before anything reads it. A silently empty app is the worst
        // failure mode: the user sees a blank list and concludes the model is missing.
        val codes = db.rawQuery("SELECT COUNT(*) FROM codes", null).use { c ->
            c.moveToFirst()
            c.getInt(0)
        }
        if (codes <= 0) {
            db.close()
            throw IllegalStateException("kb.sqlite contains no codes")
        }
        Log.i(TAG, "opened $DB_NAME: $codes codes, ${file.length()} bytes")
        return db
    }

    /**
     * Ensures a complete copy of the asset exists in the cache and returns it.
     *
     * The decision is [shouldRestage], which is pure and tested. This method only supplies the
     * four facts it needs: does the cache exist, how long is it, how long is the asset, and what
     * `versionCode` wrote it.
     *
     * The length comparison alone is what this used to do, and it was a bug: a data release
     * whose `kb.sqlite` matched the previous file size byte for byte was never picked up, so the
     * app kept serving the old answers. See `Staging.kt`.
     *
     * The asset stream is always closed here, including on the path where no copy is needed —
     * otherwise a warm start leaks a file handle every single launch.
     */
    private fun stageFile(): File {
        val target = File(appContext.cacheDir, DB_NAME)
        val stamp = File(appContext.cacheDir, STAMP_NAME)
        val exists = target.exists()
        val assetBytes = assetLength()
        val version = currentVersionCode()

        if (!shouldRestage(
                cachedExists = exists,
                cachedLength = if (exists) target.length() else -1L,
                assetLength = assetBytes,
                stampedVersion = readStamp(stamp),
                currentVersion = version,
            )
        ) {
            return target
        }

        copyAsset(target)
        writeStamp(stamp, version)
        return target
    }

    /** The asset's length, read from the zip entry header. No file content is read. */
    private fun assetLength(): Long = openAsset().use { it.available().toLong() }

    /**
     * The `versionCode` of the running APK, or [UNKNOWN_VERSION] if it cannot be read.
     *
     * This runs on the IO dispatcher, like everything else that opens the database, so the one
     * binder call is never on the main thread.
     */
    @Suppress("DEPRECATION")
    private fun currentVersionCode(): Int = runCatching {
        val info = appContext.packageManager.getPackageInfo(appContext.packageName, 0)
        PackageInfoCompat.getLongVersionCode(info).toInt()
    }.getOrElse {
        Log.w(TAG, "could not read versionCode; treating it as unknown", it)
        UNKNOWN_VERSION
    }

    /** null means "no stamp", which always re-copies. An unreadable stamp is treated as absent. */
    private fun readStamp(stamp: File): Int? =
        if (!stamp.exists()) null
        else runCatching { stamp.readText().trim().toIntOrNull() }.getOrNull()

    /**
     * A stamp that cannot be written costs one redundant re-copy on the next launch and nothing
     * else, so a failure here is logged and otherwise ignored. Getting the database staged
     * matters more than recording which build staged it.
     */
    private fun writeStamp(stamp: File, version: Int) {
        runCatching { stamp.writeText("$version\n") }
            .onFailure { Log.w(TAG, "could not write the staging stamp", it) }
    }

    private fun openAsset(): InputStream =
        runCatching { appContext.assets.open(ASSET_PATH) }.getOrNull()
            ?: throw IllegalStateException("bundled asset $ASSET_PATH is missing from the APK")

    /** Does not close the stream: the caller owns it. */
    private fun copyAsset(target: File) {
        openAsset().use { asset ->
            val tmp = File(target.parentFile, "$DB_NAME.tmp")
            try {
                tmp.outputStream().use { asset.copyTo(it) }
                if (!tmp.renameTo(target)) {
                    tmp.copyTo(target, overwrite = true)
                    tmp.delete()
                }
            } catch (e: Exception) {
                tmp.delete()
                throw IllegalStateException("could not stage $ASSET_PATH: ${e.message}", e)
            }
        }
    }


    companion object {
        const val DB_NAME = "kb.sqlite"
        const val ASSET_PATH = "db/kb.sqlite"

        /** Records which `versionCode` staged the cache copy. In `cacheDir`, so it disappears
         *  with the database it describes and a cleared cache simply re-copies. */
        private const val STAMP_NAME = "kb.stamp"
        private const val TAG = "KbDatabase"
        private const val UNKNOWN_VERSION = -1

        @Volatile
        private var instance: KbDatabase? = null

        fun get(context: Context): KbDatabase =
            instance ?: synchronized(this) {
                instance ?: KbDatabase(context.applicationContext).also { instance = it }
            }
    }
}

/** `Cursor.use` with a guaranteed moveToFirst, for the common single-row read. */
internal inline fun <T> Cursor.firstRow(read: (Cursor) -> T): T? =
    use { c -> if (c.moveToFirst()) read(c) else null }

/** Reads every row of a query. */
internal inline fun <T> Cursor.mapRows(read: (Cursor) -> T): List<T> =
    use { c ->
        val out = ArrayList<T>(c.count)
        while (c.moveToNext()) out.add(read(c))
        out
    }
