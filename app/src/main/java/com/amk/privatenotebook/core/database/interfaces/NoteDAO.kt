package com.amk.privatenotebook.core.database.interfaces

import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import kotlinx.coroutines.flow.Flow

interface NoteDAO {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(id:String):Note?
    suspend fun saveOrUpdateNote(note: Note): Note
    suspend fun deleteNote(id: String): Boolean
    fun deleteSubtopic(noteID: String, subtopic: Subtopic):Boolean
}