package com.example.proyecto_final.ui.screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_final.ui.screens.pantalla_control_dispositivos.DevicesScreen
import com.example.proyecto_final.ui.screens.pantalla_principal.HomeScreen
import com.example.proyecto_final.ui.screens.pantalla_seguridad.SecurityScreen
import com.example.proyecto_final.ui.viewmodel.AppViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DomoticaApp(viewModel: AppViewModel = viewModel()) {

    val currentScreen by viewModel.currentScreen.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = currentScreen,
                onItemSelected = viewModel::navigateTo
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (currentScreen) {
                Screen.DEVICES -> DevicesScreen(viewModel)
                Screen.SECURITY -> SecurityScreen(viewModel)
                Screen.HOME ->HomeScreen(viewModel)
            }
        }
    }
}
@Composable
fun BottomNavigationBar(
    currentScreen: Screen,
    onItemSelected: (Screen) -> Unit
) {
    NavigationBar(tonalElevation = 5.dp) {

        val items = listOf(
            Triple("Inicio", Icons.Default.Home, Screen.HOME),
            Triple("Control", Icons.Default.Dashboard, Screen.DEVICES),
            Triple("Seguridad", Icons.Default.Lock, Screen.SECURITY)
        )

        items.forEach { (label, icon, screen) ->
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = { onItemSelected(screen) },
                icon = { Icon(icon, contentDescription = label) },
                label = {
                    Text(
                        text = label,
                        fontWeight = if (currentScreen == screen)
                            FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}
