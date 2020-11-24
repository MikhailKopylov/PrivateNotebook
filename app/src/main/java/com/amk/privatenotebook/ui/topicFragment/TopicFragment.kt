package com.amk.privatenotebook.ui.topicFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.presentation.SubtopicViewModel
import com.amk.privatenotebook.presentation.TopicViewModel
import com.amk.privatenotebook.presentation.TopicViewState
import kotlinx.android.synthetic.main.fragment_topic.*


class TopicFragment : Fragment(R.layout.fragment_topic) {

    private val topicViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(TopicViewModel::class.java)
    }

    private val subTopicViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(SubtopicViewModel::class.java) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TopicAdapter(this)

        topic_view.adapter = adapter

        topicViewModel.observableTopicViewState().observe(viewLifecycleOwner) {
            when (it) {
                is
                TopicViewState.NotesList -> {
                    adapter.submitList(it.notes)
                }
                TopicViewState.EMPTY -> Unit
            }
        }
    }


    fun selectNone(note: Note) {

        subTopicViewModel?.selectNote(note)
    }
//    companion object {
//        @JvmStatic
//        fun newInstance() = TopicFragment()
//    }
}