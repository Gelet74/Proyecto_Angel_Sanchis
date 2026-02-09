package com.example.proyecto_angel_sanchis.ui.navigation

sealed class Rutas (val route: String){
    //Ruta a home
    object Home: Rutas("home")

    //Ruto al modelo concreto
    object Modelo : Rutas("modelo/{id}") {
        fun crearRuta(id: String) = "modelo/$id"
    }
    //Ruta a la lista de modelos
    object ListaModelos : Rutas("listamodelos")
}
