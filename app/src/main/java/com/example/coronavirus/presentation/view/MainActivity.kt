package com.example.coronavirus.presentation.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coronavirus.R
import com.example.coronavirus.presentation.ui.WeeklyCaseList
import com.example.coronavirus.presentation.viewmodel.MainUiState
import com.example.coronavirus.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main screen for this application.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

        setContent {
            MainScreen(
                mainUiState = viewModel.mainUiState.collectAsState().value,
                onReloadClick = viewModel::reload
            )
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun MainScreen(
        mainUiState: MainUiState,
        onReloadClick: () -> Unit,
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            when (mainUiState) {
                MainUiState.Error -> ErrorScreen(onReloadClick)
                MainUiState.Loading -> LoadingScreen()
                is MainUiState.Success -> {
                    WeeklyCaseList(weeklyCases = mainUiState.weeklyCaseList)
                }
            }
        }
    }

    @Composable
    fun ErrorScreen(onReloadClick: () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.reload_message),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onReloadClick) {
                Text(text = stringResource(id = R.string.reload_button))
            }
        }
    }

    @Composable
    fun LoadingScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = "Loading...",
            )
        }
    }

    @Preview
    @Composable
    fun ErrorScreenPreview() {
        ErrorScreen({})
    }

    @Preview
    @Composable
    fun LoadingScreenPreview() {
        LoadingScreen()
    }
}