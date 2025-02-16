package com.dpanger.vehicles.features.searchresults.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.features.search.viewmodel.UiManufacturer
import com.dpanger.vehicles.features.searchresults.viewmodel.SearchUiState
import com.dpanger.vehicles.ui.themes.VehiclesTheme
import com.dpanger.vehicles.uicomponents.components.progress.ProgressIndicator
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun SearchResultsContent(
    uiState: SearchUiState,
    onRetrySearchClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (uiState) {
                SearchUiState.Loading -> {
                    ProgressIndicator()
                }
                SearchUiState.Error -> {
                    SearchError(
                        onRetryClicked = onRetrySearchClicked,
                    )
                }
                SearchUiState.NoResults -> {
                    NoSearchResults(
                        onRetryClicked = onRetrySearchClicked,
                    )
                }
                is SearchUiState.Results -> {
                    ManufacturerList(
                        modifier = Modifier.fillMaxSize(),
                        manufacturers = uiState.manufacturers,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchContentLoadingPreview() =
    VehiclesTheme {
        SearchResultsContent(
            uiState = SearchUiState.Loading,
            onRetrySearchClicked = { },
        )
    }

@Preview
@Composable
private fun SearchContentErrorPreview() =
    VehiclesTheme {
        SearchResultsContent(
            uiState = SearchUiState.Error,
            onRetrySearchClicked = { },
        )
    }

@Preview
@Composable
private fun SearchContentNoResultsPreview() =
    VehiclesTheme {
        SearchResultsContent(
            uiState = SearchUiState.NoResults,
            onRetrySearchClicked = { },
        )
    }

@Preview
@Composable
private fun SearchContentWithResultsPreview() =
    VehiclesTheme {
        SearchResultsContent(
            uiState =
                SearchUiState.Results(
                    manufacturers =
                        listOf(
                            UiManufacturer(id = "1", name = "Toyota"),
                            UiManufacturer(id = "2", name = "Honda"),
                            UiManufacturer(id = "3", name = "Ford"),
                        ).toImmutableList(),
                ),
            onRetrySearchClicked = { },
        )
    }
