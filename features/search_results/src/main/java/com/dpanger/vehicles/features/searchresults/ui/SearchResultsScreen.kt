package com.dpanger.vehicles.features.searchresults.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dpanger.vehicles.features.searchresults.viewmodel.SearchResultsViewModel

@Composable
fun SearchResultsScreen(
    viewModel: SearchResultsViewModel,
    onRetrySearchClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiStateHolder = viewModel.uiState.collectAsStateWithLifecycle()
    SearchResultsContent(
        modifier = modifier,
        uiState = uiStateHolder.value,
        onRetrySearchClicked = onRetrySearchClicked,
    )
}
