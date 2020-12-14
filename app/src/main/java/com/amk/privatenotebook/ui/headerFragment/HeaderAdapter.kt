package com.amk.privatenotebook.ui.headerFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.databinding.ItemTopicBinding
import com.amk.privatenotebook.ui.ItemTouchHelperAdapter
import com.amk.privatenotebook.ui.subtopicFragment.SubtopicFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

val DIFF_UTIL: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.headerName == newItem.headerName
    }

}

class TopicAdapter(val fragment: HeaderFragment) :
    ListAdapter<Note, TopicAdapter.TopicViewHolder>(DIFF_UTIL),
    ItemTouchHelperAdapter {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @ExperimentalCoroutinesApi
    override fun onItemDismiss(position: Int) {
        fragment.onNoteDelete(getItem(position))

    }

    inner class TopicViewHolder(
        parent: ViewGroup,
        private val binding: ItemTopicBinding = ItemTopicBinding.inflate(
            LayoutInflater.from(parent.context)
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {
            with(item) {
                binding.topicTextView.text = headerName
                binding.root.setOnClickListener {
                    fragment.selectNote(item)
                    runFragment(item)
                }
            }
        }

        private fun runFragment(note: Note) {
            val activity = fragment.activity ?: return
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SubtopicFragment(note))
                .addToBackStack("subtitle")
                .commit()
        }
    }


}