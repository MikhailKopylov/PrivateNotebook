package com.amk.privatenotebook.core

import androidx.lifecycle.LiveData

interface NotesRepository {

    fun notes(): LiveData<List<Note>>
    fun updateNote(note: Note): LiveData<Result<Note>>
    fun updateHeaderName(note: Note, header: String): LiveData<Result<Note>>
    fun addNote(note: Note): LiveData<Result<Note>>
    fun getNoteById(id: String): LiveData<Note>
    fun deleteNote(note: Note)
}