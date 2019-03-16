package com.orionst.notist.ui.screens.note_list

import com.orionst.notist.data.entity.Note
import com.orionst.notist.ui.base.BaseViewState

class NotesViewState(val notes: List<Note>? = null, error: Throwable? = null): BaseViewState<List<Note>?>(notes, error)