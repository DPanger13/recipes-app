package com.dpanger.vehicles.features.search.viewmodel

import kotlinx.collections.immutable.ImmutableList

internal sealed interface SearchUiState {
    data object Loading : SearchUiState

    data class NotSubmitted(val manufacturerName: String = "") : SearchUiState

    sealed interface Submitted : SearchUiState {
        data object NoResults : Submitted

        data class SomeResults(val manufacturers: ImmutableList<UiManufacturer>) : Submitted

        data object Error : Submitted
    }
}
