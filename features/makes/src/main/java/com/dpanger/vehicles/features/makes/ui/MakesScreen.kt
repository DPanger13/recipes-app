package com.dpanger.vehicles.features.makes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dpanger.vehicles.features.makes.viewmodel.MakesViewModel

@Composable
fun MakesScreen(
    viewModel: MakesViewModel,
    manufacturerId: String,
    modifier: Modifier = Modifier
) {
    val uiStateHolder = viewModel.uiState.collectAsStateWithLifecycle()
    MakesContent(
        modifier = modifier,
        uiState = uiStateHolder.value
    )

    LaunchedEffect(Unit) {
        viewModel.load(manufacturerId)
    }
}