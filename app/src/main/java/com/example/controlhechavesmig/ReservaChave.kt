package com.example.controlhechavesmig
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//reserva da chave
data class ReservaChave(
    val id: String, //id da reserva
    val sala: Sala,
//Responsaqvel pela sala de aula
    val responsavel: String,
//Horarios de inicio e fim das reservas
    val horarioInicio: LocalDateTime, //data e hora
    val horarioFim: LocalDateTime
){
    // formatter add para não bugar a data e hora
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    fun horarioInicioFormat(): String = horarioInicio.format(formatter)
    fun horarioFimFormat(): String = horarioFim.format(formatter)
}

