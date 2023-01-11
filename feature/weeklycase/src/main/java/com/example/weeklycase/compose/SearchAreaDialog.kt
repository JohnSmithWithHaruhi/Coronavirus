package com.example.weeklycase

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun SearchAreaDialog(
    searchAreaDialogUiState: SearchAreaDialogUiState,
    onSelectArea: () -> Unit,
) {
    var selectedItem by remember { mutableStateOf(searchAreaDialogUiState.selectedItem) }

    AlertDialog(onDismissRequest = { searchAreaDialogUiState.setShowDialog(false) },
        title = { Text(text = stringResource(R.string.dialog_search_title)) },
        text = {
            Column(Modifier.selectableGroup()) {
                searchAreaDialogUiState.itemList.mapIndexed { index, itemText ->
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
                    searchAreaDialogUiState.setSelectedItem(selectedItem)
                    searchAreaDialogUiState.setShowDialog(false)
                    onSelectArea()
                },
            ) {
                Text(text = stringResource(id = R.string.dialog_search_positive))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { searchAreaDialogUiState.setShowDialog(false) },
            ) {
                Text(text = stringResource(id = R.string.dialog_search_negative))
            }
        })
}