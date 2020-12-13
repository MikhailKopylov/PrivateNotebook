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

class BodyViewModelTest {
    @get:Rule
    val taskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val notesRepositoryMock: NotesRepository = mockk()
    private val noteLiveData = MutableLiveData<Result<Note>>()

    private lateinit var viewModel: BodyViewModel
    private val testNote = Note("101")


    @Before
    fun setUp() {
        clearAllMocks()
        testNote.addSubtopic(Subtopic(subtopicName = "S101", body = "B101"))
        testNote.addSubtopic(Subtopic(subtopicName = "S102", body = "B102"))
        every { notesRepositoryMock.updateNote(testNote) } returns noteLiveData
        viewModel = BodyViewModel(notesRepositoryMock)
    }

    @Test
    fun `update note success`() {
        var result: Subtopic? = null
        val testSubtopic = testNote.getSubTopicList()[0].copy(subtopicName = "NewNameSubtopic")
        viewModel.selectBody(testNote.getSubTopicList()[0])
        viewModel.subtopicLiveData().observeForever {
            result = it
        }

        viewModel.onUpdate(testNote, testSubtopic.subtopicName, testSubtopic.body)
        assertEquals(testSubtopic.noteID, result?.noteID)
        assertEquals(testSubtopic.subtopicName, result?.subtopicName)
        assertEquals(testSubtopic.uuidSubTopic, result?.uuidSubTopic)
        assertEquals(testSubtopic.body, result?.body)
    }

}