package com.amk.privatenotebook.presentation

import com.amk.privatenotebook.core.Subtopic

sealed class SubtopicViewState {
    data class NotesList(val subtopics: List<Subtopic>) : SubtopicViewState()
    object EMPTY : SubtopicViewState()

}