package com.example.coronavirus.ui.viewmodel

import com.example.coronavirus.data.entity.DailyCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUpViewModel() {
        viewModel = MainViewModel()
    }

    @Test
    fun convertDailyCaseToWeeklyCase_getsCorrectWeeklyCumCases() {
        val expected = listOf(
            WeeklyCase(
                date = "18 Jun 2022",
                weeklyCumCases = 203,
                totalCumCases = 64,
                dailyCaseList = listOf(
                    DailyNum(
                        dayOfWeek = "Sat",
                        dailyCumCases = 64,
                    ),
                    DailyNum(
                        dayOfWeek = "Fri",
                        dailyCumCases = 49,
                    ),
                    DailyNum(
                        dayOfWeek = "Thu",
                        dailyCumCases = 36,
                    ),
                    DailyNum(
                        dayOfWeek = "Wed",
                        dailyCumCases = 25,
                    ),
                    DailyNum(
                        dayOfWeek = "Tue",
                        dailyCumCases = 16,
                    ),
                    DailyNum(
                        dayOfWeek = "Mon",
                        dailyCumCases = 9,
                    ),
                    DailyNum(
                        dayOfWeek = "Sun",
                        dailyCumCases = 4,
                    )
                ),
                isExpand = false,
            )
        )
        val dailyCaseList = mutableListOf<DailyCase>()
        for (i in 8 downTo 0) {
            dailyCaseList.add(
                DailyCase(
                    date = "2022-06-1${i}",
                    name = "",
                    newCasesByPublishDate = i * i,
                    cumCasesByPublishDate = i * i,
                    newDeaths28DaysByPublishDate = 0,
                    cumDeaths28DaysByPublishDate = 0
                )
            )
        }

        val actual = viewModel.convertDailyCaseToWeeklyCase(dailyCaseList)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun convertDailyCaseToWeeklyCase_withEmptyList_getsEmptyList() {
        val expected = listOf<WeeklyCase>()

        val actual = viewModel.convertDailyCaseToWeeklyCase(listOf())

        Assert.assertEquals(expected, actual)
    }
}