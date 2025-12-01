package com.example.proyecto_final.ui.screens.pantalla_principal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.proyecto_final.data.bluetooth.ConnectionStatus
import com.example.proyecto_final.ui.theme.AlertRed
import com.example.proyecto_final.ui.theme.DarkBlue
import com.example.proyecto_final.ui.theme.SuccessGreen
import com.example.proyecto_final.ui.theme.WarmGray

@Composable
fun BluetoothConnectionCard(
    status: ConnectionStatus,
    onConnect: () -> Unit,
    onDisconnect: () -> Unit
) {
    val isConnected = status is ConnectionStatus.Connected
    val statusText = when (status) {
        is ConnectionStatus.Connected -> "Conectado a ${status.deviceName}"
        is ConnectionStatus.Connecting -> "Conectando..."
        is ConnectionStatus.Error -> "Error: ${status.message}"
        is ConnectionStatus.MessageReceived -> "Recibiendo datos..."
        else -> "Desconectado"
    }
    val cardColor = if (isConnected) SuccessGreen.copy(alpha = 0.1f) else WarmGray

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.Bluetooth, contentDescription = "BT", tint = DarkBlue)
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Estado Bluetooth", fontWeight = FontWeight.Bold)
                Text(statusText, fontSize = 14.sp, color = if (isConnected) SuccessGreen else DarkBlue)
            }
            Button(
                onClick = { if (isConnected) onDisconnect() else onConnect() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isConnected) AlertRed else DarkBlue
                )
            ) {
                Text(if (isConnected) "Desconectar" else "Conectar")
            }
        }
    }
}