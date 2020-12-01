package com.amk.privatenotebook.core

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    var headerName: String = "",
    val uuidNote: String = UUID.randomUUID().toString().replace("-", ""),
    val lastChangesHeader: Date = Date(),
    private val subTopicList: MutableList<Subtopic> = mutableListOf()
) : Parcelable {

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

    fun addSubtopicList(subtopicList: List<Subtopic>) {
        subTopicList.addAll(subtopicList)
    }

    fun deleteSubtopic(subtopic: Subtopic) {
        subTopicList.remove(subtopic)
    }
}

@Parcelize
data class Subtopic(
    val noteID: String = "",
    val subtopicName: String = "",
    val body: String = "",
    val uuidSubTopic: String = UUID.randomUUID().toString().replace("-", ""),
    val lastChangesHeader: Date = Date(),
) : Parcelable
