package com.dpanger.vehicles.features.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.features.search.R
import com.dpanger.vehicles.ui.themes.VehiclesTheme
import com.dpanger.vehicles.ui.themes.spacing

@Composable
internal fun SearchContent(
    onSearchClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val manufacturerName =
        remember {
            mutableStateOf("")
        }

    Column(
        modifier =
            modifier
                .padding(MaterialTheme.spacing.margins.horizontal),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = manufacturerName.value,
            onValueChange = {
                manufacturerName.value = it
            },
            placeholder = {
                Text(stringResource(R.string.manufacturer_name))
            },
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.spacing.padding.small),
        )
        Button(
            onClick = {
                onSearchClicked(manufacturerName.value)
            },
        ) {
            Text(stringResource(R.string.search))
        }
    }
}

@Preview
@Composable
private fun SearchContentPreview() =
    VehiclesTheme {
        Surface {
            SearchContent(
                modifier = Modifier.fillMaxSize(),
                onSearchClicked = { },
            )
        }
    }
