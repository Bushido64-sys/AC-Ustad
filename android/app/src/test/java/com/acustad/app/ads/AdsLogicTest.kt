package com.acustad.app.ads

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The two ad rules that are pure arithmetic, pinned without a device.
 *
 * The save wall is a count against [FREE_SAVES]: three saves pass, the fourth
 * knocks. The native slots start after the eleventh code and repeat every
 * five, so the first screen of results is always pure content.
 */
class AdsLogicTest {

    @Test
    fun `the first three saves are free`() {
        assertFalse(needsReward(0))
        assertFalse(needsReward(2))
    }

    @Test
    fun `the fourth save knocks, and everything after stays walled`() {
        assertTrue(needsReward(FREE_SAVES))
        assertTrue(needsReward(40))
    }

    @Test
    fun `native slots start after the eleventh code and repeat every five`() {
        assertEquals(listOf(11, 16, 21), nativeSlotAfterPositions(22))
    }

    @Test
    fun `short lists and the top ten carry no slot`() {
        assertTrue(nativeSlotAfterPositions(10).isEmpty())
        assertTrue(nativeSlotAfterPositions(11).isEmpty())
        assertEquals(listOf(11), nativeSlotAfterPositions(12))
    }

    @Test
    fun `no trailing slot after the final row`() {
        assertEquals(listOf(11, 16), nativeSlotAfterPositions(21))
        assertEquals(listOf(11, 16, 21), nativeSlotAfterPositions(22))
    }
}
