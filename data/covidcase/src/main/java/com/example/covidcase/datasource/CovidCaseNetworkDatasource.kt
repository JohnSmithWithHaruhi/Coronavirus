package com.example.covidcase.datasource

import com.example.covidcase.entity.DailyCaseList
import retrofit2.Call
import javax.inject.Inject

/**
 * Provides covid data from network.
 */
class CovidCaseNetworkDatasource @Inject constructor(
    private val service: CovidCaseService
) {
    private companion object {
        private const val structure =
            """{"date":"date","name":"areaName","newCasesByPublishDate":"newCasesByPublishDate","cumCasesByPublishDate":"cumCasesByPublishDate","newDeaths28DaysByPublishDate":"newDeaths28DaysByPublishDate","cumDeaths28DaysByPublishDate":"cumDeaths28DaysByPublishDate"}"""
    }

    /**
     * Gets whole cases by area type.
     *
     * @param areaType the type of area which you want to search.
     */
    fun getCases(areaType: String): Call<DailyCaseList> {
        return service.getDailyCaseList("areaType=${areaType}", structure)
    }

}