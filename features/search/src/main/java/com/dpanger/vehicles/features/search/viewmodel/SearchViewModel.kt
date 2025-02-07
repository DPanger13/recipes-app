package com.dpanger.vehicles.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpanger.vehicles.features.search.domain.SearchManufacturersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    internal constructor(private val searchManufacturers: SearchManufacturersUseCase) : ViewModel() {
        private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.NotSubmitted())
        internal val uiState = _uiState.asStateFlow()

        fun setManufacturerName(name: String) {
            _uiState.value = SearchUiState.NotSubmitted(name)
        }

        fun clearManufacturerName() {
            _uiState.value = SearchUiState.NotSubmitted("")
        }

        fun submit() {
            viewModelScope.launch {
                val manufacturerName =
                    (uiState.value as? SearchUiState.NotSubmitted)
                        ?.manufacturerName
                        ?: ""
                _uiState.value = searchManufacturers(manufacturerName)
            }
        }
    }
