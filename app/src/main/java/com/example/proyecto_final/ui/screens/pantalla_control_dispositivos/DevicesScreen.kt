package com.example.proyecto_final.ui.screens.pantalla_control_dispositivos


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_final.*
import com.example.proyecto_final.domain.models.DeviceState
import com.example.proyecto_final.domain.models.SecurityState
import com.example.proyecto_final.ui.screens.pantalla_control_dispositivos.AccessControlCard
import com.example.proyecto_final.ui.screens.pantalla_control_dispositivos.DeviceControlCard
import com.example.proyecto_final.ui.viewmodel.AppViewModel


@Composable
fun DevicesScreen(viewModel: AppViewModel = viewModel()) {
    val devices by viewModel.devices.collectAsState()
    val securityStatus by viewModel.securityStatus.collectAsState()
    val lightAndFanDevices = devices.filter { it.type == "Light" || it.type == "Fan" }
    val accessDevices = securityStatus.filter { it.id < 200 }


    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        item {
            Text(
                "Luces y Climatización",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(lightAndFanDevices) { device ->
            DeviceControlCard(
                device = device,
                onToggle = { viewModel.toggleDevice(device.id) }
            )
            Spacer(Modifier.height(12.dp))
        }

        item {
            Spacer(Modifier.height(24.dp))
            Text(
                "Puertas y Ventanas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(accessDevices) { device ->
            AccessControlCard(device = device, viewModel = viewModel)
            Spacer(Modifier.height(12.dp))
        }
    }
}
