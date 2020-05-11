package com.example.minleeAssign.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minleeAssign.backend.models.ImageModel
import com.example.minleeAssign.repositories.ImagesRepo
import retrofit2.Call
import timber.log.Timber

class MainViewModel : ViewModel() {

    val imagesRepo = ImagesRepo() // TODO should be injected

    fun getImages(): LiveData<List<ImageModel>> {
        Timber.d("getImages: from viewmodel")
        return imagesRepo.getImages()
    }

    fun createImage(imageUri: Uri): MutableLiveData<ImageModel> {
        return imagesRepo.createImage(imageUri)
    }

}