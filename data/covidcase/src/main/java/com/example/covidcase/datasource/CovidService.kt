package com.example.covidcase.datasource

import com.example.covidcase.entity.DailyCaseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface is for retrofit.
 */
interface CovidService {
    @GET("data")
    fun getDailyCaseList(
        @Query("filters") filters: String,
        @Query("structure") structure: String
    ): Call<DailyCaseList>
}