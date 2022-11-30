package com.example.coronavirus.data.repository

import com.example.coronavirus.data.model.WeeklyCase

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
     * Fetches weekly case list.
     *
     * @return weekly case list.
     */
    suspend fun fetchWeeklyCaseList(area: SearchArea): List<WeeklyCase>
}