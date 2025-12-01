package com.example.proyecto_final.domain.models
import androidx.compose.ui.graphics.vector.ImageVector

data class DeviceState(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val type: String,
    val isActive: Boolean
)