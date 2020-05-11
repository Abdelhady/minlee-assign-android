package com.example.minleeAssign.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.minleeAssign.backend.WebServiceBuilder
import com.example.minleeAssign.backend.models.ImageModel
import com.example.minleeAssign.utils.FileUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File

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

    fun createImage(imageUri: Uri): MutableLiveData<ImageModel> {
        val data = MutableLiveData<ImageModel>()
//        val file = File(FileUtil.getRealPathFromURI(imageUri))
        val file = File(FileUtil.createPrivateCopyFromStreamedUri(imageUri))
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        // TODO investigate why file is not sent successfully here !!
        minleeAssignService.createImage(requestFile).enqueue(object : Callback<ImageModel>{
            override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                // TODO Not yet implemented
                Timber.e(t)
            }

            override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                if (!response.isSuccessful) {
                    Timber.d("response is not successfull, with message: ${response.message()}")
                    return
                }
                data.value = response.body()
            }

        })
        return data
    }

}