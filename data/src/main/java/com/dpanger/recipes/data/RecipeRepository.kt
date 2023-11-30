package com.dpanger.recipes.data

interface RecipeRepository {
    suspend fun getRecipes(): Result<List<Recipe>>
}
