package com.example.proyecto_final.domain

import androidx.compose.ui.graphics.Color


data class SecurityState(
    val sensorName: String,
    val isOpen: Boolean, // Indica si una puerta/ventana está abierta
    val hasMotion: Boolean, // Indica si un sensor de presencia está activo
    val statusColor: Color // Color del indicador de estado
)