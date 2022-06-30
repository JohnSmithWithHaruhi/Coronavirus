package com.example.coronavirus.data.repository

import com.example.coronavirus.data.datasource.CovidNetworkDatasource
import com.example.coronavirus.data.entity.DailyCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 * Class implemented [CovidRepository].
 */
class CovidRepositoryImpl @Inject constructor() : CovidRepository {
    private val covidNetworkDatasource = CovidNetworkDatasource()

    override suspend fun fetchDailyCaseList(area: CovidRepository.SearchArea): List<DailyCase> {
        return withContext(Dispatchers.IO) {
            val areaType = when (area) {
                CovidRepository.SearchArea.UnitedKingdom -> "overview"
                CovidRepository.SearchArea.NorthernIreland -> "nation;areaName=Northern Ireland"
                else -> "nation;areaName=${area.name}"
            }

            val response = try {
                covidNetworkDatasource.getCases(areaType).execute()
            } catch (e: IOException) {
                return@withContext emptyList()
            }
            if (response.isSuccessful) {
                response.body()?.let { return@withContext it.dailyCase }
            }
            return@withContext emptyList()
        }
    }
}
