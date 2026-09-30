package com.acustad.app.ui.common

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * The content-colour contract of the three containers, pinned as source text.
 *
 * Like [com.acustad.app.ui.theme.PaletteContractTest], this is a source-reading test and that is
 * a deliberate admission: there is no SDK and no emulator on the build machine, so nothing here
 * can render a pixel. What it can see is the exact line the bug lived on.
 *
 * ### The bug
 *
 * A `Surface` with no `contentColor` derives one from its own fill:
 * `contentColorFor(surfaceVariant)` returns `onSurfaceVariant`, which `Theme.kt` maps to
 * `ink_muted`. So every `Text` inside a `RaisedPanel` that does not name a colour renders
 * `ink_muted` on `surface_alt` — **4.15:1 in light mode**, under the 4.5 AA bar, on the one
 * block `PHASE_11` §2.1 exists to protect. It is light-mode only (dark is 6.93:1), which is how
 * it survives a dark-mode screenshot review and fails on a cheap LCD in sunlight.
 *
 * Three call sites were affected — the detail screen's meaning and its source line, and
 * `SeriesScreen`'s brand notes — and one of them documented the fix in a KDoc without the code
 * ever implementing it. That is the failure mode this test exists for: a claim described as done
 * that was only reasoned about.
 *
 * ### What it does not prove
 *
 * It does not prove the meaning block *renders* ink. It proves the component states its content
 * colour, so the default cannot come back quietly, and it proves the default is still the muted
 * one, so deleting the line is a failing test rather than a silent 4.15:1.
 */
class ContainerContentColorTest {

    private val components = File("src/main/java/com/acustad/app/ui/common/Components.kt").readText()
    private val theme = File("src/main/java/com/acustad/app/ui/theme/Theme.kt").readText()

    @Test
    fun `the muted default still exists, so leaving contentColor out of a container is a bug`() {
        // The premise. If someone remaps onSurfaceVariant to full ink, this fails - and that is
        // the right moment to decide whether RaisedPanel's override is still load-bearing,
        // rather than discovering it was by reading 4.15:1 off a phone.
        assertTrue(
            "Theme.kt no longer maps onSurfaceVariant to ink_muted, so the default content " +
                "colour of surfaceVariant is no longer the 4.15:1 failure this test guards. " +
                "Re-measure PHASE_11 §2.1 and update RaisedPanel's contract deliberately.",
            theme.contains("onSurfaceVariant = colorResource(R.color.ink_muted)"),
        )
    }

    @Test
    fun `RaisedPanel fills with surface_alt and states ink as its content colour`() {
        val panel = bodyOf("RaisedPanel")
        assertTrue(
            "RaisedPanel must fill with surface_variant (level 2, quoted content)",
            panel.contains("color = MaterialTheme.colorScheme.surfaceVariant"),
        )
        assertTrue(
            "RaisedPanel must pass contentColor = onSurface. Without it Material3 derives " +
                "contentColorFor(surfaceVariant) = onSurfaceVariant = ink_muted, which is " +
                "4.15:1 on this fill in light mode - PHASE_11 §2.1, PROGRESS check 34.",
            panel.contains("contentColor = MaterialTheme.colorScheme.onSurface"),
        )
        assertTrue(
            "RaisedPanel must never name onSurfaceVariant as its content colour",
            !panel.contains("onSurfaceVariant"),
        )
    }

    @Test
    fun `both level-1 containers state their content colour instead of inheriting it`() {
        for (name in listOf("BorderedPanel", "BorderedRow")) {
            assertTrue(
                "$name must state contentColor explicitly, so both levels say what their text " +
                    "colour is and neither can drift by default alone",
                bodyOf(name).contains("contentColor = MaterialTheme.colorScheme.onSurface"),
            )
        }
    }

    /**
     * The declaration of the named function through its closing brace, found by counting braces
     * rather than by slicing to the next declaration: a parameter of type
     * `content: @Composable () -> Unit` contains a brace-adjacent token that would end a
     * slice-at-the-next-annotation version of this helper *before* the body it is reading.
     */
    private fun bodyOf(name: String): String {
        val start = components.indexOf("fun $name(")
        assertTrue("fun $name( is not in Components.kt - the test anchor has drifted", start >= 0)
        var depth = 0
        var i = components.indexOf('{', start)
        assertTrue("fun $name( has no body in Components.kt", i > start)
        while (i < components.length) {
            when (components[i]) {
                '{' -> depth++
                '}' -> {
                    depth--
                    if (depth == 0) return components.substring(start, i + 1)
                }
            }
            i++
        }
        assertTrue("fun $name( body never closes - Components.kt is malformed", false)
        return ""
    }
}
