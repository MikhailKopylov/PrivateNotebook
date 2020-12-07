package com.amk.privatenotebook.core.database.interfaces

import androidx.lifecycle.LiveData
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic

interface NoteDAO {
    fun getAllNotes(): LiveData<List<Note>>
    fun saveOrUpdateNote(note: Note): LiveData<Result<Note>>
    fun deleteNote(id: String): LiveData<Boolean>
    fun deleteSubtopic(noteID: String, subtopic: Subtopic): LiveData<Boolean>
}