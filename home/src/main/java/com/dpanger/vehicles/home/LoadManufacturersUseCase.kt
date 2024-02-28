package com.dpanger.vehicles.home

import com.dpanger.vehicles.data.ManufacturerRepository
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class LoadManufacturersUseCase @Inject constructor(
    private val repository: ManufacturerRepository
) {
    suspend operator fun invoke(): HomeUiState {
        val result = repository.all()
        return when {
            result.isSuccess -> {
                val manufacturers = result
                    .getOrThrow()
                    .map { UiManufacturer(id = it.id, name = it.name) }
                HomeUiState.Success(manufacturers.toImmutableList())
            }
            else -> HomeUiState.Error
        }
    }
}