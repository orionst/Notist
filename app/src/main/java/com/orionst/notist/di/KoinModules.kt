package com.orionst.notist.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.orionst.notist.data.NotesRepository
import com.orionst.notist.data.provider.FirestoreProvider
import com.orionst.notist.data.provider.RemoteDataProvider
import com.orionst.notist.ui.screens.note.NoteViewModel
import com.orionst.notist.ui.screens.note_list.NoteListViewModel
import com.orionst.notist.ui.screens.splash.SplashViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<RemoteDataProvider> { FirestoreProvider(get(), get()) }
    single { NotesRepository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val noteListModule = module {
    viewModel { NoteListViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}