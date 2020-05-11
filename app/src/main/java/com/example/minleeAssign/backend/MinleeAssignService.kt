package com.example.minleeAssign.backend

import com.example.minleeAssign.backend.models.ImageModel
import retrofit2.Call
import retrofit2.http.GET

interface MinleeAssignService {

    @GET("images")
    fun getImages(): Call<List<ImageModel>>

}