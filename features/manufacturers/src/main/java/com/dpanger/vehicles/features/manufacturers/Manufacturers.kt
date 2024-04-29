package com.dpanger.vehicles.features.manufacturers

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.ui.themes.VehiclesTheme
import com.dpanger.vehicles.uicomponents.components.lists.SingleLineItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun Manufacturers(
    manufacturers: ImmutableList<UiManufacturer>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(manufacturers) {
            SingleLineItem(
                text = it.name
            )
        }
    }
}

@Composable
@Preview
internal fun RecipesPreview() {
    VehiclesTheme {
        Manufacturers(
            manufacturers = listOf(
                UiManufacturer(
                    id = "0",
                    name = "Mercedes"
                ),
                UiManufacturer(
                    id = "1",
                    name = "Lexus"
                ),
                UiManufacturer(
                    id = "2",
                    name = "Cadillac"
                )
            ).toImmutableList()
        )
    }
}
