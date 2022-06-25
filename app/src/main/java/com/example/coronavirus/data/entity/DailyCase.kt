package com.example.coronavirus.data.entity

import com.google.gson.annotations.SerializedName

data class Cases(
    @SerializedName("data")
    val dailyCase: List<DailyCase>
)

data class DailyCase(
    val date: String,
    val name: String,
    val newCasesByPublishDate: Int,
    val cumCasesByPublishDate: Int,
    val newDeaths28DaysByPublishDate: Int,
    val cumDeaths28DaysByPublishDate: Int
)