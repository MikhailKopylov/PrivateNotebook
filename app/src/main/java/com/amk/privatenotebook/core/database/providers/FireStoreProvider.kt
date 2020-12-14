package com.amk.privatenotebook.core.database.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.database.interfaces.DataProvider
import com.amk.privatenotebook.core.user.User
import com.amk.privatenotebook.exeptions.ErrorLoadingListNotes
import com.amk.privatenotebook.exeptions.NoAuthException
import com.amk.privatenotebook.exeptions.NoFindNoteException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val NOTES_COLLECTION = "notes_collection"
private const val USERS_COLLECTION = "users_collection"

class FireStoreProvider(
    private val db: FirebaseFirestore, private val auth: FirebaseAuth
) : DataProvider {

    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

    @ExperimentalCoroutinesApi
    private val result = MutableStateFlow<List<Note>?>(null)
    private var isNotSubscribeOnDbChange = true

    private val currentUser
        get() = auth.currentUser


    @ExperimentalCoroutinesApi
    override fun getAllNotes(): Flow<List<Note>> {
        if (isNotSubscribeOnDbChange) {
            subscribeOnDbChange()
        }
        return result.filterNotNull()
    }


    @ExperimentalCoroutinesApi
    private fun subscribeOnDbChange() {
        getUserNotesCollection().addSnapshotListener { snapshot, error ->
            if (error != null) {
                throw ErrorLoadingListNotes()
            }
            if (snapshot != null) {
                val notes = mutableListOf<Note>()

                for (doc: QueryDocumentSnapshot in snapshot) {
                    notes.add(doc.toObject(Note::class.java))
                }
                result.value = notes
            }
        }
        isNotSubscribeOnDbChange = false
    }

    @ExperimentalCoroutinesApi
    override fun getNoteById(id: String): Note? = result.value?.find { it.uuidNote == id }
//        MutableStateFlow<Note?>(null).apply {
//            getUserNotesCollection().document(id).get()
//                .addOnSuccessListener {
//                    this.value = it.toObject(Note::class.java) ?: throw IllegalArgumentException()
//                }.addOnFailureListener {
//                    throw NoFindNoteException()
//                }
//        }.filterNotNull()


    override suspend fun saveOrUpdateNote(note: Note): Note =
        suspendCoroutine { continuation ->
            try {
                getUserNotesCollection().document(note.uuidNote)
                    .set(note).addOnSuccessListener {
                        continuation.resume(note)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }


    override suspend fun deleteNote(id: String): Boolean =
        suspendCoroutine { continuation ->
            try {
                getUserNotesCollection().document(id).delete()
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }

    @ExperimentalCoroutinesApi
    override fun deleteSubtopic(noteID: String, subtopic: Subtopic):Boolean {
        try {
            val note: Note? = result.value?.find { it.uuidNote == noteID }
            if (note != null) {
                return note.deleteSubtopic(subtopic)
            } else throw NoFindNoteException()
        } catch (e: Throwable) {
            throw Throwable(e)
        }
    }


    override fun getCurrentUser(): LiveData<User?> =
        MutableLiveData<User?>().apply {
            currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
        }


    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()


}