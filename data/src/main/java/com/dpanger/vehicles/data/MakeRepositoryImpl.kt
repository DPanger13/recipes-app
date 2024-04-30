package com.dpanger.vehicles.data

import android.util.Log
import kotlinx.coroutines.withContext
import uniffi.vehicles.Make
import uniffi.vehicles.VehiclesException
import uniffi.vehicles.makes
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class MakeRepositoryImpl @Inject constructor(
    private val coroutineContext: CoroutineContext
) : MakeRepository {
    override
    suspend fun forManufacturer(id: String): List<Make>? =
        withContext(coroutineContext) {
            try {
                makes(id)
            } catch (exception: VehiclesException) {
                Log.e("Makes", "Failed fetching makes", exception)
                null
            }
        }
}