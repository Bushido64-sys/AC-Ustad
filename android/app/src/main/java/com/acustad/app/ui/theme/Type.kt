package com.acustad.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.acustad.app.R

/**
 * IBM Plex, bundled. Never Inter, never Roboto, never a system family — the typography is
 * half of the design. (RULES.md RULE 10, ASSETS.md §1)
 *
 * Rules baked in here:
 *  - Mono is for codes, model names and counts ONLY. Never a paragraph.
 *  - Condensed is for section and category headers only, to buy horizontal room.
 *  - Body text is 16sp with a 24sp line height, which is the floor, not a suggestion.
 *    Roman Urdu needs the extra leading or the words run together.
 *  - No italics anywhere.
 */
private val PlexSans = FontFamily(
    Font(R.font.ibm_plex_sans_regular, FontWeight.Normal),
    Font(R.font.ibm_plex_sans_medium, FontWeight.Medium),
    Font(R.font.ibm_plex_sans_semibold, FontWeight.SemiBold),
    Font(R.font.ibm_plex_sans_bold, FontWeight.Bold),
)

private val PlexMono = FontFamily(
    Font(R.font.ibm_plex_mono_medium, FontWeight.Medium),
    Font(R.font.ibm_plex_mono_semibold, FontWeight.SemiBold),
)

private val PlexCondensed = FontFamily(
    Font(R.font.ibm_plex_sans_condensed_semibold, FontWeight.SemiBold),
)

/** Type sizes, as floors. See DESIGN.md §1 and PHASE_8 §1. */
object UstadType {
    val code = TextStyle(
        fontFamily = PlexMono, fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp, lineHeight = 26.sp, letterSpacing = 0.4.sp,
    )
    val codeList = TextStyle(
        fontFamily = PlexMono, fontWeight = FontWeight.Medium,
        fontSize = 18.sp, lineHeight = 24.sp,
    )
    val title = TextStyle(
        fontFamily = PlexSans, fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp, lineHeight = 26.sp,
    )
    /** 16sp / 24sp is the hard floor for any text a technician has to read. */
    val body = TextStyle(
        fontFamily = PlexSans, fontWeight = FontWeight.Normal,
        fontSize = 16.sp, lineHeight = 24.sp,
    )
    val listRow = TextStyle(
        fontFamily = PlexSans, fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp, lineHeight = 22.sp,
    )
    val section = TextStyle(
        fontFamily = PlexCondensed, fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp, lineHeight = 24.sp,
    )
    val label = TextStyle(
        fontFamily = PlexSans, fontWeight = FontWeight.Bold,
        fontSize = 14.sp, lineHeight = 18.sp, letterSpacing = 0.3.sp,
    )
    val caption = TextStyle(
        fontFamily = PlexSans, fontWeight = FontWeight.Normal,
        fontSize = 14.sp, lineHeight = 20.sp,
    )
    val count = TextStyle(
        fontFamily = PlexMono, fontWeight = FontWeight.Medium,
        fontSize = 14.sp, lineHeight = 18.sp,
    )
}

val UstadTypography = Typography(
    bodyLarge = UstadType.body,
    bodyMedium = UstadType.body,
    titleLarge = UstadType.title,
    titleMedium = UstadType.listRow,
    labelLarge = UstadType.label,
    labelSmall = UstadType.caption,
)
