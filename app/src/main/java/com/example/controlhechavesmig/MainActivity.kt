package com.example.controlhechavesmig

import android.os.Bundle
import android.print.PrintAttributes
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controlhechavesmig.ui.theme.ControlheChavesMIGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ControlheChavesMIGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    meubotao(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

}



@Preview(showBackground = true)
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

