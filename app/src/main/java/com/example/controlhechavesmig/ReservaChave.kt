package com.example.controlhechavesmig
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

//reserva da chave
data class ReservaChave(
    val id: String = UUID.randomUUID().toString(), //id da reserva
    val sala: Sala,
//Responsaqvel pela sala de aula
    val responsavel: String,
    val motivo: String,
//Horarios de inicio e fim das reservas
    val horarioInicio: LocalDateTime, //data e hora
    val horarioFim: LocalDateTime
){
    // formatter add para não bugar a data e hora
    fun horarioFormatado(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm")
        return "${horarioInicio.format(formatter)} - ${horarioFim.format(formatter)}"
    }
}


