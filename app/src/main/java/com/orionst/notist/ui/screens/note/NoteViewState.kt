package com.orionst.notist.ui.screens.note

import com.orionst.notist.data.entity.Note
import com.orionst.notist.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}