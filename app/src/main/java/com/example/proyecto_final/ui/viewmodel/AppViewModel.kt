package com.example.proyecto_final.ui.viewmodel
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final.domain.models.DeviceState
import com.example.proyecto_final.domain.models.SecurityState
import com.example.proyecto_final.domain.repository.DomoticaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.proyecto_final.ui.screens.Screen
class AppViewModel(public val repo: DomoticaRepository) : ViewModel() {
    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen
    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }
    val btStatus = repo.status
    private val _devices = MutableStateFlow(
        listOf(
            DeviceState(
                id = 1,
                name = "Luz Sala",
                icon = androidx.compose.material.icons.Icons.Default.Lightbulb,
                type = "Light",
                isActive = true
            ),
            DeviceState(
                id = 2,
                name = "Ventilador Cuarto",
                icon = androidx.compose.material.icons.Icons.Default.AcUnit,
                type = "Fan",
                isActive = false
            )
        )
    )
    val devices: StateFlow<List<DeviceState>> = _devices
    fun toggleDevice(id: Int) {
        _devices.update { list ->
            list.map { device ->
                if (device.id == id) {
                    val newState = !device.isActive

                    // enviar al repo (API o Bluetooth, MQTT, ESP32...)
                    viewModelScope.launch { repo.toggleDevice(id, newState) }

                    device.copy(isActive = newState)
                } else device
            }
        }
    }
    private val _securityStatus = MutableStateFlow(
        listOf(
            SecurityState(
                id = 101,
                sensorName = "Puerta Principal",
                isLocked = true,
                hasMotion = false,
                statusColor = androidx.compose.ui.graphics.Color.Green
            ),
            SecurityState(
                id = 102,
                sensorName = "Ventana Sala",
                isLocked = false,
                hasMotion = false,
                statusColor = androidx.compose.ui.graphics.Color.Red
            )
        )
    )
    val securityStatus: StateFlow<List<SecurityState>> = _securityStatus


    fun toggleLockState(id: Int) {
        _securityStatus.update { list ->
            list.map { security ->
                if (security.id == id) {
                    val newLocked = !security.isLocked

                    // mandar al repo
                    viewModelScope.launch { repo.toggleLock(id, newLocked) }

                    val newColor = if (newLocked)
                        androidx.compose.ui.graphics.Color.Green
                    else
                        androidx.compose.ui.graphics.Color.Red

                    security.copy(isLocked = newLocked, statusColor = newColor)
                } else security
            }
        }
    }
    fun connectToBluetooth() {
        viewModelScope.launch {
            repo.connect()
        }
    }
    fun disconnectBluetooth() {
        viewModelScope.launch {
            repo.disconnect()
        }
    }
}


