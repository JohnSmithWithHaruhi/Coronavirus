package com.example.weeklycase

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun WeeklyCaseScreen(
    weeklyCaseUiState: WeeklyCaseUiState,
    searchAreaDialogUiState: SearchAreaDialogUiState,
    onReload: () -> Unit,
    onSelectSearchArea: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (weeklyCaseUiState is WeeklyCaseUiState.Success) {
                FloatingActionButton({ searchAreaDialogUiState.setShowDialog(true) }) {
                    Icon(Icons.Default.Search, contentDescription = "")
                }
            }
        }) {
        when (weeklyCaseUiState) {
            WeeklyCaseUiState.Error -> ErrorScreen(onReload)
            WeeklyCaseUiState.Loading -> LoadingScreen()
            is WeeklyCaseUiState.Success -> {
                WeeklyCaseList(weeklyCases = weeklyCaseUiState.weeklyCaseList)
                if (searchAreaDialogUiState.shouldShowDialog) {
                    SearchAreaDialog(
                        searchAreaDialogUiState = searchAreaDialogUiState,
                        onSelectArea = onSelectSearchArea
                    )
                }
            }
        }
    }
}