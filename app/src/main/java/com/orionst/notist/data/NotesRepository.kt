package com.orionst.notist.data

import com.orionst.notist.data.entity.Note
import com.orionst.notist.data.provider.FirestoreProvider
import com.orionst.notist.data.provider.RemoteDataProvider

object NotesRepository {
    private val remoteDataProvider: RemoteDataProvider = FirestoreProvider()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()

    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)

    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
}