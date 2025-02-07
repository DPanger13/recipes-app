package com.dpanger.vehicles.features.search.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.features.search.viewmodel.UiManufacturer
import com.dpanger.vehicles.ui.themes.VehiclesTheme
import com.dpanger.vehicles.uicomponents.components.lists.SingleLineItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun ManufacturerList(
    manufacturers: ImmutableList<UiManufacturer>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(manufacturers) {
            SingleLineItem(it.name)
        }
    }
}

@Preview
@Composable
private fun ManufacturerListPreview() =
    VehiclesTheme {
        Surface {
            ManufacturerList(
                manufacturers =
                    listOf(
                        UiManufacturer(id = "1", name = "Toyota"),
                        UiManufacturer(id = "2", name = "Honda"),
                        UiManufacturer(id = "3", name = "Ford"),
                    ).toImmutableList(),
            )
        }
    }
