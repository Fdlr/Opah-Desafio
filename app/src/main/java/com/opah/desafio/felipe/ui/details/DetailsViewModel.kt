package com.opah.desafio.felipe.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opah.desafio.felipe.models.CharacterResults
import com.opah.desafio.felipe.models.DataHQ
import com.opah.desafio.felipe.repository.CharacterRepository
import com.opah.desafio.felipe.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailsViewModel(private val repository: CharacterRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState>
        get() = _state


    fun takeIntention(intention: Intention) {
        when (intention) {
            is Intention.LoadInitialData -> {
                _state.postValue(ScreenState.GetPosition(repository.getPosition()!!))
            }
            is Intention.NavigateToHome -> {
                _state.postValue(ScreenState.NavigateToHome)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getComicsByCharacterId() {

        launch {
            try {
                if (repository.findComics(repository.getPosition()!!.characterId).isSuccessful) {
                    repository.saveHQ(repository.findComics(repository.getPosition()!!.characterId).body()!!)
                    _state.postValue(ScreenState.NavigateToHq)
                }
            } catch (e: Exception) {
                _state.postValue(ScreenState.ApiError(Constants.ERRODEFAULT))
            }
        }
    }

    sealed class ScreenState {
        object NavigateToHq : ScreenState()
        object NavigateToHome : ScreenState()

        data class GetPosition(val value: CharacterResults) : ScreenState()
        data class ApiSuccess(val value: DataHQ) : ScreenState()
        data class ApiError(val error: String) : ScreenState()
    }

    sealed class Intention {
        object LoadInitialData : Intention()
        object NavigateToHome : Intention()
    }

    class DetailsIntention(private val emit: (Intention) -> Unit) {
        fun loadInitialData() = emit(Intention.LoadInitialData)
        fun navigateToHome() = emit(Intention.NavigateToHome)
    }
}