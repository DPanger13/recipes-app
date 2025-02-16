package com.dpanger.vehicles.features.searchresults.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.features.search.viewmodel.UiManufacturer
import com.dpanger.vehicles.ui.themes.VehiclesTheme
import com.dpanger.vehicles.ui.themes.spacing
import com.dpanger.vehicles.uicomponents.components.lists.SingleLineItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun ManufacturerList(
    manufacturers: ImmutableList<UiManufacturer>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        itemsIndexed(manufacturers) { index, item ->
            if (index == 0) {
                Spacer(Modifier.height(MaterialTheme.spacing.lists.singleLine))
            }

            SingleLineItem(item.name)

            if (index == manufacturers.lastIndex) {
                Spacer(Modifier.height(MaterialTheme.spacing.lists.singleLine))
            }
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
