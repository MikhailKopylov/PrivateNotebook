package com.amk.privatenotebook.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.note.NotesRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SubtopicViewModelTest {

    @get:Rule
    val taskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val notesRepositoryMock: NotesRepository = mockk()
    private val noteLiveData = MutableLiveData<Note>()
    private lateinit var viewModel: SubtopicViewModel
    private val testNote = Note(
        headerName = "101",
        subTopicList = mutableListOf(Subtopic(subtopicName = "S101"), Subtopic(subtopicName = "S102"))
    )


    @Before
    fun setUp() {
        clearAllMocks()
        every { notesRepositoryMock.getNoteById(testNote.uuidNote) } returns noteLiveData
        viewModel = SubtopicViewModel(notesRepositoryMock)
    }

    @Test
    fun `call getNote success`() {
        var result: Note? = null
        viewModel.selectNote(testNote.uuidNote)
        viewModel.observableSubtopicList().observeForever {
            if (it is SubtopicViewState) result = it.note
        }

        noteLiveData.value = testNote
        assertEquals(testNote, result)
    }


//    @Test
//    fun `call delete note success`(){
//        var result: Note? = null
//        viewModel.selectNote(testNote.uuidNote)
//        viewModel.observableSubtopicList().observeForever {
//            if (it is SubtopicViewState) result = it.note
//        }
//
//        viewModel.deleteSubtopic(testNote.getSubTopicList()[0])
//        noteLiveData.value = testNote
//        assertEquals(testNote, result)
//
//    }
}