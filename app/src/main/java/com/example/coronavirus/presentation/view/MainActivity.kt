package com.example.coronavirus.presentation.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.coronavirus.presentation.ui.WeeklyCaseList
import com.example.coronavirus.presentation.viewmodel.MainUiState
import com.example.coronavirus.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main screen for this application.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

        setContent {
            val mainUiState = viewModel.mainUiState.collectAsState().value
            val weeklyCaseList = when (mainUiState) {
                MainUiState.Error -> listOf()
                MainUiState.Loading -> listOf()
                is MainUiState.Success -> {
                    mainUiState.weeklyCaseList
                }
            }

            Scaffold(modifier = Modifier.fillMaxWidth()) {
                WeeklyCaseList(weeklyCases = weeklyCaseList)
            }
        }
    }
}