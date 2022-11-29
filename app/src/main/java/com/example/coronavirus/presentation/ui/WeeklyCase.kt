package com.example.coronavirus.presentation.ui

import androidx.compose.foundation.layout.*
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
import com.example.coronavirus.presentation.viewmodel.DailyNum

@Composable
fun WeeklyCase(
    date: String,
    totalCase: String,
    weeklyCase: String,
    dailyCaseList: List<DailyNum>
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
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                )
                if (isExpand) {
                    Text(
                        text = totalCase,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = weeklyCase, style = MaterialTheme.typography.titleMedium
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
        if (isExpand){
            DailyCaseList(dailyCases = dailyCaseList)
        }
    }
}

@Preview
@Composable
fun WeeklyCasePreview() {
    WeeklyCase(
        date = "Apr 1, 2022",
        weeklyCase = "1,227,288",
        totalCase = "Total: 13,117,469",
        dailyCaseList = listOf()
    )
}