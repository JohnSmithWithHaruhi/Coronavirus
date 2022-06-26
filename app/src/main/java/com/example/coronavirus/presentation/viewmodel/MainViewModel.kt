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
import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.SUNDAY
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters.next
import java.time.temporal.TemporalAdjusters.previous
import java.util.*

/**
 * Main activity's view model.
 */
class MainViewModel : ViewModel() {

    enum class SearchArea {
        UnitedKingdom, England, NorthernIreland, Scotland, Wales
    }

    private val covidRepository: CovidRepository = CovidRepositoryImpl()
    private val weeklyCaseList = MutableLiveData<List<WeeklyCase>>()
    private val searchDialog = MutableLiveData<SearchDialog>()

    /**
     * Weekly case list
     *
     * @return provides live data type for activity to observe.
     */
    fun weeklyCaseList(): LiveData<List<WeeklyCase>> {
        return weeklyCaseList
    }

    fun searchDialog(): LiveData<SearchDialog> {
        return searchDialog
    }

    /**
     * Fetch weekly case list.
     */
    fun fetchWeeklyCaseList() {
        val selectedItem = searchDialog.value?.selectedItem ?: 0
        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                weeklyCaseList.postValue(
                    convertDailyCaseToWeeklyCase(
                        covidRepository.fetchDailyCaseList(SearchArea.values()[selectedItem])
                    )
                )
            }
        }
    }

    fun onShowDialog() {
        searchDialog.postValue(
            SearchDialog(
                selectedItem = searchDialog.value?.selectedItem ?: 0,
                isShowDialog = true
            )
        )
    }

    /**
     *
     */
    fun onAreaSelected(position: Int) {
        searchDialog.value = SearchDialog(selectedItem = position, isShowDialog = false)
        fetchWeeklyCaseList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TestOnly
    fun convertDailyCaseToWeeklyCase(dailyCaseList: List<DailyCase>): List<WeeklyCase> {
        val weeklyCaseList = mutableListOf<WeeklyCase>()

        var baseDay = LocalDate.parse(dailyCaseList.first().date).with(previous(SUNDAY))
        var weeklyCumCases = 0
        var totalCumCases = 0
        var dailyList = mutableListOf<DailyNum>()

        dailyCaseList.forEach {
            if (LocalDate.parse(it.date).toEpochDay() < baseDay.toEpochDay()) {
                weeklyCaseList.add(
                    WeeklyCase(
                        date = baseDay.with(next(SATURDAY))
                            .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                        weeklyCumCases = weeklyCumCases,
                        totalCumCases = totalCumCases,
                        dailyCaseList = dailyList,
                        isExpand = false
                    )
                )

                baseDay = baseDay.minusWeeks(1)
                weeklyCumCases = 0
                totalCumCases = 0
                dailyList = mutableListOf()
            }

            weeklyCumCases += it.newCasesByPublishDate
            totalCumCases =
                if (it.cumCasesByPublishDate > totalCumCases) it.cumCasesByPublishDate else totalCumCases
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
    var isExpand: Boolean
)

/**
 * View model holds all view data for daily case.
 */
data class DailyNum(
    val dayOfWeek: String,
    val dailyCumCases: Int,
)

data class SearchDialog(
    val selectedItem: Int = 0,
    val itemList: List<String> = MainViewModel.SearchArea.values().map { it.name },
    val isShowDialog: Boolean = false
)