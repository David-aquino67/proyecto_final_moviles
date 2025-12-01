package com.example.proyecto_final.core

object Commands {
    const val DEV_PREFIX = "DEV:" // Ej: DEV:1:ON\n
    const val SEC_PREFIX = "SEC:" // Ej: SEC:101:LOCK\n
    const val PIN_PREFIX = "PIN:" // Ej: PIN:1234\n
    const val OPEN_DOOR = "PUERTA:ABRIR"
    const val CLOSE_DOOR = "PUERTA:CERRAR"
    const val OPEN_WINDOW = "VENTANA:CERRAR"
    const val CLOSE_WINDOW = "VENTANA:ABRIR"
}