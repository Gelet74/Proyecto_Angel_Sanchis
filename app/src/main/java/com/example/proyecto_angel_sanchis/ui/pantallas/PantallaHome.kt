package com.example.proyecto_angel_sanchis.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.proyecto_angel_sanchis.ui.state.AppUiState

@Composable
fun PantallaHome(
    uiState: AppUiState,
    onVerModelosClick: () -> Unit,
    onCargarDatos: () -> Unit
) {
    // Carga inicial, solo se lanza la primera vez que iniciamos
    LaunchedEffect(Unit) {
        onCargarDatos()
    }


    //Si state existe realiza una de la siguientes opciones
    when (uiState) {

        // Mientras carga los datos sale un icono de carga con movimiento
        is AppUiState.Cargando -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Si la carga tiene éxito carga el contenido del home, que esta más abajo
        is AppUiState.Exito -> {
            ContenidoHome(
                uiState = uiState,
                onVerModelosClick = onVerModelosClick
            )


        }

        // Si la carga da un error pinta un mensaje en la pantalla, indicando cual es
        is AppUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: ${uiState.mensaje}")
            }
        }
    }
}

//Aquí se compone el contenido que tendrá la página home,
// de momento un botón para navegar a la lista de modelos
@Composable
fun ContenidoHome(
    uiState: AppUiState.Exito,
    onVerModelosClick: () -> Unit
) {
    Button(onClick = onVerModelosClick) {
        Text("Ver modelos")
    }
}