package com.amk.privatenotebook.presentation

import com.amk.privatenotebook.core.Note

sealed class SubtopicViewState(open val note: Note) {
    data class NotesList(override val note:Note) : SubtopicViewState(note)
    data class EMPTY(override val note:Note) : SubtopicViewState(note)
}