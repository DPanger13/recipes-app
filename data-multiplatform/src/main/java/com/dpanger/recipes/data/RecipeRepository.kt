package com.dpanger.recipes.data

interface RecipeRepository {
    suspend fun getRecipes(): Result<List<Recipe>>
    suspend fun saveRecipe(title: String): Result<Unit>
}
