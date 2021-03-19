package com.opah.desafio.felipe.ui.hq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.opah.desafio.felipe.models.CharacterResponse
import com.opah.desafio.felipe.models.CharacterResults
import com.opah.desafio.felipe.models.DataHQ
import com.opah.desafio.felipe.repository.CharacterRepository
import com.opah.desafio.felipe.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HQViewModel(private val repository: CharacterRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState>
        get() = _state

    private val findComics = MutableLiveData<DataHQ>()


    fun initViewModel(){
        findComics.postValue(repository.getDataHQ())
        //_state.postValue(ScreenState.GetHQ(findComics.value!!))
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


    sealed class ScreenState {
        data class GetHQ(val value: DataHQ) : ScreenState()
    }
}