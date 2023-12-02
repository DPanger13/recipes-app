package com.dpanger.recipes.add

import com.dpanger.recipes.data.RecipeRepository
import javax.inject.Inject

internal class SaveRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(title: String): AddRecipeUiState {
        val result = repository.saveRecipe(title)
        return when {
            result.isSuccess -> AddRecipeUiState.Saved
            else -> AddRecipeUiState.SavingFailed
        }
    }
}
