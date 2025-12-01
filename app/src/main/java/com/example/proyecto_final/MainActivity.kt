package com.example.proyecto_final
import com.example.proyecto_final.ui.viewmodel.AppViewModel
import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import com.example.proyecto_final.data.BluetoothService
import com.example.proyecto_final.ui.viewmodel.AppViewModelFactory
import com.example.proyecto_final.ui.screens.DomoticaApp
import com.example.proyecto_final.data.bluetooth.BluetoothRepository
import com.example.proyecto_final.data.repository.DomoticaRepositoryImpl
import com.example.proyecto_final.domain.repository.DomoticaRepository

class MainActivity : ComponentActivity() {

    public lateinit var btService: BluetoothService
    public lateinit var btRepository: BluetoothRepository

    lateinit var domoticaRepo: DomoticaRepository
    lateinit var vmFactory: AppViewModelFactory
    private val requestPerms = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btService = BluetoothService(applicationContext)
        val btRepositoryImpl = DomoticaRepositoryImpl(btService)
        domoticaRepo = btRepositoryImpl
        vmFactory = AppViewModelFactory(domoticaRepo)
        checkPermissions()
        setContent {
            MaterialTheme {
                val viewModel: AppViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = vmFactory
                )

                DomoticaApp(viewModel)
            }
        }
    }

    private fun checkPermissions() {
        val perms = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            perms.add(Manifest.permission.BLUETOOTH_CONNECT)
            perms.add(Manifest.permission.BLUETOOTH_SCAN)
        }
        requestPerms.launch(perms.toTypedArray())
    }

    override fun onDestroy() {
        super.onDestroy()
        btService.closeConnection()
    }
}