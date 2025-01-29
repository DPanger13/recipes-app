package com.dpanger.vehicles.featureflags

interface FeatureFlagRepository {
    fun isEnabled(feature: Feature): Boolean
}
