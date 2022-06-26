package com.example.coronavirus.data.repository

import com.example.coronavirus.data.datasource.CovidNetworkDatasource
import com.example.coronavirus.data.entity.DailyCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class implemented [CovidRepository].
 */
class CovidRepositoryImpl : CovidRepository {
    private val covidNetworkDatasource = CovidNetworkDatasource()

    override suspend fun fetchDailyCaseList(): List<DailyCase> {
        return withContext(Dispatchers.IO) {
            val response = covidNetworkDatasource.getCases("overview").execute()
            if (response.isSuccessful) {
                response.body()?.let { return@withContext it.dailyCase }
            }
            return@withContext emptyList()
        }
    }
}