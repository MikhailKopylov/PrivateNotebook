package com.amk.privatenotebook.ui.headerFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.NotesRepositorySimple
import com.amk.privatenotebook.presentation.SubtopicViewModel
import com.amk.privatenotebook.presentation.TopicViewModel
import com.amk.privatenotebook.presentation.TopicViewState
import com.amk.privatenotebook.ui.dialogFragmens.AddNewHeaderDialog
import com.amk.privatenotebook.ui.dialogFragmens.OnDialogListener
import kotlinx.android.synthetic.main.fragment_topic.*


class HeaderFragment : Fragment(R.layout.fragment_topic) {

    private lateinit var noteList:List<Note>
    private val topicViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(TopicViewModel::class.java)
    }

    private val subTopicViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(SubtopicViewModel::class.java) }
    }

    private val onDialogListener:OnDialogListener = object : OnDialogListener{
        override fun onDialogOK(headerName: String) {
            NotesRepositorySimple.addNote(Note(headerName))
            topicViewModel.updateNoteList()
        }

        override fun onDialogCancel() {
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TopicAdapter(this)

        topic_view.adapter = adapter

        topicViewModel.observableTopicViewState().observe(viewLifecycleOwner) {
            when (it) {
                is
                TopicViewState.NotesList -> {
                    noteList = it.notes
                    adapter.submitList(it.notes)
                }
                TopicViewState.EMPTY -> Unit
            }
        }

        add_fab.setOnClickListener {
            val addNewHeaderDialog = AddNewHeaderDialog(onDialogListener)
            activity?.supportFragmentManager?.let { addNewHeaderDialog.show(it, "Dialog add new header") }
        }
    }

    fun selectNone(note: Note) {
        subTopicViewModel?.selectNote(note)
    }


}