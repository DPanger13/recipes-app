package com.dpanger.recipes.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.recipes.data.Recipe
import com.dpanger.recipes.themes.RecipesTheme

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
    RecipesTheme {
        Recipe(
            recipe = Recipe(
                title = "Pizza"
            )
        )
    }
}
