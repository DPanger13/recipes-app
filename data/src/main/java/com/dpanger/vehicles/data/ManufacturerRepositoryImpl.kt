package com.dpanger.vehicles.data

import uniffi.vehicles.Manufacturer
import uniffi.vehicles.VehiclesException
import uniffi.vehicles.manufacturers

internal class ManufacturerRepositoryImpl : ManufacturerRepository {
    override suspend fun all(): Result<List<Manufacturer>> =
        try {
            val manufacturers = manufacturers()
            Result.success(manufacturers)
        } catch (_: VehiclesException) {
            Result.failure(Exception())
        }
}
