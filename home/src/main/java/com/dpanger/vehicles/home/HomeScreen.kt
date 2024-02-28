package com.dpanger.vehicles.home

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val uiStateHolder = viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = uiStateHolder.value) {
        is HomeUiState.Loading -> {
            CircularProgressIndicator(
                modifier = modifier
            )
        }
        is HomeUiState.Success -> {
            Manufacturers(
                modifier = modifier,
                manufacturers = uiState.manufacturers
            )
        }
        is HomeUiState.Error -> {
            Text(
                text = stringResource(id = R.string.generic_error_message)
            )
        }
    }
}
