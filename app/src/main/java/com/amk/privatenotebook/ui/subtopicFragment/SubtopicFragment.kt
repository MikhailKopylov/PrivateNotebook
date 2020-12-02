package com.amk.privatenotebook.ui.subtopicFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.note.NotesRepositoryRemote
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.presentation.BodyViewModel
import com.amk.privatenotebook.presentation.SubtopicViewModel
import com.amk.privatenotebook.presentation.SubtopicViewState
import com.amk.privatenotebook.ui.bodyFragment.BodyFragment
import com.amk.privatenotebook.utils.hideFabOnScroll
import kotlinx.android.synthetic.main.fragment_subtopic.*


class SubtopicFragment : Fragment(R.layout.fragment_subtopic) {

    private lateinit var subtopicList: List<Subtopic>
    private lateinit var note: Note

    private val subtopicViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(SubtopicViewModel::class.java) }
    }

    private val toBodyViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(BodyViewModel::class.java) }
    }

//    private val fromBodyViewModel by lazy(LazyThreadSafetyMode.NONE) {
//        activity?.let { ViewModelProvider(it).get(FromBodyViewModel::class.java) }
//    }


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

        if (initNote()) {
            header_name_editView.setText(note.headerName)
        }
        initSubtopicObserver(adapter)

        initBodyObserver(adapter)

        add_fab.setOnClickListener {
            toBodyViewModel?.selectBody(Subtopic(noteID = note.uuidNote, "", ""))
            runBodyFragment()
        }

        hideFabOnScroll(subtopic_view, add_fab)
    }

    private fun initBodyObserver(adapter: SubtopicAdapter) {
        toBodyViewModel?.subtopicLiveData()?.observe(viewLifecycleOwner) {
            adapter.submitList(subtopicList)
        }
    }

    private fun initSubtopicObserver(adapter: SubtopicAdapter) {
        subtopicViewModel?.subtopicList()?.observe(viewLifecycleOwner) {
            when (it) {
                is SubtopicViewState.NotesList -> {
                    note = it.note
                    subtopicList = it.note.getSubTopicList()
                    adapter.submitList(subtopicList)
                }
                is SubtopicViewState.EMPTY -> {
                    note = it.note
                    subtopicList = it.note.getSubTopicList()
                }
            }
        }
    }

    fun selectBody(subtopic: Subtopic) {
        toBodyViewModel?.selectBody(subtopic)
    }

    fun runBodyFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, BodyFragment())
            ?.addToBackStack("body")
            ?.commit()
    }

    override fun onPause() {
        super.onPause()
        val newHeaderName = header_name_editView.text.toString()
        if (newHeaderName.isNotEmpty()) {
            if (initNote() && note.headerName != newHeaderName) {
                NotesRepositoryRemote.updateHeaderName(note, newHeaderName)
//                headerViewModel?.updateNoteList()
            }
        }
    }

    private fun initNote(): Boolean {
        note = when (subtopicViewModel?.subtopicList()?.value) {
            is SubtopicViewState.NotesList -> (subtopicViewModel?.subtopicList()?.value as SubtopicViewState.NotesList).note
            is SubtopicViewState.EMPTY -> (subtopicViewModel?.subtopicList()?.value as SubtopicViewState.EMPTY).note
            null -> return false
        }
        return true
    }
}