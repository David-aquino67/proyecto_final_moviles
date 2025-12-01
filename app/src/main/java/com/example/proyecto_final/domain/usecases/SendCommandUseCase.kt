package com.example.proyecto_final.domain.usecases
import com.example.proyecto_final.domain.repository.DomoticaRepository

class SendCommandUseCase(private val repo: DomoticaRepository) {
    suspend operator fun invoke(command: String) {
        repo.sendCommand(command)
    }
}