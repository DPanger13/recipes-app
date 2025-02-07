package com.dpanger.vehicles

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dpanger.vehicles.features.makes.ui.ARG_MANUFACTURER_ID
import com.dpanger.vehicles.features.makes.ui.MakesScreen
import com.dpanger.vehicles.features.makes.ui.ROUTE_MAKES
import com.dpanger.vehicles.features.makes.ui.forManufacturer
import com.dpanger.vehicles.features.manufacturers.ui.ManufacturersScreen
import com.dpanger.vehicles.features.manufacturers.ui.ROUTE_MANUFACTURERS
import com.dpanger.vehicles.features.search.ui.ROUTE_SEARCH
import com.dpanger.vehicles.features.search.ui.SearchScreen
import com.dpanger.vehicles.viewmodel.AppUiState
import com.dpanger.vehicles.viewmodel.AppViewModel

@Composable
fun VehiclesApp(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    val uiStateHolder = viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = uiStateHolder.value) {
        AppUiState.Loading -> {
            // do nothing. loading is immediate.
        }
        is AppUiState.Success -> {
            val startDestination =
                remember(uiState.isSearchEnabled) {
                    if (uiState.isSearchEnabled) ROUTE_SEARCH else ROUTE_MANUFACTURERS
                }

            Surface(
                modifier = modifier,
                color = MaterialTheme.colorScheme.background,
            ) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                ) {
                    if (uiState.isSearchEnabled) {
                        composable(ROUTE_SEARCH) {
                            SearchScreen(
                                viewModel = hiltViewModel(),
                            )
                        }
                    } else {
                        composable(ROUTE_MANUFACTURERS) {
                            ManufacturersScreen(
                                viewModel = hiltViewModel(),
                                onManufacturerClicked = { id ->
                                    val route = forManufacturer(id)
                                    navController.navigate(route)
                                },
                            )
                        }
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
    }
}
