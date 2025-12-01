package com.example.proyecto_final.domain.models
import androidx.compose.ui.graphics.Color

data class SecurityState(
    val id: Int,
    val sensorName: String,
    val isLocked: Boolean,
    val hasMotion: Boolean,
    val statusColor: Color
)