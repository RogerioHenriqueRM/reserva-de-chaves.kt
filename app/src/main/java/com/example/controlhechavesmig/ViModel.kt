package com.example.controlhechavesmig


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

// ViewModel add para não perder os dados da tela

// banco de dados a ser adicionado futuramente no Room
object ReservaRepo