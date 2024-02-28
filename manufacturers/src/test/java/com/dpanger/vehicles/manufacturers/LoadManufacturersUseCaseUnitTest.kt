package com.dpanger.vehicles.manufacturers

import com.dpanger.vehicles.data.ManufacturerRepository
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
import uniffi.vehicles.Manufacturer

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
            coEvery { repository.all() } returns Result.failure(Exception())

            useCase.invoke() shouldBe ManufacturersUiState.Error
        }

    @Test
    fun whenSuccessfullyLoadedData_thenSuccessReturned() =
        runTest {
            val manufacturer = Manufacturer(id = "0", name = "Cadillac")
            coEvery { repository.all() } returns Result.success(listOf(manufacturer))

            val uiManufacturer = UiManufacturer(id = manufacturer.id, name = manufacturer.name)
            useCase.invoke() shouldBe ManufacturersUiState.Success(listOf(uiManufacturer).toImmutableList())
        }
}