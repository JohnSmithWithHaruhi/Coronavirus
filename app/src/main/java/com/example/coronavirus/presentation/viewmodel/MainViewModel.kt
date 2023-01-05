package com.example.coronavirus.presentation.viewmodel

import androidx.compose.runtime.*
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
    private val _searchDialogUiState = MutableStateFlow(SearchDialogUiState())

    val mainUiState: StateFlow<MainUiState> = _mainUiState
    val searchDialogUiState: StateFlow<SearchDialogUiState> = _searchDialogUiState

    init {
        loadWeeklyCaseList()
    }

    /**
     * Reload weekly case list.
     */
    fun reload() {
        loadWeeklyCaseList()
    }

    private fun loadWeeklyCaseList() {
        _mainUiState.value = MainUiState.Loading
        viewModelScope.launch {
            val selectedItem = _searchDialogUiState.value.selectedItem
            val area = CovidRepository.SearchArea.values()[selectedItem]
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
class SearchDialogUiState {
    val itemList: List<String> = CovidRepository.SearchArea.values().map {
        when (it) {
            CovidRepository.SearchArea.UnitedKingdom -> "United Kingdom"
            CovidRepository.SearchArea.NorthernIreland -> "Northern Ireland"
            else -> it.name
        }
    }

    var selectedItem:Int = 0
        private set
    var shouldShowDialog by mutableStateOf(false)
        private set

    fun changeSelectedItem(index: Int) {
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
