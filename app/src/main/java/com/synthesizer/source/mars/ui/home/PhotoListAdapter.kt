package com.synthesizer.source.mars.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.mars.databinding.ItemPhotoListBinding
import com.synthesizer.source.mars.domain.model.PhotoListItem
import com.synthesizer.source.mars.util.load
import java.util.concurrent.atomic.AtomicBoolean

class PhotoListAdapter :
    PagingDataAdapter<PhotoListItem, PhotoListAdapter.ViewHolder>(DIFF) {

    var itemClickListener: (id: Int) -> Unit = {}
    var firstItemLoadedListener: () -> Unit = {}
    private val _isDataLoaded = AtomicBoolean(false)

    init {
        addLoadStateListener {
            if (it.refresh is LoadState.Loading) _isDataLoaded.set(false)
            if (itemCount > 0 && _isDataLoaded.compareAndSet(false, true)) {
                firstItemLoadedListener()
            }
        }
    }

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
        fun bind(item: PhotoListItem) {
            binding.photo.load(item.imgSrc)
            binding.root.setOnClickListener {
                itemClickListener(item.id)
            }
        }
    }

    object DIFF : DiffUtil.ItemCallback<PhotoListItem>() {
        override fun areItemsTheSame(
            oldItem: PhotoListItem,
            newItem: PhotoListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PhotoListItem,
            newItem: PhotoListItem
        ): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
}