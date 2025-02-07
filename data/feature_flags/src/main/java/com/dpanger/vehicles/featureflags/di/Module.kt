package com.dpanger.vehicles.featureflags.di

import com.dpanger.vehicles.featureflags.FeatureFlagRepository
import com.dpanger.vehicles.featureflags.FeatureFlagRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface Module {
    @Binds
    fun provideFeatureFlagRepository(impl: FeatureFlagRepositoryImpl): FeatureFlagRepository
}
