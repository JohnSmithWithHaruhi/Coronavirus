package com.example.weeklycase

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covidcase.model.WeeklyCase
import com.example.covidcase.repository.CovidCaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main activity's view model.
 */
@HiltViewModel
class WeeklyCaseViewModel @Inject constructor(
    private val covidCaseRepository: CovidCaseRepository
) : ViewModel() {

    private val _weeklyCaseUiState = MutableStateFlow<WeeklyCaseUiState>(WeeklyCaseUiState.Loading)
    private val _searchAreaDialogUiState = MutableStateFlow(SearchAreaDialogUiState())

    val weeklyCaseUiState: StateFlow<WeeklyCaseUiState> = _weeklyCaseUiState
    val searchAreaDialogUiState: StateFlow<SearchAreaDialogUiState> = _searchAreaDialogUiState

    init {
        loadWeeklyCaseList()
    }

    /**
     * Reload weekly case list.
     */
    fun reload() {
        loadWeeklyCaseList()
    }

    /**
     * Select search area and reload weekly case list.
     */
    fun selectSearchArea() {
        loadWeeklyCaseList()
    }

    private fun loadWeeklyCaseList() {
        _weeklyCaseUiState.value = WeeklyCaseUiState.Loading
        val selectedItem = _searchAreaDialogUiState.value.selectedItem
        val area = CovidCaseRepository.SearchArea.values()[selectedItem]
        viewModelScope.launch {
            val weekList = covidCaseRepository.fetchWeeklyCaseList(area)
            if (weekList.isEmpty()) {
                _weeklyCaseUiState.value = WeeklyCaseUiState.Error
            } else {
                _weeklyCaseUiState.value = WeeklyCaseUiState.Success(weekList)
            }
        }
    }
}

/**
 * View model holds all view data for search dialog.
 */
@Stable
class SearchAreaDialogUiState {
    val itemList: List<String> = CovidCaseRepository.SearchArea.values().map {
        when (it) {
            CovidCaseRepository.SearchArea.UnitedKingdom -> "United Kingdom"
            CovidCaseRepository.SearchArea.NorthernIreland -> "Northern Ireland"
            else -> it.name
        }
    }

    var selectedItem: Int = 0
        private set
    var shouldShowDialog by mutableStateOf(false)
        private set

    fun setSelectedItem(index: Int) {
        selectedItem = index
    }

    fun setShowDialog(shouldShow: Boolean) {
        shouldShowDialog = shouldShow
    }
}

sealed interface WeeklyCaseUiState {
    data class Success(val weeklyCaseList: List<WeeklyCase>) : WeeklyCaseUiState
    object Error : WeeklyCaseUiState
    object Loading : WeeklyCaseUiState
}
