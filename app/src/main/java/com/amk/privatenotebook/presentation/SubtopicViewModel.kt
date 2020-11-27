package com.amk.privatenotebook.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.privatenotebook.core.Note

class SubtopicViewModel : ViewModel() {

    val subtopicList = MutableLiveData<SubtopicViewState>()
    fun selectNote(note: Note) {
        subtopicList.value =
            if (note.getSubTopicList()
                    .isEmpty()
            ) SubtopicViewState.EMPTY(note) else SubtopicViewState.NotesList(
                note
            )
    }
}