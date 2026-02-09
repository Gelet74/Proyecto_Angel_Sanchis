package com.example.proyecto_angel_sanchis.ui.pantallas


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyecto_angel_sanchis.modelo.Modelo
import com.example.proyecto_angel_sanchis.ui.state.AppUiState

//Ejemplo de una pantalla para listar modelos
@Composable
fun PantallaListaModelos(
    uiState: AppUiState,
    onModeloClick: (String) -> Unit
) {
    when (uiState) {

        is AppUiState.Cargando -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is AppUiState.Exito -> {
            ListaModelos(
                lista = uiState.lista,
                onModeloClick = onModeloClick
            )
        }

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

@Composable
fun ListaModelos(
    lista: List<Modelo>,
    onModeloClick: (String) -> Unit   // ✅ String
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(lista) { modelo ->
            ModeloCard(
                modelo = modelo,
                onClick = {
                    modelo.id?.let { id ->
                        onModeloClick(id)   // ✅ String
                    }
                }
            )
        }
    }
}


@Composable
fun ModeloCard(
    modelo: Modelo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = modelo.nombre,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
