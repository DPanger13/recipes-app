package com.dpanger.vehicles.viewmodel

import com.dpanger.vehicles.featureflags.Feature
import com.dpanger.vehicles.featureflags.FeatureFlagRepository
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

@OptIn(ExperimentalCoroutinesApi::class)
internal class AppViewModelTest {
    private val featureFlagRepository: FeatureFlagRepository = mockk()
    private lateinit var viewModel: AppViewModel

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
    fun whenSearchNotEnabled_thenUiStateIsDisabled() =
        runTest {
            coEvery { featureFlagRepository.isEnabled(Feature.SEARCH_HOME_PAGE) } returns false

            viewModel = AppViewModel(featureFlagRepository)

            (viewModel.uiState.value as AppUiState.Success).isSearchEnabled shouldBe false
        }

    @Test
    fun whenSearchEnabled_thenUiStateIsEnabled() =
        runTest {
            coEvery { featureFlagRepository.isEnabled(Feature.SEARCH_HOME_PAGE) } returns true

            viewModel = AppViewModel(featureFlagRepository)

            (viewModel.uiState.value as AppUiState.Success).isSearchEnabled shouldBe true
        }
}
