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
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters.previous
import java.util.*

/**
 * Main activity's view model.
 */
class MainViewModel : ViewModel() {

    private val covidRepository: CovidRepository = CovidRepositoryImpl()
    private val weeklyCaseList = MutableLiveData<List<WeeklyCase>>()

    /**
     * Weekly case list
     *
     * @return provides live data type for activity to observe.
     */
    fun weeklyCaseList(): LiveData<List<WeeklyCase>> {
        return weeklyCaseList
    }

    /**
     * Fetch weekly case list.
     */
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

        var baseDay = LocalDate.parse(dailyCaseList.first().date).with(previous(SUNDAY))
        var cumCases = 0
        var dailyList = mutableListOf<DailyNum>()

        dailyCaseList.forEach {
            if (LocalDate.parse(it.date).toEpochDay() < baseDay.toEpochDay()) {
                weeklyCaseList.add(
                    WeeklyCase(
                        date = baseDay.plusWeeks(1)
                            .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                        weeklyCumCases = cumCases,
                        totalCumCases = it.cumCasesByPublishDate + cumCases,
                        dailyCaseList = dailyList,
                        expand = false
                    )
                )

                baseDay = baseDay.minusWeeks(1)
                cumCases = 0
                dailyList = mutableListOf()
            }

            cumCases += it.newCasesByPublishDate
            val dayOfWeek = LocalDate.parse(it.date).dayOfWeek
            dailyList.add(
                DailyNum(
                    dayOfWeek = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    dailyCumCases = it.newCasesByPublishDate,
                )
            )
        }

        return weeklyCaseList
    }
}

/**
 * View model holds all view data for weekly case.
 */
data class WeeklyCase(
    val date: String,
    val weeklyCumCases: Int,
    val totalCumCases: Int,
    val dailyCaseList: List<DailyNum>,
    var expand: Boolean
)

/**
 * View model holds all view data for daily case.
 */
data class DailyNum(
    val dayOfWeek: String,
    val dailyCumCases: Int,
)