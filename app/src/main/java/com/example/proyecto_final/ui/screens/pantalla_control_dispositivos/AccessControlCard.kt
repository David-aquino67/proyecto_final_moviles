package com.example.proyecto_final.ui.screens.pantalla_control_dispositivos
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_final.domain.models.SecurityState
import com.example.proyecto_final.ui.theme.AlertRed
import com.example.proyecto_final.ui.theme.DarkBlue
import com.example.proyecto_final.ui.theme.SuccessGreen
import com.example.proyecto_final.ui.viewmodel.AppViewModel

@Composable
fun AccessControlCard(device: SecurityState, viewModel: AppViewModel = viewModel()) {
    val isClosed = device.isLocked
    val statusText = if (isClosed) "CERRADA" else "ABIERTA"
    val stateColor = if (isClosed) SuccessGreen else AlertRed
    val buttonIcon = if (isClosed) Icons.Default.Lock else Icons.Default.LockOpen
    val buttonText = if (isClosed) "ABRIR" else "CERRAR"

    val deviceIcon = when {
        device.sensorName.contains("Puerta") -> Icons.Default.MeetingRoom
        device.sensorName.contains("Ventana") -> Icons.Default.Window
        else -> Icons.Default.Security
    }

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
                imageVector = deviceIcon,
                contentDescription = device.sensorName,
                tint = DarkBlue,
                modifier = Modifier.size(36.dp)
            )
            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(device.sensorName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    text = "Estado: $statusText",
                    color = stateColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = {
                    viewModel.toggleLockState(device.id)
                },
                colors = ButtonDefaults.buttonColors(containerColor = stateColor)
            ) {
                Icon(buttonIcon, contentDescription = buttonText, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(4.dp))
                Text(buttonText)
            }
        }
    }
}