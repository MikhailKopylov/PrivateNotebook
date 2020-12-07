package com.amk.privatenotebook.core.database.providers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.core.Subtopic
import com.amk.privatenotebook.core.database.interfaces.DataProvider
import com.amk.privatenotebook.core.user.User
import com.amk.privatenotebook.exeptions.ErrorLoadingListNotes
import com.amk.privatenotebook.exeptions.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

private const val NOTES_COLLECTION = "notes_collection"
private const val USERS_COLLECTION = "users_collection"

class FireStoreProvider : DataProvider {

    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

    private val db = FirebaseFirestore.getInstance()
    private val result = MutableLiveData<List<Note>>()

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    private var isNotSubscribeOnDbChange = true

    override fun getAllNotes(): LiveData<List<Note>> {
        if (isNotSubscribeOnDbChange) {
            subscribeOnDbChange()
        }
        return result
    }

    private fun subscribeOnDbChange() {
        getUserNotesCollection().addSnapshotListener { snapshot, error ->
            try {
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
            } catch (e: Throwable) {
                Log.d(TAG, "Error loading list note , message: ${e.message}")
            }
        }
        isNotSubscribeOnDbChange = false
    }

    private fun getNoteById(id: String): MutableLiveData<Note> = MutableLiveData<Note>().apply {

        getUserNotesCollection().document(id)
            .get()
            .addOnSuccessListener {
                value =
                    it.toObject(Note::class.java)
            }.addOnFailureListener {
                value = null
            }

    }

    override fun saveOrUpdateNote(note: Note): LiveData<Result<Note>> =
        MutableLiveData<Result<Note>>().apply {
            try {
                getUserNotesCollection().document(note.uuidNote)
                    .set(note).addOnSuccessListener {
                        Log.d(TAG, "Note $note is saved")
                        value = Result.success(note)
                    }.addOnFailureListener {
                        Log.d(TAG, "Error saving note $note, message: ${it.message}")
                        throw it
                    }
            } catch (e: Throwable) {
                value = Result.failure(e)
            }
        }

    override fun deleteNote(id: String): LiveData<Boolean> =
        MutableLiveData<Boolean>().apply {
            try {
                getUserNotesCollection().document(id)
                    .delete().addOnSuccessListener {
                        value = true
                    }.addOnFailureListener {
                        throw it
                    }
            } catch (e: Throwable) {
                value = false
            }
        }

    override fun deleteSubtopic(noteID: String, subtopic: Subtopic): LiveData<Boolean> =
        MutableLiveData<Boolean>().apply {
            val note = result.value?.find { it.uuidNote == noteID } ?: return@apply
            note.deleteSubtopic(subtopic)
            saveOrUpdateNote(note).map { it.isSuccess }
        }


    override fun getCurrentUser(): LiveData<User?> =
        MutableLiveData<User?>().apply {
            currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
        }


    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()
}