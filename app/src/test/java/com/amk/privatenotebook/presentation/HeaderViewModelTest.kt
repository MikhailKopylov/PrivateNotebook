package com.amk.privatenotebook.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.note.NotesRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HeaderViewModelTest {

    @get:Rule
    val taskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val notesRepositoryMock: NotesRepository = mockk()
    private val notesLiveData = MutableLiveData<List<Note>>()
    private lateinit var viewModel: HeaderViewModel

    @Before
    fun setUp() {
        clearAllMocks()
        every { notesRepositoryMock.notes() } returns notesLiveData
        viewModel = HeaderViewModel(notesRepositoryMock)
    }

    @Test
    fun `Call getNotes Success`() {
        var result: List<Note>? = null
        val testData = listOf(Note(headerName = "1"), Note(headerName = "2"))
        viewModel.observableTopicViewState().observeForever {
            if (it is HeaderViewState.NotesList) result = it.notes
        }
        notesLiveData.value = testData
        assertEquals(testData, result)
    }


    @Test(expected = Throwable::class)
    fun `Call getNotes error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.observableTopicViewState().observeForever {
            if (it is Throwable) result = it
        }
        assertEquals(testData, result)
    }
}

