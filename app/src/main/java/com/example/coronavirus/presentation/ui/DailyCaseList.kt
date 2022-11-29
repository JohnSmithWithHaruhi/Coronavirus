package com.example.coronavirus.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.coronavirus.presentation.viewmodel.DailyNum

@Composable
fun DailyCaseList(dailyCases: List<DailyNum>) {
    Column {
        dailyCases.map {
            DailyCase(
                dayOfWeek = it.dayOfWeek,
                dailyCase = it.dailyCumCases.toString(),
            )
        }
    }
}

@Preview
@Composable
fun DailyCaseListPreview() {
    val dailyCases = listOf(
        DailyNum(
            dayOfWeek = "Sat",
            dailyCumCases = 123456,
        ),
        DailyNum(
            dayOfWeek = "Fri",
            dailyCumCases = 123456,
        ),
        DailyNum(
            dayOfWeek = "Thu",
            dailyCumCases = 123456,
        )
    )
    DailyCaseList(
        dailyCases = dailyCases
    )
}