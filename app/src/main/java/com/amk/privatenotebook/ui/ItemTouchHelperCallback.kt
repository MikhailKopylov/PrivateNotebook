package com.amk.privatenotebook.ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperAdapter {
    fun onItemDismiss(position: Int)
}

class ItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlag = ItemTouchHelper.DOWN or ItemTouchHelper.UP
        val swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlag, swipeFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

}