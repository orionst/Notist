package com.orionst.notist.ui.screens.note_list

import androidx.lifecycle.Observer
import com.orionst.notist.data.NotesRepository
import com.orionst.notist.data.entity.Note
import com.orionst.notist.model.NoteResult
import com.orionst.notist.ui.base.BaseViewModel

class NoteListViewModel(private val repository: NotesRepository = NotesRepository) :
    BaseViewModel<List<Note>?, NotesViewState>() {

    private val notesObserver = Observer<NoteResult> { result ->
        result ?: let { return@Observer }

        when (result) {
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = NotesViewState(result.data as? List<Note>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = NotesViewState(error = result.error)
            }
        }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = NotesViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}
