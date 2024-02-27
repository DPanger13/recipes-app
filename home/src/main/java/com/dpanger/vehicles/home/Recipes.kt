package com.dpanger.vehicles.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.vehicles.data.Recipe
import com.dpanger.vehicles.themes.VehiclesTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun Recipes(
    recipes: ImmutableList<Recipe>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(recipes) {
            Recipe(it)
        }
    }
}

@Composable
@Preview
internal fun RecipesPreview() {
    VehiclesTheme {
        Recipes(
            recipes = listOf(
                Recipe(
                    title = "Pizza"
                ),
                Recipe(
                    title = "Pasta"
                ),
                Recipe(
                    title = "Salad"
                )
            ).toImmutableList()
        )
    }
}
