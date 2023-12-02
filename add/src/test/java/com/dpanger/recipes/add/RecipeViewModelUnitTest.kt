package com.dpanger.recipes.add

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
class RecipeViewModelUnitTest {
    private val saveRecipe: SaveRecipeUseCase = mockk()
    private lateinit var viewModel: RecipeViewModel

    @BeforeEach
    fun setUp() {
        val dispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(dispatcher)

        viewModel = RecipeViewModel(saveRecipe)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenInitialized_thenTitleEmpty() =
        runTest {
            viewModel.title shouldBe ""
        }

    @Test
    fun whenInitialized_thenUiStateIsInProgress() =
        runTest {
            viewModel.uiState.value shouldBe AddRecipeUiState.InProgress
        }

    @Test
    fun whenTitleUpdated_thenTitlePropertyMatches() =
        runTest {
            val newTitle = "title"
            viewModel.updateTitle(newTitle)

            viewModel.title shouldBe newTitle
        }

    @Test
    fun whenSave_thenUiStateMatches() =
        runTest {
            val title = "title"
            val uiState = AddRecipeUiState.Saved

            coEvery { saveRecipe(title) } returns uiState

            viewModel.updateTitle(title)
            viewModel.save()

            viewModel.uiState.value shouldBe uiState
        }
}
