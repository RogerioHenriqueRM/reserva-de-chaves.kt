package com.example.controlhechavesmig
//Classe que gerencia as reservas das salas de aula
class GerenciaReservas {
    private val reservas = mutableListOf<ReservaChave>() // mantém uma lista mutável

    // Add reserva
    fun addReserva(reserva: ReservaChave): Boolean {
// add uma val para evitar conflitos de cadastro
        val conflito = reservas.any {
            it.sala == reserva.sala &&
                    !(reserva.horarioFim.isBefore(it.horarioInicio) || reserva.horarioInicio.isAfter(
                        it.horarioFim
                    ))
        }
        return if (!conflito) {
            reservas.add(reserva)
            true
        } else {
            false // não adiciona a reserva se houver conflito
        }
    }

    //listas de reservas
    fun listarReservas(): List<ReservaChave> {
        return reservas.toList() //Retorna uma copia para evitar modificações externas

    }

    // Enconta reserva desala especifica
    fun encontrarReservaSala(sala: Sala): List<ReservaChave> {
        return reservas.filter {
            it.sala == sala
        }

    }

    //Cancela uma reserva
    fun cancelarReserva(reserva: ReservaChave): Boolean {
        return reservas.remove(reserva)
    }
}