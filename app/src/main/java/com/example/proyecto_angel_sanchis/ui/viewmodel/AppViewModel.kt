package com.example.proyecto_angel_sanchis.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_angel_sanchis.data.Repositorio
import com.example.proyecto_angel_sanchis.modelo.Modelo
import com.example.proyecto_angel_sanchis.ui.state.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val repositorio: Repositorio
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<AppUiState>(AppUiState.Cargando)
    val uiState: StateFlow<AppUiState> = _uiState

    fun cargarDatos() {
        viewModelScope.launch {
            _uiState.value = AppUiState.Cargando

            runCatching {
                repositorio.obtenerTodos()
            }.onSuccess { lista ->
                _uiState.value = AppUiState.Exito(lista)
            }.onFailure { e ->
                _uiState.value = AppUiState.Error(
                    e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun insertar(modelo: Modelo) {
        viewModelScope.launch {
            repositorio.insertar(modelo)
            cargarDatos()
        }
    }

    fun actualizar(id: String, modelo: Modelo) {
        viewModelScope.launch {
            repositorio.actualizar(id, modelo)
            cargarDatos()
        }
    }

    fun borrar(id: String) {
        viewModelScope.launch {
            runCatching {
                repositorio.borrar(id)
            }.onSuccess {
                cargarDatos() // refresca la lista
            }.onFailure { e ->
                _uiState.value = AppUiState.Error(
                    e.message ?: "Error al borrar"
                )
            }
        }
    }
}