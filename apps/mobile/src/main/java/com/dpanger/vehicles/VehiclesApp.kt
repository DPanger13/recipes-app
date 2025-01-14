package com.dpanger.vehicles

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dpanger.vehicles.features.makes.ui.ARG_MANUFACTURER_ID
import com.dpanger.vehicles.features.makes.ui.MakesScreen
import com.dpanger.vehicles.features.makes.ui.ROUTE_MAKES
import com.dpanger.vehicles.features.makes.ui.forManufacturer
import com.dpanger.vehicles.features.manufacturers.ui.ManufacturersScreen
import com.dpanger.vehicles.features.manufacturers.ui.ROUTE_MANUFACTURERS

@Composable
fun VehiclesApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = ROUTE_MANUFACTURERS
        ) {
            composable(ROUTE_MANUFACTURERS) {
                ManufacturersScreen(
                    viewModel = hiltViewModel(),
                    onManufacturerClicked = { id ->
                        val route = forManufacturer(id)
                        navController.navigate(route)
                    }
                )
            }
            composable(ROUTE_MAKES) { entry ->
                MakesScreen(
                    viewModel = hiltViewModel(),
                    manufacturerId = entry.arguments?.getString(ARG_MANUFACTURER_ID).orEmpty(),
                )
            }
        }
    }
}