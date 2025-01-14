package com.dpanger.vehicles.data

import com.dpanger.vehicles.data.manufacturers.manufacturerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {
    @Provides
    fun provideManufacturerRepository() = manufacturerRepository()
}
