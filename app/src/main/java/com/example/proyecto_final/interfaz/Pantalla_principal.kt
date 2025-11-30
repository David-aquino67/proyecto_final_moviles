package com.example.proyecto_final.interfaz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_final.*

@Composable
fun HomeScreen(viewModel: AppViewModel) {
    val devices by viewModel.devices.collectAsState()
    val securityStatus by viewModel.securityStatus.collectAsState()

    val activeLights = devices.count { it.type == "Light" && it.isActive }
    val totalAlerts = securityStatus.count { it.isOpen || it.hasMotion }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Bienvenido a Casa", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("Todo bajo control. Un vistazo rápido a tu sistema.", color = Color.White.copy(alpha = 0.8f))
            }
        }
        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MetricCard(
                title = "Luces ON",
                value = "$activeLights / ${devices.count { it.type == "Light" }}",
                icon = Icons.Default.Lightbulb,
                color = LightBlue,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            MetricCard(
                title = "Ventiladores ON",
                value = devices.count { it.type == "Fan" && it.isActive }.toString(),
                icon = Icons.Default.AcUnit,
                color = LightBlue,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(20.dp))

        val securityColor = if (totalAlerts > 0) AlertRed else SuccessGreen
        val securityText = if (totalAlerts > 0) "$totalAlerts Alerta Activa" else "Sistema Seguro"

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.navigateTo(Screen.SECURITY) },
            colors = CardDefaults.cardColors(containerColor = securityColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Estado de Seguridad",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(Modifier.width(16.dp))
                Text(securityText, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Default.ArrowForward, contentDescription = "Ir a Seguridad", tint = Color.White)
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = title, tint = color, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(8.dp))
                Text(title, color = Color.Gray, fontSize = 14.sp)
            }
            Spacer(Modifier.height(8.dp))
            Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = DarkBlue)
        }
    }
}