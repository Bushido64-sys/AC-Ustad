package com.acustad.app.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.acustad.app.R
import com.acustad.app.model.ContentLanguage

/**
 * One severity level, resolved for a theme and a language.
 *
 * Every level carries three things, never one: a fill, a text colour that meets contrast
 * against that fill, and a 2dp border. Colour alone is not a signal — 60% of all faults are
 * `stop_pro`, so a chip whose only meaning is its colour tells a technician nothing. The word
 * is what a screen reader announces first, and the border is what survives a washed-out LCD in
 * sunlight. (RULES.md RULE 8)
 *
 * Measured ratios, from PHASE_8_ACCESSIBILITY_URDU.md §5:
 *   danger        white on signal_deep   6.37:1
 *   stop_pro      ink   on signal_tint  14.27:1   border 4.03:1 on its own fill
 *   check_restart ink   on signal_tint_3 15.20:1   border 3.61:1 on its own fill
 *   self_clear    ink   on neutral_fill  13.96:1
 *   info          ink   on canvas        16.89:1
 *   dark danger   ink   on signal_deep   6.67:1 (the night resource is a lighter orange)
 *
 * Colours come from the theme resources, so there is no second copy of the palette in Kotlin
 * and the light and dark schemes cannot drift apart.
 */
@Immutable
data class SeverityVisuals(
    val label: String,
    val background: Color,
    val content: Color,
    val border: BorderStroke?,
)

/** The five values in the `codes.severity` column, in the order they should be ranked. */
enum class Severity {
    DANGER,
    STOP,
    CHECK,
    CLEARS,
    INFO;

    companion object {
        /**
         * Maps the stored value. Anything unrecognised is treated as INFO, never as DANGER:
         * a wrong "stop the machine" claim is far more costly than a vague one.
         */
        fun from(value: String): Severity = when (value) {
            "danger" -> DANGER
            "stop_pro" -> STOP
            "check_restart" -> CHECK
            "self_clear" -> CLEARS
            else -> INFO
        }
    }
}

@Composable
fun severityVisuals(severity: Severity, language: ContentLanguage): SeverityVisuals =
    when (severity) {
        Severity.DANGER -> SeverityVisuals(
            label = word(R.string.severity_danger, R.string.severity_danger_ur, language),
            background = colorResource(R.color.signal_deep),
            content = colorResource(R.color.canvas),
            border = BorderStroke(2.dp, colorResource(R.color.signal_danger_border)),
        )
        Severity.STOP -> SeverityVisuals(
            label = word(R.string.severity_stop_pro, R.string.severity_stop_pro_ur, language),
            background = colorResource(R.color.signal_tint),
            content = colorResource(R.color.ink),
            border = BorderStroke(2.dp, colorResource(R.color.signal_stop)),
        )
        Severity.CHECK -> SeverityVisuals(
            label = word(R.string.severity_check_restart, R.string.severity_check_restart_ur, language),
            background = colorResource(R.color.signal_tint_3),
            content = colorResource(R.color.ink),
            border = BorderStroke(2.dp, colorResource(R.color.signal_mid)),
        )
        Severity.CLEARS -> SeverityVisuals(
            label = word(R.string.severity_self_clear, R.string.severity_self_clear_ur, language),
            background = colorResource(R.color.neutral_fill),
            content = colorResource(R.color.ink),
            border = null,
        )
        Severity.INFO -> SeverityVisuals(
            label = word(R.string.severity_info, R.string.severity_info_ur, language),
            background = colorResource(R.color.canvas),
            content = colorResource(R.color.ink),
            border = BorderStroke(2.dp, colorResource(R.color.hairline)),
        )
    }

/**
 * An indicator or parameter row — the 569 codes with `is_fault = 0`. Not a fault, so it gets
 * a muted treatment and the word INDICATOR rather than a severity chip, and a technician must
 * never read `Parameter P003` as a breakdown.
 */
@Composable
fun indicatorVisuals(language: ContentLanguage): SeverityVisuals = SeverityVisuals(
    label = word(R.string.severity_indicator, R.string.severity_indicator_ur, language),
    background = colorResource(R.color.canvas),
    content = colorResource(R.color.ink_muted),
    border = BorderStroke(2.dp, colorResource(R.color.hairline)),
)

@Composable
private fun word(enRes: Int, urRes: Int, language: ContentLanguage): String = when (language) {
    ContentLanguage.EN -> stringResource(enRes)
    ContentLanguage.UR -> stringResource(urRes)
}
