package com.example.proyecto_final.data.repository

import com.example.proyecto_final.data.BluetoothService
import com.example.proyecto_final.data.bluetooth.ConnectionStatus
import com.example.proyecto_final.domain.repository.DomoticaRepository
import kotlinx.coroutines.flow.StateFlow


class DomoticaRepositoryImpl(
    private val bluetoothService: BluetoothService
) : DomoticaRepository {


    override val status: StateFlow<ConnectionStatus>
        get() = bluetoothService.connectionStatus

    override suspend fun sendCommand(cmd: String) {
        bluetoothService.send(cmd.trim() + "\n")
    }

    override suspend fun toggleDevice(id: Int, newState: Boolean) {
        val command = if (newState)
            "DEVICE_${id}_ON"
        else
            "DEVICE_${id}_OFF"

        bluetoothService.send(command)
    }

    override suspend fun toggleLock(id: Int, newLocked: Boolean) {
        val command = if (newLocked)
            "LOCK_${id}"
        else
            "UNLOCK_${id}"

        bluetoothService.send(command)
    }
    override suspend fun connect() {
        bluetoothService.connectToESP32()
    }

    override suspend fun disconnect() {
        bluetoothService.closeConnection()
    }
}