package com.example.coronavirus.data.repository

import androidx.annotation.VisibleForTesting
import com.example.coronavirus.data.datasource.CovidNetworkDatasource
import com.example.coronavirus.data.entity.DailyCase
import com.example.coronavirus.data.model.DailyNum
import com.example.coronavirus.data.model.WeeklyCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.*
import javax.inject.Inject

/**
 * Class implemented [CovidRepository].
 */
class CovidRepositoryImpl @Inject constructor(
    private val covidNetworkDatasource: CovidNetworkDatasource
) : CovidRepository {

    override suspend fun fetchWeeklyCaseList(area: CovidRepository.SearchArea): List<WeeklyCase> {
        return withContext(Dispatchers.IO) {
            val areaType = when (area) {
                CovidRepository.SearchArea.UnitedKingdom -> "overview"
                CovidRepository.SearchArea.NorthernIreland -> "nation;areaName=Northern Ireland"
                else -> "nation;areaName=${area.name}"
            }

            val response = try {
                covidNetworkDatasource.getCases(areaType).execute()
            } catch (e: IOException) {
                return@withContext emptyList()
            }
            if (response.isSuccessful) {
                response.body()
                    ?.let { return@withContext convertDailyCaseToWeeklyCase(it.dailyCase) }
            }
            emptyList()
        }
    }

    @VisibleForTesting
    fun convertDailyCaseToWeeklyCase(dailyCaseList: List<DailyCase>): List<WeeklyCase> {
        if (dailyCaseList.isEmpty()) {
            return emptyList()
        }

        val weeklyCaseList = mutableListOf<WeeklyCase>()

        var baseDay = LocalDate.parse(dailyCaseList.first().date)
            .with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))
        var weeklyCumCases = 0
        var totalCumCases = 0
        var dailyList = mutableListOf<DailyNum>()

        dailyCaseList.forEach {
            if (LocalDate.parse(it.date).toEpochDay() < baseDay.toEpochDay()) {
                weeklyCaseList.add(
                    WeeklyCase(
                        date = baseDay.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
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
