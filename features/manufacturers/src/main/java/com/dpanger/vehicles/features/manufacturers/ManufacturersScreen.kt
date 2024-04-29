package com.dpanger.vehicles.features.manufacturers

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dpanger.vehicles.features.manufacturers.R

@Composable
fun ManufacturersScreen(
    viewModel: ManufacturersViewModel,
    modifier: Modifier = Modifier
) {
    val uiStateHolder = viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = uiStateHolder.value) {
        is ManufacturersUiState.Loading -> {
            CircularProgressIndicator(
                modifier = modifier
            )
        }
        is ManufacturersUiState.Success -> {
            Manufacturers(
                modifier = modifier,
                manufacturers = uiState.manufacturers
            )
        }
        is ManufacturersUiState.Error -> {
            Text(
                text = stringResource(id = R.string.generic_error_message)
            )
        }
    }
}
