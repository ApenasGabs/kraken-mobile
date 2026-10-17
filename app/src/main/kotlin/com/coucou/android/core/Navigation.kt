package com.coucou.android.core

/** The places the bottom bar leads to. */
enum class Tab { HOME, WHAT_NEXT, CHAT, SETTINGS }

/** Every screen of the app. Tabs are the roots; the rest are one level down. */
enum class Screen { HOME, WHAT_NEXT, CHAT, SETTINGS, HISTORY, GALLERY, WARDROBE, DIAGNOSTICS, LICENCES, SESSION, SCAN }

/**
 * Where the user is and where Back goes. Pure, so every rule is a unit test:
 * - the bar always has Status (Home), What's Next, Chat and Settings;
 * - the bar shows on the tab roots, and hides on every page below them and while the keyboard is open;
 * - Back goes up one level; on Home it is the system's (leave the app).
 */
object Nav {
    fun tabs(): List<Tab> = listOf(Tab.HOME, Tab.WHAT_NEXT, Tab.CHAT, Tab.SETTINGS)

    fun screenOf(tab: Tab): Screen = when (tab) {
        Tab.HOME -> Screen.HOME
        Tab.WHAT_NEXT -> Screen.WHAT_NEXT
        Tab.CHAT -> Screen.CHAT
        Tab.SETTINGS -> Screen.SETTINGS
    }

    /** The tab a screen belongs to, so the bar can highlight it; null for full-screen tasks. */
    fun tabOf(screen: Screen): Tab? = when (screen) {
        Screen.HOME -> Tab.HOME
        Screen.WHAT_NEXT -> Tab.WHAT_NEXT
        Screen.CHAT -> Tab.CHAT
        Screen.SETTINGS, Screen.HISTORY, Screen.GALLERY, Screen.WARDROBE, Screen.DIAGNOSTICS, Screen.LICENCES -> Tab.SETTINGS
        Screen.SESSION, Screen.SCAN -> null
    }

    fun barVisible(screen: Screen, keyboardOpen: Boolean): Boolean =
        !keyboardOpen && screen in setOf(Screen.HOME, Screen.WHAT_NEXT, Screen.CHAT, Screen.SETTINGS)

    /** One level up, or null when Back should leave the app. */
    fun back(screen: Screen): Screen? = when (screen) {
        Screen.HOME -> null
        Screen.WHAT_NEXT, Screen.CHAT, Screen.SETTINGS, Screen.SESSION, Screen.SCAN -> Screen.HOME
        Screen.HISTORY, Screen.GALLERY, Screen.WARDROBE, Screen.DIAGNOSTICS, Screen.LICENCES -> Screen.SETTINGS
    }
}
