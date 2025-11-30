package com.example.proyecto_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_final.domain.DeviceState
import com.example.proyecto_final.domain.SecurityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.proyecto_final.interfaz.HomeScreen
import com.example.proyecto_final.interfaz.SecurityScreen
import com.example.proyecto_final.interfaz.DevicesScreen

enum class Screen { HOME, DEVICES, SECURITY }

// Paleta de Colores
val DarkBlue = Color(0xFF1E3A8A)      // Color principal (Primary)
val LightBlue = Color(0xFF3B82F6)     // Color de acento (Secondary)
val WarmGray = Color(0xFFF3F4F6)      // Fondo limpio (Background)
val SuccessGreen = Color(0xFF10B981)  // Estado 'Seguro'
val AlertRed = Color(0xFFEF4444)      // Estado 'Alerta/Abierto'

/**
 * Define el tema Material 3 para la aplicación de Domótica.
 */
@Composable
fun DomoticaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = DarkBlue,
            onPrimary = Color.White,
            secondary = LightBlue,
            background = WarmGray,
            surface = Color.White,
            onSurface = Color.Black
        ),
        typography = Typography(),
        content = content
    )
}

class AppViewModel : ViewModel() {
    // Estado actual de la pantalla de navegación.
    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen

    // Estado de dispositivos controlables.
    // Esto simula los datos que vendrían de tu Repositorio MQTT.
    private val _devices = MutableStateFlow(listOf(
        DeviceState(1, "Luz Sala", Icons.Default.Lightbulb, "Light", true),
        DeviceState(2, "Ventilador Cuarto", Icons.Default.AcUnit, "Fan", false),
        DeviceState(3, "Luz Cocina", Icons.Default.Lightbulb, "Light", false),
    ))
    val devices: StateFlow<List<DeviceState>> = _devices

    // Estado de seguridad (sensores y puertas/ventanas).
    private val _securityStatus = MutableStateFlow(listOf(
        SecurityState("Puerta Principal", false, false, SuccessGreen),
        SecurityState("Ventana Habitación", true, false, AlertRed),
        SecurityState("Sensor Pasillo", false, true, AlertRed)
    ))
    val securityStatus: StateFlow<List<SecurityState>> = _securityStatus

    fun toggleDevice(deviceId: Int) {
        _devices.update { currentDevices ->
            currentDevices.map { device ->
                if (device.id == deviceId) {
                    device.copy(isActive = !device.isActive)
                } else {
                    device
                }
            }
        }
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }
}

@Composable
fun DomoticaApp(viewModel: AppViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    Scaffold(
        containerColor = WarmGray,
        bottomBar = {
            BottomNavigationBar(currentScreen) { screen ->
                viewModel.navigateTo(screen)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentScreen) {
                Screen.HOME -> HomeScreen(viewModel)
                Screen.DEVICES -> DevicesScreen(viewModel)
                Screen.SECURITY -> SecurityScreen(viewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(currentScreen: Screen, onItemSelected: (Screen) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(64.dp),
        tonalElevation = 5.dp
    ) {
        val items = listOf(
            Triple("Inicio", Icons.Default.Home, Screen.HOME),
            Triple("Control", Icons.Default.Dashboard, Screen.DEVICES),
            Triple("Seguridad", Icons.Default.Lock, Screen.SECURITY)
        )

        items.forEach { (label, icon, screen) ->
            val selected = currentScreen == screen
            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(screen) },
                icon = {
                    Icon(
                        icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(label, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkBlue,
                    selectedTextColor = DarkBlue,
                    indicatorColor = LightBlue.copy(alpha = 0.2f)
                )
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DomoticaTheme {
                DomoticaApp()
            }
        }
    }
}