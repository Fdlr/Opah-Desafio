package com.opah.desafio.felipe.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opah.desafio.felipe.models.CharacterResults
import com.opah.desafio.felipe.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
                ScreenState.GetPosition(repository.getPosition()!!)
            }
            is Intention.NavigateToHQ -> {
                _state.postValue(ScreenState.NavigateToHq)
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

    sealed class ScreenState {
        object NavigateToHq : ScreenState()
        object NavigateToHome : ScreenState()

        data class GetPosition(val value: CharacterResults) : ScreenState()
    }

    sealed class Intention {
        object LoadInitialData : Intention()
        object NavigateToHome : Intention()
        data class NavigateToHQ(val value: CharacterResults) : Intention()

    }

    class DetailsIntention(private val emit: (Intention) -> Unit) {
        fun loadInitialData() = emit(Intention.LoadInitialData)
        fun navigateToHQ(value: CharacterResults) = emit(Intention.NavigateToHQ(value))
        fun navigateToHome() = emit(Intention.NavigateToHome)
    }
}