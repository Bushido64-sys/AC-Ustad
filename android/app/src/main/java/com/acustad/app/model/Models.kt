package com.acustad.app.model

import androidx.compose.runtime.Immutable

/**
 * The app's read models. Shapes come from DATA_SCHEMA.md §5 and are pinned by
 * `SchemaContractTest`, which is the authority when the guide and the database disagree.
 *
 * Everything is `@Immutable` because these are handed straight to Compose and must not
 * trigger recomposition churn.
 */

/** Which language the code *content* is shown in. UI labels, brand names and model names
 *  are always English (RULES.md RULE 13). */
enum class ContentLanguage { EN, UR;

    /**
     * Picks one side of a bilingual pair, falling back to the other rather than showing
     * nothing.
     *
     * Blank counts as missing, not just null. 2,467 of the 4,418 codes carry no note in
     * either language, and a half-filled field is a real possibility after a data release —
     * either way, a technician must never be shown an empty box where a translation should
     * be. When both sides are blank the result is null, and the UI hides the whole block
     * instead (PHASE_4_CODE_DETAIL.md §4).
     */
    fun pick(en: String?, ur: String?): String? = when (this) {
        EN -> en.orTrimmed() ?: ur.orTrimmed()
        UR -> ur.orTrimmed() ?: en.orTrimmed()
    }

    private fun String?.orTrimmed(): String? = this?.trim()?.ifEmpty { null }
}

/** AC or solar inverter. The two categories on the home screen. */
enum class CategoryId { AC, INVERTER }

@Immutable
data class CategoryCounts(
    val acBrands: Int,
    val inverterBrands: Int,
    val acCodes: Int,
    val inverterCodes: Int,
)

/**
 * A brand. `id` is the TEXT slug, e.g. `growatt` — there is no `uid` column and `id` is not
 * an integer. `categories` is the raw JSON array string from the database, e.g. `["ac"]`;
 * 2 of the 62 brands appear in both categories, which is why the two per-category brand
 * counts sum to 64 and not 62.
 */
@Immutable
data class Brand(
    val id: String,
    val name: String,
    val categoriesJson: String,
    val codeCount: Int,
    val seriesCount: Int,
    val notes: String?,
) {
    fun isIn(category: CategoryId): Boolean = categoriesJson.contains(
        when (category) {
            CategoryId.AC -> "\"ac\""
            CategoryId.INVERTER -> "\"inverter\""
        }
    )
}

/**
 * A model line.
 *
 * `seriesId` is deliberately kept alongside `uid` and `brandId`, because **`series.id` is
 * unique only within a brand** — 6 values repeat across brands, one of them 14 times. Any
 * query that scopes to a model line must bind the brand as well, and carrying the pair in the
 * model makes it very hard to forget. See `ScopedSeries`.
 */
@Immutable
data class Series(
    val uid: String,
    val seriesId: String,
    val brandId: String,
    val name: String,
    val category: String,
    val unitType: String?,
    val codeCount: Int,
    val notes: String?,
) {
    /** The two-part identity every scoped code query needs. */
    val scope: ScopedSeries get() = ScopedSeries(seriesId, brandId)
}

/** A model line's identity, as the database actually keys it. */
@Immutable
data class ScopedSeries(val seriesId: String, val brandId: String)

@Immutable
data class CodeSummary(
    val id: Long,
    val uid: String,
    val code: String,
    val titleEn: String?,
    val titleUr: String?,
    val severity: String,
    val isFault: Boolean,
    val display: String,
) {
    fun title(language: ContentLanguage): String? = language.pick(titleEn, titleUr)
}

@Immutable
data class CodeDetail(
    val summary: CodeSummary,
    val meaningEn: String?,
    val meaningUr: String?,
    val notesEn: String?,
    val notesUr: String?,
    val confidence: String,
    val sourceType: String?,
    val sourceTitle: String?,
    val sourceUrl: String?,
    val blinkPattern: String?,
    val relatedCodes: String?,
    /** Carried so the screen can name the machine and scope anything it later needs. The uid is
     *  `brand/series/code`, so these are the same identity in usable pieces. */
    val seriesId: String,
    val brandId: String,
    /** In database order. `index` is 0-based; the number a technician reads is `index + 1`. */
    val causes: List<BilingualText>,
    val solutions: List<BilingualText>,
    val isFavourite: Boolean,
) {
    val scope: ScopedSeries get() = ScopedSeries(seriesId, brandId)

    fun meaning(language: ContentLanguage): String? = language.pick(meaningEn, meaningUr)
    fun notes(language: ContentLanguage): String? = language.pick(notesEn, notesUr)
}

/** One cause or one fix step, in both languages. */
@Immutable
data class BilingualText(val index: Int, val en: String?, val ur: String?) {
    /** The number shown to the user. The stored `idx` is 0-based in the shipped database. */
    val displayNumber: Int get() = index + 1
    fun text(language: ContentLanguage): String? = language.pick(en, ur)
}

/** One row of the `favourites` table — which is two columns and nothing else. */
@Immutable
data class FavouriteRow(val codeId: Long, val createdAt: String)

/** A saved code, joined back to `codes` for display. The saved row stores no names. */
@Immutable
data class FavouriteItem(
    val codeId: Long,
    val code: String,
    val titleEn: String?,
    val titleUr: String?,
    val severity: String,
    val brandId: String,
    val brandName: String,
    val seriesUid: String,
    val seriesId: String,
    val seriesName: String,
    /**
     * The saved row's own `created_at`, ISO-8601 UTC, exactly as stored.
     *
     * It is read for one reason: Undo has to put the row back where it was. Re-inserting with
     * a fresh timestamp would move the code to the top of a newest-first list, so a
     * remove-then-undo would visibly reorder the list instead of restoring it.
     */
    val createdAt: String,
    /**
     * Whether this is a fault at all. 569 of the 4,418 codes are indicators, parameters and
     * self-clear entries, and those must never be rendered with a severity word — a technician
     * reading "STOP" on `Parameter P003` has been told something false. (DESIGN.md §3)
     */
    val isFault: Boolean = true,
) {
    val scope: ScopedSeries get() = ScopedSeries(seriesId, brandId)
    fun title(language: ContentLanguage): String? = language.pick(titleEn, titleUr)
}

@Immutable
data class KbMeta(
    val kbVersion: String,
    val builtAt: String,
    val brandCount: Int,
    val seriesCount: Int,
    val codeCount: Int,
)
