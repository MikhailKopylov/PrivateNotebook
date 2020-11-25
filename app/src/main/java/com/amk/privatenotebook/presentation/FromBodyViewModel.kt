package com.amk.privatenotebook.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.privatenotebook.core.Subtopic

class FromBodyViewModel : ViewModel() {

    val subtopicLiveData = MutableLiveData<Subtopic>()

    fun select(subtopic: Subtopic) {
        subtopicLiveData.value = subtopic
    }

    fun onUpdate(subtopicName: String, body: String) {
        val note = subtopicLiveData.value?.note ?: return
        val uuidSubtopic = subtopicLiveData.value?.uuidSubTopic ?: return
        val subtopic = Subtopic(note, subtopicName, body, uuidSubtopic)
        subtopicLiveData.value = subtopic
        subtopicLiveData.value?.note?.updateSubtopic(subtopic)
    }

}