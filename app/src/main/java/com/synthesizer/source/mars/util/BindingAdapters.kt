package com.synthesizer.source.mars.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:load")
fun ImageView.loadImage(url: String?) {
    load(url)
}