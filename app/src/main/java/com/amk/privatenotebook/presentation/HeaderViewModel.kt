package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.note.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HeaderViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    //TODO add Result.failure handling

    private val notesLiveData: MutableLiveData<HeaderViewState> = MutableLiveData<HeaderViewState>()

    init {
        notesRepository.notes().onEach {
            notesLiveData.value =
                if (it.isEmpty()) HeaderViewState.EMPTY else HeaderViewState.NotesList(it)
        }
            .launchIn(viewModelScope)
    }

    fun observableTopicViewState(): LiveData<HeaderViewState> = notesLiveData

    fun addNote(note: Note) =
        viewModelScope.launch {
            notesRepository.addNote(note)
        }


    @ExperimentalCoroutinesApi
    fun deleteNote(note: Note) = viewModelScope.launch {
        notesRepository.deleteNote(note)
    }


}