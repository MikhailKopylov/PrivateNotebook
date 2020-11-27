package com.amk.privatenotebook.utils

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_topic.*

fun hideFabOnScroll(recyclerView: RecyclerView, fab:FloatingActionButton) {
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0 || dy < 0 && fab.isShown) {
                fab.hide()
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                fab.show()
            }
            super.onScrollStateChanged(recyclerView, newState)
        }
    })
}