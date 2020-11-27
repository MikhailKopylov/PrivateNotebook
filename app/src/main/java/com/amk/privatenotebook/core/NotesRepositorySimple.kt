package com.amk.privatenotebook.core

object NotesRepositorySimple : NotesRepository {
    private val privateNotes: MutableList<Note> = initNotes()

    override fun notes(): List<Note> {
        return privateNotes.toList()
    }

    override fun updateNote(note: Note) {
        privateNotes.find { it.uuidNote == note.uuidNote }?.let {
            if (it == note) return
            privateNotes.remove(it)
        }
        privateNotes.add(note)
    }

    override fun updateHeaderName(note: Note, header: String) {
        val newNote = Note(header, note.uuidNote)
        newNote.addSubtopicList(note.getSubTopicList())
        privateNotes.find { it.uuidNote == newNote.uuidNote }?.let {
            if (it == newNote) return
            privateNotes.remove(it)
        }
        privateNotes.add(newNote)
    }

    override fun addNote(note: Note) {
        privateNotes.add(note)
    }

    override fun deleteNote(note: Note) {
        privateNotes.remove(note)
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