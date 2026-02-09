package com.example.proyecto_angel_sanchis.connection

import com.example.proyecto_angel_sanchis.modelo.Modelo
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServicioApi {

    //el value es la dirección de la url donde están los datos que quieres usar

    //Recoger todos los modelos y hacer una lista
    @GET("modelos")
    suspend fun obtenerTodos(): List<Modelo>

    //Insertar un modelo nuevo en la DB
    @POST("modelos")
    suspend fun insertar(@Body modelo: Modelo): Modelo

    //Actualizar un modelo concreto en la DB
    @PUT("modelos/{id}")
    suspend fun actualizar(
        @Path("id") id: String,
        @Body modelo: Modelo
    )

    //Borrar un modelo concreto de la DB
    @DELETE("modelos/{id}")
    suspend fun borrar(@Path("id") id: String)
}