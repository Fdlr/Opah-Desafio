package com.opah.desafio.felipe.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opah.desafio.felipe.models.CharacterResponse
import com.opah.desafio.felipe.repository.CharacterRepository
import com.opah.desafio.felipe.utils.Constants.ERRODEFAULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val repository: CharacterRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val characterListLiveData = MutableLiveData<CharacterResponse>()
    private val messageReturn = MutableLiveData<String>()


    private val _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState>
        get() = _state

    fun takeIntention(intention: Intention) {
        when (intention) {
            is Intention.LoadInitialData -> {
                getCharacters()
            }
            is Intention.NavigateToDetail -> {

            }
        }
    }

    fun getCharacters() {
        _state.postValue(ScreenState.Loading)

        launch {
            try {
                if (repository.getCharacters().isSuccessful) {
                    characterListLiveData.postValue(repository.getCharacters().body())
                    _state.postValue(ScreenState.ApiSuccess(characterListLiveData.value!!))
                } else {
                    messageReturn.postValue(ERRODEFAULT)
                    _state.postValue(ScreenState.ApiError(messageReturn.value!!))
                }
            } catch (e: Exception) {
                messageReturn.postValue(ERRODEFAULT)
                _state.postValue(ScreenState.ApiError(messageReturn.value!!))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


    sealed class ScreenState {
        object Loading : ScreenState()
        object NavigateDetails : ScreenState()

        data class ApiSuccess(val value: CharacterResponse) : ScreenState()
        data class ApiError(val error: String) : ScreenState()
    }

    sealed class Intention {
        object LoadInitialData : Intention()
        data class NavigateToDetail(val value: CharacterResponse) : Intention()
    }

    class MainIntention(private val emit: (Intention) -> Unit) {
        fun loadInitialData() = emit(Intention.LoadInitialData)
        fun navigateToDetail(value: CharacterResponse) = emit(Intention.NavigateToDetail(value))
    }
}