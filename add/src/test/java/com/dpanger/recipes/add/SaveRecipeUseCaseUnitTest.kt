package com.dpanger.recipes.add

import com.dpanger.recipes.data.RecipeRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class SaveRecipeUseCaseUnitTest {
    private val repository: RecipeRepository = mockk()
    private lateinit var saveRecipe: SaveRecipeUseCase

    @BeforeEach
    fun setUp() {
        val dispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(dispatcher)

        saveRecipe = SaveRecipeUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenSavingFailed_thenUiStateIsSavingFailed() =
        runTest {
            coEvery { repository.saveRecipe(TITLE) } returns Result.failure(Exception())

            saveRecipe(TITLE) shouldBe AddRecipeUiState.SavingFailed
        }

    @Test
    fun whenSavingSucceeded_thenUiStateIsSaved() =
        runTest {
            coEvery { repository.saveRecipe(TITLE) } returns Result.success(Unit)

            saveRecipe(TITLE) shouldBe AddRecipeUiState.Saved
        }

    private companion object {
        const val TITLE = "title"
    }
}
