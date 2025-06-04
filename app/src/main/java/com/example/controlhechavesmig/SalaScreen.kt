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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class) // Isso permite o uso de APIs experimentais do Material 3.
@Composable
fun SalasScreen(viewModel: ReservaChaveViewModel = viewModel()) {
    val salasDisponiveis by viewModel.salasDisponiveis.collectAsState()// Coleta a lista de salas disponíveis do ViewModel como um State. Recompõe quando esta lista muda.
    val salaSelecionada by viewModel.salasSelecionada.collectAsState()// mesma coisa que o de cima mas para a sala selecionada
    // Cria um estado mutável para armazenar uma mensagem de reserva (ex: sucesso ou erro). Inicializado como nulo.
    // Esta variável `mensagemReserva` local parece ser para um propósito específico dentro deste Composable,
    // diferente da `mensagemReserva` no ViewModel que é usada para o Toast no LaunchedEffect.
    var mensagemReserva by remember { mutableStateOf<String?>(null) }
    // Coleta o estado para mostrar o diálogo de confirmação do ViewModel. Recompõe quando este muda.
    val showConfirmationDialog by viewModel.showConfirmationDialog.collectAsState()
    val context = LocalContext.current


    // Um efeito colateral que executa quando `viewModel.mensagemReserva` (do ViewModel) muda.
    // É usado aqui para mostrar uma mensagem Toast.
    // `LaunchedEffect` com `viewModel.mensagemReserva.collectAsState().value` como chave significa que o bloco será reexecutado
    // sempre que o valor do StateFlow `mensagemReserva` no ViewModel mudar.
    LaunchedEffect(mensagemReserva) {
        if (mensagemReserva != null) {
            Toast.makeText(context, mensagemReserva, Toast.LENGTH_SHORT).show()
            viewModel.limparMensagemReserva()
        }
    }

    ConfirmationDialog(
        showDialog = showConfirmationDialog,
        sala = salaSelecionada,
        onConfirm = { viewModel.confirmarReserva() },
        onDismiss = { viewModel.cancelarReserva() }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reservar Salas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
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
            Button(onClick = { viewModel.solicitarConfirmacao() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                enabled = true) {
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
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer
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
                Text("✔️", color = MaterialTheme.colorScheme.secondary)
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