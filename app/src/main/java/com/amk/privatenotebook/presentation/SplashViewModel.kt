package com.amk.privatenotebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amk.privatenotebook.core.note.NotesRepository
import com.amk.privatenotebook.exeptions.NoAuthException
import java.util.concurrent.Executors

class SplashViewModel(private val repository: NotesRepository) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<SplashViewState>()

    init {
        Executors.newSingleThreadExecutor()
            .submit {
                requestUser()
            }
    }

    fun observeViewState(): LiveData<SplashViewState> = viewStateLiveData

    private fun requestUser() {
        val user = repository.getCurrentUser().value

        viewStateLiveData.postValue(
            if (user != null) {
                SplashViewState.Auth
            } else {
                SplashViewState.Error(error = NoAuthException())
            }
        )
    }
}
