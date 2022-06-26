package com.example.coronavirus

object ExtensionUtil {
    fun Int.formatNumber(): String {
        return String.format("%,d", this)
    }
}