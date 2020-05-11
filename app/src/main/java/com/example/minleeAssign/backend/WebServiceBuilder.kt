package com.example.minleeAssign.backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
 */
object WebServiceBuilder {

    private val retrofit = Retrofit.Builder()
//        .baseUrl("http://10.0.2.2:3000/") // Use this when trying from an emulator
        .baseUrl("http://192.168.1.4:3000/")// TODO should be extracted in an environment-dependent file
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> build(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun getMinleeAssignService(): MinleeAssignService {
        return build(MinleeAssignService::class.java)
    }
}