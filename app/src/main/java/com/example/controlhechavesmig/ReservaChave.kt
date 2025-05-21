package com.example.controlhechavesmig

import java.time.LocalDateTime

//reserva da chave
data class ReservaChave(
    val sala: Sala,
//Responsaqvel pela sala de aula
    val responsavel: String,
//Horarios de inicio e fim das reservas
    val horarioInicio: LocalDateTime, //data e hora
    val horarioFim: LocalDateTime

)

