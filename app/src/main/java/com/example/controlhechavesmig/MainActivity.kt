package com.example.controlhechavesmig

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.controlhechavesmig.ui.theme.ControlheChavesMIGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ControlheChavesMIGTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SalasScreen()
                }
                }
            }
        }
    }





/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ControlheChavesMIGTheme {
        meubotao(modifier = Modifier)
    }
}
@Composable
fun meubotao(modifier: Modifier){
    Box(modifier = Modifier.padding(32.dp)
        .fillMaxSize())
        {
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Botão 1")
        }

        Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(top = 64.dp)) {
            Text("Botão 2")
        }
    }
}

*/