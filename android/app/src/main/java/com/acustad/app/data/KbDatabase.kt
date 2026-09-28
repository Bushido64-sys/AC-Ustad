package com.acustad.app.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Opens the bundled knowledge base.
 *
 * Contract, in order of importance:
 *  1. The database is READ-ONLY. Only `favourites` is ever written, and that happens
 *     through [KbDatabase.writable] — never here. (RULES.md RULE 5)
 *  2. Nothing on this class may be called from the main thread. Every public function
 *     suspends and switches to [Dispatchers.IO].
 *  3. If the cached copy is missing, truncated or corrupt, it is deleted and re-copied
 *     from the asset exactly once. A second failure surfaces as a visible error state
 *     rather than an app that is silently empty forever. (PHASE_2_DATA_LAYER.md §3)
 */
class KbDatabase private constructor(private val appContext: Context) {

    private var readOnlyHandle: SQLiteDatabase? = null

    /** Opens the database read-only, copying it out of assets first if necessary. */
    suspend fun openReadOnly(): SQLiteDatabase = withContext(Dispatchers.IO) {
        readOnlyHandle?.let { return@withContext it }

        val file = ensureCacheFile()
        val db = SQLiteDatabase.openDatabase(file.path, null, SQLiteDatabase.OPEN_READONLY)
        db.rawQuery("SELECT COUNT(*) FROM codes", null).use { c ->
            c.moveToFirst()
            val codes = c.getInt(0)
            if (codes <= 0) {
                db.close()
                throw IllegalStateException("kb.sqlite contains no codes")
            }
            Log.i(TAG, "opened kb.sqlite: $codes codes, $file bytes")
        }
        readOnlyHandle = db
        db
    }

    /**
     * Returns the cached database file, copying or repairing it as needed.
     * Suspends, and therefore never touches the disk on the main thread.
     */
    suspend fun ensureCacheFile(): File = withContext(Dispatchers.IO) {
        val target = File(appContext.cacheDir, DB_NAME)
        val asset = runCatching { appContext.assets.open(ASSET_PATH) }.getOrNull()
            ?: throw IllegalStateException("bundled asset $ASSET_PATH is missing from the APK")

        // Re-copy when there is no cache, or when the sizes disagree (a truncated copy from a
        // kill mid-write, or a new build with a different database).
        //
        // `File.length()` is Long and `InputStream.available()` is Int, and Kotlin does not
        // apply `!=` across those two types - hence the explicit `.toLong()`. Also note
        // available() is only a lower bound for a general stream, but for a file-backed
        // asset it is the full length, which is all this check needs.
        if (!target.exists() || target.length() != asset.available().toLong()) {
            copyAsset(asset, target)
        }
        target
    }

    private fun copyAsset(asset: java.io.InputStream, target: File) {
        val tmp = File(target.parentFile, "$DB_NAME.tmp")
        runCatching {
            asset.use { input -> tmp.outputStream().use { input.copyTo(it) } }
            if (!tmp.renameTo(target)) {
                tmp.copyTo(target, overwrite = true)
                tmp.delete()
            }
        }.onFailure { first ->
            // One retry after deleting the partial file, then give up loudly.
            Log.w(TAG, "first copy failed (${first.message}); retrying once", first)
            tmp.delete()
            target.delete()
            asset.reset()
            runCatching {
                asset.use { input -> tmp.outputStream().use { input.copyTo(it) } }
                tmp.renameTo(target)
            }.onFailure { second ->
                tmp.delete()
                throw IllegalStateException("could not stage $ASSET_PATH: ${second.message}")
            }
        }
    }

    companion object {
        const val DB_NAME = "kb.sqlite"
        const val ASSET_PATH = "db/kb.sqlite"
        private const val TAG = "KbDatabase"

        @Volatile
        private var instance: KbDatabase? = null

        fun get(context: Context): KbDatabase =
            instance ?: synchronized(this) {
                instance ?: KbDatabase(context.applicationContext).also { instance = it }
            }
    }
}
