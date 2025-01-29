package com.dpanger.vehicles.featureflags

import javax.inject.Inject

internal class FeatureFlagRepositoryImpl
    @Inject
    constructor() : FeatureFlagRepository {
        override fun isEnabled(feature: Feature): Boolean =
            when (feature) {
                Feature.SEARCH_HOME_PAGE -> false
            }
    }
