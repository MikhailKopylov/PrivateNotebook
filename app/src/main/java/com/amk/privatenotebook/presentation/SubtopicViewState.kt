package com.amk.privatenotebook.presentation

import com.amk.privatenotebook.core.Note

sealed class SubtopicViewState {
    data class NotesList(val note: Note) : SubtopicViewState()
    data class EMPTY(val note: Note) : SubtopicViewState()
}