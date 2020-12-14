package com.amk.privatenotebook.core.note

import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.database.interfaces.UserDAO
import kotlinx.coroutines.flow.Flow

interface NotesRepository : UserDAO {

    fun notes(): Flow<List<Note>>
    suspend fun updateNote(note: Note): Note
    suspend fun updateHeaderName(note: Note, header: String): Note
    suspend fun addNote(note: Note): Note
    fun getNoteById(id: String): Note
    suspend fun deleteNote(note: Note): Boolean
    suspend fun deleteSubtopic(noteID: String, subtopic: Subtopic): Boolean

}