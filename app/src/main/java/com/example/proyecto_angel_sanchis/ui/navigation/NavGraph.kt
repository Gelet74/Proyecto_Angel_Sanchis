package com.example.proyecto_angel_sanchis.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.proyecto_angel_sanchis.modelo.Modelo
import com.example.proyecto_angel_sanchis.ui.pantallas.PantallaHome
import com.example.proyecto_angel_sanchis.ui.pantallas.PantallaListaModelos
import com.example.proyecto_angel_sanchis.ui.pantallas.PantallaModelo
import com.example.proyecto_angel_sanchis.ui.state.AppUiState
import com.example.proyecto_angel_sanchis.ui.viewmodel.AppViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    //Creación del state para pasarlo entre pantallas y usen todas el mismo estado
    val uiState = viewModel.uiState.collectAsState().value

    //Navegación, aquí se indica donde empieza la navegación de la aplicación
    // y donde envía cada botón de navegación
    NavHost(
        navController = navController,
        startDestination = Rutas.Home.route,
        modifier = modifier
    ) {

        //Ejemplo de navegación a pantalla home, donde se cargan los datos,
        // con una navegación a la lista de modelos
        composable(Rutas.Home.route) {
            PantallaHome(
                uiState = uiState,
                onCargarDatos = {
                    viewModel.cargarDatos()
                },
                onVerModelosClick = {
                    navController.navigate(Rutas.ListaModelos.route)
                }
            )
        }

        //Ejemplo de pantalla de lista de modelos, con modelos clickables,
        // que te envían a otra pantalla con los datos del modelo clicado
        composable(Rutas.ListaModelos.route) {
            PantallaListaModelos(
                uiState = uiState,
                onModeloClick = { id: String ->
                    navController.navigate(Rutas.Modelo.crearRuta(id))
                }
            )
        }

        //Ejemplo de pantalla para mostrar los datos del modelo,
        // donde he creado un par de botones, uno para editar el modelo
        // y otro para borrarlo
        composable(
            route = Rutas.Modelo.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType  })
        ) { backStackEntry ->

            val idModelo = backStackEntry.arguments?.getString("id") ?: return@composable

            PantallaModelo(
                idModelo = idModelo,
                uiState = uiState,
                onEditar = { id, nuevoNombre ->
                    viewModel.actualizar(id, Modelo(id, nuevoNombre))
                },
                onBorrar = { id ->
                    viewModel.borrar(id)
                    navController.popBackStack()
                }
            )
        }
    }
}