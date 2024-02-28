package com.dpanger.vehicles.home

import kotlinx.collections.immutable.ImmutableList

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val manufacturers: ImmutableList<UiManufacturer>) : HomeUiState
    data object Error : HomeUiState
}