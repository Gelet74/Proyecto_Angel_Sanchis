package com.example.proyecto_angel_sanchis


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateDpAsState
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
fun AppNavigationBar() {

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
        bottomBar = {
            NavigationBar {

                val rutaActual = backStackEntry?.destination?.route

                /* ---------- HOME ---------- */
                val homeSelected = rutaActual == Rutas.Home.route
                val homeSize by animateDpAsState(
                    targetValue = if (homeSelected) 28.dp else 24.dp,
                    label = "homeSize"
                )

                NavigationBarItem(
                    selected = homeSelected,
                    onClick = {
                        navController.navigate(Rutas.Home.route) {
                            popUpTo(Rutas.Home.route)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(homeSize)
                        )
                    },
                    label = { Text("Home") }
                )

                /* ---------- LISTA (imagen propia) ---------- */
                val listaSelected = rutaActual == Rutas.ListaModelos.route
                val listaSize by animateDpAsState(
                    targetValue = if (listaSelected) 28.dp else 24.dp,
                    label = "listaSize"
                )

                NavigationBarItem(
                    selected = listaSelected,
                    onClick = {
                        navController.navigate(Rutas.ListaModelos.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.listadeverificacion),
                            contentDescription = "Lista",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(listaSize)
                        )
                    },
                    label = { Text("Lista") }
                )

                /* ---------- MODELO ---------- */
                val modeloSelected = rutaActual == Rutas.Modelo.route
                val modeloSize by animateDpAsState(
                    targetValue = if (modeloSelected) 28.dp else 24.dp,
                    label = "modeloSize"
                )

                NavigationBarItem(
                    selected = modeloSelected,
                    onClick = {
                        navController.navigate(Rutas.Modelo.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Modelo",
                            modifier = Modifier.size(modeloSize)
                        )
                    },
                    label = { Text("Modelo") }
                )
            }
        }

        ,
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
                    Modelo(nombre = nombre)
                )
                mostrarDialogoCrear = false
            },
            onCancelar = {
                mostrarDialogoCrear = false
            }
        )
    }
}
