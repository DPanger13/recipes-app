package com.dpanger.vehicles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpanger.vehicles.featureflags.Feature
import com.dpanger.vehicles.featureflags.FeatureFlagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel
    @Inject
    internal constructor(private val featureFlagRepository: FeatureFlagRepository) : ViewModel() {
        private val _uiState = MutableStateFlow<AppUiState>(AppUiState.Loading)
        internal val uiState = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                val isSearchEnabled = featureFlagRepository.isEnabled(Feature.SEARCH_HOME_PAGE)
                _uiState.value = AppUiState.Success(isSearchEnabled)
            }
        }
    }
