package com.synthesizer.source.mars.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.mars.databinding.ItemPhotoListBinding
import com.synthesizer.source.mars.ui.home.PhotoListAdapter.ViewHolder
import com.synthesizer.source.mars.util.load
import kotlin.random.Random

class PhotoListAdapter : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 20

    class ViewHolder(private val binding: ItemPhotoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val random = Random.nextInt(4)
            val url = when (random % 4) {
                0 -> "https://mars.nasa.gov/msl-raw-images/msss/01000/mcam/1000MR0044631250503685E01_DXXX.jpg"
                1 -> "https://mars.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044630190405116E02_DXXX.jpg"
                2 -> "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG"
                else -> "http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631230305220E02_DXXX.jpg"
            }
            binding.photo.load(url)
        }
    }
}