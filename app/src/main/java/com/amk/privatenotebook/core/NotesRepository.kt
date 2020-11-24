package com.amk.privatenotebook.core

interface NotesRepository {
    fun notes(): List<Note>
}