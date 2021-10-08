package com.synthesizer.source.mars.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.mars.data.remote.PhotoResponse
import com.synthesizer.source.mars.databinding.ItemPhotoListBinding
import com.synthesizer.source.mars.util.load

class PhotoListAdapter :
    PagingDataAdapter<PhotoResponse, PhotoListAdapter.ViewHolder>(DIFF) {

    var itemClickListener: (id: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoListBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemPhotoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoResponse) {
            binding.photo.load(item.imgSrc)
            binding.root.setOnClickListener {
                itemClickListener(item.id)
            }
        }
    }

    object DIFF : DiffUtil.ItemCallback<PhotoResponse>() {
        override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
}