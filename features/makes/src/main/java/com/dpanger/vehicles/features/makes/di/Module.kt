package com.dpanger.vehicles.features.makes.di

import com.dpanger.vehicles.features.makes.data.MakeRepository
import com.dpanger.vehicles.features.makes.data.MakeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal class Module {
    @Provides
    fun provideMakeRepository(): MakeRepository = MakeRepositoryImpl(Dispatchers.Default)
}
