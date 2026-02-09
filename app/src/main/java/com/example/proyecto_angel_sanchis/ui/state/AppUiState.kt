package com.example.proyecto_angel_sanchis.ui.state

import com.example.proyecto_angel_sanchis.modelo.Modelo

sealed class AppUiState {

    object Cargando : AppUiState()

    data class Exito(
        val lista: List<Modelo>
    ) : AppUiState()

    data class Error(
        val mensaje: String
    ) : AppUiState()
}