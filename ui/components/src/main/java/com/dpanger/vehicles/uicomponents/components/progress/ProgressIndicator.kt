package com.dpanger.vehicles.uicomponents.components.progress

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
    )
}

@Preview
@Composable
private fun ProgressIndicatorPreview() {
    ProgressIndicator()
}
