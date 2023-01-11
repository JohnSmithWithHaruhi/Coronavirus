package com.example.covidcase.model

/**
 * Model holds all data for weekly case.
 */
data class WeeklyCase(
    val date: String,
    val weeklyCumCases: Int,
    val totalCumCases: Int,
    val dailyCaseList: List<DailyNum>
)

/**
 * Model holds data for daily case.
 */
data class DailyNum(
    val dayOfWeek: String,
    val dailyCumCases: Int
)