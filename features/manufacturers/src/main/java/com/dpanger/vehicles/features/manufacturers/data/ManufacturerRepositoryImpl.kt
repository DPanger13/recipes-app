package com.dpanger.vehicles.features.manufacturers.data

import android.util.Log
import kotlinx.coroutines.withContext
import uniffi.vehicles.VehiclesException
import uniffi.vehicles.manufacturers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class ManufacturerRepositoryImpl
    @Inject
    constructor(private val coroutineContext: CoroutineContext) : ManufacturerRepository {
        override suspend fun all(): List<Manufacturer>? =
            withContext(coroutineContext) {
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
    }
