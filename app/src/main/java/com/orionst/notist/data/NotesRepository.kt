package com.orionst.notist.data

import com.orionst.notist.data.entity.Note
import com.orionst.notist.data.provider.RemoteDataProvider

class NotesRepository(val remoteDataProvider: RemoteDataProvider) {

    fun  getCurrentUser() = remoteDataProvider.getCurrentUser()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()

    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)

    fun deleteNote(id: String) = remoteDataProvider.deleteNoteById(id)

    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
}