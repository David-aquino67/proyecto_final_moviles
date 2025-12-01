package com.example.proyecto_final.ui.screens.pantalla_seguridad

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MotionPhotosOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_final.domain.models.SecurityState


@Composable
fun SecurityStatusCard(sensor: SecurityState) {
    val icon = when {
        !sensor.isLocked -> Icons.Default.Warning
        sensor.hasMotion -> Icons.Default.MotionPhotosOn
        else -> Icons.Default.CheckCircle
    }

    val statusText = when {
        sensor.isLocked -> "Cerrada / Activa"
        sensor.hasMotion -> "¡MOVIMIENTO DETECTADO!"
        else -> "Abierta / Inactiva"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = sensor.statusColor.copy(alpha = 0.1f)),
        border = BorderStroke(1.dp, sensor.statusColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = sensor.sensorName,
                tint = sensor.statusColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(sensor.sensorName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(statusText, color = sensor.statusColor, fontSize = 14.sp)
            }
        }
    }
}
