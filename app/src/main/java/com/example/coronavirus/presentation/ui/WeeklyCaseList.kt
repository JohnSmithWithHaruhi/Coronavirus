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
                    dailyCaseList = it.dailyCaseList
                )
            }
        }
    }
}

@Preview
@Composable
fun WeeklyCaseListPreview() {
    val weeklyCases = listOf(
        WeeklyCase(
            date = "May 7, 2022",
            weeklyCumCases = 75809,
            totalCumCases = 22181289,
            dailyCaseList = listOf(),
            isExpand = false
        ),
        WeeklyCase(
            date = "May 7, 2022",
            weeklyCumCases = 75809,
            totalCumCases = 22181289,
            dailyCaseList = listOf(),
            isExpand = false
        ),
        WeeklyCase(
            date = "May 7, 2022",
            weeklyCumCases = 75809,
            totalCumCases = 22181289,
            dailyCaseList = listOf(),
            isExpand = false
        )
    )
    WeeklyCaseList(
        weeklyCases = weeklyCases
    )
}