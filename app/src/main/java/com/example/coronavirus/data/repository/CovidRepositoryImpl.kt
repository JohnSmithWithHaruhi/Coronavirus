package com.example.coronavirus.data.repository

import com.example.coronavirus.data.datasource.CovidDatasource
import com.example.coronavirus.data.entity.DailyCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CovidRepositoryImpl : CovidRepository {
    private val covidDatasource = CovidDatasource()
    override suspend fun fetchDailyCaseList(): List<DailyCase> {
        return withContext(Dispatchers.IO) {
            return@withContext covidDatasource.getCase().execute().body()!!.dailyCase
        }
    }
}