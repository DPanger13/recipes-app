package com.dpanger.vehicles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.dpanger.vehicles.ui.themes.VehiclesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VehiclesTheme {
                VehiclesApp(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
