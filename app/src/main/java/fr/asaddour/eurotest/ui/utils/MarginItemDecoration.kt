package fr.asaddour.eurotest.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceSize
            }
            val adapter = parent.adapter
            if (adapter != null && parent.getChildAdapterPosition(view) == adapter.itemCount - 1) {
                bottom = spaceSize
            }
        }

    }
}