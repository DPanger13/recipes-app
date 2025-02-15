package com.dpanger.vehicles.features.manufacturers.domain

import com.dpanger.vehicles.features.manufacturers.data.Manufacturer
import com.dpanger.vehicles.features.manufacturers.data.ManufacturerRepository
import com.dpanger.vehicles.features.manufacturers.viewmodel.ManufacturersUiState
import com.dpanger.vehicles.features.manufacturers.viewmodel.UiManufacturer
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

class LoadManufacturersUseCaseUnitTest {
    private val repository: ManufacturerRepository = mockk()
    private lateinit var useCase: LoadManufacturersUseCase

    @BeforeEach
    fun setUp() {
        val dispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(dispatcher)

        useCase = LoadManufacturersUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenErrorLoadingData_thenErrorReturned() =
        runTest {
            coEvery { repository.all() } returns null

            useCase.invoke() shouldBe ManufacturersUiState.Error
        }

    @Test
    fun whenSuccessfullyLoadedData_thenSuccessReturned() =
        runTest {
            val manufacturer = Manufacturer(id = "0", name = "Cadillac")
            coEvery { repository.all() } returns listOf(manufacturer)

            val uiManufacturer = UiManufacturer(id = manufacturer.id, name = manufacturer.name)
            useCase.invoke() shouldBe ManufacturersUiState.Success(listOf(uiManufacturer).toImmutableList())
        }
}
