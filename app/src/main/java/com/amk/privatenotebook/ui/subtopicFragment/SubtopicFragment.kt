package com.amk.privatenotebook.ui.subtopicFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.databinding.FragmentSubtopicBinding
import com.amk.privatenotebook.presentation.BodyViewModel
import com.amk.privatenotebook.presentation.HeaderViewModel
import com.amk.privatenotebook.presentation.SubtopicViewModel
import com.amk.privatenotebook.presentation.SubtopicViewState
import com.amk.privatenotebook.ui.bodyFragment.BodyFragment
import com.amk.privatenotebook.utils.hideFabOnScroll
import org.koin.androidx.viewmodel.ext.android.viewModel


class SubtopicFragment(private var note: Note) : Fragment(R.layout.fragment_subtopic) {


    private var _binding: FragmentSubtopicBinding? = null
    private val binding: FragmentSubtopicBinding get() = _binding!!

    private val headerViewModel by viewModel<HeaderViewModel>()
    private val subtopicViewModel by viewModel<SubtopicViewModel>()
//    private val bodyViewModel by activity?.viewModel<BodyViewModel>() ?: viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSubtopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val adapter = SubtopicAdapter(this@SubtopicFragment)
            binding.subtopicView.adapter = adapter

//            if (initNote()) {
            headerNameEditView.setText(note.headerName)
//            }
            initSubtopicObserver(adapter)

            initBodyObserver(adapter)

            addFab.setOnClickListener {
                val bodyViewModel by (activity)?.viewModel<BodyViewModel>() ?: viewModel()
                bodyViewModel.selectBody(
                    Subtopic(
                        noteID = note.uuidNote,
                        subtopicName = "",
                        body = ""
                    )
                )
                runBodyFragment()
            }

            hideFabOnScroll(subtopicView, addFab)
        }
    }

    private fun initBodyObserver(adapter: SubtopicAdapter) {
        val bodyViewModel by (activity)?.viewModel<BodyViewModel>() ?: viewModel()
        bodyViewModel.subtopicLiveData().observe(viewLifecycleOwner) {
            adapter.submitList(note.getSubTopicList())
        }
    }

    private fun initSubtopicObserver(adapter: SubtopicAdapter) {
        subtopicViewModel.subtopicList().observe(viewLifecycleOwner) {
            when (it) {
                is SubtopicViewState.NotesList -> {
                    note = it.note
                    adapter.submitList(note.getSubTopicList())
                }
                is SubtopicViewState.EMPTY -> {
                    note = it.note
                }
            }
        }
    }

    fun selectBody(subtopic: Subtopic) {
        val bodyViewModel by (activity)?.viewModel<BodyViewModel>() ?: viewModel()
        bodyViewModel.selectBody(subtopic)
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
        val newHeaderName = binding.headerNameEditView.text.toString()
        if (newHeaderName.isNotEmpty()) {
            if (/*initNote() &&*/ note.headerName != newHeaderName) {
                val newNote = note.copy(headerName = newHeaderName)
                headerViewModel.addNote(newNote)
            }
        }
    }
}