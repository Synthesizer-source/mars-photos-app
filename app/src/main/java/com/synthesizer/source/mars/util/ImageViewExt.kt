package com.synthesizer.source.mars.util

import android.widget.ImageView
import com.bumptech.glide.Glide
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

fun ImageView.load(url: String?) {
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    Glide.with(context)
        .load(url)
        .placeholder(shimmerDrawable)
        .into(this)
}