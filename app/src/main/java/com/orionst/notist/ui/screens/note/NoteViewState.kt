package com.orionst.notist.ui.screens.note

import com.orionst.notist.data.entity.Note
import com.orionst.notist.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)
