package com.acustad.app.ui.theme

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * The palette contract, pinned as source text.
 *
 * This is a source-reading test, not a rendering test, and that is a deliberate admission. A
 * Compose test could assert that a chip is orange in dark mode and would prove it; it cannot be
 * written here, because there is no SDK, no emulator and no instrumented test in this project.
 * So this does the next best thing: it checks the thing that actually went wrong.
 *
 * ### What went wrong
 *
 * The dark palette lived in `res/values-night/colors.xml`. That is the obvious place for it and
 * it is **wrong the moment a user can override the theme**: Android chooses `values/` versus
 * `values-night/` from the *phone's* night setting and has no way to know that Settings says
 * Light. The theme override therefore changed which *shape* of scheme was built while the
 * *values* still came from the phone, so choosing Light on a dark phone produced a light
 * palette full of dark colours and looked like the control did nothing. (PROGRESS trap 22)
 *
 * ### What these tests prevent
 *
 * Reintroducing a night-qualified colour file, which would silently break the override again
 * with a green build and a passing test suite. The failure that was actually reported came from
 * a person looking at a screen, and these assertions exist so the next one does not have to.
 */
class PaletteContractTest {

    private val res = File("src/main/res")

    @Test
    fun `no colour file is night-qualified`() {
        // The whole regression in one assertion. A `values-night/colors.xml` means the palette
        // follows the phone again, and the Light option stops working.
        val nightColours = File(res, "values-night/colors.xml")
        assertTrue(
            "res/values-night/colors.xml must not exist: the dark palette is chosen in code, " +
                "not by Android's night qualifier. Both palettes live in values/ as colors.xml " +
                "and colors_dark.xml. See Theme.kt.",
            !nightColours.exists(),
        )
    }

    @Test
    fun `both palettes exist in the default folder`() {
        assertTrue("res/values/colors.xml is missing", File(res, "values/colors.xml").exists())
        assertTrue("res/values/colors_dark.xml is missing", File(res, "values/colors_dark.xml").exists())
    }

    @Test
    fun `the two palette files never define the same colour twice, and every token is accounted for`() {
        // A duplicate would be two sources of truth for one colour, which is the drift RULE 10
        // exists to prevent - and it would be invisible in review because both files look right.
        val light = definedColours(File(res, "values/colors.xml"))
        val dark = definedColours(File(res, "values/colors_dark.xml"))
        val overlap = light.intersect(dark)
        assertTrue(
            "these colours are defined in BOTH palette files, so one of them is never used: $overlap",
            overlap.isEmpty(),
        )

        // Every light token is either shared or has a twin. Anything else can only ever render
        // light, which is exactly how the reported Light-mode bug looked: a control that looked
        // wired up and changed nothing.
        val unexplained = light - SHARED - DARK_TWIN.keys
        assertTrue(
            "these light tokens are neither shared nor mapped to a dark twin, so they cannot " +
                "change with the theme: $unexplained",
            unexplained.isEmpty(),
        )
    }

    @Test
    fun `every token that differs between the palettes has a dark twin that exists`() {
        // The twin is not always a `_dark` suffix - design_tokens.xml names the dark text
        // `ink_invert` and the three lifted alert colours by what they are - so the mapping is
        // written out here rather than derived. Writing it out is the point: it is the document
        // that fails when a token is renamed on one side only, which is the shape of the real
        // bug in miniature. A control that looks wired up and changes nothing.
        val dark = definedColours(File(res, "values/colors_dark.xml"))

        val missing = DARK_TWIN.filterValues { it !in dark }
        assertTrue(
            "these dark tokens are mapped from a light token but do not exist in " +
                "colors_dark.xml, so that light token can never change with the theme: $missing",
            missing.isEmpty(),
        )

        // And the reverse: a dark token nobody reads is either a typo or dead weight.
        val unmapped = dark - DARK_TWIN.values.toSet()
        assertTrue(
            "these dark tokens are never used by any light token, so they are dead: $unmapped",
            unmapped.isEmpty(),
        )
    }

    @Test
    fun `the night-qualified window background uses the dark canvas`() {
        // The one place a values-night resource is still correct: the platform window is painted
        // before any Compose code runs, so it has to follow the phone. It must point at
        // canvas_dark, because `canvas` is now light in EVERY configuration - leaving it as
        // @color/canvas would put a white flash at the start of every dark launch.
        val theme = File(res, "values-night/themes.xml")
        assertTrue("res/values-night/themes.xml is missing", theme.exists())
        val text = theme.readText()
        assertTrue(
            "values-night/themes.xml must set windowBackground to @color/canvas_dark",
            text.contains("@color/canvas_dark"),
        )
        assertTrue(
            "values-night/themes.xml still points windowBackground at @color/canvas, which is " +
                "the light token and is now white in every configuration",
            !text.contains("@color/canvas>") && !text.contains("@color/canvas\""),
        )
    }

    private fun definedColours(file: File): Set<String> =
        Regex("""<color\s+name="([a-z_0-9]+)">""")
            .findAll(file.readText())
            .map { it.groupValues[1] }
            .toSet()

    private companion object {
        /**
         * Light token -> dark token, for every token whose value actually differs.
         *
         * These names are the guide's, from `design_tokens.xml`. The irregularity is the
         * guide's and is kept on purpose: a tidier `_dark`-everywhere convention invented in the
         * app would be a second naming scheme, which is a third place for someone to look.
         * PHASE_11's token drift is where that gets settled.
         */
        val DARK_TWIN = mapOf(
            "ink" to "ink_invert",
            "ink_muted" to "ink_muted_dark",
            "canvas" to "canvas_dark",
            "surface" to "surface_dark",
            "surface_alt" to "surface_alt_dark",
            "hairline" to "hairline_dark",
            "signal_deep" to "signal_on_dark",
            "signal_tint" to "signal_dark_surface",
            "signal_tint_3" to "signal_dark_surface",
            "signal_mid" to "signal_dark_text",
            "signal_stop" to "signal_dark_text",
            "signal_danger_border" to "signal_dark_text",
            "neutral_fill" to "neutral_fill_dark",
        )

        /**
         * Tokens with ONE name, because the value is identical in both palettes: the blues and
         * the one white. Duplicating `#1668A8` into a second file would be a second value to
         * forget to update, and this whole exercise was caused by two files disagreeing.
         */
        val SHARED = setOf(
            "blue_300", "blue_400", "blue_500", "blue_600", "blue_800", "on_accent",
        )
    }
}
