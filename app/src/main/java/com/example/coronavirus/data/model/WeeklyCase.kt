package com.example.coronavirus.data.model

/**
 * View model holds all view data for weekly case.
 */
data class WeeklyCase(
    val date: String,
    val weeklyCumCases: Int,
    val totalCumCases: Int,
    val dailyCaseList: List<DailyNum>,
    var isExpand: Boolean
)

/**
 * View model holds all view data for daily case.
 */
data class DailyNum(
    val dayOfWeek: String,
    val dailyCumCases: Int
)