package com.example.minleeAssign.backend

import com.example.minleeAssign.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
 */
object WebServiceBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> build(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun getMinleeAssignService(): MinleeAssignService {
        return build(MinleeAssignService::class.java)
    }
}