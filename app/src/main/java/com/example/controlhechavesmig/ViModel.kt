package com.example.controlhechavesmig


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID


// Simulação de um repositório ou fonte de dados
// banco de dados a ser adicionado futuramente no Room
object ReservaRepository {
    private val _reservas =
        MutableStateFlow<List<ReservaChave>>(emptyList()) // "_" é utilizado para dizer que é uma val privada de uso interno diferente de "reserva"
    val reservas: StateFlow<List<ReservaChave>> = _reservas.asStateFlow() // somente para leitura

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

    //add uma reserva
    fun addReserva(novaReserva: ReservaChave): Boolean {
        val atuais = _reservas.value.toMutableList()
        //Lógica de conflito de horário
        val conflito = atuais.any {
            it.sala.id == novaReserva.sala.id &&
                    !(novaReserva.horarioFim.isBefore(it.horarioInicio) ||
                            novaReserva.horarioInicio.isAfter(it.horarioFim))
        }
        //retorno da reserva se não houver conflito
        return if (!conflito) {
            atuais.add(0, novaReserva) //adiciona na primeira posição
            _reservas.value = atuais
            true
        } else {
            false

        }
        //A função getSalaById busca uma sala com um determinado ID e retorna um obj Sala
        // ou null se não for encontrada.
        fun getSalaById(id: String): Sala? {
            return _salasDisponiveis.value.find { it.id == id }
        }
    }


}
// ViewModel add para gerenciar os dados
class ReservaChavesViewModel: ViewModel() {
    private val _reservas = MutableStateFlow<List<ReservaChave>>(emptyList())
    val reservas: StateFlow<List<ReservaChave>> = _reservas.asStateFlow()

    val salasDisponiveis: StateFlow<List<Sala>> = ReservaRepository.salasDisponiveis

    //
}