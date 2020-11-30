package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.NotesRepositoryRemote
import com.amk.privatenotebook.core.Subtopic

class BodyViewModel : ViewModel() {

    private val subtopicLiveData = MutableLiveData<Subtopic>()

    fun subtopicLiveData():LiveData<Subtopic> = subtopicLiveData
    fun selectBody(subtopic: Subtopic) {
        subtopicLiveData.value = subtopic
    }

    fun onUpdate(note: Note, subtopicName: String, body: String) {
        val noteID = note.uuidNote
        val uuidSubtopic = subtopicLiveData().value?.uuidSubTopic ?: return
        val subtopic = Subtopic(noteID, subtopicName, body, uuidSubtopic)
        subtopicLiveData.value = subtopic
        note.updateSubtopic(subtopic)
        NotesRepositoryRemote.updateNote(note)
    }

    fun getNoteById(): LiveData<Note> {
        val noteID = subtopicLiveData().value?.noteID ?: ""
        return NotesRepositoryRemote.getNoteById(noteID)
    }




}