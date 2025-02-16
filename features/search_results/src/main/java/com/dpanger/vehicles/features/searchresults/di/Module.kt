package com.dpanger.vehicles.features.searchresults.di

import com.dpanger.vehicles.features.searchresults.data.ManufacturerRepository
import com.dpanger.vehicles.features.searchresults.data.ManufacturerRepositoryImpl
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
