package com.example.coronavirus.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coronavirus.R
import com.example.covidcase.model.WeeklyCase
import com.example.coronavirus.ui.ExtensionUtil.formatNumber

@Composable
fun WeeklyCaseList(weeklyCases: List<WeeklyCase>) {
    val expandStatusList = remember { List(weeklyCases.size) { false }.toMutableStateList() }

    LazyColumn {
        weeklyCases.mapIndexed { index, weeklyCase ->
            val isExpand = expandStatusList[index]
            item {
                WeeklyCase(weeklyCase, isExpand) {
                    expandStatusList[index] = !isExpand
                }
            }
        }
    }
}

@Composable
fun WeeklyCase(
    weeklyCase: WeeklyCase, isExpand: Boolean, onExpand: () -> Unit
) {
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
                        text = stringResource(
                            id = R.string.item_weekly_total_case,
                            weeklyCase.totalCumCases.formatNumber()
                        ),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = weeklyCase.weeklyCumCases.formatNumber(),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onExpand) {
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
            dailyCaseList = listOf()
        ),
        WeeklyCase(
            date = "May 7, 2022",
            weeklyCumCases = 75809,
            totalCumCases = 22181289,
            dailyCaseList = listOf()
        ),
        WeeklyCase(
            date = "May 7, 2022",
            weeklyCumCases = 75809,
            totalCumCases = 22181289,
            dailyCaseList = listOf()
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
        dailyCaseList = listOf()
    )
    WeeklyCase(weeklyCase, false) {}
}
