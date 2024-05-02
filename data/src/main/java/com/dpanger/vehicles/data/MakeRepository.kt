package com.dpanger.vehicles.data

import uniffi.vehicles.Make
import kotlin.coroutines.CoroutineContext

interface MakeRepository {
    suspend fun forManufacturer(id: String): List<Make>?
}

fun makeRepository(coroutineContext: CoroutineContext): MakeRepository =
    MakeRepositoryImpl(coroutineContext)
