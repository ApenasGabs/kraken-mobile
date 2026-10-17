package com.coucou.android

import com.coucou.android.core.Nav
import com.coucou.android.core.Screen
import com.coucou.android.core.Tab
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationTest {
    @Test fun theBarAlwaysHasHomeWhatNextChatAndSettingsInThatOrder() {
        assertEquals(listOf(Tab.HOME, Tab.WHAT_NEXT, Tab.CHAT, Tab.SETTINGS), Nav.tabs())
    }

    @Test fun theBarShowsOnTheFourRootsOnly() {
        for (s in listOf(Screen.HOME, Screen.WHAT_NEXT, Screen.CHAT, Screen.SETTINGS)) assertTrue("$s", Nav.barVisible(s, keyboardOpen = false))
        for (s in listOf(Screen.HISTORY, Screen.GALLERY, Screen.WARDROBE, Screen.DIAGNOSTICS, Screen.LICENCES, Screen.SESSION, Screen.SCAN)) assertFalse("$s", Nav.barVisible(s, false))
    }

    @Test fun theBarHidesWhileTheKeyboardIsOpen() {
        for (s in Screen.entries) assertFalse("$s", Nav.barVisible(s, keyboardOpen = true))
    }

    @Test fun backGoesUpOneLevelAndLeavesFromHome() {
        assertNull(Nav.back(Screen.HOME))
        assertEquals(Screen.HOME, Nav.back(Screen.WHAT_NEXT))
        assertEquals(Screen.HOME, Nav.back(Screen.CHAT))
        assertEquals(Screen.HOME, Nav.back(Screen.SETTINGS))
        assertEquals(Screen.HOME, Nav.back(Screen.SESSION))
        assertEquals(Screen.HOME, Nav.back(Screen.SCAN))
        assertEquals(Screen.SETTINGS, Nav.back(Screen.HISTORY))
        assertEquals(Screen.SETTINGS, Nav.back(Screen.GALLERY))
        assertEquals(Screen.SETTINGS, Nav.back(Screen.WARDROBE))
        assertEquals(Screen.SETTINGS, Nav.back(Screen.DIAGNOSTICS))
    }

    @Test fun backAlwaysReachesHomeAndNeverLoops() {
        for (start in Screen.entries) {
            var s = start
            var steps = 0
            while (true) {
                s = Nav.back(s) ?: break
                assertTrue("loop from $start", ++steps < 10)
            }
            assertEquals("from $start", Screen.HOME, s)
        }
    }

    @Test fun pagesBelowSettingsKeepSettingsHighlighted() {
        for (s in listOf(Screen.SETTINGS, Screen.HISTORY, Screen.GALLERY, Screen.WARDROBE, Screen.DIAGNOSTICS)) assertEquals(Tab.SETTINGS, Nav.tabOf(s))
        assertEquals(Tab.WHAT_NEXT, Nav.tabOf(Screen.WHAT_NEXT))
        assertNull(Nav.tabOf(Screen.SCAN))
        assertNull(Nav.tabOf(Screen.SESSION))
    }

    @Test fun everyTabLeadsToItsOwnScreen() {
        for (t in Tab.entries) assertEquals(t, Nav.tabOf(Nav.screenOf(t)))
    }
}
