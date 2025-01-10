package com.dpanger.vehicles.features.manufacturers

import com.dpanger.vehicles.data.manufacturers.ManufacturerRepository
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class LoadManufacturersUseCase @Inject constructor(
    private val repository: ManufacturerRepository
) {
    suspend operator fun invoke(): ManufacturersUiState =
        when (val result = repository.all()) {
            null -> ManufacturersUiState.Error
            else -> {
                val manufacturers = result
                    .map { UiManufacturer(id = it.id, name = it.name) }
                    .toImmutableList()
                ManufacturersUiState.Success(manufacturers)
            }
        }
}