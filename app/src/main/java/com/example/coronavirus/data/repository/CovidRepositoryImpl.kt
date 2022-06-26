package com.example.coronavirus.data.repository

import com.example.coronavirus.data.datasource.CovidNetworkDatasource
import com.example.coronavirus.data.entity.DailyCase
import com.example.coronavirus.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class implemented [CovidRepository].
 */
class CovidRepositoryImpl : CovidRepository {
    private val covidNetworkDatasource = CovidNetworkDatasource()

    override suspend fun fetchDailyCaseList(area: MainViewModel.SearchArea): List<DailyCase> {
        return withContext(Dispatchers.IO) {
            val areaType = when (area) {
                MainViewModel.SearchArea.UnitedKingdom -> "overview"
                MainViewModel.SearchArea.NorthernIreland -> "nation;areaName=Northern Ireland"
                else -> "nation;areaName=${area.name}"
            }
            val response = covidNetworkDatasource.getCases(areaType).execute()
            if (response.isSuccessful) {
                response.body()?.let { return@withContext it.dailyCase }
            }
            return@withContext emptyList()
        }
    }
}