package com.example.proyecto_final.domain.usecases

import com.example.proyecto_final.core.Commands
import com.example.proyecto_final.domain.repository.DomoticaRepository

class SendPinUseCase(private val repo: DomoticaRepository) {
    suspend operator fun invoke(pin: String) {
        repo.sendCommand("${Commands.PIN_PREFIX}$pin\n")
    }
}