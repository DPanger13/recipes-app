package com.dpanger.vehicles.features.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchScreen(
    onSearchClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchContent(
        modifier = modifier,
        onSearchClicked = onSearchClicked,
    )
}
