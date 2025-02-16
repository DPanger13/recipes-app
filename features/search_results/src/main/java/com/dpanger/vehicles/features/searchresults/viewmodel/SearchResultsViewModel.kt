package com.dpanger.vehicles.features.searchresults.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpanger.vehicles.features.search.viewmodel.UiManufacturer
import com.dpanger.vehicles.features.searchresults.data.ManufacturerRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SearchResultsViewModel.Factory::class)
class SearchResultsViewModel
    @AssistedInject
    internal constructor(
        @Assisted private val manufacturerName: String,
        private val savedState: SavedStateHandle,
        private val repository: ManufacturerRepository,
    ) : ViewModel() {
        private companion object {
            const val KEY_MANUFACTURER_NAME = "manufacturer_name"
        }

        @AssistedFactory
        interface Factory {
            fun create(manufacturerName: String): SearchResultsViewModel
        }

        private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
        internal val uiState = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                val manufacturerName = savedState.get<String>(KEY_MANUFACTURER_NAME) ?: manufacturerName
                savedState[KEY_MANUFACTURER_NAME] = manufacturerName

                _uiState.value =
                    when (val result = repository.findManufacturer(manufacturerName)) {
                        null -> SearchUiState.Error
                        else -> {
                            if (result.isEmpty()) {
                                SearchUiState.NoResults
                            } else {
                                val manufacturers =
                                    result.map {
                                        UiManufacturer(id = it.id, name = it.name)
                                    }
                                SearchUiState.Results(manufacturers.toImmutableList())
                            }
                        }
                    }
            }
        }
    }
