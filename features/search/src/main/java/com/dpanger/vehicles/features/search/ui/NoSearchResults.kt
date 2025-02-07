package com.dpanger.vehicles.features.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.features.search.R
import com.dpanger.vehicles.ui.themes.VehiclesTheme
import com.dpanger.vehicles.ui.themes.spacing

@Composable
internal fun NoSearchResults(
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_search_results),
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.spacing.padding.large),
        )
        Button(
            onClick = onRetryClicked,
        ) {
            Text(stringResource(R.string.retry_search))
        }
    }
}

@Preview
@Composable
private fun NoSearchResultsPreview() =
    VehiclesTheme {
        Surface {
            NoSearchResults(
                onRetryClicked = { },
            )
        }
    }
