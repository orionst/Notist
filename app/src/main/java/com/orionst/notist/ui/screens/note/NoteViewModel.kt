package com.orionst.notist.ui.screens.note

import com.orionst.notist.data.NotesRepository
import com.orionst.notist.data.entity.Note
import com.orionst.notist.model.NoteResult
import com.orionst.notist.ui.base.BaseViewModel

class NoteViewModel(private val repository: NotesRepository) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val currentNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun save(note: Note){
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    override fun onCleared() {
        currentNote?.let {
            repository.saveNote(it)
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { result ->
            result?.let {
                viewStateLiveData.value = when (it) {
                    is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(note = it.data as Note?))
                    is NoteResult.Error -> NoteViewState(error = it.error)
                }
            }
        }
    }

    fun deleteNote() {
        currentNote?.let {note ->
            repository.deleteNote(note.id).observeForever { result->
                result?.let {
                    viewStateLiveData.value = when (it) {
                        is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                        is NoteResult.Error -> NoteViewState(error = it.error)
                    }
                }
            }
        }
    }

}