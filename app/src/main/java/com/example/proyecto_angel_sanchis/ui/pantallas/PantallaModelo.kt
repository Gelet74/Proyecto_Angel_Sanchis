package com.example.proyecto_angel_sanchis.ui.pantallas


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto_angel_sanchis.ui.state.AppUiState

@Composable
fun PantallaModelo(
    idModelo: String,
    uiState: AppUiState,
    onEditar: (String, String) -> Unit,
    onBorrar: (String) -> Unit
) {
    var mostrarDialogoEditar by remember { mutableStateOf(false) }
    var mostrarDialogoBorrar by remember { mutableStateOf(false) }

    when (uiState) {

        is AppUiState.Cargando -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AppUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${uiState.mensaje}")
            }
        }

        is AppUiState.Exito -> {
            val modelo = uiState.lista.find { it.id == idModelo }

            if (modelo == null) {
                Text("Modelo no encontrado")
                return
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Detalle del modelo", style = MaterialTheme.typography.headlineSmall)
                Text("ID: ${modelo.id}")
                Text("Nombre: ${modelo.nombre}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { mostrarDialogoEditar = true }) {
                    Text("Editar")
                }

                Button(
                    onClick = { mostrarDialogoBorrar = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Borrar")
                }
            }

            // 🔹 DIÁLOGO EDITAR
            if (mostrarDialogoEditar) {
                DialogoEditarModelo(
                    nombreActual = modelo.nombre,
                    onConfirmar = { nuevoNombre ->
                        modelo.id?.let { id ->
                            onEditar(id, nuevoNombre)
                        }
                        mostrarDialogoEditar = false
                    },
                    onCancelar = {
                        mostrarDialogoEditar = false
                    }
                )
            }

            // 🔹 DIÁLOGO BORRAR
            if (mostrarDialogoBorrar) {
                DialogoConfirmarBorrado(
                    onConfirmar = {
                        modelo.id?.let { id ->
                            onBorrar(id)
                        }
                        mostrarDialogoBorrar = false
                    },
                    onCancelar = {
                        mostrarDialogoBorrar = false
                    }
                )
            }
        }
    }
}

@Composable
fun DialogoEditarModelo(
    nombreActual: String,
    onConfirmar: (String) -> Unit,
    onCancelar: () -> Unit
) {
    var nombre by remember { mutableStateOf(nombreActual) }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("Editar modelo") },
        text = {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirmar(nombre) }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun DialogoConfirmarBorrado(
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("Confirmar borrado") },
        text = { Text("¿Seguro que quieres borrar este modelo?") },
        confirmButton = {
            TextButton(onClick = onConfirmar) {
                Text("Sí")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("No")
            }
        }
    )
}
