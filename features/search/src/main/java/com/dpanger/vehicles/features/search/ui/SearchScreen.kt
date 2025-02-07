package com.dpanger.vehicles.features.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dpanger.vehicles.features.search.viewmodel.SearchViewModel

const val ROUTE_SEARCH = "search"

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {
    val uiStateHolder = viewModel.uiState.collectAsStateWithLifecycle()
    SearchContent(
        modifier = modifier,
        uiState = uiStateHolder.value,
        onManufacturerNameChange = viewModel::setManufacturerName,
        onSearchClicked = viewModel::submit,
        onRetrySearchClicked = viewModel::clearManufacturerName,
    )
}
