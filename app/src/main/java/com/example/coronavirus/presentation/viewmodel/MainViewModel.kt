package com.example.coronavirus.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronavirus.data.entity.DailyCase
import com.example.coronavirus.data.repository.CovidRepository
import com.example.coronavirus.data.repository.CovidRepositoryImpl
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import java.time.LocalDate

class MainViewModel : ViewModel() {

    private val covidRepository: CovidRepository = CovidRepositoryImpl()
    private val weeklyCaseList = MutableLiveData<List<WeeklyCase>>()

    fun weeklyCaseList(): LiveData<List<WeeklyCase>> {
        return weeklyCaseList
    }

    fun fetchWeeklyCaseList() {
        viewModelScope.launch {
            weeklyCaseList.postValue(
                convertDailyCaseToWeeklyCase(
                    covidRepository.fetchDailyCaseList()
                )
            )
        }
    }

    @TestOnly
    fun convertDailyCaseToWeeklyCase(dailyCaseList: List<DailyCase>): List<WeeklyCase> {
        return listOf(
            WeeklyCase(
                date = LocalDate.now(),
                cumCases = 123123,
                dailyNewCase = listOf(1, 2, 3, 4, 5, 6, 7),
            )
        )
    }

}

data class WeeklyCase(
    val date: LocalDate,
    val cumCases: Int,
    val dailyNewCase: List<Int>
)