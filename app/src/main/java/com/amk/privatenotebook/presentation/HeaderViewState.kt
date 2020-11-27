package com.amk.privatenotebook.presentation

import com.amk.privatenotebook.core.Note

sealed class HeaderViewState {
    data class NotesList(val notes: List<Note>) : HeaderViewState()
    object EMPTY : HeaderViewState()
}