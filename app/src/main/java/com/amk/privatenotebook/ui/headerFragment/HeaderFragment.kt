package com.amk.privatenotebook.ui.headerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ItemTouchHelper
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.databinding.FragmentHeaderBinding
import com.amk.privatenotebook.presentation.BodyViewModel
import com.amk.privatenotebook.presentation.HeaderViewModel
import com.amk.privatenotebook.presentation.HeaderViewState
import com.amk.privatenotebook.presentation.SubtopicViewModel
import com.amk.privatenotebook.ui.ItemTouchHelperCallback
import com.amk.privatenotebook.ui.dialogFragmens.AddNewHeaderDialog
import com.amk.privatenotebook.ui.dialogFragmens.OnDialogListener
import com.amk.privatenotebook.utils.hideFabOnScroll
import org.koin.androidx.viewmodel.ext.android.viewModel


class HeaderFragment : Fragment(R.layout.fragment_header) {

    private lateinit var noteList: List<Note>

    private var _binding: FragmentHeaderBinding? = null
    private val binding: FragmentHeaderBinding get() = _binding!!

    private val onDialogListener: OnDialogListener = object : OnDialogListener {
        override fun onDialogOK(headerName: String) {
            val headerViewModel by (activity)?.viewModel<HeaderViewModel>() ?: viewModel()
            headerViewModel.addNote(Note(headerName))
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

        with(binding) {
            topicView.adapter = adapter
            ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(topicView)
            val headerViewModel by (activity)?.viewModel<HeaderViewModel>() ?: viewModel()
            headerViewModel.observableTopicViewState().observe(viewLifecycleOwner) {
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


    fun selectNote(note: Note) {
        val subTopicViewModel by activity?.viewModel<SubtopicViewModel>() ?: viewModel()
        subTopicViewModel.selectNote(note.uuidNote)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onNoteDelete(note: Note): LiveData<Boolean> {
        val headerViewModel by (activity)?.viewModel<HeaderViewModel>() ?: viewModel()
        return headerViewModel.deleteNote(note)
    }

}