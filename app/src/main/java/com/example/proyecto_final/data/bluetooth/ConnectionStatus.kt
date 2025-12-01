package com.example.proyecto_final.data.bluetooth

sealed class ConnectionStatus {
    object Disconnected : ConnectionStatus()
    object Connecting : ConnectionStatus()
    data class Connected(val deviceName: String) : ConnectionStatus()
    data class Error(val message: String) : ConnectionStatus()
    data class MessageReceived(val message: String) : ConnectionStatus()
}