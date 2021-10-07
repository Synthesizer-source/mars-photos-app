package com.synthesizer.source.mars.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.mars.data.remote.PhotoResponse
import com.synthesizer.source.mars.databinding.ItemPhotoListBinding
import com.synthesizer.source.mars.ui.home.PhotoListAdapter.ViewHolder
import com.synthesizer.source.mars.util.load

class PhotoListAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val photos = mutableListOf<PhotoResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    fun addData(newPhotos: List<PhotoResponse>) {
        photos.addAll(newPhotos)
        notifyItemRangeChanged(photos.size - newPhotos.size, newPhotos.size)
    }

    override fun getItemCount() = photos.size

    class ViewHolder(private val binding: ItemPhotoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoResponse) {
            binding.photo.load(item.imgSrc)
        }
    }
}