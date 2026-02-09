package com.example.proyecto_angel_sanchis


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_angel_sanchis.data.ContenedorApp
import com.example.proyecto_angel_sanchis.modelo.Modelo
import com.example.proyecto_angel_sanchis.ui.navigation.NavGraph
import com.example.proyecto_angel_sanchis.ui.navigation.Pantallas
import com.example.proyecto_angel_sanchis.ui.navigation.Rutas
import com.example.proyecto_angel_sanchis.ui.viewmodel.AppViewModel
import com.example.proyecto_angel_sanchis.ui.viewmodel.AppViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

    val navController = rememberNavController()

    val viewModel: AppViewModel = viewModel(
        factory = AppViewModelFactory(ContenedorApp.repositorio)
    )

    val backStackEntry by navController.currentBackStackEntryAsState()

    val pantallaActual = when (backStackEntry?.destination?.route) {
        Rutas.Home.route -> Pantallas.Home
        Rutas.Modelo.route -> Pantallas.Modelo
        Rutas.ListaModelos.route -> Pantallas.ListaModelo
        else -> Pantallas.Home
    }

    var mostrarDialogoCrear by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            if (pantallaActual == Pantallas.Home) {
                FloatingActionButton(
                    onClick = { mostrarDialogoCrear = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(
                            id = R.string.ir_modelo
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

        NavGraph(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }

    if (mostrarDialogoCrear) {
        DialogoCrearModelo(
            onCrear = { nombre ->
                viewModel.insertar(
                    Modelo(
                        nombre = nombre
                    )
                )
                mostrarDialogoCrear = false
            },
            onCancelar = {
                mostrarDialogoCrear = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = pantallaActual.titulo))
        },
        navigationIcon = {
            if (puedeNavegarAtras) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = com.example.proyecto_angel_sanchis.R.string.atras
                        )
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun DialogoCrearModelo(
    onCrear: (String) -> Unit,
    onCancelar: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = {
            Text("Crear modelo")
        },
        text = {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onCrear(nombre) },
                enabled = nombre.isNotBlank()
            ) {
                Text("Crear")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}