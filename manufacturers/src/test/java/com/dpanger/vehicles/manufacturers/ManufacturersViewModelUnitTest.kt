package com.dpanger.vehicles.manufacturers

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

class ManufacturersViewModelUnitTest {
    private val useCase: LoadManufacturersUseCase = mockk()
    private lateinit var viewModel: ManufacturersViewModel

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
    fun whenErrorLoadingData_thenUiStateIsError() =
        runTest {
            coEvery { useCase.invoke() } returns ManufacturersUiState.Error

            viewModel = ManufacturersViewModel(useCase)

            viewModel.uiState.value shouldBe ManufacturersUiState.Error
        }

    @Test
    fun whenSuccessfullyLoadedData_thenUiStateIsSuccess() =
        runTest {
            val manufacturers = emptyList<UiManufacturer>().toImmutableList()
            coEvery { useCase.invoke() } returns ManufacturersUiState.Success(manufacturers)

            viewModel = ManufacturersViewModel(useCase)

            viewModel.uiState.value shouldBe ManufacturersUiState.Success(manufacturers)
        }
}