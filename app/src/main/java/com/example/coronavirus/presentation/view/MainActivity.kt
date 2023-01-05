package com.example.coronavirus.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coronavirus.R
import com.example.coronavirus.presentation.ui.WeeklyCaseList
import com.example.coronavirus.presentation.viewmodel.MainUiState
import com.example.coronavirus.presentation.viewmodel.MainViewModel
import com.example.coronavirus.presentation.viewmodel.SearchDialogUiState
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
                searchDialogUiState = viewModel.searchDialogUiState.collectAsState().value,
                onReload = viewModel::reload,
                onSearchSelected = viewModel::reload,
            )
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    fun MainScreen(
        mainUiState: MainUiState,
        searchDialogUiState: SearchDialogUiState,
        onReload: () -> Unit,
        onSearchSelected: () -> Unit
    ) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                if (mainUiState is MainUiState.Success) {
                    FloatingActionButton({ searchDialogUiState.setShowDialog(true) }) {
                        Icon(Icons.Default.Search, contentDescription = "")
                    }
                }
            }) {
            when (mainUiState) {
                MainUiState.Error -> ErrorScreen(onReload)
                MainUiState.Loading -> LoadingScreen()
                is MainUiState.Success -> {
                    WeeklyCaseList(weeklyCases = mainUiState.weeklyCaseList)
                    if (searchDialogUiState.shouldShowDialog) {
                        SearchDialog(
                            searchDialogUiState = searchDialogUiState,
                            onSelected = onSearchSelected
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun SearchDialog(
        searchDialogUiState: SearchDialogUiState,
        onSelected: () -> Unit,
    ) {
        var selectedItem by remember { mutableStateOf(searchDialogUiState.selectedItem) }

        AlertDialog(onDismissRequest = { searchDialogUiState.setShowDialog(false) },
            title = { Text(text = stringResource(R.string.dialog_search_title)) },
            text = {
                Column(Modifier.selectableGroup()) {
                    searchDialogUiState.itemList.mapIndexed { index, itemText ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    role = Role.RadioButton,
                                    selected = selectedItem == index,
                                    onClick = { selectedItem = index },
                                )
                                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedItem == index, onClick = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(itemText)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        searchDialogUiState.changeSelectedItem(selectedItem)
                        searchDialogUiState.setShowDialog(false)
                        onSelected()
                    },
                ) {
                    Text(text = stringResource(id = R.string.dialog_search_positive))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { searchDialogUiState.setShowDialog(false) },
                ) {
                    Text(text = stringResource(id = R.string.dialog_search_negative))
                }
            })
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
        ErrorScreen {}
    }

    @Preview
    @Composable
    fun LoadingScreenPreview() {
        LoadingScreen()
    }
}