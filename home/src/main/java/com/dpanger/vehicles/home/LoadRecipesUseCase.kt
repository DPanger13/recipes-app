package com.dpanger.vehicles.home

import com.dpanger.vehicles.data.RecipeRepository
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class LoadRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(): HomeUiState {
        val result = repository.getRecipes()
        return when {
            result.isSuccess -> HomeUiState.Success(result.getOrThrow().toImmutableList())
            else -> HomeUiState.Error
        }
    }
}