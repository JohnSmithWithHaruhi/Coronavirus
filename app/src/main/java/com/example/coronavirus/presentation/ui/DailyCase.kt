package com.example.coronavirus.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DailyCase(
    dayOfWeek: String, dailyCase: String
) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = dayOfWeek,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
        Text(
            text = dailyCase,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 56.dp)
        )
    }
}

@Preview
@Composable
fun DailyCasePreview() {
    DailyCase(dayOfWeek = "Mon", dailyCase = "123")
}