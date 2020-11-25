package com.amk.privatenotebook.ui.topicFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.ui.subtopicFragment.SubtopicFragment
import kotlinx.android.synthetic.main.item_topic.view.*

val DIFF_UTIL: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.topicName == newItem.topicName
    }

}

class TopicAdapter(val fragment: TopicFragment) :
    ListAdapter<Note, TopicAdapter.TopicViewHolder>(DIFF_UTIL) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class TopicViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
    ) {

        fun bind(item: Note) {
            with(item) {
                itemView.topic_textView.text = topicName
                itemView.setOnClickListener {
                    fragment.selectNone(item)
                    runFragment()
                }
            }
        }

        private fun runFragment() {
            val activity = fragment.activity ?: return
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SubtopicFragment())
                .addToBackStack("subtitle")
                .commit()
        }
    }

}