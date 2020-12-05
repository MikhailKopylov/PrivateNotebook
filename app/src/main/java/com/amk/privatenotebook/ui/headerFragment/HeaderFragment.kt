package com.amk.privatenotebook.ui.headerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.note.NotesRepositoryRemote.notesRepository
import com.amk.privatenotebook.databinding.FragmentHeaderBinding
import com.amk.privatenotebook.presentation.HeaderViewModel
import com.amk.privatenotebook.presentation.HeaderViewState
import com.amk.privatenotebook.presentation.SubtopicViewModel
import com.amk.privatenotebook.ui.dialogFragmens.AddNewHeaderDialog
import com.amk.privatenotebook.ui.dialogFragmens.OnDialogListener
import com.amk.privatenotebook.utils.hideFabOnScroll


class HeaderFragment : Fragment(R.layout.fragment_header) {

    private lateinit var noteList: List<Note>

    private var _binding: FragmentHeaderBinding? = null
    private val binding: FragmentHeaderBinding get() = _binding!!

    private val headerViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(HeaderViewModel::class.java) }
    }

    private val subTopicViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(SubtopicViewModel::class.java) }
    }

    private val onDialogListener: OnDialogListener = object : OnDialogListener {
        override fun onDialogOK(headerName: String) {
            notesRepository.addNote(Note(headerName))
        }

        override fun onDialogCancel() {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TopicAdapter(this)

        with(binding){
            topicView.adapter = adapter
            headerViewModel?.observableTopicViewState()?.observe(viewLifecycleOwner) {
                when (it) {
                    is
                    HeaderViewState.NotesList -> {
                        noteList = it.notes
                        adapter.submitList(it.notes)
                    }
                    HeaderViewState.EMPTY -> Unit
                }
            }

            addFab.setOnClickListener {
                val addNewHeaderDialog = AddNewHeaderDialog(onDialogListener)
                activity?.supportFragmentManager?.let {
                    addNewHeaderDialog.show(
                        it,
                        "Dialog add new header"
                    )
                }
            }

            hideFabOnScroll(topicView, addFab)
        }
    }


    fun selectNone(note: Note) {
        subTopicViewModel?.selectNote(note)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}