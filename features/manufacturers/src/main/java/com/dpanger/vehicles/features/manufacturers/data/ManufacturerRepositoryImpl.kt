package com.dpanger.vehicles.features.manufacturers.data

import android.util.Log
import uniffi.vehicles.VehiclesException
import uniffi.vehicles.manufacturers

internal class ManufacturerRepositoryImpl : ManufacturerRepository {
    override suspend fun all(): List<Manufacturer>? =
        try {
            manufacturers()
                .map {
                    Manufacturer(
                        id = it.id,
                        name = it.name,
                    )
                }
        } catch (exception: VehiclesException) {
            Log.e("Manufacturers", "Failed fetching manufacturers", exception)
            null
        }
}
