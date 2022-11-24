package com.example.coronavirus.presentation.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.coronavirus.presentation.viewmodel.WeeklyCase

@Composable
fun WeeklyCaseList(weeklyCases: List<WeeklyCase>) {
    LazyColumn {
        weeklyCases.map {
            item {
                WeeklyCase(
                    date = it.date,
                    totalCase = it.totalCumCases.toString(),
                    weeklyCase = it.weeklyCumCases.toString(),
                )
            }
        }
    }
}

@Preview
@Composable
fun WeeklyCaseListPreview() {
    WeeklyCase(
        date = "Apr 1, 2022", weeklyCase = "1,227,288", totalCase = "Total: 13,117,469"
    )
}