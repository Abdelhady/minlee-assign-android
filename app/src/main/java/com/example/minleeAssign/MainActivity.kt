package com.example.minleeAssign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.minleeAssign.databinding.ActivityMainBinding
import com.example.minleeAssign.viewmodels.MainViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getImages().observe(this, Observer {
            viewModel.fileName.value = it[0].filename
        })
    }
}
