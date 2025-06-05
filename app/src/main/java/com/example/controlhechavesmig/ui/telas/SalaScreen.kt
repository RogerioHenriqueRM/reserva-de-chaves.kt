package com.example.controlhechavesmig.ui.telas

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
import com.example.controlhechavesmig.ConfirmationDialog
import com.example.controlhechavesmig.ReservaChaveViewModel
import com.example.controlhechavesmig.Sala

@OptIn(ExperimentalMaterial3Api::class) // Isso permite o uso de APIs experimentais do Material 3 do google
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
    // Exibe o Composable ConfirmationDialog com base no estado showConfirmationDialog
    ConfirmationDialog(
        // Controla a visibilidade do dialog
        showDialog = showConfirmationDialog,
        // Passa a sala selecionada para o dialog.
        sala = salaSelecionada,
        // Função lambda  chamada quando o usuário confirma a reserva
        onConfirm = { viewModel.confirmarReserva() },
        // Função lambda para chamada quando o usuário sai do dialog
        onDismiss = { viewModel.cancelarReserva() }
    )
    //estrutura de layout
    Scaffold(
        // Define a barra de aplicativo superior.
        topBar = {
            TopAppBar(
                // Define o título da tela
                title = { Text("Reservar Salas") },
                // Personaliza as cores
                colors = TopAppBarDefaults.topAppBarColors(
                    // Define a cor de fundo da barra
                    containerColor = MaterialTheme.colorScheme.secondary,
                    // Define a cor do texto do título
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
                // O conteúdo do Scaffold é fornecido nesta lambda, com paddingValues para evitar sobreposição com a barra de app.
    ){paddingValues ->
        // Column organiza verticalmente os elementos filhos
        Column(
            //modificação de estilização
            modifier = Modifier
                    // Faz a Column ocupar o espaço disponível
                .fillMaxSize()
                // Aplica o preenchimento fornecido pelo Scaffold para evitar barras do sistema/barra de app
                .padding(paddingValues)
                .padding(16.dp),
            //centraliza dentro da Column
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //texto de título
            Text(
                // Conteúdo e estilo do texto
                "Selecione uma sala:", style = MaterialTheme.typography.headlineSmall)
            // Adiciona um espaço vertical de 16dp
            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn é usado para exibir uma lista rolável
            LazyColumn(
                modifier = Modifier.weight(1f),//para a LazyColumn ocupar o espaço disponivel
                //espaçamento entre os itens
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                // `items` é uma função construtora para LazyColumn para definir o que tem dentro
                // Ele faz interação `salasDisponiveis`. A `key` ajuda o Compose a otimizar as recomposições.
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