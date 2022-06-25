package com.example.coronavirus.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronavirus.data.entity.DailyCase
import com.example.coronavirus.data.repository.CovidRepository
import com.example.coronavirus.data.repository.CovidRepositoryImpl
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import java.time.DayOfWeek.SUNDAY
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters.previous

class MainViewModel : ViewModel() {

    private val covidRepository: CovidRepository = CovidRepositoryImpl()
    private val weeklyCaseList = MutableLiveData<List<WeeklyCase>>()

    fun weeklyCaseList(): LiveData<List<WeeklyCase>> {
        return weeklyCaseList
    }

    fun fetchWeeklyCaseList() {
        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                weeklyCaseList.postValue(
                    convertDailyCaseToWeeklyCase(
                        covidRepository.fetchDailyCaseList()
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TestOnly
    fun convertDailyCaseToWeeklyCase(dailyCaseList: List<DailyCase>): List<WeeklyCase> {
        val weeklyCaseList = mutableListOf<WeeklyCase>()

        var baseDay = LocalDate.now().with(previous(SUNDAY))
        var cumCases = 0
        val dailyNewCaseList = mutableListOf<Int>()


        dailyCaseList.forEach {
            if (LocalDate.parse(it.date).toEpochDay() > baseDay.toEpochDay()) {
                cumCases += it.newCasesByPublishDate
                dailyNewCaseList.add(it.newCasesByPublishDate)
            } else {
                baseDay = baseDay.minusWeeks(1)
                weeklyCaseList.add(
                    WeeklyCase(
                        baseDay,
                        cumCases,
                        dailyNewCaseList
                    )
                )
                cumCases = 0
                dailyNewCaseList.clear()
            }
        }

        return weeklyCaseList
    }
}

data class WeeklyCase(
    val date: LocalDate,
    val cumCases: Int,
    val dailyNewCase: List<Int>
)