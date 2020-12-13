package com.amk.privatenotebook.core.database.providers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.exeptions.NoAuthException
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FireStoreProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()

    private val mockDocument = mockk<DocumentReference>()
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    private val provider = FireStoreProvider(mockDb, mockAuth)


    @Before
    fun setUp() {
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every {
            mockDb.collection(any()).document(any()).collection(any())
        } returns mockResultCollection

        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]

    }

    @Test(expected = NoAuthException::class)
    fun `no auth - throw NoAuthException`() {
        var result: Any? = null
        every { mockAuth.currentUser } returns null

        provider.getAllNotes().observeForever {
            result = it
        }
    }

    @Test
    fun `returns notes`() {
        var result: List<Note>? = null
        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockSnapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()

        provider.getAllNotes().observeForever {
            result = it
        }

        slot.captured.onEvent(mockSnapshot, null)
        assertEquals(testNotes, result)

    }

    @Test
    fun `saveOrUpdateNote - save success`() {
        var result: Note? = null//testNotes[0].copy(headerName = "100")
        val slot = slot<OnSuccessListener<in Void>>()

        every { mockResultCollection.document(testNotes[0].uuidNote) } returns mockDocument
        every {
            mockDocument.set(testNotes[0]).addOnSuccessListener(capture(slot))
        } returns mockk()
        provider.saveOrUpdateNote(testNotes[0]).observeForever() {
            result = it.getOrNull()
        }

        slot.captured.onSuccess(null)
        assertEquals(testNotes[0], result)
    }

    @Test
    fun `saveOrUpdateNote - update success`() {
        var result: Note? = testNotes[0]
        val newNote = testNotes[0].copy(headerName = "1100")
        val slot = slot<OnSuccessListener<in Void>>()

        every { mockResultCollection.document(testNotes[0].uuidNote) } returns mockDocument
        every {
            mockDocument.set(newNote).addOnSuccessListener(capture(slot))
        } returns mockk()
        provider.saveOrUpdateNote(newNote).observeForever() {
            result = it.getOrNull()
        }

        slot.captured.onSuccess(null)
        assertEquals(newNote, result)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}