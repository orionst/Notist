package com.orionst.notist.ui.screens.splash

import com.orionst.notist.data.NotesRepository
import com.orionst.notist.data.errors.NoAuthException
import com.orionst.notist.ui.base.BaseViewModel

class SplashViewModel(private val repository: NotesRepository): BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        repository.getCurrentUser().observeForever {
            viewStateLiveData.value = if (it != null) {
                SplashViewState(isAuth = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}