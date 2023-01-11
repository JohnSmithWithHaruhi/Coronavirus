package com.example.coronavirus.ui

/**
 * Class for extensions.
 */
object ExtensionUtil {
    /**
     * Formats number
     * 123456789 -> 123,456,789
     */
    fun Int.formatNumber(): String {
        return String.format("%,d", this)
    }
}