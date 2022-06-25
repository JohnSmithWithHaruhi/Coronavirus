package com.example.coronavirus.data.repository

import com.example.coronavirus.data.entity.DailyCase

interface CovidRepository {
    suspend fun fetchDailyCaseList(): List<DailyCase>
}