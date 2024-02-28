package com.dpanger.vehicles.manufacturers

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.themes.VehiclesTheme

@Composable
internal fun Manufacturer(
    manufacturer: UiManufacturer,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = manufacturer.name
    )
}

@Composable
@Preview
internal fun RecipePreview() {
    VehiclesTheme {
        Manufacturer(
            manufacturer = UiManufacturer(
                id = "0",
                name = "Mercedes"
            )
        )
    }
}
