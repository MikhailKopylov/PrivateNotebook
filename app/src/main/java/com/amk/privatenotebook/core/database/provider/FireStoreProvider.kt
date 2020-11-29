package com.amk.privatenotebook.core.database.provider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amk.privatenotebook.core.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

private const val NOTES_COLLECTION = "notes_collection"

class FireStoreProvider : RemoteDataProvider {

    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)
    private val result = MutableLiveData<List<Note>>()

    private var isNotSubscribeOnDbChange = true

    override fun getAllNotes(): LiveData<List<Note>> {
        if (isNotSubscribeOnDbChange) {
            subscribeOnDbChange()
        }
        return result
    }

    private fun subscribeOnDbChange() {
        notesReference.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(TAG, "Error loading list note , message: ${error.message}")
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

    override fun getNoteById(id: String): LiveData<Note> {
        val result = MutableLiveData<Note>()

        notesReference.document(id)
            .get()
            .addOnSuccessListener {
                result.value =
                    it.toObject(Note::class.java)
            }.addOnFailureListener {
                result.value = null
            }
        return result
    }

    override fun saveOrUpdateNote(note: Note): LiveData<Result<Note>> {
        val result = MutableLiveData<Result<Note>>()

        notesReference.document(note.uuidNote)
            .set(note).addOnSuccessListener {
                Log.d(TAG, "Note $note is saved")
                result.value = Result.success(note)
            }.addOnFailureListener {
                Log.d(TAG, "Error saving note $note, message: ${it.message}")
                result.value = Result.failure(it)
            }
        return result
    }
}