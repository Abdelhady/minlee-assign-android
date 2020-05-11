package com.example.minleeAssign.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minleeAssign.backend.models.ImageModel
import com.example.minleeAssign.databinding.ImageItemBinding

/**
 * https://www.untitledkingdom.com/blog/refactoring-recyclerview-adapter-to-data-binding-5631f239095f-0
 * also: https://androidwave.com/android-data-binding-recyclerview/
 */
class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    var images: MutableList<ImageModel> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    inner class ViewHolder(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageModel) {
            binding.imageModel = item
            binding.executePendingBindings()
        }
    }

}