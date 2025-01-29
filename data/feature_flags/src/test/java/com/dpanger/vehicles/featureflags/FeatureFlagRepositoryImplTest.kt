package com.dpanger.vehicles.featureflags

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class FeatureFlagRepositoryImplTest {
    private val repository: FeatureFlagRepository = FeatureFlagRepositoryImpl()

    @Test
    fun givenSearchFeature_whenCheckIsEnabled_thenReturnsFalse() {
        repository.isEnabled(Feature.SEARCH_HOME_PAGE) shouldBe false
    }
}
