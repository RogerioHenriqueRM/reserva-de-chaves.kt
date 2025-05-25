package com.example.controlhechavesmig


import java.util.UUID

// sala de aula
data class Sala(
    //id da sala aleatorio para um futuro banco de dados a ser adicionado
    val id: String = UUID.randomUUID().toString(),
    val numero: String,
    val descricao: String, //descricao da sala ex: lab informática


) {}

