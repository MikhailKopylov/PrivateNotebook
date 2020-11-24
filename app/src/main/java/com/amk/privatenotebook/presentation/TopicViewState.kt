package com.amk.privatenotebook.presentation

import com.amk.privatenotebook.core.Note

sealed class TopicViewState {
    data class NotesList(val notes: List<Note>) : TopicViewState()
    object EMPTY : TopicViewState()
}