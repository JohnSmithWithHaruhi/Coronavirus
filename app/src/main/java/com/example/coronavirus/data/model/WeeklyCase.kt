package com.example.coronavirus.data.model

/**
 * Model holds all data for weekly case.
 */
data class WeeklyCase(
    val date: String,
    val weeklyCumCases: Int,
    val totalCumCases: Int,
    val dailyCaseList: List<DailyNum>,
    var isExpand: Boolean
)

/**
 * Model holds data for daily case.
 */
data class DailyNum(
    val dayOfWeek: String,
    val dailyCumCases: Int
)