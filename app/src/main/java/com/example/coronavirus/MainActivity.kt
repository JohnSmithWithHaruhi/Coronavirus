package com.example.coronavirus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weeklycase.WeeklyCaseScreen
import com.example.weeklycase.WeeklyCaseViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main screen for this application.
 */
@AndroidEntryPoint
@OptIn(ExperimentalLifecycleComposeApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: WeeklyCaseViewModel by viewModels()

        setContent {
            MaterialTheme(
                colorScheme = if (isSystemInDarkTheme()) {
                    dynamicDarkColorScheme(LocalContext.current)
                } else {
                    dynamicLightColorScheme(LocalContext.current)
                }
            ) {
                WeeklyCaseScreen(
                    weeklyCaseUiState = viewModel.weeklyCaseUiState.collectAsStateWithLifecycle().value,
                    searchAreaDialogUiState = viewModel.searchAreaDialogUiState.collectAsStateWithLifecycle().value,
                    onReload = viewModel::reload,
                    onSelectSearchArea = viewModel::selectSearchArea,
                )
            }
        }
    }
}