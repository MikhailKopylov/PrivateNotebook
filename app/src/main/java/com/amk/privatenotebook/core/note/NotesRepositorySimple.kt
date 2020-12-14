package com.amk.privatenotebook.core.note

import androidx.lifecycle.LiveData
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.user.User
import com.amk.privatenotebook.exeptions.NoFindNoteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class NotesRepositorySimple : NotesRepository {
    private val privateNotes: MutableList<Note> = initNotes()

    override fun notes(): Flow<List<Note>> {
        return MutableStateFlow(privateNotes.toList())
    }

    override suspend fun updateNote(note: Note): Note {

        privateNotes.find { it.uuidNote == note.uuidNote }?.let {
            privateNotes.remove(it)
        }
        privateNotes.add(note)
        return note
    }

    override suspend fun updateHeaderName(note: Note, header: String): Note {
        val newNote = Note(header, note.uuidNote)
        newNote.addSubtopicList(note.getSubTopicList())
        privateNotes.find { it.uuidNote == newNote.uuidNote }?.let {
            privateNotes.remove(it)
        }
        privateNotes.add(newNote)
        return newNote
    }

    override suspend fun addNote(note: Note): Note {
        privateNotes.add(note)
        return note
    }

    override fun getNoteById(id: String): Note {
        return privateNotes.find { it.uuidNote == id } ?: throw NoFindNoteException()
    }


    override suspend fun deleteNote(note: Note): Boolean =
        privateNotes.remove(note)

    override suspend fun deleteSubtopic(noteID: String, subtopic: Subtopic): Boolean =
        privateNotes.find { (it.uuidNote == noteID) }?.deleteSubtopic(subtopic)
            ?: throw NoFindNoteException()


    override fun getCurrentUser(): LiveData<User?> {
        TODO("Not yet implemented")
    }


    private fun initNotes(): MutableList<Note> {
        val notesList = mutableListOf(
            Note("Welcome in Private Notebook "),
//            Note("TitleSecond"),
//            Note("TitleThird"),
//            Note("TitleFourth"),
//            Note("TitleFifth"),
//            Note("TitleSixth"),
//            Note("TitleSeventh"),
//            Note("TitleEighth"),
//            Note("TitleNinth"),
        )
//        notesList.forEach {
//            for (i in 1..3) {
//                it.addSubtopic(
//                    Subtopic(
//                        it,
//                        "${it.headerName} subtopic $i",
//                        "${it.headerName} body $i"
//                    )
//                )
//            }
//        }
        return notesList
    }

}