package com.dpanger.vehicles.data

import com.dpanger.vehicles.data.makes.makeRepository
import com.dpanger.vehicles.data.manufacturers.manufacturerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {
    @Provides
    fun provideManufacturerRepository() = manufacturerRepository()

    @Provides
    fun provideMakeRepository() = makeRepository(Dispatchers.Default)
}
