package com.dpanger.vehicles.data

interface RecipeRepository {
    suspend fun getRecipes(): Result<List<Recipe>>
    suspend fun saveRecipe(title: String): Result<Unit>
}

fun recipeRepository() =
    object : RecipeRepository {
        override suspend fun getRecipes(): Result<List<Recipe>> =
            Result.success(emptyList())

        override suspend fun saveRecipe(title: String): Result<Unit> =
            Result.success(Unit)
    }
