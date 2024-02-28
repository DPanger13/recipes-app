package com.dpanger.vehicles.data

import uniffi.vehicles.Make
import uniffi.vehicles.VehiclesException
import uniffi.vehicles.makes

internal class MakeRepositoryImpl : MakeRepository {
    override fun forManufacturer(id: String): Result<List<Make>> =
        try {
            val makes = makes(id)
            Result.success(makes)
        } catch (_: VehiclesException) {
            Result.failure(Exception())
        }
}