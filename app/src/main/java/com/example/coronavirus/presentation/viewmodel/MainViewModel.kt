package com.example.coronavirus.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronavirus.data.model.WeeklyCase
import com.example.coronavirus.data.repository.CovidRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
            weeklyCaseList.postValue(covidRepository.fetchWeeklyCaseList(area))
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
}

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