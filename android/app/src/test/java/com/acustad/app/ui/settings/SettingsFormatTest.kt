package com.acustad.app.ui.settings

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * The two pure formatters behind the Settings data block.
 *
 * Both exist because the alternative is a claim about the data that nothing measured. A hard-coded
 * "9 MB" in `strings.xml` and a truncated `built_at` are the same failure in different clothes: the
 * screen states something the database has not said. (trap 5)
 */
class SettingsFormatTest {

    // ── the data size ─────────────────────────────────────────────────────────

    @Test
    fun `the real knowledge base reads as a sensible size`() {
        // 9,273,344 bytes is the file as built on 2026-09-28. The assertion is on the *shape*,
        // because a data release moves the number and this test must not have to be edited when
        // it does — the thing worth pinning is that 8.8 MB is what a 9.27 MB file renders as.
        assertEquals("8.8 MB", formatDataSize(9_273_344L))
    }

    @Test
    fun `a small file is reported in bytes and a medium one in kB`() {
        assertEquals("812 bytes", formatDataSize(812L))
        assertEquals("1 kB", formatDataSize(1024L))
        assertEquals("900 kB", formatDataSize(921_600L))
    }

    @Test
    fun `an unmeasured size is null, never zero`() {
        // `File.length()` answers 0 for a file that is not there, and 0 is also the honest
        // reading of a genuinely empty database. Neither may be rendered as a size: the caller
        // omits the clause, which is a statement about nothing, rather than a false one.
        assertNull(formatDataSize(null))
        assertNull(formatDataSize(0L))
        assertNull(formatDataSize(-1L))
    }

    @Test
    fun `the decimal separator is a dot on every device`() {
        // `String.format("%.1f", …)` would follow the device locale, putting a comma inside an
        // otherwise English sentence. (RULE 13)
        val rendered = formatDataSize(1_048_576L)!!
        assertEquals("1.0 MB", rendered)
        assertEquals('.', rendered[1])
    }

    // ── the build date ────────────────────────────────────────────────────────

    @Test
    fun `an ISO timestamp is reduced to its date`() {
        assertEquals("2026-09-28", formatBuiltDate("2026-09-28T05:48:34Z"))
    }

    @Test
    fun `a value with no date in it is null, not a truncated string`() {
        // The "unknown" fallback in `CatalogDao.meta()`, an empty column, and anything a future
        // data release might write. All of them omit the line instead of showing a fragment.
        assertNull(formatBuiltDate("unknown"))
        assertNull(formatBuiltDate(""))
        assertNull(formatBuiltDate("   "))
        assertNull(formatBuiltDate(null))
    }

    @Test
    fun `a bare date is passed through unchanged`() {
        assertEquals("2026-09-26", formatBuiltDate("2026-09-26"))
    }
}
