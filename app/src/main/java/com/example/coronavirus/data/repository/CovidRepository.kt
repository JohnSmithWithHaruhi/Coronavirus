package com.example.coronavirus.data.repository

import com.example.coronavirus.data.entity.DailyCase
import com.example.coronavirus.presentation.viewmodel.MainViewModel

/**
 * Repository provides data about covid.
 */
interface CovidRepository {

    /**
     * Fetches daily case list.
     *
     * @return daily case list.
     */
    suspend fun fetchDailyCaseList(area: MainViewModel.SearchArea): List<DailyCase>
}