package com.opah.desafio.felipe.repository

import com.opah.desafio.felipe.models.CharacterResults
import com.opah.desafio.felipe.network.api.ApiService
import com.orhanobut.hawk.Hawk

class CharacterRepository(private val apiService: ApiService) {

    suspend fun getCharacters() = apiService.getCharacters().await()

    fun savePosition(characterResults: CharacterResults) {
       Hawk.put(CHARACTERRESULTS, characterResults)
    }

    fun getPosition(): CharacterResults? {
        return Hawk.get(CHARACTERRESULTS)
    }

    companion object {
        const val CHARACTERRESULTS ="characterResults"
    }
}