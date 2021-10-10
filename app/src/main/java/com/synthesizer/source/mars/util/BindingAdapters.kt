package com.synthesizer.source.mars.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

private val shimmer by lazy {
    Shimmer.AlphaHighlightBuilder()
        .setDuration(1600)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()
}

@BindingAdapter("app:load")
fun ImageView.load(url: String?) {

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(shimmerDrawable)
        .into(this)
}

@BindingAdapter("app:visible")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}