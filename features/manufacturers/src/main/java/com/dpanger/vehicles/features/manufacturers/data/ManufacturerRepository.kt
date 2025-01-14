package com.dpanger.vehicles.features.manufacturers.data

interface ManufacturerRepository {
    suspend fun all(): List<Manufacturer>?
}

fun manufacturerRepository(): ManufacturerRepository = ManufacturerRepositoryImpl()
