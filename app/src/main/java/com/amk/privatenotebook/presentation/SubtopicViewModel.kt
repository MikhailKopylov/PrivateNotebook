package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.note.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class SubtopicViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    private val subtopicLiveData: MutableLiveData<SubtopicViewState> =
        MutableLiveData<SubtopicViewState>()

    private lateinit var noteId: String
    fun selectNote(noteId: String) {
        this.noteId = noteId
        val note = notesRepository.getNoteById(noteId)
        setSubtopicLiveData(note)

    }

    private fun setSubtopicLiveData(note: Note) {
        subtopicLiveData.value = if (note.getSubTopicList().isEmpty()) {
            SubtopicViewState.EMPTY(note)
        } else {
            SubtopicViewState.NotesList(note)
        }
    }

    fun observableSubtopicList(): LiveData<SubtopicViewState> = subtopicLiveData


    @ExperimentalCoroutinesApi
    fun deleteSubtopic(subtopic: Subtopic) {
        viewModelScope.launch {
            if (notesRepository.deleteSubtopic(subtopic.noteID, subtopic)) {
                setSubtopicLiveData(notesRepository.getNoteById(noteId))
            }
        }
    }

}