package com.dpanger.vehicles.features.makes.data

internal interface MakeRepository {
    suspend fun forManufacturer(id: String): List<Make>?
}
