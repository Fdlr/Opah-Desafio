package com.opah.desafio.felipe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opah.desafio.felipe.models.CharacterResponse
import com.opah.desafio.felipe.models.CharacterResults
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
                repository.savePosition(intention.valuesPosition)
                navigateDetails(intention.valuesPosition)
            }
        }
    }

    fun getCharacters() {
        _state.postValue(ScreenState.Loading)

        launch {
            try {
                if (repository.getCharacters().isSuccessful) {
                    characterListLiveData.postValue(repository.getCharacters().body())
                    _state.postValue(
                            ScreenState.ApiSuccess(
                                    characterListLiveData.value!!
                            )
                    )
                } else {
                    messageReturn.postValue(ERRODEFAULT)
                    _state.postValue(
                            ScreenState.ApiError(
                                    messageReturn.value ?: ERRODEFAULT
                            )
                    )
                }
            } catch (e: Exception) {
                messageReturn.postValue(ERRODEFAULT)
                _state.postValue(
                        ScreenState.ApiError(
                                messageReturn.value ?: ERRODEFAULT
                        )
                )
            }
        }
    }

    fun navigateDetails(characterResults: CharacterResults) {
        _state.postValue(ScreenState.NavigateDetails(characterResults))
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


    sealed class ScreenState {
        object Loading : ScreenState()

        data class NavigateDetails(val value: CharacterResults) : ScreenState()
        data class ApiSuccess(val value: CharacterResponse) : ScreenState()
        data class ApiError(val error: String) : ScreenState()
    }

    sealed class Intention {
        object LoadInitialData : Intention()
        data class NavigateToDetail(val valuesPosition: CharacterResults) : Intention()
    }

    class HomeIntention(private val emit: (Intention) -> Unit) {
        fun loadInitialData() = emit(Intention.LoadInitialData)
        fun navigateToDetail(value: CharacterResults) = emit(Intention.NavigateToDetail(value))
    }
}