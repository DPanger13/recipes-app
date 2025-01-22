package com.dpanger.vehicles.uicomponents.components.lists

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.ui.themes.spacing

@Composable
fun SingleLineItem(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier =
            modifier
                .padding(
                    horizontal = MaterialTheme.spacing.margins.horizontal,
                    vertical = MaterialTheme.spacing.lists.singleLine,
                ),
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Preview
@Composable
private fun SingleLineItemPreview() {
    SingleLineItem(
        text = "Mercedes",
    )
}
