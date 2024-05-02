package com.dpanger.vehicles.features.manufacturers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dpanger.vehicles.uicomponents.components.error.ErrorMessage
import com.dpanger.vehicles.uicomponents.components.progress.ProgressIndicator

const val ROUTE_MANUFACTURERS = "manufacturers"

@Composable
fun ManufacturersScreen(
    viewModel: ManufacturersViewModel,
    onManufacturerClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiStateHolder = viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = uiStateHolder.value) {
        is ManufacturersUiState.Loading -> {
            ProgressIndicator(
                modifier = modifier
            )
        }
        is ManufacturersUiState.Success -> {
            ManufacturersList(
                modifier = modifier,
                manufacturers = uiState.manufacturers,
                onManufacturerClicked = onManufacturerClicked
            )
        }
        is ManufacturersUiState.Error -> {
            ErrorMessage(
                modifier = modifier
            )
        }
    }
}
