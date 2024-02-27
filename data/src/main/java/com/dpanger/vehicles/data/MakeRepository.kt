package com.dpanger.vehicles.data

import uniffi.vehicles.Make

interface MakeRepository {
    fun forManufacturer(id: String): Result<List<Make>>
}

fun makeRepository(): MakeRepository =
    MakeRepositoryImpl()
