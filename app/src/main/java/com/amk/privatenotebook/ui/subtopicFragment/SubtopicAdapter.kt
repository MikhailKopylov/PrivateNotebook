package com.amk.privatenotebook.ui.subtopicFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.databinding.ItemSubtopicBinding
import com.amk.privatenotebook.ui.ItemTouchHelperAdapter

val DIFF_UTIL: DiffUtil.ItemCallback<Subtopic> = object : DiffUtil.ItemCallback<Subtopic>() {
    override fun areItemsTheSame(oldItem: Subtopic, newItem: Subtopic): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Subtopic, newItem: Subtopic): Boolean {
        return oldItem.subtopicName == newItem.subtopicName
    }
}

class SubtopicAdapter(val fragment: SubtopicFragment) :
    ListAdapter<Subtopic, SubtopicAdapter.SubtopicViewHolder>(DIFF_UTIL),
    ItemTouchHelperAdapter {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtopicViewHolder {
        return SubtopicViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SubtopicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onItemDismiss(position: Int) {
        fragment.subtopicDelete(getItem(position)).observe(fragment){
            if(!it){
                Toast.makeText(fragment.context, "Delete subtopic failed!!!", Toast.LENGTH_SHORT)
                    .show()
            } else{
                notifyItemRemoved(position)
            }
        }
    }

    inner class SubtopicViewHolder(
        parent: ViewGroup,
        private val binding: ItemSubtopicBinding = ItemSubtopicBinding.inflate(
            LayoutInflater.from(parent.context)
        )
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(item: Subtopic) {
            with(item) {
                binding.subtopicEditText.text = subtopicName
                binding.root.setOnClickListener {
                    fragment.selectBody(item)
                    fragment.runBodyFragment()
                }
            }
        }


    }


}