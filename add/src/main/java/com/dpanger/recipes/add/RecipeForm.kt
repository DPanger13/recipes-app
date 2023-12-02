package com.dpanger.recipes.add

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
@ExperimentalMaterial3Api
internal fun RecipeForm(
    title: String,
    onTitleChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            TextField(
                value = title,
                onValueChange = onTitleChanged
            )
        }
        item {
            Button(
                onClick = onSaveClicked
            ) {
                Text(
                    text = stringResource(id = R.string.save_recipe)
                )
            }
        }
    }
}

@Composable
@Preview
@ExperimentalMaterial3Api
private fun RecipeFormPreview() {
    RecipeForm(
        title = "",
        onTitleChanged = {},
        onSaveClicked = {}
    )
}
