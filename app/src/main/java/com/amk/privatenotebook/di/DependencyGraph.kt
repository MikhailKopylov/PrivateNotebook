package com.amk.privatenotebook.di

import com.amk.privatenotebook.core.database.interfaces.DataProvider
import com.amk.privatenotebook.core.database.providers.FireStoreProvider
import com.amk.privatenotebook.core.note.NotesRepository
import com.amk.privatenotebook.core.note.NotesRepositoryRemote
import com.amk.privatenotebook.presentation.BodyViewModel
import com.amk.privatenotebook.presentation.HeaderViewModel
import com.amk.privatenotebook.presentation.SplashViewModel
import com.amk.privatenotebook.presentation.SubtopicViewModel
import com.amk.privatenotebook.ui.ItemTouchHelperAdapter
import com.amk.privatenotebook.ui.headerFragment.HeaderFragment
import com.amk.privatenotebook.ui.headerFragment.TopicAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

object DependencyGraph {

    private val repositoryModule by lazy {
        module {
            single { FirebaseFirestore.getInstance() }
            single { FirebaseAuth.getInstance() }
            single { FireStoreProvider(get(), get()) } bind DataProvider::class
            single { NotesRepositoryRemote(get()) } bind NotesRepository::class
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { BodyViewModel(get()) }
            viewModel { HeaderViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { SubtopicViewModel(get()) }
        }
    }

    val modules: List<Module> = listOf(repositoryModule, viewModelModule)
}