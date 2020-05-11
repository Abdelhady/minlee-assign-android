package com.example.minleeAssign.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.minleeAssign.Constants
import com.squareup.picasso.Picasso
import timber.log.Timber

object CustomBindingAdapters {

    @BindingAdapter("picasso")
    @JvmStatic
    fun showImage(imageView: ImageView, fileName: String?) {
        Timber.d("loading the image")
        Picasso.get()
            .load("${Constants.baseUrl}images/${fileName}")
            .into(imageView)
    }

}