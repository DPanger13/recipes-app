package com.dpanger.recipes.home

import com.dpanger.recipes.data.Recipe
import com.dpanger.recipes.data.RecipeRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoadRecipesUseCaseUnitTest {
    private val repository: RecipeRepository = mockk()
    private lateinit var useCase: LoadRecipesUseCase

    @BeforeEach
    fun setUp() {
        val dispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(dispatcher)

        useCase = LoadRecipesUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenErrorLoadingData_thenErrorReturned() =
        runTest {
            coEvery { repository.getRecipes() } returns Result.failure(Exception())

            useCase.invoke() shouldBe HomeUiState.Error
        }

    @Test
    fun whenSuccessfullyLoadedData_thenSuccessReturned() =
        runTest {
            val recipes = emptyList<Recipe>().toImmutableList()
            coEvery { repository.getRecipes() } returns Result.success(recipes)

            useCase.invoke() shouldBe HomeUiState.Success(recipes)
        }
}