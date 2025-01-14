package com.dpanger.vehicles.features.manufacturers.viewmodel

import kotlinx.collections.immutable.ImmutableList

sealed interface ManufacturersUiState {
    data object Loading : ManufacturersUiState

    data class Success(val manufacturers: ImmutableList<UiManufacturer>) : ManufacturersUiState

    data object Error : ManufacturersUiState
}
