package com.example.controlhechavesmig

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalasScreen(viewModel: ReservaChaveViewModel = viewModel()) {
    val salasDisponiveis by viewModel.salasDisponiveis.collectAsState()
    val salaSelecionada by viewModel.salasSelecionada.collectAsState()
    val mensagemReserva by viewModel.mensagemReserva.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(mensagemReserva) {
        if (mensagemReserva != null) {
            Toast.makeText(context, mensagemReserva, Toast.LENGTH_SHORT).show()
            viewModel.limparMensagemReserva()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reservar Salas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ){paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Selecione uma sala:", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),//para ocupar o espaço disponivel
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(salasDisponiveis, key = {it.id}){sala ->
                    SalaItem(
                        sala = sala,
                        isSelected = sala == salaSelecionada,
                        onSalaClick = { viewModel.selecionarSala(sala) }

                    )
            }


        }
        Spacer(modifier = Modifier.height(16.dp))

        if (salaSelecionada != null) {
            Text(
                "Sala selecionada: ${salaSelecionada?.numero}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.reservarSalaSelecionada() },
                modifier = Modifier.fillMaxWidth(),
                enabled = salaSelecionada != null) {
                Text("Reservar ${salaSelecionada?.numero}")
            }
        }else {
            Text("Nenhuma sala selecionada")

        }

    }
}}


@Composable
fun SalaItem(sala: Sala, isSelected: Boolean, onSalaClick: (Sala) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSalaClick(sala) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(sala.numero, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(sala.descricao, style = MaterialTheme.typography.bodyMedium)
            }
            if (isSelected) {
                Spacer(modifier = Modifier.width(8.dp))
                Text("✔️", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}



































































/*import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

//reserva da chave
data class SalaScreen(
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


*/