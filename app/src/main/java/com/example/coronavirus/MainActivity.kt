package com.example.coronavirus

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: WeeklyCaseViewModel by viewModels()

        setContent {
            WeeklyCaseScreen(
                mainUiState = viewModel.mainUiState.collectAsStateWithLifecycle().value,
                searchAreaDialogUiState = viewModel.searchAreaDialogUiState.collectAsStateWithLifecycle().value,
                onReload = viewModel::reload,
                onSelectSearchArea = viewModel::selectSearchArea,
            )
        }
    }
}