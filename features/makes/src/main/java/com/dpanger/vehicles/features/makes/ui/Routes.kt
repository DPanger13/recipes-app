package com.dpanger.vehicles.features.makes.ui

const val ROUTE_MAKES = "makes/{manufacturerId}"
private const val ROUTE_MAKES_BASE = "makes/"

const val ARG_MANUFACTURER_ID = "manufacturerId"

fun forManufacturer(id: String) = ROUTE_MAKES_BASE + id
