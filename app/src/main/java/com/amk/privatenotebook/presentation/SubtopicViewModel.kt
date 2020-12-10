package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.note.NotesRepository

class SubtopicViewModel(private val notesRepository: NotesRepository) : ViewModel() {


    private lateinit var noteId: String
    fun selectNote(noteId: String) {
        this.noteId = noteId
    }

    fun observableSubtopicList(): LiveData<SubtopicViewState> =
        notesRepository.getNoteById(noteId).map {
            if (it.getSubTopicList()
                    .isEmpty()
            ) SubtopicViewState.EMPTY(it) else SubtopicViewState.NotesList(it)
        }


    fun deleteSubtopic(subtopic: Subtopic): LiveData<Boolean> =
        notesRepository.deleteSubtopic(subtopic.noteID, subtopic)

}