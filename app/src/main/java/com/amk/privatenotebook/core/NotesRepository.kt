package com.amk.privatenotebook.core

interface NotesRepository {
    fun notes(): List<Note>
    fun updateNote(note: Note)
    fun updateHeaderName(note: Note, header: String)
    fun addNote(note: Note)
    fun deleteNote(note: Note)
}