package com.dpanger.vehicles.data

import uniffi.vehicles.Make

interface MakeRepository {
    fun forManufacturer(id: String): List<Make>?
}

fun makeRepository(): MakeRepository =
    MakeRepositoryImpl()
