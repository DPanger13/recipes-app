package com.dpanger.vehicles.features.makes.domain

import com.dpanger.vehicles.data.makes.MakeRepository
import com.dpanger.vehicles.features.makes.viewmodel.MakesUiState
import com.dpanger.vehicles.features.makes.viewmodel.UiMake
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class LoadMakesUseCase @Inject constructor(
    private val repository: MakeRepository
) {
    suspend operator fun invoke(manufacturerId: String): MakesUiState =
        when (val result = repository.forManufacturer(manufacturerId)) {
            null -> MakesUiState.Error
            else -> {
                val makes = result
                   .map { UiMake(name = it.name) }
                   .toImmutableList()
                MakesUiState.Success(makes)
            }
        }
}