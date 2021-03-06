package com.example.coronavirus.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronavirus.data.entity.DailyCase
import com.example.coronavirus.data.repository.CovidRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.SUNDAY
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters.next
import java.time.temporal.TemporalAdjusters.previous
import java.util.*
import javax.inject.Inject

/**
 * Main activity's view model.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val covidRepository: CovidRepository
) : ViewModel() {

    private val weeklyCaseList = MutableLiveData<List<WeeklyCase>>()
    private val searchDialog = MutableLiveData<SearchDialog>()

    /**
     * Weekly case list.
     *
     * @return provides live data type for activity to observe.
     */
    fun weeklyCaseList(): LiveData<List<WeeklyCase>> {
        return weeklyCaseList
    }

    /**
     * Search dialog.
     *
     * @return provides live data type for activity to observe.
     */
    fun searchDialog(): LiveData<SearchDialog> {
        return searchDialog
    }

    /**
     * Fetch weekly case list.
     */
    fun fetchWeeklyCaseList() {
        val selectedItem = searchDialog.value?.selectedItem ?: 0
        viewModelScope.launch {
            val area = CovidRepository.SearchArea.values()[selectedItem]
            val dailyCaseList = covidRepository.fetchDailyCaseList(area)
            convertDailyCaseToWeeklyCase(dailyCaseList).let {
                weeklyCaseList.postValue(it)
            }
        }
    }

    /**
     * When dialog on show.
     */
    fun onShowDialog() {
        searchDialog.postValue(
            SearchDialog(
                selectedItem = searchDialog.value?.selectedItem ?: 0,
                isShowDialog = true
            )
        )
    }

    /**
     * When user selected an area.
     */
    fun onAreaSelected(position: Int) {
        searchDialog.value = SearchDialog(selectedItem = position, isShowDialog = false)
        fetchWeeklyCaseList()
    }

    @VisibleForTesting
    fun convertDailyCaseToWeeklyCase(dailyCaseList: List<DailyCase>): List<WeeklyCase> {
        if (dailyCaseList.isEmpty()) {
            return emptyList()
        }

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
    val dailyCumCases: Int
)

/**
 * View model holds all view data for search dialog.
 */
data class SearchDialog(
    val selectedItem: Int = 0,
    val itemList: List<String> =
        CovidRepository.SearchArea.values().map {
            when (it) {
                CovidRepository.SearchArea.UnitedKingdom -> "United Kingdom"
                CovidRepository.SearchArea.NorthernIreland -> "Northern Ireland"
                else -> it.name
            }
        },
    val isShowDialog: Boolean = false
)