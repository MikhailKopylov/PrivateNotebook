package com.amk.privatenotebook.core.note

import androidx.lifecycle.LiveData
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.database.interfaces.UserDAO

interface NotesRepository : UserDAO {

    fun notes(): LiveData<List<Note>>
    fun updateNote(note: Note): LiveData<Result<Note>>
    fun updateHeaderName(note: Note, header: String): LiveData<Result<Note>>
    fun addNote(note: Note): LiveData<Result<Note>>
    fun getNoteById(id: String): LiveData<Note>
    fun deleteNote(note: Note)


}