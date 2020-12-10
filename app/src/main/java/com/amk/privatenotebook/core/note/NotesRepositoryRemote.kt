package com.amk.privatenotebook.core.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.database.interfaces.DataProvider


class NotesRepositoryRemote(private val dataProvider: DataProvider) : NotesRepository {

    private val TAG = "${dataProvider::class.java.simpleName} :"

    override fun notes(): LiveData<List<Note>> {
        return dataProvider.getAllNotes()
    }

    override fun updateNote(note: Note): LiveData<Result<Note>> {
        return dataProvider.saveOrUpdateNote(note)
    }

    override fun updateHeaderName(note: Note, header: String): LiveData<Result<Note>> {
        note.headerName = header
        return updateNote(note)
    }

    override fun addNote(note: Note): LiveData<Result<Note>> {
        return updateNote(note)
    }

    override fun getNoteById(id: String): LiveData<Note> =
        MutableLiveData(dataProvider.getAllNotes().value?.find { it.uuidNote == id })


    override fun deleteNote(note: Note): LiveData<Boolean> =
        dataProvider.deleteNote(note.uuidNote)

    override fun deleteSubtopic(noteID: String, subtopic: Subtopic): LiveData<Boolean> =
        dataProvider.deleteSubtopic(noteID, subtopic)



    override fun getCurrentUser() = dataProvider.getCurrentUser()
}