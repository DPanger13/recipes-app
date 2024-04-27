package com.dpanger.vehicles.data

import android.util.Log
import uniffi.vehicles.Make
import uniffi.vehicles.VehiclesException
import uniffi.vehicles.makes

internal class MakeRepositoryImpl : MakeRepository {
    override fun forManufacturer(id: String): List<Make>? =
        try {
            makes(id)
        } catch (exception: VehiclesException) {
            Log.e("Makes", "Failed fetching makes", exception)
            null
        }
}