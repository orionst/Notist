package com.orionst.notist.ui.screens.note_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.orionst.notist.data.NotesRepository

class NoteListViewModel(private val repository: NotesRepository = NotesRepository) : ViewModel() {

    private val viewStateLiveData : MutableLiveData<NotesViewState> = MutableLiveData()

    init {
        repository.getNotes().observeForever { notes ->
            viewStateLiveData.value = viewStateLiveData.value?.copy(notes = notes!!) ?: NotesViewState(notes!!)
        }
    }

    fun viewState(): LiveData<NotesViewState> = viewStateLiveData
}
