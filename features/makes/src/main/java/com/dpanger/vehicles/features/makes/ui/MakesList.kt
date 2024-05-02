package com.dpanger.vehicles.features.makes.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.features.makes.viewmodel.UiMake
import com.dpanger.vehicles.ui.themes.VehiclesTheme
import com.dpanger.vehicles.uicomponents.components.lists.SingleLineItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun MakesList(
    makes: ImmutableList<UiMake>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(makes) {
            SingleLineItem(
                text = it.name
            )
        }
    }
}

@Preview
@Composable
private fun MakesListPreview() {
    VehiclesTheme {
        MakesList(
            makes = listOf(
                UiMake(
                    name = "Honda"
                ),
                UiMake(
                    name = "Toyota"
                ),
                UiMake(
                    name = "Nissan"
                ),
            ).toImmutableList()
        )
    }
}