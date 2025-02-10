package com.dpanger.vehicles.features.search.di

import com.dpanger.vehicles.features.search.data.ManufacturerRepository
import com.dpanger.vehicles.features.search.data.ManufacturerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal class Module {
    @Provides
    fun provideManufacturerRepository(): ManufacturerRepository = ManufacturerRepositoryImpl(Dispatchers.Default)
}
