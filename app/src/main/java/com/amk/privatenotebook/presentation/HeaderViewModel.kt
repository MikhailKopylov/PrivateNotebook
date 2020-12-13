package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.note.NotesRepository

class HeaderViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    //TODO add Result.failure handling

    fun observableTopicViewState(): LiveData<HeaderViewState> = notesRepository.notes()
        .map {
            if (it.isEmpty()) HeaderViewState.EMPTY else HeaderViewState.NotesList(it)
        }

    fun addNote(note: Note):LiveData<Result<Note>> =
        notesRepository.addNote(note)


    fun deleteNote(note: Note): LiveData<Boolean> = notesRepository.deleteNote(note)


}