package com.example.proyecto_angel_sanchis.data

import com.example.proyecto_angel_sanchis.connection.ServicioApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContenedorApp {

    //Cambiar solo la url de ser necesario, si es local no es necesario,
    // cambiar o añadir repositorio si se crean nuevos o se cambia el nombre
    // URL base de la API
    private const val BASE_URL = "http://10.0.2.2:3000/"

    // Retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Servicio API
    private val servicioApi: ServicioApi =
        retrofit.create(ServicioApi::class.java)

    // Repositorio (usa Modelo)
    val repositorio: Repositorio =
        Repositorio(servicioApi)
}