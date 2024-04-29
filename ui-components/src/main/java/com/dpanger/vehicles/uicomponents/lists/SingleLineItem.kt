package com.dpanger.vehicles.uicomponents.lists

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val PADDING_VERTICAL = 6.dp
private val PADDING_HORIZONTAL = 16.dp

@Composable
fun SingleLineItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .padding(
                horizontal = PADDING_HORIZONTAL,
                vertical = PADDING_VERTICAL
            ),
        text = text,
        style = MaterialTheme.typography.titleMedium
    )
}

@Preview
@Composable
private fun SingleLineItemPreview() {
    SingleLineItem(
        text = "Mercedes"
    )
}