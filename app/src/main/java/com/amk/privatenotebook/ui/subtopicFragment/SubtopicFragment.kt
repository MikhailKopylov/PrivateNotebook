package com.amk.privatenotebook.ui.subtopicFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.presentation.FromBodyViewModel
import com.amk.privatenotebook.presentation.SubtopicViewModel
import com.amk.privatenotebook.presentation.SubtopicViewState
import com.amk.privatenotebook.presentation.ToBodyViewModel
import kotlinx.android.synthetic.main.fragment_subtopic.*


class SubtopicFragment : Fragment(R.layout.fragment_subtopic) {

    private lateinit var subtopicList: List<Subtopic>
    private lateinit var note: Note

    private val subtopicViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(SubtopicViewModel::class.java) }
    }

    private val toBodyViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(ToBodyViewModel::class.java) }
    }

    private val fromBodyViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(FromBodyViewModel::class.java) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subtopic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SubtopicAdapter(this)
        subtopic_view.adapter = adapter

        subtopicViewModel?.subtopicList?.observe(viewLifecycleOwner) {
            when (it) {
                is SubtopicViewState.NotesList -> {
                    note = it.note
                    subtopicList = it.note.getSubTopicList()
                    adapter.submitList(subtopicList)
                }
                SubtopicViewState.EMPTY -> Unit
            }
        }

        fromBodyViewModel?.subtopicLiveData?.observe(viewLifecycleOwner) {
            adapter.submitList(subtopicList)
        }
    }

    fun selectBody(subtopic: Subtopic) {
        toBodyViewModel?.selectBody(subtopic)
    }
}