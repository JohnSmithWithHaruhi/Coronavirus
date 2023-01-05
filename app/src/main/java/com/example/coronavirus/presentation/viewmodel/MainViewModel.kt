package com.example.coronavirus.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coronavirus.data.model.WeeklyCase
import com.example.coronavirus.data.repository.CovidRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main activity's view model.
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val covidRepository: CovidRepository) :
    ViewModel() {

    private val _mainUiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    private val _searchAreaDialogUiState = MutableStateFlow(SearchAreaDialogUiState())

    val mainUiState: StateFlow<MainUiState> = _mainUiState
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
        _mainUiState.value = MainUiState.Loading
        val selectedItem = _searchAreaDialogUiState.value.selectedItem
        val area = CovidRepository.SearchArea.values()[selectedItem]
        viewModelScope.launch {
            val weekList = covidRepository.fetchWeeklyCaseList(area)
            if (weekList.isEmpty()) {
                _mainUiState.value = MainUiState.Error
            } else {
                _mainUiState.value = MainUiState.Success(weekList)
            }
        }
    }
}

/**
 * View model holds all view data for search dialog.
 */
@Stable
class SearchAreaDialogUiState {
    val itemList: List<String> = CovidRepository.SearchArea.values().map {
        when (it) {
            CovidRepository.SearchArea.UnitedKingdom -> "United Kingdom"
            CovidRepository.SearchArea.NorthernIreland -> "Northern Ireland"
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

sealed interface MainUiState {
    data class Success(val weeklyCaseList: List<WeeklyCase>) : MainUiState
    object Error : MainUiState
    object Loading : MainUiState
}
