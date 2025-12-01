package com.example.proyecto_final.data.bluetooth

import com.example.proyecto_final.data.BluetoothService
import kotlinx.coroutines.flow.StateFlow

class BluetoothRepository(public val bluetoothService: BluetoothService) {
    val connectionStatus: StateFlow<ConnectionStatus> = bluetoothService.connectionStatus
    suspend fun connectToDevice(deviceName: String) {
        bluetoothService.connectToESP32(deviceName)
    }
    suspend fun sendCommand(command: String) {
        bluetoothService.send(command)
    }
    fun disconnect() {
        bluetoothService.closeConnection()
    }
}