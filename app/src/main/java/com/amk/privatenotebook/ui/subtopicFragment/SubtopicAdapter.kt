package com.amk.privatenotebook.ui.subtopicFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Subtopic
import kotlinx.android.synthetic.main.item_subtopic.view.*

val DIFF_UTIL: DiffUtil.ItemCallback<Subtopic> = object : DiffUtil.ItemCallback<Subtopic>() {
    override fun areItemsTheSame(oldItem: Subtopic, newItem: Subtopic): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Subtopic, newItem: Subtopic): Boolean {
        return oldItem.subtopicName == newItem.subtopicName
    }

}

class SubtopicAdapter(val fragment: SubtopicFragment) :
    ListAdapter<Subtopic, SubtopicAdapter.SubtopicViewHolder>(DIFF_UTIL) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtopicViewHolder {
        return SubtopicViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SubtopicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SubtopicViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_subtopic, parent, false)
    ) {

        fun bind(item: Subtopic) {
            with(item) {
                itemView.subtopic_edit_text.text = subtopicName
                itemView.setOnClickListener {
                    fragment.selectBody(item)
                    fragment.runBodyFragment()
                }
            }
        }


    }

}