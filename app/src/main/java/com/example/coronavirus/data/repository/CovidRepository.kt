package com.example.coronavirus.data.repository

import com.example.coronavirus.data.entity.DailyCase

/**
 * Repository provides data about covid.
 */
interface CovidRepository {

    /**
     * Fetches daily case list.
     *
     * @return daily case list.
     */
    suspend fun fetchDailyCaseList(): List<DailyCase>
}