package com.dpanger.vehicles.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.data.Recipe
import com.dpanger.vehicles.themes.VehiclesTheme

@Composable
internal fun Recipe(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = recipe.title
    )
}

@Composable
@Preview
internal fun RecipePreview() {
    VehiclesTheme {
        Recipe(
            recipe = Recipe(
                title = "Pizza"
            )
        )
    }
}
