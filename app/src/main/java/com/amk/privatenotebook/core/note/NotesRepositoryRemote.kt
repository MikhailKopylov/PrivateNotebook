package com.amk.privatenotebook.core.note

import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.database.interfaces.DataProvider
import com.amk.privatenotebook.exeptions.NoFindNoteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class NotesRepositoryRemote(private val dataProvider: DataProvider) : NotesRepository {

    private val TAG = "${dataProvider::class.java.simpleName} :"

    override fun notes(): Flow<List<Note>> {
        return dataProvider.getAllNotes()
    }

    override suspend fun updateNote(note: Note): Note = withContext(Dispatchers.IO) {
        dataProvider.saveOrUpdateNote(note)
    }

    override suspend fun updateHeaderName(note: Note, header: String): Note {
        note.headerName = header
        return updateNote(note)
    }

    override suspend fun addNote(note: Note): Note = updateNote(note)


    override fun getNoteById(id: String): Note =
        dataProvider.getNoteById(id) ?: throw NoFindNoteException()


    override suspend fun deleteNote(note: Note): Boolean = withContext(Dispatchers.IO) {
        dataProvider.deleteNote(note.uuidNote)
    }

    override suspend fun deleteSubtopic(noteID: String, subtopic: Subtopic): Boolean =
        withContext(Dispatchers.IO) {
            dataProvider.deleteSubtopic(noteID, subtopic)
        }

    override fun getCurrentUser() = dataProvider.getCurrentUser()
}