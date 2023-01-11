package com.example.weeklycase

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.covidcase.model.DailyNum
import com.example.weeklycase.ExtensionUtil.formatNumber

@Composable
fun DailyCaseList(dailyCases: List<DailyNum>) {
    Column {
        dailyCases.map {
            DailyCase(
                dayOfWeek = it.dayOfWeek,
                dailyCase = it.dailyCumCases.formatNumber(),
            )
        }
    }
}

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
    DailyCaseList(dailyCases = dailyCases)
}

@Preview
@Composable
fun DailyCasePreview() {
    DailyCase(dayOfWeek = "Mon", dailyCase = "123")
}