package com.amk.privatenotebook.core

import java.util.*


data class Note(
    val topicName: String,
    val subTopicList: List<Subtopic>,
    val uuidNote: UUID = UUID.randomUUID(),
)

data class Subtopic(
    val subtopicName: String,
    val text: String,
    val uuidSubTopic: UUID = UUID.randomUUID(),
)