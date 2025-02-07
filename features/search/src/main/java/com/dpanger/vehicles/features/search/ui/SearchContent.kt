package com.dpanger.vehicles.features.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.features.search.viewmodel.SearchUiState
import com.dpanger.vehicles.features.search.viewmodel.UiManufacturer
import com.dpanger.vehicles.ui.themes.VehiclesTheme
import com.dpanger.vehicles.ui.themes.spacing
import com.dpanger.vehicles.uicomponents.components.progress.ProgressIndicator
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun SearchContent(
    uiState: SearchUiState,
    onManufacturerNameChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onRetrySearchClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(MaterialTheme.spacing.margins.horizontal),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            SearchUiState.Loading -> {
                ProgressIndicator()
            }
            is SearchUiState.NotSubmitted -> {
                SearchForm(
                    manufacturerName = uiState.manufacturerName,
                    onManufacturerNameChange = onManufacturerNameChange,
                    onSearchClicked = onSearchClicked,
                )
            }
            SearchUiState.Submitted.Error -> {
                SearchError(
                    onRetryClicked = onRetrySearchClicked,
                )
            }
            SearchUiState.Submitted.NoResults -> {
                NoSearchResults(
                    onRetryClicked = onRetrySearchClicked,
                )
            }
            is SearchUiState.Submitted.SomeResults -> {
                ManufacturerList(
                    modifier = Modifier.fillMaxSize(),
                    manufacturers = uiState.manufacturers,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchContentLoadingPreview() =
    VehiclesTheme {
        Surface {
            SearchContent(
                modifier = Modifier.fillMaxSize(),
                uiState = SearchUiState.Loading,
                onManufacturerNameChange = { },
                onSearchClicked = { },
                onRetrySearchClicked = { },
            )
        }
    }

@Preview
@Composable
private fun SearchContentFormPreview() =
    VehiclesTheme {
        Surface {
            SearchContent(
                modifier = Modifier.fillMaxSize(),
                uiState = SearchUiState.NotSubmitted(manufacturerName = "Toyota"),
                onManufacturerNameChange = { },
                onSearchClicked = { },
                onRetrySearchClicked = { },
            )
        }
    }

@Preview
@Composable
private fun SearchContentErrorPreview() =
    VehiclesTheme {
        Surface {
            SearchContent(
                modifier = Modifier.fillMaxSize(),
                uiState = SearchUiState.Submitted.Error,
                onManufacturerNameChange = { },
                onSearchClicked = { },
                onRetrySearchClicked = { },
            )
        }
    }

@Preview
@Composable
private fun SearchContentNoResultsPreview() =
    VehiclesTheme {
        Surface {
            SearchContent(
                modifier = Modifier.fillMaxSize(),
                uiState = SearchUiState.Submitted.NoResults,
                onManufacturerNameChange = { },
                onSearchClicked = { },
                onRetrySearchClicked = { },
            )
        }
    }

@Preview
@Composable
private fun SearchContentWithResultsPreview() =
    VehiclesTheme {
        Surface {
            SearchContent(
                modifier = Modifier.fillMaxSize(),
                uiState =
                    SearchUiState.Submitted.SomeResults(
                        manufacturers =
                            listOf(
                                UiManufacturer(id = "1", name = "Toyota"),
                                UiManufacturer(id = "2", name = "Honda"),
                                UiManufacturer(id = "3", name = "Ford"),
                            ).toImmutableList(),
                    ),
                onManufacturerNameChange = { },
                onSearchClicked = { },
                onRetrySearchClicked = { },
            )
        }
    }
