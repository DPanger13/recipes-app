package com.dpanger.vehicles.features.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.features.search.R

@Composable
internal fun SearchForm(
    manufacturerName: String,
    onManufacturerNameChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = manufacturerName,
            onValueChange = onManufacturerNameChange,
            placeholder = {
                Text(stringResource(R.string.manufacturer_name))
            },
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSearchClicked,
        ) {
            Text(stringResource(R.string.search))
        }
    }
}

@Preview
@Composable
private fun SearchFormWithoutTextPreview() =
    SearchForm(
        manufacturerName = "",
        onManufacturerNameChange = { },
        onSearchClicked = { },
    )

@Preview
@Composable
private fun SearchFormWithTextPreview() =
    SearchForm(
        manufacturerName = "Toyota",
        onManufacturerNameChange = { },
        onSearchClicked = { },
    )
