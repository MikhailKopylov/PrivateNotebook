package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.note.NotesRepository
import kotlinx.coroutines.launch

class BodyViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    private val subtopicLiveData = MutableLiveData<Subtopic>()

    fun subtopicLiveData(): LiveData<Subtopic> = subtopicLiveData
    fun selectBody(subtopic: Subtopic) {
        subtopicLiveData.value = subtopic
    }

    fun onUpdate(subtopicName: String, body: String) {
        viewModelScope.launch {
            val noteID = subtopicLiveData.value?.noteID ?: return@launch
            val note = notesRepository.getNoteById(noteID)
            val uuidSubtopic: String = subtopicLiveData().value?.uuidSubTopic ?: return@launch
            val subtopic = Subtopic(noteID, subtopicName, body, uuidSubtopic)
            subtopicLiveData.value = subtopic
            note.updateSubtopic(subtopic)
            notesRepository.updateNote(note)
        }
    }
}