package com.amk.privatenotebook.core

object NotesRepositorySimple : NotesRepository {
    private val privateNotes: MutableList<Note> = initNotes()

    override fun notes(): List<Note> {
        return privateNotes.toList()
    }

    private fun initNotes(): MutableList<Note> {
        return mutableListOf(
            Note("TitleFirst", listOf(Subtopic("SubtitleFirst", "TextFirst"))),
            Note("TitleSecond", listOf(Subtopic("SubtitleSecond", "TextSecond"))),
            Note("TitleThird", listOf(Subtopic("SubtitleThird", "TextThird"))),
            Note("TitleFourth", listOf(Subtopic("SubtitleFourth", "TextFourth"))),
            Note("TitleFifth", listOf(Subtopic("SubtitleFifth", "TextFifth"))),
            Note("TitleSixth", listOf(Subtopic("SubtitleSixth", "TextSixth"))),
            Note("TitleSeventh", listOf(Subtopic("SubtitleSeventh", "TextSeventh"))),
            Note("TitleEighth", listOf(Subtopic("SubtitleEighth", "TextEighth"))),
            Note("TitleNinth", listOf(Subtopic("SubtitleNinth", "TextNinth"))),
        )
    }

}