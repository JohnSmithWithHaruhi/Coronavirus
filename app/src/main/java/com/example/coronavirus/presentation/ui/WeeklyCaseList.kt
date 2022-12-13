package com.example.coronavirus.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coronavirus.R
import com.example.coronavirus.data.model.WeeklyCase

@Composable
fun WeeklyCaseList(weeklyCases: List<WeeklyCase>) {
    LazyColumn {
        weeklyCases.map {
            item { WeeklyCase(it) }
        }
    }
}

@Composable
fun WeeklyCase(
    weeklyCase: WeeklyCase
) {
    var isExpand by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = weeklyCase.date,
                    style = MaterialTheme.typography.bodySmall,
                )
                if (isExpand) {
                    Text(
                        text = weeklyCase.totalCumCases.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = weeklyCase.weeklyCumCases.toString(),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { isExpand = !isExpand }) {
                if (isExpand) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_expand_less_24dp),
                        contentDescription = ""
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_expand_more_24dp),
                        contentDescription = ""
                    )
                }
            }
        }
        if (isExpand) {
            DailyCaseList(dailyCases = weeklyCase.dailyCaseList)
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
        ),
    )
    WeeklyCaseList(weeklyCases = weeklyCases)
}

@Preview
@Composable
fun WeeklyCasePreview() {
    val weeklyCase = WeeklyCase(
        date = "May 7, 2022",
        weeklyCumCases = 75809,
        totalCumCases = 22181289,
        dailyCaseList = listOf(),
        isExpand = false
    )
    WeeklyCase(weeklyCase)
}