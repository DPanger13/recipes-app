package com.dpanger.vehicles.manufacturers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManufacturersViewModel @Inject internal constructor(
    private val loadManufacturers: LoadManufacturersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ManufacturersUiState>(ManufacturersUiState.Loading)
    val uiState: StateFlow<ManufacturersUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = loadManufacturers()
        }
    }
}