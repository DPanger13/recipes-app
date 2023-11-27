package com.dpanger.recipes.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpanger.recipes.themes.RecipesTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Hello Android!",
    )
}

@Composable
@Preview
private fun HomeScreenPreview() {
    RecipesTheme {
        HomeScreen()
    }
}
