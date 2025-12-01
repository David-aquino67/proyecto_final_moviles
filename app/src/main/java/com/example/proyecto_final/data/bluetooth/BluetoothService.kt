package com.example.proyecto_final.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.proyecto_final.data.bluetooth.ConnectionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.currentCoroutineContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import kotlinx.coroutines.isActive

class BluetoothService(private val context: Context) {

    private val sppUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private var btSocket: BluetoothSocket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    private val _connectionStatus = MutableStateFlow<ConnectionStatus>(ConnectionStatus.Disconnected)
    val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus

    private var listeningRunning = false

    @SuppressLint("MissingPermission")
    suspend fun connectToESP32(targetName: String = "ESP32") = withContext(Dispatchers.IO) {
        if (btAdapter == null) {
            _connectionStatus.value = ConnectionStatus.Error("Bluetooth no soportado")
            return@withContext
        }

        _connectionStatus.value = ConnectionStatus.Connecting

        try {
            val paired = btAdapter.bondedDevices
            val device = paired.firstOrNull { it.name.contains(targetName, true) }

            if (device == null) {
                _connectionStatus.value = ConnectionStatus.Error("No se encontró ESP32 emparejado")
                return@withContext
            }

            try {
                btSocket?.close()  // cerrar conexiones previas
                btSocket = device.createRfcommSocketToServiceRecord(sppUUID)

                btAdapter.cancelDiscovery()
                btSocket!!.connect()

                inputStream = btSocket!!.inputStream
                outputStream = btSocket!!.outputStream

                _connectionStatus.value = ConnectionStatus.Connected(device.name)
                toast("Conectado a ${device.name}")

                startListening()

            } catch (e: IOException) {
                closeConnection()
                _connectionStatus.value = ConnectionStatus.Error("Error socket: ${e.message}")
            }

        } catch (e: SecurityException) {
            _connectionStatus.value = ConnectionStatus.Error("Permisos ausentes")
        }
    }

    private suspend fun startListening() = withContext(Dispatchers.IO) {
        listeningRunning = true

        val buffer = ByteArray(1024)

        try {
            while (listeningRunning && currentCoroutineContext().isActive) {
                val available = inputStream?.available() ?: 0

                if (available > 0) {
                    val read = inputStream?.read(buffer) ?: break
                    val message = String(buffer, 0, read).trim()

                    if (message.isNotEmpty()) {
                        _connectionStatus.value = ConnectionStatus.MessageReceived(message)
                    }
                }
            }
        } catch (_: Exception) {
            closeConnection()
        }
    }

    /** ÚNICA FUNCIÓN PARA ENVIAR DATOS **/
    suspend fun send(data: String) = withContext(Dispatchers.IO) {
        try {
            outputStream?.write(data.toByteArray())
        } catch (e: Exception) {
            _connectionStatus.value = ConnectionStatus.Error("Error al enviar: ${e.message}")
            closeConnection()
        }
    }

    fun closeConnection() {
        listeningRunning = false
        try { btSocket?.close() } catch (_: IOException) {}
        btSocket = null
        _connectionStatus.value = ConnectionStatus.Disconnected
    }

    private fun toast(msg: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
