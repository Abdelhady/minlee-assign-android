package com.example.minleeAssign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minleeAssign.databinding.ActivityMainBinding
import com.example.minleeAssign.ui.ImagesAdapter
import com.example.minleeAssign.viewmodels.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val imagesAdapter = ImagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initImagesAdapter()
    }

    private fun initImagesAdapter() {
        imagesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        imagesRecyclerView.setHasFixedSize(true)
        imagesRecyclerView.adapter = imagesAdapter
        viewModel.getImages().observe(this, Observer {
            imagesAdapter.images = it.toMutableList()
        })
    }
}
