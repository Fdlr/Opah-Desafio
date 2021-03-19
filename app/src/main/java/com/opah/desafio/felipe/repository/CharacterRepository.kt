package com.opah.desafio.felipe.repository

import androidx.lifecycle.MutableLiveData
import com.opah.desafio.felipe.models.CharacterResults
import com.opah.desafio.felipe.network.api.ApiService

class CharacterRepository(private val apiService: ApiService) {
    private val _characterResults = MutableLiveData<CharacterResults>()
    val characterResults: MutableLiveData<CharacterResults>
        get() = _characterResults

    suspend fun getCharacters() = apiService.getCharacters().await()

    fun savePosition(characterResults: CharacterResults) {
        _characterResults.postValue(characterResults)
    }

    fun getPosition(): CharacterResults? {
        return characterResults.value
    }
}