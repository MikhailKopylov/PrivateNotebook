package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.privatenotebook.core.NotesRepositorySimple

class TopicViewModel : ViewModel() {

    private val topicViewStateLiveData = MutableLiveData<TopicViewState>(TopicViewState.EMPTY)

    init {
        val noteList = NotesRepositorySimple.notes()
        topicViewStateLiveData.value = TopicViewState.NotesList(noteList)
    }

    fun observableTopicViewState(): LiveData<TopicViewState> = topicViewStateLiveData
}