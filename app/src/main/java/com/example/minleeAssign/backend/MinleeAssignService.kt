package com.example.minleeAssign.backend

import com.example.minleeAssign.backend.models.ImageModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MinleeAssignService {

    @GET("images")
    fun getImages(): Call<List<ImageModel>>

    @Multipart
    @POST("images")
    fun createImage(@Part image: MultipartBody.Part): Call<ImageModel>

}