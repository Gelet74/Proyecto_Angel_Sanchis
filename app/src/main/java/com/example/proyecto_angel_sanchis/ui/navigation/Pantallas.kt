package com.example.proyecto_angel_sanchis.ui.navigation

import androidx.annotation.StringRes
import com.example.proyecto_angel_sanchis.R

//Info de las pantallas, en este caso el título que se mostrará
enum class Pantallas(@StringRes val titulo: Int) {
    Home(titulo = R.string.pantalla_inicio),
    ListaModelo(titulo = R.string.pantalla_lista),
    Modelo(titulo = R.string.pantalla_modelo)
}