package com.example.minleeAssign.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.minleeAssign.backend.WebServiceBuilder
import com.example.minleeAssign.backend.models.ImageModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ImagesRepo {

    val minleeAssignService = WebServiceBuilder.getMinleeAssignService() // TODO should be injected

    fun getImages(): LiveData<List<ImageModel>> {
        val data = MutableLiveData<List<ImageModel>>()
        minleeAssignService.getImages().enqueue(object : Callback<List<ImageModel>> {
            override fun onFailure(call: Call<List<ImageModel>>, t: Throwable) {
                Timber.d("getImages: onFailure reached")
                Timber.e(t)
                // TODO Not yet implemented
            }

            override fun onResponse(
                call: Call<List<ImageModel>>,
                response: Response<List<ImageModel>>
            ) {
                if (!response.isSuccessful) {
                    TODO("Not yet implemented")
                    return;
                }
                data.value = response.body()
            }

        })
        return data
    }

}