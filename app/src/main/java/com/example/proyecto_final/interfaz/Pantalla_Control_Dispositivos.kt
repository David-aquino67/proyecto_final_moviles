package com.example.proyecto_final.interfaz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_final.AlertRed
import com.example.proyecto_final.AppViewModel
import com.example.proyecto_final.SuccessGreen
import com.example.proyecto_final.domain.DeviceState


@Composable
fun DevicesScreen(viewModel: AppViewModel) {
    val devices by viewModel.devices.collectAsState()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        item {
            Text(
                "Control de Dispositivos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        devices.forEach { device ->
            item {
                DeviceControlCard(
                    device = device,
                    onToggle = { viewModel.toggleDevice(device.id) }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun DeviceControlCard(device: DeviceState, onToggle: () -> Unit) {
    val stateColor = if (device.isActive) SuccessGreen else AlertRed.copy(alpha = 0.7f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = device.icon,
                contentDescription = device.name,
                tint = stateColor,
                modifier = Modifier.size(36.dp)
            )
            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(device.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    text = if (device.isActive) "Encendido" else "Apagado",
                    color = stateColor,
                    fontSize = 14.sp
                )
            }
            
            Switch(
                checked = device.isActive,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = SuccessGreen,
                    uncheckedTrackColor = AlertRed.copy(alpha = 0.5f)
                )
            )
        }
    }
}