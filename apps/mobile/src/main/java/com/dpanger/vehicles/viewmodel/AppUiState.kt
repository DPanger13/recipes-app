package com.dpanger.vehicles.viewmodel

internal sealed interface AppUiState {
    /**
     * Note that loading is immediate.
     */
    data object Loading : AppUiState

    data class Success(val isSearchEnabled: Boolean) : AppUiState
}
