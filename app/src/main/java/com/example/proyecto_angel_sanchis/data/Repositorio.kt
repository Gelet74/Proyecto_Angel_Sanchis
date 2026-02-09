package com.example.proyecto_angel_sanchis.data

import com.example.proyecto_angel_sanchis.connection.ServicioApi
import com.example.proyecto_angel_sanchis.modelo.Modelo

// Añadir api a los parametros de ser necesario, no debería
class Repositorio(
    private val api: ServicioApi
) {

    //Funcion para llamar a la api y obtener la lista de modelos
    suspend fun obtenerTodos(): List<Modelo> =
        api.obtenerTodos()

    //Funcion para llamar a la api e insertar un modelo nuevo
    suspend fun insertar(modelo: Modelo) =
        api.insertar(modelo)

    //Funcion para llamar a la api y actualizar un modelo concreto
    suspend fun actualizar(id: String, modelo: Modelo) =
        api.actualizar(id, modelo)

    //Funcion para llamar a la api y borrar un modelo concreto
    suspend fun borrar(id: String) {
        api.borrar(id)
    }
}