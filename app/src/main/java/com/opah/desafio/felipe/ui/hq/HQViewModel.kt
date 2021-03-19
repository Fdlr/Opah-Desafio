package com.opah.desafio.felipe.ui.hq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opah.desafio.felipe.models.MarvelComicsResponse
import com.opah.desafio.felipe.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class HQViewModel(private val repository: CharacterRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState>
        get() = _state


    fun initViewModel() {
        repository.getDataHQ()?.let {
            _state.postValue(ScreenState.GetHQ(it))
        }

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


    sealed class ScreenState {
        data class GetHQ(val value: MarvelComicsResponse) : ScreenState()
    }
}