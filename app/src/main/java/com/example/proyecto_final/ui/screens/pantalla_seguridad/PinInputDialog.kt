package com.example.proyecto_final.ui.screens.pantalla_seguridad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.proyecto_final.ui.theme.SuccessGreen


@Composable
fun PinInputDialog(onDismiss: () -> Unit, onPinConfirmed: (String) -> Unit) {
    var pinValue by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ingresar PIN de Acceso", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = pinValue,
                    onValueChange = {
                        if (it.length <= 4) pinValue = it.filter { c -> c.isDigit() }
                    },
                    label = { Text("PIN de 4 dígitos") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Key, contentDescription = "PIN") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onPinConfirmed(pinValue)
                    onDismiss()
                },
                enabled = pinValue.length == 4,
                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
