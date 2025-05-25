package com.example.controlhechavesmig


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ReservaChaveViewModel : ViewModel() {
    //lista fixa de salas

    private val _salasDisponiveis = MutableStateFlow<List<Sala>>(
        listOf(
            Sala("100", "Sala 01", "Laboratório de Informática"),
            Sala("99", "Sala 02", "Laboratório de Informática 2"),
            Sala("98", "Sala 03", "Laboratório de Eletrônica"),
            Sala("97", "Sala 04", "Laboratório de Logistica"),
            Sala("96", "Sala 05", "Laboratório de Cachorro"),
        )
    )
    val salasDisponiveis: StateFlow<List<Sala>> = _salasDisponiveis.asStateFlow()

    private val _salasSelecionada = MutableStateFlow<Sala?>(null)
    val salasSelecionada: StateFlow<Sala?> = _salasSelecionada.asStateFlow()

    private val _mensagemReserva = MutableStateFlow<String?>(null)
    val mensagemReserva: StateFlow<String?> = _mensagemReserva.asStateFlow()

    fun selecionarSala(sala: Sala) {
        _salasSelecionada.update { sala }

    }

    fun reservarSalaSelecionada() {
        _salasSelecionada.value?.let { sala ->
            _mensagemReserva.value ="Sala '${sala.numero}' reservada com sucesso!"
            _salasSelecionada.value = null
        }

    }

    fun limparMensagemReserva() {
        _mensagemReserva.value = null
    }
}
