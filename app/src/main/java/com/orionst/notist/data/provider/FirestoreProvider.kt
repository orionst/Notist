package com.orionst.notist.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.orionst.notist.data.entity.Note
import com.orionst.notist.data.entity.User
import com.orionst.notist.data.errors.NoAuthException
import com.orionst.notist.model.NoteResult

class FirestoreProvider(private val firebaseAuth: FirebaseAuth, private val store: FirebaseFirestore) : RemoteDataProvider {

    private val currentUser get() = firebaseAuth.currentUser

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User?>().apply {
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().addSnapshotListener { snapshot, e ->
                this.value = e?.let {
                    throw it
                } ?: snapshot?.let { snap ->
                    val notes = snap.documents.map { it.toObject(Note::class.java) }
                    NoteResult.Success(notes)
                }
            }
        } catch (e: Throwable) {
            this.value = NoteResult.Error(e)
        }
    }

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(id).get()
                .addOnSuccessListener {
                    val note = it.toObject(Note::class.java)
                    this.value = NoteResult.Success(note)
                }
                .addOnFailureListener {
                    throw it
                }
        } catch (e: Throwable) {
            this.value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(note.id)
                .set(note)
                .addOnSuccessListener {
                    Timber.d { "Note $note is saved" }
                    this.value = NoteResult.Success(note)
                }.addOnFailureListener {
                    Timber.e(it) { "Error saving note $note" }
                    throw it
                }
        } catch (e: Throwable) {
            this.value = NoteResult.Error(e)
        }
    }

    override fun deleteNoteById(id: String) = MutableLiveData<NoteResult>().apply {
        getUserNotesCollection().document(id).delete()
            .addOnSuccessListener {
                value = NoteResult.Success(null)
            }
            .addOnFailureListener {
                value = NoteResult.Error(it)
            }
    }

    // Get notes without auth from storage
//
//    override fun subscribeToAllNotes() =  MutableLiveData<NoteResult>().apply {
//        notesReference.addSnapshotListener { snapshot, e ->
//            this.value = e?.let {
//                NoteResult.Error(e)
//            } ?: snapshot?.let {snap ->
//                    val notes = snap.documents.map { it.toObject(Note::class.java) }
//                NoteResult.Success(notes)
//                }
//            }
//    }
//
//    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
//        notesReference.document(id)
//            .get()
//            .addOnSuccessListener { snapshot ->
//                val note = snapshot.toObject(Note::class.java)
//                this.value = NoteResult.Success(note)
//            }.addOnFailureListener {
//                Timber.e(it) {"Error reading note with id $id"}
//                this.value = NoteResult.Error(it)
//            }
//    }
//
//    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
//        notesReference.document(note.id)
//            .set(note)
//            .addOnSuccessListener {
//                Timber.d { "Note $note is saved" }
//                this.value = NoteResult.Success(note)
//            }.addOnFailureListener {
//                Timber.e(it) { "Error saving note $note" }
//                this.value = NoteResult.Error(it)
//            }
//    }

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }
}
