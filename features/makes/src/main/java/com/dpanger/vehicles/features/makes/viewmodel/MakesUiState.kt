package com.dpanger.vehicles.features.makes.viewmodel

import kotlinx.collections.immutable.ImmutableList

internal interface MakesUiState {
    data object Loading : MakesUiState
    data class Success(val makes: ImmutableList<UiMake>) : MakesUiState
    data object Error : MakesUiState
}