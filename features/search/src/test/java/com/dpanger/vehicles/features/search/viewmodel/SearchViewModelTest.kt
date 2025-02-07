package com.dpanger.vehicles.features.search.viewmodel

import com.dpanger.vehicles.features.search.domain.SearchManufacturersUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SearchViewModelTest {
    private companion object {
        const val MANUFACTURER_NAME = "Toyota"
    }

    private val searchManufacturers: SearchManufacturersUseCase = mockk()
    private val viewModel = SearchViewModel(searchManufacturers)

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
    fun whenViewModelInitialized_thenManufacturerNameEmpty() {
        (viewModel.uiState.value as SearchUiState.NotSubmitted).manufacturerName shouldBe ""
    }

    @Test
    fun whenManufacturerNameChanged_thenUiStateUpdated() {
        viewModel.setManufacturerName(MANUFACTURER_NAME)

        (viewModel.uiState.value as SearchUiState.NotSubmitted).manufacturerName shouldBe MANUFACTURER_NAME
    }

    @Test
    fun whenManufacturerNameCleared_thenUiStateUpdated() {
        viewModel.setManufacturerName(MANUFACTURER_NAME)
        viewModel.clearManufacturerName()

        (viewModel.uiState.value as SearchUiState.NotSubmitted).manufacturerName shouldBe ""
    }

    @Test
    fun givenSearchFails_whenSubmit_thenUiStateIsError() {
        coEvery { searchManufacturers(MANUFACTURER_NAME) } returns SearchUiState.Submitted.Error

        viewModel.run {
            setManufacturerName(MANUFACTURER_NAME)
            submit()
        }

        viewModel.uiState.value shouldBe SearchUiState.Submitted.Error
    }

    @Test
    fun givenNoResultsFound_whenSubmit_thenUiStateIsNoResults() {
        coEvery { searchManufacturers(MANUFACTURER_NAME) } returns SearchUiState.Submitted.NoResults

        viewModel.run {
            setManufacturerName(MANUFACTURER_NAME)
            submit()
        }

        viewModel.uiState.value shouldBe SearchUiState.Submitted.NoResults
    }

    @Test
    fun givenResultsFound_whenSubmit_thenUiStateIsSomeResults() {
        val manufacturers =
            listOf(
                UiManufacturer(id = "1", name = "Corolla"),
            ).toImmutableList()
        val uiState = SearchUiState.Submitted.SomeResults(manufacturers)
        coEvery { searchManufacturers(MANUFACTURER_NAME) } returns uiState

        viewModel.run {
            setManufacturerName(MANUFACTURER_NAME)
            submit()
        }

        viewModel.uiState.value shouldBe uiState
    }
}
