package com.dpanger.vehicles.uicomponents.components.error

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.ui.components.R
import com.dpanger.vehicles.ui.themes.spacing

@Composable
fun ErrorMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.title_generic),
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.spacing.padding.small),
        )
        Text(
            text = stringResource(R.string.message_generic),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun ErrorMessagePreview() {
    ErrorMessage()
}
