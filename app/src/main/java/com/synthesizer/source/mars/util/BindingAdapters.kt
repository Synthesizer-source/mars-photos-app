package com.synthesizer.source.mars.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:load")
fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(context).load(it).into(this)
    }
}