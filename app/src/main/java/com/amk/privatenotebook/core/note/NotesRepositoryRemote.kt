package com.amk.privatenotebook.core.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.database.provider.FireStoreProvider


object NotesRepositoryRemote : NotesRepository {

    val notesRepository: NotesRepository by lazy { NotesRepositoryRemote }
    private val TAG = "${NotesRepositoryRemote::class.java.simpleName} :"

    private val remoteDataProvider = FireStoreProvider()

    override fun notes(): LiveData<List<Note>> {
        return remoteDataProvider.getAllNotes()
    }

    override fun updateNote(note: Note): LiveData<Result<Note>> {
        return remoteDataProvider.saveOrUpdateNote(note)
    }

    override fun updateHeaderName(note: Note, header: String): LiveData<Result<Note>> {
        note.headerName = header
        return updateNote(note)
    }

    override fun addNote(note: Note): LiveData<Result<Note>> {
        return updateNote(note)
    }

    override fun getNoteById(id: String): LiveData<Note> =
        MutableLiveData(remoteDataProvider.getAllNotes().value?.find { it.uuidNote == id })


    override fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser() = remoteDataProvider.getCurrentUser()
}