package com.synthesizer.source.mars.ui.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.mars.R

class PhotoDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val space = parent.context.resources.getDimensionPixelSize(R.dimen.item_photo_space)
        val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
        if (parent.getChildAdapterPosition(view) < 2) outRect.top = space
        else outRect.top = space / 2
        outRect.bottom = space / 2
        if (spanIndex == 1) {
            outRect.left = space / 2
            outRect.right = space
        } else {
            outRect.right = space / 2
            outRect.left = space
        }
    }
}