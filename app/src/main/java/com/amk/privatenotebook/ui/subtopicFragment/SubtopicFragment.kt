package com.amk.privatenotebook.ui.subtopicFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amk.privatenotebook.R
import com.amk.privatenotebook.presentation.SubtopicViewModel
import com.amk.privatenotebook.presentation.SubtopicViewState
import kotlinx.android.synthetic.main.fragment_subtopic.*


class SubtopicFragment : Fragment(R.layout.fragment_subtopic) {


    private val subTopicViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(SubtopicViewModel::class.java) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subtopic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SubtopicAdapter()
        subtopic_view.adapter = adapter


        this.subTopicViewModel?.subtopicList?.observe(viewLifecycleOwner) {
            when (it) {
                is SubtopicViewState.NotesList -> {
                    adapter.submitList(it.subtopics)
                }
                SubtopicViewState.EMPTY -> Unit
            }
        }
    }
}