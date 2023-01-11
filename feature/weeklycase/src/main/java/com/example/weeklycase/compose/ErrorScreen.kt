package com.example.weeklycase

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen {}
}