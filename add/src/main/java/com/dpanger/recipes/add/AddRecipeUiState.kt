package com.dpanger.recipes.add

sealed interface AddRecipeUiState {
    data object InProgress : AddRecipeUiState

    data object Saving : AddRecipeUiState
    data object SavingFailed : AddRecipeUiState
    data object Saved : AddRecipeUiState
}
