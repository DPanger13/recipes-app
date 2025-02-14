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
import androidx.navigation.toRoute
import com.dpanger.vehicles.features.makes.ui.MakesRoute
import com.dpanger.vehicles.features.makes.ui.MakesScreen
import com.dpanger.vehicles.features.manufacturers.ui.ManufacturersRoute
import com.dpanger.vehicles.features.manufacturers.ui.ManufacturersScreen
import com.dpanger.vehicles.features.search.ui.SearchRoute
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
                    if (uiState.isSearchEnabled) SearchRoute else ManufacturersRoute
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
                        composable<SearchRoute> {
                            SearchScreen(
                                viewModel = hiltViewModel(),
                            )
                        }
                    } else {
                        composable<ManufacturersRoute> {
                            ManufacturersScreen(
                                viewModel = hiltViewModel(),
                                onManufacturerClicked = { id ->
                                    val route = MakesRoute(id)
                                    navController.navigate(route)
                                },
                            )
                        }
                    }
                    composable<MakesRoute> { entry ->
                        val route = entry.toRoute<MakesRoute>()
                        MakesScreen(
                            viewModel = hiltViewModel(),
                            manufacturerId = route.manufacturerId,
                        )
                    }
                }
            }
        }
    }
}
