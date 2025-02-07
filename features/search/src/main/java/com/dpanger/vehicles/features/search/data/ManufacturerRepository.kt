package com.dpanger.vehicles.features.search.data

interface ManufacturerRepository {
    /**
     * @param name The full or partial name of the manufacturer.
     */
    suspend fun findManufacturer(name: String): List<Manufacturer>?
}
