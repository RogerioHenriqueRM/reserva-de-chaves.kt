package com.example.controlhechavesmig

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    sala: Sala?,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog && sala != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Confirmar reserva") },
            text = { Text("Tem certeza que deseja reservar a sala ${sala.numero} - ${sala.descricao}?") },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    onDismiss()
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }


            })
    }
}