package com.dpanger.vehicles.features.searchresults.viewmodel

import com.dpanger.vehicles.features.search.viewmodel.UiManufacturer
import kotlinx.collections.immutable.ImmutableList

internal sealed interface SearchUiState {
    data object Loading : SearchUiState

    data object Error : SearchUiState

    data object NoResults : SearchUiState

    data class Results(val manufacturers: ImmutableList<UiManufacturer>) : SearchUiState
}
