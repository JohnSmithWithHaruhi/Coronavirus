package com.example.coronavirus.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coronavirus.data.entity.DailyCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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

        assertEquals(expected, actual)
    }

    @Test
    fun convertDailyCaseToWeeklyCase_withEmptyList_getsEmptyList() {
        val expected = listOf<WeeklyCase>()

        val actual = viewModel.convertDailyCaseToWeeklyCase(listOf())

        assertEquals(expected, actual)
    }

    @Test
    fun onShowDialog_isShowDialogIsTrue() {
        val expected = true

        viewModel.onShowDialog()

        assertEquals(expected, viewModel.searchDialog().value?.isShowDialog ?: false)
    }

    @Test
    fun onAreaSelected_getsCorrectArea() {
        val expected = 1

        viewModel.onAreaSelected(1)

        assertEquals(expected, viewModel.searchDialog().value?.selectedItem ?: 0)
    }
}