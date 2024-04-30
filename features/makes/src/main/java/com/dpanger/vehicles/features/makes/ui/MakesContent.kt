package com.dpanger.vehicles.features.makes.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.features.makes.viewmodel.MakesUiState
import com.dpanger.vehicles.features.makes.viewmodel.UiMake
import com.dpanger.vehicles.uicomponents.components.error.ErrorMessage
import com.dpanger.vehicles.uicomponents.components.progress.ProgressIndicator
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun MakesContent(
    uiState: MakesUiState,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(uiState, label = "makes") {
        when (it) {
            is MakesUiState.Loading -> {
                ProgressIndicator(
                    modifier = modifier
                )
            }
            is MakesUiState.Success -> {
                MakesList(
                    makes = it.makes,
                    modifier = modifier
                )
            }
            is MakesUiState.Error -> {
                ErrorMessage(
                    modifier = modifier
                )
            }
        }
    }
}

@Preview
@Composable
private fun MakesContentPreview() {
    MakesContent(
        uiState = MakesUiState.Success(
            makes = listOf(
                UiMake(
                    name = "Honda"
                )
            ).toImmutableList()
        )
    )
}
