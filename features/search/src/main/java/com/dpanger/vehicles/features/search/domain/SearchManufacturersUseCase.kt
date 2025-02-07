package com.dpanger.vehicles.features.search.domain

import com.dpanger.vehicles.features.search.data.ManufacturerRepository
import com.dpanger.vehicles.features.search.viewmodel.SearchUiState
import com.dpanger.vehicles.features.search.viewmodel.UiManufacturer
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class SearchManufacturersUseCase
    @Inject
    constructor(
        private val repository: ManufacturerRepository,
    ) {
        suspend operator fun invoke(manufacturerName: String): SearchUiState.Submitted =
            when (val result = repository.findManufacturer(manufacturerName)) {
                null -> SearchUiState.Submitted.Error
                else -> {
                    if (result.isEmpty()) {
                        SearchUiState.Submitted.NoResults
                    } else {
                        val manufacturers =
                            result.map {
                                UiManufacturer(id = it.id, name = it.name)
                            }
                        SearchUiState.Submitted.SomeResults(manufacturers.toImmutableList())
                    }
                }
            }
    }
