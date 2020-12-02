package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.amk.privatenotebook.core.note.NotesRepositoryRemote.notesRepository

class HeaderViewModel : ViewModel() {

    //TODO add Result.failure handling

    fun observableTopicViewState(): LiveData<HeaderViewState> = notesRepository.notes()
        .map {
            if (it.isEmpty()) HeaderViewState.EMPTY else HeaderViewState.NotesList(it)
        }
}