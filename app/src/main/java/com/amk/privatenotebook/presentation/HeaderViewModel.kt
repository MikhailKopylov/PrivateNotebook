package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.note.NotesRepositoryRemote
import com.amk.privatenotebook.core.note.NotesRepositoryRemote.addNote
import com.amk.privatenotebook.core.note.NotesRepositoryRemote.notesRepository

class HeaderViewModel : ViewModel() {

    //TODO add Result.failure handling

    fun observableTopicViewState(): LiveData<HeaderViewState> = notesRepository.notes()
        .map {
            if (it.isEmpty()) HeaderViewState.EMPTY else HeaderViewState.NotesList(it)
        }

    fun addNote(note: Note){
        NotesRepositoryRemote.addNote(note)
    }
}