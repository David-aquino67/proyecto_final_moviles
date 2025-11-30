package com.example.proyecto_final.interfaz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_final.*
import com.example.proyecto_final.domain.SecurityState
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SecurityScreen(viewModel: AppViewModel) {
    val securityStatus by viewModel.securityStatus.collectAsState()
    var pinDialogVisible by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        item {
            Text(
                "Seguridad y Alertas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Botón de Control de Puerta (PIN Access)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { pinDialogVisible = true },
                colors = CardDefaults.cardColors(containerColor = LightBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.VpnKey, contentDescription = "PIN", tint = Color.White)
                    Spacer(Modifier.width(16.dp))
                    Text("Abrir Puerta Principal (PIN)", color = Color.White, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.LockOpen, contentDescription = "Open", tint = Color.White)
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("Estado de Sensores", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
        }

        securityStatus.forEach { sensor ->
            item {
                SecurityStatusCard(sensor = sensor)
                Spacer(Modifier.height(8.dp))
            }
        }
    }

    if (pinDialogVisible) {
        // Enviar el PIN al ViewModel/UseCase
        PinInputDialog(onDismiss = { pinDialogVisible = false }) { pin ->
            println("PIN enviado al sistema: $pin")
            // Aquí iría la llamada a ValidatePinAccessUseCase(pin)
        }
    }
}

// Componente: Diálogo para ingresar el PIN
@Composable
fun PinInputDialog(onDismiss: () -> Unit, onPinConfirmed: (String) -> Unit) {
    var pinValue by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ingresar PIN de Acceso", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = pinValue,
                    onValueChange = { if (it.length <= 4) pinValue = it.filter { char -> char.isDigit() } },
                    label = { Text("PIN de 4 dígitos") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Key, contentDescription = "PIN") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
                Spacer(Modifier.height(16.dp))
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onPinConfirmed(pinValue)
                    onDismiss()
                },
                enabled = pinValue.length == 4,
                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

// Componente: Tarjeta de Estado del Sensor
@Composable
fun SecurityStatusCard(sensor: SecurityState) {
    val icon = when {
        sensor.isOpen -> Icons.Default.Warning
        sensor.hasMotion -> Icons.Default.MotionPhotosOn
        else -> Icons.Default.CheckCircle
    }
    val statusText = when {
        sensor.isOpen -> "ABIERTA / DESACTIVADO"
        sensor.hasMotion -> "¡MOVIMIENTO DETECTADO!"
        else -> "Cerrada / Activa"
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
                imageVector = icon,
                contentDescription = sensor.sensorName,
                tint = sensor.statusColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(sensor.sensorName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text(statusText, color = sensor.statusColor, fontSize = 14.sp)
            }
        }
    }
}


