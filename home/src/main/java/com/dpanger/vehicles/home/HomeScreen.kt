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
    when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
        is HomeUiState.Loading -> {
            CircularProgressIndicator(
                modifier = modifier
            )
        }
        is HomeUiState.Success -> {
            Recipes(
                modifier = modifier,
                recipes = uiState.recipes
            )
        }
        is HomeUiState.Error -> {
            Text(
                text = stringResource(id = R.string.generic_error_message)
            )
        }
    }
}
