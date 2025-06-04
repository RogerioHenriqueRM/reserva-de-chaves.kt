package com.example.controlhechavesmig


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// sala de aula
data class Sala(
    //id da sala
    val id: String,
    val numero: String,
    val descricao: String, //descricao da sala ex: lab informática


)


class ReservaChaveViewModel : ViewModel() {
    //lista fixa de salas

    private val _salasDisponiveis = MutableStateFlow<List<Sala>>(
        listOf(
            Sala("100", "Sala 01", "Laboratório de Informática"),
            Sala("99", "Sala 02", "Laboratório de Informática 2"),
            Sala("98", "Sala 03", "Laboratório de Eletrônica"),
            Sala("97", "Sala 04", "Laboratório de Logística"),
            Sala("96", "Sala 05", "Laboratório de Química"),
        )
    )
    val salasDisponiveis: StateFlow<List<Sala>> = _salasDisponiveis.asStateFlow()

    private val _salasSelecionada = MutableStateFlow<Sala?>(null)
    val salasSelecionada: StateFlow<Sala?> = _salasSelecionada.asStateFlow()

    private val _mensagemReserva = MutableStateFlow<String?>(null)
    val mensagemReserva: StateFlow<String?> = _mensagemReserva.asStateFlow()

    private val _showConfirmationDialog = MutableStateFlow(false)
    val showConfirmationDialog: StateFlow<Boolean> = _showConfirmationDialog.asStateFlow()

    fun selecionarSala(sala: Sala) {
        // Se a sala clicada já é a selecionada, desmarca. Senão, seleciona a nova sala.
        _salasSelecionada.update { salaAtual ->
            if (salaAtual == sala) null else sala
        }

    }

    fun solicitarConfirmacao() {
        if (_salasSelecionada.value != null) {
            _showConfirmationDialog.value = true
        }

    }


    fun confirmarReserva() {
        _salasSelecionada.value?.let { sala ->
            _mensagemReserva.value = "Sala '${sala.numero}' reservada com sucesso!"
            _salasSelecionada.value = null
        }
        _showConfirmationDialog.value = false

    }

    fun cancelarReserva() {
        _showConfirmationDialog.value = false
    }

    fun limparMensagemReserva() {
        _mensagemReserva.value = null
    }
}
