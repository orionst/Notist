package com.orionst.notist.ui.screens.note

import androidx.lifecycle.ViewModel
import com.orionst.notist.data.NotesRepository
import com.orionst.notist.data.entity.Note

class NoteViewModel(private val repository: NotesRepository = NotesRepository) : ViewModel() {

    private var pendingNote: Note? = null

    fun save(note: Note){
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            repository.saveNote(it)
        }
    }

}