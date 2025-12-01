package com.example.proyecto_final.ui.screens.pantalla_seguridad


import androidx.compose.foundation.BorderStroke
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_final.domain.models.SecurityState
import com.example.proyecto_final.ui.screens.pantalla_seguridad.PinInputDialog
import com.example.proyecto_final.ui.screens.pantalla_seguridad.SecurityStatusCard
import com.example.proyecto_final.ui.theme.LightBlue
import com.example.proyecto_final.ui.viewmodel.AppViewModel

@Composable
fun SecurityScreen(viewModel: AppViewModel = viewModel()) {
    val securityStatus by viewModel.securityStatus.collectAsState()
    var pinDialogVisible by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Text(
                "Seguridad y Alertas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

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
            Text("Estado de Sensores", style = MaterialTheme.typography.titleMedium)
        }
        securityStatus.forEach { sensor: SecurityState ->
            item {
                SecurityStatusCard(sensor = sensor)
                Spacer(Modifier.height(8.dp))
            }
        }
    }

    if (pinDialogVisible) {
        PinInputDialog(
            onDismiss = { pinDialogVisible = false }
        ) { pin ->
            println("PIN enviado: $pin")
        }
    }
}
