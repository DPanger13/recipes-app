package com.dpanger.vehicles.features.searchresults.data

import android.util.Log
import kotlinx.coroutines.withContext
import uniffi.vehicles.VehiclesException
import uniffi.vehicles.searchManufacturers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class ManufacturerRepositoryImpl
    @Inject
    constructor(private val coroutineContext: CoroutineContext) : ManufacturerRepository {
        private val tag = ManufacturerRepository::class.simpleName

        override suspend fun findManufacturer(name: String): List<Manufacturer>? =
            withContext(coroutineContext) {
                try {
                    searchManufacturers(name)
                        .map {
                            Manufacturer(
                                id = it.id,
                                name = it.name,
                            )
                        }
                } catch (exception: VehiclesException) {
                    Log.e(tag, "Failed fetching manufacturers by name: $name", exception)
                    null
                }
            }
    }
