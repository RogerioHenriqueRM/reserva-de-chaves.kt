package com.example.controlhechavesmig.ui.telas


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable // componente 
fun LoginScreen(
    onContinueClicked: (String) -> Unit,
) {
    var email by remember { mutableStateOf("") }

    Scaffold( // Scaffold fornece uma estrutura básica de layout do Material Design
        content = { paddingValues ->


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Aplica o padding do Scaffold
                    .padding(horizontal = 32.dp), // Padding lateral adicional
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                //texto e estilo
                Text(
                    text = "Reserva-Chan",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 70.dp)
                )
                // texto e estilo
                Text(
                    text = "Coloque sua conta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                //só texto e estilo
                Text(
                    text = "Faz o Login ai bro",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("email@domain.com") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { onContinueClicked(email) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Continue", color = Color.White, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.weight(1f)) // Empurra o texto abaixo para o final

                // texto e estilo
                Text(
                    text = "Politicas de privacidade? Não temos",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
    )
}

/*
fun validacao () {
    val emailValidacao = "emaildoprofessor@gmail.com"
}*/
