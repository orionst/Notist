package com.orionst.notist.ui.screens.note_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.orionst.notist.data.entity.NotesRepository
import com.orionst.notist.ui.NotesViewState

class NoteListViewModel : ViewModel() {

    private val viewStateLiveData : MutableLiveData<NotesViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = NotesViewState(NotesRepository.notes)
    }

    fun viewState(): LiveData<NotesViewState> = viewStateLiveData
}
