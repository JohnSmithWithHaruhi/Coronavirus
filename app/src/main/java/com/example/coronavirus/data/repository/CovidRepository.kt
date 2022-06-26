package com.example.coronavirus.data.repository

import com.example.coronavirus.data.entity.DailyCase

/**
 * Repository provides data about covid.
 */
interface CovidRepository {

    /**
     * Type of area to filter.
     */
    enum class SearchArea {
        UnitedKingdom, England, NorthernIreland, Scotland, Wales
    }

    /**
     * Fetches daily case list.
     *
     * @return daily case list.
     */
    suspend fun fetchDailyCaseList(area: SearchArea): List<DailyCase>
}