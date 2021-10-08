package com.synthesizer.source.mars.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:load")
fun ImageView.loadImage(url: String?) {
    load(url)
}

@BindingAdapter("app:visible")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}