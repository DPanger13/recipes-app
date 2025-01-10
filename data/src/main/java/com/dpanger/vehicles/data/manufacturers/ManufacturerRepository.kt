package com.dpanger.vehicles.data.manufacturers

interface ManufacturerRepository {
    suspend fun all(): List<Manufacturer>?
}

fun manufacturerRepository(): ManufacturerRepository = ManufacturerRepositoryImpl()
