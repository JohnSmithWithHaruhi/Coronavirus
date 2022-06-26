package com.example.coronavirus

import android.app.Application
import com.google.android.material.color.DynamicColors

/**
 * Main application.
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}