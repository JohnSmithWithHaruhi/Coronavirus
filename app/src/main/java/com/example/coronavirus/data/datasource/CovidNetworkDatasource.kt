package com.example.coronavirus.data.datasource

import com.example.coronavirus.data.entity.Cases
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Provides covid data from network.
 */
class CovidNetworkDatasource {
    private companion object {
        private const val baseUrl = "https://api.coronavirus.data.gov.uk/v1/"
        private const val structure =
            """{"date":"date","name":"areaName","newCasesByPublishDate":"newCasesByPublishDate","cumCasesByPublishDate":"cumCasesByPublishDate","newDeaths28DaysByPublishDate":"newDeaths28DaysByPublishDate","cumDeaths28DaysByPublishDate":"cumDeaths28DaysByPublishDate"}"""
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: CovidService = retrofit.create(CovidService::class.java)

    /**
     * Gets whole cases by area type.
     *
     * @param areaType the type of area which you want to search.
     */
    fun getCases(areaType: String): Call<Cases> {
        return service.getCases("areaType=${areaType}", structure)
    }

}