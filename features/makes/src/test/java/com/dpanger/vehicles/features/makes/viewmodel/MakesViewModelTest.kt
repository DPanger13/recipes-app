package com.dpanger.vehicles.features.makes.viewmodel

import com.dpanger.vehicles.features.makes.domain.LoadMakesUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MakesViewModelTest {
    private companion object {
        const val MANUFACTURER_ID = "0"
    }

    private val loadMakes: LoadMakesUseCase = mockk()
    private val viewModel = MakesViewModel(loadMakes)

    @BeforeEach
    fun setUp() {
        val dispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenInitialized_thenUiStateIsLoading() {
        viewModel.uiState.value shouldBe MakesUiState.Loading
    }

    @Test
    fun whenErrorLoadingData_thenUiStateIsError() {
        coEvery { loadMakes.invoke(any()) } returns MakesUiState.Error

        viewModel.load(MANUFACTURER_ID)

        viewModel.uiState.value shouldBe MakesUiState.Error
    }

    @Test
    fun whenSuccessfullyLoadedData_thenUiStateIsSuccess() {
        val makes = emptyList<UiMake>().toImmutableList()
        coEvery { loadMakes.invoke(any()) } returns MakesUiState.Success(makes)

        viewModel.load(MANUFACTURER_ID)

        val uiState = viewModel.uiState.value
        (uiState as MakesUiState.Success).makes shouldBe makes

    }
}