package com.orionst.notist.ui.screens.note

import com.orionst.notist.data.NotesRepository
import com.orionst.notist.data.entity.Note
import com.orionst.notist.model.NoteResult
import com.orionst.notist.ui.base.BaseViewModel

class NoteViewModel(private val repository: NotesRepository = NotesRepository) : BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun save(note: Note){
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            repository.saveNote(it)
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { result ->
            result ?: let { return@observeForever }

            when (result) {
                is NoteResult.Success<*> ->
                    viewStateLiveData.value = NoteViewState(result.data as? Note)
                is NoteResult.Error ->
                    viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
    }

}