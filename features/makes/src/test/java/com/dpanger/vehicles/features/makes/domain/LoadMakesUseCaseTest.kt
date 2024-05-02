package com.dpanger.vehicles.features.makes.domain

import com.dpanger.vehicles.data.MakeRepository
import com.dpanger.vehicles.features.makes.viewmodel.MakesUiState
import com.dpanger.vehicles.features.makes.viewmodel.UiMake
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import uniffi.vehicles.Make

internal class LoadMakesUseCaseTest {
    private companion object {
        const val MANUFACTURER_ID = "0"
        const val MAKE_NAME = "Escalade"
    }

    private val repository: MakeRepository = mockk()

    private val useCase = LoadMakesUseCase(repository)

    @Test
    fun whenErrorLoadingData_thenErrorReturned() =
        runTest {
            coEvery { repository.forManufacturer(MANUFACTURER_ID) } returns null

            val result = useCase(MANUFACTURER_ID)

            result shouldBe MakesUiState.Error
        }

    @Test
    fun whenSuccessfullyLoadedData_thenSuccessReturned() =
        runTest {
            val makes = listOf(Make(MAKE_NAME))
            coEvery { repository.forManufacturer(MANUFACTURER_ID) } returns makes

            val result = useCase(MANUFACTURER_ID)

            val uiMakes = listOf(UiMake(MAKE_NAME)).toImmutableList()
            result shouldBe MakesUiState.Success(uiMakes)
        }
}