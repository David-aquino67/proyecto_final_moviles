package com.example.proyecto_final.domain.repository
import com.example.proyecto_final.data.bluetooth.ConnectionStatus
import kotlinx.coroutines.flow.StateFlow


interface DomoticaRepository {
    val status: StateFlow<ConnectionStatus>
    suspend fun sendCommand(cmd: String)
    suspend fun toggleDevice(id: Int, newState: Boolean) // Para luces/ventiladores
    suspend fun toggleLock(id: Int, newLocked: Boolean)
    suspend fun connect()
    suspend fun disconnect()
}