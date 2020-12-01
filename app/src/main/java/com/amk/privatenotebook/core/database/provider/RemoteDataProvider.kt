package com.amk.privatenotebook.core.database.provider

import androidx.lifecycle.LiveData
import com.amk.privatenotebook.core.Note

interface RemoteDataProvider {
    fun getAllNotes(): LiveData<List<Note>>
    fun getNoteById(id: String): LiveData<Note>
    fun saveOrUpdateNote(note: Note): LiveData<Result<Note>>
}