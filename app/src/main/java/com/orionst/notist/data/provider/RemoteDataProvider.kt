package com.orionst.notist.data.provider

import androidx.lifecycle.LiveData
import com.orionst.notist.data.entity.Note
import com.orionst.notist.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
}