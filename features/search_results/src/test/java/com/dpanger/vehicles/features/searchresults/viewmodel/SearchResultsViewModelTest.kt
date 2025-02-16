package com.dpanger.vehicles.features.searchresults.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.dpanger.vehicles.features.search.viewmodel.UiManufacturer
import com.dpanger.vehicles.features.searchresults.data.Manufacturer
import com.dpanger.vehicles.features.searchresults.data.ManufacturerRepository
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
internal class SearchResultsViewModelTest {
    private companion object {
        const val MANUFACTURER_ID = "1"
        const val MANUFACTURER_NAME = "Toyota"
    }

    private val repository: ManufacturerRepository = mockk()
    private lateinit var viewModel: SearchResultsViewModel

    private val emptySavedState = SavedStateHandle()

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
    fun givenSearchFails_whenSubmit_thenUiStateIsError() {
        coEvery { repository.findManufacturer(MANUFACTURER_NAME) } returns null

        viewModel = SearchResultsViewModel(MANUFACTURER_NAME, emptySavedState, repository)

        viewModel.uiState.value shouldBe SearchUiState.Error
    }

    @Test
    fun givenNoResultsFound_whenSubmit_thenUiStateIsNoResults() {
        coEvery { repository.findManufacturer(MANUFACTURER_NAME) } returns emptyList()

        viewModel = SearchResultsViewModel(MANUFACTURER_NAME, emptySavedState, repository)

        viewModel.uiState.value shouldBe SearchUiState.NoResults
    }

    @Test
    fun givenResultsFound_whenSubmit_thenUiStateIsSomeResults() {
        val manufacturers =
            listOf(
                Manufacturer(id = MANUFACTURER_ID, name = MANUFACTURER_NAME),
            )
        coEvery { repository.findManufacturer(MANUFACTURER_NAME) } returns manufacturers

        viewModel = SearchResultsViewModel(MANUFACTURER_NAME, emptySavedState, repository)

        val uiState =
            SearchUiState.Results(
                listOf(
                    UiManufacturer(id = MANUFACTURER_ID, name = MANUFACTURER_NAME),
                ).toImmutableList(),
            )
        viewModel.uiState.value shouldBe uiState
    }
}
