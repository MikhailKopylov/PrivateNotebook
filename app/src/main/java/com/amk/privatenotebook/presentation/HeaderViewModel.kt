package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.privatenotebook.core.NotesRepositorySimple

class HeaderViewModel : ViewModel() {

    private val topicViewStateLiveData = MutableLiveData<HeaderViewState>(HeaderViewState.EMPTY)

    init {
        updateNoteList()
    }

    fun updateNoteList() {
        val noteList = NotesRepositorySimple.notes()
        topicViewStateLiveData.value = HeaderViewState.NotesList(noteList)
    }

    fun observableTopicViewState(): LiveData<HeaderViewState> = topicViewStateLiveData
}