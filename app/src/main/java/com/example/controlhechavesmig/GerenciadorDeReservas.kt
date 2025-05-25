package com.example.controlhechavesmig


import androidx.activity.result.launch
import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

//formulario para nova reserva
data class FormularioReserva(
    val responsavel: String = "",
    val salaSelecionada: Sala? = null,
    val horarioInicio: LocalDateTime = LocalDateTime.now().plusHours(1),
    val horarioFim: LocalDateTime = LocalDateTime.now().plusHours(2),
    val mensagemErro: String? = null,
    val submetidoComSucesso: Boolean = false
)

class ReservaChaveViewModel : ViewModel() {
    private val _reservasInterrnas = mutableListOf<ReservaChave>()

    private val _salasDisponiveis = MutableStateFlow<List<Sala>>(
        listOf(
            Sala("1", "100", "Laboratório de Informática"),
            Sala("2", "101", "Laboratório de Informática 2"),
            Sala("3", "102", "Laboratório de Eletrônica"),
            Sala("4", "103", "Laboratório de Logistica"),
            Sala("5", "104", "Laboratório de Cachorro"),
        )
    )
    val salasDisponiveis: StateFlow<List<Sala>> = _salasDisponiveis.asStateFlow()

    private val _reservasExibidas = MutableStateFlow<List<ReservaChave>>(emptyList())
    val reservasExibidas: StateFlow<List<ReservaChave>> = _reservasExibidas.asStateFlow()

    private val _formulario = MutableStateFlow(FormularioReserva())
    val formulario: StateFlow<FormularioReserva> = _formulario.asStateFlow()

    init {
        // Inicializa a sala selecionada no formulário, se houver salas
        viewModelScope.launch {
            salasDisponiveis.collect { salas ->
                if (salas.isNotEmpty() && _formulario.value.salaSelecionada == null) {
                    _formulario.update { it.copy(salaSelecionada = salas.first()) }
                }
            }
        }
        // Exemplo: Adicionar algumas reservas iniciais para teste
        // adicionarReservaInicial(
        // ReservaChave(
        // sala = _salasDisponiveis.value.first { it.numero == "101" },
        // responsavel = "Admin Teste",
        // horarioInicio = LocalDateTime.now().plusHours(2),
        // horarioFim = LocalDateTime.now().plusHours(3),
        // motivo = "Teste Inicial"
        // )
        // )
    }

   // Funções do Formulário
    fun mudarResponsavel(responsavel: String) {
        _formulario.update { it.copy(responsavel = responsavel) }
    }
}

