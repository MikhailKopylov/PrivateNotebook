package com.amk.privatenotebook.core

import java.util.*


data class Note(
    val headerName: String,
    val uuidNote: UUID = UUID.randomUUID(),
) {
    private val subTopicList: MutableList<Subtopic> = mutableListOf()

    fun addSubtopic(subtopic: Subtopic) {
        subTopicList.add(subtopic)
    }

    fun updateSubtopic(newSubtopic: Subtopic) {
        subTopicList.find { it.uuidSubTopic == newSubtopic.uuidSubTopic }?.let {
            if (newSubtopic == it) return
            subTopicList.remove(it)
        }
        subTopicList.add(newSubtopic)
    }

    fun getSubTopicList(): List<Subtopic> = subTopicList.toList()

    fun deleteSubtopic(subtopic: Subtopic) {
        subTopicList.remove(subtopic)
    }
}

data class Subtopic(
    val note: Note,
    val subtopicName: String = "",
    val body: String = "",
    val uuidSubTopic: UUID = UUID.randomUUID(),
)
