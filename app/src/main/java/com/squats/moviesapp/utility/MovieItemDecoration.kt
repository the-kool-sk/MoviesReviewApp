package com.squats.moviesapp.utility

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MovieItemDecoration(private val bottom: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0)
            outRect.bottom = bottom
    }
}
