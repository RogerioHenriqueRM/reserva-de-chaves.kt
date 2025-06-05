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

// Declara a classe ReservaChaveViewModel, que herda de ViewModel.
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
        // Se a sala clicada já é a selecionada, senão, seleciona a nova sala.
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
        // Lógica para quando o usuário confirma no diálogo
        val sala = salasSelecionada.value
        if (sala != null) {
            // --- COLOCAR AQUI A LÓGICA REAL DA RESERVA ---
            // Exemplo:
            // val sucesso = fazerReservaNoBancoDeDados(sala, usuarioAtual, etc...)
            val sucesso = true // Simular sucesso para este exemplo

            if (sucesso) {
                _mensagemReserva.value = "Sala '${sala.numero}' reservada com sucesso!" // DEFINE A MENSAGEM
            } else {
                _mensagemReserva.value = "Falha ao reservar a sala '${sala.numero}'."
            }
        } else {
            _mensagemReserva.value = "Erro: Nenhuma sala selecionada para confirmar."
        }
        _showConfirmationDialog.value = false



        /*_salasSelecionada.value?.let { sala ->
            _mensagemReserva.value = "Sala '${sala.numero}' reservada com sucesso!"
            _salasSelecionada.value = null
        }
        _showConfirmationDialog.value = false*/

    }

    fun cancelarReserva() {
        _showConfirmationDialog.value = false
    }

    fun limparMensagemReserva() {
        _mensagemReserva.value = null
    }
}
