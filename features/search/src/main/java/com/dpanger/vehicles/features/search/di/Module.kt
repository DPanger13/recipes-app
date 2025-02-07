package com.dpanger.vehicles.features.search.di

import com.dpanger.vehicles.features.search.data.Manufacturer
import com.dpanger.vehicles.features.search.data.ManufacturerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class Module {
    @Provides
    fun provideManufacturerRepository(): ManufacturerRepository =
        object : ManufacturerRepository {
            override suspend fun findManufacturer(name: String): List<Manufacturer>? = null
        }
}
