package com.dpanger.vehicles.features.search.domain

import com.dpanger.vehicles.features.search.data.Manufacturer
import com.dpanger.vehicles.features.search.data.ManufacturerRepository
import com.dpanger.vehicles.features.search.viewmodel.SearchUiState
import com.dpanger.vehicles.features.search.viewmodel.UiManufacturer
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class SearchManufacturersUseCaseTest {
    private companion object {
        const val MANUFACTURER_ID = "1"
        const val MANUFACTURER_NAME = "Toyota"
    }

    private val repository: ManufacturerRepository = mockk()
    private val searchManufacturers = SearchManufacturersUseCase(repository)

    @Test
    fun whenErrorFindingManufacturers_thenErrorReturned() =
        runTest {
            coEvery { repository.findManufacturer(MANUFACTURER_NAME) } returns null

            val result = searchManufacturers(MANUFACTURER_NAME)

            result shouldBe SearchUiState.Submitted.Error
        }

    @Test
    fun whenNoManufacturersFound_thenNoResultsReturned() =
        runTest {
            coEvery { repository.findManufacturer(MANUFACTURER_NAME) } returns emptyList()

            val result = searchManufacturers(MANUFACTURER_NAME)

            result shouldBe SearchUiState.Submitted.NoResults
        }

    @Test
    fun whenManufacturersFound_thenResultsReturned() =
        runTest {
            val manufacturers =
                listOf(
                    Manufacturer(id = MANUFACTURER_ID, name = MANUFACTURER_NAME),
                )
            coEvery { repository.findManufacturer(MANUFACTURER_NAME) } returns manufacturers

            val result = searchManufacturers(MANUFACTURER_NAME)

            val uiManufacturers =
                listOf(
                    UiManufacturer(id = MANUFACTURER_ID, name = MANUFACTURER_NAME),
                ).toImmutableList()
            result shouldBe SearchUiState.Submitted.SomeResults(uiManufacturers)
        }
}
