package com.amk.privatenotebook.core.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.user.User


class NotesRepositorySimple : NotesRepository {
    private val privateNotes: MutableList<Note> = initNotes()

    override fun notes(): LiveData<List<Note>> {
        return MutableLiveData(privateNotes.toList())
    }

    override fun updateNote(note: Note): LiveData<Result<Note>> {
        val result = MutableLiveData<Result<Note>>()
        privateNotes.find { it.uuidNote == note.uuidNote }?.let {
            if (it == note) {
                result.value = Result.success(note)
            }
            privateNotes.remove(it)
        }
        privateNotes.add(note)
        result.value = Result.success(note)
        return result
    }

    override fun updateHeaderName(note: Note, header: String): LiveData<Result<Note>> {
        val result = MutableLiveData<Result<Note>>()
        val newNote = Note(header, note.uuidNote)
        newNote.addSubtopicList(note.getSubTopicList())
        privateNotes.find { it.uuidNote == newNote.uuidNote }?.let {
            if (it == newNote) {
                result.value = Result.success(note)
            }
            privateNotes.remove(it)
        }
        result.value = Result.success(note)
        privateNotes.add(newNote)
        return result
    }

    override fun addNote(note: Note): LiveData<Result<Note>> {
        val result = MutableLiveData<Result<Note>>()
        privateNotes.add(note)
        result.value = Result.success(note)
        return result
    }

    override fun getNoteById(id: String): LiveData<Note> {
        val result = MutableLiveData<Note>()

        result.value = privateNotes.find { it.uuidNote == id }
        return result
    }


    override fun deleteNote(note: Note): LiveData<Boolean> =
        MutableLiveData<Boolean>().apply {
            value = privateNotes.remove(note)
        }

    override fun deleteSubtopic(noteID: String, subtopic: Subtopic): LiveData<Boolean> =
        MutableLiveData<Boolean>().apply { privateNotes.find { (it.uuidNote == noteID) }?.deleteSubtopic(subtopic)}



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