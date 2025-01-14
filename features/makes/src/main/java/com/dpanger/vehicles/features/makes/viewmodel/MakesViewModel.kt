package com.dpanger.vehicles.features.makes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpanger.vehicles.features.makes.domain.LoadMakesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakesViewModel
    @Inject
    internal constructor(
        private val loadMakes: LoadMakesUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<MakesUiState>(MakesUiState.Loading)
        internal val uiState: StateFlow<MakesUiState> = _uiState.asStateFlow()

        internal fun load(manufacturerId: String) {
            viewModelScope.launch {
                _uiState.value = loadMakes(manufacturerId)
            }
        }
    }
