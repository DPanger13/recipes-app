package com.dpanger.vehicles.home

import com.dpanger.vehicles.data.Recipe
import kotlinx.collections.immutable.ImmutableList

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val recipes: ImmutableList<Recipe>) : HomeUiState
    data object Error : HomeUiState
}