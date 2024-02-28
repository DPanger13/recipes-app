package com.dpanger.vehicles.data

import uniffi.vehicles.Manufacturer

interface ManufacturerRepository {
    suspend fun all(): Result<List<Manufacturer>>
}

fun manufacturerRepository(): ManufacturerRepository =
    ManufacturerRepositoryImpl()
