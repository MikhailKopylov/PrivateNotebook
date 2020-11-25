package com.amk.privatenotebook.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.privatenotebook.core.Subtopic

class ToBodyViewModel : ViewModel() {

    val subtopicLiveData = MutableLiveData<Subtopic>()
    fun selectBody(subtopic: Subtopic) {
        subtopicLiveData.value = subtopic
    }
}