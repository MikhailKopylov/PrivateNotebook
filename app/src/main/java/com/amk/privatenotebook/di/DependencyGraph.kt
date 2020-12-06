package com.amk.privatenotebook.di

import com.amk.privatenotebook.core.database.interfaces.DataProvider
import com.amk.privatenotebook.core.database.providers.FireStoreProvider
import com.amk.privatenotebook.core.note.NotesRepository
import com.amk.privatenotebook.core.note.NotesRepositoryRemote
import com.amk.privatenotebook.presentation.BodyViewModel
import com.amk.privatenotebook.presentation.HeaderViewModel
import com.amk.privatenotebook.presentation.SplashViewModel
import com.amk.privatenotebook.presentation.SubtopicViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

object DependencyGraph {

    private val repositoryModule by lazy {
        module {
            single { NotesRepositoryRemote(get()) } bind NotesRepository::class
            single { FireStoreProvider() } bind DataProvider::class
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { BodyViewModel(get())}
            viewModel { HeaderViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { SubtopicViewModel() }
        }
    }

    val modules:List<Module> = listOf(repositoryModule, viewModelModule)
}