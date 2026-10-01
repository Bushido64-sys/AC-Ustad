package com.acustad.app.ads

import com.acustad.app.BuildConfig

/**
 * Which ad unit each format loads.
 *
 * Debug serves Google's demo units (set in `build.gradle.kts`), so a debug
 * build can never bill the owner's account or generate invalid traffic.
 * Release serves the git-ignored `ads.properties` — never committed, because
 * the repository is public. ADS.md is the full contract.
 */
object AdIds {
    val banner: String get() = BuildConfig.AD_UNIT_BANNER
    val interstitial: String get() = BuildConfig.AD_UNIT_INTERSTITIAL
    val rewarded: String get() = BuildConfig.AD_UNIT_REWARDED
    val native: String get() = BuildConfig.AD_UNIT_NATIVE
    val appOpen: String get() = BuildConfig.AD_UNIT_APP_OPEN
}

/**
 * The first [FREE_SAVES] saves are free. The wall starts at that count, so a
 * technician who saves one or two codes for the job in front of them never
 * sees a popup at all.
 *
 * Pure, so the rule is unit-tested without a device.
 */
const val FREE_SAVES = 3

fun needsReward(savedCount: Int): Boolean = savedCount >= FREE_SAVES

/**
 * 1-based code positions after which a native slot is inserted. Never inside
 * the top 10: the first screen of results is always pure content. Never
 * trailing: a slot after the final row is a dead end with no content below it.
 *
 * Pure, so the placement math is unit-tested without a device.
 */
fun nativeSlotAfterPositions(total: Int): List<Int> =
    generateSequence(11) { it + 5 }.takeWhile { it < total }.toList()
